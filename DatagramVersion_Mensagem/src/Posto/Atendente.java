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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabri
 */
public class Atendente extends Thread{
    private PostoDatagram posto;
    private DatagramSocket socket;
    private InetAddress ip;
    private int porta;
    private int pedido;
    
    public Atendente(PostoDatagram posto, int porta, InetAddress ip, int pedido) throws SocketException{
        this.posto = posto;
        this.porta = porta;
        this.ip = ip;
        this.pedido = pedido;
        this.socket = new DatagramSocket();
    }
    
    @Override
    public void run(){
        byte[] inf;
        if(this.pedido > 0){
            System.out.println("Posto: consumidor pediu - " + Integer.toString(this.pedido) );
            int resultado = this.posto.Consome(this.pedido);
            inf = Integer.toString(resultado).getBytes();
            DatagramPacket p = new DatagramPacket(inf, inf.length, this.ip, this.porta);
            System.out.println("Posto: enviando para consumidor - " + Integer.parseInt(new String(inf)));
            try {
                
                this.socket.send(p);
            } catch (IOException ex) {
                Logger.getLogger(Atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Posto: Tanque - " + Integer.toString(this.posto.GetCombustivel().ret_pop()));
        }else{
            System.out.println("Posto: produtor abasteceu");
            this.posto.Produz();
            System.out.println("Posto: Tanque - " + Integer.toString(this.posto.GetCombustivel().ret_pop()));
        }
    }
    
}
