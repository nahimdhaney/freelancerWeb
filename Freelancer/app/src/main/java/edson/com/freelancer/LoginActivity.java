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

//dxasas
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
                //Intent i = new Intent(this , menuActivity.class);
                //startActivity(i);
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
                return;
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

        String url = "http://192.168.0.15:8080/Ingenieria_de_software_3/api/usuario/login";

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

                                Intent intent = new Intent(LoginActivity.this, menuActivity.class);
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
}

