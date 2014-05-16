package pt.registofatura.ws.cli.test;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;



import pt.registofatura.ws.Fatura;
import pt.registofatura.ws.ItemFatura;
import pt.registofatura.ws.RegistoFaturaPortType;
import pt.registofatura.ws.RegistoFaturaService;
import pt.registofatura.ws.Serie;

import org.junit.*;

import static org.junit.Assert.*;

public class ListarEConsultarIVATest {

    
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
   

    //Procedimento: Pede nova série, comunica fatura, depois lista-a
    //Verificação: verifica se a listagem tem a fatura
    //Nota: Assume-se que este é o único teste que emite fatura em nome de 5001
    @Test
    public void comunicaEListarUmaFatura() throws Exception {
        Serie s = port.pedirSerie(5001);
        assertNotNull(s);
        
        int numSerie = s.getNumSerie();
        XMLGregorianCalendar gregDate = XMLCalendarToDate.toXMLGregorianCalendar(new Date());
        
        Fatura f = createFaturaExemplo(gregDate, numSerie, 1, "xpto", 5001, 2222); 
        
        port.comunicarFatura(f);
    
        //lista esta fatura
        List<Fatura> l = port.listarFacturas(5001, 2222);
        assertNotNull(l);
        // expected, actual
	assertEquals(1, l.size());
	assertEquals(f.getTotal(), l.get(0).getTotal());
    }

    //Procedimento: Pede nova série, comunica lote de 3 faturas, cada uma com 1 dia de diferença,
    //depois lista-as
    //Verificação: verifica se a listagem tem as 3 faturas
    //Nota: Assume-se que este é o único teste que emite fatura em nome de 5001
    @Test
    public void comunicarEListarLoteDeFaturas() throws Exception {
        Serie s = port.pedirSerie(5003);
        assertNotNull(s);
        
        int numSerie = s.getNumSerie();

        //emite primeira fatura
        GregorianCalendar d = new GregorianCalendar();
        d.add(GregorianCalendar.DATE, 1);
		XMLGregorianCalendar gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
        Fatura f = createFaturaExemplo(gregDate, numSerie, 1, "zleep", 5003, 2222);
        port.comunicarFatura(f);
        
        d.add(GregorianCalendar.DATE, 1);
		gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
        f = createFaturaExemplo(gregDate, numSerie, 2, "zleep", 5003, 2222);
        port.comunicarFatura(f); 

        d.add(GregorianCalendar.DATE, 1);
		gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
        f = createFaturaExemplo(gregDate, numSerie, 3, "zleep", 5003, 2222);
        port.comunicarFatura(f); 

        //lista esta fatura
        List<Fatura> l = port.listarFacturas(5003, 2222);
        assertNotNull(l);
	assertEquals(3, l.size());
	assertEquals(f.getTotal(), l.get(0).getTotal());
	assertEquals(f.getTotal(), l.get(1).getTotal());
	assertEquals(f.getTotal(), l.get(2).getTotal());
    }

    //Procedimento: Pede nova série, comunica fatura, depois consulta o IVA do emissor
    //Verificação: verifica se o iva é o correcto
    //Nota: Assume-se que este é o único teste que emite fatura em nome de 5002
    @Test
    public void comunicaEConsultarIVAOK() throws Exception {
        Serie s = port.pedirSerie(5002);
        assertNotNull(s);
        
        int numSerie = s.getNumSerie();
        XMLGregorianCalendar gregDate = XMLCalendarToDate.toXMLGregorianCalendar(new Date());
        
        Fatura f = createFaturaExemplo(gregDate, numSerie, 1, "yez", 5002, 2222); 
        
        port.comunicarFatura(f);
    
        //lista esta fatura
        assertEquals(f.getIva(), port.consultarIVADevido(5002, gregDate));
    }

    
    private Fatura createFaturaExemplo(XMLGregorianCalendar date,
                                int numSerie,
                                int numSeqFatura,
                                String nomeEmissor,
                                int nifEmissor,
                                int nifCliente) {
        Fatura f = new Fatura();

        f.setData(date);
        f.setNumSeqFatura(numSeqFatura);
        f.setNumSerie(numSerie);
        f.setNifEmissor(nifEmissor);
        f.setNifCliente(nifCliente);

        ItemFatura item = new ItemFatura();
        item.setDescricao("produto1");
        item.setPreco(123);
        item.setQuantidade(1);
        f.getItens().add(item);
        
        f.setIva(23);
        f.setTotal(123);
        f.setNomeEmissor(nomeEmissor);
        
        return f;
    }
}
