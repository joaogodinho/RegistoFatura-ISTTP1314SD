package pt.registofatura.domain;

import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;

public class Fatura extends Fatura_Base {

    public Fatura() {
        super();
    }

    public Fatura(int iva, XMLGregorianCalendar data, int numSeq, int total,
            Serie serie, Entidade cliente, Entidade emissor) {
        super();
        setIva(iva);
        setData(new DateTime(data.toGregorianCalendar().getTime()));
        setNumSeq(numSeq);
        setTotal(total);
        setCliente(cliente);
        setEmissor(emissor);
        setSerie(serie);
    }

    public final void addItem(pt.registofatura.ws.ItemFatura item) {
        addItem(new ItemFatura(item));
    }
}
