/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.dao.categoria;

import br.udesc.ceavi.produto.util.Conexao;
import br.udesc.ceavi.produto.model.entidade.Categoria;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sila Siebert
 */
public class JDBCCategoriaDAO implements CategoriaDAO {

    @Override
    public boolean inserir(Categoria c) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO produto.categoria(\n"
                + "            id, descricao)\n"
                + "    VALUES (?, ?);";
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            stmt.setInt(1, c.getId());
            stmt.setString(2, c.getDescricao());
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
        PreparedStatement stmt = null;
        String sql = "DELETE FROM produto.categoria\n"
                + " WHERE id = ?;";
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fechar();
        }
    }

    @Override
    public boolean atualizar(Categoria c) {
        PreparedStatement stmt = null;
        String sql = "UPDATE produto.categoria\n"
                + "   SET id=?, descricao=?\n"
                + " WHERE id = ?;";
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            stmt.setInt(1, c.getId());
            stmt.setString(2, c.getDescricao());
            stmt.setInt(3, c.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fechar();
        }
    }

    @Override
    public Categoria pesquisar(int id) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, descricao\n"
                + "  FROM produto.categoria"
                + "WHERE id = ?;";
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            c = new Categoria(rs.getInt(1), rs.getString(2));
            stmt.close();
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fechar();
        }

    }

    @Override
    public List<Categoria> listar() {
        PreparedStatement stmt = null;
        String sql = "SELECT id, descricao\n"
                + "  FROM produto.categoria;";
        ArrayList<Categoria> lista = new ArrayList<>();
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            Categoria cat = null;
            while (rs.next()) {
                cat = new Categoria(rs.getInt("categoriaId"), rs.getString("descricao"));
                lista.add(cat);

            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);

        }
        System.out.println("Categorias listadas com sucesso!");
        return lista;

    }

    @Override
    public int getQuantidade() {
        Statement st = null;
        ResultSet rs = null;
        int numCol = 0;
        String sql = "SELECT count(id) as quantCat FROM produto.categoria;";
        try {
            st = Conexao.getConexao(2).createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            numCol = rs.getInt("quantCat");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numCol;
    }

}
