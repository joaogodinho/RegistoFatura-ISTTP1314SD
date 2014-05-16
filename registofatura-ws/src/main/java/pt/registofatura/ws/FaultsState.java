package pt.registofatura.ws;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Class FaultsState.
 */
public final class FaultsState {
    
    /**
     * The instance.
     */
    private static FaultsState instance = null;

    /**
     * The father.
     */
    private int father;

    /**
     * The faults thread.
     */
    private ExecutorService faultsThread = Executors.newFixedThreadPool(5);
    
    /**
     * The grand father.
     */
    private int grandFather;

    /**
     * The last.
     */
    private int last;
    
    /**
     * The last ticket.
     */
    private int lastTicket = 1;

    /**
     * Server number. Starts with 0 (principal).
     */
    private int n;
    
    /**
     * The name.
     */
    private String name;
    
    /**
     * The rf url.
     */
    private String rfUrl;
    
    /**
     * The son.
     */
    private int son = -1;
    
    /**
     * The son address.
     */
    private String sonAddress = null;

    /**
     * The uddi url.
     */
    private String uddiUrl;
    
    /**
     * The crash order.
     */
    public boolean crashOrder = false;
    
    /**
     * Instantiates a new faults state.
     */
    private FaultsState() { }

    /**
     * Gets the single instance of FaultsState.
     *
     * @return single instance of FaultsState
     */
    public static FaultsState getInstance() {
        if (instance == null) {
            instance = new FaultsState();
        }
        return instance;
    }
    
    /**
     * Gets the son address.
     *
     * @return the son address
     */
    public String getSonAddress() {
        return sonAddress;
    }

    /**
     * Sets the son address.
     *
     * @param sonAddress the new son address
     */
    public void setSonAddress(String sonAddress) {
        this.sonAddress = sonAddress;
    }

    /**
     * Gets the a ticket.
     *
     * @return the a ticket
     */
    public int getATicket() {
        return ++lastTicket;
    }
    
    /**
     * Gets the father.
     *
     * @return the father
     */
    public int getFather() {
        return father;
    }

    /**
     * Gets the faults thread.
     *
     * @return the faults thread
     */
    public ExecutorService getFaultsThread() {
        return faultsThread;
    }
    
    /**
     * Gets the grand father.
     *
     * @return the grand father
     */
    public int getGrandFather() {
        return grandFather;
    }

    /**
     * Gets the last.
     *
     * @return the last
     */
    public int getLast() {
        return last;
    }

    /**
     * Gets the n.
     *
     * @return the n
     */
    public int getN() {
        return n;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the r furl.
     *
     * @return the r furl
     */
    public String getRFurl() {
        return this.rfUrl;
    }

    /**
     * Gets the son.
     *
     * @return the son
     */
    public int getSon() {
        return son;
    }

    /**
     * Gets the son name.
     *
     * @return the son name
     */
    public String getSonName() {
        if (son != -1) {
            return "faults" + son;
        } else {
            return null;
        }
    }

    /**
     * Gets the uddi url.
     *
     * @return the uddi url
     */
    public String getUddiUrl() {
        return uddiUrl;
    }
    
    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl() {
        return "http://localhost:" + (16000 + n) + "/faults";
    }

    /**
     * Sets the father.
     *
     * @param father the new father
     */
    public void setFather(int father) {
        this.father = father;
    }

    /**
     * Sets the grand father.
     *
     * @param grandFather the new grand father
     */
    public void setGrandFather(int grandFather) {
        this.grandFather = grandFather;
    }

    /**
     * Sets the last.
     *
     * @param last the new last
     */
    public void setLast(int last) {
        this.last = last;
    }
    
    /**
     * Sets the n.
     *
     * @param n the new n
     */
    public void setN(int n) {
        this.n = n;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the r furl.
     *
     * @param url the new r furl
     */
    public void setRFurl(String url) {
        this.rfUrl = url;
    }

    /**
     * Sets the son.
     *
     * @param son the new son
     */
    public void setSon(int son) {
        this.son = son;
    }

    /**
     * Sets the uddi url.
     *
     * @param uddiUrl the new uddi url
     */
    public void setUddiUrl(String uddiUrl) {
        this.uddiUrl = uddiUrl;
    }
}
