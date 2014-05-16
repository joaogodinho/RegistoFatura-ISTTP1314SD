package pt.registofatura.ws.cli;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import pt.registofatura.ws.Fatura;
import pt.registofatura.ws.RegistoFaturaPortType;

public class RegistoFaturaClient {

    public static void main(String[] args) throws Exception {

        String uddiURL = System.getProperty("uddiURL", "http://localhost:8081");
        String name = System.getProperty("name", "registofatura");

        RegistoFaturaPortType port = Utility.initJuddi(uddiURL, name);

        /* testes */
        

        XMLGregorianCalendar dataTestes = Utility.todaysDate();
        
        System.out.println("----------------------");
        System.out.println("Pedir serie bem sucedida");

        int numSerie = 0;

        try {
            numSerie = port.pedirSerie(5111).getNumSerie();
            System.out.println("Serie: " + numSerie);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------------");
        System.out.println("Pedir 10 series bem sucedidas para o portal nif=1234");
        
        List<Integer> seriesDoPortal = new ArrayList<>();
        
        try {
            for(int i = 0; i < 10; i++) {
                seriesDoPortal.add(port.pedirSerie(1234).getNumSerie());
                System.out.println("Serie[" + i + "]: " + seriesDoPortal.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("----------------------");
        System.out.println("A comunicar Fatura bem sucedida entre emissor 5111 e cliente 1001");

        //data, numSerie, numSeq, nifEmissor, nomeEmissor, nifClient, items ...
        Fatura fatura = Utility.createFatura(Utility.todaysDate(), numSerie, 1, 5111, "bc", 1001, Utility.createItem("bacalhau", 40, 23));

        try {
            port.comunicarFatura(fatura);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------------");
        System.out.println("Listar Faturas entre Emissor 5111 e Cliente 1001");

        try {
            for (Fatura f : port.listarFacturas(5111, 1001)) {
                System.out.println("Fatura encontrada:");
                System.out.println("Nif do Emissor: " + f.getNifEmissor()
                        + " | Nif do Cliente: " + f.getNifCliente() + " | "
                        + " Data: " + f.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------------");
        System.out.println("Pedir serie a uma entidade nao existente: 9999");

        int numSerie2 = 0;
        try {
            numSerie2 = port.pedirSerie(9999).getNumSerie();
            System.out.println("Serie: " + numSerie2);
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Comunicar fatura de um emissor inexistente");

        //data, numSerie, numSeq, nifEmissor, nomeEmissor, nifClient, items ...
        fatura = Utility.createFatura(Utility.todaysDate(), numSerie2, 1, 5223, "bc", 1001, Utility.createItem("bacalhau", 40, 23), Utility.createItem(), Utility.createItem());
        
        try {
            port.comunicarFatura(fatura);
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Comunicar fatura de um cliente inexistente");

        //data, numSerie, numSeq, nifEmissor, nomeEmissor, nifClient, items ...
        fatura = Utility.createFatura(Utility.todaysDate(), numSerie, 2, 5222, "bf", 1005, Utility.createItem(), Utility.createItem("atum", 56, 25), Utility.createItem());
        
        try {
            port.comunicarFatura(fatura);
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Comunicar fatura com totais inconsistentes");

        //data, numSerie, numSeq, nifEmissor, nomeEmissor, nifClient, items ...
        fatura = Utility.createFatura(Utility.todaysDate(), numSerie, 1, 5222, "bf", 1005, Utility.createItem(), Utility.createItem("atum", 56, 25), Utility.createItem());
        //tornar o total inconsistente
        fatura.setTotal(fatura.getTotal() * 3 + 4);
        
        try {
            port.comunicarFatura(fatura);
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Comunicar fatura com totais inconsistentes devido ao IVA");

        //data, numSerie, numSeq, nifEmissor, nomeEmissor, nifClient, items ...
        fatura = Utility.createFatura(Utility.todaysDate(), numSerie, 1, 5222, "bf", 1005, Utility.createItem(), Utility.createItem("atum", 56, 25), Utility.createItem());

        //tornar valor inconsistente
        fatura.setIva(fatura.getIva()+10);
        
        try {
            port.comunicarFatura(fatura);
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Comunicar fatura com uma serie inexistente");

        //data, numSerie, numSeq, nifEmissor, nomeEmissor, nifClient, items ...
        fatura = Utility.createFatura(Utility.todaysDate(), 9999, 1, 1234, "portal", 1001, Utility.createItem(), Utility.createItem(), Utility.createItem(), Utility.createItem());
        
        try {
            port.comunicarFatura(fatura);
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Comunicar fatura com uma data superior 10 dias em relacao a criacao da serie");

        //data, numSerie, numSeq, nifEmissor, nomeEmissor, nifClient, items ...
        fatura = Utility.createFatura(Utility.addToDate(Utility.todaysDate(), 0, 0, 11), seriesDoPortal.get(1), 1, 1234, "portal", 1001, Utility.createItem(), Utility.createItem(), Utility.createItem(), Utility.createItem());
        
        try {
            port.comunicarFatura(fatura);
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }
        
        System.out.println("----------------------");
        System.out.println("Comunicar fatura com uma data inferior a outra fatura da mesma serie");

        //data, numSerie, numSeq, nifEmissor, nomeEmissor, nifClient, items ...
        fatura = Utility.createFatura(dataTestes, numSerie, 2, 5222, "bf", 1005, Utility.createItem(), Utility.createItem("atum", 56, 25), Utility.createItem());
        
        try {
            port.comunicarFatura(fatura);
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Comunicar fatura com um numero de sequencia ja enviado anteriormente");

        //data, numSerie, numSeq, nifEmissor, nomeEmissor, nifClient, items ...
        fatura = Utility.createFatura(Utility.todaysDate(), numSerie, 1, 5222, "bf", 1001, Utility.createItem(), Utility.createItem());
        
        try {
            port.comunicarFatura(fatura);
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }
        
        System.out.println("----------------------");
        System.out.println("Comunicar faturas ate encher a serie");
        
        try {
            System.out.println("Serie a encher: " + seriesDoPortal.get(4));
            for(int i = 1; true; i++) {
                // data, numSerie, numSeq, nifEmissor, nomeEmissor, nifClient, items ...
                System.out.println("seqNum: " + i);
                fatura = Utility.createFatura(Utility.todaysDate(), seriesDoPortal.get(4), i, 1234, "portal", 1001, Utility.createItem(), Utility.createItem());
                port.comunicarFatura(fatura);
            }
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Comunicar fatura com um numero de serie de outro emissor");

        // data, numSerie, numSeq, nifEmissor, nomeEmissor, nifClient, items ...
        fatura = Utility.createFatura(Utility.todaysDate(), seriesDoPortal.get(5), 1, 5111, "portal", 1001, Utility.createItem(), Utility.createItem());
        
        try {
            port.comunicarFatura(fatura);
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Listar todas as faturas do cliente 1001");

        try {
            for (Fatura f : port.listarFacturas(null, 1001)) {
                System.out.println("Fatura encontrada:");
                System.out.println("Nif do Emissor: " + f.getNifEmissor()
                        + " | Nif do Cliente: " + f.getNifCliente() + " | "
                        + " Data: " + f.getData());
            }
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Listar todas as faturas do emissor 5111");

        try {
            for (Fatura f : port.listarFacturas(5111, null)) {
                System.out.println("Fatura encontrada:");
                System.out.println("Nif do Emissor: " + f.getNifEmissor()
                        + " | Nif do Cliente: " + f.getNifCliente() + " | "
                        + " Data: " + f.getData());
            }
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Listar todas as faturas");

        try {
            for (Fatura f : port.listarFacturas(null, null)) {
                System.out.println("Fatura encontrada:");
                System.out.println("Nif do Emissor: " + f.getNifEmissor()
                        + " | Nif do Cliente: " + f.getNifCliente() + " | "
                        + " Data: " + f.getData());
            }
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Listar faturas com um emissor desconhecido");

        try {
            for (Fatura f : port.listarFacturas(9999, 1001)) {
                System.out.println("Fatura encontrada:");
                System.out.println("Nif do Emissor: " + f.getNifEmissor()
                        + " | Nif do Cliente: " + f.getNifCliente() + " | "
                        + " Data: " + f.getData());
            }
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Listar faturas com um cliente desconhecido");

        try {
            for (Fatura f : port.listarFacturas(1234, 9999)) {
                System.out.println("Fatura encontrada:");
                System.out.println("Nif do Emissor: " + f.getNifEmissor()
                        + " | Nif do Cliente: " + f.getNifCliente() + " | "
                        + " Data: " + f.getData());
            }
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

        System.out.println("----------------------");
        System.out.println("Consultar IVA de um emissor conhecido");

        try {
            System.out.println("Iva do portal nif=5111: " + port.consultarIVADevido(5111, Utility.todaysDate()));
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }
        
        System.out.println("----------------------");
        System.out.println("Consultar IVA de um emissor desconhecido");

        try {
            System.out.println("Iva: " + port.consultarIVADevido(9999, Utility.todaysDate()));
        } catch (Exception e) {
            System.out.println("Apanhada a excepcao: " + e.getMessage());
        }

    }
}
