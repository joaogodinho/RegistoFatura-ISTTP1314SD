package pt.registofatura.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.pstm.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class ItemFatura_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.ItemFatura,pt.registofatura.domain.Fatura> role$$fatura = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.ItemFatura,pt.registofatura.domain.Fatura>() {
        public pt.registofatura.domain.Fatura getValue(pt.registofatura.domain.ItemFatura o1) {
            return ((ItemFatura_Base.DO_State)o1.get$obj$state(false)).fatura;
        }
        public void setValue(pt.registofatura.domain.ItemFatura o1, pt.registofatura.domain.Fatura o2) {
            ((ItemFatura_Base.DO_State)o1.get$obj$state(true)).fatura = o2;
        }
        public dml.runtime.Role<pt.registofatura.domain.Fatura,pt.registofatura.domain.ItemFatura> getInverseRole() {
            return pt.registofatura.domain.Fatura.role$$item;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.ItemFatura,pt.registofatura.domain.Fatura> FaturaTemItens = new pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.ItemFatura,pt.registofatura.domain.Fatura>(role$$fatura);
    static {
        pt.registofatura.domain.Fatura.FaturaTemItens = FaturaTemItens.getInverseRelation();
    }
    
    static {
        FaturaTemItens.setRelationName("pt.registofatura.domain.ItemFatura.FaturaTemItens");
    }
    
    
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  ItemFatura_Base() {
        super();
    }
    
    public java.lang.String getDescricao() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "descricao");
        return ((DO_State)this.get$obj$state(false)).descricao;
    }
    
    public void setDescricao(java.lang.String descricao) {
        ((DO_State)this.get$obj$state(true)).descricao = descricao;
    }
    
    private java.lang.String get$descricao() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).descricao;
        return (value == null) ? null : pt.ist.fenixframework.pstm.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$descricao(java.lang.String arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).descricao = (java.lang.String)((arg0 == null) ? null : arg0);
    }
    
    public int getPreco() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "preco");
        return ((DO_State)this.get$obj$state(false)).preco;
    }
    
    public void setPreco(int preco) {
        ((DO_State)this.get$obj$state(true)).preco = preco;
    }
    
    private int get$preco() {
        int value = ((DO_State)this.get$obj$state(false)).preco;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$preco(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).preco = (int)(arg0);
    }
    
    public int getQuantidade() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "quantidade");
        return ((DO_State)this.get$obj$state(false)).quantidade;
    }
    
    public void setQuantidade(int quantidade) {
        ((DO_State)this.get$obj$state(true)).quantidade = quantidade;
    }
    
    private int get$quantidade() {
        int value = ((DO_State)this.get$obj$state(false)).quantidade;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$quantidade(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).quantidade = (int)(arg0);
    }
    
    public pt.registofatura.domain.Fatura getFatura() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "fatura");
        return ((DO_State)this.get$obj$state(false)).fatura;
    }
    
    public void setFatura(pt.registofatura.domain.Fatura fatura) {
        FaturaTemItens.add((pt.registofatura.domain.ItemFatura)this, fatura);
    }
    
    public boolean hasFatura() {
        return (getFatura() != null);
    }
    
    public void removeFatura() {
        setFatura(null);
    }
    
    private java.lang.Long get$oidFatura() {
        pt.ist.fenixframework.pstm.AbstractDomainObject value = ((DO_State)this.get$obj$state(false)).fatura;
        return (value == null) ? null : value.getOid();
    }
    
    protected void checkDisconnected() {
        if (hasFatura()) handleAttemptToDeleteConnectedObject();
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$descricao(pt.ist.fenixframework.pstm.ResultSetReader.readString(rs, "DESCRICAO"), state);
        set$preco(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "PRECO"), state);
        set$quantidade(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "QUANTIDADE"), state);
        castedState.fatura = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_FATURA");
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private java.lang.String descricao;
        private int preco;
        private int quantidade;
        private pt.registofatura.domain.Fatura fatura;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.descricao = this.descricao;
            newCasted.preco = this.preco;
            newCasted.quantidade = this.quantidade;
            newCasted.fatura = this.fatura;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private java.lang.String descricao;
            private int preco;
            private int quantidade;
            private pt.registofatura.domain.Fatura fatura;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.descricao = obj.descricao;
                this.preco = obj.preco;
                this.quantidade = obj.quantidade;
                this.fatura = obj.fatura;
                
            }
            
             Object readResolve() throws java.io.ObjectStreamException {
                DO_State newState = new DO_State();
                fillInState(newState);
                return newState;
            }
            
            protected void fillInState(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State obj) {
                super.fillInState(obj);
                DO_State state = (DO_State)obj;
                state.descricao = this.descricao;
                state.preco = this.preco;
                state.quantidade = this.quantidade;
                state.fatura = this.fatura;
                
            }
            
        }
        
    }
    
}
