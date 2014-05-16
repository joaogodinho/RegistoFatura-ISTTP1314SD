package pt.registofatura.ws.broadcast;

import pt.faults.ws.FaultsPortType;
import pt.registofatura.ws.Fatura;

/**
 * The Class ComunicarFaturaRequest.
 */
public class ComunicarFaturaRequest
        implements Request {

    /**
     * The fatura.
     */
    private Fatura fatura;
    
    /**
     * The ticket.
     */
    private int ticket;
    
    /**
     * The seq.
     */
    private int seq;

    /**
     * Instantiates a new comunicar fatura request.
     *
     * @param fatura the fatura
     * @param ticket the ticket
     * @param seq the seq
     */
    public ComunicarFaturaRequest(Fatura fatura, int ticket, int seq) {
        this.fatura = fatura;
        this.ticket = ticket;
        this.seq = seq;
    }

    /* (non-Javadoc)
     * @see pt.registofatura.ws.broadcast.Request#execute(pt.faults.ws.FaultsPortType)
     */
    @Override
    public void execute(FaultsPortType port) throws Exception {
        port.comunicarFatura(fatura.getData(), fatura.getNumSeqFatura(), fatura.getNumSerie(),
                fatura.getNifEmissor(), fatura.getNomeEmissor(), fatura.getNifCliente(),
                fatura.getItens(), fatura.getIva(), fatura.getTotal());
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
