/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice_package;

/**
 *
 * @author jzorita
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Mysql_connection {
    Connection connection;
    private String host = "localhost";    
    private String user = "jzorita";
    private String pass = "password";    
    private String port = "3306";
    private String bdd;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getBdd() {
        return bdd;
    }

    public void setBdd(String bdd) {
        this.bdd = bdd;
    }        
    
    Mysql_connection(String bdd){
        this.bdd = bdd;
        connect();
    }
    
    boolean connect(){        
        String url = "jdbc:mysql://" + host + ":" + port + "/" + bdd;
                        
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        }catch(Exception e){
            System.out.println("SQL ERROR: " + e.getMessage());            
        }
        return true;
        
    }
    void disconnect(){
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());            
        }
    }
}
