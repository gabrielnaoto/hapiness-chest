package br.udesc.ceavi.produto.model.dao.produto;

import br.udesc.ceavi.produto.model.entidade.Produto;
import java.util.List;

public interface ProdutoDAO {

    public boolean inserir(Produto p);

    public boolean deletar(int id);

    public boolean atualizar(Produto p);

    public Produto pesquisar(int id);

    public List<Produto> listar();

    public int getQuantidade();

    public List<Produto> listarPorCategoria();

    public List<Produto> listarPorFornecedor(int id);

    public List<Produto> listarPorCliente(int id);

}
