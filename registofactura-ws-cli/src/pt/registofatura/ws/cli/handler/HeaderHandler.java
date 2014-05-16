package pt.registofatura.ws.cli.handler;

import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import pt.registofatura.ws.cli.FrontEnd;

// TODO: Auto-generated Javadoc
/**
 * This SOAPHandler shows how to set/get values from headers in inbound/outbound
 * SOAP messages. A header is created in an outbound message and is read on an
 * inbound message.
 */
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

    public static boolean crash = false;

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
//        System.out.println("AddHeaderHandler: Handling message.");

        Boolean outboundElement = (Boolean) smc
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        try {// servidor
         // get SOAP envelope
            SOAPMessage msg = smc.getMessage();
            SOAPPart sp = msg.getSOAPPart();
            SOAPEnvelope se = sp.getEnvelope();
            if (outboundElement.booleanValue()) {// saida
//                System.out.println("Writing header in outbound"
//                        + "SOAP message...");

//                System.out.println("inicio");
//                msg.writeTo(System.out);
//                System.out.println();

                // add header
                SOAPHeader sh = se.getHeader();
                if (sh == null)
                    sh = se.addHeader();

                // add header element (name, namespace prefix, namespace)
                Name ticket = se.createName("ticket", "tf", "http://demo");
                SOAPHeaderElement ticketElement = sh.addHeaderElement(ticket);

                Name seq = se.createName("seq", "tf", "http://demo");
                SOAPHeaderElement seqElement = sh.addHeaderElement(seq);

                if (crash) {
                    Name crash = se.createName("crash", "tf", "http://demo");
                    SOAPHeaderElement crashElement = sh.addHeaderElement(crash);
                    crashElement.addTextNode("true");
                    HeaderHandler.crash = false;
                }
                
                if (FrontEnd.repeticaoFeita) {
                    Name rep = se.createName("repDone", "tf", "http://demo");
                    SOAPHeaderElement crashElement = sh.addHeaderElement(rep);
                    crashElement.addTextNode("true");
                    FrontEnd.repeticaoFeita = false;
                }

                String ticketValue = "" + FrontEnd.ticket;
                ticketElement.addTextNode(ticketValue);
                String seqValue = "" + FrontEnd.seq;
                seqElement.addTextNode(seqValue);

                // System.out.println("fim");
                // msg.writeTo(System.out);
                // System.out.println();

            } else {// entrada
                Name repOrder = se.createName("repPlease", "tf", "http://demo");
                SOAPHeader sh = se.getHeader();
                if(sh == null) {
                    return true;
                }
                Iterator<?> it = sh.getChildElements(repOrder);

                // check header element
                if (it.hasNext()) {
                    FrontEnd.repetirPedidos = true;
                    SOAPElement element = (SOAPElement) it.next();
                    FrontEnd.repetirPedidosLastSeq = DatatypeConverter.parseInt(element.getValue());
                }
            }
        } catch (Exception e) {
            System.out.printf("Caught exception in handleMessage: %s%n", e);
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
//        System.out.println("close");
    }

}