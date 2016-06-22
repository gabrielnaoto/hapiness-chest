/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.dao.clienteproduto;

import br.udesc.ceavi.caixeiro.model.Cliente;
import br.udesc.ceavi.caixeiro.model.Usuario;
import br.udesc.ceavi.caixeiro.model.dao.iDaoCliente;
import br.udesc.ceavi.caixeiro.model.dao.iDaoUsuario;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import br.udesc.ceavi.core.persistence.Persistence;
import static br.udesc.ceavi.core.persistence.PersistenceType.JDBC;
import br.udesc.ceavi.produto.model.entidade.ClienteProduto;
import br.udesc.ceavi.produto.model.entidade.Produto;
import br.udesc.ceavi.produto.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sila Siebert
 */
public class JDBCClienteProdutoDAO implements ClienteProdutoDAO {

    @Override
    public boolean inserir(ClienteProduto c) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO produto.cliente_produto(\n"
                + "            cliente_id, produto_id, satisfacao)\n"
                + "    VALUES (?, ?, ?);";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, c.getUsuario().getId());
            stmt.setInt(2, c.getProduto().getId());
            stmt.setInt(3, c.getSatisfacao());
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
        String sql = "DELETE FROM produto.cliente_produto\n"
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
    public boolean atualizar(ClienteProduto c) {
        PreparedStatement stmt = null;
        String sql = "UPDATE produto.cliente_produto\n"
                + "   SET id=?, cliente_id=?, produto_id=?, satisfacao=?\n"
                + " WHERE id = ?;";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, c.getId());
            stmt.setInt(2, c.getUsuario().getId());
            stmt.setInt(3, c.getProduto().getId());
            stmt.setInt(4, c.getSatisfacao());
            stmt.setInt(5, c.getId());
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
    public ClienteProduto pesquisar(int id) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, cliente_id, produto_id, satisfacao\n"
                + "  FROM produto.cliente_produto"
                + "WHERE id = ?;";
        ClienteProduto c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            iDaoUsuario dao = JDBCFactory.getDaoUsuario();
            Usuario rc = new Usuario();
            rc.setId(rs.getInt(2));
            Usuario usuario = dao.findOne(rc);
            Produto produto = Persistence.getPersistence(JDBC).getProdutoDAO().pesquisar(rs.getInt(3));
            c = new ClienteProduto(rs.getInt(1), usuario, produto, rs.getInt(4));
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
    public List<ClienteProduto> listar() {
        PreparedStatement stmt = null;
        String sql = "SELECT id, cesta_id, produto_id, data\n"
                + "  FROM produto.cesta_produto;";
        ArrayList<ClienteProduto> lista = new ArrayList<>();
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ClienteProduto cat = null;
            while (rs.next()) {
                iDaoUsuario dao = JDBCFactory.getDaoUsuario();
                Usuario rc = new Usuario();
                rc.setId(rs.getInt(2));
                Usuario usuario = dao.findOne(rc);
                Produto produto = Persistence.getPersistence(JDBC).getProdutoDAO().pesquisar(rs.getInt(3));
                cat = new ClienteProduto(rs.getInt(1), usuario, produto, rs.getInt(4));
                lista.add(cat);
            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);

        }
        System.out.println("ClienteProdutos listadas com sucesso!");
        return lista;

    }

}
