
package webservice_package;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Classe per gestionar els usuaris que es connecten des de els clients.
 * @author José Luis Zorita Gutiérrez
 */
public class Usuaris {
    /**
     * Variable que emmagatzema els usuaris: <br>
     * 1r valor (<b>key</b>): Codi de sessió <br>
     * 2n valor (<b>value</b>): Nom de l'usuari
     */
    private HashMap<String, String> llistaUsuaris = new HashMap<String, String>();

    /**
     * 
     * @return llista d'usuaris
     */
    public HashMap<String, String> getLlistaUsuaris() {
        return llistaUsuaris;
    }

    /**
     *
     * @param llistaUsuaris modificar llista d'usuaris
     */
    public void setLlistaUsuaris(HashMap<String, String> llistaUsuaris) {
        this.llistaUsuaris = llistaUsuaris;
    }
    
    /**
     * Mètode que comprova si un usuari està connectar (està dins el HashMap).
     * @param nom Nom de l'usuari.
     * @return <b>true</b> = Usuari connectat.<br><b>false</b> = Usuari no connectat.
     */
    public boolean connectat(String nom){
        if(llistaUsuaris.containsValue(nom)) return true;
        else return false;
    }
    
    /**
     * Mètode que retorna el tipus d'usuari donat un codi de sessió.
     * @param codi codi de sessió de l'usuari.
     * @return <b>0</b> = Usuari normal.<br><b>1</b> = Admin <br><b>-1</b> = No connectat.
     */
    public int tipsUsuari(String codi){
        if(!llistaUsuaris.containsKey(codi)){
            return -1;
        }else{
            char tipus = codi.charAt(0);            
            System.out.println("Tipus: " + tipus);
            if(tipus == '0') return 0;
            else return 1;                
        }                
    }
    
    /**
     * Mètode que donat el nom d'un usuari, retorna el seu codi de sessió.
     * @param nom Nom de l'usuari.
     * @return codi de sessió d'un usuari
     */
    public String getClau(String nom){
        for(Entry<String, String> entry : llistaUsuaris.entrySet()){
            if(Objects.equals(nom, entry.getValue())){
                return entry.getKey();                
            }           
        }
        return "-1";        
    }
        
    /**
     * Mètode que insereix un nou usuari dins el HashMap llistaUusuaris.
     * @param nom Nom de l'usuari.
     * @param clau Codi de sessió.
     */
    public void inserir(String nom, String clau){
        llistaUsuaris.put(clau, nom);        
    }
    
    /**
     * Mètode que esborra un usuari del HashMap llistausuaris.
     * @param clau codi de sessió
     */
    public void esborrar(String clau){
        llistaUsuaris.remove(clau);
    }    
}
