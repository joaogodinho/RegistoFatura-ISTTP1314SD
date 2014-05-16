package pt.registofatura.ws.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import jvstm.Atomic;

import org.apache.log4j.Logger;

import pt.ist.fenixframework.FenixFramework;
import pt.registofatura.domain.Entidade;
import pt.registofatura.domain.Financa;
import pt.registofatura.ws.Broadcaster;
import pt.registofatura.ws.ClienteInexistente_Exception;
import pt.registofatura.ws.EmissorInexistente_Exception;
import pt.registofatura.ws.Fatura;
import pt.registofatura.ws.FaturaInvalida_Exception;
import pt.registofatura.ws.FaultsState;
import pt.registofatura.ws.ItemFatura;
import pt.registofatura.ws.RegistoFaturaPortType;
import pt.registofatura.ws.Serie;
import pt.registofatura.ws.TotaisIncoerentes_Exception;
import pt.registofatura.ws.handler.HeaderHandler;
import pt.registofatura.ws.handler.History;
import pt.registofatura.ws.handler.Record;

/**
 * The Class RegistoFaturaImpl.
 */
@WebService(endpointInterface = "pt.registofatura.ws.RegistoFaturaPortType",
    wsdlLocation = "RegistoFatura.1_0.wsdl",
    name = "RegistoFaturaService",
    portName = "RegistoFaturaPort",
    targetNamespace = "urn:pt:registofatura:ws",
    serviceName = "RegistoFaturaService")
