
package webservice_package;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.*;

/**
 * <b>Classe principal.</b><br>
 * Es crea una variable usuaris i es gestionen els @WebMethod per tractar
 * les operacions demanades pels clients.
 * 
 * @author José Luis Zorita Gutiérrez
 */
@WebService(serviceName = "WebService_flashcats")
public class WebService_flashcats {
    private static Usuaris usuaris = new Usuaris();
        
    /**
     * Mètode per iniciar sessió. Es comproven les dades donades pels clients
     * i s'inicia sessió si són correctes.
     * @param usuari Nom de l'usuari.
     * @param pass Contrasenya de l'usuari.
     * @return Usuari i contrasenya correctes i no té sessió vigent: <b>{codi 
     * sessió, 0 o 1}</b> (0 = normal, 1 = administrador)<br> Usuari i contrasenya 
     * correctes i té sessió vigent: <b>{codi sessió, -1}</b> <br> Usuari i/o contrasenya 
     * incorrectes: <b>{0,0}</b>
     */
    @WebMethod(operationName = "login")
    public String []login(  @WebParam(name = "usuari") String usuari, 
                                @WebParam(name = "pass") String pass) {                        
        String[] resultat = {"0","0"};
        try{                        
            Mysql_connection con = new Mysql_connection("flashcats");            
            
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.LOGIN));            
          
            statement.setString(1, usuari);
            statement.setString(2, pass);
            
