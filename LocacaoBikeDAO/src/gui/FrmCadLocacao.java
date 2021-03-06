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
import locacaobike.Locacao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import javax.swing.text.MaskFormatter;
import java.sql.Date;
import locacaobike.dao.LocacaoDAO;
/**
 *
 * @author melisa
 */
public class FrmCadLocacao extends javax.swing.JFrame {

    /**
     * Creates new form FrmCadLocacao
     */
    private final SimpleDateFormat formatarData = new SimpleDateFormat("dd/MM/yyyy");
    private ArrayList<Locacao> arrayLocacao = new ArrayList();
    Locacao locacao = new Locacao();
    LocacaoDAO loc = new LocacaoDAO();
    char tipoEdicao;
    
    
    public FrmCadLocacao() {
        initComponents();
        camposFormatado();
        carregarLocacao();
    }
    public void alteraCliente(){
        String modelo;
        if(tblLoc.getSelectedRow()!= -1){
           modelo = (String) tblLoc.getValueAt(tblLoc.getSelectedRow(), 2);
           locacao = buscarLocacao(modelo);
           preencheFormulario();
        }else
           JOptionPane.showMessageDialog(this, "Selecione algum cliente");
    }
    public Locacao buscarLocacao (String modelo){
        for (int i =0; i<arrayLocacao.size(); i++){
           if(loc.pegaModelo(arrayLocacao.get(i).getIdBicicleta()).equals(modelo)){
               return arrayLocacao.get(i);
           } 
        }
        return null;
    }
    public void preencheFormulario(){
       edtHoraL.setText(String.valueOf(locacao.getHorarioLoc()));
       edtNome.setText(loc.pegaNome(locacao.getIdCliente()));
       edtModelo.setText(loc.pegaModelo(locacao.getIdBicicleta()));
       txtDat.setText(locacao.getDataD().toString());
       edtHoraD.setText(String.valueOf(locacao.getHorarioDev()));
       txtData.setText(locacao.getDataLoc().toString());
       edtVar.setText(String.valueOf(locacao.getValorLoc()));       
    }
    
