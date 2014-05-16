
package pt.faults.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for imAliveResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="imAliveResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="grandfather" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "imAliveResponse", propOrder = {
    "grandfather"
})
public class ImAliveResponse {

    protected int grandfather;

    /**
     * Gets the value of the grandfather property.
     * 
     */
    public int getGrandfather() {
        return grandfather;
    }

    /**
     * Sets the value of the grandfather property.
     * 
     */
    public void setGrandfather(int value) {
        this.grandfather = value;
    }

}
