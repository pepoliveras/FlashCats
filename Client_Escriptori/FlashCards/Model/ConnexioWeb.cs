using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Runtime.Remoting.Channels;
using System.Text;
using System.Threading.Tasks;
using System.Web.Services;
using FlashCards.net.ddns.jlzorita;


namespace FlashCards.Model
{
    class ConnexioWeb
    {
        private string codi = "";

        //Connecta al webservice i torna un missatge
        // funció només per fase de proves
        public static string connecta(Vista vista)
        {
            WebService_flashcats enlinia = new WebService_flashcats();


            String resposta = enlinia.responMissatge("Hola Server :)");
            
            Console.WriteLine(resposta);
            vista.SetlabelInfo(resposta);
            return (resposta);
        }

        // fa login
        public static string[] login(string user, string pass)
        {
            WebService_flashcats enlinia = new WebService_flashcats();


            String[] resposta = enlinia.login(user, pass);

            
            return (resposta);
        }

        // fa logout
        public static void logout(string codi)
        {
            WebService_flashcats enlinia = new WebService_flashcats();


            enlinia.logout(codi);


            
        }

        //***** getters && setters *******
        public void setcodi (string codi)
        {
            this.codi = codi;
        }
        
    }

}
