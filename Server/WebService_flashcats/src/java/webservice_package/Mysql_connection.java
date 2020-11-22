
package webservice_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe amb dades i mètodes per connectar amb la base de dades.
 * 
 * @author José Luis Zorita Gutiérrez
 */
public class Mysql_connection {
    public final int LOGIN = 1;
    public final int SELECCIONA_TEMA = 2;
    public final int ALTA_TEMA = 3;
    public final int LLISTA_TEMES = 4;
    public final int INSERT_FLASHCARD = 5;
    public final int FILES_TEMES = 6;
    public final int LLISTA_FLASHCARDS = 7;
    public final int FILES_FLASHCARDS = 8;
    public final int TAGS_FLASHCARD = 9;
    public final int TAGS_TEMA = 10;
    public final int EXISTEIX_CODI_TEMA = 11;
    public final int GET_EXTENSIO = 12;
    public final int ESBORRAR_TEMA = 13;
    public final int ESBORRAR_FLASHCARD = 14;
    public final int AFEGIR_TAG = 15;
    public final int ESBORRAR_TAG = 16;
    public final int GET_CODIUSUARI = 17;
    public final int ALTA_USUARITEMA = 18;
    public final int MATEIX_TEMA = 19;
    public final int ACTUALITZA_TEMA = 20;        
    public final int GET_ANVERS_TEXT = 22;
    public final int ACTUALITZA_FLASHCARD = 23;
    public final int BAIXA_USUARITEMA = 24;
    public final int EXISTEIX_CODI_FLASHCARD = 25;
    public final int SET_EXTENSIO = 26;
    public final int GET_FLASHCARD = 27;
    
    private Connection connection;
    private String host = "localhost";    
    private String user = "jzorita";
    private String pass = "password";    
    private String port = "3306";
    private String bdd;

    /**
     * @return obtenir el connector a la BDD
     */
    public Connection getConnection() { return connection; }

    /**
     * @param connection modificar valor connector a BDD
     */
    public void setConnection(Connection connection) { this.connection = connection; }

    /**    
     * @return obtenir cadena amb host que allotja la BDD
     */
    public String getHost() { return host; }

    /**     
     * @param host modificar cadena amb host que allotja la BDD
     */
    public void setHost(String host) { this.host = host; }

    /**     
     * @return obtenir cadena amb usuari que connecta la BDD
     */
    private String getUser() { return user; }

    /**    
     * @param user modificar cadena amb usuari que connecta la BDD
     */
    private void setUser(String user) { this.user = user; }

    /**    
     * @return obtenir cadena amb contrasenya de l'usuari que connecta la BDD
     */
    private String getPass() { return pass; }

    /**
     * @param pass modificar cadena amb usuari que connecta la BDD
     */
    private void setPass(String pass) { this.pass = pass; }

    /**    
     * @return obtenir cadena amb port utilitzat per connectar al host de la BDD
     */
    public String getPort() { return port; }

    /**     
     * @param port modificar port utilitzat per connectar al host de la BDD
     */
    public void setPort(String port) { this.port = port; }

    /**
     * @return obtenir cadena amb nom de la base de dades
     */
    public String getBdd() { return bdd; }

    /**     
     * @param bdd modificar cadena amb nom de la base de dades
     */
    public void setBdd(String bdd) { this.bdd = bdd; }        

    
    Mysql_connection(String bdd){
        this.bdd = bdd;
        connect();
    }
    
