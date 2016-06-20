/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.dao.cesta;

import br.udesc.ceavi.produto.model.entidade.Cesta;
import java.util.List;

/**
 *
 * @author ignoi
 */
public interface CestaDAO {

    public boolean inserir(Cesta c);

    public boolean deletar(int id);

    public boolean atualizar(Cesta c);

    public Cesta pesquisar(int id);

    public List<Cesta> listar();
    
    public List<Cesta> listarFinalizadas();
    
    public Cesta getAtual();

}
