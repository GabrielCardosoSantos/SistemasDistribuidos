/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Remoto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author gabri
 */
public interface IServerChat extends Remote {
    public ArrayList getRooms() throws RemoteException;
    public void createRoom(String roomName) throws RemoteException;
}
