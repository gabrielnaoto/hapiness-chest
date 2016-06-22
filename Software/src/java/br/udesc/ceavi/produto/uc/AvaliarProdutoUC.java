/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.uc;

import br.udesc.ceavi.caixeiro.model.Usuario;
import br.udesc.ceavi.core.persistence.Persistence;
import br.udesc.ceavi.core.persistence.PersistenceType;
import br.udesc.ceavi.produto.model.dao.cesta.CestaDAO;
import br.udesc.ceavi.produto.model.dao.clienteproduto.ClienteProdutoDAO;
import br.udesc.ceavi.produto.model.dao.produto.ProdutoDAO;
import br.udesc.ceavi.produto.model.entidade.ClienteProduto;
import br.udesc.ceavi.produto.model.entidade.Produto;
import java.util.List;

/**
 *- Clientes podem avaliar produtos, fornecedores so podem se forem tambem clientes, caso contrario, nao.

- O cliente pode avaliar o produto quantas vezes for necessário (desde que esteja dentro do prazo) e apenas a ultima avaliaçao ficara salva.

- Os produtos devem entrar para avaliaçao assim que forem lançados pelos fornecedores.
 * @author ignoi
 */
public class AvaliarProdutoUC {
    
   private ProdutoDAO pdao;
   private CestaDAO cdao;
   private ClienteProdutoDAO cpdao;

    public AvaliarProdutoUC() {
        pdao = Persistence.getPersistence(PersistenceType.JDBC).getProdutoDAO();
        cdao = Persistence.getPersistence(PersistenceType.JDBC).getCestaDAO();
        cpdao = Persistence.getPersistence(PersistenceType.JDBC).getClienteProdutoDAO();
    }
    
    public List<Produto> obterProdutos(int id){
        return pdao.listarPorCliente(id);
    }
    
    public boolean avaliar(Produto p, Usuario u, int satisfacao){
        ClienteProduto cp = new ClienteProduto(u, p, satisfacao);
        return cpdao.inserir(cp);
    }
    
}
