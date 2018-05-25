/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Posto;

/**
 *
 * @author gabri
 */
public class Combustivel{
    private int combustivel;
    
    public Combustivel(){
        combustivel = 100000;
    }
    
    public synchronized void push(){
        if(combustivel + 10000 >= 100000){     //verifica limite do tanque de combustivel
            combustivel = 100000;
        }else{
            combustivel += 10000;
        }
    }    
    public synchronized int pop(int num){
        if(combustivel == 0){
            return -1;
        }else{ 
            if((combustivel - num) < 0){ //verifica se não tirou mais do que tem no tanque
                combustivel = 0;
                return (num - combustivel);
            }                            
            combustivel -= num;
            return num;
        }
    }    
    public synchronized int ret_pop(){
        return this.combustivel;
    }
}
