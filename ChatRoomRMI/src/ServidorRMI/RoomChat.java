/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Testas commit
package ServidorRMI;

import Remoto.*;
import java.awt.List;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabri
 */
public class RoomChat extends UnicastRemoteObject implements IRoomChat{
    String roomName;
    List mensagens;
    
    public RoomChat(String roomName) throws RemoteException {
        this.roomName = roomName;
        List mensagens = new List();
    }

    @Override
    public void sendMsg(String usrName, String msg) throws RemoteException, AccessException {
        mensagens.add(usrName + ": " + msg);
        Registry registry = LocateRegistry.getRegistry(2020);
        registry.rebind(roomName, this);
    } 

    @Override
    public void joinRoom(String usrName) throws RemoteException {
        Registry registry = LocateRegistry.getRegistry(2020);
        
        try{
            RoomChat sala = (RoomChat) registry.lookup(usrName);
        } catch (NotBoundException ex) {
            Logger.getLogger(RoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void leaveRoom(String usrName) throws RemoteException {
        Registry registry = LocateRegistry.getRegistry(2020);
        try {
            RoomChat sala = (RoomChat) registry.lookup(usrName);
        } catch (NotBoundException ex) {
            Logger.getLogger(RoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void closeRoom() throws RemoteException {
        
    }
    
}
