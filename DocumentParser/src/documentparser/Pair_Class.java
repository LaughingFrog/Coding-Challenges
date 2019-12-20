/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentparser;
import java.util.*; 
/**
 *
 * @author crand
 */
public class Pair_Class{
    private String key = "";
    private int value = 0;
    
    Pair_Class(String word,int count){
        key = word;
        value = count;
    }
    
    public void update(){
        value++;
    }
    
    public String getKey(){
        return key;
    }
    
    public int getValue(){
        return value;
    }   
}