package pt.registofatura.domain;

import java.util.ArrayList;
import java.util.List;

public class Financa extends Financa_Base {

    public Financa() {
        super();
    }

    public Entidade getEntidadeByNif(int nif) {
        for(Entidade e : getEntidade()) {
            if(e.getNif() == nif) {
                return e;
            }
        }
        return null;
    }
    
    public Serie getSerieByNum(int numero) {
        for(Serie s : getSerie()) {
            if(s.getNumero() == numero) {
                return s;
            }
        }
        return null;
    }
    
    public List<Fatura> getFaturasFromEmissor(int nif) {
        List<Fatura> faturas = new ArrayList<Fatura>();
        for(Fatura f : getFatura()) {
            if(f.getEmissor().getNif() == nif) {
                faturas.add(f);
            }
        }
        return faturas;
    }
    
    public List<Fatura> getFaturasFromClient(int nif) {
        List<Fatura> faturas = new ArrayList<Fatura>();
        for(Fatura f : getFatura()) {
            if(f.getCliente().getNif() == nif) {
                faturas.add(f);
            }
        }
        return faturas;
    }
    
    public List<Fatura> getFaturasBetweenEmissorAndCliente(int nifEmissor, int nifCliente) {
        List<Fatura> faturas = new ArrayList<Fatura>();
        for(Fatura f : getFaturasFromEmissor(nifEmissor)) {
            if(f.getCliente().getNif() == nifCliente) {
                faturas.add(f);
            }
        }
        return faturas;
    }
}
