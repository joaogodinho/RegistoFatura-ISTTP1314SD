package pt.registofatura.ws.impl;

import pt.registofatura.ws.FaultsState;
import pt.registofatura.ws.ReplicationHandling;

/**
 * The Class RegistoFaturaMain.
 */
public class RegistoFaturaMain {
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        // Check arguments
        if (args.length < 3) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s <url> <name> <uddi>%n",
                    RegistoFaturaMain.class.getName());
            return;
        }
        
        String url = args[0];
        String name = args[1];
        String uddi = args[2];

        FenixFrameworkSetup.setup();

        FaultsState faultsState = FaultsState.getInstance();
        faultsState.setRFurl(url);
        faultsState.setName(name);
        faultsState.setUddiUrl(uddi);

        if (!ReplicationHandling.isPrimaryActive()) {
            ReplicationHandling.startAsPrimary();
        } else {
            ReplicationHandling.startAsSecundary(ReplicationHandling
                    .getNextServerNumber());
        }
        System.out.println("Waiting for connections...");
    }
}
