package pt.registofatura.ws.cli.test.tolerancia;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.registofatura.ws.EmissorInexistente_Exception;
import pt.registofatura.ws.RegistoFaturaPortType;
import pt.registofatura.ws.cli.FrontEnd;
import pt.registofatura.ws.cli.handler.HeaderHandler;
import simplemysql.QueryBuilder;
import simplemysql.SimpleMySQL;
import simplemysql.SimpleMySQLResult;

public class TestPedirSerie {
    String uddiURL = System.getProperty("uddiURL");
    String name = System.getProperty("name");
    RegistoFaturaPortType port = new FrontEnd(name, uddiURL);

    public static final String MySQL_HOST = "localhost:3306";
    public static final String DB_PRINCIPAL = "faturasdb";
    public static final String DB_SEC1 = "faturasdb1";
    public static final String DB_SEC2 = "faturasdb2";
    public static final String MySQL_USER = "rest";
    public static final String MySQL_USER_PASSWORD = "r3st";

    private SimpleMySQL mysql = new SimpleMySQL();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUpConnection() {
        QueryBuilder deleteSeries = new QueryBuilder();
        deleteSeries.DELETE(true).FROM("SERIE");
        
        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_PRINCIPAL);
        mysql.Query(deleteSeries.toString());
        mysql.close();
        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC1);
        mysql.Query(deleteSeries.toString());
        mysql.close();
        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC2);
        mysql.Query(deleteSeries.toString());
        mysql.close();
    }

    @Test
    public void test1() throws EmissorInexistente_Exception {
        port.pedirSerie(5111);
        port.pedirSerie(5111);
        FrontEnd.seq--;
        port.pedirSerie(5111);
        port.pedirSerie(5111);
        
        // waits for all servers to sync themselves
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        QueryBuilder query = new QueryBuilder();
        query.SELECT(false).FROM("SERIE");
        
        QueryBuilder deleteSeries = new QueryBuilder();
        deleteSeries.DELETE(true).FROM("SERIE");
        
        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_PRINCIPAL);
        SimpleMySQLResult result = mysql.Query(query.toString());
        assertEquals("Main server", 3, result.getNumRows());
        mysql.Query(deleteSeries.toString());
        mysql.close();
        
        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC1);
        result = mysql.Query(query.toString());
        assertEquals("Sec 1", 3, result.getNumRows());
        mysql.Query(deleteSeries.toString());
        mysql.close();
        
        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC2);
        result = mysql.Query(query.toString());
        assertEquals("Sec 2", 3, result.getNumRows());
        mysql.Query(deleteSeries.toString());
        mysql.close();
    }

//    @Test
//    public void test2() throws EmissorInexistente_Exception {
//        port.pedirSerie(5111);
//        port.pedirSerie(5111);
//        // diz para o servidor crashar-se
//        HeaderHandler.crash = true;
//        port.pedirSerie(5111);
//        port.pedirSerie(5111);
//        
//        // waits for all servers to sync themselves
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        QueryBuilder query = new QueryBuilder();
//        query.SELECT(false).FROM("SERIE");
//        
//        QueryBuilder deleteSeries = new QueryBuilder();
//        deleteSeries.DELETE(true).FROM("SERIE");
//        
//        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_PRINCIPAL);
//        SimpleMySQLResult result = mysql.Query(query.toString());
//        assertEquals("Main server", 2, result.getNumRows());
//        mysql.Query(deleteSeries.toString());
//        mysql.close();
//        
//        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC1);
//        result = mysql.Query(query.toString());
//        assertEquals("Sec 1", 4, result.getNumRows());
//        mysql.Query(deleteSeries.toString());
//        mysql.close();
//        
//        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC2);
//        result = mysql.Query(query.toString());
//        assertEquals("Sec 2", 4, result.getNumRows());
//        mysql.Query(deleteSeries.toString());
//        mysql.close();
//    }

}
