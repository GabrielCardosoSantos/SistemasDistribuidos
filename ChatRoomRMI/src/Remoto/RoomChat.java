/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Ola voce tbm
package Remoto;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gabri
 */
public class RoomChat extends UnicastRemoteObject implements IRoomChat{
    public String roomName;
    public Map<String, IUserChat> userList;
    
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
        if(userList.containsKey(usrName)){
            sendMsg(usrName, "saindo da sala.");
            userList.remove(usrName);
        }
    }

    @Override
    public void closeRoom() throws RemoteException{
        sendMsg("Servidor", "sala finalizada");
        userList.clear();
    }

    @Override
    public String getRoomName(){
        return roomName;
    }
    
}
