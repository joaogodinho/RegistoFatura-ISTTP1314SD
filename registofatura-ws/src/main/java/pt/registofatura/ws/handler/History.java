package pt.registofatura.ws.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * The Class History.
 */
public class History {
    
    /**
     * The Constant logger.
     */
    private static final Logger logger = Logger.getLogger(History.class);
    
    /**
     * The instance.
     */
    private static History instance = null;
    
    /**
     * The history.
     */
    private Map<Integer, Record> history = new HashMap<Integer, Record>();
    
    /**
     * Instantiates a new history.
     */
    protected History() { }
    
    /**
     * Gets the single instance of History.
     *
     * @return single instance of History
     */
    public static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }
    
    /**
     * Validate message.
     *
     * @param ticket the ticket
     * @param seq the seq
     * @return the record
     */
    public Record validateMessage(int ticket, int seq) {
        logger.info("Validating request message...");
        Record record = history.get(ticket);
        if (record != null) {
            if (record.getSeq() == seq) {
                logger.info("Message not validated, duplicated message");
                return record;
            }
        }
        logger.info("Message validated.");
        return null;
    }
    
    /**
     * Gets the last record.
     *
     * @param ticket the ticket
     * @return the last record
     */
    public Record getLastRecord(int ticket) {
        return history.get(ticket);
    }
    
    /**
     * Insert record.
     *
     * @param ticket the ticket
     * @param record the record
     */
    public void insertRecord(int ticket, Record record) {
        history.put(ticket, record);
    }
}
