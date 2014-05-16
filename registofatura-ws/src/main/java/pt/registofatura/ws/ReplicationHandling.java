package pt.registofatura.ws;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import java.util.Map;

import javax.xml.registry.JAXRException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;

import pt.faults.ws.FaultsPortType;
import pt.faults.ws.FaultsService;
import pt.registofatura.ws.impl.FaultsImpl;
import pt.registofatura.ws.impl.RegistoFaturaImpl;
import example.ws.cli.UDDINaming;

/**
 * The Class ReplicationHandling.
 */
public class ReplicationHandling {
    
    /**
     * The Constant logger.
     */
    private static final Logger logger = Logger.getLogger(ReplicationHandling.class);
    
    /**
     * The rf endpoint.
     */
    private static Endpoint rfEndpoint;
    
    /**
     * The endpoint.
     */
    private static Endpoint fEndpoint;

    /**
     * Won't let class get instanciated.
     */
    private ReplicationHandling() {
    }

    /**
     * Checks if is primary active.
     *
     * @return true, if is primary active
     */
    public static boolean isPrimaryActive() {
        logger.info("Checking if primary server is active...");
        FaultsState faultsState = FaultsState.getInstance();
        String endpointAddress = uddiLookup(faultsState.getUddiUrl(), faultsState.getName());
        try {
            // Must query endpoint to see if it's up.
            RegistoFaturaService service = new RegistoFaturaService();
            RegistoFaturaPortType port = service.getRegistoFaturaPort();
            BindingProvider bindingProvider = (BindingProvider) port;
            Map<String, Object> requestContext = bindingProvider.getRequestContext();
            requestContext.put(ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
            port.listarFacturas(0, 0);
        } catch (ClienteInexistente_Exception | EmissorInexistente_Exception e) {
            // Server is active
        } catch (Exception e1) {
            logger.info("Primary server not active.");
            return false;
        }
        logger.info("Primary server active.");
        return true;
    }

    /**
     * Start as primary.
     */
    public static void startAsPrimary() {
        logger.info("Starting as primary...");
        FaultsState faultsState = FaultsState.getInstance();
        faultsState.setN(0);
        faultsState.setFather(-1);
        faultsState.setGrandFather(-1);
        faultsState.setLast(0);
        faultsState.setSon(1);
        startFaults(0);
        rebindRF();
        faultsState.getFaultsThread().execute(new Broadcaster());
    }

    /**
     * Start as secundary.
     *
     * @param serverNumber the server number
     */
    public static void startAsSecundary(int serverNumber) {
        logger.info("Starting as secundary...");
        FaultsState faultsState = FaultsState.getInstance();
        faultsState.setN(serverNumber);
        faultsState.setFather(serverNumber - 1);
        //faultsState.setGrandFather(serverNumber - 2);
        startFaults(serverNumber);
        faultsState.getFaultsThread().execute(new Broadcaster());
        notifyFather(serverNumber - 1);
    }

    /**
     * Replace dead server.
     */
    public static void replaceDeadServer() {
        logger.info("Replacing dead server...");
        FaultsState faultsState = FaultsState.getInstance();
        int grandfather = faultsState.getGrandFather();
        if (grandfather == -1) {
            logger.info("Switching to primary.");
            //faultsState.setN(0);
            faultsState.setFather(-1);
            faultsState.setGrandFather(-1);
            rebindRF();
        } else {
            logger.info("Switching up the chain...");
            //faultsState.setN(grandfather + 1);
            faultsState.setFather(grandfather);
            notifyFather(grandfather);
        }
    }

    /**
     * Notify father.
     *
     * @param father the father
     */
    private static void notifyFather(int father) {
        logger.info("Notifying father I'm his son...");
        FaultsState faultsState = FaultsState.getInstance();
        String uddiURL = faultsState.getUddiUrl();
        String endpointAddress = uddiLookup(uddiURL, "faults" + father);

        logger.info("Creating faults" + father + " stub...");
        FaultsService service = new FaultsService();
        FaultsPortType port = service.getFaultsPort();

        logger.info("Binding to faults" + father + " endpoint...");
        BindingProvider bindingProvider = (BindingProvider) port;
        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(ENDPOINT_ADDRESS_PROPERTY, endpointAddress);

        try {
            logger.info("Connecting to father");
            port.connectTo(faultsState.getN());
            faultsState.getFaultsThread().execute(new ScheduleChangeToPrincipal());
        } catch (Exception e) {
            logger.fatal("Unable to call remote method, is the endpoint working?", e);
            System.exit(1);
        }
    }

    /**
     * Gets the next server number.
     *
     * @return the next server number
     */
    public static int getNextServerNumber() {
        logger.info("Getting next server number from primary...");
        FaultsState faults = FaultsState.getInstance();
        String uddiURL = faults.getUddiUrl();
        String endpointAddress = uddiLookup(uddiURL, "faults0");

        logger.info("Creating faults0 stub...");
        FaultsService service = new FaultsService();
        FaultsPortType port = service.getFaultsPort();

        logger.info("Binding to faults0 endpoint...");
        BindingProvider bindingProvider = (BindingProvider) port;
        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(ENDPOINT_ADDRESS_PROPERTY, endpointAddress);

        try {
            return port.getLastServer(true) + 1;
        } catch (Exception e) {
            logger.fatal("Unable to call remote method, is the endpoint working?", e);
            System.exit(1);
        }
        return -1;
    }

    /**
     * Rebind rf.
     */
    public static void rebindRF() {
        logger.info("Rebinding RegistoFatura WS...");
        FaultsState faults = FaultsState.getInstance();
        String url = faults.getRFurl();

        try {
            logger.info("Publishing RegistoFatura to UDDI @" + url);
            UDDINaming uddiNaming = new UDDINaming(url);
            uddiNaming.rebind(faults.getName(), url);
        } catch (JAXRException e) {
            logger.fatal("Unable to bind RegistoFatura", e);
            System.exit(1);
        }

        logger.info("Publishing RegistoFatura endpoint @" + url);
        rfEndpoint = Endpoint.create(new RegistoFaturaImpl());
        rfEndpoint.publish(url);
    }

    /**
     * Start faults.
     *
     * @param faultN the fault n
     */
    private static void startFaults(int faultN) {
        logger.info("Starting faults WS...");

        FaultsState faults = FaultsState.getInstance();
        String url = faults.getUrl();

        logger.info("Publishing faults" + faultN + " to UDDI @" + url);
        try {
            UDDINaming uddiNaming = new UDDINaming(faults.getUddiUrl());
            uddiNaming.rebind("faults" + faultN, url);
        } catch (JAXRException e) {
            logger.fatal("Unable to bind fault" + faultN, e);
            System.exit(1);
        }

        logger.info("Publishing faults" + faultN + " endpoint @" + url);
        fEndpoint = Endpoint.create(new FaultsImpl());
        fEndpoint.publish(url);
    }

    /**
     * Stop rf endpoint.
     */
    public static void stopRFEndpoint() {
        logger.info("Stopping RegistoFatura endpoint...");
        if (rfEndpoint != null) {
            rfEndpoint.stop();
        }
    }

    /**
     * Stop f endpoint.
     */
    public static void stopFEndpoint() {
        logger.info("Stopping faults endpoint...");
        if (fEndpoint != null) {
            fEndpoint.stop();
        }
    }

    /**
     * Connect to father.
     *
     * @return the faults port type
     */
    public static FaultsPortType connectToFather() {
        FaultsState faults = FaultsState.getInstance();
        String uddiURL = faults.getUddiUrl();
        int father = faults.getFather();
        String endpointAddress = uddiLookup(uddiURL, "faults" + father);

        logger.info("Creating faults" + father + " stub...");
        FaultsService service = new FaultsService();
        FaultsPortType port = service.getFaultsPort();

        logger.info("Binding to faults" + father + " endpoint...");
        BindingProvider bindingProvider = (BindingProvider) port;
        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
        return port;
    }

    /**
     * Lookup the giving name on UDDI. Will exit if UDDI is not running
     * 
     * @param uddiURL the uddi url
     * @param name the name
     * @return Endpoint address or null if not found
     */
    public static String uddiLookup(String uddiURL, String name) {
        logger.info("Asking UDDI (" + uddiURL + ") for " + name);
        String endpointAddress = null;
        try {
            UDDINaming uddiNaming = new UDDINaming(uddiURL);
            endpointAddress = uddiNaming.lookup(name);
        } catch (JAXRException e) {
            logger.fatal("Error on lookup, is UDDI running?"/*, e*/);
            System.exit(-1);
        }
        return endpointAddress;
    }
}
