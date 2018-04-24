package edson.com.freelancer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edson.com.freelancer.httpclient.HttpConnection;
import edson.com.freelancer.httpclient.MethodType;
import edson.com.freelancer.httpclient.StandarRequestConfiguration;

public class RestablecerPassActivity extends AppCompatActivity implements View.OnClickListener  {


    private EditText edit_email;
    private Button btn_restablecer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecerpass);

        edit_email = (EditText) findViewById(R.id.Edit_emailConfimacion);
        btn_restablecer = (Button) findViewById(R.id.btn_restablcer);
        btn_restablecer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_restablcer){
            registrar();
            Intent intent = new Intent(this , CodigoVerificacionActivity.class);
            startActivity(intent);
        }
    }
    public static boolean validarEmailSimple(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void registrar() {
        String emailV = edit_email.getText().toString().trim();
        boolean isValid = true;

        if (emailV.isEmpty()) {
            edit_email.setError("Debe ingresar su correo");
            isValid = false;
        } else if (validarEmailSimple(emailV) == false) {
            edit_email.setError("email no valido");
            isValid = false;
        }
        if (!isValid) {
            return;
        }

        String email = edit_email.getText().toString();
        //RegistrarUsuario(username,contraseña,email,getValor());
    }


    private void RestablecerBRL(final String email) {

        //String token = FirebaseInstanceId.getInstance().getToken();

        AsyncTask<String, Integer, String> RegistrarUsuario = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {

                String url = "http://192.168.43.77:8080/RedSocialWeb/ServletRegistro";

                Hashtable<String, String> params = new Hashtable<>();

                params.put("email", strings[1]);
                params.put("accion", "registro");

                String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(url, MethodType.POST, params));
                return respuesta;
            }

            @Override
            protected void onPostExecute(String getnick) {

                if (getnick.contains("invalido")) {
                   // edit_username.setError("este nick ya esta registrado");
                    return;
                } else if (getnick.contains("ok")) {
                    try {
                      //  Toast.makeText(getContext(), "exito", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        //Log.e(RegistroActivity.APP_TAG, "Error al registrarse", e);
                    }
                }
            }
        };

        String[] parametros = {email};
        RegistrarUsuario.execute(parametros);
        //}catch (Exception e){
        //  Toast.makeText(getContext(),"No hay conexión al servidor", Toast.LENGTH_LONG).show();
        //}
    }

    private AsyncTask<String, Integer, String> execuse(String[] p) {
        return execuse(p);
    }



}
