/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice_package;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.*;

/**
 *
 * @author jzorita
 */
@WebService(serviceName = "WebService_flashcats")
public class WebService_flashcats {
    static Usuaris usuaris = new Usuaris();
    
    
    
    @WebMethod(operationName = "login")
    public String []login(  @WebParam(name = "usuari") String usuari, 
                                @WebParam(name = "pass") String pass) {                
        int num = 0;
        String[] resultat = {"0","0"};
        try{                        
            Mysql_connection con = new Mysql_connection("flashcats");            
            
            Statement statement = con.connection.createStatement();
          
            String query = "SELECT admin FROM usuaris WHERE usuaris.nom LIKE ";
            query = query + "'" + usuari + "' AND usuaris.contrasenya LIKE '" + pass + "'";
            
            ResultSet result = statement.executeQuery(query);
            con.disconnect();
            
            if(result.next()){                
                if(!usuaris.connectat(usuari)){                    
                    resultat[1] = result.getString(1);
                    resultat[0] = resultat[1] + Aux.generar_codi();
                    usuaris.inserir(usuari,resultat[0]);
                }else{                    
                    resultat[0] = usuaris.getClau(usuari);
                    resultat[1] = "-1";
                }
            }
            result.close();                                  
            return resultat;

        }catch(Exception ex){
            return resultat;
        }
    }  
    
    
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
}
