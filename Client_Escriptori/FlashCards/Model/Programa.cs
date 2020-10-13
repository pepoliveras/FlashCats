using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FlashCards
{
    static class Programa
    {
        /// <summary>
        /// Punto de entrada principal para la aplicación.
        /// </summary>
        [STAThread]
        static void Main()
        {
            // TODO
            // Crea un controlador
            // Controlador control = new Controlador();


            // Crea la vista
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Vista());



            //TODO demanar login

            //TODO
            // Inicia el socket
            // control.connecta


            //Prova webservice

        }
    }
}
