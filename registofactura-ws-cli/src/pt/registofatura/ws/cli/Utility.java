package pt.registofatura.ws.cli;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.registry.JAXRException;
import javax.xml.ws.BindingProvider;

import example.ws.cli.UDDINaming;
import pt.registofatura.ws.Fatura;
import pt.registofatura.ws.ItemFatura;
import pt.registofatura.ws.RegistoFaturaPortType;
import pt.registofatura.ws.RegistoFaturaService;

public class Utility {

    private static final float PERCENTAGEM_IVA = 0.23f;
    
    /**
     * Todays date.
     *
     * @return the XML gregorian calendar
     */
    public static XMLGregorianCalendar todaysDate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        try {
            return DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException e) {
            System.out.println("Error: todaysDate()");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds the to date. You may use negative values to subtract.
     * 
     * @param date
     *            the date
     * @param years
     *            the years
     * @param months
     *            the months
     * @param days
     *            the days
     * @return the XML gregorian calendar
     */
    public static XMLGregorianCalendar addToDate(XMLGregorianCalendar date,
            int years, int months, int days) {
        GregorianCalendar gc = new GregorianCalendar(date.getYear(),
                date.getMonth(), date.getDay(), date.getHour(),
                date.getMinute(), date.getSecond());
        gc.add(GregorianCalendar.YEAR, years);
        gc.add(GregorianCalendar.MONTH, months);
        gc.add(GregorianCalendar.DAY_OF_MONTH, days);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException e) {
            System.out.println("Error: addToDate(...)");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the fatura.
     *
     * @param date the date
     * @param numSerie the num serie
     * @param numSeq the num seq
     * @param nifEmissor the nif emissor
     * @param nomeEmissor the nome emissor
     * @param nifClient the nif client
     * @param items the items
     * @return the fatura
     */
    public static Fatura createFatura(XMLGregorianCalendar date, int numSerie,
            int numSeq, int nifEmissor, String nomeEmissor, int nifClient,
            ItemFatura... items) {
        Fatura fatura = new Fatura();
        fatura.setData(date);
        fatura.setNifEmissor(nifEmissor);
        fatura.setNomeEmissor(nomeEmissor);
        fatura.setNumSerie(numSerie);
        fatura.setNumSeqFatura(numSeq);
        fatura.setNifCliente(nifClient);
        
        int total = 0;
        float iva = 0;
        
        for (ItemFatura item : items) {
            total += item.getPreco();
            iva += (float) item.getPreco() * PERCENTAGEM_IVA / (1f + PERCENTAGEM_IVA);
            fatura.getItens().add(item);
        }
        
        fatura.setIva(Math.round(iva));
        fatura.setTotal(total);
        
        return fatura;
    }

    /**
     * Creates the item.
     *
     * @param descricao the descicao
     * @param preco the preco
     * @param quantidade the quantidade
     * @return the item fatura
     */
    public static ItemFatura createItem(String descricao, int preco,
            int quantidade) {
        ItemFatura item = new ItemFatura();
        item.setDescricao(descricao);
        item.setPreco(preco);
        item.setQuantidade(quantidade);
        return item;
    }

    /**
     * Creates the item.
     *
     * @return the item fatura
     */
    public static ItemFatura createItem() {
        return createItem(String.valueOf(Math.random()), (int) Math.random() * 100,
                (int) Math.random() * 100);
    }

    public static RegistoFaturaPortType initJuddi(String uddiURL, String name)
            throws JAXRException {
        System.out.printf("Contacting UDDI at %s%n", uddiURL);
        UDDINaming uddiNaming = new UDDINaming(uddiURL);
    
        System.out.printf("Looking for '%s'%n", name);
        String endpointAddress = uddiNaming.lookup(name);
    
        if (endpointAddress == null) {
            System.out.println("Not found!");
            System.exit(-1);
        } else {
            System.out.printf("Found %s%n", endpointAddress);
        }
    
        System.out.println("Creating stub ...");
        RegistoFaturaService service = new RegistoFaturaService();
        RegistoFaturaPortType port = service.getRegistoFaturaPort();
    
        System.out.println("Setting endpoint address ...");
        BindingProvider bindingProvider = (BindingProvider) port;
        Map<String, Object> requestContext = bindingProvider
                .getRequestContext();
        requestContext.put(ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
        return port;
    }

}
