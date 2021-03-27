/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/*import java.util.ArrayList;
import java.awt.List;
import java.awt.event.ActionEvent;*/
import controller.ControllerCliente;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import utils.Utils;

/**
 *
 * @author melisa
 */
public final class FrmCadCliente extends javax.swing.JFrame {

    private ArrayList<Object []> arrayCliente = new ArrayList();
    Object [] cliente = new Object [8];
    char tipoEdicao;
    public FrmCadCliente() {
        initComponents();
        habilitaCampos(false);
        TableCliente();
        try {
            MaskFormatter maskCPF = new MaskFormatter("###.###.###-##");
            MaskFormatter maskTel = new MaskFormatter("(##) #####-####");

            maskCPF.install(txtCpf);
            maskTel.install(btnTel);

        } catch (ParseException ex) {
            Logger.getLogger(FrmCadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void excluiCliente(){
        String CPF;
        if(tblcliente.getSelectedRow()!= -1){
           CPF = (String) tblcliente.getValueAt(tblcliente.getSelectedRow(), 4);
           cliente = buscarCliente(CPF);
           preencheFormulario();
            habilitaCampos(false);
            limparCampos();
            if(JOptionPane.showConfirmDialog(rootPane,"Deseja excluir o(a):" + cliente[1],"Locacao de Bicicletas",JOptionPane.YES_NO_OPTION)== 0){
                ControllerCliente.delete((int)cliente[0]);
                TableCliente();
                
            }
        }else
           JOptionPane.showMessageDialog(rootPane, "Selecione algum cliente");
    }
    
    public void alteraCliente() {
        String CPF;
        if (tblcliente.getSelectedRow() != -1) {
            CPF = (String) tblcliente.getValueAt(tblcliente.getSelectedRow(), 4);
            cliente = buscarCliente(CPF);
            preencheFormulario();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Selecione algum cliente");
        }
    }

    public Object [] buscarCliente(String CPF) {
        for (int i = 0; i < arrayCliente.size(); i++) {
            if (arrayCliente.get(i)[3].equals(CPF)) {
                return arrayCliente.get(i);
            }
        }
        return null;
    }

    public void preencheFormulario() {
        edtEndereco.setText((String)cliente[4]);
        edtEmail.setText((String)cliente[6]);
        btnTel.setText((String)cliente[5]);
        edtIdade.setText(""+(int)cliente[3]);
        edtNome.setText((String)cliente[1]);
        txtCpf.setText((String)cliente[2]);
        if ((char)cliente[7] == 'M') 
            cbxSexo.setSelectedIndex(0);
        else 
            cbxSexo.setSelectedIndex(1);
    }

    public void salvarCliente(){
        char sexo = ' ';
        if (cbxSexo.getSelectedIndex() == 0)
            sexo = 'M';
        if (cbxSexo.getSelectedIndex() == 1)
            sexo = 'F';
        if (tipoEdicao == 'N')     
            ControllerCliente.insert(edtNome.getText(),txtCpf.getText(),Integer.parseInt(edtIdade.getText()),edtEndereco.getText(),btnTel.getText(),edtEmail.getText(),sexo);
        else if (tipoEdicao == 'E') 
            ControllerCliente.update(edtNome.getText(),txtCpf.getText(),Integer.parseInt(edtIdade.getText()),edtEndereco.getText(),btnTel.getText(),edtEmail.getText(),sexo,(int)cliente[0]);       
    }

    public void TableCliente() {
        String[] colunas = {"id", "nome", "idade", "sexo", "cpf", "telefone", "email", "endereco"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        arrayCliente = ControllerCliente.select();
        for(int i=0; i<arrayCliente.size(); i++){
                Object [] linha = {(int)arrayCliente.get(i)[0], (String)arrayCliente.get(i)[1],(int)arrayCliente.get(i)[3],(char)arrayCliente.get(i)[7], (String)arrayCliente.get(i)[2], (String)arrayCliente.get(i)[5], (String)arrayCliente.get(i)[6], (String)arrayCliente.get(i)[4]};
                model.addRow(linha);
            }
            tblcliente.setModel(model);
   }

    public void habilitaCampos(boolean flag) {
        edtNome.setEnabled(flag);
        txtCpf.setEnabled(flag);
        edtIdade.setEnabled(flag);
        cbxSexo.setEnabled(flag);
        btnTel.setEnabled(flag);
        edtEndereco.setEnabled(flag);
        edtEmail.setEnabled(flag);
    }

    public void limparCampos() {
        edtNome.setText("");
        txtCpf.setText("");
        edtIdade.setText("");
        cbxSexo.setSelectedIndex(0);
        btnTel.setText("");
        edtEndereco.setText("");
        edtEmail.setText("");
    }

    public Object [] pesquisa(String CPF) {
        for (int i = 0; i < arrayCliente.size() - 1; i++) {
            if (arrayCliente.get(i)[2].equals(CPF)) {
                return arrayCliente.get(i);
            }
        }
        return null;
    }

    public void objetoParaCampos(Cliente p) {
        edtNome.setText(p.getNome());
        txtCpf.setText(p.getCpf());
        edtIdade.setText("" + p.getIdade());
        cbxSexo.setSelectedIndex(0);
        edtEndereco.setText(p.getEndereco());
        btnTel.setText(p.getTelefone());
        edtEmail.setText(p.getEmail());

    }

    public boolean validacaoCampos() {

        if (edtIdade.getText().replace(" ", "").matches("[0-9]")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha a idade corretamente (somente numeros)");
            edtIdade.requestFocus();
            return false;
        }
        if (edtNome.getText().replace(" ", "").matches("[A-Za-z]")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha o Nome corretamente (somente as letras sem acentos)");
            edtNome.requestFocus();
            return false;
        }
      
        if (btnTel.getText().replace(" ", "").length() < 11) {
            JOptionPane.showMessageDialog(rootPane, "Preencha o telefone corretamente (deve conter 11 digitos)");
            btnTel.requestFocus();
            return false;
        }
        if (edtEmail.getText().replace(" ", "").matches("[A-Za-z]")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha o email corretamente (somente as letras sem acentos)");
            edtEmail.requestFocus();
            return false;
        }
        if (edtEndereco.getText().replace(" ", "").matches("[A-Za-z]")) {
            JOptionPane.showMessageDialog(rootPane, "Preencha o Endereco corretamente (somente as letras sem acentos)");
            edtEndereco.requestFocus();
            return false;
        }
        if (txtCpf.getText().replace(" ", "").length() < 13) {
            JOptionPane.showMessageDialog(rootPane, "Preencha o CPF corretamente (deve conter 11 digitos)");
            txtCpf.requestFocus();
            return false;
        } else if (!Utils.verificarCPF(txtCpf.getText())) {
            JOptionPane.showMessageDialog(rootPane, "CPF Inválido");
            return false;
        }

        return true;
    }

    public void ativaForm(boolean ativaDesativa) {
        for (int i = 0; i < pnlForm.getComponents().length; i++) {
            pnlForm.getComponent(i).setEnabled(ativaDesativa);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pnlForm = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        edtNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        edtIdade = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        edtEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        edtEndereco = new javax.swing.JTextField();
        txtCpf = new javax.swing.JFormattedTextField();
        btnTel = new javax.swing.JFormattedTextField();
        cbxSexo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblcliente = new javax.swing.JTable();
        btnVoltar = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();

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
        jScrollPane2.setViewportView(jTable1);

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

        jLabel3.setText("Nome:");

        edtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtNomeActionPerformed(evt);
            }
        });

        jLabel4.setText("CPF:");

        jLabel5.setText("Idade:");

        jLabel6.setText("Sexo:");

        jLabel7.setText("Telefone:");

        jLabel8.setText("Email:");

        jLabel9.setText("Endereço:");

        edtEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtEnderecoActionPerformed(evt);
            }
        });

        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Masculino", "Feminino" }));

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(btnCancelar)
                                .addGap(35, 35, 35)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edtNome)))
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(pnlFormLayout.createSequentialGroup()
                                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnlFormLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlFormLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnTel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlFormLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(edtIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnCancelar)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir)
                    .addComponent(btnSalvar))
                .addGap(18, 18, 18)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(edtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(edtIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(edtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(edtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cadastro de Cliente");

        tblcliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "CPF", "Idade", "Sexo", "Telefone", "Email", "Endereço"
            }
        ));
        jScrollPane3.setViewportView(tblcliente);

        btnVoltar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnVoltar.setText("VOLTAR");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnConsulta.setText("Consulta");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(184, 184, 184))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConsulta)
                        .addGap(42, 42, 42))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(293, 293, 293)
                .addComponent(btnVoltar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(34, 34, 34)
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConsulta)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnVoltar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:    
        tipoEdicao = 'N';
        habilitaCampos(true);
        limparCampos();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void edtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtNomeActionPerformed

    private void edtEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtEnderecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtEnderecoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        habilitaCampos(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        tipoEdicao = 'E';
        habilitaCampos(true);
        limparCampos();
        alteraCliente();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        excluiCliente();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
            if (validacaoCampos()) {
                //JOptionPane.showMessageDialog(rootPane, "Dados preenchidos corretamente");
                salvarCliente();
                limparCampos();
                habilitaCampos(true);
                JOptionPane.showMessageDialog(rootPane, "Dados salvos com sucesso");
                TableCliente();
            }
        

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        // TODO add your handling code here:
        new Inicio().setVisible(true);
        this.dispose();

    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        try {
            TableCliente();
        }catch (Exception ex) {
            Logger.getLogger(FrmCadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnConsultaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JFormattedTextField btnTel;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox cbxSexo;
    private javax.swing.JTextField edtEmail;
    private javax.swing.JTextField edtEndereco;
    private javax.swing.JTextField edtIdade;
    private javax.swing.JTextField edtNome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JTable tblcliente;
    private javax.swing.JFormattedTextField txtCpf;
    // End of variables declaration//GEN-END:variables
}
