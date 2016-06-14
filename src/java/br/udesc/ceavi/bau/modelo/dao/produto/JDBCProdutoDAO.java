/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.bau.modelo.dao.produto;

import br.udesc.ceavi.bau.modelo.dao.core.Persistencia;
import br.udesc.ceavi.bau.util.Conexao;
import br.udesc.ceavi.cesta.modelo.dao.produto.ProdutoDAO;
import br.udesc.ceavi.cesta.modelo.entidade.Categoria;
import br.udesc.ceavi.cesta.modelo.entidade.Produto;
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
        String sql = "INSERT INTO public.\"Produto\"(\n"
                + "            \"produtoId\", descricao, valor, peso, \"categoriaId\")\n"
                + "    VALUES (?, ?, ?, ?, ?);";
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getDescricao());
            stmt.setDouble(3, p.getValor());
            stmt.setDouble(4, p.getPeso());
            stmt.setInt(5, p.getCategoria().getId());
            stmt.executeUpdate();
            stmt.close();
            Conexao.fechar();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        System.out.println("Produto inserido com sucesso!");
        return true;
    }

    @Override
    public boolean deletar(int id) {
        PreparedStatement stmt = null;
        String sql = "DELETE FROM public.\"Produto\"\n"
                + " WHERE \"produtoId\"=?\n";
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            Conexao.fechar();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        System.out.println("Produto removido com sucesso!");
        return true;
    }

    @Override
    public boolean atualizar(Produto p) {
        PreparedStatement stmt = null;
        String sql = "UPDATE public.\"Produto\"\n"
                + "   SET \"produtoId\"=?, descricao=?, valor=?, peso=?, \"categoriaId\"=?\n"
                + " WHERE \"produtoId\"=?;\n";
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getDescricao());
            stmt.setDouble(3, p.getValor());
            stmt.setDouble(4, p.getPeso());
            stmt.setInt(5, p.getCategoria().getId());
            stmt.executeUpdate();

            stmt.close();
            Conexao.fechar();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        System.out.println("Produto atualizado com sucesso!");
        return true;
    }

    @Override
    public Produto pesquisar(int id) {
        PreparedStatement stmt = null;
        String sql = "SELECT \"produtoId\", descricao, valor, peso, \"categoriaId\"\n"
                + "  FROM public.\"Produto\"\n"
                + " WHERE \"produtoId\" = ?";
        Produto p = null;
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            Categoria c = Persistencia.getPersistencia(Persistencia.JDBC).getCategoriaDAO().pesquisar(rs.getInt(5));
            p = new Produto(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), c);
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);

        }

        System.out.println(
                "Produto pesquisado com sucesso!");
        return p;

    }

    @Override
    public List<Produto> listar() {
        PreparedStatement stmt = null;
        String sql = "SELECT \"produtoId\", descricao, valor, peso, \"categoriaId\"\n"
                + "  FROM public.\"Produto\";";
        ArrayList<Produto> lista = new ArrayList<>();
        Produto p = null;
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria c = Persistencia.getPersistencia(1).getCategoriaDAO().pesquisar(rs.getInt(5));
                p = new Produto(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), c);
                lista.add(p);
            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);

        }
        System.out.println("Produtos listados com sucesso!");
        return lista;

    }

    @Override
    public int getQuantidade() {
        Statement st = null;
        ResultSet rs = null;
        int numRow = 0;
        String sql = "SELECT count(\"produtoId\") as quantProd FROM \"Produto\";";
        try {
            st = Conexao.getConexao(2).createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            numRow = rs.getInt("quantProd");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return numRow;
    }

    @Override
    public List<Produto> listarPorCategoria() {
        PreparedStatement stmt = null;
        String sql = "SELECT p.\"produtoId\",p.\"categoriaId\",p.valor,avg(a.satisfacao) as mediaSatisfacao\n"
                + "  FROM public.avalia as a, public.\"Produto\" as p\n"
                + "  WHERE p.\"produtoId\"= a.\"produtoId\"\n"
                + "  group by p.\"produtoId\",p.valor,p.\"categoriaId\" order by p.\"categoriaId\",p.\"produtoId\";\n"
                + "  ";
        ArrayList<Produto> lista = new ArrayList<>();
        Produto p = null;
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria c = Persistencia.getPersistencia(1).getCategoriaDAO().pesquisar(rs.getInt(2));
                p = new Produto(rs.getInt(1), rs.getDouble(3), rs.getDouble(4), c);
                lista.add(p);
            }
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);

        }
        System.out.println("Produtos listados por categoria com sucesso!");
        return lista;
    }
}
