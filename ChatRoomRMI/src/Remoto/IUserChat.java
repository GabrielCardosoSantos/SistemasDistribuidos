/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Remoto;

import java.rmi.RemoteException;

/**
 *
 * @author gabri
 */
public interface IUserChat {
    public void deliverMsg(String senderName, String msg) throws RemoteException;
}
