package pt.registofatura.ws.impl;

import org.apache.log4j.Logger;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.registofatura.domain.Financa;

public class FenixFrameworkSetup {
    static final Logger logger = Logger.getLogger(FenixFrameworkSetup.class);
    
    
    public static void setup() {
        logger.info("Starting FenixFramework");
        final String dml = System.getProperty("dml", "src/main/dml/faturas.dml");
        final String host = System.getProperty("host", "localhost:3306");
        final String database = System.getProperty("database", "faturasdb");
        final String location = "//" + host + "/" + database;
        final String user = System.getProperty("user", "rest");
        final String pass = System.getProperty("pass", "r3st");
        FenixFramework.initialize(new Config() {
            {
                domainModelPath = dml;
                dbAlias = location;
                dbUsername = user;
                dbPassword = pass;
                rootClass = Financa.class;
            }
        });
    }
}
