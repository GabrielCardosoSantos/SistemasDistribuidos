/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Produtor;

import Consumidor.*;
import java.io.Serializable;

/**
 *
 * @author gabri
 */
public class Mensagem implements Serializable {
    public String tipo;
    public int numero;

    public Mensagem(String tipo, int numero) {
        this.tipo = tipo;
        this.numero = numero;
    }

    public Mensagem() {
        this.tipo = "";
        this.numero =  0;
    }

    public void setTipo(String t) {
        tipo = t;
    }

    public String getTipo() {
        return tipo;
    }

    public void setNumero(int n) {
        numero = n;
    }

    public int getNumero() {
        return numero;
    }
}
