/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Ola voce tbm
package Remoto;

import Remoto.*;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabri
 */
public class RoomChat extends UnicastRemoteObject implements IRoomChat{
    String roomName;
    Map<String, IUserChat> userList;
    
    public RoomChat(String roomName)throws RemoteException{
        this.roomName = roomName;
        userList = new HashMap<>();
    }

    @Override
    public void sendMsg(String usrName, String msg) throws RemoteException {
        for(Map.Entry mp : userList.entrySet()){
            IUserChat userRemote = (IUserChat) mp.getValue();
            userRemote.deliverMsg(usrName, msg);
        }
    }    
    
    @Override
    public void joinRoom(String usrName, IUserChat user) throws RemoteException{
        userList.put(usrName, user);
        sendMsg(usrName, "entrou na sala");
    }

    @Override
    public void leaveRoom(String usrName) throws RemoteException {
        userList.remove(usrName);
        sendMsg(usrName, "saindo da sala.");
    }

    @Override
    public void closeRoom() throws RemoteException{
        sendMsg("Servidor", "sala finalizada");
    }

    @Override
    public String getRoomName(){
        return roomName;
    }
    
}
