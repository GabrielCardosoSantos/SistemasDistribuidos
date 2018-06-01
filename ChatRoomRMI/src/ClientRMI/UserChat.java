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
import java.util.ArrayList;

/**
 *
 * @author gabri
 */
public class UserChat extends UnicastRemoteObject implements IUserChat {
    frmPrincipal frm;
    
    public UserChat(frmPrincipal frm) throws RemoteException{
        this.frm = frm;
    }
    
    @Override
    public void deliverMsg(String senderName, String msg){
        frm.AdicionaTexto("[" + senderName + "]: " + msg);
    }    
}
