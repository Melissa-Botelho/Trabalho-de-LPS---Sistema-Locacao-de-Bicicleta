/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Calendar;

/**
 *
 * @author melisa
 */
public class Utils {
   
    public static boolean verificaData(String data){
        Calendar cal = Calendar.getInstance();
        int ano, mes, dia;
        dia = Integer.parseInt(data.substring(0,2));
        mes = Integer.parseInt(data.substring(3,5));
        ano = Integer.parseInt(data.substring(6,10));
        if(ano < cal.get(Calendar.YEAR))
            return false;
        if(mes > 12 || mes < 1)
            return false;
        if((mes == 2 && (ano % 400 == 0)) && dia > 29)
            return false;
        if((mes == 2 && (ano % 400 != 0)) && dia > 28)
            return false;
        if(((mes < 8 && mes%2 == 0) || (mes > 7 && mes%2 != 0)) && dia > 30)
            return false;
        return(dia > 0 || dia < 32);
    }//fim verifica data
    
    public static boolean verificaDataLocDev(String loc, String dev){
       int anoLoc = Integer.parseInt(loc.substring(6,10));
       int diaLoc = Integer.parseInt(loc.substring(0,2));
       int mesLoc = Integer.parseInt(loc.substring(3,5));
       int anoDev = Integer.parseInt(dev.substring(6,10));
       int diaDev = Integer.parseInt(dev.substring(0,2));
       int mesDev = Integer.parseInt(dev.substring(3,5));
       if(anoLoc > anoDev)
           return false;
       if(anoLoc == anoDev && mesLoc > mesDev)
           return false;
       return !(mesLoc == mesDev && diaLoc > diaDev);
    }
    
    public static boolean verificaHora(float hora){
        if(hora < 0 || hora > 23.59)
            return false;
        int h = (int) hora;
        hora = hora - h;
        return !(hora < 0.0 || hora > 0.60);
    }
    
    public static boolean verificarCPF(String cpf) {
        int calc1 = 0, calc2 = 0, aux1 = 10, aux2 = 11;
        int[] arrayCPF;
        boolean repetido = true;
        arrayCPF = new int[9];
        int dig1 = Integer.parseInt(cpf.substring(12, 13));
        int dig2 = Integer.parseInt(cpf.substring(13, 14));
        cpf = cpf.substring(0, 3) + cpf.substring(4, 7) + cpf.substring(8, 11);
        for (int i = 0; i < arrayCPF.length; i++) {
            arrayCPF[i] = Integer.parseInt(cpf.substring(i, i + 1));

            calc1 += aux1 * arrayCPF[i];
            aux1--;
            calc2 += aux2 * arrayCPF[i];
            aux2--;

            if (arrayCPF[0] != arrayCPF[i] && repetido) {
                repetido = false;
            }
        }
        calc2 += dig1 * aux2;

        calc1 = (calc1 * 10) % 11;
        calc2 = (calc2 * 10) % 11;

        if (calc1 == 10) {
            calc1 = 0;
        }

        if (calc2 == 10) {
            calc2 = 0;
        }

        return (calc1 == dig1 && calc2 == dig2 && !repetido);
    }//fim função verifica CPF
}
