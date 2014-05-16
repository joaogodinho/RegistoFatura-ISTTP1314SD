package pt.registofatura.ws.cli.test;

import javax.xml.registry.JAXRException;

import pt.registofatura.ws.RegistoFaturaPortType;
import pt.registofatura.ws.cli.Utility;
import junit.framework.TestCase;

public class RegistoFaturaTest extends TestCase {

    protected RegistoFaturaPortType port = null;
    
    /**
     * Instantiates a new registo fatura test.
     *
     * @param msg the msg
     */
    protected RegistoFaturaTest(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new registo fatura test.
     */
    protected RegistoFaturaTest() {
        super();
    }
    
    @Override
    protected void setUp() throws JAXRException {
        if(port == null) {
            String uddiURL = System.getProperty("uddiURL", ""); //http://localhost:8080
            String name = System.getProperty("name", "registofatura");
            System.out.println("uddiURL: " + uddiURL);
            System.out.println("ws name: " + name);
            port = Utility.initJuddi(uddiURL, name);
        }
    }
}
