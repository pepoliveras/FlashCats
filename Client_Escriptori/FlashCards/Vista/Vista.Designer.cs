namespace FlashCards
{
    partial class Vista
    {
        /// <summary>
        /// Variable del diseñador necesaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Limpiar los recursos que se estén usando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben desechar; false en caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Vista));
            this.labelinfo = new System.Windows.Forms.Label();
            this.paneltitol = new System.Windows.Forms.Panel();
            this.picturelogo = new System.Windows.Forms.PictureBox();
            this.labeltitol = new System.Windows.Forms.Label();
            this.buttontancar = new System.Windows.Forms.PictureBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.panelbuttonAdminOff = new System.Windows.Forms.Panel();
            this.buttonAdminOff = new System.Windows.Forms.Button();
            this.panelbuttonadmin1 = new System.Windows.Forms.Panel();
            this.buttonadmin1 = new System.Windows.Forms.Button();
            this.panelbuttonadmin2 = new System.Windows.Forms.Panel();
            this.buttonadmin2 = new System.Windows.Forms.Button();
            this.panel5 = new System.Windows.Forms.Panel();
            this.button3 = new System.Windows.Forms.Button();
            this.panel4 = new System.Windows.Forms.Panel();
            this.button2 = new System.Windows.Forms.Button();
            this.panel3 = new System.Windows.Forms.Panel();
            this.button1 = new System.Windows.Forms.Button();
            this.panel2 = new System.Windows.Forms.Panel();
            this.buttonAdminOn = new System.Windows.Forms.Button();
            this.panelinfo = new System.Windows.Forms.Panel();
            this.panelcontingut = new System.Windows.Forms.Panel();
            this.pictureboxprincipal = new System.Windows.Forms.PictureBox();
            this.btConnecta = new System.Windows.Forms.Button();
            this.textboxPass = new System.Windows.Forms.TextBox();
            this.textboxUser = new System.Windows.Forms.TextBox();
            this.paneltitol.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picturelogo)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.buttontancar)).BeginInit();
            this.panel1.SuspendLayout();
            this.panelinfo.SuspendLayout();
            this.panelcontingut.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureboxprincipal)).BeginInit();
            this.SuspendLayout();
            // 
            // labelinfo
            // 
            this.labelinfo.AutoSize = true;
            this.labelinfo.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            this.labelinfo.Location = new System.Drawing.Point(127, 13);
            this.labelinfo.Name = "labelinfo";
            this.labelinfo.Size = new System.Drawing.Size(24, 13);
            this.labelinfo.TabIndex = 2;
            this.labelinfo.Text = "info";
            // 
            // paneltitol
            // 
            this.paneltitol.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(21)))), ((int)(((byte)(28)))), ((int)(((byte)(32)))));
            this.paneltitol.Controls.Add(this.picturelogo);
            this.paneltitol.Controls.Add(this.labeltitol);
            this.paneltitol.Controls.Add(this.buttontancar);
            this.paneltitol.Dock = System.Windows.Forms.DockStyle.Top;
            this.paneltitol.Location = new System.Drawing.Point(0, 0);
            this.paneltitol.Name = "paneltitol";
            this.paneltitol.Size = new System.Drawing.Size(1000, 49);
            this.paneltitol.TabIndex = 7;
            this.paneltitol.MouseDown += new System.Windows.Forms.MouseEventHandler(this.Vista_MouseDown);
            // 
            // picturelogo
            // 
            this.picturelogo.BackColor = System.Drawing.Color.DarkGray;
            this.picturelogo.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.picturelogo.Cursor = System.Windows.Forms.Cursors.Hand;
            this.picturelogo.Image = ((System.Drawing.Image)(resources.GetObject("picturelogo.Image")));
            this.picturelogo.Location = new System.Drawing.Point(12, 12);
            this.picturelogo.Name = "picturelogo";
            this.picturelogo.Size = new System.Drawing.Size(30, 30);
            this.picturelogo.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.picturelogo.TabIndex = 5;
            this.picturelogo.TabStop = false;
            // 
            // labeltitol
            // 
            this.labeltitol.AutoSize = true;
            this.labeltitol.Font = new System.Drawing.Font("Miriam Mono CLM", 26F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(177)));
            this.labeltitol.ForeColor = System.Drawing.SystemColors.ButtonFace;
            this.labeltitol.Location = new System.Drawing.Point(45, 7);
            this.labeltitol.Margin = new System.Windows.Forms.Padding(0);
            this.labeltitol.Name = "labeltitol";
            this.labeltitol.Size = new System.Drawing.Size(206, 39);
            this.labeltitol.TabIndex = 4;
            this.labeltitol.Text = "FLASHCATS";
            // 
            // buttontancar
            // 
            this.buttontancar.BackColor = System.Drawing.Color.Transparent;
            this.buttontancar.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.buttontancar.Cursor = System.Windows.Forms.Cursors.Hand;
            this.buttontancar.Image = ((System.Drawing.Image)(resources.GetObject("buttontancar.Image")));
            this.buttontancar.Location = new System.Drawing.Point(958, 12);
            this.buttontancar.Name = "buttontancar";
            this.buttontancar.Size = new System.Drawing.Size(30, 30);
            this.buttontancar.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.buttontancar.TabIndex = 3;
            this.buttontancar.TabStop = false;
            this.buttontancar.Click += new System.EventHandler(this.buttontancar_Click);
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(32)))), ((int)(((byte)(40)))));
            this.panel1.Controls.Add(this.panelbuttonAdminOff);
            this.panel1.Controls.Add(this.buttonAdminOff);
            this.panel1.Controls.Add(this.panelbuttonadmin1);
            this.panel1.Controls.Add(this.buttonadmin1);
            this.panel1.Controls.Add(this.panelbuttonadmin2);
            this.panel1.Controls.Add(this.buttonadmin2);
            this.panel1.Controls.Add(this.panel5);
            this.panel1.Controls.Add(this.button3);
            this.panel1.Controls.Add(this.panel4);
            this.panel1.Controls.Add(this.button2);
            this.panel1.Controls.Add(this.panel3);
            this.panel1.Controls.Add(this.button1);
            this.panel1.Controls.Add(this.panel2);
            this.panel1.Controls.Add(this.buttonAdminOn);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Left;
            this.panel1.Location = new System.Drawing.Point(0, 49);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(200, 591);
            this.panel1.TabIndex = 8;
            // 
            // panelbuttonAdminOff
            // 
            this.panelbuttonAdminOff.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(21)))), ((int)(((byte)(28)))), ((int)(((byte)(32)))));
            this.panelbuttonAdminOff.Location = new System.Drawing.Point(0, 35);
            this.panelbuttonAdminOff.Name = "panelbuttonAdminOff";
            this.panelbuttonAdminOff.Size = new System.Drawing.Size(13, 45);
            this.panelbuttonAdminOff.TabIndex = 13;
            this.panelbuttonAdminOff.Visible = false;
            // 
            // buttonAdminOff
            // 
            this.buttonAdminOff.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(32)))), ((int)(((byte)(40)))));
            this.buttonAdminOff.FlatAppearance.BorderSize = 0;
            this.buttonAdminOff.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Gray;
            this.buttonAdminOff.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.buttonAdminOff.Font = new System.Drawing.Font("Verdana", 16F);
            this.buttonAdminOff.ForeColor = System.Drawing.SystemColors.AppWorkspace;
            this.buttonAdminOff.Location = new System.Drawing.Point(0, 35);
            this.buttonAdminOff.Name = "buttonAdminOff";
            this.buttonAdminOff.Size = new System.Drawing.Size(200, 45);
            this.buttonAdminOff.TabIndex = 12;
            this.buttonAdminOff.Text = "admin Off";
            this.buttonAdminOff.UseVisualStyleBackColor = false;
            this.buttonAdminOff.Visible = false;
            this.buttonAdminOff.Click += new System.EventHandler(this.buttonAdminOff_Click);
            // 
            // panelbuttonadmin1
            // 
            this.panelbuttonadmin1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(21)))), ((int)(((byte)(28)))), ((int)(((byte)(32)))));
            this.panelbuttonadmin1.Location = new System.Drawing.Point(0, 410);
            this.panelbuttonadmin1.Name = "panelbuttonadmin1";
            this.panelbuttonadmin1.Size = new System.Drawing.Size(13, 45);
            this.panelbuttonadmin1.TabIndex = 11;
            this.panelbuttonadmin1.Visible = false;
            // 
            // buttonadmin1
            // 
            this.buttonadmin1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(32)))), ((int)(((byte)(40)))));
            this.buttonadmin1.FlatAppearance.BorderSize = 0;
            this.buttonadmin1.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Gray;
            this.buttonadmin1.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.buttonadmin1.Font = new System.Drawing.Font("Verdana", 20F);
            this.buttonadmin1.ForeColor = System.Drawing.SystemColors.Control;
            this.buttonadmin1.Location = new System.Drawing.Point(0, 410);
            this.buttonadmin1.Name = "buttonadmin1";
            this.buttonadmin1.Size = new System.Drawing.Size(200, 45);
            this.buttonadmin1.TabIndex = 10;
            this.buttonadmin1.Text = "< 1 >";
            this.buttonadmin1.UseVisualStyleBackColor = false;
            this.buttonadmin1.Visible = false;
            // 
            // panelbuttonadmin2
            // 
            this.panelbuttonadmin2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(21)))), ((int)(((byte)(28)))), ((int)(((byte)(32)))));
            this.panelbuttonadmin2.Location = new System.Drawing.Point(0, 461);
            this.panelbuttonadmin2.Name = "panelbuttonadmin2";
            this.panelbuttonadmin2.Size = new System.Drawing.Size(13, 45);
            this.panelbuttonadmin2.TabIndex = 9;
            this.panelbuttonadmin2.Visible = false;
            // 
            // buttonadmin2
            // 
            this.buttonadmin2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(32)))), ((int)(((byte)(40)))));
            this.buttonadmin2.FlatAppearance.BorderSize = 0;
            this.buttonadmin2.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Gray;
            this.buttonadmin2.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.buttonadmin2.Font = new System.Drawing.Font("Verdana", 20F);
            this.buttonadmin2.ForeColor = System.Drawing.SystemColors.Control;
            this.buttonadmin2.Location = new System.Drawing.Point(0, 461);
            this.buttonadmin2.Name = "buttonadmin2";
            this.buttonadmin2.Size = new System.Drawing.Size(200, 45);
            this.buttonadmin2.TabIndex = 8;
            this.buttonadmin2.Text = "< 2 >";
            this.buttonadmin2.UseVisualStyleBackColor = false;
            this.buttonadmin2.Visible = false;
            // 
            // panel5
            // 
            this.panel5.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(21)))), ((int)(((byte)(28)))), ((int)(((byte)(32)))));
            this.panel5.Location = new System.Drawing.Point(0, 204);
            this.panel5.Name = "panel5";
            this.panel5.Size = new System.Drawing.Size(13, 45);
            this.panel5.TabIndex = 7;
            // 
            // button3
            // 
            this.button3.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(32)))), ((int)(((byte)(40)))));
            this.button3.FlatAppearance.BorderSize = 0;
            this.button3.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Gray;
            this.button3.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.button3.Font = new System.Drawing.Font("Verdana", 20F);
            this.button3.ForeColor = System.Drawing.SystemColors.Control;
            this.button3.Location = new System.Drawing.Point(0, 204);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(200, 45);
            this.button3.TabIndex = 6;
            this.button3.Text = "THREE";
            this.button3.UseVisualStyleBackColor = false;
            // 
            // panel4
            // 
            this.panel4.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(21)))), ((int)(((byte)(28)))), ((int)(((byte)(32)))));
            this.panel4.Location = new System.Drawing.Point(0, 153);
            this.panel4.Name = "panel4";
            this.panel4.Size = new System.Drawing.Size(13, 45);
            this.panel4.TabIndex = 5;
            // 
            // button2
            // 
            this.button2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(32)))), ((int)(((byte)(40)))));
            this.button2.FlatAppearance.BorderSize = 0;
            this.button2.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Gray;
            this.button2.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.button2.Font = new System.Drawing.Font("Verdana", 20F);
            this.button2.ForeColor = System.Drawing.SystemColors.Control;
            this.button2.Location = new System.Drawing.Point(0, 153);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(200, 45);
            this.button2.TabIndex = 4;
            this.button2.Text = "BUTTON ";
            this.button2.UseVisualStyleBackColor = false;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // panel3
            // 
            this.panel3.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(21)))), ((int)(((byte)(28)))), ((int)(((byte)(32)))));
            this.panel3.Location = new System.Drawing.Point(0, 97);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(13, 45);
            this.panel3.TabIndex = 3;
            // 
            // button1
            // 
            this.button1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(32)))), ((int)(((byte)(40)))));
            this.button1.FlatAppearance.BorderSize = 0;
            this.button1.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Gray;
            this.button1.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.button1.Font = new System.Drawing.Font("Verdana", 20F);
            this.button1.ForeColor = System.Drawing.SystemColors.Control;
            this.button1.Location = new System.Drawing.Point(0, 97);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(200, 45);
            this.button1.TabIndex = 2;
            this.button1.Text = "BOTO ONE";
            this.button1.UseVisualStyleBackColor = false;
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.Color.DarkGreen;
            this.panel2.Location = new System.Drawing.Point(0, 35);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(13, 45);
            this.panel2.TabIndex = 1;
            this.panel2.Visible = false;
            // 
            // buttonAdminOn
            // 
            this.buttonAdminOn.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(26)))), ((int)(((byte)(32)))), ((int)(((byte)(40)))));
            this.buttonAdminOn.FlatAppearance.BorderSize = 0;
            this.buttonAdminOn.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Gray;
            this.buttonAdminOn.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.buttonAdminOn.Font = new System.Drawing.Font("Verdana", 16F);
            this.buttonAdminOn.ForeColor = System.Drawing.SystemColors.Control;
            this.buttonAdminOn.Location = new System.Drawing.Point(0, 35);
            this.buttonAdminOn.Name = "buttonAdminOn";
            this.buttonAdminOn.Size = new System.Drawing.Size(200, 45);
            this.buttonAdminOn.TabIndex = 0;
            this.buttonAdminOn.Text = "admin On";
            this.buttonAdminOn.UseVisualStyleBackColor = false;
            this.buttonAdminOn.Visible = false;
            this.buttonAdminOn.Click += new System.EventHandler(this.buttonAdmin_Click);
            // 
            // panelinfo
            // 
            this.panelinfo.BackColor = System.Drawing.SystemColors.ControlDarkDark;
            this.panelinfo.Controls.Add(this.labelinfo);
            this.panelinfo.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panelinfo.Location = new System.Drawing.Point(200, 605);
            this.panelinfo.Name = "panelinfo";
            this.panelinfo.Size = new System.Drawing.Size(800, 35);
            this.panelinfo.TabIndex = 9;
            // 
            // panelcontingut
            // 
            this.panelcontingut.BackColor = System.Drawing.Color.DarkGray;
            this.panelcontingut.Controls.Add(this.pictureboxprincipal);
            this.panelcontingut.Controls.Add(this.btConnecta);
            this.panelcontingut.Controls.Add(this.textboxPass);
            this.panelcontingut.Controls.Add(this.textboxUser);
            this.panelcontingut.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panelcontingut.Location = new System.Drawing.Point(200, 49);
            this.panelcontingut.Name = "panelcontingut";
            this.panelcontingut.Size = new System.Drawing.Size(800, 556);
            this.panelcontingut.TabIndex = 10;
            // 
            // pictureboxprincipal
            // 
            this.pictureboxprincipal.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.pictureboxprincipal.Image = ((System.Drawing.Image)(resources.GetObject("pictureboxprincipal.Image")));
            this.pictureboxprincipal.Location = new System.Drawing.Point(0, -1);
            this.pictureboxprincipal.Name = "pictureboxprincipal";
            this.pictureboxprincipal.Size = new System.Drawing.Size(800, 600);
            this.pictureboxprincipal.TabIndex = 6;
            this.pictureboxprincipal.TabStop = false;
            this.pictureboxprincipal.Visible = false;
            // 
            // btConnecta
            // 
            this.btConnecta.FlatAppearance.BorderSize = 0;
            this.btConnecta.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btConnecta.Font = new System.Drawing.Font("Verdana", 18F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Underline))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btConnecta.ForeColor = System.Drawing.SystemColors.ButtonFace;
            this.btConnecta.Location = new System.Drawing.Point(45, 201);
            this.btConnecta.Name = "btConnecta";
            this.btConnecta.Size = new System.Drawing.Size(252, 48);
            this.btConnecta.TabIndex = 2;
            this.btConnecta.Text = "connecta";
            this.btConnecta.UseVisualStyleBackColor = true;
            this.btConnecta.Click += new System.EventHandler(this.btConnecta_Click);
            // 
            // textboxPass
            // 
            this.textboxPass.BackColor = System.Drawing.Color.DarkGray;
            this.textboxPass.Font = new System.Drawing.Font("Miriam Mono CLM", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(177)));
            this.textboxPass.Location = new System.Drawing.Point(45, 125);
            this.textboxPass.Name = "textboxPass";
            this.textboxPass.PasswordChar = '*';
            this.textboxPass.Size = new System.Drawing.Size(252, 46);
            this.textboxPass.TabIndex = 1;
            this.textboxPass.Text = "Password";
            this.textboxPass.Enter += new System.EventHandler(this.textboxPass_Enter);
            this.textboxPass.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textboxPass_KeyPress);
            // 
            // textboxUser
            // 
            this.textboxUser.BackColor = System.Drawing.Color.DarkGray;
            this.textboxUser.Font = new System.Drawing.Font("Miriam Mono CLM", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(177)));
            this.textboxUser.Location = new System.Drawing.Point(45, 36);
            this.textboxUser.Name = "textboxUser";
            this.textboxUser.Size = new System.Drawing.Size(252, 46);
            this.textboxUser.TabIndex = 0;
            this.textboxUser.Text = "usuari";
            this.textboxUser.Enter += new System.EventHandler(this.textboxUser_Enter);
            this.textboxUser.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.textboxPass_KeyPress);
            // 
            // Vista
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1000, 640);
            this.Controls.Add(this.panelcontingut);
            this.Controls.Add(this.panelinfo);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.paneltitol);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Vista";
            this.Text = "FlashCats";
            this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.Vista_MouseDown);
            this.paneltitol.ResumeLayout(false);
            this.paneltitol.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.picturelogo)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.buttontancar)).EndInit();
            this.panel1.ResumeLayout(false);
            this.panelinfo.ResumeLayout(false);
            this.panelinfo.PerformLayout();
            this.panelcontingut.ResumeLayout(false);
            this.panelcontingut.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureboxprincipal)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.Label labelinfo;
        private System.Windows.Forms.Panel paneltitol;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Panel panelinfo;
        private System.Windows.Forms.Panel panelcontingut;
        private System.Windows.Forms.TextBox textboxUser;
        private System.Windows.Forms.Button btConnecta;
        private System.Windows.Forms.TextBox textboxPass;
        private System.Windows.Forms.Button buttonAdminOn;
        private System.Windows.Forms.PictureBox buttontancar;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Panel panel4;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Panel panelbuttonadmin1;
        private System.Windows.Forms.Button buttonadmin1;
        private System.Windows.Forms.Panel panelbuttonadmin2;
        private System.Windows.Forms.Button buttonadmin2;
        private System.Windows.Forms.Panel panel5;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Panel panelbuttonAdminOff;
        private System.Windows.Forms.Button buttonAdminOff;
        private System.Windows.Forms.Label labeltitol;
        private System.Windows.Forms.PictureBox picturelogo;
        private System.Windows.Forms.PictureBox pictureboxprincipal;
    }
}

