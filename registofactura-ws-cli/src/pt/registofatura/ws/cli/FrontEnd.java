package pt.registofatura.ws.cli;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.registry.JAXRException;
import javax.xml.ws.BindingProvider;

import pt.faults.ws.FaultsPortType;
import pt.faults.ws.FaultsService;
import pt.registofatura.ws.ClienteInexistente_Exception;
import pt.registofatura.ws.EmissorInexistente_Exception;
import pt.registofatura.ws.Fatura;
import pt.registofatura.ws.FaturaInvalida_Exception;
import pt.registofatura.ws.RegistoFaturaPortType;
import pt.registofatura.ws.RegistoFaturaService;
import pt.registofatura.ws.Serie;
import pt.registofatura.ws.TotaisIncoerentes_Exception;
import example.ws.cli.UDDINaming;

public class FrontEnd implements RegistoFaturaPortType {

    private static class Operation {
        private boolean comunicarFatura = false;
        private boolean pedirSerie = false;

        private Fatura fatura;
        private int emissor;

        private Operation(Fatura fatura) {
            comunicarFatura = true;
            this.fatura = fatura;
        }

        private Operation(int emissor) {
            pedirSerie = true;
            this.emissor = emissor;
        }

        private boolean isComunicarFatura() {
            return comunicarFatura;
        }

        private boolean isPedirSerie() {
            return pedirSerie;
        }

        public Fatura getFatura() {
            return fatura;
        }

        public int getEmissor() {
            return emissor;
        }
    }

    public static boolean repetirPedidos = false;
    public static int repetirPedidosLastSeq = -1;
    public static boolean repeticaoFeita = false;
    
    public static int seq = 0;

    public static int ticket;

    private String name;

    private RegistoFaturaPortType port;

    private final Map<Integer, Operation> operations = new HashMap<>();

    private Map<String, Object> requestContext;

    private int retries = 10;

    private String uddiUrl;

    public FrontEnd(String name, String uddiUrl) {
        super();
        this.name = name;
        this.uddiUrl = uddiUrl;
        port = initJuddi(uddiUrl, name);
    }

    /**
     * Comunica ao servidor todos os pedidos de escrita efectuados desde seq.
     * Nao sao guardados pedidos que deram excecoes por isso se o server 
     * pedir duas vezes para repetir pedidos enviar um header a dizer que ja
     * foi feito isso.
     * 
     * @param seq
     */
    public void sendOperationsSince(int seq) {
        int oldSeq = FrontEnd.seq;
        ArrayList<Integer> listOfSeqs = new ArrayList<>(operations.keySet());
        Collections.sort(listOfSeqs);
        for (Integer i : listOfSeqs) {
            if (i > seq) {
                FrontEnd.seq = i;
                Operation op = operations.get(i);
                if (operations.get(i).isComunicarFatura()) {
                    try {
                        comunicarFatura(op.getFatura());
                    } catch (ClienteInexistente_Exception
                            | EmissorInexistente_Exception
                            | FaturaInvalida_Exception
                            | TotaisIncoerentes_Exception e) {
                        // ja foi comunicado
                    }
                } else if (operations.get(i).isPedirSerie()) {
                    try {
                        pedirSerie(op.getEmissor());
                    } catch (EmissorInexistente_Exception e) {
                        // ja foi comunicado
                    }
                }
            }
        }

        FrontEnd.seq = oldSeq;
    }

