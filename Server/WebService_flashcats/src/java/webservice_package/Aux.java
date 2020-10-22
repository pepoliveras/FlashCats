/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice_package;
import java.util.UUID;

/**
 *
 * @author jzorita
 */
import java.time.LocalDateTime;
    

public class Aux {
    static String generar_codi(){
        return UUID.randomUUID().toString().replace("-","");        
    }
        
    
    
}
