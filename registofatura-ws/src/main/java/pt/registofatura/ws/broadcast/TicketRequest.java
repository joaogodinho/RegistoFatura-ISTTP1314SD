package pt.registofatura.ws.broadcast;

import pt.faults.ws.FaultsPortType;

/**
 * The Class TicketRequest.
 */
public class TicketRequest
        implements Request {

    /**
     * The ticket.
     */
    private int ticket;
    
    /**
     * The seq.
     */
    private int seq;
    
    /**
     * Instantiates a new ticket request.
     *
     * @param ticket the ticket
     * @param seq the seq
     */
    public TicketRequest(int ticket, int seq) {
        this.ticket = ticket;
        this.seq = seq;
    }

    /* (non-Javadoc)
     * @see pt.registofatura.ws.broadcast.Request#execute(pt.faults.ws.FaultsPortType)
     */
    @Override
    public void execute(FaultsPortType port) throws Exception {
        port.ticket(false);
    }

    /* (non-Javadoc)
     * @see pt.registofatura.ws.broadcast.Request#getTicket()
     */
    @Override
    public int getTicket() {
        return ticket;
    }

    /* (non-Javadoc)
     * @see pt.registofatura.ws.broadcast.Request#getSeq()
     */
    @Override
    public int getSeq() {
        return seq;
    }

}
