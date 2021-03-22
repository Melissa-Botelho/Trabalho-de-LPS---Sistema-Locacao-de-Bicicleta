/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.Locacao;
import model.dao.LocacaoDAO;

/**
 *
 * @author melisa
 */
public class ControllerLocacao {
    public static boolean insert(Locacao locacao){
        LocacaoDAO locacaoD = new LocacaoDAO();
        if(locacao.getIdCliente() == -1 || locacao.getIdBicicleta() == -1)
            return false;
        return locacaoD.insert(locacao);
    }
    
    public static boolean update(Locacao locacao){
        LocacaoDAO locacaoD = new LocacaoDAO();
        if(locacao.getIdCliente() == -1 || locacao.getIdBicicleta() == -1)
            return false;
        return locacaoD.update(locacao);
    }
    
    public static boolean delete(int id){
        LocacaoDAO locacaoD = new LocacaoDAO();
        return locacaoD.delete(id);
    }
    
    public static ArrayList<Locacao> select(){
        LocacaoDAO locacaoD = new LocacaoDAO();
        return locacaoD.selectAll();
    
    }
    
    public static String pegaNomeOuModelo(int id, char tipo){
        LocacaoDAO locacaoD = new LocacaoDAO();
        if(tipo == 'B')
            return locacaoD.pegaModelo(id);
        else
            return locacaoD.pegaNome(id);
    }
    
    public static int pegaId(String s, char tipo){
        LocacaoDAO locacaoD = new LocacaoDAO();
        if(tipo == 'B')
            return locacaoD.pegaIdBicicleta(s);
        else
            return locacaoD.pegaIdCliente(s);
    }
}
