package pt.registofatura.ws.cli.test;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import pt.registofatura.ws.EmissorInexistente_Exception;
import pt.registofatura.ws.Fatura;
import pt.registofatura.ws.FaturaInvalida_Exception;
import pt.registofatura.ws.ItemFatura;
import pt.registofatura.ws.RegistoFaturaPortType;
import pt.registofatura.ws.RegistoFaturaService;
import pt.registofatura.ws.Serie;
import pt.registofatura.ws.TotaisIncoerentes_Exception;

import org.junit.*;

import static org.junit.Assert.*;

public class ComunicarFaturaTest {

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

    //Procedimento: Cria série, depois comunica fatura com total incoerente (total=10000,
    //total efectivo=123)
    //Verificação: deve lançar excepção TotaisIncoerentes_Exception
    @Test(expected=TotaisIncoerentes_Exception.class)
    public void comunicarTotalIncoerente() throws Exception {
        Serie s = port.pedirSerie(1111);
        assertNotNull(s);

        int numSerie = s.getNumSerie();
        XMLGregorianCalendar gregDate = XMLCalendarToDate.toXMLGregorianCalendar(new Date());

        Fatura f = createFaturaExemplo(gregDate, numSerie, 1, "zeze", 1111, 2222);
        f.setTotal(10000);

        port.comunicarFatura(f);
    }

    //Procedimento: Cria série, depois comunica fatura com total de iva incoerente
    //(totalIva=1000, total de iva efectivo =23)
    //Verificação: deve lançar excepção TotaisIncoerentes_Exception
    @Test(expected=TotaisIncoerentes_Exception.class)
    public void comunicarTotalIVAIncoerente() throws Exception {
        Serie s = port.pedirSerie(1111);
        assertNotNull(s);

        int numSerie = s.getNumSerie();
        XMLGregorianCalendar gregDate = XMLCalendarToDate.toXMLGregorianCalendar(new Date());

        Fatura f = createFaturaExemplo(gregDate, numSerie, 1, "zeze", 1111, 2222);
        f.setIva(1000);

        port.comunicarFatura(f);
    }


    //Procedimento: Comunica fatura sobre série 9999, que nunca foi gerada
    //Verificação: deve lançar excepção FaturaInvalida_Exception
    @Test(expected=FaturaInvalida_Exception.class)
    public void comunicarFaturaSerieInexistente() throws Exception {

        XMLGregorianCalendar gregDate = XMLCalendarToDate.toXMLGregorianCalendar(new Date());

        Fatura f = createFaturaExemplo(gregDate, 9999, 1, "zeze", 1111, 2222);
        port.comunicarFatura(f);
    }


    //Procedimento: Pede nova série do emissor 1111, depois comunica fatura com um emissor diferente
    //Verificação: deve lançar excepção FaturaInvalida_Exception
    @Test(expected=FaturaInvalida_Exception.class)
    public void comunicarEmissorErrado() throws Exception {
        Serie s = port.pedirSerie(1111);
        assertNotNull(s);

        XMLGregorianCalendar gregDate = XMLCalendarToDate.toXMLGregorianCalendar(new Date());

        Fatura f = createFaturaExemplo(gregDate, 9999, 1, "mng", 3333, 2222);

        port.comunicarFatura(f);
    }

    //Procedimento: Pede nova série do emissor, depois comunica fatura com data de 100 dias após
    //Verificação: deve lançar excepção FaturaInvalida_Exception
    @Test(expected=FaturaInvalida_Exception.class)
    public void comunicarFaturaForaDeValidade() throws Exception {
        Serie s = port.pedirSerie(1111);
        assertNotNull(s);

        int numSerie = s.getNumSerie();

        //define data para alem da validade
        GregorianCalendar d = new GregorianCalendar();
		d.add(GregorianCalendar.DATE, 100);
		XMLGregorianCalendar gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);

        Fatura f = createFaturaExemplo(gregDate, numSerie, 1, "zeze", 1111, 2222);

        port.comunicarFatura(f);
    }

    //Procedimento: Pede nova série do emissor, comunica fatura com data+2 dias
    //após, comunica outra fatura com data original da série (i.e., fora de ordem)
    //Verificação: deve lançar excepção FaturaInvalida_Exception
//    @Test(expected=FaturaInvalida_Exception.class)
//    public void comunicarFaturaForaDeOrdem() throws Exception {
//        Serie s = port.pedirSerie(1111);
//        assertNotNull(s);
//
//        int numSerie = s.getNumSerie();
//
//        //emite primeira fatura
//        GregorianCalendar d = new GregorianCalendar();
//        d.add(GregorianCalendar.DATE, 2);
//		XMLGregorianCalendar gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
//
//        Fatura f = createFaturaExemplo(gregDate, numSerie, 1, "zeze", 1111, 2222);
//        port.comunicarFatura(f);
//
//        //emite segunda fatura, com data anterior
//        d = new GregorianCalendar();
//		gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
//
//		f = createFaturaExemplo(gregDate, numSerie, 1, "zeze", 1111, 2222);
//
//        port.comunicarFatura(f);
//    }


    //Procedimento: Pede nova série, comunica fatura com emissor inexistente
    //Verificação: deve lançar excepção EmissorInexistente_Exception
    @Test(expected=EmissorInexistente_Exception.class)
    public void comunicarFaturaEmissorInexistente() throws Exception {
        Serie s = port.pedirSerie(1111);
        assertNotNull(s);

        int numSerie = s.getNumSerie();

        //define data para alem da validade
        GregorianCalendar d = new GregorianCalendar();
		XMLGregorianCalendar gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);

        Fatura f = createFaturaExemplo(gregDate, numSerie, 1, "xpto", 123456, 2222);

        port.comunicarFatura(f);
    }


    //Procedimento: Pede nova série, comunica lote de 5 faturas, cada uma com 1 dia de diferença
    //Verificação: deve lançar excepção FaturaInvalida_Exception
    @Test(expected=FaturaInvalida_Exception.class)
    public void comunicarDemasiadasFaturas() throws Exception {
        Serie s = port.pedirSerie(1111);
        assertNotNull(s);

        int numSerie = s.getNumSerie();

        //emite primeira fatura
        GregorianCalendar d = new GregorianCalendar();
        d.add(GregorianCalendar.DATE, 1);
		XMLGregorianCalendar gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
        Fatura f = createFaturaExemplo(gregDate, numSerie, 1, "zeze", 1111, 2222);
        port.comunicarFatura(f);

        d.add(GregorianCalendar.DATE, 1);
		gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
        f = createFaturaExemplo(gregDate, numSerie, 2, "zeze", 1111, 2222);
        port.comunicarFatura(f);

        d.add(GregorianCalendar.DATE, 1);
		gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
        f = createFaturaExemplo(gregDate, numSerie, 3, "zeze", 1111, 2222);
        port.comunicarFatura(f);

        d.add(GregorianCalendar.DATE, 1);
		gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
        f = createFaturaExemplo(gregDate, numSerie, 4, "zeze", 1111, 2222);
        port.comunicarFatura(f);

        d.add(GregorianCalendar.DATE, 1);
		gregDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(d);
        f = createFaturaExemplo(gregDate, numSerie, 5, "zeze", 1111, 2222);
        port.comunicarFatura(f);

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
