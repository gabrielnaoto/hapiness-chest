/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.fornecedorproduto;

import br.udesc.ceavi.produto.model.entidade.FornecedorProduto;
import java.util.List;

/**
 *
 * @author ignoi
 */
public interface FornecedorProdutoDAO {

    public boolean inserir(FornecedorProduto c);

    public boolean deletar(int id);

    public boolean atualizar(FornecedorProduto c);

    public FornecedorProduto pesquisar(int id);

    public List<FornecedorProduto> listar();

}
