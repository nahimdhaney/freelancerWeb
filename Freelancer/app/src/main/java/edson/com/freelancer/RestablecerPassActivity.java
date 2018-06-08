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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edson.com.freelancer.Model.Usuario;
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
            acceder();
        }
    }

    /*public static boolean validarEmailSimple(String email) {
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
    }*/


    private void acceder() {
        String email = edit_email.getText().toString().trim();
        boolean isValid = true;
        if (email.isEmpty()) {
            edit_email.setError("Debe ingresar su correo");
            isValid = false;
        }
        if (!isValid) {
            return;
        }
        Restablecer(email);
    }

    private void Restablecer(final String email) {

        String url = "http://192.168.43.32:8080/freelancerWeb/api/usuario/passwordForgotten";

        JSONObject obj = new JSONObject();
        try {
            obj.put("user",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest= new JsonObjectRequest(
                Request.Method.POST,
                url,
                obj,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = (boolean) response.get("success");
                            if (success) {
                                String message = (String) response.get("message");
                                Toast.makeText(RestablecerPassActivity.this, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RestablecerPassActivity.this, CodigoVerificacionActivity.class);
                                Bundle params = new Bundle();
                                params.putString("usr",email);
                                intent.putExtras(params);
                                startActivity(intent);
                            } else {
                                String message = (String) response.get("message");
                                Toast.makeText(RestablecerPassActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(objectRequest);
    }

}
