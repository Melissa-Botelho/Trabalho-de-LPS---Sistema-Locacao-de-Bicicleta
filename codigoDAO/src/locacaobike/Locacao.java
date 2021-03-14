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
    private int IdLocacao;
    private int IdCliente;
    private int IdBicicleta;    
    private float HorarioLoc;
    private Date DataLoc ;
    private float HorarioDev;
    private Date DataD;
    private float ValorLoc;

     public Locacao() {
        this.IdCliente = 0;
        this.IdBicicleta = 0;
        this.HorarioDev = 0;
        this.DataD = null;
        this.HorarioLoc = 0;
        this.DataLoc = null;
        this.ValorLoc = 0;
    }

    
     public String imprimir(){
        String saida = "";
        saida = "         Informações do Médico         \n"+
                "IdCliente: "+this.getIdCliente()+"\n"+
                "IdBicicleta: "+this.getIdBicicleta()+ "\n"+
                "Horario de devolucao: "+this.getHorarioDev()+"\n"+
                "Data Devolucao: "+this.getDataD()+ "\n"+
                "Valor da Locacao: "+this.getValorLoc()+"\n"+
                "Horario da Locacao: "+this.getHorarioLoc()+"\n"+
                "Data da locacao: "+this.getDataLoc()+"\n";
                
        return saida;   
    }

    public int getIdLocacao() {
        return IdLocacao;
    }

    public void setIdLocacao(int IdLocacao) {
        this.IdLocacao = IdLocacao;
    }
     
    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int IdCliente) {
        this.IdCliente = IdCliente;
    }

    public int getIdBicicleta() {
        return IdBicicleta;
    }

    public void setIdBicicleta(int IdBicicleta) {
        this.IdBicicleta = IdBicicleta;
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
}
