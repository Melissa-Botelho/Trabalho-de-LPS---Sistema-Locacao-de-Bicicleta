/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.Cliente;
import model.dao.ClienteDAO;


/**
 *
 * @author melisa
 */
public class ControllerCliente {
    public static boolean insert(Cliente cliente){
        ClienteDAO clienteD = new ClienteDAO();
        return clienteD.insert(cliente);
    }
    
    public static boolean update(Cliente cliente){
        ClienteDAO clienteD = new ClienteDAO();
        return clienteD.update(cliente);
    }
   
    public static boolean delete(int id){
        ClienteDAO clienteD = new ClienteDAO();
        return clienteD.delete(id);
    }
   
    public static ArrayList<Cliente> select(){
        ClienteDAO clienteD = new ClienteDAO();
        return clienteD.selectAll();
    }
    
}
