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
public interface IUserChat extends Remote {
    public void deliverMsg(String senderName, String msg) throws RemoteException;
}
