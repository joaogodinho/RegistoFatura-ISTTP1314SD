package pt.registofatura.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.pstm.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class Serie_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public final static dml.runtime.RoleMany<pt.registofatura.domain.Serie,pt.registofatura.domain.Fatura> role$$fatura = new dml.runtime.RoleMany<pt.registofatura.domain.Serie,pt.registofatura.domain.Fatura>() {
        public dml.runtime.RelationBaseSet<pt.registofatura.domain.Fatura> getSet(pt.registofatura.domain.Serie o1) {
            return ((Serie_Base)o1).get$rl$fatura();
        }
        public dml.runtime.Role<pt.registofatura.domain.Fatura,pt.registofatura.domain.Serie> getInverseRole() {
            return pt.registofatura.domain.Fatura.role$$serie;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Serie,pt.registofatura.domain.Financa> role$$financa = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Serie,pt.registofatura.domain.Financa>() {
        public pt.registofatura.domain.Financa getValue(pt.registofatura.domain.Serie o1) {
            return ((Serie_Base.DO_State)o1.get$obj$state(false)).financa;
        }
        public void setValue(pt.registofatura.domain.Serie o1, pt.registofatura.domain.Financa o2) {
            ((Serie_Base.DO_State)o1.get$obj$state(true)).financa = o2;
        }
        public dml.runtime.Role<pt.registofatura.domain.Financa,pt.registofatura.domain.Serie> getInverseRole() {
            return pt.registofatura.domain.Financa.role$$serie;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Serie,pt.registofatura.domain.Entidade> role$$emissor = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Serie,pt.registofatura.domain.Entidade>() {
        public pt.registofatura.domain.Entidade getValue(pt.registofatura.domain.Serie o1) {
            return ((Serie_Base.DO_State)o1.get$obj$state(false)).emissor;
        }
        public void setValue(pt.registofatura.domain.Serie o1, pt.registofatura.domain.Entidade o2) {
            ((Serie_Base.DO_State)o1.get$obj$state(true)).emissor = o2;
        }
        public dml.runtime.Role<pt.registofatura.domain.Entidade,pt.registofatura.domain.Serie> getInverseRole() {
            return pt.registofatura.domain.Entidade.role$$serie;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Serie,pt.registofatura.domain.Fatura> FaturaTemSerie = new pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Serie,pt.registofatura.domain.Fatura>(role$$fatura);
    static {
        pt.registofatura.domain.Fatura.FaturaTemSerie = FaturaTemSerie.getInverseRelation();
    }
    
    static {
        FaturaTemSerie.setRelationName("pt.registofatura.domain.Serie.FaturaTemSerie");
    }
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Serie,pt.registofatura.domain.Financa> FinancaTemSeries = new pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Serie,pt.registofatura.domain.Financa>(role$$financa);
    static {
        pt.registofatura.domain.Financa.FinancaTemSeries = FinancaTemSeries.getInverseRelation();
    }
    
    static {
        FinancaTemSeries.setRelationName("pt.registofatura.domain.Serie.FinancaTemSeries");
    }
    public static dml.runtime.Relation<pt.registofatura.domain.Serie,pt.registofatura.domain.Entidade> SerieTemEmissor;
    
    
    private RelationList<pt.registofatura.domain.Serie,pt.registofatura.domain.Fatura> get$rl$fatura() {
        return get$$relationList("fatura", FaturaTemSerie);
        
    }
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  Serie_Base() {
        super();
    }
    
    public int getNumero() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "numero");
        return ((DO_State)this.get$obj$state(false)).numero;
    }
    
    public void setNumero(int numero) {
        ((DO_State)this.get$obj$state(true)).numero = numero;
    }
    
    private int get$numero() {
        int value = ((DO_State)this.get$obj$state(false)).numero;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$numero(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).numero = (int)(arg0);
    }
    
    public int getSeq() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "seq");
        return ((DO_State)this.get$obj$state(false)).seq;
    }
    
    public void setSeq(int seq) {
        ((DO_State)this.get$obj$state(true)).seq = seq;
    }
    
    private int get$seq() {
        int value = ((DO_State)this.get$obj$state(false)).seq;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$seq(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).seq = (int)(arg0);
    }
    
    public org.joda.time.DateTime getData() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "data");
        return ((DO_State)this.get$obj$state(false)).data;
    }
    
    public void setData(org.joda.time.DateTime data) {
        ((DO_State)this.get$obj$state(true)).data = data;
    }
    
    private java.sql.Timestamp get$data() {
        org.joda.time.DateTime value = ((DO_State)this.get$obj$state(false)).data;
        return (value == null) ? null : pt.ist.fenixframework.pstm.ToSqlConverter.getValueForDateTime(value);
    }
    
    private final void set$data(org.joda.time.DateTime arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).data = (org.joda.time.DateTime)((arg0 == null) ? null : arg0);
    }
    
    public int getFaturaCount() {
        return get$rl$fatura().size();
    }
    
    public boolean hasAnyFatura() {
        return (! get$rl$fatura().isEmpty());
    }
    
    public boolean hasFatura(pt.registofatura.domain.Fatura fatura) {
        return get$rl$fatura().contains(fatura);
    }
    
    public java.util.Set<pt.registofatura.domain.Fatura> getFaturaSet() {
        return get$rl$fatura();
    }
    
    public void addFatura(pt.registofatura.domain.Fatura fatura) {
        FaturaTemSerie.add((pt.registofatura.domain.Serie)this, fatura);
    }
    
    public void removeFatura(pt.registofatura.domain.Fatura fatura) {
        FaturaTemSerie.remove((pt.registofatura.domain.Serie)this, fatura);
    }
    
    public java.util.List<pt.registofatura.domain.Fatura> getFatura() {
        return get$rl$fatura();
    }
    
    public void set$fatura(OJBFunctionalSetWrapper fatura) {
        get$rl$fatura().setFromOJB(this, "fatura", fatura);
    }
    
    public java.util.Iterator<pt.registofatura.domain.Fatura> getFaturaIterator() {
        return get$rl$fatura().iterator();
    }
    
    public pt.registofatura.domain.Financa getFinanca() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "financa");
        return ((DO_State)this.get$obj$state(false)).financa;
    }
    
    public void setFinanca(pt.registofatura.domain.Financa financa) {
        FinancaTemSeries.add((pt.registofatura.domain.Serie)this, financa);
    }
    
    public boolean hasFinanca() {
        return (getFinanca() != null);
    }
    
    public void removeFinanca() {
        setFinanca(null);
    }
    
    private java.lang.Long get$oidFinanca() {
        pt.ist.fenixframework.pstm.AbstractDomainObject value = ((DO_State)this.get$obj$state(false)).financa;
        return (value == null) ? null : value.getOid();
    }
    
    public pt.registofatura.domain.Entidade getEmissor() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "emissor");
        return ((DO_State)this.get$obj$state(false)).emissor;
    }
    
    public void setEmissor(pt.registofatura.domain.Entidade emissor) {
        SerieTemEmissor.add((pt.registofatura.domain.Serie)this, emissor);
    }
    
    public boolean hasEmissor() {
        return (getEmissor() != null);
    }
    
    public void removeEmissor() {
        setEmissor(null);
    }
    
    private java.lang.Long get$oidEmissor() {
        pt.ist.fenixframework.pstm.AbstractDomainObject value = ((DO_State)this.get$obj$state(false)).emissor;
        return (value == null) ? null : value.getOid();
    }
    
    protected void checkDisconnected() {
        if (hasAnyFatura()) handleAttemptToDeleteConnectedObject();
        if (hasFinanca()) handleAttemptToDeleteConnectedObject();
        if (hasEmissor()) handleAttemptToDeleteConnectedObject();
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$numero(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "NUMERO"), state);
        set$seq(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "SEQ"), state);
        set$data(pt.ist.fenixframework.pstm.ResultSetReader.readDateTime(rs, "DATA"), state);
        castedState.financa = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_FINANCA");
        castedState.emissor = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_EMISSOR");
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("fatura")) return FaturaTemSerie;
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("fatura", FaturaTemSerie);
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private int numero;
        private int seq;
        private org.joda.time.DateTime data;
        private pt.registofatura.domain.Financa financa;
        private pt.registofatura.domain.Entidade emissor;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.numero = this.numero;
            newCasted.seq = this.seq;
            newCasted.data = this.data;
            newCasted.financa = this.financa;
            newCasted.emissor = this.emissor;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private int numero;
            private int seq;
            private org.joda.time.DateTime data;
            private pt.registofatura.domain.Financa financa;
            private pt.registofatura.domain.Entidade emissor;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.numero = obj.numero;
                this.seq = obj.seq;
                this.data = obj.data;
                this.financa = obj.financa;
                this.emissor = obj.emissor;
                
            }
            
             Object readResolve() throws java.io.ObjectStreamException {
                DO_State newState = new DO_State();
                fillInState(newState);
                return newState;
            }
            
            protected void fillInState(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State obj) {
                super.fillInState(obj);
                DO_State state = (DO_State)obj;
                state.numero = this.numero;
                state.seq = this.seq;
                state.data = this.data;
                state.financa = this.financa;
                state.emissor = this.emissor;
                
            }
            
        }
        
    }
    
}
