package pt.registofatura.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.pstm.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class Financa_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public final static dml.runtime.RoleMany<pt.registofatura.domain.Financa,pt.registofatura.domain.Entidade> role$$entidade = new dml.runtime.RoleMany<pt.registofatura.domain.Financa,pt.registofatura.domain.Entidade>() {
        public dml.runtime.RelationBaseSet<pt.registofatura.domain.Entidade> getSet(pt.registofatura.domain.Financa o1) {
            return ((Financa_Base)o1).get$rl$entidade();
        }
        public dml.runtime.Role<pt.registofatura.domain.Entidade,pt.registofatura.domain.Financa> getInverseRole() {
            return pt.registofatura.domain.Entidade.role$$financa;
        }
        
    };
    public final static dml.runtime.RoleMany<pt.registofatura.domain.Financa,pt.registofatura.domain.Serie> role$$serie = new dml.runtime.RoleMany<pt.registofatura.domain.Financa,pt.registofatura.domain.Serie>() {
        public dml.runtime.RelationBaseSet<pt.registofatura.domain.Serie> getSet(pt.registofatura.domain.Financa o1) {
            return ((Financa_Base)o1).get$rl$serie();
        }
        public dml.runtime.Role<pt.registofatura.domain.Serie,pt.registofatura.domain.Financa> getInverseRole() {
            return pt.registofatura.domain.Serie.role$$financa;
        }
        
    };
    public final static dml.runtime.RoleMany<pt.registofatura.domain.Financa,pt.registofatura.domain.Fatura> role$$fatura = new dml.runtime.RoleMany<pt.registofatura.domain.Financa,pt.registofatura.domain.Fatura>() {
        public dml.runtime.RelationBaseSet<pt.registofatura.domain.Fatura> getSet(pt.registofatura.domain.Financa o1) {
            return ((Financa_Base)o1).get$rl$fatura();
        }
        public dml.runtime.Role<pt.registofatura.domain.Fatura,pt.registofatura.domain.Financa> getInverseRole() {
            return pt.registofatura.domain.Fatura.role$$financa;
        }
        
    };
    public static dml.runtime.Relation<pt.registofatura.domain.Financa,pt.registofatura.domain.Entidade> FinancaTemEntidades;
    public static dml.runtime.Relation<pt.registofatura.domain.Financa,pt.registofatura.domain.Serie> FinancaTemSeries;
    public static dml.runtime.Relation<pt.registofatura.domain.Financa,pt.registofatura.domain.Fatura> FinancaTemFaturas;
    
    
    private RelationList<pt.registofatura.domain.Financa,pt.registofatura.domain.Entidade> get$rl$entidade() {
        return get$$relationList("entidade", FinancaTemEntidades);
        
    }
    private RelationList<pt.registofatura.domain.Financa,pt.registofatura.domain.Serie> get$rl$serie() {
        return get$$relationList("serie", FinancaTemSeries);
        
    }
    private RelationList<pt.registofatura.domain.Financa,pt.registofatura.domain.Fatura> get$rl$fatura() {
        return get$$relationList("fatura", FinancaTemFaturas);
        
    }
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  Financa_Base() {
        super();
    }
    
    public int getUltimoNumSerie() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "ultimoNumSerie");
        return ((DO_State)this.get$obj$state(false)).ultimoNumSerie;
    }
    
    public void setUltimoNumSerie(int ultimoNumSerie) {
        ((DO_State)this.get$obj$state(true)).ultimoNumSerie = ultimoNumSerie;
    }
    
    private int get$ultimoNumSerie() {
        int value = ((DO_State)this.get$obj$state(false)).ultimoNumSerie;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$ultimoNumSerie(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).ultimoNumSerie = (int)(arg0);
    }
    
    public int getEntidadeCount() {
        return get$rl$entidade().size();
    }
    
    public boolean hasAnyEntidade() {
        return (! get$rl$entidade().isEmpty());
    }
    
    public boolean hasEntidade(pt.registofatura.domain.Entidade entidade) {
        return get$rl$entidade().contains(entidade);
    }
    
    public java.util.Set<pt.registofatura.domain.Entidade> getEntidadeSet() {
        return get$rl$entidade();
    }
    
    public void addEntidade(pt.registofatura.domain.Entidade entidade) {
        FinancaTemEntidades.add((pt.registofatura.domain.Financa)this, entidade);
    }
    
    public void removeEntidade(pt.registofatura.domain.Entidade entidade) {
        FinancaTemEntidades.remove((pt.registofatura.domain.Financa)this, entidade);
    }
    
    public java.util.List<pt.registofatura.domain.Entidade> getEntidade() {
        return get$rl$entidade();
    }
    
    public void set$entidade(OJBFunctionalSetWrapper entidade) {
        get$rl$entidade().setFromOJB(this, "entidade", entidade);
    }
    
    public java.util.Iterator<pt.registofatura.domain.Entidade> getEntidadeIterator() {
        return get$rl$entidade().iterator();
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
        FinancaTemSeries.add((pt.registofatura.domain.Financa)this, serie);
    }
    
    public void removeSerie(pt.registofatura.domain.Serie serie) {
        FinancaTemSeries.remove((pt.registofatura.domain.Financa)this, serie);
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
        FinancaTemFaturas.add((pt.registofatura.domain.Financa)this, fatura);
    }
    
    public void removeFatura(pt.registofatura.domain.Fatura fatura) {
        FinancaTemFaturas.remove((pt.registofatura.domain.Financa)this, fatura);
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
    
    protected void checkDisconnected() {
        if (hasAnyEntidade()) handleAttemptToDeleteConnectedObject();
        if (hasAnySerie()) handleAttemptToDeleteConnectedObject();
        if (hasAnyFatura()) handleAttemptToDeleteConnectedObject();
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$ultimoNumSerie(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "ULTIMO_NUM_SERIE"), state);
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("entidade")) return FinancaTemEntidades;
        if (attrName.equals("serie")) return FinancaTemSeries;
        if (attrName.equals("fatura")) return FinancaTemFaturas;
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("entidade", FinancaTemEntidades);
        get$$relationList("serie", FinancaTemSeries);
        get$$relationList("fatura", FinancaTemFaturas);
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private int ultimoNumSerie;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.ultimoNumSerie = this.ultimoNumSerie;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private int ultimoNumSerie;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.ultimoNumSerie = obj.ultimoNumSerie;
                
            }
            
             Object readResolve() throws java.io.ObjectStreamException {
                DO_State newState = new DO_State();
                fillInState(newState);
                return newState;
            }
            
            protected void fillInState(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State obj) {
                super.fillInState(obj);
                DO_State state = (DO_State)obj;
                state.ultimoNumSerie = this.ultimoNumSerie;
                
            }
            
        }
        
    }
    
}
