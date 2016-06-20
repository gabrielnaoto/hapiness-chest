/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.dao.cestacategoria;

import java.util.List;
import br.udesc.ceavi.produto.model.entidade.CestaCategoria;

/**
 *
 * @author ignoi
 */
public interface CestaCategoriaDAO {

    public boolean inserir(CestaCategoria c);

    public boolean deletar(int id);

    public boolean atualizar(CestaCategoria c);

    public CestaCategoria pesquisar(int id);

    public List<CestaCategoria> listar();


}