            try (ResultSet result = statement.executeQuery()) {
                con.disconnect();
                
                if(result.next()){
                    if(!usuaris.connectat(usuari)){
                        resultat[1] = result.getString(1);
                        resultat[0] = resultat[1] + Auxiliar.generar_codi();
                        usuaris.inserir(usuari,resultat[0]);
                    }else{
                        resultat[0] = usuaris.getClau(usuari);
                        resultat[1] = "-1"; 
                    }
                }
            }
            return resultat;

        }catch(SQLException ex){
            return resultat;
        }
    }  
    
    /**
     * Mètode que donat un codi de sessió tanca la sessió de l'usuari.
     * @param codi Codi de sessió.
     * @return <b>true</b> = S'ha esborrat la sessió. <br><b>false</b> = Codi no correcte.
     */
    @WebMethod(operationName = "logout")
    public boolean logout(  @WebParam(name = "codi") String codi) {
        boolean trobat = false;
        trobat = usuaris.getLlistaUsuaris().containsKey(codi);
        if(trobat){
            usuaris.esborrar(codi);
            trobat = true;
        }
        return trobat;        
    }            
       
     /**
     * Mètode que dóna d'alta un tema.
     * <br>Usuari que pot utilitzar-lo: <b>Administrador</b>.
     * @param codi Codi de sessió.
     * @param nom Nom del tema.
     * @param desc Descripcio del tema.
     * @return Codi del tema donat d'alta. <br><b>-1</b> = Usuari no té permís. 
     * <br><b>-2</b> = El tema ja existeix.<br><b>-3</b> = Error consulta.
     */
    @WebMethod(operationName = "altaTema")
    public long altaTema(  @WebParam(name = "codi_sessio") String codi,
                              @WebParam(name = "nom_tema") String nom,
                              @WebParam(name = "descripcio") String desc) {
        long codi_tema = -3;
        int tipus = usuaris.tipsUsuari(codi);
        if(tipus < 1) return -1;
        if (nom == null || nom.equals("")) return -3;
        
        try{                        
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.SELECCIONA_TEMA));
            statement.setString(1, nom);
            try (ResultSet result = statement.executeQuery()) {
                if(!result.next()){
                    statement = con.getConnection().prepareStatement(
                            con.consulta(con.ALTA_TEMA), Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, nom);
                    if(desc == null) statement.setString(2, "");
                    else statement.setString(2, desc);
                    statement.executeQuery();
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    generatedKeys.next();
                    codi_tema = generatedKeys.getLong(1);
                }else return -2;
            }
            con.disconnect();            
        }catch(SQLException ex){
            return -3;
        }            
        return codi_tema;
    }  

     /**
     * Mètode per modificar un tema.
     * <br>Usuari que pot utilitzar-lo: <b>Administrador</b>.
     * @param codi Codi de sessió.
     * @param codi_tema Codi del tema.
     * @param nom Nom del tema.
     * @param desc Descripcio del tema.
     * @return <b>1</b> = S'ha modificat. <br><b>-1</b> = Usuari no té permís.<br> 
     * <b>-2</b> = El nom del tema ja existeix o esta buit.<br><b>-3</b> = Error consulta.
     */
    @WebMethod(operationName = "modificaTema")
    public int modificaTema(      @WebParam(name = "codi_sessio") String codi,
                              @WebParam(name = "codi_tema") long codi_tema,
                              @WebParam(name = "nom_tema") String nom,
                              @WebParam(name = "descripcio") String desc) {
        
        int tipus = usuaris.tipsUsuari(codi);
        if(tipus < 1) return -1;
        if (nom == null || nom.equals("")) return -2;        
        
        try{                        
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.MATEIX_TEMA));
            statement.setString(1, nom);
            statement.setLong(2, codi_tema);
            try (ResultSet result = statement.executeQuery()) {
            result.next();
            if(result.getInt(1) == 0){
                    statement = con.getConnection().prepareStatement(
                            con.consulta(con.ACTUALITZA_TEMA));                                        
                    statement.setString(1, nom);
                    if(desc == null) statement.setString(2, "");
                    else statement.setString(2, desc);                    
                    statement.setLong(3, codi_tema);                    
                    ResultSet result2 = statement.executeQuery();
                    result2.close();
                }else return -2;
            }
            con.disconnect();
        }catch(SQLException ex){
            return -3;
        }            
        return 1;
    }      
     /**
     * Mètode que donat un número de pàgina i de files, retorna un llistat de temes.
     * <br>Usuari que pot utilitzar-lo: <b>Tots</b>.
     * @param codi Codi de sessió.
     * @param num_pag Número de pàgina.
     * @param num_files Número de files.
     * @return Matriu amb les columnes codi, tema, i fila.<br>
     * <b>-1,-1,-1</b> = Usuari no connectat.<br><b>-3,-3,-3</b> = Error dades o SQL.
     */
    @WebMethod(operationName = "llistarTemes")
    public String[][] llistarTemes(  @WebParam(name = "codi_sessio") String codi,
                              @WebParam(name = "num_pagina") int num_pag,
                              @WebParam(name = "num_files") int num_files) {
        
        int i = 0;        
        String [][] no_permis = {{"-1","-1","-1"}};
        String [][] error = {{"-3","-3","-3"}};
                    
        if(usuaris.tipsUsuari(codi) < 0) return no_permis;
        if(num_files < 0)return error;

        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.LLISTA_TEMES));

            String[][] temes = new String[num_files][3];
            statement.setInt(1, num_files*num_pag - num_files);
            statement.setInt(2, num_files);
            
            try (ResultSet result = statement.executeQuery()) {
                while(result.next()){
                    temes[i][0] = result.getString(1);
                    temes[i][1] = result.getString(2);
                    temes[i][2] = result.getString(3);
                    i++;
                }
            }
            con.disconnect();
            return temes;

        }catch(SQLException ex){ }
        return error;
    }
    
     /**
     * Mètode que retorna el número de files de la taula temes de la base de dades.     
     * <br>Usuari que pot utilitzar-lo: <b>Tots</b>.
     * @param codi Codi sessió.
     * @return Nùmero de files.<br><b>-1</b> = Usuari no connectat.<br> 
     * <b>-3</b> = Error consulta.
     */
    @WebMethod(operationName = "numeroFilesTemes")
    public int numeroFilesTemes( @WebParam(name = "codi_sessio") String codi){
        if(usuaris.tipsUsuari(codi) < 0) return -1;        

        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.FILES_TEMES));            
            int files;
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                files = Integer.parseInt(result.getString(1));
            }
            con.disconnect();
            return files;
        }catch(SQLException ex){
            return -3;
        }
    }
    
    /**
     * Mètode que permet pujar un fitxer al servidor
     * <br>Usuari que pot utilitzar-lo: <b>Administrador</b>.
     * @param codi Codi de sessió.     
     * @param codi_flashcard Codi de la flashcard.    
     * @param anvers_multimedia Variable que conté el fitxer que es converteix en fitxer.
     * @param ext_multimedia Extensió del fitxer a pujar.
     * @return <b>1</b> = El fitxer s'ha pogut pujar corretament.<br>
     * <b>-1</b> = L'usuari no té permís per pujar fitxers.<br>
     * <b>-2</b> = Dades incorrectes.<br>
     * <b>-3</b> = Error al pujar l'arxiu.<br>
     */
    @WebMethod(operationName = "pujaFitxer")
    public int pujaFitxer(   @WebParam(name = "codi_sessio") String codi,
                                @WebParam(name = "codi_flashcard") long codi_flashcard,
                                @WebParam(name = "anvers_multimedia") byte[]anvers_multimedia,
                                @WebParam(name = "ext_multimedia") String ext_multimedia){
        
        esborraFitxer(codi,codi_flashcard);
        
        if(usuaris.tipsUsuari(codi) < 1) return -1;
        if(ext_multimedia == null || "".equals(ext_multimedia)
           || Auxiliar.existeixCodiFlashcard(codi_flashcard) != 1
           || anvers_multimedia == null) return -2;
        
        String dir = "/home/jzorita/NetBeansProjects/WebService_flashcats/files/";        
        String filePath = dir + codi_flashcard + ext_multimedia;
        
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream outputStream = new BufferedOutputStream(fos);
            outputStream.write(anvers_multimedia);
            outputStream.close();
            fos.close();
            Auxiliar.setExtensio(codi_flashcard, ext_multimedia);
        } catch (IOException ex) {
            return -3;
        }
        return 1;
    }
    
    /**
     * Mètode que retorna un fitxer allotjat al servidor.
     * <br>Usuari que pot utilitzar-lo: <b>Tots</b>.
     * @param codi Codi de sessió.
     * @param codiFlashcard Codi de la flashcard.
     * @return Fitxer en objecte del tipus byte[].<br>
     * Usuari no connectat, consulta errònea o flashcard no té fitxer = null.
     */
    @WebMethod(operationName = "baixaFitxer")
    public byte[] baixaFitxer(  @WebParam(name = "codi_sessio") String codi,
                                @WebParam(name = "codi_flahcard") long codiFlashcard) {
        
        if(usuaris.tipsUsuari(codi) < 0) return null;
        File f = new File("/home/jzorita/NetBeansProjects/WebService_flashcats/files/");                                
        File[] matchingFiles = f.listFiles(new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name) {
                return (name.startsWith(codiFlashcard+"") 
                     && name.endsWith(getExtensio(codi,codiFlashcard)));
            }
            
        });
        if(matchingFiles.length == 1){
            try {                
                FileInputStream fis = new FileInputStream(matchingFiles[0]);
                byte[] fileBytes;
                try (BufferedInputStream inputStream = new BufferedInputStream(fis)) {
                    fileBytes = new byte[(int) matchingFiles[0].length()];
                    inputStream.read(fileBytes);
                    inputStream.close();
                    fis.close();                    
                }
                
                return fileBytes;                
            } catch (IOException ex) {                
                return null;                
            }
        }
        return null;        
    }
    
    /**
     * Mètode per donar d'alta una flashcard.
     * <br>Usuari que pot utilitzar-lo: <b>Administrador</b>.
     * <b>Requisits:</b><br>El text de l'anvers pot ser null només si hi ha fitxer.<br>
     * Si es puja fitxer la extensió també ha d'estar informada.<br>
     * El text del revers és obligatori.
     * @param codi Codi de sessió.
     * @param codi_tema Codi del tema al que pertany la flashcard.
     * @param anvers_text Text de la part del anvers.
     * @param anvers_multimedia Fitxer multimèdia.
     * @param ext_multimedia Extensió del fitxer.
     * @param revers_text Text del revers.
     * @return Codi de la flashcard donada d'alta.<br><b>-1</b> = Usuari no té permís.
     * <br><b>-2</b> = Error en els paràmetres. <br><b>-3</b> = Error en consulta.
     */
    @WebMethod(operationName = "altaFlashcard")
    public long altaFlashcard( @WebParam(name = "codi_sessio") String codi,
                              @WebParam(name = "codi_tema") long codi_tema,
                              @WebParam(name = "anvers_text") String anvers_text,                              
                              @WebParam(name = "anvers_multimedia") byte[] anvers_multimedia,
                              @WebParam(name = "ext_multimedia") String ext_multimedia,
                              @WebParam(name = "revers_text") String revers_text) {
        
        if(usuaris.tipsUsuari(codi)<1)return -1;
        if(Auxiliar.existeixCodiTema(codi_tema) != 1 
                || ((anvers_text == null || "".equals(anvers_text)) && anvers_multimedia == null) 
                || (anvers_multimedia != null && (ext_multimedia == null || "".equals(ext_multimedia)))
                || (revers_text == null) || ("".equals(revers_text))) return -2;
                
        try{                        
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.INSERT_FLASHCARD), Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, codi_tema);
            if(anvers_text == null)statement.setString(2, "");
            else statement.setString(2, anvers_text);
            if(anvers_multimedia == null)statement.setString(3, "");
            else statement.setString(3,ext_multimedia);
            statement.setString(4, revers_text);                                 
            statement.executeQuery();
                        
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            long codi_flashcard = generatedKeys.getLong(1);                        
            if(anvers_multimedia != null)
                pujaFitxer(codi, codi_flashcard,anvers_multimedia, ext_multimedia);
            
            con.disconnect();
            return codi_flashcard;
        }catch(SQLException ex){
            return -3;
        }
    }

     /**
     * Mètode per modificar una flashcard.
     * <br>Usuari que pot utilitzar-lo: <b>Administrador</b>.
     * <b>Requisits:</b><br>El text de l'anvers pot ser null només si hi ha fitxer.<br>     
     * El text del revers es obligatori.
     * @param codi Codi de sessió.
     * @param codi_flashcard Codi de la flashcard.
     * @param anvers_text Text de la part del anvers.
     * @param revers_text Text del revers.
     * @return <b>1</b> = Modificació correcta. <br><b>-1</b> = Usuari no té permís.
     * <br><b>-2</b> = Error en els paràmetres. <br><b>-3</b> = Error en consulta.
     */
    @WebMethod(operationName = "modificaFlashcard")
    public int modificaFlashcard( @WebParam(name = "codi_sessio") String codi,
                              @WebParam(name = "codi_flashcard") long codi_flashcard,
                              @WebParam(name = "anvers_text") String anvers_text,
                              @WebParam(name = "revers_text") String revers_text) {
                                
        if(usuaris.tipsUsuari(codi)<1)return -1;
        if(((anvers_text == null || "".equals(anvers_text)) 
                &&  "".equals(getExtensio(codi,codi_flashcard)))                 
                || (revers_text == null || "".equals(revers_text))) return -2;

        try{                        
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.ACTUALITZA_FLASHCARD));            
            if(anvers_text == null)statement.setString(1, "");
            else statement.setString(1, anvers_text);            
            statement.setString(2, revers_text);            
            statement.setLong(3, codi_flashcard);
            statement.executeQuery();                        
            con.disconnect();
        }catch(SQLException ex){
            return -3;
        }            
        return 1;
    }
    
     /**
     * Mètode que donat un número de pàgina i de files, retorna un llistat de flashcards.
     * <br>Usuari que pot utilitzar-lo: <b>Tots</b>.
     * @param codi Codi de sessió.
     * @param codi_tema Codi del tema.
     * @param num_pag Número de pàgina.
     * @param num_files Número de files.
     * @return Matriu amb les columnes del paràmetres de les flashcard, i una última 
     * columna amb els tags separats pel símbol ";"<br>
     * <b>-1,-1,-1</b> = Usuari no connectat.<br><b>-3,-3,-3</b> = Error consulta.
     */
    @WebMethod(operationName = "llistarFlashcards")
    public String[][] llistarFlashcards(  @WebParam(name = "codi_sessio") String codi,
                              @WebParam(name = "codi_tema") long codi_tema,  
                              @WebParam(name = "num_pagina") int num_pag,
                              @WebParam(name = "num_files") int num_files) {        
        int i = 0;        
        String [][] no_permis = {{"-1","-1","-1"}};
        String [][] error = {{"-3","-3","-3"}};
                    
        if(usuaris.tipsUsuari(codi) < 0) return no_permis;
        if(num_files < 0)return error;
        
        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.LLISTA_FLASHCARDS));

            String[][] flashcards = new String[num_files][6];
            statement.setLong(1, codi_tema);
            statement.setInt(2, num_files*num_pag - num_files);
            statement.setInt(3, num_files);
            
            try (ResultSet result = statement.executeQuery()) {
                while(result.next()){                                                           
                    flashcards[i][0] = result.getString(1);
                    flashcards[i][1] = result.getString(2);
                    flashcards[i][2] = result.getString(3);
                    flashcards[i][3] = result.getString(4);
                    flashcards[i][4] = result.getString(5);                    
                    flashcards[i][5] = tagsFlashcard(codi, Integer.parseInt(flashcards[i][0]));
                    i++;
                }
            }
            con.disconnect();
            return flashcards;
        }catch(SQLException ex){ }        
        return error;
    }
    
    /**
     * Mètode que retorna les dades d'una flashcard, donat un codi.
     * <br>Usuari que pot utilitzar-lo: <b>Tots</b>.
     * @param codi Codi de sessió.
     * @param codi_flashcard Codi de la flashcard.     
     * @return Vector amb els valors de la flashcard, i una últim camp
     * amb els tags separats pel símbol ";"<br>
     * <b>-1</b> = Usuari no connectat.<br><b>-2</b> = Flashcard no existeix.
     * <br><b>-3</b> = Error consulta.
     */
    @WebMethod(operationName = "getFlashcard")
    public String[] getFlashcard(  @WebParam(name = "codi_sessio") String codi,
                              @WebParam(name = "codi_flashcard") long codi_flashcard){
        String [] no_permis = {"-1"};
        String [] no_existeix = {"-2"};
        String [] error = {"-3"};
        if(usuaris.tipsUsuari(codi) < 0) return no_permis;
        if(Auxiliar.existeixCodiFlashcard(codi_flashcard) == 0) return no_existeix;
        
        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.GET_FLASHCARD));

            String[] flashcards = new String[6];
            statement.setLong(1, codi_flashcard);            
            
            ResultSet result = statement.executeQuery();
            result.next();                
            flashcards[0] = result.getString(1);
            flashcards[1] = result.getString(2);
            flashcards[2] = result.getString(3);
            flashcards[3] = result.getString(4);
            flashcards[4] = result.getString(5);                    
            flashcards[5] = tagsFlashcard(codi, Integer.parseInt(flashcards[0]));                    
            
            con.disconnect();
            return flashcards;
        }catch(SQLException ex){ }        
        return error;
    }
   
    /**
     * Mètode que retorna el número de flashcards que té un tema.
     * <br>Usuari que pot utilitzar-lo: <b>Tots</b>.
     * @param codi Codi de sessió.
     * @param codi_tema Codi del tema.
     * @return Número de flashcards del tema.<br><b>-1</b> = Usuari no connectat.
     * <br><b>-3</b> = Error en la consulta.
     */
    @WebMethod(operationName = "numeroFilesFlashcards")
    public int numeroFilesFlashcards( @WebParam(name = "codi_sessio") String codi,
                                      @WebParam(name = "codi_tema") long codi_tema){
        if(usuaris.tipsUsuari(codi) < 0) return -1;
        
        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.FILES_FLASHCARDS));            
            int files;
            statement.setLong(1, codi_tema);
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                files = Integer.parseInt(result.getString(1));
            }
            con.disconnect();
            return files;
        }catch(SQLException ex){
            return -3;
        }
    }
    
    /**
     * Mètode que retorna els tags d'una flashcard.
     * <br>Usuari que pot utilitzar-lo: <b>Tots</b>.
     * @param codi Codi de sessió.
     * @param codi_flashcard Codi de la flashcard.
     * @return String amb els tags de la flashcard separats pel símbol ";"
     * <b>-1</b> = Usuari no connectat. <br><b>-2</b> = No existeix la flashcard.
     * <br><b>-3</b> = Error en la consulta.
     */
    @WebMethod(operationName = "tagsFlashcard")
    public String tagsFlashcard( @WebParam(name = "codi_sessio") String codi,
                              @WebParam(name = "codi_flashcard") long codi_flashcard){
        if(usuaris.tipsUsuari(codi) < 0) return "-1";
        if(Auxiliar.existeixCodiFlashcard(codi_flashcard) == 0) return "-2";
        
        String tags = "";
        int i = 0;
        
        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.TAGS_FLASHCARD));            
            
            statement.setLong(1, codi_flashcard);
            try (ResultSet result = statement.executeQuery()) {
                
                while(result.next()){
                    if(!tags.equals(""))tags =tags + ";";
                    tags = tags + result.getString(1);
                    i++;
                }                
            }
            con.disconnect();
            return tags;
        }catch(SQLException ex){
            return "-3";
        }
    }
    
    /**
     * Mètode que retorna els tags d'un tema.<br>
     * Tags d'un tema: Conjunt de tags de totes les flashcards a les que pertany
     * el tema.
     * <br>Usuari que pot utilitzar-lo: <b>Tots</b>.
     * @param codi Codi de sessió.
     * @param codi_tema Codi de la flashcard.
     * @return String amb els tags del tema separats pel símbol ";"
     * <b>-1</b> = Usuari no connectat.<br><b>-2</b> = No existeix el tema.
     * <br><b>-3</b> = Error en la consulta.
     */
    @WebMethod(operationName = "tagsTema")
    public String tagsTema( @WebParam(name = "codi_sessio") String codi,
                              @WebParam(name = "codi_tema") long codi_tema){
        
        if(usuaris.tipsUsuari(codi) < 0) return "-1";
        if(Auxiliar.existeixCodiTema(codi_tema) == 0) return "-2";
        
        String tags = "";
        int i = 0;        
        
        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.TAGS_TEMA));            
            
            statement.setLong(1, codi_tema);
            try (ResultSet result = statement.executeQuery()) {
                
                while(result.next()){
                    if(!tags.equals(""))tags =tags + ";";
                    tags = tags + result.getString(1);
                    i++;
                }                
            }
            con.disconnect();
            return tags;
        }catch(SQLException ex){
            return "-3";
        }
    }    
    
    /**
     * Mètode que retorna la extensió del fitxer d'una flashcard.
     * <br>Usuari que pot utilitzar-lo: <b>Tots</b>.
     * @param codi Codi d'usuari.
     * @param codi_flashcard Codi de la flashcard.
     * @return Extensió del fitxer (per exemple ".jpg"), buit si no té.<br>
     * <b>-1</b> = Usuari no connectat.<br>
     * <b>-3</b> = Error consulta.
     */
    @WebMethod(operationName = "getExtensio")
    public String getExtensio(  @WebParam(name = "codi_sessio") String codi,
                                @WebParam(name = "codi_flashcard") long codi_flashcard){
        if(usuaris.tipsUsuari(codi) < 0) return "-1";
        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = 
                    con.getConnection().prepareStatement(con.consulta(con.GET_EXTENSIO));
            statement.setLong(1, codi_flashcard);
            String ext;
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                ext = result.getString(1);
            }
            con.disconnect();
            return ext;
        }catch(SQLException ex){
            return "-3";
        }
    }    
    
    /**
     * Mètode que esborra un tema i totes les flashcards associades.
     * <br>Usuari que pot utilitzar-lo: <b>Administrador</b>.
     * @param codi Codi de sessió.
     * @param codi_tema Codi del tema a esborrar.
     * @return <b>1</b> = Operació correcta. <br><b>-1</b>Usuari no té permís.
     * <br><b>-2</b> = No existeix el tema. <br><b>-3</b> = Error
     */
    @WebMethod(operationName = "esborraTema")
    public int esborraTema(@WebParam(name = "codi_sessio") String codi,
                                @WebParam(name = "codi_tema") long codi_tema){
        if(usuaris.tipsUsuari(codi) < 1) return -1;
        if(Auxiliar.existeixCodiTema(codi_tema) == 0)return -2;
                
        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.ESBORRAR_TEMA));            
            statement.setLong(1, codi_tema);
            statement.executeQuery();
            return 1;
        }catch(SQLException ex){
            return -3;
        }
    }
    
     /**
     * Mètode que esborra una flashcard i aparicions a taula tags.
     * <br>Usuari que pot utilitzar-lo: <b>Administrador</b>.
     * @param codi Codi de sessió.
     * @param codi_flashcard Codi de la flashcard a esborrar.
     * @return <b>1</b> = Consulta s'ha executat correctament.<br>
     * <b>-1</b> = Usuari no té permís. <br><b>-3</b>Error consulta.
     */    
    @WebMethod(operationName = "esborraFlashcard")
    public int esborraFlashcard(@WebParam(name = "codi_sessio") String codi,
                                @WebParam(name = "codi_flashcard") long codi_flashcard){
        if(usuaris.tipsUsuari(codi) < 1) return -1;
        
        esborraFitxer(codi,codi_flashcard);
        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.ESBORRAR_FLASHCARD));
            statement.setLong(1, codi_flashcard);
            statement.executeQuery();
            return 1;
        }catch(SQLException ex){
            return -3;
        }
    }
    
     /**
     * Mètode que afegeix un tag a una flashcard. Mida màxima 15 caràcters.
     * <br>Usuari que pot utilitzar-lo: <b>Administrador</b>.
     * @param codi Codi de sessió.
     * @param codi_flashcard Codi de la flashcard.
     * @param nom Nom del tag.
     * @return <b>1</b> = Tag afegit correctament.<br><b>-2</b> = Mida incorrecta o nom buit.
     * <br><b>-1</b> = No es té permís. <br><b>-3</b> = Error en la consulta.
     */
    @WebMethod(operationName = "afegirTag")
    public int afegirTag( @WebParam(name = "codi_sessio") String codi,
                             @WebParam(name = "codi_flashcard") long codi_flashcard,
                             @WebParam(name = "nom_tag") String nom){
        if(usuaris.tipsUsuari(codi) < 1) return -1;
        if(nom == null || "".equals(nom)) return -2;
        if(nom.length() > 15) return -2;
        
        nom = nom.replace(" ","_");                        
                
        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.AFEGIR_TAG));            
            statement.setString(1, nom);
            statement.setLong(2, codi_flashcard);
            statement.executeQuery();
            return 1;
        }catch(SQLException ex){
            return -3;
        }
    }
    
     /**
     * Mètode que esborra un tag de una flashcard.
     * <br>Usuari que pot utilitzar-lo: <b>Administrador</b>.
     * @param codi Codi de sessió.
     * @param codi_flashcard Codi de la flashcard.
     * @param nom Nom del tag.
     * @return <b>1</b> = Tag esborrat correctament.<br>
     * <b>-1</b> = No es té permís. <br><b>-3</b> = Error en la consulta.
     */        
    @WebMethod(operationName = "esborraTag")
    public int esborraTag( @WebParam(name = "codi_sessio") String codi,
                             @WebParam(name = "codi_flashcard") long codi_flashcard,
                             @WebParam(name = "nom_tag") String nom){
        if(usuaris.tipsUsuari(codi) < 1) return -1;        
        if(nom == null || "".equals(nom)) return -2;
        if(Auxiliar.existeixCodiFlashcard(codi_flashcard) == 0) return -2;
                
        try{
            Mysql_connection con = new Mysql_connection("flashcats");
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.ESBORRAR_TAG));            
            statement.setString(1, nom);
            statement.setLong(2, codi_flashcard);
            statement.executeQuery();
            return 1;
        }catch(SQLException ex){
            return -3;
        }
    }                
    
    /**
     * Mètode per esborrar el fitxer de una Flashcard.
     * <br>Usuari que pot utilitzar-lo: <b>Administrador</b>.
     * @param codi Codi de sessió.     
     * @param codi_flashcard Codi de la flashcard.
     * @return <b>1</b> = Operació correcta.<br>
     * <b>-1</b> = No es té permís. <br><b>-2</b> = Flashcard no té fitxer o text anvers.
     * <br><b>-3</b> = Error en la consulta.
     */
    
    @WebMethod(operationName = "esborraFitxer")
    public int esborraFitxer(   @WebParam(name = "codi_sessio") String codi,
                                    @WebParam(name = "codi_flashcard") long codi_flashcard){
        if(usuaris.tipsUsuari(codi) < 1) return -1;
        String ext = getExtensio(codi,codi_flashcard);
        if(ext.equals("")) return -2;
        String dir = "/home/jzorita/NetBeansProjects/WebService_flashcats/files/";
        File file = new File(dir + codi_flashcard + ext);           
        file.delete();                                 
        Auxiliar.setExtensio(codi_flashcard, "");                    
        return 1;
        
    }
    
    /**
     * Mètode per apuntar un usuari a un tema.
     * <br>Usuari que pot utilitzar-lo: <b>Tots</b>.
     * @param codi Codi de sessió.
     * @param nom_usuari Nom de l'usuari.
     * @param codi_tema Codi del tema.
     * @return <b>1</b> = Alta correcta.
     * <b>-1</b> = Usuari no connectat. <br><b>-2</b> = Usuari o tema no existeix.
     * <br><b>-3</b> = Error en la consulta.
     */          
    @WebMethod(operationName = "altaUsuariTema")
    public int altaUsuariTema(   @WebParam(name = "codi_sessio") String codi,
                                    @WebParam(name = "nom_usuari") String nom_usuari,
                                    @WebParam(name = "codi_tema") long codi_tema){
        if(usuaris.tipsUsuari(codi) < 0) return -1;        
        int codi_usuari = Auxiliar.getCodiUsuari(nom_usuari);
        if(codi_usuari == -1 || Auxiliar.existeixCodiTema(codi_tema) == 0)
            return -2;
         
        try{
            Mysql_connection con = new Mysql_connection("flashcats");            
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.ALTA_USUARITEMA));            
            statement.setInt(1, codi_usuari);
            statement.setLong(2, codi_tema);
            statement.executeQuery();
            return 1;
        }catch(SQLException ex){
            return -3;
        }
    }
    
    /**
     * Mètode per esborrar un usuari d'un tema.
     * <br>Usuari que pot utilitzar-lo: <b>Tots</b>.
     * @param codi Codi de sessió.
     * @param nom_usuari Nom de l'usuari.
     * @param codi_tema Codi del tema.
     * @return <b>1</b> = Baixa correcta.<br>
     * <b>-1</b> = Usuari no connectat.<br><b>-2</b> = Usuari o tema no existeix.
     * <br><b>-3</b> = Error en la consulta.
     */          
    @WebMethod(operationName = "baixaUsuariTema")
    public int baixaUsuariTema(   @WebParam(name = "codi_sessio") String codi,
                                    @WebParam(name = "nom_usuari") String nom_usuari,
                                    @WebParam(name = "codi_tema") long codi_tema){
        if(usuaris.tipsUsuari(codi) < 0) return -1;
        int codi_usuari = Auxiliar.getCodiUsuari(nom_usuari);
        if(codi_usuari == -1 || Auxiliar.existeixCodiTema(codi_tema) == 0)
            return -2;
        
        try{
            Mysql_connection con = new Mysql_connection("flashcats");            
            PreparedStatement statement = con.getConnection().prepareStatement(
                                con.consulta(con.BAIXA_USUARITEMA));
            statement.setInt(1, codi_usuari);
            statement.setLong(2, codi_tema);
            statement.executeQuery();
            return 1;
        }catch(SQLException ex){
            return -3;
        }
    }
     
}
