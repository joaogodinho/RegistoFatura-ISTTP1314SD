package pt.registofatura.ws.broadcast;

import pt.faults.ws.FaultsPortType;

/**
 * The Interface Request.
 */
public interface Request {
    
    /**
     * Execute.
     *
     * @param port the port
     * @throws Exception the exception
     */
    void execute(FaultsPortType port) throws Exception;
    
    /**
     * Gets the ticket.
     *
     * @return the ticket
     */
    int getTicket();
    
    /**
     * Gets the seq.
     *
     * @return the seq
     */
    int getSeq();
}
