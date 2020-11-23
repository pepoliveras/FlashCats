package com.flashcats.data;

import com.flashcats.data.model.FlashCard;
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
* Classe que accedeix a la font de dades per alimentar el continguts del llistat de FlashCards
*
**/
public class FlashCardsDataSource {

    private int result;
    private ArrayList<FlashCard> llistat_flashcards;

    private static String URL = "http://jlzorita.ddns.net:8080/WebService_flashcats/WebService_flashcats?wsdl";
    private static String NAMESPACE = "http://webservice_package/";
    private String METHOD_NAME = "llistarFlashcards";
    private String SOAP_ACTION = "";

    public FlashCardsDataSource() {
        this.llistat_flashcards = new ArrayList<FlashCard>();
    }

    public ArrayList<FlashCard> obtenirLlistatFlashCards(String codi_sessio, String codi_tema, String num_flashcards){
        try {
            webservice_call("llistarFlashCards",codi_sessio,codi_tema,"1",num_flashcards);
        } catch(Exception e)  {
            System.out.println("TemesDataSource ERROR obtenirLlistatFlashCards");
        }
        return llistat_flashcards;
    }

    public int altaFlashCard(String codi_sessio,String codi_tema,String anvers_text, String revers_text){
        try {
            webservice_call("altaFlashCard",codi_sessio,codi_tema,anvers_text,revers_text);
        } catch(Exception e)  {
            System.out.println("TemesDataSource ERROR altaFlashCard");
        }
        return result;
    }

    public Integer numFlashCards(String codi_sessio,String codi_tema){
        try {
            webservice_call("numFlashCards",codi_sessio,codi_tema);
        } catch(Exception e)  {
            System.out.println("TemesDataSource ERROR numFlashCards");
        }
        return result;
    }

    public int modificaFlashCard(String codi_sessio,String codi_flashcard,String anvers_text, String revers_text){
        try {
            webservice_call("modificaFlashCard",codi_sessio,codi_flashcard,anvers_text,revers_text);
        } catch(Exception e)  {
            System.out.println("TemesDataSource ERROR modificaFlashCard");
        }
        return result;
    }

    public int eliminaFlashCard(String codi_sessio,String codi_flashcard){
        try {
            webservice_call("eliminaFlashCard",codi_sessio,codi_flashcard);
        } catch(Exception e)  {
            System.out.println("TemesDataSource ERROR eliminaFlashCard");
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

        if (params[0].compareTo("llistarFlashCards")==0) { //llistar flashcards
            METHOD_NAME = "llistarFlashcards";
            // Inicialitzem la crida SOAP
            soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            SOAP_ACTION = NAMESPACE.concat(METHOD_NAME);

            // afegim a la crida el primer paràmetre "codi_sessio"
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("codi_sessio");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[1]);

            // afegim a la crida el segon paràmetre "codi_tema"
            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("codi_tema");
            propertyInfo2.setType(String.class);
            propertyInfo2.setValue(params[2]);

            // afegim a la crida el tercer paràmetre "num_pagina"
            PropertyInfo propertyInfo3 = new PropertyInfo();
            propertyInfo3.setName("num_pagina");
            propertyInfo3.setType(String.class);
            propertyInfo3.setValue(params[3]);

            // afegim a la crida el tercer paràmetre "num_files"
            PropertyInfo propertyInfo4 = new PropertyInfo();
            propertyInfo4.setName("num_files");
            propertyInfo4.setType(String.class);
            propertyInfo4.setValue(params[4]);

            soapObject.addProperty(propertyInfo1);
            soapObject.addProperty(propertyInfo2);
            soapObject.addProperty(propertyInfo3);
            soapObject.addProperty(propertyInfo4);

        } else if (params[0].compareTo("numFlashCards")==0) { //nombre de flashcards
            METHOD_NAME = "numeroFilesFlashcards";
            // Inicialitzem la crida SOAP
            soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            SOAP_ACTION = NAMESPACE.concat(METHOD_NAME);

            // afegim a la crida el primer paràmetre "codi_sessio"
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("codi_sessio");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[1]);

            // afegim a la crida el primer paràmetre "codi_tema"
            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("codi_tema");
            propertyInfo2.setType(String.class);
            propertyInfo2.setValue(params[2]);

            soapObject.addProperty(propertyInfo1);
            soapObject.addProperty(propertyInfo2);

        } else if (params[0].compareTo("altaFlashCard")==0) { //donar d'alta una nova flashcard
            METHOD_NAME = "altaFlashcard";
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

            // afegim a la crida el paràmetre "anvers_text"
            PropertyInfo propertyInfo3 = new PropertyInfo();
            propertyInfo3.setName("anvers_text");
            propertyInfo3.setType(String.class);
            propertyInfo3.setValue(params[3]);

            // afegim a la crida el paràmetre "revers_text"
            PropertyInfo propertyInfo4 = new PropertyInfo();
            propertyInfo4.setName("revers_text");
            propertyInfo4.setType(String.class);
            propertyInfo4.setValue(params[4]);

            soapObject.addProperty(propertyInfo1);
            soapObject.addProperty(propertyInfo2);
            soapObject.addProperty(propertyInfo3);
            soapObject.addProperty(propertyInfo4);

        } else if (params[0].compareTo("modificaFlashCard")==0) { //modificar una flashcard existent
            METHOD_NAME = "modificaFlashcard";
            // Inicialitzem la crida SOAP
            soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            SOAP_ACTION = NAMESPACE.concat(METHOD_NAME);

            // afegim a la crida el paràmetre "codi_sessio"
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("codi_sessio");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[1]);

