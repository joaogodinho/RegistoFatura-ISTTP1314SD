package pt.registofatura.ws.cli.test.tolerancia;

import static org.junit.Assert.*;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.registofatura.ws.ClienteInexistente_Exception;
import pt.registofatura.ws.EmissorInexistente_Exception;
import pt.registofatura.ws.FaturaInvalida_Exception;
import pt.registofatura.ws.RegistoFaturaPortType;
import pt.registofatura.ws.Serie;
import pt.registofatura.ws.TotaisIncoerentes_Exception;
import pt.registofatura.ws.cli.FrontEnd;
import pt.registofatura.ws.cli.Utility;
import pt.registofatura.ws.cli.handler.HeaderHandler;
import simplemysql.QueryBuilder;
import simplemysql.SimpleMySQL;
import simplemysql.SimpleMySQLResult;

public class TestComunicarFatura {
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
        QueryBuilder deleteFaturas = new QueryBuilder();
        deleteFaturas.DELETE(true).FROM("FATURA");
        QueryBuilder deleteItemFatura = new QueryBuilder();
        deleteItemFatura.DELETE(true).FROM("ITEM_FATURA");

        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_PRINCIPAL);
        mysql.Query(deleteSeries.toString());
        mysql.Query(deleteFaturas.toString());
        mysql.Query(deleteItemFatura.toString());
        mysql.close();

        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC1);
        mysql.Query(deleteSeries.toString());
        mysql.Query(deleteFaturas.toString());
        mysql.Query(deleteItemFatura.toString());
        mysql.close();

        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC2);
        mysql.Query(deleteSeries.toString());
        mysql.Query(deleteFaturas.toString());
        mysql.Query(deleteItemFatura.toString());
        mysql.close();
    }

    @Test
    public void test1() {
        Serie serie = null;
        try {
            serie = port.pedirSerie(5111);
        } catch (EmissorInexistente_Exception e1) {
            System.out.println("pedir serie Excepcao inesperada: "
                    + e1.getMessage());
        }

        XMLGregorianCalendar calendar = Utility.todaysDate();

        try {
            port.comunicarFatura(Utility.createFatura(calendar,
                    serie.getNumSerie(), 1, 5111, "bc", 1234,
                    Utility.createItem("Batatas Cozidas", 4, 2)));
        } catch (EmissorInexistente_Exception | ClienteInexistente_Exception
                | FaturaInvalida_Exception | TotaisIncoerentes_Exception e) {
            System.out.println("test1 fatura1 Excepcao inesperada: "
                    + e.getMessage());
        }

        try {
            port.comunicarFatura(Utility.createFatura(calendar,
                    serie.getNumSerie(), 2, 5111, "bc", 1234,
                    Utility.createItem("Batatas Cozidas", 4, 2)));
        } catch (EmissorInexistente_Exception | ClienteInexistente_Exception
                | FaturaInvalida_Exception | TotaisIncoerentes_Exception e) {
            System.out.println("test1 fatura2 Excepcao inesperada: "
                    + e.getMessage());
        }

        FrontEnd.seq--;

        try {
            port.comunicarFatura(Utility.createFatura(calendar,
                    serie.getNumSerie(), 3, 5111, "bc", 1234,
                    Utility.createItem("Batatas Cozidas", 4, 2)));
        } catch (EmissorInexistente_Exception | ClienteInexistente_Exception
                | FaturaInvalida_Exception | TotaisIncoerentes_Exception e) {
            System.out.println("test1 fatura3 Excepcao inesperada: "
                    + e.getMessage());
        }

        try {
            port.comunicarFatura(Utility.createFatura(calendar,
                    serie.getNumSerie(), 4, 5111, "bc", 1234,
                    Utility.createItem("Batatas Cozidas", 4, 2)));
        } catch (EmissorInexistente_Exception | ClienteInexistente_Exception
                | FaturaInvalida_Exception | TotaisIncoerentes_Exception e) {
            System.out.println("test1 fatura4 Excepcao inesperada: "
                    + e.getMessage());
        }

        // System.out.println("Foi pedida 1 serie e comunicadas 4 faturas,"
        // + " sendo a terceira duplicada");
        // System.out.println("Verificar que o primario tem 2 faturas");
        // System.out.println("----------------------------------");
        // System.out.println();

        // waits for all servers to sync themselves
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        QueryBuilder query = new QueryBuilder();
        query.SELECT(false).FROM("FATURA");

        QueryBuilder deleteSeries = new QueryBuilder();
        deleteSeries.DELETE(true).FROM("SERIE");
        QueryBuilder deleteFaturas = new QueryBuilder();
        deleteFaturas.DELETE(true).FROM("FATURA");
        QueryBuilder deleteItemFatura = new QueryBuilder();
        deleteItemFatura.DELETE(true).FROM("ITEM_FATURA");

        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_PRINCIPAL);
        SimpleMySQLResult result = mysql.Query(query.toString());
        assertEquals("MainServer", 2, result.getNumRows());
        mysql.Query(deleteSeries.toString());
        mysql.Query(deleteFaturas.toString());
        mysql.Query(deleteItemFatura.toString());
        mysql.close();

        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC1);
        result = mysql.Query(query.toString());
        assertEquals("SecundaryServer1", 2, result.getNumRows());
        mysql.Query(deleteSeries.toString());
        mysql.Query(deleteFaturas.toString());
        mysql.Query(deleteItemFatura.toString());
        mysql.close();

        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC2);
        result = mysql.Query(query.toString());
        assertEquals("SecundaryServer2", 2, result.getNumRows());
        mysql.Query(deleteSeries.toString());
        mysql.Query(deleteFaturas.toString());
        mysql.Query(deleteItemFatura.toString());
        mysql.close();
    }

    @Test
    public void test2() throws EmissorInexistente_Exception,
            ClienteInexistente_Exception, FaturaInvalida_Exception,
            TotaisIncoerentes_Exception {
        Serie serie = null;
        try {
            serie = port.pedirSerie(1234);
        } catch (EmissorInexistente_Exception e1) {
            System.out.println("pedir serie Excepcao inesperada: "
                    + e1.getMessage());
        }

        XMLGregorianCalendar calendar = Utility.todaysDate();

        try {
            port.comunicarFatura(Utility.createFatura(calendar,
                    serie.getNumSerie(), 1, 1234, "portal", 5111,
                    Utility.createItem("Batatas Cozidas", 4, 2)));
        } catch (EmissorInexistente_Exception | ClienteInexistente_Exception
                | FaturaInvalida_Exception | TotaisIncoerentes_Exception e) {
            System.out.println("test2 fatura1 Excepcao inesperada: "
                    + e.getMessage());
        }

        try {
            port.comunicarFatura(Utility.createFatura(calendar,
                    serie.getNumSerie(), 2, 1234, "portal", 5111,
                    Utility.createItem("Batatas Cozidas", 4, 2)));
        } catch (EmissorInexistente_Exception | ClienteInexistente_Exception
                | FaturaInvalida_Exception | TotaisIncoerentes_Exception e) {
            System.out.println("test2 fatura2: Excepcao inesperada: "
                    + e.getMessage());
        }

        HeaderHandler.crash = true;

        try {
            port.comunicarFatura(Utility.createFatura(calendar,
                    serie.getNumSerie(), 3, 1234, "portal", 5111,
                    Utility.createItem("Batatas Cozidas", 4, 2)));
        } catch (EmissorInexistente_Exception | ClienteInexistente_Exception
                | FaturaInvalida_Exception | TotaisIncoerentes_Exception e) {
            System.out.println("test2 fatura3 Excepcao inesperada: "
                    + e.getMessage());
        }

        try {
            port.comunicarFatura(Utility.createFatura(calendar,
                    serie.getNumSerie(), 4, 1234, "portal", 5111,
                    Utility.createItem("Batatas Cozidas", 4, 2)));
        } catch (EmissorInexistente_Exception | ClienteInexistente_Exception
                | FaturaInvalida_Exception | TotaisIncoerentes_Exception e) {
            System.out.println("test2 fatura4 Excepcao inesperada: "
                    + e.getMessage());
        }

        // System.out.println("Foi pedida 1 serie e comunicadas 4 faturas,"
        // + " sendo que a do meio crashou o server");
        // System.out.println("Verificar que o primario ficou com 2 faturas"
        // + " e o secundario com 4");
        // System.out.println("----------------------------------");
        // System.out.println();

        // waits for all servers to sync themselves
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        QueryBuilder query = new QueryBuilder();
        query.SELECT(false).FROM("FATURA");

        QueryBuilder deleteSeries = new QueryBuilder();
        deleteSeries.DELETE(true).FROM("SERIE");
        QueryBuilder deleteFaturas = new QueryBuilder();
        deleteFaturas.DELETE(true).FROM("FATURA");
        QueryBuilder deleteItemFatura = new QueryBuilder();
        deleteItemFatura.DELETE(true).FROM("ITEM_FATURA");

        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_PRINCIPAL);
        SimpleMySQLResult result = mysql.Query(query.toString());
        assertEquals("MainServer", 2, result.getNumRows());
        mysql.Query(deleteSeries.toString());
        mysql.Query(deleteFaturas.toString());
        mysql.Query(deleteItemFatura.toString());
        mysql.close();

        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC1);
        result = mysql.Query(query.toString());
        assertEquals("SecundaryServer1", 4, result.getNumRows());
        mysql.Query(deleteSeries.toString());
        mysql.Query(deleteFaturas.toString());
        mysql.Query(deleteItemFatura.toString());
        mysql.close();

        mysql.connect(MySQL_HOST, MySQL_USER, MySQL_USER_PASSWORD, DB_SEC2);
        result = mysql.Query(query.toString());
        assertEquals("SecundaryServer2", 4, result.getNumRows());
        mysql.Query(deleteSeries.toString());
        mysql.Query(deleteFaturas.toString());
        mysql.Query(deleteItemFatura.toString());
        mysql.close();
    }
}
