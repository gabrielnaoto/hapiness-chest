/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.bau.modelo.DAO;

import br.udesc.ceavi.bau.util.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ignoi
 */
public class NewClass {
    
    public static void main(String[] args) {
        try {
            Connection conexao = Conexao.getConexao(Conexao.POSTGRES);
            Statement comando =  conexao.createStatement();
            ResultSet resultado = comando.executeQuery("select * from tabela");
            while(resultado.next()){
                System.out.println(resultado.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
