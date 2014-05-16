
package pt.faults.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getLastServer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getLastServer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notUsed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLastServer", propOrder = {
    "notUsed"
})
public class GetLastServer {

    protected boolean notUsed;

    /**
     * Gets the value of the notUsed property.
     * 
     */
    public boolean isNotUsed() {
        return notUsed;
    }

    /**
     * Sets the value of the notUsed property.
     * 
     */
    public void setNotUsed(boolean value) {
        this.notUsed = value;
    }

}
