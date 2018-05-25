/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consumidor;
import java.io.IOException;
import java.net.*;
import java.util.Random;
/**
 *
 * @author gabri
 */
public class ConsumidorDatagram extends Thread {
    
    private DatagramSocket socket;
    private InetAddress ip;
    private String identificacao;
    private byte[] dado;
    private int porta;
    private int valor;
    
    public ConsumidorDatagram(String id, int porta, String ip) throws SocketException, UnknownHostException{
        this.dado = new byte[2];
        this.identificacao = id;
        this.porta = porta;
        this.ip = InetAddress.getByName(ip);
        this.socket = new DatagramSocket();
    }
    
    public static void main(String[] args) throws SocketException, UnknownHostException {
        ConsumidorDatagram c1 = new ConsumidorDatagram("1", 2020, "127.0.0.1");
        c1.start();
    }
    
    @Override
    public void run(){
        try{
            while(true){
                Random gera = new Random();
                String num = Integer.toString((gera.nextInt(40)+10));
                dado = num.getBytes();
                DatagramPacket pacote = new DatagramPacket(this.dado, this.dado.length, this.ip, this.porta);
                
                System.out.println("Consumidor [" + this.identificacao + "]: Solicitando combustível " + (Integer.valueOf(num.trim())));
                socket.send(pacote);
                System.out.println("Consumidor [" + this.identificacao + "]: Aguardando resposta ..");
                DatagramPacket resposta = new DatagramPacket(this.dado, this.dado.length);
                
                socket.receive(resposta);
                dado = resposta.getData();
                
                if(Integer.parseInt(new String(dado)) > 0)
                    System.out.println("Consumidor [" + this.identificacao + "]: Abasteci - " + new String(dado));
                else
                    System.out.println("Consumidor [" + this.identificacao + "]: Sem combustivel");
            }
            
        }catch(IOException e){
            System.out.println(e.toString());
        }
    }
    
}
