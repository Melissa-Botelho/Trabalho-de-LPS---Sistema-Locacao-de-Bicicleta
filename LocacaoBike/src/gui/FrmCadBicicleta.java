/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import locacaobike.Bicicleta;
import conexao.Conexaoo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author melisa
 */
public class FrmCadBicicleta extends javax.swing.JFrame {
private ArrayList<Bicicleta> arrayBicicleta = new ArrayList();
Bicicleta bicicleta = new Bicicleta();
char tipoEdicao;

    PreparedStatement ps =  null;
    ResultSet resultado = null;
    
    public FrmCadBicicleta() {
        initComponents();
        //camposFormatado();
    }
    public void alteraCliente(){
        String modelo;
        if(tblBike.getSelectedRow()!= -1){
           modelo = (String) tblBike.getValueAt(tblBike.getSelectedRow(), 0);
           bicicleta = buscarBicicleta(modelo);
           preencheFormulario();
        }else
           JOptionPane.showMessageDialog(rootPane, "Selecione algum bicicleta");
    }
    public Bicicleta buscarBicicleta (String modelo){
        for (int i =0; i<arrayBicicleta.size(); i++){
           if(arrayBicicleta.get(i).getModelo()== modelo){
               return arrayBicicleta.get(i);
           } 
        }
        return null;
    }
     public void preencheFormulario(){
        edtTam.setText(String.valueOf(bicicleta.getTamanho()));
        edtNome.setText(bicicleta.getNome());
        edtModelo.setText(bicicleta.getModelo());
        edtTemp.setText(String.valueOf(bicicleta.getTempUs()));
        edtCor.setText(bicicleta.getCor());
        edtTipo.setText(bicicleta.getTipPneu()); 
        edtLoca.setText(String.valueOf(bicicleta.getVarLocacao()));
        
         
     }
    
