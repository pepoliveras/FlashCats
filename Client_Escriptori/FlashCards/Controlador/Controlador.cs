using FlashCards.Model;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace FlashCards
{
    class Controlador


    {
        // Crea image benvinguda
        Image imatge = Image.FromFile("F:/Repositori/FlashCards/FlashCards/Assets/presentacio_ROB2702.jpg");

        // Codi de sessió
        String codi = "";

        //objectes vista i model
        //private Connexio con;
        private Vista vista;


        // Constructor. Requereix un objecte Vista on actuar

        public Controlador(Vista vista)
        {
            this.vista = vista;


        }
        // Mètode provisional fase prova
        public void Connecta()
        {
            //TO DO
            // Inici socket client
            // Connexio.

            // connexió socket
            //Connexio.StartClient(vista);
            //vista.Setlabeltext();

            //Connexio webservice i desa la resposta
            String resposta = ConnexioWeb.connecta(vista);
            //AnalitzaResposta(resposta);


        }

        //Anàlisi de la resposta del servidor
        public void AnalitzaResposta(string primera, string segona)
        {
            vista.SetlabelInfo("analitzant resposta" + " primera: " + primera + " segona: " + segona);

            if ((primera == "0") && (segona == "0"))
            {
                // Usuari no reconegut
                // TODO torna a introduir login pass
                vista.SetlabelInfo("usuari no reconegut");

            }
            else if ((primera != "0") && (segona == "1"))
            {
                codi = primera;
                //modo admin

                //Mostra el botó per activar el mode admin
                vista.AdminMode(true);
                PosaImatge(imatge);
                vista.SetlabelInfo("administrador");
            }
            else if ((primera != "0") && (segona == "0"))
            {
                codi = primera;
                //mode usuari
                vista.AdminMode(false);
                PosaImatge(imatge);
                vista.SetlabelInfo("usuari");
            }
            else if ((primera != "0") && (segona == "-1"))
            {
                codi = primera;
                // sessió ja iniciada
            }

        }

        // Carrega imatge principal
        public void PosaImatge(Image imatge)
        {
            vista.SetPicturePrincipal(imatge);
        }



        public void Login(string user, string pass)
        {
            // TODO
            //Connexio webservice i desa la resposta
            String[] resposta = ConnexioWeb.login(user, pass);
            AnalitzaResposta(resposta[0], resposta[1]);
        }

        public void Logout()
        {
            ConnexioWeb.logout(codi);

        }
    }

}
