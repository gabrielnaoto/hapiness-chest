/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.dao.categoria;

import br.udesc.ceavi.produto.model.entidade.Categoria;
import java.util.List;

/**
 *
 * @author ignoi
 */
public interface CategoriaDAO {

    public boolean inserir(Categoria c);

    public boolean deletar(int id);

    public boolean atualizar(Categoria c);

    public Categoria pesquisar(int id);

    public List<Categoria> listar();

    public int getQuantidade();

}
