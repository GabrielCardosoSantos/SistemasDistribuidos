/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CausalMulticast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
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
    //DatagramSocket socketUni;
    String grupo;
    String user;
    ArrayList<String> users;
    
    public ThreadGrupo(CMChannel t, MulticastSocket socket) throws SocketException{
        canal = t;
        this.socket = socket;
        users = new ArrayList<>();
    }
    
    public void Iniciar(String nome, String dest) throws IOException{
        socket.joinGroup(InetAddress.getByName(dest));
        user = nome;
        grupo = dest;
        running = true;
    }
    
    @Override
    public void run(){
        while(running){
            buffer = new byte[1024];
            DatagramPacket receber = new DatagramPacket(buffer,buffer.length);
            try {
                socket.receive(receber);
                byte[] data = receber.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                
                Mensagem m = (Mensagem) is.readObject();
                
                System.out.println("Tentando entrar: " + m.user);
                
                if(!canal.users.contains(m.user)){
                    canal.users.add(m.user);
                    canal.vectorClocks.Add(m.user);
                    System.out.println("Usuarios conectados: " + canal.users.toString());
                    canal.c.deliver("[" + m.user + "]: entrou na sala \n");
                    
                    Mensagem m1 = new Mensagem();
                    m1.setUser(user);
                    
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    ObjectOutputStream os = new ObjectOutputStream(output);
                    os.writeObject(m1);
                    buffer = output.toByteArray();
                    
                    DatagramPacket p = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(m.user), 2020);
                    socket.send(p);
                    
                }
                
            } catch (IOException ex) {
                Logger.getLogger(ThreadGrupo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadGrupo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
