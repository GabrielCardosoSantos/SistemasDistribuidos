/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CausalMulticast;

import java.awt.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.swing.JOptionPane;

/**
 *
 * @author gabri
 */
public class CMChannel{
    int cont;
    byte[] buffer;
    String user;
    ArrayList<String> users;
    ICausalMulticast c;
    ThreadGrupo threadGrupo;
    ThreadMensagens threadMsg;
    MulticastSocket socket;
    DatagramSocket socketUni;
    VectorClock vectorClocks;
    HashMap<String, Mensagem> atrasadas;
    ArrayList<Mensagem> historico;
    
    public CMChannel(ICausalMulticast c) throws IOException{
        cont = 0;
        this.c = c;  
        users = new ArrayList<>();
        buffer = new byte[10024];  
        socket = new MulticastSocket(2020);
        socketUni = new DatagramSocket(3030);
        threadGrupo = new ThreadGrupo(this, socket);        
        threadMsg = new ThreadMensagens(this, socketUni);
        vectorClocks = new VectorClock();
        atrasadas = new HashMap<>();
        historico = new ArrayList<>();
    }
    
    public void join(String user, String dest) throws UnknownHostException, IOException{
        if(user.length() > 0 && dest.length() > 0){
            this.user = user;
            threadGrupo.Iniciar(user, dest);       
            Mensagem m = new Mensagem();
            m.setUser(user);
            
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(output);
            os.writeObject(m);
            
            buffer = output.toByteArray();
            
            DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(dest), 2020);
            socket.send(sendPacket);
            
            threadGrupo.running = true;
            threadGrupo.start();
            
            threadMsg.running = true;
            threadMsg.start();
        }
    }

    public void mcsend(String msg, String dest) throws IOException{        
        String str = "[" + user + "]: " + msg + "\n";
        Mensagem m = new Mensagem();
        m.setTexto(str);
        m.setClocks(vectorClocks);
        m.setDest(dest);
        m.setUser(user);
        
        String aux = JOptionPane.showInputDialog("Informe o IP para remover:");
        if(aux == null)
            aux = "";
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(output);
        os.writeObject(m);
        
        buffer = output.toByteArray();
        
        for(String s : users){
            if(!aux.equals(s)){
                System.out.println("Enviando para: " + s + " Vetor: " + vectorClocks.vector.toString());
                DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(s), 3030);
                socketUni.send(sendPacket);
            }else{
                String[] split = aux.split(";");
                for(String s1 : split){
                    Mensagem m1 = new Mensagem();
                    VectorClock v1 = new VectorClock();
                    v1.vector.putAll(m.clocks.vector);
                    m1.setClocks(v1);
                    m1.setTexto(m.texto);
                    m1.setUser(m.user);
                    atrasadas.put(s1, m1);
                }
                    
                System.out.println("Nao enviara para: "+ s);
            }
        }

        if(atrasadas.size() > 0){
            EsperaMsg();
        }
        
        buffer = new byte[1024];
    }
    
    public void EsperaMsg() throws IOException{
        int i = JOptionPane.showConfirmDialog(null, "Pressione para desbloquear.");
        
        if(i == 0){
            for (Entry<String, Mensagem> entry : atrasadas.entrySet()) {
                ByteArrayOutputStream output = new ByteArrayOutputStream();

                Mensagem m = entry.getValue();
                String usr = entry.getKey();
                System.out.println("Enviando atrasada: Vetor-" + m.clocks.vector.toString());
                ObjectOutputStream os = new ObjectOutputStream(output);
                os.writeObject(m);
                    
                byte[] buf = output.toByteArray();

                DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, InetAddress.getByName(usr), 3030);
                socketUni.send(sendPacket);
            }
            atrasadas.clear();
            
        }
        
    }
    
    public void leave(String user, String dest) throws IOException{
        mcsend("[Sistema] Usuario " + user + " saiu. \n", dest);
        threadGrupo.running = false;
        threadMsg.running = false;
        socket.disconnect();
        socket.close();
        socketUni.disconnect();
        socketUni.close();
    }
    
    public String getUser(){
        return user;
    }
    
    public synchronized void addUser(String s){
        users.add(s);
    }
    
    public boolean contem(String a){
        for(String s: users){
            if(a.equals(s)){
                return true;
            }
        }
        
        return false;
    }
    
}
