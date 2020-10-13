using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FlashCards
{
    public partial class Vista : Form
    {
        // *****************************************
        // Aquestes línies de codi s'han copiat de:
        // https://stackoverrun.com/es/q/309672
        // *****************************************
        // Permet moure la finestra sense necessitat de tenir barra de títol
        // amb la funcio Vista_MouseDown
        public const int WM_NCLBUTTONDOWN = 0xA1;
        public const int HT_CAPTION = 0x2;

        [System.Runtime.InteropServices.DllImportAttribute("user32.dll")]
        public static extern int SendMessage(IntPtr hWnd, int Msg, int wParam, int lParam);
        [System.Runtime.InteropServices.DllImportAttribute("user32.dll")]
        public static extern bool ReleaseCapture();

        // *****************************************



        //objecte per desar el controlador
        Controlador con;

        public Vista()
        {
            InitializeComponent();

            // Crida a la funció per crear un controlador
            CreaControlador();

        }

        private void btConnecta_Click(object sender, EventArgs e)
        {
            //con.Connecta();
            //con.UserPass(textboxUser.Text, textboxPass.Text);


            string pass = "";
            string user = "";

            // captura text
            user = textboxUser.Text;
            pass = textboxPass.Text;


            //con.Login("demo", "demo");
            con.Login(user, pass);

        }

        private void CreaControlador()
        {
            //Crea un controlador amb aquesta vista per paràmetre
            con = new Controlador(this);
            //labelinfo.Text = "controlador creat";

        }

        //Accepta polsar tecla 'intro' per connectar
        private void textboxPass_KeyPress(object sender, KeyPressEventArgs e)
        {
            if ((int)e.KeyChar == (int)Keys.Enter)
            {
                // TODO
                btConnecta.PerformClick();
            }

        }





        




        //Posa la imatge principal
        public void SetPicturePrincipal(Image imatge)
        {
            pictureboxprincipal.Image = imatge;
            pictureboxprincipal.Visible = true;
            //pictureboxprincipal.BringToFront();
        }


        // Estableix el valor de l'etiqueta
        public void SetlabelInfo(String text)
        {

            labelinfo.Text = text;
        }

        // Activa el mode admin
        // o el mode usuari
        public void AdminMode(Boolean admin)
        {
            // amaga login ui
            btConnecta.Visible = false;
            textboxUser.Visible = false;
            textboxPass.Visible = false;


            if (admin)
            {
                buttonAdminOn.Visible = true;
                panel2.Visible = true;
                buttonadmin1.Visible = true;
                buttonadmin2.Visible = true;
                panelbuttonadmin1.Visible = true;
                panelbuttonadmin2.Visible = true;
                buttonAdminOff.Visible = false;
                panelbuttonAdminOff.Visible = false;
            }
            else
            {
                buttonAdminOn.Visible = false;
                panel2.Visible = false;
                buttonadmin1.Visible = false;
                buttonadmin2.Visible = false;
                panelbuttonadmin1.Visible = false;
                panelbuttonadmin2.Visible = false;
            }
        }

        // Permet moure la finestra sense necessitat de tenir barra de títol
        private void Vista_MouseDown(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                ReleaseCapture();
                SendMessage(Handle, WM_NCLBUTTONDOWN, HT_CAPTION, 0);
            }
        }

        // Tanca l'aplicació i fa logout

        private void buttontancar_Click(object sender, EventArgs e)
        {
            con.Logout();
            Application.Exit();
        }

        private void button2_Click(object sender, EventArgs e)
        {

        }

        // Canvia a mode admin off
        private void buttonAdmin_Click(object sender, EventArgs e)
        {
            AdminMode(false);
            buttonAdminOff.Visible = true;
            panelbuttonAdminOff.Visible = true;
        }

        // Canvia a mode admin On
        private void buttonAdminOff_Click(object sender, EventArgs e)
        {
            AdminMode(true);

        }

        //Selecciona tot el text quan s'entra al textbox
        private void textboxPass_Enter(object sender, EventArgs e)
        {
            textboxPass.SelectAll();
        }

        //Selecciona tot el text quan s'entra al textbox
        private void textboxUser_Enter(object sender, EventArgs e)
        {
            textboxUser.SelectAll();
        }
    }
}
