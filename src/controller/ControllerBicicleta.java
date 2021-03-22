/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.Bicicleta;
import model.dao.BicicletaDAO;

/**
 *
 * @author melisa
 */
public class ControllerBicicleta {
    public static boolean insert(Bicicleta bicicleta){
        BicicletaDAO bicicletaD = new BicicletaDAO();
        return bicicletaD.insert(bicicleta);
    }
    
    public static boolean update(Bicicleta bicicleta){
        BicicletaDAO bicicletaD = new BicicletaDAO();
        return bicicletaD.update(bicicleta);
    }
    
    public static boolean delete(int id){
        BicicletaDAO bicicletaD = new BicicletaDAO();
        return bicicletaD.delete(id);
    }
    
    public static ArrayList<Bicicleta> select(){
        BicicletaDAO bicicletaD = new BicicletaDAO();
        return bicicletaD.selectAll();
    }  
}
