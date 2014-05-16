package pt.registofatura.ws.broadcast;

import pt.faults.ws.FaultsPortType;

/**
 * The Class PedirSerieRequest.
 */
public class PedirSerieRequest implements Request {
    
    /**
     * The nif emissor.
     */
    private int nifEmissor;
    
    /**
     * The ticket.
     */
    private int ticket;
    
    /**
     * The seq.
     */
    private int seq;

    /**
     * Instantiates a new pedir serie request.
     *
     * @param nifEmissor the nif emissor
     * @param ticket the ticket
     * @param seq the seq
     */
    public PedirSerieRequest(int nifEmissor, int ticket, int seq) {
        this.nifEmissor = nifEmissor;
        this.ticket = ticket;
        this.seq = seq;
    }

    /* (non-Javadoc)
     * @see pt.registofatura.ws.broadcast.Request#execute(pt.faults.ws.FaultsPortType)
     */
    @Override
    public void execute(FaultsPortType port) throws Exception {
        port.pedirSerie(nifEmissor);
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