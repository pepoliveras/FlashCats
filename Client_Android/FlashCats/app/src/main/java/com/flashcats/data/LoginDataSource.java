package com.flashcats.data;

import android.opengl.GLES20;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Toast;

import com.flashcats.data.model.LoggedInUser;
import com.flashcats.ui.login.LoginActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private String clau_sessio = "0";
    private String tipus_usuari = "0";
    private Boolean logout_ok = false;

    LoggedInUser usuari;

    private String URL = "http://jlzorita.ddns.net:8080/WebService_flashcats/WebService_flashcats?wsdl";
    private String NAMESPACE = "http://webservice_package/";
    private String METHOD_NAME = "login";
    private String SOAP_ACTION = "";

    public Result<LoggedInUser> login(String username, String password) {

        try {

            System.out.println("LoginDataSource: Abans de cridar LoginDataSource.login");

            webservice_call("login",username,password);

            System.out.println("LoginDataSource: Després de cridar LoginDataSource.login");

            usuari = new LoggedInUser(clau_sessio,username);

            // Si retorn ok
            if (clau_sessio.compareTo("0")!=0) {
                return new Result.Success<>(usuari);
            } else {
                return new Result.Error(new IOException("Error logging in"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout(String clau_sessio) {
        // revoke authentication
        System.out.println("LoginDataSource ... Logout");
        System.out.println("LoginDataSource ... clau sessio: " + clau_sessio);

        webservice_call("logout",clau_sessio);
    }

    // métodes Webservice Server:
    // login(usuari,pass)
    // ├──> retorna String[] amb [0,0] si falla o [clau_sessió, tipus_user] si èxit
    // ├──> tipus:user: 0 --> usuari normal, 1 --> usuari admin
    // └──> usuaris (demo, demo) o (root, root) per fer proves
    // logout(clau) retorna true/fals si ha trobat la clau
    // responMissatge (msg) retorna "Missatge rebut" + msg

    private void webservice_call(String... params) {

        SoapObject soapObject = null;

        if (params[0].compareTo("login")==0) { //login
            METHOD_NAME = "login";
            // Inicialitzem la crida SOAP
            soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            SOAP_ACTION = NAMESPACE.concat(METHOD_NAME);

            // afegim a la crida el primer paràmetre "usuari"
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("usuari");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[1]);

            // afegim a la crida el segon paràmetre "pass"
            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("pass");
            propertyInfo2.setType(String.class);
            propertyInfo2.setValue(params[2]);

            soapObject.addProperty(propertyInfo1);
            soapObject.addProperty(propertyInfo2);
        } else if (params[0].compareTo("logout")==0) { //logout
            METHOD_NAME = "logout";
            // Inicialitzem la crida SOAP
            soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            SOAP_ACTION = NAMESPACE.concat(METHOD_NAME);

            // afegim a la crida el primer paràmetre "clau_sessio"
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("codi");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[1]);

            soapObject.addProperty(propertyInfo1);
        }

        //Declarem la cersió de la crida SOAP
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        // Set output SOAP object
        envelope.setOutputSoapObject(soapObject);

        try {
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.debug = true;

            // Fem la crida
            httpTransportSE.call(SOAP_ACTION, envelope);

            // Just being curious let's see how our XML request/response look like
            System.out.println("LoginDataSource SOAP Request " + httpTransportSE.requestDump);
            System.out.println("LoginDataSource SOAP Response " + httpTransportSE.responseDump);

            // Obtenim la resposta
            String resposta = envelope.getResponse().toString();
            System.out.println("LoginDataSource envelope toString: " + resposta);

            if (envelope.bodyIn != null) {
                if (params[0].compareTo("login")==0) { // login
                    //getProperty() Retorna el valor de un paràmetre en un índex determinat
                    SoapPrimitive resultSOAP1 = (SoapPrimitive) ((SoapObject) envelope.bodyIn)
                            .getProperty(0);
                    SoapPrimitive resultSOAP2 = (SoapPrimitive) ((SoapObject) envelope.bodyIn)
                            .getProperty(1);

                    clau_sessio = resultSOAP1.toString();
                    tipus_usuari = resultSOAP2.toString();

                    System.out.println("LoginDataSource login ok");
                    System.out.println("LoginDataSource param1: " + clau_sessio);
                    System.out.println("LoginDataSource param2: " + tipus_usuari);

                } else if (params[0].compareTo("logout")==0){ //logout
                    //getProperty() Retorna el valor de un paràmetre en un índex determinat
                    SoapPrimitive resultSOAP1 = (SoapPrimitive) ((SoapObject) envelope.bodyIn)
                            .getProperty(0);

                    logout_ok =  Boolean.valueOf(resultSOAP1.toString());

                    System.out.println("LoginDataSource logout ok: " + logout_ok.toString());

                }
            } else {
                // error de resposta deixem els paràmetres de sessió a 0
                clau_sessio = "0";
                tipus_usuari = "0";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}