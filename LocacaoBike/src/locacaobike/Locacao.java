/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locacaobike;

import java.util.Date;

/**
 *
 * @author melisa
 */
public class Locacao {
    private String Nome;
    private String Modelo;
    private float HorarioDev;
    private Date DataD;
    private float HorarioLoc;
    private Date DataLoc ;

     public Locacao() {
        this.Nome = "";
        this.Modelo = "";
        this.HorarioDev = 0;
        this.DataD = null;
        this.HorarioLoc = 0;
        this.DataLoc = null;
        this.ValorLoc = 0;
    }

    
     public String imprimir(){
        String saida = "";
        saida = "         Informações do Médico         \n"+
                "Nome: "+this.getNome()+"\n"+
                "Modelo: "+this.getModelo()+ "\n"+
                "Horario de devolucao: "+this.getHorarioDev()+"\n"+
                "Data Devolucao: "+this.getDataD()+ "\n"+
                "Valor da Locacao: "+this.getValorLoc()+"\n"+
                "Horario da Locacao: "+this.getHorarioLoc()+"\n"+
                "Data da locacao: "+this.getDataLoc()+"\n";
                
        return saida;   
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

    public float getHorarioDev() {
        return HorarioDev;
    }

    public void setHorarioDev(float HorarioDev) {
        this.HorarioDev = HorarioDev;
    }

    public Date getDataD() {
        return DataD;
    }

    public void setDataD(Date DataD) {
        this.DataD = DataD;
    }

    public float getHorarioLoc() {
        return HorarioLoc;
    }

    public void setHorarioLoc(float HorarioLoc) {
        this.HorarioLoc = HorarioLoc;
    }

    public Date getDataLoc() {
        return DataLoc;
    }

    public void setDataLoc(Date DataLoc) {
        this.DataLoc = DataLoc;
    }

    public float getValorLoc() {
        return ValorLoc;
    }

    public void setValorLoc(float ValorLoc) {
        this.ValorLoc = ValorLoc;
    }
    private float ValorLoc;

   
    
}
