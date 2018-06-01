/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServidorRMI;

import Remoto.IRoomChat;
import Remoto.IServerChat;
import Remoto.IUserChat;
import Remoto.RoomChat;
import java.awt.List;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author gabri
 */
public class ServerChat extends UnicastRemoteObject implements IServerChat {
    private Registry registry;
    private ArrayList<IRoomChat> roomList;
    private frmPrincipal frm;
    
    public ServerChat() throws RemoteException, AlreadyBoundException{
        registry = LocateRegistry.createRegistry(2020);       
        registry.rebind("Servidor", this);
        System.out.println("[Servidor]: Conexão aberta na porta 2020");
    }
    
    @Override
    public ArrayList getRooms(){
        return roomList;
    }

    @Override
    public void createRoom(String roomName){
        try {
            if(!VerificaNome(roomName)){
                IRoomChat sala = new RoomChat(roomName);
                try {
                    this.registry.bind(roomName, sala);
                    roomList.add(sala);
                } catch (AlreadyBoundException ex) {
                    Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Sala [" + roomName + "] criada.");
                
            }
            frm.Atualiza(roomList);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean VerificaNome(String nome) throws RemoteException{
        for(int i = 0; i < roomList.size(); i++){
            if(roomList.get(i).getRoomName().equals(nome)){
                return true;
            }
        }
        return false;
    }
    
    public void Inicia(){
        frm = new frmPrincipal(this);
        roomList = new ArrayList<>();
        frm.setVisible(true);
    }
    
    public void closeRoom(String roomName) throws RemoteException, NotBoundException {
        RoomChat chat = (RoomChat) registry.lookup(roomName);
        if(chat != null){
            chat.closeRoom();
            this.registry.unbind(roomName);
            this.roomList.remove(chat);
            frm.Atualiza(roomList);
        }
    }
    public static void main(String[] args) {
        try {
            ServerChat serv = new ServerChat();
            serv.Inicia();
        }catch (Exception e) {
            System.out.println("Servidor erro" + e);
        }
    }
}
