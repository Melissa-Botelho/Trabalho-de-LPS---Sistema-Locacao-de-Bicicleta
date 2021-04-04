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
    public static boolean insert(String nome, String modelo, float tamanho, float tempUs, String cor, String tipoP, float valor){
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setNome(nome);
        bicicleta.setModelo(modelo);
        bicicleta.setCor(cor);
        bicicleta.setTipPneu(tipoP);
        bicicleta.setTamanho(tamanho);
        bicicleta.setTempUs(tempUs);
        bicicleta.setVarLocacao(valor);
        BicicletaDAO bicicletaD = new BicicletaDAO();
        return bicicletaD.insert(bicicleta);
    }
    
    public static boolean update(String nome, String modelo, float tamanho, float tempUs, String cor, String tipoP, float valor, int id){
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setNome(nome);
        bicicleta.setModelo(modelo);
        bicicleta.setCor(cor);
        bicicleta.setTipPneu(tipoP);
        bicicleta.setTamanho(tamanho);
        bicicleta.setTempUs(tempUs);
        bicicleta.setVarLocacao(valor);
        bicicleta.setIdBicicleta(id);
        BicicletaDAO bicicletaD = new BicicletaDAO();
        return bicicletaD.update(bicicleta);
    }
    
    public static boolean delete(int id){
        BicicletaDAO bicicletaD = new BicicletaDAO();
        return bicicletaD.delete(id);
    }
    
    public static ArrayList<Object []> select(){
        BicicletaDAO bicicletaD = new BicicletaDAO();
        ArrayList<Bicicleta> lista = bicicletaD.selectAll();
        ArrayList<Object []> bicicletas = new ArrayList<>();
        for(int i=0; i<lista.size(); i++){
            Object [] bicicleta  = new Object[8];
            bicicleta[0] = ((Bicicleta) lista.get(i)).getIdBicicleta();
            bicicleta[1] = ((Bicicleta) lista.get(i)).getNome();
            bicicleta[2] = ((Bicicleta) lista.get(i)).getModelo();
            bicicleta[3] = ((Bicicleta) lista.get(i)).getTamanho();
            bicicleta[4] = ((Bicicleta) lista.get(i)).getTempUs();
            bicicleta[5] = ((Bicicleta) lista.get(i)).getCor();
            bicicleta[6] = ((Bicicleta) lista.get(i)).getTipPneu();
            bicicleta[7] = ((Bicicleta) lista.get(i)).getVarLocacao();
            bicicletas.add(bicicleta);
        }
        return bicicletas; 
    }  
}
