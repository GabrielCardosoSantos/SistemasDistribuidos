/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientRMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author gabri
 */
public class ThreadAtt extends Thread{
    UserChat user;
    
    ThreadAtt(frmPrincipal frm, UserChat user){
        this.user = user;
    }
    
    @Override
    public void run() {
        while(true){
            try {            
                Registry r = LocateRegistry.getRegistry(2020);
                DefaultListModel l = new DefaultListModel();
                for (String list : r.list()) {
                    if(!list.equals("Servidor"))
                        l.addElement(list);
                }
                
                user.frm.Atualiza(l);
            } catch (RemoteException ex) {
                DefaultListModel l = new DefaultListModel();
                user.frm.Atualiza(l);
                Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadAtt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
