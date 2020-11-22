package com.flashcats.data;

import com.flashcats.data.model.TemaFlashCard;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
*
* Classe que accedeix a la font de dades per alimentar el continguts del Temes
*
**/
public class TemesDataSource {

    private int result;
    private ArrayList<TemaFlashCard> llistat_temes;

    private static String URL = "http://jlzorita.ddns.net:8080/WebService_flashcats/WebService_flashcats?wsdl";
    private static String NAMESPACE = "http://webservice_package/";
    private String METHOD_NAME = "llistarTemes";
    private String SOAP_ACTION = "";

    public TemesDataSource() {
        this.llistat_temes = new ArrayList<TemaFlashCard>();
    }

    public ArrayList<TemaFlashCard> obtenirLlistatTemes(String codi_sessio, String num_temes){
        try {
            webservice_call("llistarTemes",codi_sessio,"1",num_temes);
        } catch(Exception e)  {
            System.out.println("TemesDataSource ERROR obtenirLlistatTemes");
        }
        return llistat_temes;
    }

    public int altaTema(String codi_sessio,String nomTema, String descTema){
        try {
            webservice_call("altaTema",codi_sessio,nomTema,descTema);
        } catch(Exception e)  {
            System.out.println("TemesDataSource ERROR altaTema");
        }
        return result;
    }

    public Integer numTemes(String codi_sessio){
        try {
            webservice_call("numTemes",codi_sessio);
        } catch(Exception e)  {
            System.out.println("TemesDataSource ERROR numTemes");
        }
        return result;
    }

    public int modificaTema(String codi_sessio,String codiTema,String nomTema, String descTema){
        try {
            webservice_call("modificaTema",codi_sessio,codiTema,nomTema,descTema);
        } catch(Exception e)  {
            System.out.println("TemesDataSource ERROR modificaTema");
        }
        return result;
    }

    public int eliminaTema(String codi_sessio,String codiTema){
        try {
            webservice_call("eliminaTema",codi_sessio,codiTema);
        } catch(Exception e)  {
            System.out.println("TemesDataSource ERROR modificaTema");
        }
        return result;
    }

    /**********************************
    *
    * CRIDA AL WebService
    *
    ***********************************/

    private void webservice_call(String... params) {

        SoapObject soapObject = null;

        if (params[0].compareTo("llistarTemes")==0) { //llistar temes
            METHOD_NAME = "llistarTemes";
            // Inicialitzem la crida SOAP
            soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            SOAP_ACTION = NAMESPACE.concat(METHOD_NAME);

            // afegim a la crida el primer paràmetre "codi_sessio"
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("codi_sessio");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[1]);

            // afegim a la crida el segon paràmetre "num_pagina"
            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("num_pagina");
            propertyInfo2.setType(String.class);
            propertyInfo2.setValue(params[2]);

            // afegim a la crida el tercer paràmetre "num_files"
            PropertyInfo propertyInfo3 = new PropertyInfo();
            propertyInfo3.setName("num_files");
            propertyInfo3.setType(String.class);
            propertyInfo3.setValue(params[3]);

            soapObject.addProperty(propertyInfo1);
            soapObject.addProperty(propertyInfo2);
            soapObject.addProperty(propertyInfo3);

        } else if (params[0].compareTo("numTemes")==0) { //nombre de temes
            METHOD_NAME = "numeroFilesTemes";
            // Inicialitzem la crida SOAP
            soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            SOAP_ACTION = NAMESPACE.concat(METHOD_NAME);

            // afegim a la crida el primer paràmetre "codi_sessio"
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("codi_sessio");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[1]);

