package br.udesc.ceavi.caixeiro.model.dao;

import br.udesc.ceavi.caixeiro.model.RelacionamentoEndereco;
import br.udesc.ceavi.core.persistence.Persistible;
import java.sql.ResultSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ricardo Augusto Küstner
 */
public interface iDaoRelacionamentoEndereco extends Persistible<RelacionamentoEndereco> {

    public ResultSet getAllEnderecoEntrega();

}
