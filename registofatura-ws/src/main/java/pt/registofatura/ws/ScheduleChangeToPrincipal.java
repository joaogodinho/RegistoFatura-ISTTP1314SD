package pt.registofatura.ws;

import org.apache.log4j.Logger;

import pt.faults.ws.FaultsPortType;

/**
 * The Class ScheduleChangeToPrincipal.
 */
public class ScheduleChangeToPrincipal implements Runnable {
    
    /**
     * The Constant logger.
     */
    private static final Logger logger = Logger.getLogger(ScheduleChangeToPrincipal.class);

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        FaultsPortType fatherFaults = ReplicationHandling.connectToFather();
        FaultsState faultsState = FaultsState.getInstance();
        while(true) {
            try {
                logger.info("Pinging father...");
                int gf = fatherFaults.imAlive();
                faultsState.setGrandFather(gf);
                logger.info("Father alive, grandfather is: " + gf);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.fatal("Couldn't Thread.sleep()", e);
                System.exit(1);
            } catch (Exception e) {
                logger.info("Father died, switching...");
                ReplicationHandling.replaceDeadServer();
                return;
            }
        }
    }
}