@HandlerChain(file="/main/java/pt/registofatura/ws/handler/handler-chain.xml")
public class RegistoFaturaImpl
        implements RegistoFaturaPortType {
    
    /**
     * The Constant logger.
     */
    public static final Logger logger = Logger.getLogger(RegistoFaturaImpl.class);
    
    /**
     * The Constant SERIE_EXPIRATION_LIMIT.
     */
    private static final int SERIE_EXPIRATION_LIMIT = 10; // days
    
    /**
     * The Constant SERIE_SEQ_INIT.
     */
    private static final int SERIE_SEQ_INIT = 1;
    
    /**
     * The Constant PERCENTAGEM_IVA.
     */
    private static final float PERCENTAGEM_IVA = 0.23f;
    
    /**
     * The web service context.
     */
    @Resource
    private WebServiceContext webServiceContext;

    
    
    /* (non-Javadoc)
     * @see pt.registofatura.ws.RegistoFaturaPortType#pedirSerie(int)
     */
    @Override
    public Serie pedirSerie(int nifEmissor) throws EmissorInexistente_Exception {
        
        //Broadcaster.pedirSerie(nifEmissor, getTicket(), getSeq());
        Broadcaster.pushPedirSerie(nifEmissor, getTicket(), getSeq());
        
        // se seq for negativo crashar servidor (razao de testes)
        // meter o crash DEPOIS do broadcast
        if(FaultsState.getInstance().crashOrder) {
            FaultsState.getInstance().crashOrder = false;
            System.exit(0);
        }
        
        if (isDupRequest()) {
            Record record = getLastRecord();
            // Duplicated request with exception
            if (record.getSerie() == null) {
                Serie serie = pedirSerieFF(nifEmissor);
                return serie;
            } else {
                return record.getSerie();
            }
        } else {
            Record record = new Record();
            int ticket = getTicket();
            record.setSeq(getSeq());
            try {
                Serie serie = pedirSerieFF(nifEmissor);                                            
                record.setSerie(serie);
                History history = History.getInstance();
                history.insertRecord(ticket, record);
                return serie;
            } catch (Exception e) { 
                History history = History.getInstance();
                history.insertRecord(ticket, record);
                throw e;
            }
        }
    }
    
    /**
     * Pedir serie ff.
     *
     * @param nifEmissor the nif emissor
     * @return the serie
     * @throws EmissorInexistente_Exception the emissor inexistente_ exception
     */
    @Atomic
    public Serie pedirSerieFF(int nifEmissor) throws EmissorInexistente_Exception {
        Financa financa = FenixFramework.getRoot();
        Entidade emissor = financa.getEntidadeByNif(nifEmissor);
        int nSerie = financa.getUltimoNumSerie();

        if (emissor == null) {
            throw ExceptionFactory.generateEmissorInexistenteException(nifEmissor);
        }

        // Create ws Serie
        Serie serie = new Serie();
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DAY_OF_MONTH, SERIE_EXPIRATION_LIMIT);
        serie.setValidoAte(convertCalendar(gc));
        serie.setNumSerie(nSerie);
        
        pt.registofatura.domain.Serie serieDomain = new pt.registofatura.domain.Serie(serie);
        serieDomain.setSeq(SERIE_SEQ_INIT);
        serieDomain.setEmissor(emissor);
        
        financa.addSerie(serieDomain);
        financa.setUltimoNumSerie(nSerie + 1);

        return serie;
    }

    /**
     * Convert calendar.
     *
     * @param gc the gc
     * @return the XML gregorian calendar
     */
    private XMLGregorianCalendar convertCalendar(GregorianCalendar gc) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException e) {
            System.out.println("RegistoFaturaImpl.pedirSerieFF(): convertCalendar();");
            e.printStackTrace();
        }
        return null;
    }


    /* (non-Javadoc)
     * @see pt.registofatura.ws.RegistoFaturaPortType#comunicarFatura(pt.registofatura.ws.Fatura)
     */
    @Override
    public void comunicarFatura(Fatura fatura) throws ClienteInexistente_Exception,
            EmissorInexistente_Exception,
            FaturaInvalida_Exception,
            TotaisIncoerentes_Exception {
        
        //Broadcaster.comunicarFatura(fatura, getTicket(), getSeq());
        Broadcaster.pushComunicarFatura(fatura, getTicket(), getSeq());
        
        // se seq for negativo crashar servidor (razao de testes)
        // meter o crash DEPOIS do broadcast
        if(FaultsState.getInstance().crashOrder) {
            FaultsState.getInstance().crashOrder = false;
            System.exit(0);
        }
        
        if (isDupRequest()) {
            Record record = getLastRecord();
            // Duplicated request with exception
            if (!record.getComunicarFaturaAnswer()) {
                comunicarFaturaFF(fatura);
            } else {
                return;
            }
        } else {
            Record record = new Record();
            int ticket = getTicket();
            record.setSeq(getSeq());
            try {
                comunicarFaturaFF(fatura);
                record.setComunicarFaturaAnswer(true);
                History history = History.getInstance();
                history.insertRecord(ticket, record);
                return;
            } catch (Exception e) { 
                History history = History.getInstance();
                history.insertRecord(ticket, record);
                throw e;
            }
        }
    }

    /**
     * Comunicar fatura ff.
     *
     * @param fatura the fatura
     * @throws ClienteInexistente_Exception the cliente inexistente_ exception
     * @throws EmissorInexistente_Exception the emissor inexistente_ exception
     * @throws FaturaInvalida_Exception the fatura invalida_ exception
     * @throws TotaisIncoerentes_Exception the totais incoerentes_ exception
     */
    @Atomic
    public void comunicarFaturaFF(Fatura fatura) throws ClienteInexistente_Exception,
            EmissorInexistente_Exception,
            FaturaInvalida_Exception,
            TotaisIncoerentes_Exception {
        Financa financa = FenixFramework.getRoot();
        Entidade emissor = financa.getEntidadeByNif(fatura.getNifEmissor());
        Entidade cliente = financa.getEntidadeByNif(fatura.getNifCliente());

        // Emissor inexistente
        if (emissor == null) {
            throw ExceptionFactory.generateEmissorInexistenteException(fatura.getNifEmissor());
        }
        // Cliente inexistente
        if (cliente == null) {
            throw ExceptionFactory.generateClienteInexistenteException(fatura.getNifCliente());
        }
        // Totais incoerentes
        int total = 0;
        for (ItemFatura item : fatura.getItens()) {
            total += item.getPreco();
        }
        if (total != fatura.getTotal()) {
            throw ExceptionFactory.generateTotaisIncoerentesException(total, fatura.getTotal());
        }

        float totalIva = 0;
        for (ItemFatura item : fatura.getItens()) {
            totalIva += (float) item.getPreco() * PERCENTAGEM_IVA / (1f + PERCENTAGEM_IVA);
        }

        if (Math.round(totalIva) != fatura.getIva()) {
            throw ExceptionFactory.generateTotaisIncoerentesByIvaException(Math.round(totalIva), fatura.getIva());
        }

        pt.registofatura.domain.Serie serie = financa.getSerieByNum(fatura.getNumSerie());

        //serie inexistente
        if (serie == null) {
            throw ExceptionFactory.generateFaturaInvalidaException("A serie nao existe",
                    fatura.getNumSeqFatura(), fatura.getNumSerie());
        }

        // Data invalida
        GregorianCalendar dataFatura = fatura.getData().toGregorianCalendar();
        GregorianCalendar dataSerie = serie.getData().toGregorianCalendar();
        if (dataSerie.compareTo(dataFatura) < 0) {
            throw ExceptionFactory.generateDataFaturaInvalidaException(fatura.getNumSeqFatura(),
                    serie.getNumero());
        }
        
        for (pt.registofatura.domain.Fatura f : serie.getFatura()) {
            GregorianCalendar dataF = f.getData().toGregorianCalendar();
            if (dataFatura.compareTo(dataF) < 0) {
                throw ExceptionFactory.generateFaturaInvalidaException(
                        "Data da fatura menor que outras faturas da serie",
                        fatura.getNumSeqFatura(), serie.getNumero());
            }
        }

        // NumSeqFatura invalido
        if (fatura.getNumSeqFatura() > 4 || fatura.getNumSeqFatura() < 1
                || fatura.getNumSeqFatura() != serie.getSeq()) {
            throw ExceptionFactory.generateFaturaInvalidaException("Sequencia da fatura invalida",
                    fatura.getNumSeqFatura(), serie.getNumero());
        }
        // Num serie invalido
        if (serie.getSeq() == 5/*limite faturas*/ ||
                serie.getEmissor().getNif() != fatura.getNifEmissor()) {
            throw ExceptionFactory.generateFaturaInvalidaException("Numero da serie invalido",
                    fatura.getNumSeqFatura(), serie.getNumero());
        }

        serie.setSeq(serie.getSeq() + 1);

        pt.registofatura.domain.Fatura faturaDomain = new pt.registofatura.domain.Fatura(
                fatura.getIva(), fatura.getData(), fatura.getNumSeqFatura(), fatura.getTotal(),
                serie, cliente, emissor);
        financa.addFatura(faturaDomain);
    }

    /* (non-Javadoc)
     * @see pt.registofatura.ws.RegistoFaturaPortType#listarFacturas(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<Fatura> listarFacturas(Integer nifEmissor, Integer nifCliente) throws ClienteInexistente_Exception,
            EmissorInexistente_Exception {
        return listarFaturasFF(nifEmissor, nifCliente);
    }

    /**
     * Listar faturas ff.
     *
     * @param nifEmissor the nif emissor
     * @param nifCliente the nif cliente
     * @return the list
     * @throws ClienteInexistente_Exception the cliente inexistente_ exception
     * @throws EmissorInexistente_Exception the emissor inexistente_ exception
     */
    @Atomic
    public List<Fatura> listarFaturasFF(Integer nifEmissor, Integer nifCliente) throws ClienteInexistente_Exception,
            EmissorInexistente_Exception {
        Financa financa = FenixFramework.getRoot();

        ArrayList<Fatura> faturas = new ArrayList<Fatura>();

        //caso ambos os nifs estejam vazios
        if (nifEmissor == null && nifCliente == null) {
            for (pt.registofatura.domain.Fatura f : financa.getFatura()) {
                faturas.add(convertFatura(f));
            }
            return faturas;
        }
        //caso apenas o nifEmissor esteja vazio
        else if (nifEmissor == null) {
            for (pt.registofatura.domain.Fatura f : financa.getFaturasFromClient(nifCliente)) {
                faturas.add(convertFatura(f));
            }
            return faturas;
        }
        //caso apenas o nifCliente esteja vazio
        else if (nifCliente == null) {
            for (pt.registofatura.domain.Fatura f : financa.getFaturasFromEmissor(nifEmissor)) {
                faturas.add(convertFatura(f));
            }
            return faturas;
        }
        //caso nenhum esteja vazio
        Entidade emissor = financa.getEntidadeByNif(nifEmissor);
        if (emissor == null) {
            throw ExceptionFactory.generateEmissorInexistenteException(nifEmissor);
        }
        Entidade cliente = financa.getEntidadeByNif(nifCliente);
        if (cliente == null) {
            throw ExceptionFactory.generateClienteInexistenteException(nifCliente);
        }

        for (pt.registofatura.domain.Fatura f : financa.getFaturasBetweenEmissorAndCliente(
                nifEmissor, nifCliente)) {
            faturas.add(convertFatura(f));
        }
        return faturas;
    }

    /**
     * Convert fatura.
     *
     * @param fatura the fatura
     * @return the fatura
     */
    public Fatura convertFatura(pt.registofatura.domain.Fatura fatura) {

        if (fatura == null)
            return null;

        pt.registofatura.ws.Fatura faturaService = new pt.registofatura.ws.Fatura();

        Date data = fatura.getData().toDate();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        faturaService.setData(xmlCalendar);
        faturaService.setIva(fatura.getIva());
        faturaService.setNifCliente(fatura.getCliente().getNif());
        faturaService.setNifEmissor(fatura.getEmissor().getNif());
        faturaService.setNomeEmissor(fatura.getEmissor().getNome());
        faturaService.setNumSeqFatura(fatura.getNumSeq());
        faturaService.setNumSerie(fatura.getSerie().getNumero());
        faturaService.setTotal(fatura.getTotal());


        for (pt.registofatura.domain.ItemFatura item : fatura.getItem()) {
            faturaService.getItens().add(convertItem(item));
        }

        return faturaService;
    }


    /**
     * Convert item.
     *
     * @param item the item
     * @return the item fatura
     */
    public ItemFatura convertItem(pt.registofatura.domain.ItemFatura item) {

        if (item == null)
            return null;

        ItemFatura itemService = new ItemFatura();

        itemService.setDescricao(item.getDescricao());
        itemService.setPreco(item.getPreco());
        itemService.setQuantidade(item.getQuantidade());

        return itemService;
    }


    /* (non-Javadoc)
     * @see pt.registofatura.ws.RegistoFaturaPortType#consultarIVADevido(int, javax.xml.datatype.XMLGregorianCalendar)
     */
    @Override
    public int consultarIVADevido(int nifEmissor, XMLGregorianCalendar ano) throws EmissorInexistente_Exception {
        return consultarIVADevidoFF(nifEmissor, ano);
    }


    /**
     * Consultar iva devido ff.
     *
     * @param nifEmissor the nif emissor
     * @param ano the ano
     * @return the int
     * @throws EmissorInexistente_Exception the emissor inexistente_ exception
     */
    @Atomic
    public int consultarIVADevidoFF(int nifEmissor, XMLGregorianCalendar ano) throws EmissorInexistente_Exception {
        Financa financa = FenixFramework.getRoot();

        Entidade emissor = financa.getEntidadeByNif(nifEmissor);
        if (emissor == null) {
            throw ExceptionFactory.generateEmissorInexistenteException(nifEmissor);
        }

        List<pt.registofatura.domain.Fatura> faturasDoEmissor = financa
                .getFaturasFromEmissor(nifEmissor);

        float total = 0;
        for (pt.registofatura.domain.Fatura f : faturasDoEmissor) {
            if (f.getData().getYear() == ano.getYear()) {
                total += f.getIva();
            }
        }
        return (int) total;
    }
    
    /**
     * Gets the seq.
     *
     * @return the seq
     */
    private int getSeq() {
MessageContext messageContext = webServiceContext.getMessageContext();
        
        return (int) messageContext.get(HeaderHandler.SEQ_PROPERTY);
//        return FaultsState.getInstance().getLastSeqRequest();
    }

    /**
     * Gets the ticket.
     *
     * @return the ticket
     */
    public int getTicket() {
        MessageContext messageContext = webServiceContext.getMessageContext();
        
        return (int) messageContext.get(HeaderHandler.TICKET_PROPERTY);
//        return FaultsState.getInstance().getLastTicketRequest();
    }

    /**
     * Gets the last record.
     *
     * @return the last record
     */
    private Record getLastRecord() {
        History history = History.getInstance();
        return history.getLastRecord(getTicket());
    }

    /**
     * Checks if is dup request.
     *
     * @return true, if is dup request
     */
    public boolean isDupRequest() {
        MessageContext messageContext = webServiceContext.getMessageContext();
        
        return (boolean) messageContext.get(HeaderHandler.DUP_PROPERTY);
//        return FaultsState.getInstance().isDuplicated();
    }
    
}