    /**
     * Connexió amb la base de dades amb paràmetres de la classe.
     */
    private boolean connect(){        
        String url = "jdbc:mysql://" + host + ":" + port + "/" + bdd;
                        
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("SQL ERROR: " + e.getMessage());            
        }
        return true;        
    }
    
    /**
     * Desconnexió de la base de dades.
     */
    public void disconnect(){
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());            
        }
    }
    
    /**
     * Mètode amb consultes predefinides a la BDD per poder emprar-les amb un prepare statement.
     * @param consulta Constant que indica quin tipus de consulta s'ha d'executar.
     * @return Consulta SQL
     */
    public String consulta(int consulta){
        String c = "";
        
        switch(consulta){
            case(LOGIN):
                c = "SELECT admin FROM usuaris WHERE nom LIKE ? AND contrasenya LIKE PASSWORD(?)";            
                break;
            case (SELECCIONA_TEMA):
                c = "SELECT * FROM temes WHERE nom LIKE ?";
                break;
            case(ALTA_TEMA):
                c = "INSERT INTO temes (nom, descripcio) VALUES(?,?)";
                break;
            case(LLISTA_TEMES):
                c = "SELECT * FROM temes ORDER BY nom ASC LIMIT ?, ?";
                break;
            case(INSERT_FLASHCARD):
                c = "INSERT INTO flashcards(codi_tema, anvers_text, anvers_multimedia, revers_text) VALUES (?,?,?,?)";
                break;
            case(FILES_TEMES):
                c = "SELECT COUNT(*) FROM temes";
                break;
            case(LLISTA_FLASHCARDS):
                c = "SELECT * FROM flashcards WHERE codi_tema = ? ORDER BY codi ASC LIMIT ?, ?";
                break;
            case(FILES_FLASHCARDS):
                c = "SELECT COUNT(*) FROM flashcards WHERE codi_tema = ?";
                break;
            case(TAGS_FLASHCARD):
                c = "SELECT nom FROM tags WHERE codi_flashcard = ?";
                break;
            case(TAGS_TEMA):
                c = "SELECT DISTINCT nom FROM tags WHERE codi_flashcard IN "
                        + "(SELECT codi FROM flashcards WHERE codi_tema = ?)";
                break;
            case(EXISTEIX_CODI_TEMA):
                c = "SELECT COUNT(*) FROM temes WHERE codi = ?";
                break;
            case(GET_EXTENSIO):
                c = "SELECT anvers_multimedia FROM flashcards WHERE codi = ?";
                break;
            case(GET_ANVERS_TEXT):
                c = "SELECT anvers_text FROM flashcards WHERE codi = ?";
                break;
            case(ESBORRAR_TEMA):
                c = "DELETE FROM temes WHERE codi = ?";
                break;
            case(ESBORRAR_FLASHCARD):
                c = "DELETE FROM flashcards WHERE codi = ?";
                break;
            case(AFEGIR_TAG):
                c = "INSERT INTO tags(nom,codi_flashcard) VALUES(?,?)";
                break;
            case(ESBORRAR_TAG):
                c = "DELETE FROM tags WHERE nom = ? and codi_flashcard = ?";
                break;     
            case(GET_CODIUSUARI):
                c = "SELECT codi FROM usuaris WHERE nom LIKE ?";
                break;
            case(ALTA_USUARITEMA):
                c = "INSERT INTO usuarisTema(codi_usuari,codi_tema) VALUES(?,?)";
                break;
            case(MATEIX_TEMA):
                c = "SELECT COUNT(*) FROM temes WHERE nom LIKE ? AND codi != ?";
                break;
            case(ACTUALITZA_TEMA):
                c = "UPDATE temes SET nom = ?, descripcio = ? WHERE codi = ?";
                break;            
            case(ACTUALITZA_FLASHCARD):
                c = "UPDATE flashcards SET anvers_text = ?, revers_text = ? WHERE codi = ?";
                break;
            case(BAIXA_USUARITEMA):
                c = "DELETE FROM usuarisTema WHERE codi_usuari = ? AND codi_tema = ?";
                break;
            case(EXISTEIX_CODI_FLASHCARD):
                c = "SELECT COUNT(*) FROM flashcards WHERE codi = ?";
                break;
            case(SET_EXTENSIO):
                c = "UPDATE flashcards SET anvers_multimedia = ? WHERE codi = ?";
                break;
            case(GET_FLASHCARD):
                c = "SELECT * FROM flashcards WHERE codi = ?";
                break;
        }        
        return c;        
    }
}
