package pt.registofatura.ws.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

import pt.faults.ws.FaultsPortType;
import pt.registofatura.ws.Broadcaster;
import pt.registofatura.ws.EmissorInexistente_Exception;
import pt.registofatura.ws.Fatura;
import pt.registofatura.ws.FaultsState;
import pt.registofatura.ws.ItemFatura;
import pt.registofatura.ws.Serie;
import pt.registofatura.ws.handler.HeaderHandler;
import pt.registofatura.ws.handler.History;
import pt.registofatura.ws.handler.Record;

/**
 * The Class FaultsImpl.
 */
@WebService(endpointInterface = "pt.faults.ws.FaultsPortType", wsdlLocation = "Faults.wsdl", name = "FaultsService", portName = "FaultsPort", targetNamespace = "urn:pt:faults:ws", serviceName = "FaultsService")
@HandlerChain(file = "/main/java/pt/registofatura/ws/handler/handler-chain.xml")
public class FaultsImpl
        implements FaultsPortType {
    
    /**
     * The Constant logger.
     */
    private static final Logger logger = Logger.getLogger(FaultsImpl.class);
    
    /**
     * The web service context.
     */
    @Resource
    private WebServiceContext webServiceContext;


    /* (non-Javadoc)
     * @see pt.faults.ws.FaultsPortType#getLastServer(boolean)
     */
    @Override
    public int getLastServer(boolean notUsed) {
        FaultsState faltas = FaultsState.getInstance();
        int lastServer = faltas.getLast();
        faltas.setLast(lastServer + 1);
        return lastServer;
    }

    /* (non-Javadoc)
     * @see pt.faults.ws.FaultsPortType#imAlive()
     */
    @Override
    public synchronized int imAlive() {
        FaultsState faults = FaultsState.getInstance();
        int father = faults.getFather();
        logger.info("Ping from son, sending his grandfather: " + father);
        return father;
    }

    /* (non-Javadoc)
     * @see pt.faults.ws.FaultsPortType#connectTo(int)
     */
    @Override
    public synchronized void connectTo(int newServer) {
        logger.info("Got new son @" + newServer);
        FaultsState faultsState = FaultsState.getInstance();
        faultsState.setSon(newServer);
    }

    /*
     * O servidor principal pede aos secundarios para atualizarem
     * o seu estado atraves dos seguintes metodos. Falta arranjar
     * um modo dos pedidos ficarem por ordem.
     */

    /* (non-Javadoc)
     * @see pt.faults.ws.FaultsPortType#pedirSerie(int)
     */
    @Override
    public synchronized void pedirSerie(int nifEmissor) {
        Broadcaster.pushPedirSerie(nifEmissor, getTicket(), getSeq());
        try {
            if (!isDupRequest()) {
                Record record = new Record();
                int ticket = getTicket();
                History history = History.getInstance();
                history.insertRecord(ticket, record);
                record.setSeq(getSeq());
                try {
                    Serie serie = new RegistoFaturaImpl().pedirSerieFF(nifEmissor);
                    record.setSerie(serie);
                    return;
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (EmissorInexistente_Exception e) {
            System.out.println("pedir serie sync lancou excecao");
        }
    }

    /* (non-Javadoc)
     * @see pt.faults.ws.FaultsPortType#comunicarFatura(javax.xml.datatype.XMLGregorianCalendar, int, int, int, java.lang.String, java.lang.Integer, java.util.List, int, int)
     */
    @Override
    public synchronized void comunicarFatura(XMLGregorianCalendar data,
                                             int numSeqFatura,
                                             int numSerie,
                                             int nifEmissor,
                                             String nomeEmissor,
                                             Integer nifCliente,
                                             List<ItemFatura> itens,
                                             int iva,
                                             int total) {
        // FIXME
        Fatura fatura = new Fatura();
        fatura.setData(data);
        fatura.setNumSeqFatura(numSeqFatura);
        fatura.setNumSerie(numSerie);
        fatura.setNifEmissor(nifEmissor);
        fatura.setNifCliente(nifCliente);
        fatura.getItens().addAll(itens);
        fatura.setIva(iva);
        fatura.setTotal(total);

        Broadcaster.pushComunicarFatura(fatura, getTicket(), getSeq());

        try {
            if (!isDupRequest()) {
                Record record = new Record();
                int ticket = getTicket();
                History history = History.getInstance();
                history.insertRecord(ticket, record);
                record.setSeq(getSeq());
                logger.info("SeqSerie: " + numSeqFatura);
                try {
                    new RegistoFaturaImpl().comunicarFaturaFF(fatura);
                    record.setComunicarFaturaAnswer(true);
                    return;
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e) {
            logger.warn("Exception from comunicaFatura."/*, e*/);
        }

    }

    /* (non-Javadoc)
     * @see pt.faults.ws.FaultsPortType#ticket(boolean)
     */
    @Override
    public synchronized int ticket(boolean notUsed) {
        FaultsState faltas = FaultsState.getInstance();
        int ticket = faltas.getATicket();
        Broadcaster.pushTicket(0, 0);
        logger.info("ticket() : " + ticket);
        return ticket;
    }

    /**
     * Gets the seq.
     *
     * @return the seq
     */
    private int getSeq() {
        MessageContext messageContext = webServiceContext.getMessageContext();
        return (int) messageContext.get(HeaderHandler.SEQ_PROPERTY);
    }

    /**
     * Gets the ticket.
     *
     * @return the ticket
     */
    public int getTicket() {
        MessageContext messageContext = webServiceContext.getMessageContext();
        return (int) messageContext.get(HeaderHandler.TICKET_PROPERTY);
    }

    /**
     * Checks if is dup request.
     *
     * @return true, if is dup request
     */
    public boolean isDupRequest() {
        MessageContext messageContext = webServiceContext.getMessageContext();
        return (boolean) messageContext.get(HeaderHandler.DUP_PROPERTY);
    }
}
