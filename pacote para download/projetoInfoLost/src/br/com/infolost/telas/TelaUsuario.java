/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.infolost.telas;

import java.sql.*;
import br.com.infolost.dal.ModuloConexao;
import javax.swing.JOptionPane;

/**
 *
 * @author Vinicius Barbosa
 */
public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void consultar() {
        String sql = "select * from tbusuarios where iduser = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldUsuId.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                jTextFieldUsuNome.setText(rs.getString(2));
                jTextFieldUsuFone.setText(rs.getString(3));
                jTextFieldUsuLogin.setText(rs.getString(4));
                jTextFieldUsuSenha.setText(rs.getString(5));
                jComboBoxUsuPerfil.setSelectedItem(rs.getString(6));

            } else {
                JOptionPane.showMessageDialog(null, "Usuario nao cadastrado!");
                // as linhas abaixo limpam os campos
                jTextFieldUsuNome.setText(null);
                jTextFieldUsuFone.setText(null);
                jTextFieldUsuLogin.setText(null);
                jTextFieldUsuSenha.setText(null);
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void adicionar() {
        String sql = "insert into tbusuarios(iduser, usuario, fone, login, senha, perfil) values (?, ?, ?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldUsuId.getText());
            pst.setString(2, jTextFieldUsuNome.getText());
            pst.setString(3, jTextFieldUsuFone.getText());
            pst.setString(4, jTextFieldUsuLogin.getText());
            pst.setString(5, jTextFieldUsuSenha.getText());
            pst.setString(6, jComboBoxUsuPerfil.getSelectedItem().toString());
            // estrutura para confirmar os campos obrigatorios
            if ((jTextFieldUsuId.getText().isEmpty()) || (jTextFieldUsuNome.getText().isEmpty()) || (jTextFieldUsuLogin.getText().isEmpty()) || (jTextFieldUsuSenha.getText().isEmpty())) {
                 JOptionPane.showMessageDialog(null, "Preencha os campos obrigatorios!");
                 
            } else {

                // estrutura para confirmar a inserção dos dados na tabela            
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso!");
                    jTextFieldUsuId.setText(null);
                    jTextFieldUsuNome.setText(null);
                    jTextFieldUsuFone.setText(null);
                    jTextFieldUsuLogin.setText(null);
                    jTextFieldUsuSenha.setText(null);
                    
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void alterar(){
        String sql = "update tbusuarios set usuario=?, fone=?, login=?, senha=?, perfil=? where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldUsuNome.getText());
            pst.setString(2, jTextFieldUsuFone.getText());
            pst.setString(3, jTextFieldUsuLogin.getText());
            pst.setString(4, jTextFieldUsuSenha.getText());
            pst.setString(5, jComboBoxUsuPerfil.getSelectedItem().toString());
            pst.setString(6, jTextFieldUsuId.getText());
            
            if ((jTextFieldUsuId.getText().isEmpty()) || (jTextFieldUsuNome.getText().isEmpty()) || (jTextFieldUsuLogin.getText().isEmpty()) || (jTextFieldUsuSenha.getText().isEmpty())) {
                 JOptionPane.showMessageDialog(null, "Preencha os campos obrigatorios!");
                 
            } else {

                // estrutura para confirmar a alteração dos dados na tabela            
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario alterado com sucesso!");
                    jTextFieldUsuId.setText(null);
                    jTextFieldUsuNome.setText(null);
                    jTextFieldUsuFone.setText(null);
                    jTextFieldUsuLogin.setText(null);
                    jTextFieldUsuSenha.setText(null);
                    
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void remover(){
        // a estrutura abaixo confirma a remoção do usuário
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuario?");
        if (confirma == JOptionPane.YES_OPTION){
            String sql = "delete from tbusuarios where iduser=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, jTextFieldUsuId.getText());
                int apagado = pst.executeUpdate();
                if(apagado > 0){
                    JOptionPane.showMessageDialog(null, "Usuario removido com sucesso!");
                    jTextFieldUsuNome.setText(null);
                    jTextFieldUsuFone.setText(null);
                    jTextFieldUsuLogin.setText(null);
                    jTextFieldUsuSenha.setText(null);
                    
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
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

        jLabel1 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldUsuId = new javax.swing.JTextField();
        jTextFieldUsuNome = new javax.swing.JTextField();
        jTextFieldUsuLogin = new javax.swing.JTextField();
        jTextFieldUsuSenha = new javax.swing.JTextField();
        jComboBoxUsuPerfil = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldUsuFone = new javax.swing.JTextField();
        jButtonUsuCreate = new javax.swing.JButton();
        jButtonUsuUpdate = new javax.swing.JButton();
        jButtonUsuRead = new javax.swing.JButton();
        jButtonUsuDelete = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        jTextField5.setText("jTextField5");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(426, 394));

        jLabel2.setText("* ID:");

        jLabel3.setText("* Nome:");

        jLabel4.setText("* Login:");

        jLabel5.setText("* Senha:");

        jLabel6.setText("* Perfil:");

        jComboBoxUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));

        jLabel7.setText("  Fone:");

        jButtonUsuCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infolost/icones/create.png"))); // NOI18N
        jButtonUsuCreate.setToolTipText("adcionar...");
        jButtonUsuCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonUsuCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUsuCreateActionPerformed(evt);
            }
        });

        jButtonUsuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infolost/icones/update.jpg"))); // NOI18N
        jButtonUsuUpdate.setToolTipText("atualizar....");
        jButtonUsuUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonUsuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUsuUpdateActionPerformed(evt);
            }
        });

        jButtonUsuRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infolost/icones/read.jpg"))); // NOI18N
        jButtonUsuRead.setToolTipText("procurar...");
        jButtonUsuRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonUsuRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUsuReadActionPerformed(evt);
            }
        });

        jButtonUsuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infolost/icones/delete.jpg"))); // NOI18N
        jButtonUsuDelete.setToolTipText("deletar...");
        jButtonUsuDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUsuDeleteActionPerformed(evt);
            }
        });

        jLabel8.setText("* Campos obrigatorios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel7))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 1, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel5))))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextFieldUsuFone)
                                    .addComponent(jTextFieldUsuLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(jTextFieldUsuSenha)
                                    .addComponent(jComboBoxUsuPerfil, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonUsuCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBoxUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jButtonUsuCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonUsuUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonUsuDelete)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(34, 34, 34))
        );

        setBounds(0, 0, 427, 396);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonUsuReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUsuReadActionPerformed
        consultar();
    }//GEN-LAST:event_jButtonUsuReadActionPerformed

    private void jButtonUsuCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUsuCreateActionPerformed
        adicionar();
    }//GEN-LAST:event_jButtonUsuCreateActionPerformed

    private void jButtonUsuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUsuUpdateActionPerformed
        alterar();
    }//GEN-LAST:event_jButtonUsuUpdateActionPerformed

    private void jButtonUsuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUsuDeleteActionPerformed
        remover();
    }//GEN-LAST:event_jButtonUsuDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonUsuCreate;
    private javax.swing.JButton jButtonUsuDelete;
    private javax.swing.JButton jButtonUsuRead;
    private javax.swing.JButton jButtonUsuUpdate;
    private javax.swing.JComboBox<String> jComboBoxUsuPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextFieldUsuFone;
    private javax.swing.JTextField jTextFieldUsuId;
    private javax.swing.JTextField jTextFieldUsuLogin;
    private javax.swing.JTextField jTextFieldUsuNome;
    private javax.swing.JTextField jTextFieldUsuSenha;
    // End of variables declaration//GEN-END:variables
}
