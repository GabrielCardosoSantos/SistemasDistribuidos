/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Produtor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author gabri
 */
public class Produtor {
    private byte[] dado;
    private DatagramSocket socket;
    private final DatagramPacket envio;
    private Mensagem msg;
    private InetAddress ip;
    private int porta;
    private int tempo;
    
    public Produtor(int tempo, int porta, String ip) throws UnknownHostException, SocketException{
        this.ip = InetAddress.getByName(ip);
        this.porta = porta;
        this.tempo = tempo;
        this.socket = new DatagramSocket();
        this.msg = new Mensagem("PUSH", -1);
        this.dado = new byte[3];
    }
    
    private byte[] serialize (Object msg) 
    {
        byte[] serializedMessage = null;
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(msg);
            serializedMessage = bout.toByteArray();
        }catch (java.io.IOException e2){
            System.out.println("Erro ao tentar serializar mensagem.");
            System.exit(1);
        }
        return serializedMessage;
    }
    public static void main(String[] args) throws UnknownHostException, SocketException, InterruptedException, IOException{
        Produtor p = new Produtor(5000, 2020, "127.0.0.1");
        
        while(true){       
            
            this.envio = new DatagramPacket(dado, dado.length, p.ip, p.porta);
            p.socket.send(p.envio);
            System.out.println("Produtor : Produzindo ..");
            Thread.sleep(p.tempo);
        }
    }
}
