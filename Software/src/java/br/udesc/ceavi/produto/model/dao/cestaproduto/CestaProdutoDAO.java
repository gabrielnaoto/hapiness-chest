package br.udesc.ceavi.produto.model.dao.cestaproduto;

import java.util.List;
import br.udesc.ceavi.produto.model.entidade.CestaProduto;

public interface CestaProdutoDAO {

    public boolean inserir(CestaProduto c);

    public boolean deletar(int id);

    public boolean atualizar(CestaProduto c);

    public CestaProduto pesquisar(int id);

    public List<CestaProduto> listar();


}
