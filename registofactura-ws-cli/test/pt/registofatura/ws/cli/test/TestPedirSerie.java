package pt.registofatura.ws.cli.test;

public class TestPedirSerie extends RegistoFaturaTest {

    /**
     * Instantiates a new pedir serie test.
     */
    public TestPedirSerie() {
        super();
    }

    /**
     * Instantiates a new pedir serie test.
     *
     * @param msg the msg
     */
    public TestPedirSerie(String msg) {
        super(msg);
    }
    
    public void testPedirUmaSerieParaEmissorValido() {
        int numSerie = 0;

        System.out.println("-----------------------------------------");
        System.out.println("testPedirUmaSerieParaEmissorValido");
        try {
            numSerie = port.pedirSerie(5111).getNumSerie();
            assertTrue(true);
            System.out.println("Pedir serie bem sucedida, serie: " + numSerie);
        } catch (Exception e) {
            System.out.println("pedir serie bem sucedida nao devia ter dado excepcao: " + e.getMessage());
            fail();
        }
        System.out.println("-----------------------------------------");
    }
    
    public void testPedirUmaSerieParaEmissorNaoValido() {
        int numSerie = 0;

        System.out.println("-----------------------------------------");
        System.out.println("testPedirUmaSerieParaEmissorNaoValido");
        try {
            numSerie = port.pedirSerie(9999).getNumSerie();
            System.out.println("Devia ter lancado excepcao mas retornou uma serie: " + numSerie);
            fail();
        } catch (Exception e) {
            System.out.println("Pedir serie a uma entidade nao existente (9999) mensagem: " + e.getMessage());
            assertTrue(true);
        }
        System.out.println("-----------------------------------------");
    }
}
