/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
/**
 *
 * @author melisa
 */
public class Persistencia {
    private static Connection conn = null;
    private Persistencia() {
        try {
            
            /*Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/locacaobike", "root", "");*/
            
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/locacaobike"); //CONFIGURADO DO POOL DO PAYARA
            conn = ds.getConnection();
            
        /*} catch (ClassNotFoundException e) {
            System.err.println("Erro: não foi possível encontrar o driver expecificado "+e);*/
        } catch (SQLException ex) {
            System.err.println("Erro: não foi possível conectar ao banco de dados "+ex);
        } catch (NamingException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//fim construtor
    
    public static Connection getConnection(){
        if(conn == null)
            new Persistencia();
        return conn;
    }
    
}