    public void salvarBicicleta(){
        try {
        if(tipoEdicao == 'N'){    
            bicicleta = new Bicicleta();
            bicicleta.setModelo(edtModelo.getText());
        }
        
        bicicleta.setNome(edtNome.getText());
        bicicleta.setCor(edtCor.getText());
        bicicleta.setTipPneu(edtTipo.getText());
        bicicleta.setTamanho(Integer.valueOf(edtTam.getText()));
        bicicleta.setTempUs(Integer.valueOf(edtTemp.getText()));
        bicicleta.setVarLocacao(Integer.valueOf(edtLoca.getText()));
        
        
        
        
        if(tipoEdicao == 'N'){
            
            ps = Conexaoo.conexao().prepareStatement("INSERT INTO `locacaobike´.`cad_bicicleta` `nome`, `modelo`, `tamanho`, `tempUs`, `cor`, `tipoP`, `valorLoc` VALUES(?, ?, ?, ?, ?,?,?)"); 


            ps.setString(1, bicicleta.getNome());
            ps.setString(2, bicicleta.getModelo());
            ps.setString(3, String.valueOf(bicicleta.getTamanho()));
            ps.setString(4, String.valueOf(bicicleta.getTempUs()));
            ps.setString(5, bicicleta.getCor());
            ps.setString(6, bicicleta.getTipPneu());
            ps.setString(7, String.valueOf(bicicleta.getVarLocacao()));
           
            
            ps.executeUpdate();
        }
        else if(tipoEdicao == 'E'){
            ps = Conexaoo.conexao().prepareStatement("UPDATE `locacaobike`.`cad_bicicleta` SET (nome=?,modelo=?, tamanho=?, tempUs=?, cor=?,tipoP=?, valorLoc=?) "); 


            ps.setString(1, bicicleta.getNome());
            ps.setString(2, bicicleta.getModelo());
            ps.setString(3, String.valueOf(bicicleta.getTamanho()));
            ps.setString(4, String.valueOf(bicicleta.getTempUs()));
            ps.setString(5, bicicleta.getCor());
            ps.setString(6, bicicleta.getTipPneu());
            ps.setString(7, String.valueOf(bicicleta.getVarLocacao()));
            
            ps.executeUpdate();
        }
            
        }catch (Exception ex) {
            Logger.getLogger(FrmCadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
   
      public void carregarBicicleta(){
        String [] colunas = {"Nome", "Modelo", "Tamanho", "Tempo de Uso", "Valor da Locacao","Cor","Tipo do Pneu"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        
        try {
            ps = Conexaoo.conexao().prepareStatement("SELECT * FROM locacaobike.cad_bicicleta");
            resultado = ps.executeQuery();      
        
        while (resultado.next()){
            bicicleta = new Bicicleta();
               bicicleta.setNome(resultado.getString("nomeC"));
               bicicleta.setModelo(resultado.getString("modeloB"));
               bicicleta.setTamanho(resultado.getInt("tamanho"));
               bicicleta.setTempUs(resultado.getInt("tempUs"));
               bicicleta.setCor(resultado.getString("cor"));
               bicicleta.setTipPneu(resultado.getString("tipoP"));
               bicicleta.setVarLocacao(resultado.getFloat("valorLoc"));
            
            arrayBicicleta.add(bicicleta);
            
            Object [] linha  = {resultado.getString("nome"),resultado.getString("modelo"),(resultado.getString("tamanho")),resultado.getString("tempUs"),resultado.getString("cor"),resultado.getString("tipoP"),resultado.getString("valorLoc")};
            modelo.addRow(linha);
        }
        tblBike.setModel(modelo);
        
        } catch (SQLException ex) {
            Logger.getLogger(FrmCadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void limparDados(){
        edtModelo.setText("");
        edtNome.setText("");
        edtTam.setText("");
        edtTemp.setText("");
        edtCor.setText("");
        edtTipo.setText("");
        edtLoca.setText("");
    }

    public boolean validacaoCampos(){
        
        if(edtTam.getText().replace(" ", "").matches("[0-9]")){
            JOptionPane.showMessageDialog(rootPane,"Preencha o Tamanho corretamente (somente numeros)");
            edtTam.requestFocus();
            return false;
        }
        if(edtNome.getText().replace(" ", "").matches("[A-Za-z]")){
            JOptionPane.showMessageDialog(rootPane,"Preencha o Nome corretamente (somente as letras sem acentos)");
            edtNome.requestFocus();
            return false;
        }
        if(edtModelo.getText().replace(" ", "").matches("[A-Za-z]")){
            JOptionPane.showMessageDialog(rootPane,"Preencha o Modelo corretamente (somente as letras sem acentos)");
            edtModelo.requestFocus();
            return false;
        }
        if(edtTemp.getText().replace(" ", "").matches("[0-9]")){
            JOptionPane.showMessageDialog(rootPane,"Preencha o Tempo de Uso corretamente (somente numeros)");
            edtTemp.requestFocus();
            return false;
        }
        if(edtCor.getText().replace(" ", "").matches("[A-Za-z]")){
            JOptionPane.showMessageDialog(rootPane,"Preencha a Cor corretamente (somente as letras sem acentos)");
            edtCor.requestFocus();
            return false;
        }
        if(edtTipo.getText().replace(" ", "").matches("[A-Za-z]")){
            JOptionPane.showMessageDialog(rootPane,"Preencha o Tipo de Pneu corretamente (somente as letras sem acentos)");
            edtTipo.requestFocus();
            return false;
        }
        if(edtLoca.getText().replace(" ", "").matches("[0-9]")){
            JOptionPane.showMessageDialog(rootPane,"Preencha o Valor da Locacao corretamente (somente numeros)");
            edtLoca.requestFocus();
            return false;
        }
       
        return true;
    }
    
    public void ativaForm(boolean ativaDesativa){
        
        for(int i=0; i<pnlForm.getComponents().length; i++){
            pnlForm.getComponent(i).setEnabled(ativaDesativa);
        }
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalv = new javax.swing.JButton();
        pnlForm = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        edtNome = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        edtModelo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        edtTam = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        edtTemp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        edtCor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        edtTipo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        edtLoca = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBike = new javax.swing.JTable();
        btnConsulta = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/WhatsApp Image 2021-02-16 at 20.47.18.jpeg"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/WhatsApp Image 2021-02-16 at 20.47.19.jpeg"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/WhatsApp Image 2021-02-16 at 20.47.20 (1).jpeg"))); // NOI18N
        btnEditar.setText("Editar\n");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/WhatsApp Image 2021-02-16 at 20.47.20.jpeg"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnSalv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/sav.jpg"))); // NOI18N
        btnSalv.setText("Salvar");
        btnSalv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalv)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnCancelar)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir)
                    .addComponent(btnSalv))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setText("Nome:");

        jLabel10.setText("Modelo:");

        jLabel1.setText("Tamanho:");

        edtTam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtTamActionPerformed(evt);
            }
        });

        jLabel2.setText("Tempo de Uso:");

        jLabel3.setText("Cor:");

        jLabel4.setText("Tipo de Pneu:");

        jLabel5.setText("Valor Locacao:");

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtLoca))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(edtCor)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtTam, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(edtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(edtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(edtTam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(edtTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(edtCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(edtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(edtLoca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        tblBike.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblBike);

        btnConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/WhatsApp Image 2021-02-16 at 20.47.19 (1).jpeg"))); // NOI18N
        btnConsulta.setText("Consultar");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton7.setText("VOLTAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Cadastro de Bicicleta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnConsulta)
                .addGap(28, 28, 28))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jButton7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnConsulta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void edtTamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtTamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtTamActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        tipoEdicao = 'E';
        ativaForm(true);
        limparDados();
        alteraCliente();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        tipoEdicao = 'N';
        ativaForm(true);
        limparDados();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        ativaForm(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvActionPerformed
        // TODO add your handling code here:
        try {
        if(validacaoCampos()){
                //JOptionPane.showMessageDialog(rootPane, "Dados preenchidos corretamente");
           salvarBicicleta();
           limparDados();
           ativaForm(false);
           JOptionPane.showMessageDialog(rootPane, "Dados salvos com sucesso"); 

        }
        }catch (Exception ex) {
                Logger.getLogger(FrmCadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSalvActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        // TODO add your handling code here:
        carregarBicicleta();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        new Inicio().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalv;
    private javax.swing.JTextField edtCor;
    private javax.swing.JTextField edtLoca;
    private javax.swing.JTextField edtModelo;
    private javax.swing.JTextField edtNome;
    private javax.swing.JTextField edtTam;
    private javax.swing.JTextField edtTemp;
    private javax.swing.JTextField edtTipo;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JTable tblBike;
    // End of variables declaration//GEN-END:variables
}