            // afegim a la crida el paràmetre "codi_flashcard"
            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("codi_flashcard");
            propertyInfo2.setType(String.class);
            propertyInfo2.setValue(params[2]);

            // afegim a la crida el paràmetre "anvers_text"
            PropertyInfo propertyInfo3 = new PropertyInfo();
            propertyInfo3.setName("anvers_text");
            propertyInfo3.setType(String.class);
            propertyInfo3.setValue(params[3]);

            // afegim a la crida el paràmetre "revers_text"
            PropertyInfo propertyInfo4 = new PropertyInfo();
            propertyInfo4.setName("revers_text");
            propertyInfo4.setType(String.class);
            propertyInfo4.setValue(params[4]);

            soapObject.addProperty(propertyInfo1);
            soapObject.addProperty(propertyInfo2);
            soapObject.addProperty(propertyInfo3);
            soapObject.addProperty(propertyInfo4);

        } else if (params[0].compareTo("eliminaFlashCard")==0) { //eliminar una flashcard existent
            METHOD_NAME = "esborraFlashcard";
            // Inicialitzem la crida SOAP
            soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
            SOAP_ACTION = NAMESPACE.concat(METHOD_NAME);

            // afegim a la crida el paràmetre "codi_sessio"
            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("codi_sessio");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[1]);

            // afegim a la crida el paràmetre "codi_flashcard"
            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("codi_flashcard");
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

                if (params[0].compareTo("llistarFlashCards")==0) { //resultat llistar flashcards

                    SoapObject obj1 = (SoapObject) envelope.bodyIn;

                    //getPropertyCount() Retorna el número de paràmetres dins de l'objecte
                    for (int j = 0; j< obj1.getPropertyCount(); j++) {
                        //getProperty() Retorna el valor de un paràmetre en un índex determinat
                        SoapObject obj2 = (SoapObject) obj1.getProperty(j);
                        String codi_flashcard = obj2.getProperty(0).toString();
                        String codi_tema = obj2.getProperty(1).toString();
                        String anvers_text = obj2.getProperty(2).toString();
                        //String ext_multimedia = obj2.getProperty(3).toString();
                        String revers_text = obj2.getProperty(4).toString();

                        //Afegim els paràmetres recollits amb una nova flashcard a l'ArrayList llistat_flashcards
                        llistat_flashcards.add(new FlashCard(codi_flashcard,codi_tema,anvers_text,revers_text));
                    }
                } else if (params[0].compareTo("numFlashCards")==0) { //resultat numFlashCards
                    SoapPrimitive resultSOAP1 = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
                    result = Integer.valueOf(resultSOAP1.toString());
                } else if (params[0].compareTo("altaFlashCard")==0) { //resultat altaFlashCard
                    SoapPrimitive resultSOAP1 = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
                    result = Integer.valueOf(resultSOAP1.toString());
                } else if (params[0].compareTo("modificaFlashCard")==0) { //resultat modificaFlashCard
                    SoapPrimitive resultSOAP1 = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
                    result = Integer.valueOf(resultSOAP1.toString());
                } else if (params[0].compareTo("eliminaFlashCard")==0) { //resultat eliminaFlashCard
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
