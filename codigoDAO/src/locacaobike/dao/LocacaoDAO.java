/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locacaobike.dao;

import conexao.Persistencia;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import locacaobike.Locacao;

/**
 *
 * @author melisa
 */
public class LocacaoDAO {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    public boolean insert(Locacao locacao){
        String sql = "INSERT INTO cad_locacao (idCliente, idBicicleta, horarioL, dataL, horarioD, dataD, valorLoc) VALUES (?, ?, ?, ?, ?, ?, ?);" ;
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setInt(1, locacao.getIdCliente());
            stmt.setInt(2, locacao.getIdBicicleta());
            stmt.setFloat(3, locacao.getHorarioLoc());
            stmt.setDate(4, new Date (locacao.getDataLoc().getTime()));
            stmt.setFloat(5, locacao.getHorarioDev());
            stmt.setDate(6, new Date (locacao.getDataD().getTime()));
            stmt.setFloat(7, locacao.getValorLoc());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return false;
        }
    }
    
    public ArrayList<Locacao> selectAll(){
        String sql = "SELECT * FROM cad_locacao";
        ArrayList<Locacao> lista  = new ArrayList<>();
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                Locacao locacao = new Locacao();
                locacao.setIdLocacao(rs.getInt("idLocacao"));                
                locacao.setIdCliente(rs.getInt("idCliente"));                
                locacao.setIdBicicleta(rs.getInt("idBicicleta"));
                locacao.setHorarioLoc(rs.getFloat("horarioL"));
                locacao.setDataLoc(rs.getDate("dataL"));
                locacao.setHorarioDev(rs.getFloat("horarioD"));
                locacao.setDataD(rs.getDate("dataD"));
                locacao.setValorLoc(rs.getFloat("valorLoc"));
                lista.add(locacao);
            }
            return lista;
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return null;
        }   
    }
    
    public String pegaModelo(int id){
        String sql = "SELECT modelo FROM cad_bicicleta WHERE cad_bicicleta.idBicicleta = ?";
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            return rs.getString("modelo");
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return null;
        }   
    }
    
    public String pegaNome(int id){
        String sql = "SELECT nome FROM cad_cliente WHERE cad_cliente.idCliente = ?";
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            return rs.getString("nome");
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return null;
        }   
    }
    
    public int pegaIdBicicleta(String modelo){
        String sql = "SELECT idBicicleta FROM cad_bicicleta WHERE modelo LIKE ?";
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setString(1, modelo);
            rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("idBicicleta");
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return -1;
        }   
    }
    
    public int pegaIdCliente(String nome){
        String sql = "SELECT idCliente FROM cad_cliente WHERE nome LIKE ?";
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setString(1, nome);
            rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("idCliente");
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return -1;
        }   
    }
    
    public boolean update(Locacao locacao){
        String sql = "UPDATE cad_locacao SET idCliente = ?, IdBicicleta = ?, horarioL = ?, dataL = ?, horarioD = ?, dataD = ? WHERE cad_locacao.idLocacao = ?";
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setInt(1, locacao.getIdCliente());
            stmt.setInt(2, locacao.getIdBicicleta());
            stmt.setFloat(3, locacao.getHorarioLoc());
            stmt.setDate(4, new Date (locacao.getDataLoc().getTime()));
            stmt.setFloat(5, locacao.getHorarioDev());
            stmt.setDate(6, new Date (locacao.getDataD().getTime()));
            stmt.setFloat(7, locacao.getValorLoc());
            stmt.setInt(8, locacao.getIdLocacao());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return false;
        }
    }  
    
    public boolean delete(int id){
        String sql = "DELETE FROM cad_locacao WHERE cad_locacao.idLocacao = ?";
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return false;
        }
    }   
}
