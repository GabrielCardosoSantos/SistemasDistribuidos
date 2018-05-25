/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Produtor;

import java.io.IOException;
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
    private DatagramSocket socket;
    private final DatagramPacket envio;
    private InetAddress ip;
    private int porta;
    private int tempo;
    
    public Produtor(int tempo, int porta, String ip) throws UnknownHostException, SocketException{
        String num = Integer.toString(-1);
        byte[] dado = num.getBytes();
        this.ip = InetAddress.getByName(ip);
        this.porta = porta;
        this.tempo = tempo;
        this.envio = new DatagramPacket(dado, dado.length, this.ip, this.porta);
        this.socket = new DatagramSocket();
    }
    
    
    public static void main(String[] args) throws UnknownHostException, SocketException, InterruptedException, IOException{
        Produtor p = new Produtor(5000, 2020, "127.0.0.1");
        
        while(true){            
            p.socket.send(p.envio);
            System.out.println("Produtor : Produzindo ..");
            Thread.sleep(p.tempo);
        }
    }
}
