/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CausalMulticast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gabri
 */
public class VectorClock implements Serializable, Cloneable{
    HashMap<String, Integer> vector;
    
    public VectorClock(){
        vector = new HashMap<>();
    }
    
    public HashMap<String, Integer> getVector(){
        return vector;
    }
    
    public int Add(String user){
        if(!vector.containsKey(user)){
            vector.put(user, -1);
        }
        
        int value = vector.get(user) + 1;
        vector.put(user, value);
        return value;
    }
    
    
    public void Set(String nome, int valor){
        if(!vector.containsKey(nome))
            vector.put(nome, valor);
    }
    
    public boolean ComparaVector(String userAqui, String userVeio, VectorClock v){
        //HashMap<String, Integer> hash1 = this.vector;
        //HashMap<String, Integer> hash2 = v.vector;
        
        int i1 = v.vector.get(userVeio);
        int i2 = this.vector.get(userVeio);
        
        return i1 == i2;
    }
    
    public boolean ComparaVector(VectorClock v){
        HashMap<String, Integer> hash1 = this.vector;
        HashMap<String, Integer> hash2 = v.vector;
        
        return mapsAreEqual(hash1, hash2);
    }
    
    public boolean mapsAreEqual(Map<String, Integer> mapA, Map<String, Integer> mapB) {
        try{
            for (String k : mapB.keySet())
            {
                if (!mapA.get(k).equals(mapB.get(k))) {
                    return false;
                }
            } 
            for (String y : mapA.keySet())
            {
                if (!mapB.containsKey(y)) {
                    return false;
                }
            } 
        } catch (NullPointerException np) {
            return false;
        }
    return true;
}
    
    
    public int get(String nome){
        if(!vector.containsKey(nome)){
            vector.put(nome, 0);
        }
        
        return vector.get(nome);
    }
    
    
    
}
