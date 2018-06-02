package edson.com.freelancer;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import edson.com.freelancer.Model.Usuario;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private AutoCompleteTextView tv_nickname;
    private EditText edit_contraseña;
    private Button btn_acceder;
    private TextView tv_nuevacuenta;
    private TextView tv_RestablecerPass;

    public static final String mipreferencia = "mipref";
    public static final String name = "nickname";
    public static final String pass = "contraseña";

    SharedPreferences sharedPreferences;
    public static Context contextOfApplication;
    public static String APP_TAG = "login";

   // UsuarioBRL usuario = new UsuarioBRL();

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_nickname = (AutoCompleteTextView) findViewById(R.id.tv_nickname);
        edit_contraseña = (EditText) findViewById(R.id.edit_contraseña);
        btn_acceder = (Button) findViewById(R.id.btn_acceder);
        tv_nuevacuenta = (TextView) findViewById(R.id.tv_NuevaCuenta);
        tv_RestablecerPass = (TextView) findViewById(R.id.tv_RestablecerPass);

        sharedPreferences = getSharedPreferences(mipreferencia, Context.MODE_PRIVATE);

        contextOfApplication = getApplicationContext();

        tv_nuevacuenta.setOnClickListener(this);
        tv_RestablecerPass.setOnClickListener(this);
        btn_acceder.setOnClickListener(this);
        isSesion();
    }

    //tv.setTextColor(Color.GREEN);

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (v.getId()) {
            case R.id.tv_NuevaCuenta:
                new RegistroDialog().show(fragmentManager, "LoginDialog");
                break;
            case R.id.tv_RestablecerPass:
                Intent intent = new Intent(this,RestablecerPassActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_acceder:
                acceder();
                break;
        }
    }

    private void isSesion() {

        sharedPreferences = getSharedPreferences(mipreferencia, Context.MODE_PRIVATE);
        String nickname = sharedPreferences.getString("edson", "");
        String password = sharedPreferences.getString("cito", "");

        try {
            if (nickname.equals("") && password.equals("")) {
                Toast.makeText(this, "no hay registros", Toast.LENGTH_LONG).show();
            } else {
                //obtener("edson" ,"cito");
                //      Intent itent = new Intent(LoginActivity.this, RespuestaActivity.class);
                //      startActivity(itent);
                //obtener(name ,pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LoginActivity.APP_TAG, "error al logearse", e);
        }
    }

    private void acceder() {
        String nickname2 = tv_nickname.getText().toString().trim();
        String contraseña2 = edit_contraseña.getText().toString().trim();

        boolean isValid = true;
        if (nickname2.isEmpty()) {
            tv_nickname.setError("Debe ingresar su username");
            isValid = false;
        }
        if (contraseña2.isEmpty()) {
            edit_contraseña.setError("Debe ingresar su password");
            isValid = false;
        }
        if (!isValid) {
            return;
        }
        String username = tv_nickname.getText().toString();
        String contraseña = edit_contraseña.getText().toString();
        //Intent itent = new Intent(LoginActivity.this, menuActivity.class);
        //startActivity(itent);
        logearse(username, contraseña);
    }

    public void logearse(String usr, String pass){
        String url = "http://192.168.43.32:8080/freelancerWeb/api/usuario/login";
        JSONObject obj = new JSONObject();
        try {
            obj.put("user",usr);
            obj.put("password",pass);
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
                                JSONObject usuario = (JSONObject) response.get("response");

                                Usuario objUsuario = new Usuario();
                                objUsuario.setId(usuario.getInt("id"));
                                objUsuario.setFullName(usuario.getString("fullName"));
                                objUsuario.setUser(usuario.getString("user"));
                                objUsuario.setEmail(usuario.getString("email"));
                                objUsuario.setType(usuario.getInt("type"));

                                Usuario.setUsuario(objUsuario);

                                Intent intent = new Intent(LoginActivity.this, Activity_List_Estado.class);
                                startActivity(intent);
                            } else {
                                String message = (String) response.get("message");
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
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

   /* private void Logearse(final String user, final String contra) {
       //String token = FirebaseInstanceId.getInstance().getToken();
       try {
           AsyncTask<String, Integer, String> task = new AsyncTask<String, Integer, String>() {
               @Override
               protected String doInBackground(String... strings) {

                   //casa de elmer
                   //String url = "http://192.168.1.4:8080/RedSocialWeb/ServletRegistro";

                   //micelu
                   String url = "http://192.168.0.21:8080/freelancerWeb/api/usuario/login";

                   Hashtable<String, String> params = new Hashtable<>();
                   params.put("user", strings[0]);
                   params.put("password", strings[1]);
                   String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(url, MethodType.POST, params));
                   return respuesta;
               }
               @Override
               protected void onPostExecute(String getContenido) {
                    if (getContenido == null) return;
                   if (getContenido.contains("name")) {
                       //es por que esta mal el nick
                       tv_nickname.setError("el nickname es incorrecto");
                       return;
                   } else if (getContenido.contains("pass")) {
                       //es por que esta mal la contraseña
                       edit_contraseña.setError("la contraseña es incorrecto   ");
                       return;
                   } else if (getContenido.contains("incorrecto")) {
                       //es por que ambas estan mal
                       tv_nickname.setError("el nickname es incorrecto");
                       edit_contraseña.setError("la contraseña es incorrecto");
                       return;

                   } else if (getContenido.contains("ok")) {

                       Usuario usr = new Usuario();
                       usr.setUsername(user);

                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.putString("edson", user);
                       editor.putString("cito", contra);
                       editor.commit();
                       Intent itent = new Intent(LoginActivity.this, menuActivity.class);
                       startActivity(itent);
                   }
               }
           };
           String[] parametros = {user, contra};
           task.execute(parametros);
       } catch (Exception e) {
           Toast.makeText(this,  "No hay conexión al servidor", Toast.LENGTH_LONG).show();
       }
    }
     private AsyncTask<String, Integer, String> execuse(String[] p) {
         return execuse(p);
    }*/

}

