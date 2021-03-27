/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerLocacao;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import utils.Utils;
import javax.swing.text.MaskFormatter;
/**
 *
 * @author melisa
 */
public class FrmCadLocacao extends javax.swing.JFrame {

    /**
     * Creates new form FrmCadLocacao
     */
    private final SimpleDateFormat formatarData = new SimpleDateFormat("dd/MM/yyyy");
    private ArrayList<Object []> arrayLocacao = new ArrayList();
    Object [] locacao = new Object [8];
    char tipoEdicao;
    
    public FrmCadLocacao() {
        initComponents();
        camposFormatado();
        carregarLocacao();
        ativaForm(false);
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
    
    public Object [] buscarLocacao (String modelo){
        for (int i =0; i<arrayLocacao.size(); i++){
           if(ControllerLocacao.pegaNomeOuModelo((int)arrayLocacao.get(i)[2], 'B').equals(modelo)){
               return arrayLocacao.get(i);
           } 
        }
        return null;
    }
    
    public void preencheFormulario(){
       edtHoraL.setText(String.valueOf(locacao[3]));
       edtNome.setText(ControllerLocacao.pegaNomeOuModelo((int)locacao[1], 'C'));
       edtModelo.setText(ControllerLocacao.pegaNomeOuModelo((int)locacao[2],'B'));
       String dataD = locacao[3].toString().replace("-", "");
       dataD = dataD.substring(6, 8)+ dataD.substring(4, 6)+ dataD.substring(0, 4);
       txtDat.setText(dataD);
       edtHoraD.setText(String.valueOf(locacao[5]));
       String dataL = locacao[4].toString().replace("-", "");
       dataL = dataL.substring(6, 8)+ dataL.substring(4, 6)+ dataL.substring(0, 4);
       txtData.setText(dataL);
       edtVar.setText(String.valueOf(locacao[7]));       
    }
    
    public boolean salvarLocacao(){
        try {
            if(tipoEdicao == 'N')
                return ControllerLocacao.insert(ControllerLocacao.pegaId(edtNome.getText(),'C'),ControllerLocacao.pegaId(edtModelo.getText(),'B'),Float.valueOf(edtHoraL.getText()),formatarData.parse(txtDat.getText()),Float.valueOf(edtHoraD.getText()),formatarData.parse(txtData.getText()),Float.valueOf(edtVar.getText()));
            else if(tipoEdicao == 'E')
                return ControllerLocacao.update(ControllerLocacao.pegaId(edtNome.getText(),'C'),ControllerLocacao.pegaId(edtModelo.getText(),'B'),Float.valueOf(edtHoraL.getText()),formatarData.parse(txtDat.getText()),Float.valueOf(edtHoraD.getText()),formatarData.parse(txtData.getText()),Float.valueOf(edtVar.getText()),(int)locacao[0]);
        } catch (ParseException ex) {
            Logger.getLogger(FrmCadLocacao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       return false;
    }
    
    public void editar() {
        if (tblLoc.getSelectedRow() != -1) {
            int id  = (Integer) tblLoc.getValueAt(tblLoc.getSelectedRow(), 0);
            locacao = buscar(id);
            ativaForm(true);
            preencheFormulario();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Selecione algum cliente");
        }
    }
   
    public Object [] buscar(int id) {
        for (int i = 0; i < arrayLocacao.size(); i++) {
            if ((int)arrayLocacao.get(i)[0]==id) {
                return arrayLocacao.get(i);
            }
        }
        return null;
    }
    
    public void excluir(){
        if(tblLoc.getSelectedRow()!= -1){
           int id  = (Integer)tblLoc.getValueAt(tblLoc.getSelectedRow(), 0);
           locacao = buscar(id);
           preencheFormulario();
            ativaForm(true);
            limparDados();
            if(JOptionPane.showConfirmDialog(rootPane,"Deseja excluir o(a) locacao " + (int)locacao[0],"Locacao de Bicicletas",JOptionPane.YES_NO_OPTION)== 0){
                ControllerLocacao.delete((int)locacao[0]);
                carregarLocacao();
                ativaForm(false);
                
            }
        }else
           JOptionPane.showMessageDialog(rootPane, "Selecione algum cliente");
    }
    
    public final void carregarLocacao(){
        String [] colunas = {"ID","Nome", "Modelo", "Data Locacao", "Data Devolucacao", "Horario Locacao","Horario Devolucao","Valor Locacao"};
        arrayLocacao = ControllerLocacao.select();
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        for(int i=0; i<arrayLocacao.size(); i++){
            Object [] linha = {(int)arrayLocacao.get(i)[0],ControllerLocacao.pegaNomeOuModelo((int)arrayLocacao.get(i)[1],'C') , ControllerLocacao.pegaNomeOuModelo((int)arrayLocacao.get(i)[2],'B') , (Date)arrayLocacao.get(i)[4], (Date)arrayLocacao.get(i)[6], (float)arrayLocacao.get(i)[3],  (float)arrayLocacao.get(i)[5], (float)arrayLocacao.get(i)[7]};
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
    
    public final void camposFormatado(){
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
        
        if(edtHoraL.getText().replace(" ", "").matches("[0-9]") || !Utils.verificaHora(Float.parseFloat(edtHoraL.getText()))){
            JOptionPane.showMessageDialog(this,"Preencha o horario de locação corretamente (somente numeros)");
            edtHoraL.requestFocus();
            return false;
        }
        if(edtHoraD.getText().replace(" ", "").matches("[0-9]") || !Utils.verificaHora(Float.parseFloat(edtHoraD.getText()))){
            JOptionPane.showMessageDialog(this, "Preencha o horario de devolução corretamente");
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
            JOptionPane.showMessageDialog(this,"Preencha o Valor da Object [] corretamente (somente numeros)");
            edtVar.requestFocus();
            return false;
        }
         if(txtDat.getText().replace(" ", "").length() < 10 || !Utils.verificaData(txtDat.getText())){
            JOptionPane.showMessageDialog(this,"Preencha a Data de Locação corretamente (deve conter 8 digitos)");
            txtDat.requestFocus();
            return false;
        }
         if(txtData.getText().replace(" ", "").length() < 10 || !Utils.verificaData(txtData.getText()) || !Utils.verificaDataLocDev(txtDat.getText(),txtData.getText())){
            JOptionPane.showMessageDialog(this,"Preencha a Data de devoloção corretamente (deve conter 8 digitos)");
            txtData.requestFocus();
            return false;
        }
        return true;
    }
        
    public final void ativaForm(boolean ativaDesativa){
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
        btnConsultar = new javax.swing.JButton();
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

        btnConsultar.setText("Consulta");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
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
                                .addComponent(edtVar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConsultar))
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
                .addContainerGap(17, Short.MAX_VALUE))
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
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtDat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(edtHoraD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(edtVar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btnConsultar)))
                .addGap(352, 352, 352))
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(373, 373, 373))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
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
        limparDados();
        editar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
            if(validacaoCampos()){
                if(salvarLocacao()){
                    limparDados();
                    ativaForm(false);
                    JOptionPane.showMessageDialog(this, "Dados salvos com sucesso");
                    carregarLocacao();
                }else
                    JOptionPane.showMessageDialog(this, "Erro no salvamento dos dados");
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

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
        try {
            carregarLocacao();
        }catch (Exception ex) {
            Logger.getLogger(FrmCadLocacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnConsultarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsultar;
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
