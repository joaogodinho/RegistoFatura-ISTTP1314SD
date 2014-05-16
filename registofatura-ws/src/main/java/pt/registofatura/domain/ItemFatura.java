package pt.registofatura.domain;

public class ItemFatura extends ItemFatura_Base {

    public ItemFatura() {
        super();
    }

    public ItemFatura(String descricao, int preco, int quantidade) {
        super();
        setDescricao(descricao);
        setPreco(preco);
        setQuantidade(quantidade);
    }
    
    public ItemFatura(pt.registofatura.ws.ItemFatura item) {
        super();
        setDescricao(item.getDescricao());
        setPreco(item.getPreco());
        setQuantidade(item.getQuantidade());
    }
    
}
