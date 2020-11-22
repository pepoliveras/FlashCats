
package webservice_package;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Classe amb mètodes auxiliar utilitzats a altres classes.
 * @author José Luis Zorita Gutiérrez
 */
public class Auxiliar {
        
    /**
     * Mètode que genera un codi aleatori de 32 caràcters amb classe UUID
     * @return codi generat, treient els guions
     */
    static public String generar_codi(){
        return UUID.randomUUID().toString().replace("-","");        
    }   
    
    /**
     * Mètode per saber si existeix un tema a la base de dades, donat un codi.     
     * @param codi Codi del tema.
     * @return <b>1</b> = Existeix.<br> <b>0</b> = No existeix.<br>
     * <b>-3</b> = Error en la consulta.
     */
    static public int existeixCodiTema(long codi){

        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(con.consulta(con.EXISTEIX_CODI_TEMA));
            statement.setLong(1, codi);            
            int existeix;
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                existeix = Integer.parseInt(result.getString(1));
            }
            con.disconnect();
            return existeix;
        }catch(SQLException ex){
            return -3;
        }
    } 
     /**
     * Mètode per saber si existeix una flashcard a la base de dades, donat un codi.
     * @param codi Codi de la flashcard.
     * @return <b>1</b> = Existeix. <br><b>0</b> = No existeix.<br>
     * <b>-3</b> = Error en la consulta.
     */
    static public int existeixCodiFlashcard(long codi){

        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(con.consulta(con.EXISTEIX_CODI_FLASHCARD));
            statement.setLong(1, codi);            
            int existeix;
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                existeix = Integer.parseInt(result.getString(1));
            }
            con.disconnect();
            return existeix;
        }catch(SQLException ex){
            return -3;
        }
    }        
    
    /**
     * Mètode per obtenir el codi d'un usuari donat el seu nom.
     * @param nom Non de l'usuari.
     * @return Codi de l'usuari. <br><b>-1</b> = El usuari no existeix.<br>
     * <b>-3</b> = Error en la consulta.
     */
    static public int getCodiUsuari(String nom){

        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(con.consulta(con.GET_CODIUSUARI));
            statement.setString(1, nom);
            int codi;
            try (ResultSet result = statement.executeQuery()) {
                if(result.next())
                    codi = Integer.parseInt(result.getString(1));
                else codi = -1;
            }
            con.disconnect();
            return codi;
        }catch(SQLException ex){
            return -3;
        }
    }
    
    /**
     * Mètode que modifica la extensió d'un fitxer a la BDD.
     * @param codi_flashcard Codi de la flashcard.
     * @param extensio Extensio del fitxer.
     * @return <b>1</b> = Canvi correcte <br><b>-3</b> = Error en consulta.
     */    
    static public int setExtensio(long codi_flashcard, String extensio){        
        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = 
                    con.getConnection().prepareStatement(con.consulta(con.SET_EXTENSIO));            
            statement.setString(1, extensio);
            statement.setLong(2, codi_flashcard);
            statement.executeQuery();                
            con.disconnect();
            return 1;
        }catch(SQLException ex){
            return -3;
        }
    }    
}
