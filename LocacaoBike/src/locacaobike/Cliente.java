/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locacaobike;

/**
 *
 * @author melisa
 */

public class Cliente extends Pessoa{
    private String email;

    public Cliente() {
        this.email = "";
    }
    public String imprimir(){
        String saida = "";
        saida = "         Informações do Cliente         \n"+
                "Nome: "+this.getNome()+"\n"+
                "CPF: "+this.getCpf() + "\n"+
                "Idade: "+this.getIdade() +"\n"+
                "Endereço: "+this.getEndereco() + "\n"+
                "Sexo: "+this.getSexo() +"\n"+
                "Telefone: "+this.getTelefone()+"\n"+
                "Email: "+this.email+"\n";
        return saida;   
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  
    @Override
    public void setSexo(char Sexo) {
        super.setSexo(Sexo); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public char getSexo() {
        return super.getSexo(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setEndereco(String endereco) {
        super.setEndereco(endereco); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEndereco() {
        return super.getEndereco(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setIdade(int idade) {
        super.setIdade(idade); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getIdade() {
        return super.getIdade(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCpf(String cpf) {
        super.setCpf(cpf); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCpf() {
        return super.getCpf(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNome() {
        return super.getNome(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTelefone(String telefone) {
        super.setTelefone(telefone); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTelefone() {
        return super.getTelefone(); //To change body of generated methods, choose Tools | Templates.
    }
    
}

