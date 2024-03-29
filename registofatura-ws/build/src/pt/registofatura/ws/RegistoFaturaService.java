
package pt.registofatura.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.jws.HandlerChain;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "RegistoFaturaService", targetNamespace = "urn:pt:registofatura:ws", wsdlLocation = "file:/home/joao/workspace/registofatura-ws/build/src/registofatura.wsdl")
@HandlerChain(file = "RegistoFaturaService_handler.xml")
public class RegistoFaturaService
    extends Service
{

    private final static URL REGISTOFATURASERVICE_WSDL_LOCATION;
    private final static WebServiceException REGISTOFATURASERVICE_EXCEPTION;
    private final static QName REGISTOFATURASERVICE_QNAME = new QName("urn:pt:registofatura:ws", "RegistoFaturaService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/home/joao/workspace/registofatura-ws/build/src/registofatura.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        REGISTOFATURASERVICE_WSDL_LOCATION = url;
        REGISTOFATURASERVICE_EXCEPTION = e;
    }

    public RegistoFaturaService() {
        super(__getWsdlLocation(), REGISTOFATURASERVICE_QNAME);
    }

    public RegistoFaturaService(WebServiceFeature... features) {
        super(__getWsdlLocation(), REGISTOFATURASERVICE_QNAME, features);
    }

    public RegistoFaturaService(URL wsdlLocation) {
        super(wsdlLocation, REGISTOFATURASERVICE_QNAME);
    }

    public RegistoFaturaService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, REGISTOFATURASERVICE_QNAME, features);
    }

    public RegistoFaturaService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RegistoFaturaService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns RegistoFaturaPortType
     */
    @WebEndpoint(name = "RegistoFaturaPort")
    public RegistoFaturaPortType getRegistoFaturaPort() {
        return super.getPort(new QName("urn:pt:registofatura:ws", "RegistoFaturaPort"), RegistoFaturaPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RegistoFaturaPortType
     */
    @WebEndpoint(name = "RegistoFaturaPort")
    public RegistoFaturaPortType getRegistoFaturaPort(WebServiceFeature... features) {
        return super.getPort(new QName("urn:pt:registofatura:ws", "RegistoFaturaPort"), RegistoFaturaPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (REGISTOFATURASERVICE_EXCEPTION!= null) {
            throw REGISTOFATURASERVICE_EXCEPTION;
        }
        return REGISTOFATURASERVICE_WSDL_LOCATION;
    }

}
