/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.dao.cestaproduto;

import java.util.List;
import br.udesc.ceavi.produto.model.entidade.CestaProduto;

/**
 *
 * @author ignoi
 */
public interface CestaProdutoDAO {

    public boolean inserir(CestaProduto c);

    public boolean deletar(int id);

    public boolean atualizar(CestaProduto c);

    public CestaProduto pesquisar(int id);

    public List<CestaProduto> listar();


}
