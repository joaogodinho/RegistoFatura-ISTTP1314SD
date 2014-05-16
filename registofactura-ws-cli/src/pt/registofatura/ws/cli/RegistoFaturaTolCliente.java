package pt.registofatura.ws.cli;

import java.util.ArrayList;
import java.util.List;

import javax.xml.registry.JAXRException;

import pt.registofatura.ws.Fatura;
import pt.registofatura.ws.RegistoFaturaPortType;

public class RegistoFaturaTolCliente {
    public static void main(String[] args) throws JAXRException {
     // Check arguments
        if (args.length < 2) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s uddiURL name%n",
                    RegistoFaturaClient.class.getName());
            return;
        }

        String uddiURL = args[0];
        String name = args[1];

        RegistoFaturaPortType port = new FrontEnd(name, uddiURL);

        /* testes */

        System.out.println("----------------------");
        System.out.println("Pedir 10 series para o portal nif=1234");
        
        List<Integer> seriesDoPortal = new ArrayList<>();
        
        try {
            for(int i = 0; i < 10; i++) {
                seriesDoPortal.add(port.pedirSerie(1234).getNumSerie());
                System.out.println("Serie[" + i + "]: " + seriesDoPortal.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("----------------------");
        System.out.println("comunicar 40 Faturas entre emissor 1234 e cliente 1001");
        
        try {
            for(int i = 0; i < 10; i++) {
                for(int j = 1; j < 5; j++) {
                        Fatura fatura = Utility.createFatura(Utility.todaysDate(), seriesDoPortal.get(i),
                            j, 1234, "bc", 1001, Utility.createItem("bacalhau", 40, 23), Utility.createItem());
                        port.comunicarFatura(fatura);
                        System.out.println("Fatura Serie[" + i + "] Seq[" + j + "]");
                        //Thread.sleep(500);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------------");
    }
}
