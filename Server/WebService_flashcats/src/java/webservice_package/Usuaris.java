/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice_package;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

/**
 *
 * @author jzorita
 */
public class Usuaris {
    private HashMap<String, String> llistaUsuaris = new HashMap<String, String>();

    public HashMap<String, String> getLlistaUsuaris() {
        return llistaUsuaris;
    }

    public void setLlistaUsuaris(HashMap<String, String> llistaUsuaris) {
        this.llistaUsuaris = llistaUsuaris;
    }
    
    public boolean connectat(String nom){
        if(llistaUsuaris.containsValue(nom)) return true;
        else return false;
    }
    
    public String getClau(String nom){
        for(Entry<String, String> entry : llistaUsuaris.entrySet()){
            if(Objects.equals(nom, entry.getValue())){
                return entry.getKey();                
            }           
        }
        return "-1";        
    }
        
    
    public void inserir(String nom, String clau){
        llistaUsuaris.put(clau, nom);        
    }
    
    public void esborrar(String clau){
        llistaUsuaris.remove(clau);
    }
   
    
    
    
        
}
