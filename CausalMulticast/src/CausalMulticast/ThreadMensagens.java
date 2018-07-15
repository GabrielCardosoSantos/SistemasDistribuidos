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
import java.net.DatagramSocket;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabri
 */
public class ThreadMensagens extends Thread{
    byte[] buf;
    boolean running;
    boolean achou;
    DatagramSocket socket;
    CMChannel canal;
    Mensagem m;
    
    public ThreadMensagens(CMChannel c, DatagramSocket socket){
        this.socket = socket;
        buf = new byte[10024];
        this.canal = c;
        running = true;
        achou = true;
    }
    
    @Override
    public void run(){
        while(running){
            System.out.println("[" + canal.user + "]: Vetor de clock: " + canal.vectorClocks.vector.toString());
            if(canal.historico.size() > 0){
                System.out.println("---Buffer---");
                for(Mensagem msg : canal.historico){
                    System.out.println("["+msg.texto+"]");
                }
                System.out.println("------------");
            }else{
                System.out.println("[Middleware] Buffer vazio.");
            }
            
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);    
                byte[] data = packet.getData();
                
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                
                m = (Mensagem) is.readObject();
                
                if(canal.vectorClocks.ComparaVector(m.clocks)){
                    canal.vectorClocks.Add(m.user);
                    canal.c.deliver(m.texto);
                    
                    if(canal.historico.size() > 0){
                        achou = true;
                        while(achou){
                            achou = false;
                            for(Mensagem m1 : canal.historico){
                                if(canal.vectorClocks.ComparaVector(m1.clocks)){
                                    canal.vectorClocks.Add(m1.user);
                                    canal.c.deliver(m1.texto);
                                    canal.historico.remove(m1);
                                    achou = true;
                                    break;
                                }
                            }
                        }
                    }
                }else{
                    System.out.println("Historico vetor: " + m.clocks.vector.toString());
                    canal.historico.add(m);
                }
                
                buf = new byte[10024];
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ThreadMensagens.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
