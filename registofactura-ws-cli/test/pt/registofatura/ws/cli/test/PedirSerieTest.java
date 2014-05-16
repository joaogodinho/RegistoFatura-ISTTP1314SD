package pt.registofatura.ws.cli.test;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.*;

import static org.junit.Assert.*;
import pt.registofatura.ws.EmissorInexistente_Exception;
import pt.registofatura.ws.RegistoFaturaPortType;
import pt.registofatura.ws.RegistoFaturaService;
import pt.registofatura.ws.Serie;


public class PedirSerieTest {

    private static final int NIF_MARIA = 2222;
   
    private static RegistoFaturaPortType port;

    // one-time initialization and clean-up

    @BeforeClass
    public static void oneTimeSetUp() {
        RegistoFaturaService service = new RegistoFaturaService();
        port = service.getRegistoFaturaPort();
    }

    @AfterClass
    public static void oneTimeTearDown() {
        port = null;
    }

    // initialization and clean-up for each test

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    //Procedimento: Pede nova série
    //Verificação: se data de validade da nova série é a esperada
    @Test
    public void pedirSerieEmissorCorrecto() throws Exception {
    	
        Serie s = port.pedirSerie(NIF_MARIA);
        
		GregorianCalendar d = new GregorianCalendar();
		d.add(GregorianCalendar.DATE, 10);
		XMLGregorianCalendar c = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
        assertEquals(s.getValidoAte().getDay(), c.getDay());
        assertEquals(s.getValidoAte().getMonth(), c.getMonth());
        assertEquals(s.getValidoAte().getYear(), c.getYear());
    }

    //Procedimento: Pede nova série para emissor inexistente
    //Verificação: deve lançar excepção emissorInexistente
    @Test(expected=EmissorInexistente_Exception.class)
    public void pedirSerieEmissorInexistente() throws Exception {
        port.pedirSerie(999999);
    }
}
