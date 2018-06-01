/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientRMI;

import Remoto.*;
import ServidorRMI.ServerChat;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author gabri
 */
public class UserChat extends UnicastRemoteObject implements IUserChat {
    String usrName;
    IRoomChat sala;
    frmPrincipal frm;
    
    public UserChat() throws RemoteException{
        frm = new frmPrincipal(this);
        frm.setVisible(true);
        ThreadAtt t = new ThreadAtt(frm, this);
        t.start();
    }
    
    public void SetName(String name){
        usrName = name;    
    }
    
    public void EnviaMsg(String msg) throws RemoteException{
        sala.sendMsg(usrName, msg);
    }
    
    public void EntraSala(String nomeSala) throws RemoteException, NotBoundException{
        Registry reg = LocateRegistry.getRegistry(2020);
        sala = (IRoomChat) reg.lookup(nomeSala);
        System.out.println(sala.getRoomName() + " : " + usrName);
        sala.joinRoom(usrName, this);
    }
    
    public static void main (String[] args) throws RemoteException{
        UserChat user = new UserChat();
    }
    
    @Override
    public void deliverMsg(String senderName, String msg){
        frm.AdicionaTexto("[" + senderName + "]: " + msg);
    }    
}
