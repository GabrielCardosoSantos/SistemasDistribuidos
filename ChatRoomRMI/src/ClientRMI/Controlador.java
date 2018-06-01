/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientRMI;

import Remoto.IRoomChat;
import Remoto.IServerChat;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 *
 * @author gabri
 */
public class Controlador {
    UserChat user;
    String usrName;
    IRoomChat sala;
    frmPrincipal frm;
    
    public Controlador() throws RemoteException{
        frm = new frmPrincipal(this);
        frm.setVisible(true);
        user = new UserChat(frm);
        ThreadAtt t = new ThreadAtt(frm, user);
        t.start();
    }
    
    public void SetName(String name){
        usrName = name;    
    }
    
    public void EnviaMsg(String msg) throws RemoteException{
        if(sala != null)
            sala.sendMsg(usrName, msg);
    }
    
    public void EntraSala(String nomeSala) throws RemoteException, NotBoundException{
        Registry reg = LocateRegistry.getRegistry(2020);
        sala = (IRoomChat) reg.lookup(nomeSala);
        sala.joinRoom(usrName, user);
    }
    
    public void NovaSala(String nome) throws RemoteException, NotBoundException{
        Registry reg = LocateRegistry.getRegistry(2020);
        IServerChat server = (IServerChat) reg.lookup("Servidor");
        ArrayList l = server.getRooms();
        
        if(!l.contains(nome)){
            server.createRoom(nome);
        }
    }
    
    public void SairSala() throws RemoteException{
        sala.leaveRoom(usrName);
        sala = null;
    }
    
    public static void main (String[] args) throws RemoteException{
        Controlador user = new Controlador();
    }
}
