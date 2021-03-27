/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Date;
import model.Locacao;
import model.dao.LocacaoDAO;

/**
 *
 * @author melisa
 */
public class ControllerLocacao {
    public static boolean insert(int idC, int idB, float horarioL, Date dataL, float horarioD, Date dataD, float valor){
        LocacaoDAO locacaoD = new LocacaoDAO();
        Locacao locacao = new Locacao();
        locacao.setIdCliente(idC);
        locacao.setIdLocacao(idB);
        locacao.setHorarioLoc(horarioL);
        locacao.setDataLoc(dataL);
        locacao.setHorarioDev(horarioD);
        locacao.setDataD(dataD);
        locacao.setValorLoc(valor);
        if(locacao.getIdCliente() == -1 || locacao.getIdLocacao() == -1)
            return false;
        return locacaoD.insert(locacao);
    }
    
    public static boolean update(int idC, int idB, float horarioL, Date dataL, float horarioD, Date dataD, float valor, int id){
        LocacaoDAO locacaoD = new LocacaoDAO();
        Locacao locacao = new Locacao();
        locacao.setIdCliente(idC);
        locacao.setIdLocacao(idB);
        locacao.setHorarioLoc(horarioL);
        locacao.setDataLoc(dataL);
        locacao.setHorarioDev(horarioD);
        locacao.setDataD(dataD);
        locacao.setValorLoc(valor);
        if(locacao.getIdCliente() == -1 || locacao.getIdLocacao() == -1)
            return false;
        return locacaoD.update(locacao);
    }
    
    public static boolean delete(int id){
        LocacaoDAO locacaoD = new LocacaoDAO();
        return locacaoD.delete(id);
    }
    
    public static ArrayList<Object []> select(){
        LocacaoDAO locacaoD = new LocacaoDAO();
        ArrayList<Locacao> lista = locacaoD.selectAll();
        ArrayList<Object []> locacoes =  new ArrayList<>();
        for(int i=0; i<lista.size(); i++){
            Object [] locacao  = new Object[8];
            locacao[0] = ((Locacao) lista.get(i)).getIdLocacao();
            locacao[1] = ((Locacao) lista.get(i)).getIdCliente();
            locacao[2] = ((Locacao) lista.get(i)).getIdBicicleta();
            locacao[3] = ((Locacao) lista.get(i)).getHorarioLoc();
            locacao[4] = ((Locacao) lista.get(i)).getDataLoc();
            locacao[5] = ((Locacao) lista.get(i)).getHorarioDev();
            locacao[6] = ((Locacao) lista.get(i)).getDataD();
            locacao[7] = ((Locacao) lista.get(i)).getValorLoc();
            locacoes.add(locacao);
        }
        return locacoes; 
    
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