            soapObject.addProperty(propertyInfo1);

        } else if (params[0].compareTo("altaTema")==0) { //donar d'alta un tema nou
            METHOD_NAME = "altaTema";
            // Inicialitzem la crida SOAP
            soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            SOAP_ACTION = NAMESPACE.concat(METHOD_NAME);

            // afegim a la crida el paràmetre "codi_sessio"
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("codi_sessio");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[1]);

            // afegim a la crida el paràmetre "nom_tema"
            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("nom_tema");
            propertyInfo2.setType(String.class);
            propertyInfo2.setValue(params[2]);

            // afegim a la crida el paràmetre "descripcio"
            PropertyInfo propertyInfo3 = new PropertyInfo();
            propertyInfo3.setName("descripcio");
            propertyInfo3.setType(String.class);
            propertyInfo3.setValue(params[3]);

            soapObject.addProperty(propertyInfo1);
            soapObject.addProperty(propertyInfo2);
            soapObject.addProperty(propertyInfo3);

        } else if (params[0].compareTo("modificaTema")==0) { //modificar un tema existent
            METHOD_NAME = "modificaTema";
            // Inicialitzem la crida SOAP
            soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            SOAP_ACTION = NAMESPACE.concat(METHOD_NAME);

            // afegim a la crida el paràmetre "codi_sessio"
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("codi_sessio");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[1]);

            // afegim a la crida el paràmetre "codi_tema"
            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("codi_tema");
            propertyInfo2.setType(String.class);
            propertyInfo2.setValue(params[2]);

            // afegim a la crida el paràmetre "nom_tema"
            PropertyInfo propertyInfo3 = new PropertyInfo();
            propertyInfo3.setName("nom_tema");
            propertyInfo3.setType(String.class);
            propertyInfo3.setValue(params[3]);

            // afegim a la crida el paràmetre "descripcio"
            PropertyInfo propertyInfo4 = new PropertyInfo();
            propertyInfo4.setName("descripcio");
            propertyInfo4.setType(String.class);
            propertyInfo4.setValue(params[4]);

            soapObject.addProperty(propertyInfo1);
            soapObject.addProperty(propertyInfo2);
            soapObject.addProperty(propertyInfo3);
            soapObject.addProperty(propertyInfo4);

        } else if (params[0].compareTo("eliminaTema")==0) { //eliminar un tema existent
            METHOD_NAME = "esborraTema";
            // Inicialitzem la crida SOAP
            soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            SOAP_ACTION = NAMESPACE.concat(METHOD_NAME);

            // afegim a la crida el paràmetre "codi_sessio"
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("codi_sessio");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[1]);

            // afegim a la crida el paràmetre "codi_tema"
            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("codi_tema");
            propertyInfo2.setType(String.class);
            propertyInfo2.setValue(params[2]);

            soapObject.addProperty(propertyInfo1);
            soapObject.addProperty(propertyInfo2);
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
            System.out.println("TemesDataSource SOAP Request " + httpTransportSE.requestDump);
            System.out.println("TemesDataSource SOAP Response " + httpTransportSE.responseDump);

            // Obtenim la resposta
            String resposta = envelope.getResponse().toString();
            System.out.println("TemesDataSource envelope toString: " + resposta);

            if (envelope.bodyIn != null) {

                if (params[0].compareTo("llistarTemes")==0) { //resultat llistar temes

                    SoapObject obj1 = (SoapObject) envelope.bodyIn;

                    //getPropertyCount() Retorna el número de paràmetres dins de l'objecte
                    for (int j = 0; j< obj1.getPropertyCount(); j++) {
                        //getProperty() Retorna el valor de un paràmetre en un índex determinat
                        SoapObject obj2 = (SoapObject) obj1.getProperty(j);
                        String codi = obj2.getProperty(0).toString();
                        String nom = obj2.getProperty(1).toString();
                        String descripcio = obj2.getProperty(2).toString();
                        //Afegim els paràmetres recollits amb un nou tema a l'ArrayList llistat_temes
                        llistat_temes.add(new TemaFlashCard(codi,nom,descripcio));
                    }
                } else if (params[0].compareTo("numTemes")==0) { //resultat numeroFilesTemes
                    SoapPrimitive resultSOAP1 = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
                    result = Integer.valueOf(resultSOAP1.toString());
                } else if (params[0].compareTo("altaTema")==0) { //resultat altaTema
                    SoapPrimitive resultSOAP1 = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
                    result = Integer.valueOf(resultSOAP1.toString());
                } else if (params[0].compareTo("modificaTema")==0) { //resultat altaTema
                    SoapPrimitive resultSOAP1 = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
                    result = Integer.valueOf(resultSOAP1.toString());
                } else if (params[0].compareTo("eliminaTema")==0) { //resultat altaTema
                    SoapPrimitive resultSOAP1 = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
                    result = Integer.valueOf(resultSOAP1.toString());
                }
            } else {
                System.out.println("TemesDataSource ERROR SOAP Response");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
