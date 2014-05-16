package pt.registofatura.ws.handler;

import pt.registofatura.ws.Serie;

/**
 * The Class Record.
 */
public class Record {
    
    /**
     * The serie.
     */
    private Serie serie;
    
    /**
     * The comunicar fatura answer.
     */
    private boolean comunicarFaturaAnswer = false;
    
    /**
     * The seq.
     */
    private int seq;
    
    /**
     * Gets the comunicar fatura answer.
     *
     * @return the comunicar fatura answer
     */
    public boolean getComunicarFaturaAnswer() {
        return comunicarFaturaAnswer;
    }
    
    /**
     * Sets the comunicar fatura answer.
     *
     * @param answer the new comunicar fatura answer
     */
    public void setComunicarFaturaAnswer(boolean answer) {
        comunicarFaturaAnswer = answer;
    }
    
    /**
     * Gets the seq.
     *
     * @return the seq
     */
    public int getSeq() {
        return seq;
    }
    
    /**
     * Sets the seq.
     *
     * @param seq the new seq
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }

    /**
     * Sets the serie.
     *
     * @param serie the new serie
     */
    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    /**
     * Gets the serie.
     *
     * @return the serie
     */
    public Serie getSerie() {
        return serie;
    }
}
