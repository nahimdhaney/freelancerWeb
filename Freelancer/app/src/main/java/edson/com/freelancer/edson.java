package edson.com.freelancer;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



/**
 * A login screen that offers login via email/password.
 */
public class edson extends AppCompatActivity implements View.OnClickListener {


    private AutoCompleteTextView tv_nickname;
    private EditText edit_contraseña;
    private Button btn_acceder;
    private TextView tv_nuevacuenta;

    public static final String mipreferencia = "mipref";
    public static final String name = "nickname";
    public static final String pass = "contraseña";
    SharedPreferences sharedPreferences;

    public static Context contextOfApplication;

    public static String APP_TAG = "login";

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


        sharedPreferences = getSharedPreferences(mipreferencia, Context.MODE_PRIVATE);

        contextOfApplication = getApplicationContext();

        tv_nuevacuenta.setOnClickListener(this);
        btn_acceder.setOnClickListener(this);
        isSesion();
    }

    //tv.setTextColor(Color.GREEN);

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (v.getId()) {
            case R.id.tv_NuevaCuenta:
                //registrarse();
                new RegistroDialog().show(fragmentManager, "LoginDialog");
                break;
            case R.id.btn_acceder:
                acceder();
                //aux();
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
            Log.e(edson.APP_TAG, "error al al logearse", e);
        }
    }

    private void registrarse() {
        // Intent itent = new Intent(LoginActivity.this , RegistroActivity.class);
        // startActivity(itent);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    boolean isValid;

    private void validarCampo() {

        String nickname2 = tv_nickname.getText().toString().trim();
        String contraseña2 = edit_contraseña.getText().toString().trim();
        isValid = true;
        if (nickname2.isEmpty()) {
            tv_nickname.setError("Debe ingresar su username");
            isValid = false;
        }
        if (contraseña2.isEmpty()) {
            edit_contraseña.setError("Debe ingresar su password");
            isValid = false;
        }
    }


    private void acceder() {

        this.validarCampo();
        if (!isValid) {
            return;
        }
        String username = tv_nickname.getText().toString();
        String contraseña = edit_contraseña.getText().toString();

        //obtener(username, contraseña);
    }

}