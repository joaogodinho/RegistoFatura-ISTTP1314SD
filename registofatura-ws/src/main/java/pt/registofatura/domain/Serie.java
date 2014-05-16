package pt.registofatura.domain;

import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;

public class Serie extends Serie_Base {

    public static final int MAX_FATURAS_IN_SERIE = 4; 
    
    public Serie() {
        super();
    }

    public Serie(pt.registofatura.ws.Serie serie) {
        super();
        setNumero(serie.getNumSerie());
        setData(new DateTime(serie.getValidoAte().toGregorianCalendar()
                .getTime()));
    }

    public Serie(int Numero, XMLGregorianCalendar data) {
        super();
        setNumero(Numero);
        setData(new DateTime(data.toGregorianCalendar().getTime()));
    }

    public boolean isFull() {
        return getSeq() <= MAX_FATURAS_IN_SERIE;
    }
}
