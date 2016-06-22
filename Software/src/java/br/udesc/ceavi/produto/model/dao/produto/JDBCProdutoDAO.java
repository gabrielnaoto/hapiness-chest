/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.dao.produto;

import br.udesc.ceavi.core.persistence.Persistence;
import static br.udesc.ceavi.core.persistence.PersistenceType.JDBC;
import br.udesc.ceavi.produto.model.entidade.Produto;
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
public class JDBCProdutoDAO implements ProdutoDAO {

    @Override
    public boolean inserir(Produto p) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO produto.produto(\n"
                + "descricao, valor, peso, categoria_id)\n"
                + "VALUES (?, ?, ?, ?);";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, p.getDescricao());
            stmt.setDouble(2, p.getValor());
            stmt.setDouble(3, p.getPeso());
            stmt.setInt(4, p.getCategoria().getId());
            if (stmt.executeUpdate() > 0) {
                ResultSet result = stmt.getGeneratedKeys();
                if (result.next()) {
                    int chave = result.getInt(1);
                    p.setId(chave);
                }
            }
            stmt.close();
            Conexao.fechar();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletar(int id) {
        PreparedStatement stmt = null;
        String sql = "DELETE FROM produto.produto\n"
                + "WHERE id=?";
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
    public boolean atualizar(Produto p) {
        PreparedStatement stmt = null;
        String sql = "UPDATE produto.produto\n"
                + "   SET id=?, descricao=?, valor=?, peso=?, categoria_id=?\n"
                + " WHERE where id=?;";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getDescricao());
            stmt.setDouble(3, p.getValor());
            stmt.setDouble(4, p.getPeso());
            stmt.setInt(5, p.getCategoria().getId());
            stmt.setInt(6, p.getId());
            stmt.executeUpdate();

            stmt.close();
            Conexao.fechar();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produto pesquisar(int id) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, descricao, valor, peso, categoria_id\n"
                + "FROM produto.produto"
                + "WHERE id=?";
        Produto p = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Categoria c = Persistence.getPersistence(JDBC).getCategoriaDAO().pesquisar(rs.getInt(5));
            p = new Produto(rs.getInt(1), rs.getString(2), (int) rs.getDouble(3), (int) rs.getDouble(4), c);
            stmt.close();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fechar();
        }
    }

    @Override
    public List<Produto> listar() {
        PreparedStatement stmt = null;
        String sql = "SELECT id, descricao, valor, peso, categoria_id\n"
                + "FROM produto.produto";
        ArrayList<Produto> lista = new ArrayList<>();
        Produto p = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Categoria c = Persistence.getPersistence(JDBC).getCategoriaDAO().pesquisar(rs.getInt(5));
                p = new Produto(rs.getInt("id"), rs.getString("descricao"), (int) rs.getDouble("valor"), (int) rs.getDouble("peso"), c);
                lista.add(p);
            }
            stmt.close();
            Conexao.fechar();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getQuantidade() {
        Statement st = null;
        ResultSet rs = null;
        int numRow = 0;
        String sql = "SELECT count(id) as q FROM produto.produto;";
        try {
            st = Conexao.getConexao(1).createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            numRow = rs.getInt("q");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numRow;
    }

    @Override
    public List<Produto> listarPorCategoria() {
        PreparedStatement stmt = null;
        String sql = "SELECT id, categoria_id, valor, avg(satisfacao) as ms"
                + "FROM produto.cliente_produto as a, produto.produto as p"
                + "WHERE p.id = a.id"
                + "GROUP BY p.id, p.valor, p.categoria_id"
                + "ORDER BY p.categoria_id, p.id";
        ArrayList<Produto> lista = new ArrayList<>();
        Produto p = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Categoria c = Persistence.getPersistence(JDBC).getCategoriaDAO().pesquisar(rs.getInt(2));
                p = new Produto(rs.getInt(1), rs.getDouble(3), (int) rs.getDouble(4), c);
                lista.add(p);
            }
            stmt.close();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Conexao.fechar();
        }
    }

    @Override
    public List<Produto> listarPorFornecedor(int id) {
        PreparedStatement stmt = null;
        String sql = "select p.id, descricao, p.valor, p.peso, p.categoria_id\n"
                + "from produto.produto p join produto.fornecedor_produto pf on p.id = pf.produto_id join (\n"
                + "	select id, cesta_id, produto_id, data \n"
                + "	from produto.cesta_produto) as cesta on cesta.produto_id = p.id\n"
                + "where cesta_id = (select id \n"
                + "from produto.cesta\n"
                + "where data is null)\n"
                + "and pf.fornecedor_id = ?";
        ArrayList<Produto> lista = new ArrayList<>();
        Produto p = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Categoria c = Persistence.getPersistence(JDBC).getCategoriaDAO().pesquisar(rs.getInt(5));
                p = new Produto(rs.getInt(1), rs.getString(2), (int) rs.getDouble(3), (int) rs.getDouble(4), c);
                lista.add(p);
            }
            stmt.close();
            Conexao.fechar();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Produto> listarPorCliente(int id) {
        PreparedStatement stmt = null;
        //p.id = 1, p.descricao = 2, valor = 3, peso = 4, c.id = 5, satisfacao = 6
        String sql = "select  produto_id, feaa.descricao, feaa.valor, feaa.peso, feaa.categoria_id, satisfacao\n"
                + "from produto.usuario pu join produto.cliente_produto pcp on pu.id = pcp.cliente_id join produto.produto pp on pp.id = pcp.produto_id join produto.produto feaa on pcp.produto_id = feaa.id\n"
                + "where cliente_id = ?\n"
                + "\n"
                + "UNION\n"
                + "\n"
                + "(\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "select  pp.id as produto_id, descricao, valor, pp.peso, categoria_id, null as satisfacao\n"
                + "from produto.produto pp join produto.cesta_produto pcp on pcp.produto_id = pp.id join produto.cesta pc on pc.id = pcp.cesta_id\n"
                + "where pc.data is null\n"
                + "\n"
                + "and pp.id not in (\n"
                + "\n"
                + "\n"
                + "select  produto_id\n"
                + "from produto.usuario pu join produto.cliente_produto pcp on pu.id = pcp.cliente_id join produto.produto pp on pp.id = pcp.produto_id join produto.produto feaa on pcp.produto_id = feaa.id\n"
                + "where cliente_id = ?\n"
                + "\n"
                + "\n"
                + "\n"
                + "))";
        ArrayList<Produto> lista = new ArrayList<>();
        Produto p = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            //p.id = 1, p.descricao = 2, valor = 3, peso = 4, c.id = 5, satisfacao = 6
            //Produto(int id, String descricao, double peso, double valor, int satisfacao, Categoria categoria
            while (rs.next()) {
                Categoria c = Persistence.getPersistence(JDBC).getCategoriaDAO().pesquisar(rs.getInt(5));
                p = new Produto(rs.getInt(1), rs.getString(2), (int) rs.getDouble(4), (int) rs.getDouble(3), (int) rs.getDouble(6), c);
                lista.add(p);
            }
            stmt.close();
            Conexao.fechar();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