    @Override
    public void comunicarFatura(Fatura parameters)
            throws ClienteInexistente_Exception, EmissorInexistente_Exception,
            FaturaInvalida_Exception, TotaisIncoerentes_Exception {
        int tries = 0;
        while (tries++ < retries) {
            try {
                port.comunicarFatura(parameters);
                if(repetirPedidos) {
                    sendOperationsSince(FrontEnd.repetirPedidosLastSeq);
                    continue;
                }
                operations.put(seq, new Operation(parameters));
                if (seq < 20) seq++;
                break;
            } catch (Exception e) {
                if (e instanceof ClienteInexistente_Exception
                        || e instanceof EmissorInexistente_Exception
                        || e instanceof FaturaInvalida_Exception
                        || e instanceof TotaisIncoerentes_Exception) {
                    if (seq < 20) seq++;
                    throw e;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Attempting to reconnect");
            }
        }
    }

    @Override
    public int consultarIVADevido(int nifEmissor, XMLGregorianCalendar ano)
            throws EmissorInexistente_Exception {
        int tries = 0;
        while (tries++ < retries) {
            try {
                int iva = port.consultarIVADevido(nifEmissor, ano);
                return iva;
            } catch (Exception e) {
                if (e instanceof EmissorInexistente_Exception) {
                    throw e;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Attempting to reconnect");
            }
        }
        return 0;
    }

    public int getRetries() {
        return retries;
    }

    private RegistoFaturaPortType initJuddi(String uddiUrl, String name) {
        UDDINaming uddiNaming = null;
        String registoFaturasAddress = null;
        String faultsAddress = null;
        int tries = 0;
        
        while (tries++ < retries) {
            try {
                uddiNaming = new UDDINaming(uddiUrl);
                registoFaturasAddress = uddiNaming.lookup(name);
                break;
            } catch (JAXRException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Attempting to reconnect");
            }
        }
        tries = 0;
        String faults = "faults";
        int faultsServiceCount = 0;
        while (tries++ < retries) {
            try {
                faultsAddress = uddiNaming.lookup(faults + faultsServiceCount);
                faultsServiceCount++;
                break;
            } catch (JAXRException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Attempting to reconnect");
            }
        }

        if (registoFaturasAddress == null || faultsAddress == null) {
            System.out.println("Service off-line");
            System.exit(-1);
        }
        RegistoFaturaService service = new RegistoFaturaService();
        RegistoFaturaPortType port = service.getRegistoFaturaPort();
        BindingProvider bindingProvider = (BindingProvider) port;
        requestContext = bindingProvider.getRequestContext();

        requestContext.put(ENDPOINT_ADDRESS_PROPERTY, registoFaturasAddress);

        FaultsService faultsService = new FaultsService();
        FaultsPortType faultsPort = faultsService.getFaultsPort();
        BindingProvider faultsBindingProvider = (BindingProvider) faultsPort;
        Map<String, Object> faultsRequestContext = faultsBindingProvider
                .getRequestContext();
        faultsRequestContext.put(ENDPOINT_ADDRESS_PROPERTY, faultsAddress);

        tries = 0;
        while (tries++ < retries) {
            try {
                ticket = faultsPort.ticket(true);
                break;
            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Attempting to reconnect");
            }
        }

        return port;
    }

    @Override
    public List<Fatura> listarFacturas(Integer nifEmissor, Integer nifCliente)
            throws ClienteInexistente_Exception, EmissorInexistente_Exception {
        int tries = 0;
        while (tries++ < retries) {
            try {
                List<Fatura> faturas = port.listarFacturas(nifEmissor,
                        nifCliente);
                return faturas;
            } catch (Exception e) {
                if (e instanceof ClienteInexistente_Exception
                        || e instanceof EmissorInexistente_Exception) {
                    throw e;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Attempting to reconnect");
            }
        }
        return null;
    }

    @Override
    public Serie pedirSerie(int nifEmissor) throws EmissorInexistente_Exception {
        int tries = 0;
        while (tries++ < retries) {
            try {
                Serie serie = port.pedirSerie(nifEmissor);
                if(repetirPedidos) {
                    sendOperationsSince(FrontEnd.repetirPedidosLastSeq);
                    continue;
                }
                operations.put(seq, new Operation(nifEmissor));
                seq++;
                return serie;
            } catch (Exception e) {
                if (e instanceof EmissorInexistente_Exception) {
                    seq++;
                    throw e;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Attempting to reconnect");
            }
        }
        return null;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

}
