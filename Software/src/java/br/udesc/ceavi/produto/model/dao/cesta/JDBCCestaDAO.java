/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.dao.cesta;

import br.udesc.ceavi.produto.model.entidade.Cesta;
import br.udesc.ceavi.produto.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ignoi
 */
public class JDBCCestaDAO implements CestaDAO {

    @Override
    public boolean inserir(Cesta c) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO produto.cesta(\n"
                + "            data, tema, valor_max, peso)\n"
                + "    VALUES (?, ?, ?, ?);";
        try {
            stmt = Conexao.getConexao(Conexao.POSTGRES).prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(c.getData().getTime()));
            stmt.setString(2, c.getTema());
            stmt.setDouble(3, c.getValorMaximo());
            stmt.setInt(4, c.getPeso());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletar(int id) {
        PreparedStatement stmt = null;
        String sql = "DELETE FROM produto.cesta\n"
                + "WHERE id=?";
        try {
            stmt = Conexao.getConexao(Conexao.POSTGRES).prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean atualizar(Cesta c) {
        PreparedStatement stmt = null;
        String sql = "UPDATE produto.cesta\n"
                + "   SET id=?, data=?, tema=?, valor_max=?, peso=?\n"
                + " WHERE id=?;";
        try {
            stmt = Conexao.getConexao(Conexao.POSTGRES).prepareStatement(sql);
            stmt.setInt(1, c.getId());
            stmt.setDate(2, new java.sql.Date(c.getData().getTime()));
            stmt.setString(3, c.getTema());
            stmt.setDouble(4, c.getValorMaximo());
            stmt.setInt(5, c.getPeso());
            stmt.setInt(6, c.getId());
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
    public Cesta pesquisar(int id) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, data, tema, valor_max, peso\n"
                + "  FROM produto.cesta"
                + "WHERE id=?";
        Cesta c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            c = new Cesta(rs.getInt(1), new Date(rs.getDate(2).toString()), rs.getString(3), rs.getDouble(4), rs.getInt(5));
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
    public List<Cesta> listar() {
        PreparedStatement stmt = null;
        String sql = "SELECT id, data, tema, valor_max, peso\n"
                + "  FROM produto.cesta;";
        ArrayList<Cesta> lista = new ArrayList<>();
        Cesta c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                c = new Cesta(rs.getInt(1), new Date(rs.getDate(2).getTime()), rs.getString(3), rs.getDouble(4), rs.getInt(5));
                lista.add(c);
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
