/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CausalMulticast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author gabri
 */
public class CMChannel{
    int cont;
    byte[] buffer;
    
    ArrayList<String> users;
    ICausalMulticast c;
    ThreadGrupo threadGrupo;
    ThreadMensagens threadMsg;
    Mensagem mensagem;
    MulticastSocket socket;
    DatagramSocket socketUni;
    
    public CMChannel(ICausalMulticast c) throws IOException{
        cont = 0;
        this.c = c;        
        users = new ArrayList<>();
        buffer = new byte[1024];  
        socket = new MulticastSocket(2020);
        socketUni = new DatagramSocket(3030);
        threadGrupo = new ThreadGrupo(this, socket);        
        threadMsg = new ThreadMensagens(this, socketUni);
        mensagem = new Mensagem();
    }
    
    public void join(String user, String dest) throws UnknownHostException, IOException{
        if(user.length() > 0 && dest.length() > 0){
            users.add(user);
            threadGrupo.Iniciar(dest);
            threadGrupo.start();
            threadGrupo.running = true;
            threadMsg.start();
            
            String s = "Entrou na sala: " + dest + " --- IP: " + user;
            buffer = s.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(dest), 2020);
            socketUni.send(sendPacket);
        }
    }

    public void mcsend(String msg, String dest) throws IOException{
        msg = msg + "\n";
        buffer = msg.getBytes();
        
        for(String s : users){
            DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(s), 3030);
            socketUni.send(sendPacket);
        }        
        
        buffer = new byte[1024];
    }
    
    
    
    public void leave(String user, String dest) throws IOException{
        users.clear();
        mcsend("[Sistema] Usuario " + user + " saiu.", dest);
        threadGrupo.running = false;
    }
    
}
