package br.udesc.ceavi.produto.model.dao.cestacategoria;

import java.util.List;
import br.udesc.ceavi.produto.model.entidade.CestaCategoria;

public interface CestaCategoriaDAO {

    public boolean inserir(CestaCategoria c);

    public boolean deletar(int id);

    public boolean atualizar(CestaCategoria c);

    public CestaCategoria pesquisar(int id);

    public List<CestaCategoria> listar();


}
