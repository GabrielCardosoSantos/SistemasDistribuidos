/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CausalMulticast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stoll
 */
public class ThreadGrupo extends Thread {
    MulticastSocket socket;
    byte[] buffer;
    boolean running;
    CMChannel canal;
    
    public ThreadGrupo(CMChannel t, MulticastSocket socket){
        canal = t;
        this.socket = socket;
    }
    
    public void Iniciar(String dest) throws IOException{
        socket.joinGroup(InetAddress.getByName(dest));
        System.out.println("Thread Grupo - Start");
        running = true;
    }
    
    @Override
    public void run(){
        while(running){
            buffer = new byte[1024];
            DatagramPacket receber = new DatagramPacket(buffer,buffer.length);
            try {
                socket.receive(receber);
                String s = new String(buffer);
                canal.c.deliver(s);
                
             } catch (IOException ex) {
                Logger.getLogger(ThreadGrupo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
