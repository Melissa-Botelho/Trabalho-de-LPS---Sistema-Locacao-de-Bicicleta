/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author melisa
 */

public class Bicicleta {

    private int IdBicicleta;
    private String Nome;
    private String Modelo;
    private float Tamanho;
    private String Cor;
    private String TipPneu;
    private float VarLocacao;
    private float TempUs;

     

    public Bicicleta() {
        this.Nome = "";
        this.Modelo = "";
        this.Tamanho = 0;
        this.Cor = "";
        this.VarLocacao = 0;
        this.TempUs = 0;
        this.TipPneu = "";
    }

    

     public String imprimir(){
        String saida = "";
        saida = "         Informações do Médico         \n"+
                "Nome: "+this.getNome()+"\n"+
                "Modelo: "+this.getModelo()+ "\n"+
                "Tamanho: "+this.getTamanho()+"\n"+
                "Cor: "+this.getCor()+ "\n"+
                "Valor da Locacao: "+this.getVarLocacao()+"\n"+
                "Tempo de Uso: "+this.getTempUs()+"\n"+
                "Tipo de Pneu: "+this.getTipPneu()+"\n";
                
        return saida;   
    }

    public int getIdBicicleta() {
        return IdBicicleta;
    }

    public void setIdBicicleta(int IdBicicleta) {
        this.IdBicicleta = IdBicicleta;
    }
    
    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }
    
    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String Modelo) {
        this.Modelo = Modelo;
    }

    public float getTamanho() {
        return Tamanho;
    }

    public void setTamanho(float Tamanho) {
        this.Tamanho = Tamanho;
    }

    public String getCor() {
        return Cor;
    }

    public void setCor(String Cor) {
        this.Cor = Cor;
    }

    public float getVarLocacao() {
        return VarLocacao;
    }

    public void setVarLocacao(float VarLocacao) {
        this.VarLocacao = VarLocacao;
    }
     public float getTempUs() {
        return TempUs;
    }

    public void setTempUs(float TempUs) {
        this.TempUs = TempUs;
    }

    public String getTipPneu() {
        return TipPneu;
    }

    public void setTipPneu(String TipPneu) {
        this.TipPneu = TipPneu;
    }
}