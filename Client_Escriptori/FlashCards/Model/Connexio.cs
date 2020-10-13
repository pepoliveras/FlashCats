using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using System.Net.Sockets;

namespace FlashCards

{
    //Socket client sincrònic
    public static class Connexio
    {
        

        public static void  StartClient(Vista vista)
        {
            //string user = "roberto_guiral";
            //string pass = "user_password";

            //String servidor = "127.0.0.1";
            String servidor = "jlzorita.ddns.net/";


            try
            {
                vista.SetlabelInfo("connectant");
                // missatge per enviar
                //probar login mysql
                String missatge = "Hola server :)";
                

                //Crea un client TCP
                int port = 6066;
                //int port = 3306;
                TcpClient client = new TcpClient(servidor, port);

                // Enmmagatzema el missatge en un array de byte
                // missatge String --> Byte[]
                Byte[] buffer = System.Text.Encoding.ASCII.GetBytes(missatge);

                //Fluxe de dades per comunicar-se amb servidor
                NetworkStream stream = client.GetStream();

                // Envia missatge
                stream.Write(buffer, 0, buffer.Length);
                //Console.WriteLine("Enviat: {0}", missatge);
                vista.SetlabelInfo(missatge);
                
                // Resposta del servidor.

                // Reinicializem el buffer per desar la resposta.
                // màxim de bytes que es llegiran -->256 
                buffer = new Byte[256];

                // String per la resposta.
                String resposta = String.Empty;

                // Llegeix la resposta del servidor
                //bytes indica el nombre total que es llegeixen al buffer
                Int32 bytes = stream.Read(buffer, 0, buffer.Length);

                // resposta byte[] --> string
                resposta = System.Text.Encoding.ASCII.GetString(buffer, 0, bytes);
                //Console.WriteLine("Received: {0}", resposta);
                vista.SetlabelInfo(resposta);

                // Tancar
                stream.Close();
                client.Close();

                

            }
                catch (ArgumentNullException ane)
                {
                    Console.WriteLine("ArgumentNullException : {0}", ane.ToString());
                }
                catch (SocketException se)
                {
                    Console.WriteLine("SocketException : {0}", se.ToString());
                }
                catch (Exception e)
                {
                    Console.WriteLine("Unexpected exception : {0}", e.ToString());
                }
            

            
                
            
        }

    }
}
