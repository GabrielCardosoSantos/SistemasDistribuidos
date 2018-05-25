/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Posto;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabri
 */
public class PostoDatagram extends Thread{
    private final DatagramSocket socket;
    private final DatagramPacket recebido;
    private final InetAddress ip;
    private Combustivel combustivel;
    private final int porta;
    private byte[] dado;
    
    public PostoDatagram(int porta, String ip) throws SocketException, UnknownHostException {
        this.dado = new byte[4];
        this.porta = porta;
        this.ip = InetAddress.getByName(ip);
        this.socket = new DatagramSocket(this.porta);
        this.recebido = new DatagramPacket(dado, dado.length);
        this.combustivel = new Combustivel();
    }
    
    public void Escuta() throws IOException{
        this.socket.receive(this.recebido);
    }
    
    public int Pedido(){
        return (Integer.parseInt(new String(this.recebido.getData()).trim()));
    }
    
    public void Responde(DatagramPacket p) throws IOException{
        this.socket.send(p);
    }
    
    public Combustivel GetCombustivel(){
        return this.combustivel;
    }
    
    public String Endereco(){
        return this.ip.getHostAddress();
    }
    
    public int Consome(int quantidade){
        return this.combustivel.pop(quantidade);
    }
    
    public void Produz(){
        this.combustivel.push();
    }
    
    @Override
    public void run(){
        
    }
    
    public static void main(String[] args){
        try {
            PostoDatagram posto = new PostoDatagram(2020, "127.0.0.1");
            System.out.println("Posto aberto no endereço: " + posto.Endereco());
            
            while(true){
                posto.Escuta();
                Atendente a = new Atendente(posto, posto.recebido.getPort(), posto.recebido.getAddress(), posto.Pedido());
                a.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(PostoDatagram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
