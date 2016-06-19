/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.uc;

import br.udesc.ceavi.core.persistence.Persistence;
import static br.udesc.ceavi.core.persistence.PersistenceType.JDBC;
import br.udesc.ceavi.produto.model.dao.cesta.CestaDAO;
import br.udesc.ceavi.produto.model.entidade.Cesta;
import java.util.List;

/**
 *
 * @author ignoi
 */
public class GerenciarCestaUC {
    
    CestaDAO dao;
    
    public GerenciarCestaUC() {
        dao = Persistence.getPersistence(JDBC).getCestaDAO();
    }

    public boolean criar(Cesta c) {
        return dao.inserir(c);
    }
    
    public List<Cesta> obterCestas(){
        return dao.listar();
    }

}
