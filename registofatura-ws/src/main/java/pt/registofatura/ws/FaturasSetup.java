package pt.registofatura.ws;

import jvstm.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.registofatura.domain.Entidade;
import pt.registofatura.domain.Financa;
import pt.registofatura.ws.impl.FenixFrameworkSetup;

public class FaturasSetup {

    public static void main(String[] args) {
        FaturasSetup setup = new FaturasSetup();
        setup.populateDomain();
    }
    
    public FaturasSetup() {
        FenixFrameworkSetup.setup();
    }
    
    /**
     * Populates the domain.
     */
    public void populateDomain() {
        try {
            setUltimoNumSerie(1);
            
            addEntidade("alice", 1001);
            addEntidade("bruno", 1002);
            addEntidade("carlos", 1003);
            
            addEntidade("xpto", 5001);
            addEntidade("yez", 5002);
            addEntidade("zleep", 5003);
            
            addEntidade("zeze", 1111);
            addEntidade("mariazinha", 2222);
            
            addEntidade("mng", 3333);
            addEntidade("pp", 4444);
            
            addEntidade("portal", 1234);
            
            addEntidade("bc", 5111);
            addEntidade("bf", 5222);
        }
        catch (Exception e) {
            System.out.println("Ocorreu um erro a popular a BD: " + e);
            e.printStackTrace();
        }
    }

    @Atomic
    private void setUltimoNumSerie(int start) {
        Financa financa = FenixFramework.getRoot();
        financa.setUltimoNumSerie(start);
    }

    @Atomic
    private void addEntidade(String name, int nif) {
        Financa financa = FenixFramework.getRoot();
        Entidade entidade = new Entidade(name, nif); 
        financa.addEntidade(entidade);
    }
}
