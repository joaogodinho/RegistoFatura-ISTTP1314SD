package pt.registofatura.ws.impl;

import pt.registofatura.ws.ClienteInexistente;
import pt.registofatura.ws.ClienteInexistente_Exception;
import pt.registofatura.ws.EmissorInexistente;
import pt.registofatura.ws.EmissorInexistente_Exception;
import pt.registofatura.ws.FaturaInvalida;
import pt.registofatura.ws.FaturaInvalida_Exception;
import pt.registofatura.ws.TotaisIncoerentes;
import pt.registofatura.ws.TotaisIncoerentes_Exception;

/**
 * A factory for creating Exception objects.
 */
final class ExceptionFactory {
    
    /**
     * Instantiates a new exception factory.
     */
    private ExceptionFactory() {
    }

    /**
     * Generate emissor inexistente exception.
     *
     * @param nifEmissor the nif emissor
     * @return the emissor inexistente_ exception
     */
    public static EmissorInexistente_Exception generateEmissorInexistenteException(int nifEmissor) {
        EmissorInexistente emissorInexistente = new EmissorInexistente();
        emissorInexistente.setMensagem("Nao existe nenhum emissor com o NIF: " + nifEmissor);
        emissorInexistente.setNif(nifEmissor);
        return new EmissorInexistente_Exception(emissorInexistente.getMensagem(),
                emissorInexistente);
    }

    /**
     * Generate cliente inexistente exception.
     *
     * @param nifCliente the nif cliente
     * @return the cliente inexistente_ exception
     */
    public static ClienteInexistente_Exception generateClienteInexistenteException(int nifCliente) {
        ClienteInexistente clienteInexistente = new ClienteInexistente();
        clienteInexistente.setMensagem("Nao existe nenhum cliente com o NIF: " + nifCliente);
        clienteInexistente.setNif(nifCliente);
        return new ClienteInexistente_Exception(clienteInexistente.getMensagem(),
                clienteInexistente);
    }

    /**
     * Generate totais incoerentes exception.
     *
     * @param total the total
     * @param total2 the total2
     * @return the totais incoerentes_ exception
     */
    public static TotaisIncoerentes_Exception generateTotaisIncoerentesException(int total,
                                                                                 int total2) {
        TotaisIncoerentes totaisIncoerentes = new TotaisIncoerentes();
        totaisIncoerentes.setRazao("O valor dos items (" + total
                + ") nao coincide com o valor da fatura (" + total2 + ")");
        return new TotaisIncoerentes_Exception(totaisIncoerentes.getRazao(), totaisIncoerentes);
    }
    
    /**
     * Generate totais incoerentes by iva exception.
     *
     * @param total the total
     * @param total2 the total2
     * @return the totais incoerentes_ exception
     */
    public static TotaisIncoerentes_Exception generateTotaisIncoerentesByIvaException(int total,
                                                                                 int total2) {
        TotaisIncoerentes totaisIncoerentes = new TotaisIncoerentes();
        totaisIncoerentes.setRazao("A soma dos ivas (" + total
                + ") nao coincide com o valor indicado do iva (" + total2 + ")");
        return new TotaisIncoerentes_Exception(totaisIncoerentes.getRazao(), totaisIncoerentes);
    }

    /**
     * Generate data fatura invalida exception.
     *
     * @param seqFatura the seq fatura
     * @param seqSerie the seq serie
     * @return the fatura invalida_ exception
     */
    public static FaturaInvalida_Exception generateDataFaturaInvalidaException(int seqFatura, int seqSerie) {
        return generateFaturaInvalidaException("A data de validade ja expirou", seqFatura, seqSerie);
    }
    
    /**
     * Generate fatura invalida exception.
     *
     * @param msg the msg
     * @param seqFatura the seq fatura
     * @param seqSerie the seq serie
     * @return the fatura invalida_ exception
     */
    public static FaturaInvalida_Exception generateFaturaInvalidaException(String msg, int seqFatura, int seqSerie) {
        FaturaInvalida faturaInvalida = new FaturaInvalida();
        faturaInvalida.setMensagem(msg);
        faturaInvalida.setNumSeqFatura(seqFatura);
        faturaInvalida.setNumSerie(seqSerie);
        return new FaturaInvalida_Exception(faturaInvalida.getMensagem(), faturaInvalida);
    }
}
