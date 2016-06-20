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
                + "            descricao)\n"
                + "    VALUES (?);";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getDescricao());
            if (stmt.executeUpdate() > 0) {
                ResultSet result = stmt.getGeneratedKeys();
                if (result.next()) {
                    int chave = result.getInt(1);
                    c.setId(chave);
                }
            } else {
                throw new Exception("Usuário não inserido");
            }
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
            stmt = Conexao.getConexao(1).prepareStatement(sql);
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
            stmt = Conexao.getConexao(1).prepareStatement(sql);
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
                + " WHERE descricao = ?;";
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
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
    public Categoria pesquisar(String descricao) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, descricao\n"
                + "  FROM produto.categoria\n"
                + "WHERE descricao = ?;";
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setString(1, descricao);
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
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            Categoria cat = null;
            while (rs.next()) {
                cat = new Categoria(rs.getInt("categoria_id"), rs.getString("descricao"));
                lista.add(cat);

            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);
            ;

        }
        System.out.println("Categorias listadas com sucesso!");
        return lista;

    }

    @Override
    public List<Categoria> getFromCesta(int cestaid) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, descricao\n"
                + "FROM produto.categoria\n"
                + "WHERE id IN (\n"
                + "SELECT categoria_id\n"
                + "FROM produto.cesta c JOIN produto.cesta_categoria cc ON c.id = cc.cesta_id\n"
                + "WHERE c.id = ?)";
        ArrayList<Categoria> lista = new ArrayList<>();
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, cestaid);
            ResultSet rs = stmt.executeQuery();
            Categoria cat = null;
            while (rs.next()) {
                cat = new Categoria(rs.getInt("id"), rs.getString("descricao"));
                lista.add(cat);
            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);
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
            st = Conexao.getConexao(1).createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            numCol = rs.getInt("quantCat");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numCol;
    }

}
