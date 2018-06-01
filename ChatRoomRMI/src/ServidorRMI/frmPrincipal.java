/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServidorRMI;


import Remoto.RoomChat;
import java.awt.List;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;


/**
 *
 * @author gabri
 */
public class frmPrincipal extends javax.swing.JFrame {
    ServerChat server;
    /**
     * Creates new form frmPrincipal
     */
    public frmPrincipal() {
        initComponents();
    }
    
    public frmPrincipal(ServerChat server){
        this.server = server;
        initComponents();
        listSalas.removeAll();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnFecharSala = new javax.swing.JButton();
        btnAbrirSala = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtfNomeSala = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        listSalas = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor");

        btnFecharSala.setText("Fechar Sala");
        btnFecharSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharSalaActionPerformed(evt);
            }
        });

        btnAbrirSala.setText("Adicionar Sala");
        btnAbrirSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirSalaActionPerformed(evt);
            }
        });

        jLabel1.setText("Nome da Sala: ");

        jScrollPane1.setViewportView(listSalas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jtfNomeSala, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAbrirSala, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFecharSala, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfNomeSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAbrirSala))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(btnFecharSala)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbrirSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirSalaActionPerformed
        // TODO add your handling code here:
        this.server.createRoom(jtfNomeSala.getText());
    }//GEN-LAST:event_btnAbrirSalaActionPerformed

    private void btnFecharSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharSalaActionPerformed
        try {
            if(!listSalas.isSelectionEmpty())
                server.closeRoom(listSalas.getSelectedValue());
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnFecharSalaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirSala;
    private javax.swing.JButton btnFecharSala;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtfNomeSala;
    private javax.swing.JList<String> listSalas;
    // End of variables declaration//GEN-END:variables

    public void Atualiza(ArrayList salas) throws RemoteException{
        DefaultListModel info = new DefaultListModel();
        for (int i = 0; i < salas.size(); i++) {
            RoomChat r = (RoomChat) salas.get(i);
            info.addElement(r.getRoomName());
        }
        listSalas.setModel(info);
    }   
}
