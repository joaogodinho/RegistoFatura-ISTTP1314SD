package pt.registofatura.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.pstm.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class Entidade_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public final static dml.runtime.RoleMany<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura> role$$faturaEmissor = new dml.runtime.RoleMany<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura>() {
        public dml.runtime.RelationBaseSet<pt.registofatura.domain.Fatura> getSet(pt.registofatura.domain.Entidade o1) {
            return ((Entidade_Base)o1).get$rl$faturaEmissor();
        }
        public dml.runtime.Role<pt.registofatura.domain.Fatura,pt.registofatura.domain.Entidade> getInverseRole() {
            return pt.registofatura.domain.Fatura.role$$emissor;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Entidade,pt.registofatura.domain.Financa> role$$financa = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.registofatura.domain.Entidade,pt.registofatura.domain.Financa>() {
        public pt.registofatura.domain.Financa getValue(pt.registofatura.domain.Entidade o1) {
            return ((Entidade_Base.DO_State)o1.get$obj$state(false)).financa;
        }
        public void setValue(pt.registofatura.domain.Entidade o1, pt.registofatura.domain.Financa o2) {
            ((Entidade_Base.DO_State)o1.get$obj$state(true)).financa = o2;
        }
        public dml.runtime.Role<pt.registofatura.domain.Financa,pt.registofatura.domain.Entidade> getInverseRole() {
            return pt.registofatura.domain.Financa.role$$entidade;
        }
        
    };
    public final static dml.runtime.RoleMany<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura> role$$faturaCliente = new dml.runtime.RoleMany<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura>() {
        public dml.runtime.RelationBaseSet<pt.registofatura.domain.Fatura> getSet(pt.registofatura.domain.Entidade o1) {
            return ((Entidade_Base)o1).get$rl$faturaCliente();
        }
        public dml.runtime.Role<pt.registofatura.domain.Fatura,pt.registofatura.domain.Entidade> getInverseRole() {
            return pt.registofatura.domain.Fatura.role$$cliente;
        }
        
    };
    public final static dml.runtime.RoleMany<pt.registofatura.domain.Entidade,pt.registofatura.domain.Serie> role$$serie = new dml.runtime.RoleMany<pt.registofatura.domain.Entidade,pt.registofatura.domain.Serie>() {
        public dml.runtime.RelationBaseSet<pt.registofatura.domain.Serie> getSet(pt.registofatura.domain.Entidade o1) {
            return ((Entidade_Base)o1).get$rl$serie();
        }
        public dml.runtime.Role<pt.registofatura.domain.Serie,pt.registofatura.domain.Entidade> getInverseRole() {
            return pt.registofatura.domain.Serie.role$$emissor;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura> FaturaTemEmissor = new pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura>(role$$faturaEmissor);
    static {
        pt.registofatura.domain.Fatura.FaturaTemEmissor = FaturaTemEmissor.getInverseRelation();
    }
    
    static {
        FaturaTemEmissor.setRelationName("pt.registofatura.domain.Entidade.FaturaTemEmissor");
    }
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Entidade,pt.registofatura.domain.Financa> FinancaTemEntidades = new pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Entidade,pt.registofatura.domain.Financa>(role$$financa);
    static {
        pt.registofatura.domain.Financa.FinancaTemEntidades = FinancaTemEntidades.getInverseRelation();
    }
    
    static {
        FinancaTemEntidades.setRelationName("pt.registofatura.domain.Entidade.FinancaTemEntidades");
    }
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura> FaturaTemCliente = new pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura>(role$$faturaCliente);
    static {
        pt.registofatura.domain.Fatura.FaturaTemCliente = FaturaTemCliente.getInverseRelation();
    }
    
    static {
        FaturaTemCliente.setRelationName("pt.registofatura.domain.Entidade.FaturaTemCliente");
    }
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Entidade,pt.registofatura.domain.Serie> SerieTemEmissor = new pt.ist.fenixframework.pstm.LoggingRelation<pt.registofatura.domain.Entidade,pt.registofatura.domain.Serie>(role$$serie);
    static {
        pt.registofatura.domain.Serie.SerieTemEmissor = SerieTemEmissor.getInverseRelation();
    }
    
    static {
        SerieTemEmissor.setRelationName("pt.registofatura.domain.Entidade.SerieTemEmissor");
    }
    
    
    private RelationList<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura> get$rl$faturaEmissor() {
        return get$$relationList("faturaEmissor", FaturaTemEmissor);
        
    }
    private RelationList<pt.registofatura.domain.Entidade,pt.registofatura.domain.Fatura> get$rl$faturaCliente() {
        return get$$relationList("faturaCliente", FaturaTemCliente);
        
    }
    private RelationList<pt.registofatura.domain.Entidade,pt.registofatura.domain.Serie> get$rl$serie() {
        return get$$relationList("serie", SerieTemEmissor);
        
    }
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  Entidade_Base() {
        super();
    }
    
    public int getNif() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "nif");
        return ((DO_State)this.get$obj$state(false)).nif;
    }
    
    public void setNif(int nif) {
        ((DO_State)this.get$obj$state(true)).nif = nif;
    }
    
    private int get$nif() {
        int value = ((DO_State)this.get$obj$state(false)).nif;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$nif(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).nif = (int)(arg0);
    }
    
    public java.lang.String getNome() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "nome");
        return ((DO_State)this.get$obj$state(false)).nome;
    }
    
    public void setNome(java.lang.String nome) {
        ((DO_State)this.get$obj$state(true)).nome = nome;
    }
    
    private java.lang.String get$nome() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).nome;
        return (value == null) ? null : pt.ist.fenixframework.pstm.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$nome(java.lang.String arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).nome = (java.lang.String)((arg0 == null) ? null : arg0);
    }
    
    public int getFaturaEmissorCount() {
        return get$rl$faturaEmissor().size();
    }
    
    public boolean hasAnyFaturaEmissor() {
        return (! get$rl$faturaEmissor().isEmpty());
    }
    
    public boolean hasFaturaEmissor(pt.registofatura.domain.Fatura faturaEmissor) {
        return get$rl$faturaEmissor().contains(faturaEmissor);
    }
    
    public java.util.Set<pt.registofatura.domain.Fatura> getFaturaEmissorSet() {
        return get$rl$faturaEmissor();
    }
    
    public void addFaturaEmissor(pt.registofatura.domain.Fatura faturaEmissor) {
        FaturaTemEmissor.add((pt.registofatura.domain.Entidade)this, faturaEmissor);
    }
    
    public void removeFaturaEmissor(pt.registofatura.domain.Fatura faturaEmissor) {
        FaturaTemEmissor.remove((pt.registofatura.domain.Entidade)this, faturaEmissor);
    }
    
    public java.util.List<pt.registofatura.domain.Fatura> getFaturaEmissor() {
        return get$rl$faturaEmissor();
    }
    
    public void set$faturaEmissor(OJBFunctionalSetWrapper faturaEmissor) {
        get$rl$faturaEmissor().setFromOJB(this, "faturaEmissor", faturaEmissor);
    }
    
    public java.util.Iterator<pt.registofatura.domain.Fatura> getFaturaEmissorIterator() {
        return get$rl$faturaEmissor().iterator();
    }
    
    public pt.registofatura.domain.Financa getFinanca() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "financa");
        return ((DO_State)this.get$obj$state(false)).financa;
    }
    
    public void setFinanca(pt.registofatura.domain.Financa financa) {
        FinancaTemEntidades.add((pt.registofatura.domain.Entidade)this, financa);
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
    
    public int getFaturaClienteCount() {
        return get$rl$faturaCliente().size();
    }
    
    public boolean hasAnyFaturaCliente() {
        return (! get$rl$faturaCliente().isEmpty());
    }
    
    public boolean hasFaturaCliente(pt.registofatura.domain.Fatura faturaCliente) {
        return get$rl$faturaCliente().contains(faturaCliente);
    }
    
    public java.util.Set<pt.registofatura.domain.Fatura> getFaturaClienteSet() {
        return get$rl$faturaCliente();
    }
    
    public void addFaturaCliente(pt.registofatura.domain.Fatura faturaCliente) {
        FaturaTemCliente.add((pt.registofatura.domain.Entidade)this, faturaCliente);
    }
    
    public void removeFaturaCliente(pt.registofatura.domain.Fatura faturaCliente) {
        FaturaTemCliente.remove((pt.registofatura.domain.Entidade)this, faturaCliente);
    }
    
    public java.util.List<pt.registofatura.domain.Fatura> getFaturaCliente() {
        return get$rl$faturaCliente();
    }
    
    public void set$faturaCliente(OJBFunctionalSetWrapper faturaCliente) {
        get$rl$faturaCliente().setFromOJB(this, "faturaCliente", faturaCliente);
    }
    
    public java.util.Iterator<pt.registofatura.domain.Fatura> getFaturaClienteIterator() {
        return get$rl$faturaCliente().iterator();
    }
    
    public int getSerieCount() {
        return get$rl$serie().size();
    }
    
    public boolean hasAnySerie() {
        return (! get$rl$serie().isEmpty());
    }
    
    public boolean hasSerie(pt.registofatura.domain.Serie serie) {
        return get$rl$serie().contains(serie);
    }
    
    public java.util.Set<pt.registofatura.domain.Serie> getSerieSet() {
        return get$rl$serie();
    }
    
    public void addSerie(pt.registofatura.domain.Serie serie) {
        SerieTemEmissor.add((pt.registofatura.domain.Entidade)this, serie);
    }
    
    public void removeSerie(pt.registofatura.domain.Serie serie) {
        SerieTemEmissor.remove((pt.registofatura.domain.Entidade)this, serie);
    }
    
    public java.util.List<pt.registofatura.domain.Serie> getSerie() {
        return get$rl$serie();
    }
    
    public void set$serie(OJBFunctionalSetWrapper serie) {
        get$rl$serie().setFromOJB(this, "serie", serie);
    }
    
    public java.util.Iterator<pt.registofatura.domain.Serie> getSerieIterator() {
        return get$rl$serie().iterator();
    }
    
    protected void checkDisconnected() {
        if (hasAnyFaturaEmissor()) handleAttemptToDeleteConnectedObject();
        if (hasFinanca()) handleAttemptToDeleteConnectedObject();
        if (hasAnyFaturaCliente()) handleAttemptToDeleteConnectedObject();
        if (hasAnySerie()) handleAttemptToDeleteConnectedObject();
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$nif(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "NIF"), state);
        set$nome(pt.ist.fenixframework.pstm.ResultSetReader.readString(rs, "NOME"), state);
        castedState.financa = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_FINANCA");
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("faturaEmissor")) return FaturaTemEmissor;
        if (attrName.equals("faturaCliente")) return FaturaTemCliente;
        if (attrName.equals("serie")) return SerieTemEmissor;
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("faturaEmissor", FaturaTemEmissor);
        get$$relationList("faturaCliente", FaturaTemCliente);
        get$$relationList("serie", SerieTemEmissor);
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private int nif;
        private java.lang.String nome;
        private pt.registofatura.domain.Financa financa;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.nif = this.nif;
            newCasted.nome = this.nome;
            newCasted.financa = this.financa;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private int nif;
            private java.lang.String nome;
            private pt.registofatura.domain.Financa financa;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.nif = obj.nif;
                this.nome = obj.nome;
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
                state.nif = this.nif;
                state.nome = this.nome;
                state.financa = this.financa;
                
            }
            
        }
        
    }
    
}
