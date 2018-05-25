/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServidorRMI;

import Remoto.IServerChat;
import java.awt.List;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabri
 */
public class ServerRoomChat extends UnicastRemoteObject implements IServerChat {
    Registry registry;     
    static ArrayList<String> salas = new ArrayList<String>();
    static frmPrincipal frm;
    
    public ServerRoomChat() throws RemoteException{
        super();
        try{
            registry = LocateRegistry.createRegistry(2020);           
            registry.rebind("Servidor", this);
            System.out.println("Servidor iniciado!");
        }catch(RemoteException e){
            System.out.println("Erro:" + e);
        }
    }
    
    @Override
    public String[] getRooms() throws RemoteException {
        return this.registry.list();
    }

    @Override
    public void createRoom(String roomName) throws RemoteException {        
        try{
            RoomChat room = new RoomChat(roomName);
            String s[] = this.registry.list();
            if(!VerificaNome(s, roomName)){
                this.registry.bind(roomName, (Remote) room);
                salas.add(roomName);
                System.out.println("Sala Criada:" + roomName);
            }
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(ServerRoomChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        frm.Atualiza();
    }
    
    public boolean VerificaNome(String[] list, String nome){
        for(String s : list){
            if(s.equals(nome)){
                return true;
            }
        }
        return false;
    }
    
    public void closeRoom(String salaNome) throws RemoteException, NotBoundException{
        RoomChat r = (RoomChat) this.registry.lookup(salaNome);
        r.sendMsg("Servidor", "Sala fechada");
        this.registry.unbind(salaNome);
        salas.remove(salaNome);
        System.out.println("Sala removida");
        frm.Atualiza();
    }
    
    public static void main(String[] args) throws RemoteException {
        ServerRoomChat server = new ServerRoomChat();
        frm = new frmPrincipal(server, salas);
        frm.setVisible(true);
    }
}
