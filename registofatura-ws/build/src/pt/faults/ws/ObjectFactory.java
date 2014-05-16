
package pt.faults.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the pt.faults.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ImAlive_QNAME = new QName("urn:pt:faults:ws", "imAlive");
    private final static QName _Ticket_QNAME = new QName("urn:pt:faults:ws", "ticket");
    private final static QName _ImAliveResponse_QNAME = new QName("urn:pt:faults:ws", "imAliveResponse");
    private final static QName _TicketResponse_QNAME = new QName("urn:pt:faults:ws", "ticketResponse");
    private final static QName _ConnectTo_QNAME = new QName("urn:pt:faults:ws", "connectTo");
    private final static QName _GetLastServerResponse_QNAME = new QName("urn:pt:faults:ws", "getLastServerResponse");
    private final static QName _GetLastServer_QNAME = new QName("urn:pt:faults:ws", "getLastServer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pt.faults.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ImAlive }
     * 
     */
    public ImAlive createImAlive() {
        return new ImAlive();
    }

    /**
     * Create an instance of {@link Ticket }
     * 
     */
    public Ticket createTicket() {
        return new Ticket();
    }

    /**
     * Create an instance of {@link GetLastServerResponse }
     * 
     */
    public GetLastServerResponse createGetLastServerResponse() {
        return new GetLastServerResponse();
    }

    /**
     * Create an instance of {@link GetLastServer }
     * 
     */
    public GetLastServer createGetLastServer() {
        return new GetLastServer();
    }

    /**
     * Create an instance of {@link TicketResponse }
     * 
     */
    public TicketResponse createTicketResponse() {
        return new TicketResponse();
    }

    /**
     * Create an instance of {@link ImAliveResponse }
     * 
     */
    public ImAliveResponse createImAliveResponse() {
        return new ImAliveResponse();
    }

    /**
     * Create an instance of {@link ConnectTo }
     * 
     */
    public ConnectTo createConnectTo() {
        return new ConnectTo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImAlive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:faults:ws", name = "imAlive")
    public JAXBElement<ImAlive> createImAlive(ImAlive value) {
        return new JAXBElement<ImAlive>(_ImAlive_QNAME, ImAlive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ticket }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:faults:ws", name = "ticket")
    public JAXBElement<Ticket> createTicket(Ticket value) {
        return new JAXBElement<Ticket>(_Ticket_QNAME, Ticket.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImAliveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:faults:ws", name = "imAliveResponse")
    public JAXBElement<ImAliveResponse> createImAliveResponse(ImAliveResponse value) {
        return new JAXBElement<ImAliveResponse>(_ImAliveResponse_QNAME, ImAliveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TicketResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:faults:ws", name = "ticketResponse")
    public JAXBElement<TicketResponse> createTicketResponse(TicketResponse value) {
        return new JAXBElement<TicketResponse>(_TicketResponse_QNAME, TicketResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectTo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:faults:ws", name = "connectTo")
    public JAXBElement<ConnectTo> createConnectTo(ConnectTo value) {
        return new JAXBElement<ConnectTo>(_ConnectTo_QNAME, ConnectTo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastServerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:faults:ws", name = "getLastServerResponse")
    public JAXBElement<GetLastServerResponse> createGetLastServerResponse(GetLastServerResponse value) {
        return new JAXBElement<GetLastServerResponse>(_GetLastServerResponse_QNAME, GetLastServerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastServer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:pt:faults:ws", name = "getLastServer")
    public JAXBElement<GetLastServer> createGetLastServer(GetLastServer value) {
        return new JAXBElement<GetLastServer>(_GetLastServer_QNAME, GetLastServer.class, null, value);
    }

}
