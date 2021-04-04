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
    
    public static boolean insert(String nome, String cpf,int idade,String endereco, String telefone, String email,char sexo){
        ClienteDAO clienteD = new ClienteDAO();
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setIdade(idade);
        cliente.setEndereco(endereco);
        cliente.setEmail(email);
        cliente.setSexo(sexo);
        return clienteD.insert(cliente);
    }
    
    public static boolean update(String nome, String cpf,int idade,String endereco, String telefone, String email,char sexo, int id){
        ClienteDAO clienteD = new ClienteDAO();
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setIdade(idade);
        cliente.setEndereco(endereco);
        cliente.setEmail(email);
        cliente.setSexo(sexo);
        cliente.setIdCliente(id);
        return clienteD.update(cliente);
    }
   
    public static boolean delete(int id){
        ClienteDAO clienteD = new ClienteDAO();
        return clienteD.delete(id);
    }

    public static ArrayList<Object []> select(){
        ClienteDAO clienteD = new ClienteDAO();
        ArrayList<Cliente> lista = clienteD.selectAll();
        ArrayList<Object []> clientes = new ArrayList<>();
        for(int i=0; i<lista.size(); i++){
            Object [] cliente  = new Object[8];
            cliente[0] = ((Cliente) lista.get(i)).getIdCliente();
            cliente[1] = ((Cliente) lista.get(i)).getNome();
            cliente[2] = ((Cliente) lista.get(i)).getCpf();
            cliente[3] = ((Cliente) lista.get(i)).getIdade();
            cliente[4] = ((Cliente) lista.get(i)).getEndereco();
            cliente[5] = ((Cliente) lista.get(i)).getTelefone();
            cliente[6] = ((Cliente) lista.get(i)).getEmail();
            cliente[7] = ((Cliente) lista.get(i)).getSexo();
            clientes.add(cliente);
        }
        return clientes; 
    }
    
}
