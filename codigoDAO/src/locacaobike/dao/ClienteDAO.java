package locacaobike.dao;

import conexao.Persistencia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import locacaobike.Cliente;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author melisa
 */
public class ClienteDAO {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    public boolean insert(Cliente cliente){
        String sql = "INSERT INTO cad_cliente (nome, cpf, idade, endereco, telefone, email, sexo) VALUES (?,?,?,?,?,?,?)" ;
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setInt(3, cliente.getIdade());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getTelefone());
            stmt.setString(6, cliente.getEmail());
            stmt.setString(7, ""+cliente.getSexo());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return false;
        }
    }  

    public ArrayList<Cliente> selectAll(){     
        String sql = "SELECT * FROM cad_cliente ";
        ArrayList<Cliente> lista  = new ArrayList<>();
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setIdade(rs.getInt("idade"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setSexo(rs.getString("sexo").toCharArray()[0]);
                lista.add(cliente);
            }
            return lista;
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return null;
        }    
    }//fim selectAll

    public boolean update(Cliente cliente){
        String sql = "UPDATE cad_cliente SET nome = ?, cpf = ?, idade = ?, endereco = ?,  telefone = ?, email = ?, sexo = ? WHERE cad_cliente.idCliente = ?";
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setInt(3, cliente.getIdade());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getTelefone());
            stmt.setString(6, cliente.getEmail());
            stmt.setString(7, ""+cliente.getSexo());            
            stmt.setInt(8, cliente.getIdCliente());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return false;
        }
    }//fim update  
    
    public boolean delete(int id){
        String sql = "DELETE FROM cad_cliente WHERE cad_cliente.idCliente = ?";
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return false;
        }
    }//fim delete  
}
