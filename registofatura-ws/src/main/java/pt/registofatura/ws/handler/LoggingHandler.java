package pt.registofatura.ws.handler;

import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

/**
 * This SOAPHandler outputs the contents of inbound and outbound messages.
 */
public class LoggingHandler
        implements SOAPHandler<SOAPMessageContext> {
    public static final Logger logger = Logger.getLogger(LoggingHandler.class);
    
    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
        logToSystemOut(smc);
        return true;
    }

    public boolean handleFault(SOAPMessageContext smc) {
        logToSystemOut(smc);
        return true;
    }

    // nothing to clean up
    public void close(MessageContext messageContext) {
        return;
    }

    /**
     * Check the MESSAGE_OUTBOUND_PROPERTY in the context to see if this is an outgoing or
     * incoming message. Write a brief message to the print stream and output the message.
     * The writeTo() method can throw SOAPException or IOException
     */
    private void logToSystemOut(SOAPMessageContext smc) {
        Boolean outbound = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        SOAPMessage message = smc.getMessage();
        if (outbound) {
            logger.info("Outbound SOAP message:");
        } else {
            logger.info("Inbound SOAP message:");
        }
        try {
            message.writeTo(System.out);
            System.out.println();
            System.out.println("-------------------------");
        } catch (SOAPException | IOException e) {
            e.printStackTrace();
        }
    }

}
