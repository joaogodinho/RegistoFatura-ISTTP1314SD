package pt.registofatura.domain;

public class Entidade extends Entidade_Base {

    public Entidade() {
        super();
    }

    public Entidade(String name, int nif) {
        super();
        this.setNome(name);
        this.setNif(nif);
    }

}
