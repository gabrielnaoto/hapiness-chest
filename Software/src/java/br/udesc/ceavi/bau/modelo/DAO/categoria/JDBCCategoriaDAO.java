/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.bau.modelo.DAO.categoria;

import br.udesc.ceavi.bau.modelo.entidade.Categoria;
import br.udesc.ceavi.bau.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String sql = "INSERT INTO public.\"Categoria\"(\n"
                + "            \"categoriaId\", descricao)\n"
                + "    VALUES (?, ?);";
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            stmt.setInt(1, c.getId());
            stmt.setString(2, c.getDescricao());
            stmt.executeUpdate();
            stmt.close();
            Conexao.fechar();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        System.out.println("Categoria inserida com sucesso!");
        return true;
    }

    @Override
    public boolean deletar(int id) {
        PreparedStatement stmt = null;
        String sql = "DELETE FROM public.\"Categoria\"\n"
                + " WHERE \"categoriaId\"=?";
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
        System.out.println("Categoria removida com sucesso!");
        return true;
    }

    @Override
    public boolean atualizar(Categoria c) {
        PreparedStatement stmt = null;
        String sql = "UPDATE public.\"Categoria\"\n"
                + "   SET \"categoriaId\"=?, descricao=?\n"
                + " WHERE \"categoriaId\"=?";
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            stmt.setInt(1, c.getId());
            stmt.setString(2, c.getDescricao());
            stmt.setInt(3, c.getId());
            stmt.executeUpdate();
            stmt.close();
            Conexao.fechar();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        System.out.println("Categoria atualizada com sucesso!");
        return true;
    }

    @Override
    public Categoria pesquisar(int id) {
        PreparedStatement stmt = null;
        String sql = "SELECT \"categoriaId\", descricao\n"
                + "  FROM public.\"Categoria\"\n"
                + " WHERE \"categoriaId\"=?";
        Categoria c = null;
        try {
            stmt = Conexao.getConexao(2).prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            c = new Categoria(rs.getInt(1), rs.getString(2));
            stmt.close();
            Conexao.fechar();

        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);

        }
        System.out.println("Categoria pesquisada com sucesso!");
        return c;

    }

    @Override
    public List<Categoria> listar() {
        PreparedStatement stmt = null;
        String sql = "SELECT \"categoriaId\", descricao\n"
                + "  FROM public.\"Categoria\";\n";
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

}
