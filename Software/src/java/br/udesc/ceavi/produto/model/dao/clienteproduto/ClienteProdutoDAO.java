package br.udesc.ceavi.produto.model.dao.clienteproduto;

import br.udesc.ceavi.produto.model.entidade.ClienteProduto;
import java.util.List;

public interface ClienteProdutoDAO {

    public boolean inserir(ClienteProduto c);

    public boolean deletar(int id);

    public boolean atualizar(ClienteProduto c);

    public ClienteProduto pesquisar(int id);

    public List<ClienteProduto> listar();

}
