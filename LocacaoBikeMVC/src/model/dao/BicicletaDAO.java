package model.dao;

import conexao.Persistencia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Bicicleta;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author melisa
 */
public class BicicletaDAO {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    public boolean insert(Bicicleta bicicleta){
        String sql = "INSERT INTO cad_bicicleta (nome, modelo, tamanho, tempUs, cor, tipoP, valorLoc) VALUES (?, ?, ?, ?, ?, ?, ?)" ;
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setString(1, bicicleta.getNome());
            stmt.setString(2, bicicleta.getModelo());
            stmt.setFloat(3, bicicleta.getTamanho());
            stmt.setFloat(4, bicicleta.getTempUs());
            stmt.setString(5, bicicleta.getCor());
            stmt.setString(6, bicicleta.getTipPneu());
            stmt.setFloat(7, bicicleta.getVarLocacao());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return false;
        }
    }
 
    public ArrayList<Bicicleta> selectAll(){
        String sql = "SELECT * FROM cad_bicicleta";
        ArrayList<Bicicleta> lista  = new ArrayList<>();
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                Bicicleta bicicleta = new Bicicleta();
                bicicleta.setIdBicicleta(rs.getInt("idBicicleta"));                
                bicicleta.setNome(rs.getString("nome"));                
                bicicleta.setModelo(rs.getString("modelo"));
                bicicleta.setTamanho(rs.getInt("tamanho"));
                bicicleta.setTempUs(rs.getInt("tempUs"));
                bicicleta.setCor(rs.getString("cor"));
                bicicleta.setTipPneu(rs.getString("tipoP"));
                bicicleta.setVarLocacao(rs.getFloat("valorLoc"));
                lista.add(bicicleta);
            }
            return lista;
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return null;
        }   
    }
    
    public boolean update(Bicicleta bicicleta){
        String sql = "UPDATE cad_bicicleta SET nome = ?, modelo = ?, tamanho = ?, tempUs = ?, cor = ?, tipoP = ?, valorLoc = ? WHERE cad_bicicleta.idBicicleta = ?";
        try {
            stmt = Persistencia.getConnection().prepareStatement(sql);
            stmt.setString(1, bicicleta.getNome());
            stmt.setString(2, bicicleta.getModelo());
            stmt.setFloat(3, bicicleta.getTamanho());
            stmt.setFloat(4, bicicleta.getTempUs());
            stmt.setString(5, bicicleta.getCor());
            stmt.setString(6, bicicleta.getTipPneu());
            stmt.setFloat(7, bicicleta.getVarLocacao());
            stmt.setInt(8, bicicleta.getIdBicicleta());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex);
            return false;
        }
    }  
    
    public boolean delete(int id){
        String sql = "DELETE FROM cad_bicicleta WHERE cad_bicicleta.idBicicleta = ?";
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
    

