package pt.ist.fenixframework;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.pstm.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;

public class ValueTypeSerializationGenerator {
    
    // VT: TxNumber
    public static class Serialized$TxNumber implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        private java.lang.Long pt_ist_fenixframework_TxNumber_externalize;
        private  Serialized$TxNumber(pt.ist.fenixframework.TxNumber obj) {
            this.pt_ist_fenixframework_TxNumber_externalize = (java.lang.Long)pt.ist.fenixframework.TxNumber.externalize(obj);
            
        }
        
    }
    public static Serialized$TxNumber serialize$TxNumber(pt.ist.fenixframework.TxNumber obj) {
        return (obj == null) ? null : new Serialized$TxNumber(obj);
    }
    public static pt.ist.fenixframework.TxNumber deSerialize$TxNumber(Serialized$TxNumber obj) {
        return (obj == null) ? null : (pt.ist.fenixframework.TxNumber)pt.ist.fenixframework.TxNumber.internalize(obj.pt_ist_fenixframework_TxNumber_externalize);
    }
    
}
