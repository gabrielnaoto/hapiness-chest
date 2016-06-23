package br.udesc.ceavi.produto.model.dao.fornecedorproduto;

import br.udesc.ceavi.produto.model.entidade.FornecedorProduto;
import java.util.List;

public interface FornecedorProdutoDAO {

    public boolean inserir(FornecedorProduto c);

    public boolean deletar(int id);

    public boolean atualizar(FornecedorProduto c);

    public FornecedorProduto pesquisar(int id);

    public List<FornecedorProduto> listar();

}