    public void salvarLocacao(){
        try {
            if(tipoEdicao == 'N')    
                locacao = new Locacao();
            locacao.setIdCliente(loc.pegaIdCliente(edtNome.getText()));
            locacao.setIdBicicleta(loc.pegaIdBicicleta(edtModelo.getText()));
            locacao.setHorarioDev(Integer.valueOf(edtHoraD.getText()));
            locacao.setHorarioLoc(Integer.valueOf(edtHoraL.getText()));
            locacao.setDataLoc(formatarData.parse(txtDat.getText()));
            locacao.setDataD(formatarData.parse(txtData.getText()));
            locacao.setValorLoc(Integer.valueOf(edtVar.getText()));
            if(tipoEdicao == 'N')
                loc.insert(locacao);
            else if(tipoEdicao == 'E')
                loc.update(locacao);
        } catch (ParseException ex) {
            Logger.getLogger(FrmCadLocacao.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
   
    public void carregarLocacao(){
        String [] colunas = {"ID","Nome", "Modelo", "Data Locacao", "Data Devolucacao", "Horario Locacao","Horario Devolucao","Valor Locacao"};
        arrayLocacao = loc.selectAll();
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        for(int i=0; i<arrayLocacao.size(); i++){
            Object [] linha = {arrayLocacao.get(i).getIdLocacao(),loc.pegaNome(arrayLocacao.get(i).getIdCliente()) , loc.pegaModelo(arrayLocacao.get(i).getIdBicicleta()) , arrayLocacao.get(i).getDataLoc(), arrayLocacao.get(i).getDataD(), arrayLocacao.get(i).getHorarioLoc(),  arrayLocacao.get(i).getHorarioDev(), arrayLocacao.get(i).getValorLoc()};
            modelo.addRow(linha);
        }
        tblLoc.setModel(modelo);
    }
    
    public void limparDados(){
        edtModelo.setText("");
        edtNome.setText("");
        edtHoraL.setText("");
        txtDat.setText("");
        txtData.setText("");
        edtHoraD.setText("");
        edtVar.setText("");
    }
    public void camposFormatado(){
        try {
            
            MaskFormatter maskData = new MaskFormatter("##/##/####");
            MaskFormatter maskData2 = new MaskFormatter("##/##/####");
            maskData.install(txtDat);
            maskData2.install(txtData);
            
        } catch (ParseException ex) {
            Logger.getLogger(FrmCadLocacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean validacaoCampos(){
        
        if(edtHoraL.getText().replace(" ", "").matches("[0-9]")){
            JOptionPane.showMessageDialog(this,"Preencha o Tamanho corretamente (somente numeros)");
            edtHoraL.requestFocus();
            return false;
        }
        if(edtHoraD.getText().replace(" ", "").matches("[0-9]")){
            JOptionPane.showMessageDialog(this, "Preencha a data corretamente");
            edtHoraD.requestFocus();
            return false;
        }
        if(edtNome.getText().replace(" ", "").matches("[A-Za-z]")){
            JOptionPane.showMessageDialog(this,"Preencha o Nome corretamente (somente as letras sem acentos)");
            edtNome.requestFocus();
            return false;
        }
        if(edtModelo.getText().replace(" ", "").matches("[A-Za-z]")){
            JOptionPane.showMessageDialog(this,"Preencha o Modelo corretamente (somente as letras sem acentos)");
            edtModelo.requestFocus();
            return false;
        }
        if(edtVar.getText().replace(" ", "").matches("[0-9]")){
            JOptionPane.showMessageDialog(this,"Preencha o Valor da Locacao corretamente (somente numeros)");
            edtVar.requestFocus();
            return false;
        }
         if(txtDat.getText().replace(" ", "").length() < 10){
            JOptionPane.showMessageDialog(this,"Preencha a Data de Nascimento corretamente (deve conter 8 digitos)");
            txtDat.requestFocus();
            return false;
        }
         if(txtData.getText().replace(" ", "").length() < 10){
            JOptionPane.showMessageDialog(this,"Preencha a Data de Nascimento corretamente (deve conter 8 digitos)");
            txtData.requestFocus();
            return false;
        }
        return true;
    }

    public void ativaForm(boolean ativaDesativa){
        for(int i=0; i<pnlForm.getComponents().length; i++){
            pnlForm.getComponent(i).setEnabled(ativaDesativa);
        }
                
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlForm = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        edtNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        edtHoraL = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        edtModelo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        edtHoraD = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        edtVar = new javax.swing.JTextField();
        txtDat = new javax.swing.JFormattedTextField();
        txtData = new javax.swing.JFormattedTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("Nome do Cliente:");

        edtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtNomeActionPerformed(evt);
            }
        });

        jLabel4.setText("Horario da Locação:");

        edtHoraL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtHoraLActionPerformed(evt);
            }
        });

        jLabel7.setText("Modelo da Bicicleta:");

        jLabel8.setText("Data da Locação:");

        jLabel5.setText("Horario da Devolução:");

        jLabel6.setText("Data da Devolução:");

        jLabel9.setText("Valor da Locação:");

        edtVar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtVarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addComponent(txtDat, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edtHoraD, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edtVar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addComponent(edtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edtHoraL, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGap(449, 449, 449)
                        .addComponent(jLabel1)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(11, 11, 11)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(edtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(edtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(edtHoraL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(edtHoraD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(edtVar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(386, 386, 386))
        );

        tblLoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome do Cliente", "Modelo da Bicicleta", "Horario da Locação", "Data da Locaçãol", "Horario de Devolução", "Data de Devolução", "Valor da Locação", "Devolvido"
            }
        ));
        jScrollPane3.setViewportView(tblLoc);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("VOLTAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
        btnEditar.setText("Editar");
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

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/sav.jpg"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Cadastro de Locação ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addGap(29, 29, 29)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnCancelar)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(355, 355, 355)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlForm, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addGap(42, 42, 42)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        tipoEdicao = 'E';
        ativaForm(true);
        limparDados();
        alteraCliente();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
            if(validacaoCampos()){
                //JOptionPane.showMessageDialog(rootPane, "Dados preenchidos corretamente");
                salvarLocacao();
                limparDados();
                ativaForm(false);
                JOptionPane.showMessageDialog(this, "Dados salvos com sucesso");
                carregarLocacao();
            }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void edtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtNomeActionPerformed

    private void edtHoraLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtHoraLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtHoraLActionPerformed

    private void edtVarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtVarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtVarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new Inicio().setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JTextField edtHoraD;
    private javax.swing.JTextField edtHoraL;
    private javax.swing.JTextField edtModelo;
    private javax.swing.JTextField edtNome;
    private javax.swing.JTextField edtVar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JTable tblLoc;
    private javax.swing.JFormattedTextField txtDat;
    private javax.swing.JFormattedTextField txtData;
    // End of variables declaration//GEN-END:variables
}
