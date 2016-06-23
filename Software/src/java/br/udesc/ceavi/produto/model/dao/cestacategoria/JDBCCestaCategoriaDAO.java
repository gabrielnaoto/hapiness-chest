package br.udesc.ceavi.produto.model.dao.cestacategoria;

import br.udesc.ceavi.core.persistence.Persistence;
import static br.udesc.ceavi.core.persistence.PersistenceType.JDBC;
import br.udesc.ceavi.produto.model.entidade.Categoria;
import br.udesc.ceavi.produto.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import br.udesc.ceavi.produto.model.entidade.Cesta;
import br.udesc.ceavi.produto.model.entidade.CestaCategoria;
import java.util.Date;

public class JDBCCestaCategoriaDAO implements CestaCategoriaDAO {

    @Override
    public boolean inserir(CestaCategoria c) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO produto.cesta_categoria(\n"
                + "             cesta_id, categoria_id, data)\n"
                + "    VALUES (?, ?, ?);";
        try {
            c.setData(new Date());//pega sempre a data atual 
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, c.getCesta().getId());
            stmt.setInt(2, c.getCategoria().getId());
            stmt.setDate(3, new java.sql.Date(c.getData().getTime()));
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
        String sql = "DELETE FROM produto.cesta_categoria\n"
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
    public boolean atualizar(CestaCategoria c) {
        PreparedStatement stmt = null;
        String sql = "UPDATE produto.cesta_categoria\n"
                + "   SET id=?, cesta_id=?, categoria_id=?\n"
                + " WHERE id = ?;";
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, c.getId());
            stmt.setInt(2, c.getCesta().getId());
            stmt.setInt(3, c.getCategoria().getId());
            stmt.setInt(4, c.getId());
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
    public CestaCategoria pesquisar(int id) {
        PreparedStatement stmt = null;
        String sql = "SELECT id, cesta_id, categoria_id\n"
                + "  FROM produto.cesta_produto"
                + "WHERE id = ?;";
        CestaCategoria c = null;
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Cesta cesta = Persistence.getPersistence(JDBC).getCestaDAO().pesquisar(rs.getInt(2));
            Categoria categoria = Persistence.getPersistence(JDBC).getCategoriaDAO().pesquisar(rs.getInt(3));
            c = new CestaCategoria(rs.getInt(1), cesta, categoria);
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
    public List<CestaCategoria> listar() {
        PreparedStatement stmt = null;
        String sql = "SELECT id, cesta_id, categoria_id, data\n"
                + "  FROM produto.cesta_categoria;";
        ArrayList<CestaCategoria> lista = new ArrayList<>();
        try {
            stmt = Conexao.getConexao(1).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            CestaCategoria cat = null;
            while (rs.next()) {
                Cesta cesta = Persistence.getPersistence(JDBC).getCestaDAO().pesquisar(rs.getInt(2));
                Categoria categoria = Persistence.getPersistence(JDBC).getCategoriaDAO().pesquisar(rs.getInt(3));
                cat = new CestaCategoria(rs.getInt(1), cesta, categoria);
                lista.add(cat);
            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);

        }
        System.out.println("CestaCategorias listadas com sucesso!");
        return lista;

    }

}
