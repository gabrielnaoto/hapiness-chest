package br.udesc.ceavi.caixeiro.model.dao;

import br.udesc.ceavi.caixeiro.model.Fornecedor;
import br.udesc.ceavi.core.persistence.Persistible;

/**
 *
 * @author Samuel Felício Adriano
 */
public interface iDaoFornecedor extends Persistible<Fornecedor> {

    public boolean isNomeCadastrado(String nome);

}