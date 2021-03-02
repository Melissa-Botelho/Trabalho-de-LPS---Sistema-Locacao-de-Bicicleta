/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/*import java.util.ArrayList;
import java.awt.List;
import java.awt.event.ActionEvent;*/
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import locacaobike.Cliente;
import javax.swing.text.MaskFormatter;
import conexao.Conexaoo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author melisa
 */
public final class FrmCadCliente extends javax.swing.JFrame {

    private ArrayList<Cliente> arrayCliente = new ArrayList();
    Cliente cliente = new Cliente();
    char tipoEdicao;

    PreparedStatement ps = null;
    ResultSet resultado = null;

    public FrmCadCliente() {
        initComponents();

        try {
            MaskFormatter maskData = new MaskFormatter("##/##/####");
            MaskFormatter maskCPF = new MaskFormatter("###.###.###-##");
            MaskFormatter maskTel = new MaskFormatter("(##) #####-####");

            maskCPF.install(txtCpf);
            maskTel.install(btnTel);

        } catch (ParseException ex) {
            Logger.getLogger(FrmCadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*public void excluiCliente(){
        String CPF;
        if(tblcliente.getSelectedRow()!= -1){
           CPF = (String) tblcliente.getValueAt(tblcliente.getSelectedRow(), 0);
           cliente = buscarCliente(CPF);
           preencheFormulario();
            ativaForm(false);
            if(JOptionPane.showConfirmDialog(rootPane,"Deseja excluir o(a):" + cliente.getNome(),"Locacao de Bicicletas",JOptionPane.YES_NO_OPTION)== 0){
                ps = Conexaoo.conexao().prepareStatement("DELETE FROM `locacaobike`.`cad_cliente`");
                ps.setString(1, String.valueOf(cliente.get()));
                ps.executeUpdate();
                TableCliente();
                
            }
        }else
           JOptionPane.showMessageDialog(rootPane, "Selecione algum cliente");
    }*/
    public void alteraCliente() {
        String CPF;
        if (tblcliente.getSelectedRow() != -1) {
            CPF = (String) tblcliente.getValueAt(tblcliente.getSelectedRow(), 0);
            cliente = buscarCliente(CPF);
            preencheFormulario();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Selecione algum cliente");
        }
    }

    public Cliente buscarCliente(String CPF) {
        for (int i = 0; i < arrayCliente.size(); i++) {
            if (arrayCliente.get(i).getCpf() == CPF) {
                return arrayCliente.get(i);
            }
        }
        return null;
    }

    public void preencheFormulario() {
        edtIdade.setText(String.valueOf(cliente.getIdade()));
        edtNome.setText(cliente.getNome());
        txtCpf.setText(cliente.getCpf());
        if (cliente.getSexo() == 'M') {
            cbxSexo.setSelectedIndex(0);
        } else {
            cbxSexo.setSelectedIndex(1);
        }

    }

    /*public void salvarCliente(){
        try {
        Cliente cliente = new Cliente();
        cliente.setNome(edtNome.getText());
        cliente.setIdade(Integer.parseInt(edtIdade.getText()));
        cliente.setSexo(cbxSexo.getText());
        cliente.setCpf(txtCpf.getText());
        cliente.setTelefone(btnTel.getText());
        cliente.setEmail(edtEmail.getText());
        cliente.setEndereco(edtEndereco.getText());
       
        
        arrayCliente.add(cliente);
        
        }catch (Exception ex) {
            Logger.getLogger(FrmCadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }*/

    public void salvarCliente() throws SQLException {
       
        try {
            if (tipoEdicao == 'N') {
                
                cliente = new Cliente();
                cliente.setCpf(txtCpf.getText());
            }

            cliente.setNome(edtNome.getText());
            cliente.setEmail(edtEmail.getText());
            cliente.setEndereco(edtEndereco.getText());
            cliente.setTelefone(btnTel.getText());
            cliente.setIdade(Integer.valueOf(edtIdade.getText()));
            if (cbxSexo.getSelectedIndex() == 0) {
                cliente.setSexo('M');
            }
            if (cbxSexo.getSelectedIndex() == 1) {
                cliente.setSexo('F');
            }

            if (tipoEdicao == 'N') {
                
                try {

                    ps = Conexaoo.conexao().prepareStatement("INSERT INTO `locacaobike´.`cad_cliente` (`nome`, `cpf`, `idade`, `endereco`, `telefone`, `email`, `sexo`) VALUES(?, ?, ?, ?, ?,?,?)");
                   
                    ps.setString(1, cliente.getNome());
                    ps.setString(2, cliente.getCpf());
                    ps.setString(3, String.valueOf(cliente.getIdade()));
                    ps.setString(4, cliente.getEndereco());
                    ps.setString(5, cliente.getTelefone());
                    ps.setString(6, cliente.getEmail());
                    ps.setString(7, String.valueOf(cliente.getSexo()));

                    ps.execute();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            } else if (tipoEdicao == 'E') {
                ps = Conexaoo.conexao().prepareStatement("UPDATE `locacaobike`.`pessoa` SET (nome=?,idade=?, email=?, endereco=?, telefone=?,sexo=?) WHERE cpf = ?");

                ps.setString(1, cliente.getNome());
                ps.setString(2, cliente.getCpf());
                ps.setString(3, cliente.getEmail());
                ps.setString(4, cliente.getEndereco());
                ps.setString(5, cliente.getTelefone());
                ps.setString(6, String.valueOf(cliente.getSexo()));
                ps.setString(7, String.valueOf(cliente.getIdade()));

                ps.executeUpdate();
            }

        } catch (Exception ex) {
            Logger.getLogger(FrmCadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void TableCliente() {
        String[] colunas = {"nome", "idade", "sexo", "cpf", "telefone", "email", "endereco"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        /*for(int i=0; i<arrayCliente.size(); i++){
                Object [] linha = {arrayCliente.get(i).getNome(),arrayCliente.get(i).getIdade(),arrayCliente.get(i).getCpf(),arrayCliente.get(i).getEmail(), arrayCliente.get(i).getEndereco(), arrayCliente.get(i).getSexo(),arrayCliente.get(i).getTelefone()};
                model.addRow(linha);
            }
            tblcliente.setModel(model);*/
        try {
            //ps = Conexaoo.conexao().prepareStatement("SELECT * FROM vacinacao.pessoa WHERE codigo_cliente is not null;");
            ps = Conexaoo.conexao().prepareStatement("SELECT * FROM locacaobike.cad_cliente");
            resultado = ps.executeQuery();      
        
        while (resultado.next()){
               cliente = new Cliente();
               cliente.setNome(resultado.getString("nome"));
               cliente.setCpf(resultado.getString("cpf"));
               cliente.setIdade(resultado.getInt("idade"));
               cliente.setEndereco(resultado.getString("endereco"));
               cliente.setTelefone(resultado.getString("telefone"));
               cliente.setEmail(resultado.getString("email"));
               cliente.setSexo(resultado.getString("sexo").charAt(0));
            
            arrayCliente.add(cliente);
            
            Object [] linha  = {cliente.getNome(),cliente.getCpf(),cliente.getIdade(),cliente.getEndereco(),cliente.getTelefone(),cliente.getEmail(),cliente.getSexo()};
            model.addRow(linha);
        }
        tblcliente.setModel(model);
        
        } catch (SQLException ex) {
            Logger.getLogger(FrmCadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    /* public Cliente camposParaObjeto(){
        Cliente p = new Cliente();
        p.setNome(edtNome.getText());
        p.setCpf(txtCpf.getText());
        if(!edtIdade.getText().isEmpty())
            p.setIdade(Integer.parseInt(edtIdade.getText()));
        if(!cbxSexo.getText().isEmpty())
            p.setSexo(cbxSexo.getText());
        p.setEndereco(edtEndereco.getText());
        p.setTelefone(btnTel.getText());
        p.setEmail(edtEmail.getText());
        return p;
        
    }*/
    public String imprimirTela() {
        String listagemFinal = "";
        for (int i = 0; i < arrayCliente.size(); i++) {
            listagemFinal = listagemFinal + arrayCliente.get(i).imprimir();
        }
        return listagemFinal;
    }

    public Cliente pesquisa(String CPF) {
        for (int i = 0; i < arrayCliente.size() - 1; i++) {
            if (arrayCliente.get(i).getCpf().equals(CPF)) {
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

    public boolean verificarCPF(String cpf) {
        int dig1 = 0, dig2 = 0, calc1 = 0, calc2 = 0, aux1 = 10, aux2 = 11;
        int[] arrayCPF;
        boolean repetido = true;
        arrayCPF = new int[9];
        dig1 = Integer.parseInt(cpf.substring(12, 13));
        dig2 = Integer.parseInt(cpf.substring(13, 14));
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

        if (calc1 == dig1 && calc2 == dig2 && !repetido) {
            return true;
        } else {
            return false;
        }
    }//fim função verifica CPF

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
        /*if(ftxtDataNascimento.getText().replace(" ", "").length() < 10){
            JOptionPane.showMessageDialog(rootPane,"Preencha a Data de Nascimento corretamente (deve conter 8 digitos)");
            ftxtDataNascimento.requestFocus();
            return false;
        }*/

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
        } else if (!verificarCPF(txtCpf.getText())) {
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
                .addContainerGap(27, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
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
        ativaForm(true);
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
        ativaForm(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        tipoEdicao = 'E';
        ativaForm(true);
        limparCampos();
        alteraCliente();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        //excluiCliente();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        try {
            if (validacaoCampos()) {
                //JOptionPane.showMessageDialog(rootPane, "Dados preenchidos corretamente");
                salvarCliente();
                limparCampos();
                ativaForm(true);
                JOptionPane.showMessageDialog(rootPane, "Dados salvos com sucesso");

            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmCadCliente.class.getName()).log(Level.SEVERE, null, ex);
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
