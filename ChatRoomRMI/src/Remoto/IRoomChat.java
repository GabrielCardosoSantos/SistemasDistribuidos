/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Remoto;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author gabri
 */
public interface IRoomChat extends Remote{
    public void sendMsg(String usrName, String msg) throws RemoteException;
    public void joinRoom(String usrName, IUserChat user) throws RemoteException;
    public void leaveRoom(String usrName) throws RemoteException;
    public void closeRoom() throws RemoteException;
    public String getRoomName() throws RemoteException;
}
