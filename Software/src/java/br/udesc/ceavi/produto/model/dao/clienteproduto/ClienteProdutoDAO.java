/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.dao.clienteproduto;

import br.udesc.ceavi.produto.model.entidade.ClienteProduto;
import java.util.List;

/**
 *
 * @author ignoi
 */
public interface ClienteProdutoDAO {

    public boolean inserir(ClienteProduto c);

    public boolean deletar(int id);

    public boolean atualizar(ClienteProduto c);

    public ClienteProduto pesquisar(int id);

    public List<ClienteProduto> listar();

}
