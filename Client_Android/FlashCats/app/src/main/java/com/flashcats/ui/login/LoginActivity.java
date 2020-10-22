package com.flashcats.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flashcats.PantallaPrincipal;
import com.flashcats.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public ArrayList<String> retornWebService; // Dades de retorn del Webservice
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    private LoginViewModel loginViewModel;

    private String URL = "http://jlzorita.ddns.net:8080/WebService_flashcats/WebService_flashcats?wsdl";
    private String NAMESPACE = "http://webservice_package/";
    private String METHOD_NAME = "login";
    private String SOAP_ACTION = "http://webservice_package/login";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {

            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                // Cridem al WebService
                try {
                    //Es crea la classe que crida a la connexió de forma asíncorna
                    // i li passem la referència de lobjecrte que la crida
                    // per fer el retorn dels paràmetres de forma també asíncrona.
                    new WebServiceClient().execute(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                    
                    // Cridem a la següent pantalla
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            segPntalla();
                        }
                    }, 3000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        //String welcome = getString(R.string.welcome) + model.getDisplayName();
        final EditText usernameEditText = findViewById(R.id.username);
        String welcome = getString(R.string.welcome) +  usernameEditText.getText().toString();

        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void segPntalla() {
        Bundle extras = new Bundle();
        Intent intent = new Intent(this, PantallaPrincipal.class);

        if (retornWebService != null){
            extras.putString("param1", this.retornWebService.get(0));
            extras.putString("param2", this.retornWebService.get(1));
            intent.putExtras(extras);
        }

        startActivity(intent,extras);
    }

    // métodes Webservice Server:
    // login(usuari,password)
    // ├──> retorna String[] amb [0,0] si falla o [clau_sessió, tipus_user] si èxit
    // ├──> tipus:user: 0 --> usuari normal, 1 --> usuari admin
    // └──> usuaris (demo, demo) o (root, root) per fer proves
    // logout(clau) no retorna res
    // responMissatge (msg) retorna "Missatge rebut" + msg

    // Inner Class per a fer la crida SOAP asíncronament
    class WebServiceClient extends AsyncTask<String, Void, ArrayList<String> > {

        protected void onPostExecute(ArrayList<String> retorn) {
            //Passem el resultat de l'execució al fil principal d'execució que ens ha cridat
            retornWebService = retorn;
        }

        /**
         * Parse out a primitive String or a primitive String List response.
         *
         * @param response Object
         * @return values LinkedList<String>
         */
        protected ArrayList<String> getPrimitiveStringList(Object response) {
            ArrayList<String> values = new ArrayList<String>();
            try {
                if ((response != null) && (response instanceof SoapPrimitive)) {
                    String value = (String) ((SoapPrimitive) response).getValue();
                    values.add(value);
                } else if ((response != null) && (response instanceof List)) {
                    List<SoapPrimitive> primitiveValues = (List<SoapPrimitive>) response;
                    for (SoapPrimitive primitiveValue : primitiveValues) {
                        values.add((String) primitiveValue.getValue());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return values;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This will normally run on a background thread. But to better
         * support testing frameworks, it is recommended that this also tolerates
         * direct execution on the foreground thread, as part of the {@link #execute} call.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected ArrayList<String> doInBackground(String... params) {

            ArrayList<String> retornValues = new ArrayList<String>();

            // Inicialitzem la crida SOAP
            SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo propertyInfo1 = new PropertyInfo();
            propertyInfo1.setName("usuari");
            propertyInfo1.setType(String.class);
            propertyInfo1.setValue(params[0]);

            PropertyInfo propertyInfo2 = new PropertyInfo();
            propertyInfo2.setName("password");
            propertyInfo2.setType(String.class);
            propertyInfo2.setValue(params[1]);

            soapObject.addProperty(propertyInfo1);
            soapObject.addProperty(propertyInfo2);

            //Declarem la cersió de la crida SOAP
            SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER10);
            //envelope.dotNet = true;
            // Set output SOAP object
            envelope.setOutputSoapObject(soapObject);

            try {
                HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
                httpTransportSE.debug = true;

                // Fem la crida
                httpTransportSE.call(SOAP_ACTION, envelope);

                // Just being curious let's see how our XML request/response look like
                System.out.println("SOAP Request " + httpTransportSE.requestDump);
                System.out.println("SOAP Response " + httpTransportSE.responseDump);

                // Obtenim la resposta
                String prova = envelope.getResponse().toString();

                System.out.println("envelope toString: " + prova);

                if (envelope.bodyIn != null) {
                    //getProperty() Returns a specific property at a certain index.
                    SoapPrimitive resultSOAP1 = (SoapPrimitive) ((SoapObject) envelope.bodyIn)
                            .getProperty(0);
                    SoapPrimitive resultSOAP2 = (SoapPrimitive) ((SoapObject) envelope.bodyIn)
                            .getProperty(1);

                    retornValues.add(resultSOAP1.toString());
                    retornValues.add(resultSOAP2.toString());

                    System.out.println("param1: " + resultSOAP1.toString());
                    System.out.println("param2: " + resultSOAP2.toString());
                } else {
                    Toast.makeText(getApplicationContext(), "No Response from server",Toast.LENGTH_LONG).show();
                }

                /*SoapObject result = (SoapObject) envelope.bodyIn;
                SoapPrimitive resultSOAP = (SoapPrimitive) ((SoapObject) envelope.bodyIn);

                if(result != null)
                {
                    //retornValues = getPrimitiveStringList(result);
                    //retornWebService.retornWebService = retornValues;
                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }

            return retornValues;
        }

    }

}