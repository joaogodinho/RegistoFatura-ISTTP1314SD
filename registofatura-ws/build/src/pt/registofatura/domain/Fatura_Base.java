package pt.registofatura.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.pstm.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class Fatura_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Fatura,pt.registofatura.domain.Serie> role$$serie = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Fatura,pt.registofatura.domain.Serie>() {
        public pt.registofatura.domain.Serie getValue(pt.registofatura.domain.Fatura o1) {
            return ((Fatura_Base.DO_State)o1.get$obj$state(false)).serie;
        }
        public void setValue(pt.registofatura.domain.Fatura o1, pt.registofatura.domain.Serie o2) {
            ((Fatura_Base.DO_State)o1.get$obj$state(true)).serie = o2;
        }
        public dml.runtime.Role<pt.registofatura.domain.Serie,pt.registofatura.domain.Fatura> getInverseRole() {
            return pt.registofatura.domain.Serie.role$$fatura;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Fatura,pt.registofatura.domain.Entidade> role$$emissor = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Fatura,pt.registofatura.domain.Entidade>() {
        public pt.registofatura.domain.Entidade getValue(pt.registofatura.domain.Fatura o1) {
            return ((Fatura_Base.DO_State)o1.get$obj$state(false)).emissor;
        }
        public void setValue(pt.registofatura.domain.Fatura o1, pt.registofatura.domain.Entidade o2) {
            ((Fatura_Base.DO_State)o1.get$obj$state(true)).emissor = o2;
        }
        public dml.runtime.Role<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura> getInverseRole() {
            return pt.registofatura.domain.Entidade.role$$faturaEmissor;
        }
        
    };
    public final static dml.runtime.RoleMany<pt.registofatura.domain.Fatura,pt.registofatura.domain.ItemFatura> role$$item = new dml.runtime.RoleMany<pt.registofatura.domain.Fatura,pt.registofatura.domain.ItemFatura>() {
        public dml.runtime.RelationBaseSet<pt.registofatura.domain.ItemFatura> getSet(pt.registofatura.domain.Fatura o1) {
            return ((Fatura_Base)o1).get$rl$item();
        }
        public dml.runtime.Role<pt.registofatura.domain.ItemFatura,pt.registofatura.domain.Fatura> getInverseRole() {
            return pt.registofatura.domain.ItemFatura.role$$fatura;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Fatura,pt.registofatura.domain.Entidade> role$$cliente = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Fatura,pt.registofatura.domain.Entidade>() {
        public pt.registofatura.domain.Entidade getValue(pt.registofatura.domain.Fatura o1) {
            return ((Fatura_Base.DO_State)o1.get$obj$state(false)).cliente;
        }
        public void setValue(pt.registofatura.domain.Fatura o1, pt.registofatura.domain.Entidade o2) {
            ((Fatura_Base.DO_State)o1.get$obj$state(true)).cliente = o2;
        }
        public dml.runtime.Role<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura> getInverseRole() {
            return pt.registofatura.domain.Entidade.role$$faturaCliente;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Fatura,pt.registofatura.domain.Financa> role$$financa = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Fatura,pt.registofatura.domain.Financa>() {
        public pt.registofatura.domain.Financa getValue(pt.registofatura.domain.Fatura o1) {
            return ((Fatura_Base.DO_State)o1.get$obj$state(false)).financa;
        }
        public void setValue(pt.registofatura.domain.Fatura o1, pt.registofatura.domain.Financa o2) {
            ((Fatura_Base.DO_State)o1.get$obj$state(true)).financa = o2;
        }
        public dml.runtime.Role<pt.registofatura.domain.Financa,pt.registofatura.domain.Fatura> getInverseRole() {
            return pt.registofatura.domain.Financa.role$$fatura;
        }
        
    };
    public static dml.runtime.Relation<pt.registofatura.domain.Fatura,pt.registofatura.domain.Serie> FaturaTemSerie;
    public static dml.runtime.Relation<pt.registofatura.domain.Fatura,pt.registofatura.domain.Entidade> FaturaTemEmissor;
    public static dml.runtime.Relation<pt.registofatura.domain.Fatura,pt.registofatura.domain.ItemFatura> FaturaTemItens;
    public static dml.runtime.Relation<pt.registofatura.domain.Fatura,pt.registofatura.domain.Entidade> FaturaTemCliente;
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Fatura,pt.registofatura.domain.Financa> FinancaTemFaturas = new pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Fatura,pt.registofatura.domain.Financa>(role$$financa);
    static {
        pt.registofatura.domain.Financa.FinancaTemFaturas = FinancaTemFaturas.getInverseRelation();
    }
    
    static {
        FinancaTemFaturas.setRelationName("pt.registofatura.domain.Fatura.FinancaTemFaturas");
    }
    
    
    private RelationList<pt.registofatura.domain.Fatura,pt.registofatura.domain.ItemFatura> get$rl$item() {
        return get$$relationList("item", FaturaTemItens);
        
    }
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  Fatura_Base() {
        super();
    }
    
    public int getIva() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "iva");
        return ((DO_State)this.get$obj$state(false)).iva;
    }
    
    public void setIva(int iva) {
        ((DO_State)this.get$obj$state(true)).iva = iva;
    }
    
    private int get$iva() {
        int value = ((DO_State)this.get$obj$state(false)).iva;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$iva(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).iva = (int)(arg0);
    }
    
    public int getNumSeq() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "numSeq");
        return ((DO_State)this.get$obj$state(false)).numSeq;
    }
    
    public void setNumSeq(int numSeq) {
        ((DO_State)this.get$obj$state(true)).numSeq = numSeq;
    }
    
    private int get$numSeq() {
        int value = ((DO_State)this.get$obj$state(false)).numSeq;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$numSeq(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).numSeq = (int)(arg0);
    }
    
    public int getTotal() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "total");
        return ((DO_State)this.get$obj$state(false)).total;
    }
    
    public void setTotal(int total) {
        ((DO_State)this.get$obj$state(true)).total = total;
    }
    
    private int get$total() {
        int value = ((DO_State)this.get$obj$state(false)).total;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$total(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).total = (int)(arg0);
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
    
    public pt.registofatura.domain.Serie getSerie() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "serie");
        return ((DO_State)this.get$obj$state(false)).serie;
    }
    
    public void setSerie(pt.registofatura.domain.Serie serie) {
        FaturaTemSerie.add((pt.registofatura.domain.Fatura)this, serie);
    }
    
    public boolean hasSerie() {
        return (getSerie() != null);
    }
    
    public void removeSerie() {
        setSerie(null);
    }
    
    private java.lang.Long get$oidSerie() {
        pt.ist.fenixframework.pstm.AbstractDomainObject value = ((DO_State)this.get$obj$state(false)).serie;
        return (value == null) ? null : value.getOid();
    }
    
    public pt.registofatura.domain.Entidade getEmissor() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "emissor");
        return ((DO_State)this.get$obj$state(false)).emissor;
    }
    
    public void setEmissor(pt.registofatura.domain.Entidade emissor) {
        FaturaTemEmissor.add((pt.registofatura.domain.Fatura)this, emissor);
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
    
    public int getItemCount() {
        return get$rl$item().size();
    }
    
    public boolean hasAnyItem() {
        return (! get$rl$item().isEmpty());
    }
    
    public boolean hasItem(pt.registofatura.domain.ItemFatura item) {
        return get$rl$item().contains(item);
    }
    
    public java.util.Set<pt.registofatura.domain.ItemFatura> getItemSet() {
        return get$rl$item();
    }
    
    public void addItem(pt.registofatura.domain.ItemFatura item) {
        FaturaTemItens.add((pt.registofatura.domain.Fatura)this, item);
    }
    
    public void removeItem(pt.registofatura.domain.ItemFatura item) {
        FaturaTemItens.remove((pt.registofatura.domain.Fatura)this, item);
    }
    
    public java.util.List<pt.registofatura.domain.ItemFatura> getItem() {
        return get$rl$item();
    }
    
    public void set$item(OJBFunctionalSetWrapper item) {
        get$rl$item().setFromOJB(this, "item", item);
    }
    
    public java.util.Iterator<pt.registofatura.domain.ItemFatura> getItemIterator() {
        return get$rl$item().iterator();
    }
    
    public pt.registofatura.domain.Entidade getCliente() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "cliente");
        return ((DO_State)this.get$obj$state(false)).cliente;
    }
    
    public void setCliente(pt.registofatura.domain.Entidade cliente) {
        FaturaTemCliente.add((pt.registofatura.domain.Fatura)this, cliente);
    }
    
    public boolean hasCliente() {
        return (getCliente() != null);
    }
    
    public void removeCliente() {
        setCliente(null);
    }
    
    private java.lang.Long get$oidCliente() {
        pt.ist.fenixframework.pstm.AbstractDomainObject value = ((DO_State)this.get$obj$state(false)).cliente;
        return (value == null) ? null : value.getOid();
    }
    
    public pt.registofatura.domain.Financa getFinanca() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "financa");
        return ((DO_State)this.get$obj$state(false)).financa;
    }
    
    public void setFinanca(pt.registofatura.domain.Financa financa) {
        FinancaTemFaturas.add((pt.registofatura.domain.Fatura)this, financa);
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
    
    protected void checkDisconnected() {
        if (hasSerie()) handleAttemptToDeleteConnectedObject();
        if (hasEmissor()) handleAttemptToDeleteConnectedObject();
        if (hasAnyItem()) handleAttemptToDeleteConnectedObject();
        if (hasCliente()) handleAttemptToDeleteConnectedObject();
        if (hasFinanca()) handleAttemptToDeleteConnectedObject();
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$iva(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "IVA"), state);
        set$numSeq(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "NUM_SEQ"), state);
        set$total(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "TOTAL"), state);
        set$data(pt.ist.fenixframework.pstm.ResultSetReader.readDateTime(rs, "DATA"), state);
        castedState.serie = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_SERIE");
        castedState.emissor = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_EMISSOR");
        castedState.cliente = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_CLIENTE");
        castedState.financa = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_FINANCA");
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("item")) return FaturaTemItens;
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("item", FaturaTemItens);
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private int iva;
        private int numSeq;
        private int total;
        private org.joda.time.DateTime data;
        private pt.registofatura.domain.Serie serie;
        private pt.registofatura.domain.Entidade emissor;
        private pt.registofatura.domain.Entidade cliente;
        private pt.registofatura.domain.Financa financa;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.iva = this.iva;
            newCasted.numSeq = this.numSeq;
            newCasted.total = this.total;
            newCasted.data = this.data;
            newCasted.serie = this.serie;
            newCasted.emissor = this.emissor;
            newCasted.cliente = this.cliente;
            newCasted.financa = this.financa;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private int iva;
            private int numSeq;
            private int total;
            private org.joda.time.DateTime data;
            private pt.registofatura.domain.Serie serie;
            private pt.registofatura.domain.Entidade emissor;
            private pt.registofatura.domain.Entidade cliente;
            private pt.registofatura.domain.Financa financa;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.iva = obj.iva;
                this.numSeq = obj.numSeq;
                this.total = obj.total;
                this.data = obj.data;
                this.serie = obj.serie;
                this.emissor = obj.emissor;
                this.cliente = obj.cliente;
                this.financa = obj.financa;
                
            }
            
             Object readResolve() throws java.io.ObjectStreamException {
                DO_State newState = new DO_State();
                fillInState(newState);
                return newState;
            }
            
            protected void fillInState(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State obj) {
                super.fillInState(obj);
                DO_State state = (DO_State)obj;
                state.iva = this.iva;
                state.numSeq = this.numSeq;
                state.total = this.total;
                state.data = this.data;
                state.serie = this.serie;
                state.emissor = this.emissor;
                state.cliente = this.cliente;
                state.financa = this.financa;
                
            }
            
        }
        
    }
    
}
