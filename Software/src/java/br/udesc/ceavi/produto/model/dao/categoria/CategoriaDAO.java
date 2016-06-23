package br.udesc.ceavi.produto.model.dao.categoria;

import br.udesc.ceavi.produto.model.entidade.Categoria;
import java.util.List;

public interface CategoriaDAO {

    public boolean inserir(Categoria c);

    public boolean deletar(int id);

    public boolean atualizar(Categoria c);

    public Categoria pesquisar(int id);

    public Categoria pesquisar(String descricao);

    public List<Categoria> pesquisarVarios(String query);

    public List<Categoria> listar();
    
    public List<Categoria> listarPodemInserir();

    public int getQuantidade();

    public List<Categoria> getFromCesta(int cestaid);

}
