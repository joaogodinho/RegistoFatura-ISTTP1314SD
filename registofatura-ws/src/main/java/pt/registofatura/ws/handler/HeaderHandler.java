package pt.registofatura.ws.handler;

import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

import pt.registofatura.ws.FaultsState;

/**
 * This SOAPHandler shows how to set/get values from headers in inbound/outbound
 * SOAP messages. A header is created in an outbound message and is read on an
 * inbound message.
 */
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {
    private static final Logger logger = Logger.getLogger(HeaderHandler.class);
    public static final String DUP_PROPERTY = "duplicated.property";
    public static final String TICKET_PROPERTY = "ticket.property";
    public static final String SEQ_PROPERTY = "seq.property";
    
    //
    // Handler interface methods
    //
    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.ws.handler.soap.SOAPHandler#getHeaders()
     */
    public Set<QName> getHeaders() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.ws.handler.Handler#handleMessage(javax.xml.ws.handler.
     * MessageContext)
     */
    public boolean handleMessage(SOAPMessageContext smc) {
        Boolean outboundElement = (Boolean) smc
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        

        try {// servidor
            // get SOAP envelope
            SOAPMessage msg = smc.getMessage();
            SOAPPart sp = msg.getSOAPPart();
            SOAPEnvelope se = sp.getEnvelope();
            SOAPBody sb = se.getBody();

            Iterator<?> it = sb.getChildElements();
            if (it.hasNext()) {
                SOAPElement el1 = (SOAPElement) it.next();
                String name = el1.getLocalName().toString();
                if (!name.equals("pedirSerie") && !name.equals("comunicarFatura")) {
                    return true;
                }
            }
            
            if (!outboundElement.booleanValue()) {// entrada
                logger.info("Handling inbound message");
                

                // add header
                SOAPHeader sh = se.getHeader();
                if (sh == null) {
                    return true;
                }
                    
                
                Name ticketName = se.createName("ticket", "tf", "http://demo");
                it = sh.getChildElements(ticketName);

                // check header element
                if (!it.hasNext()) {
                    return true;
                }

                SOAPElement element = (SOAPElement) it.next();
                int ticket = DatatypeConverter.parseInt(element.getValue());
                
                logger.info("ticket: " + ticket);
                
                Name seqName = se.createName("seq", "tf", "http://demo");
                it = sh.getChildElements(seqName);
                
                // check header element
                if (!it.hasNext()) {
                    logger.info("Header element not found.");
                    return true;
                }
                
                element = (SOAPElement) it.next();
                int seq = DatatypeConverter.parseInt(element.getValue());
                
                logger.info("seq: " + seq);
                
                Name crashName = se.createName("crash", "tf", "http://demo");
                it = sh.getChildElements(crashName);

                // check header element
                if (it.hasNext()) {
                    logger.info("crash order received");
                    FaultsState.getInstance().crashOrder = true;
                }
                
                History history = History.getInstance();
                Record record = history.validateMessage(ticket, seq);
                smc.put(TICKET_PROPERTY, ticket);
                smc.put(SEQ_PROPERTY, seq);
                smc.setScope(TICKET_PROPERTY, Scope.APPLICATION);
                smc.setScope(SEQ_PROPERTY, Scope.APPLICATION);
                
                if (record != null) {
                    logger.info("Setting DUP_PROPERTY = TRUE.");
                    smc.put(DUP_PROPERTY, true);
                    smc.setScope(DUP_PROPERTY, Scope.APPLICATION);
                } else {
                    logger.info("Setting DUP_PROPERTY = FALSE.");
                    smc.put(DUP_PROPERTY, false);
                    smc.setScope(DUP_PROPERTY, Scope.APPLICATION);
                }
                
                return true;
            } else {// saida
                logger.info("Handling outbound message");
                
             // add header
                SOAPHeader sh = se.getHeader();
                if (sh == null)
                    sh = se.addHeader();

                // add header element (name, namespace prefix, namespace)
                Name ticket = se.createName("ticket", "tf", "http://demo");
                SOAPHeaderElement ticketElement = sh.addHeaderElement(ticket);

                Name seq = se.createName("seq", "tf", "http://demo");
                SOAPHeaderElement seqElement = sh.addHeaderElement(seq);
                
                int lastTickettmp = (int) smc.get(TICKET_PROPERTY);
                ticketElement.addTextNode(String.valueOf(lastTickettmp));
                int lastSeqtmp = (int) smc.get(SEQ_PROPERTY);
                seqElement.addTextNode(String.valueOf(lastSeqtmp));
                
            }
        } catch (Exception e) {
            logger.warn("Caught exception handling message", e);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.xml.ws.handler.Handler#handleFault(javax.xml.ws.handler.MessageContext
     * )
     */
    public boolean handleFault(SOAPMessageContext smc) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.xml.ws.handler.Handler#close(javax.xml.ws.handler.MessageContext)
     */
    public void close(MessageContext messageContext) {
    }

}