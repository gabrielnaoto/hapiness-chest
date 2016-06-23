package br.udesc.ceavi.produto.model.dao.cesta;

import br.udesc.ceavi.produto.model.entidade.Cesta;
import java.util.List;

public interface CestaDAO {

    public boolean inserir(Cesta c);

    public boolean deletar(int id);

    public boolean atualizar(Cesta c);

    public Cesta pesquisar(int id);

    public List<Cesta> listar();
    
    public List<Cesta> listarFinalizadas();
    
    public Cesta getAtual();

}
