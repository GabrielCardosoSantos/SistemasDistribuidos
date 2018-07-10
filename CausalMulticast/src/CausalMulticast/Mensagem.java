/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CausalMulticast;

import java.io.Serializable;

/**
 *
 * @author gabri
 */
public class Mensagem implements Serializable{    
    int tipo;
    String texto;
    String user;
    String dest;
    VectorClock clocks;
    
    public Mensagem(){
        
    }
    
    public Mensagem(int tipo, String texto, String user, String dest){
        this.dest = dest;
        this.texto = texto;
        this.tipo = tipo;
        this.user = user;
    }
    
    public void Clear(){
        tipo = 0;
        texto = "";
        user = "";
        dest = "";
        clocks = null;
    }
    
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public VectorClock getClocks() {
        return clocks;
    }

    public void setClocks(VectorClock clocks) {
        this.clocks = clocks;
    }
    
}
