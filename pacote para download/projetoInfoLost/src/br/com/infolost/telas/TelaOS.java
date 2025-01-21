/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.infolost.telas;

import java.sql.*;
import br.com.infolost.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Vinicius Barbosa
 */
public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private String tipo;

    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void pesquisarCliente() {
        String sql = "select idcli as Id, nomecli as Nome, fonecli as Fone from tbclientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            jTableClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setarCampos() {
        int setar = jTableClientes.getSelectedRow();
        jTextFieldCliID.setText(jTableClientes.getModel().getValueAt(setar, 0).toString());
    }

    // metodo para cadastrar uma OS
    private void emitirOS() {
        String sql = "insert into tbos (tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli) values(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, jComboBoxOSSit.getSelectedItem().toString());
            pst.setString(3, jTextFieldOSEquip.getText());
            pst.setString(4, jTextFieldOSDef.getText());
            pst.setString(5, jTextFieldOSServ.getText());
            pst.setString(6, jTextFieldOSTec.getText());
            pst.setString(7, jTextFieldOSValor.getText().replace(",", "."));
            pst.setString(8, jTextFieldCliID.getText());

            // validação dos campos obrigatórios
            if (jTextFieldCliID.getText().isEmpty() || jTextFieldOSEquip.getText().isEmpty() || jTextFieldOSDef.getText().isEmpty() || jComboBoxOSSit.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adcionado = pst.executeUpdate();
                if (adcionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso!");
                    recuperarOs();
                    jButtonOSAdd.setEnabled(false);
                    jButtonOSPesq.setEnabled(false);
                    jButtonOSImprimir.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisarOS() {
        String numOs = JOptionPane.showInputDialog("Numero da OS: ");
        String sql = "select os,date_format(data_os,'%d/%m/%Y'),tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcli from tbos where os=" + numOs;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                jTextFieldOS.setText(rs.getString(1));
                jTextFieldData.setText(rs.getString(2));
                String rbTipo = rs.getString(3);
                if (rbTipo.equals("os")) {
                    jRadioButtonOS.setSelected(true);
                    tipo = "os";
                } else {
                    jRadioButtonOrc.setSelected(true);
                    tipo = "orcamento";
                }
                jComboBoxOSSit.setSelectedItem(rs.getString(4));
                jTextFieldOSEquip.setText(rs.getString(5));
                jTextFieldOSDef.setText(rs.getString(6));
                jTextFieldOSServ.setText(rs.getString(7));
                jTextFieldOSTec.setText(rs.getString(8));
                jTextFieldOSValor.setText(rs.getString(9));
                jTextFieldCliID.setText(rs.getString(10));

                jButtonOSPesq.setEnabled(false);
                jButtonOSAdd.setEnabled(false);
                jTextFieldCliPesquisar.setEnabled(false);
                jTableClientes.setVisible(false);
                
                jButtonOSAlter.setEnabled(true);
                jButtonOSExcl.setEnabled(true);
                jButtonOSImprimir.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "OS nao cadastrada!");
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS Invalida!");
            //System.out.println(e);
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);

        }
    }

    private void alterarOs() {
        String sql = "update tbos set tipo=?, situacao=?, equipamento=?, defeito=?, servico=?, tecnico=?, valor=? where os=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, jComboBoxOSSit.getSelectedItem().toString());
            pst.setString(3, jTextFieldOSEquip.getText());
            pst.setString(4, jTextFieldOSDef.getText());
            pst.setString(5, jTextFieldOSServ.getText());
            pst.setString(6, jTextFieldOSTec.getText());
            pst.setString(7, jTextFieldOSValor.getText().replace(",", "."));
            pst.setString(8, jTextFieldOS.getText());

            // validação dos campos obrigatórios
            if (jTextFieldCliID.getText().isEmpty() || jTextFieldOSEquip.getText().isEmpty() || jTextFieldOSDef.getText().isEmpty() || jComboBoxOSSit.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adcionado = pst.executeUpdate();
                if (adcionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS alterada com sucesso!");

                    limparCampos();

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void excluirOs() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir? ", "Atencao", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbos where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, jTextFieldOS.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS excluida com sucesso!");

                    limparCampos();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }

    }

    private void imprimirOs(){
        
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão? ", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            // imprimindo relatorio com o JasperReport
            try {
                // criando um filtro
                HashMap filtro = new HashMap();
                filtro.put("os", Integer.parseInt(jTextFieldOS.getText()));
                
                // imprimindo
                JasperPrint relatorio = JasperFillManager.fillReport(getClass().getResourceAsStream("/reports/os.jasper"),filtro, conexao);
                JasperViewer.viewReport(relatorio, false);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                
            }
        }
    }
    
    // recuperar a OS
    private void recuperarOs(){
        String sql = "select max(os) from tbos";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()){
                jTextFieldOS.setText(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    private void limparCampos() {
        jComboBoxOSSit.setSelectedItem(" ");
        ((DefaultTableModel) jTableClientes.getModel()).setRowCount(0);
        jTextFieldCliPesquisar.setText(null);
        jTextFieldOS.setText(null);
        jTextFieldData.setText(null);
        jTextFieldOSEquip.setText(null);
        jTextFieldOSDef.setText(null);
        jTextFieldOSServ.setText(null);
        jTextFieldOSTec.setText(null);
        jTextFieldOSValor.setText(null);
        jTextFieldCliID.setText(null);
        //habilitar os objetos
        jButtonOSPesq.setEnabled(true);
        jButtonOSAdd.setEnabled(true);
        jTextFieldCliPesquisar.setEnabled(true);
        jTableClientes.setEnabled(true);
        //desabilitar os botoes
        jButtonOSAlter.setEnabled(false);
        jButtonOSExcl.setEnabled(false);
        jButtonOSImprimir.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldOS = new javax.swing.JTextField();
        jTextFieldData = new javax.swing.JTextField();
        jRadioButtonOrc = new javax.swing.JRadioButton();
        jRadioButtonOS = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxOSSit = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jTextFieldCliPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldCliID = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldOSEquip = new javax.swing.JTextField();
        jTextFieldOSDef = new javax.swing.JTextField();
        jTextFieldOSServ = new javax.swing.JTextField();
        jTextFieldOSTec = new javax.swing.JTextField();
        jTextFieldOSValor = new javax.swing.JTextField();
        jButtonOSAdd = new javax.swing.JButton();
        jButtonOSPesq = new javax.swing.JButton();
        jButtonOSAlter = new javax.swing.JButton();
        jButtonOSExcl = new javax.swing.JButton();
        jButtonOSImprimir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("N° OS:");

        jLabel2.setText("Data:");

        jTextFieldOS.setEditable(false);

        jTextFieldData.setEditable(false);

        buttonGroup1.add(jRadioButtonOrc);
        jRadioButtonOrc.setText("Orçamento");
        jRadioButtonOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonOrcActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButtonOS);
        jRadioButtonOS.setText("OS");
        jRadioButtonOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButtonOrc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonOS)
                        .addGap(7, 7, 7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldOS, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldData, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonOrc)
                    .addComponent(jRadioButtonOS))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel3.setText("Situação:");

        jComboBoxOSSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Na Bancada", "Entrega Ok", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando Peças", "Abandonado pelo Cliente", "Retornou" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        jTextFieldCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldCliPesquisarKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infolost/icones/lupa.png"))); // NOI18N

        jLabel5.setText("* Id");

        jTextFieldCliID.setEditable(false);

        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Fone"
            }
        ));
        jTableClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableClientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldCliPesquisar)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldCliID)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextFieldCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldCliID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setText("* Equipamento:");

        jLabel7.setText("* Defeito:");

        jLabel8.setText("Serviço:");

        jLabel9.setText("Técnico:");

        jLabel10.setText("Valor Total:");

        jTextFieldOSValor.setText("0.00");

        jButtonOSAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infolost/icones/create.png"))); // NOI18N
        jButtonOSAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOSAddActionPerformed(evt);
            }
        });

        jButtonOSPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infolost/icones/read.jpg"))); // NOI18N
        jButtonOSPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOSPesqActionPerformed(evt);
            }
        });

        jButtonOSAlter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infolost/icones/update.jpg"))); // NOI18N
        jButtonOSAlter.setEnabled(false);
        jButtonOSAlter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOSAlterActionPerformed(evt);
            }
        });

        jButtonOSExcl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infolost/icones/delete.jpg"))); // NOI18N
        jButtonOSExcl.setEnabled(false);
        jButtonOSExcl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOSExclActionPerformed(evt);
            }
        });

        jButtonOSImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infolost/icones/impressao.png"))); // NOI18N
        jButtonOSImprimir.setToolTipText("Imprimir OS");
        jButtonOSImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonOSImprimir.setEnabled(false);
        jButtonOSImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOSImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonOSAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonOSPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonOSAlter, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonOSExcl, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonOSImprimir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBoxOSSit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldOSEquip))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldOSServ, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldOSDef, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldOSTec, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(jTextFieldOSValor))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboBoxOSSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldOSEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldOSTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldOSDef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jTextFieldOSValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldOSServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonOSAlter, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(jButtonOSExcl, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButtonOSPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButtonOSAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButtonOSImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        setBounds(0, 0, 428, 395);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCliPesquisarKeyReleased
        // chamando o método pesquisar cliente:
        pesquisarCliente();
    }//GEN-LAST:event_jTextFieldCliPesquisarKeyReleased

    private void jTableClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClientesMouseClicked
        // chamando o método setar campos:
        setarCampos();
    }//GEN-LAST:event_jTableClientesMouseClicked

    private void jRadioButtonOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonOrcActionPerformed
        // escolha do tipo orçamento/os
        tipo = "orcamento";
    }//GEN-LAST:event_jRadioButtonOrcActionPerformed

    private void jRadioButtonOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonOSActionPerformed
        // escolha tipo orçamento/os
        tipo = "os";
    }//GEN-LAST:event_jRadioButtonOSActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // ao abrir o formulario marcar o radio button orçamento:
        jRadioButtonOrc.setSelected(true);
        tipo = "orcamento";

    }//GEN-LAST:event_formInternalFrameOpened

    private void jButtonOSAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOSAddActionPerformed
        // TODO add your handling code here:
        emitirOS();
    }//GEN-LAST:event_jButtonOSAddActionPerformed

    private void jButtonOSPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOSPesqActionPerformed
        // TODO add your handling code here:
        pesquisarOS();
    }//GEN-LAST:event_jButtonOSPesqActionPerformed

    private void jButtonOSAlterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOSAlterActionPerformed
        // TODO add your handling code here:
        alterarOs();
    }//GEN-LAST:event_jButtonOSAlterActionPerformed

    private void jButtonOSExclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOSExclActionPerformed
        // TODO add your handling code here:
        excluirOs();
    }//GEN-LAST:event_jButtonOSExclActionPerformed

    private void jButtonOSImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOSImprimirActionPerformed
        // TODO add your handling code here:
        imprimirOs();
    }//GEN-LAST:event_jButtonOSImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonOSAdd;
    private javax.swing.JButton jButtonOSAlter;
    private javax.swing.JButton jButtonOSExcl;
    private javax.swing.JButton jButtonOSImprimir;
    private javax.swing.JButton jButtonOSPesq;
    private javax.swing.JComboBox<String> jComboBoxOSSit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButtonOS;
    private javax.swing.JRadioButton jRadioButtonOrc;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTextField jTextFieldCliID;
    private javax.swing.JTextField jTextFieldCliPesquisar;
    private javax.swing.JTextField jTextFieldData;
    private javax.swing.JTextField jTextFieldOS;
    private javax.swing.JTextField jTextFieldOSDef;
    private javax.swing.JTextField jTextFieldOSEquip;
    private javax.swing.JTextField jTextFieldOSServ;
    private javax.swing.JTextField jTextFieldOSTec;
    private javax.swing.JTextField jTextFieldOSValor;
    // End of variables declaration//GEN-END:variables
}
