/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.cesta.modelo.dao.produto;

import br.udesc.ceavi.cesta.modelo.entidade.Produto;
import java.util.List;

/**
 *
 * @author ignoi
 */
public interface ProdutoDAO {

    public boolean inserir(Produto p);

    public boolean deletar(int id);

    public boolean atualizar(Produto p);

    public Produto pesquisar(int id);

    public List<Produto> listar();

    public int getQuantidade();

    public List<Produto> listarPorCategoria();

}
