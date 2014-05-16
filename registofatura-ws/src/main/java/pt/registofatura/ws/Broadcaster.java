package pt.registofatura.ws;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import pt.faults.ws.FaultsPortType;
import pt.faults.ws.FaultsService;
import pt.registofatura.ws.broadcast.ComunicarFaturaRequest;
import pt.registofatura.ws.broadcast.PedirSerieRequest;
import pt.registofatura.ws.broadcast.Request;
import pt.registofatura.ws.broadcast.TicketRequest;
import pt.registofatura.ws.handler.HeaderHandler;

/**
 * The Class Broadcaster.
 */
public class Broadcaster
        implements Runnable {
    /**
     * The Constant logger.
     */
    private static final Logger logger = Logger.getLogger(Broadcaster.class);

    private static final int MAX_TRIES = 3;
    
    private static LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<Request>();

    public static void pushPedirSerie(int nifEmissor, int ticket, int seq) {
        requests.add(new PedirSerieRequest(nifEmissor, ticket, seq));
    }
    
    public static void pushComunicarFatura(Fatura fatura, int ticket, int seq) {
        requests.add(new ComunicarFaturaRequest(fatura, ticket, seq));
    }
    
    public static void pushTicket(int ticket, int seq) {
        requests.add(new TicketRequest(ticket, seq));
    }

    /**
     * Try to get FaultsPortType for the son, returns null if there's no son.
     * 
     * @return the son's port
     */
    private static FaultsPortType getPort(int ticket, int seq) {
        logger.info("Trying to get son's FaultsPortType...");
        FaultsState faultsState = FaultsState.getInstance();
        String uddiURL = faultsState.getUddiUrl();
        String sonName = faultsState.getSonName();
        String endpointAddress = faultsState.getSonAddress();
        FaultsPortType port = null;

        if (faultsState.getSon() == -1) {
            logger.info("No active son");
            return null;
        }
        if (endpointAddress == null) {
            endpointAddress = ReplicationHandling.uddiLookup(uddiURL, sonName);
            faultsState.setSonAddress(endpointAddress);
            logger.info("Son is @" + endpointAddress);
        }
        FaultsService service = new FaultsService();
        port = service.getFaultsPort();

        BindingProvider bindingProvider = (BindingProvider) port;
        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
        requestContext.put(HeaderHandler.TICKET_PROPERTY, ticket);
        requestContext.put(HeaderHandler.SEQ_PROPERTY, seq);
        return port;
    }

    @Override
    public void run() {
        int attempts = 0;
        FaultsState faultsState = FaultsState.getInstance();
        while (true) {
            try {
                // Will wait for requests if queue empty
                Request request = requests.take();
                while (true) {
                    try {
                        FaultsPortType port = getPort(request.getTicket(), request.getSeq());                
                        request.execute(port);
                        attempts = 0;
                        break;
                    } catch (Exception e) {
                        logger.info("Son not active, attempt #" + attempts);
                        faultsState.setSonAddress(null);
                        Thread.sleep(1000);
                        if (attempts++ == MAX_TRIES) {
                            logger.info("No son active, stopping broadcaster.");
                            return;
                        }
                    }
                }
            } catch (InterruptedException e) {
                logger.warn("Broadcaster terminated while waiting for requests");
            }
        }
    }
}