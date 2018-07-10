/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CausalMulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabri
 */
public class ThreadMensagens extends Thread{
    byte[] buf;
    boolean running;
    DatagramSocket socket;
    CMChannel canal;
    
    public ThreadMensagens(CMChannel c, DatagramSocket socket){
        this.socket = socket;
        buf = new byte[1024];
        this.canal = c;
        running = true;
    }
    
    @Override
    public void run(){
        while(running){
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                canal.c.deliver(new String(buf));
                buf = new byte[1024];
            } catch (IOException ex) {
                Logger.getLogger(ThreadMensagens.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
