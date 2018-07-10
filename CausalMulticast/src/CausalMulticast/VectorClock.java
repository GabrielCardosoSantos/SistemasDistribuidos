/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CausalMulticast;

import java.util.HashMap;

/**
 *
 * @author gabri
 */
public class VectorClock implements Cloneable{
    HashMap<String, Integer> vector;
    
    public VectorClock(){
        vector = new HashMap<>();
    }
    
    public HashMap<String, Integer> getVector(){
        return vector;
    }
    
    public void set(String nome, int valor){
        vector.put(nome, valor);
    }
    
    public int get(String nome){
        if(!vector.containsKey(nome)){
            vector.put(nome, 0);
        }
        return vector.get(nome);
    }
    
    
    
}
