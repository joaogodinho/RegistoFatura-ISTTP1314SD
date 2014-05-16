package pt.registofatura.ws.cli.test;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Java program to convert XMLGregorianCalendar to Date and inverse i.e. java.util.Date to
 * XMLGregorianCalendar. If you are using XJC to create Java classes from XML Schema or
 * XSD file, By default JAXB map XSD data types xs:date, xs:time and xs:dateTime to
 * XMLGregorianCalendar in Java.
 * 
 * @author Javin Paul
 *         {@link "http://javarevisited.blogspot.pt/2013/02/convert-xmlgregoriancalendar-to-date-xmlgregoriancalendar-java-example-tutorial.html"}
 */
public class XMLCalendarToDate {

    /*
     * Converts java.util.Date to javax.xml.datatype.XMLGregorianCalendar
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException("Erro a converter de Date para GregorianCalendar.\n");
        }
        return xmlCalendar;
    }

    /*
     * Converts XMLGregorianCalendar to java.util.Date in Java
     */
    public static Date toDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

}
