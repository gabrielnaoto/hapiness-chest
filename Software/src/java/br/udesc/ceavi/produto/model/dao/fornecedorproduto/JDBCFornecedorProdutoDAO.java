package br.udesc.ceavi.produto.model.dao.fornecedorproduto;

import br.udesc.ceavi.produto.model.entidade.FornecedorProduto;
import br.udesc.ceavi.produto.util.Conexao;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.List;

public class JDBCFornecedorProdutoDAO implements FornecedorProdutoDAO {

    @Override
    public boolean inserir(FornecedorProduto c) {
         PreparedStatement stmt = null;
        String sql = "INSERT INTO produto.fornecedor_produto(\n"
                + "            fornecedor_id, produto_id, valor)\n"
                + "    VALUES (?, ?, ?);";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, c.getUsuario().getId());
            stmt.setInt(2, c.getProduto().getId());
            stmt.setInt(3, c.getValor());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fechar();
        }
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
