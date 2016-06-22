/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.dao.fornecedorproduto;

import br.udesc.ceavi.produto.model.entidade.FornecedorProduto;
import br.udesc.ceavi.produto.util.Conexao;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author Sila Siebert
 */
public class JDBCFornecedorProdutoDAO implements FornecedorProdutoDAO {

    @Override
    public boolean inserir(FornecedorProduto c) {
//         PreparedStatement stmt = null;
//        String sql = "INSERT INTO produto.cesta_produto(\n"
//                + "            id, cesta_id, produto_id, data)\n"
//                + "    VALUES (?, ?, ?, ?);";
//        try {
//            stmt = Conexao.getConexao(1).prepareStatement(sql);
//            stmt.setInt(1, c.getId());
//            stmt.setInt(2, c.getCesta().getId());
//            stmt.setInt(3, c.getProduto().getId());
//            stmt.setDate(4, new java.sql.Date(c.getCesta().getData().getTime()));
//            stmt.executeUpdate();
//            stmt.close();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            Conexao.fechar();
//        }
        return false;
    }

    @Override
    public boolean deletar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizar(FornecedorProduto c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FornecedorProduto pesquisar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FornecedorProduto> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   


}
