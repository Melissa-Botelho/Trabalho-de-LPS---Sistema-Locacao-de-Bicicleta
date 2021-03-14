/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import locacaobike.Bicicleta;
import locacaobike.dao.BicicletaDAO;

/**
 *
 * @author melisa
 */
public class FrmCadBicicleta extends javax.swing.JFrame {
    
    /**
     * Creates new form FrmCadBicicleta
     */
    private ArrayList<Bicicleta> arrayBicicleta = new ArrayList();
    Bicicleta bicicleta = new Bicicleta();
    BicicletaDAO bic = new BicicletaDAO();
    char tipoEdicao;
    public FrmCadBicicleta() {
        initComponents();
        //camposFormatado();
        carregarBicicleta();
    }
    public void alteraCliente(){
        String modelo;
        if(tblBike.getSelectedRow()!= -1){
           modelo = (String) tblBike.getValueAt(tblBike.getSelectedRow(), 2);
           bicicleta = buscarBicicleta(modelo);
           preencheFormulario();
        }else
           JOptionPane.showMessageDialog(rootPane, "Selecione algum bicicleta");
    }
    public Bicicleta buscarBicicleta (String modelo){
        for (int i =0; i<arrayBicicleta.size(); i++){
           if(arrayBicicleta.get(i).getModelo().equals(modelo)){
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
        if(tipoEdicao == 'N')    
            bicicleta = new Bicicleta();
        bicicleta.setModelo(edtModelo.getText());
        bicicleta.setNome(edtNome.getText());
        bicicleta.setCor(edtCor.getText());
        bicicleta.setTipPneu(edtTipo.getText());
        bicicleta.setTamanho(Float.parseFloat(edtTam.getText()));
        bicicleta.setTempUs(Float.parseFloat(edtTemp.getText()));
        bicicleta.setVarLocacao(Float.parseFloat(edtLoca.getText()));
        if(tipoEdicao == 'N')  
            bic.insert(bicicleta);
        else if(tipoEdicao == 'E')
            bic.update(bicicleta);
    }
   
    public void carregarBicicleta(){
        String [] colunas = {"ID","Nome", "Modelo", "Tamanho", "Tempo de Uso", "Valor da Locacao","Cor","Tipo do Pneu"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        arrayBicicleta = bic.selectAll();
        for(int i=0; i<arrayBicicleta.size(); i++){
                Object [] linha = {arrayBicicleta.get(i).getIdBicicleta(), arrayBicicleta.get(i).getNome(), arrayBicicleta.get(i).getModelo(), arrayBicicleta.get(i).getTamanho(),arrayBicicleta.get(i).getTempUs(),arrayBicicleta.get(i).getVarLocacao(),  arrayBicicleta.get(i).getCor(), arrayBicicleta.get(i).getTipPneu()};
                modelo.addRow(linha);
            }
        tblBike.setModel(modelo);        
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
    public void excluir(){
        String modelo;
        if(tblBike.getSelectedRow()!= -1){
           modelo = (String) tblBike.getValueAt(tblBike.getSelectedRow(), 2);
           bicicleta = buscarBicicleta(modelo);
           preencheFormulario();
            ativaForm(false);
            if(JOptionPane.showConfirmDialog(rootPane,"Deseja excluir o(a):" + bicicleta.getModelo(),"Locacao de Bicicletas",JOptionPane.YES_NO_OPTION)== 0){
                bic.delete(bicicleta.getIdBicicleta());
                carregarBicicleta();
                
            }
        }else
           JOptionPane.showMessageDialog(rootPane, "Selecione algum bicicleta");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalv = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        btnEditar.setText("Editar ");
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

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Cadastro de Bicicleta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btnNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalv))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel6)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConsulta)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnConsulta)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        //excluir
        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvActionPerformed
        // TODO add your handling code here:
            if(validacaoCampos()){
                //JOptionPane.showMessageDialog(rootPane, "Dados preenchidos corretamente");
                salvarBicicleta();
                limparDados();
                JOptionPane.showMessageDialog(rootPane, "Dados salvos com sucesso");
                carregarBicicleta();

            }
    }//GEN-LAST:event_btnSalvActionPerformed

    private void edtTamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtTamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtTamActionPerformed

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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JTable tblBike;
    // End of variables declaration//GEN-END:variables
}
