package edson.com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class NuevaPassActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_guardar;
    private EditText edit_nuevapass;
    private EditText edit_nuevapassrepeat;
    String usuario ;
    String codigo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevapass);

        btn_guardar = (Button) findViewById(R.id.btn_guardar);
        edit_nuevapass = (EditText) findViewById(R.id.Edit_usuario);
        edit_nuevapassrepeat = (EditText) findViewById(R.id.Edit_Nuevacontraseña);

        Intent it = getIntent();
        if (it != null) {
            Bundle params = it.getExtras();
            if (params != null) {
                usuario = params.getString("usr");
                codigo = params.getString("codigo");
            }
        }

        btn_guardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_guardar){
            registrar();
        }
    }

    private void registrar() {
        String nuevaPass = this.edit_nuevapass.getText().toString().trim();
        String nuevaPass2 = this.edit_nuevapassrepeat.getText().toString().trim();
        boolean isValid = true;

        if (nuevaPass.isEmpty()) {
            edit_nuevapass.setError("Debe ingresar su nueva contraseña");
            isValid = false;
        }
        if (nuevaPass2.isEmpty()) {
            edit_nuevapassrepeat.setError("Debe volver a ingresar su nueva contraseña");
            isValid = false;
        }
        if (nuevaPass != nuevaPass2){
            edit_nuevapassrepeat.setError("La contraseña debe ser la misma");
            isValid = false;
        }
        if (!isValid) {
            return;
        }
        nuevaPass(nuevaPass2);
    }

    private void nuevaPass(final String contraseña) {

        String url = "http://192.168.43.32:8080/freelancerWeb/api/usuario/newPassword";

        JSONObject obj = new JSONObject();
        try {
            obj.put("user",usuario);
            obj.put("code",codigo);
            obj.put("newPassword",contraseña);
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
                                Toast.makeText(NuevaPassActivity.this, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NuevaPassActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                String message = (String) response.get("message");
                                Toast.makeText(NuevaPassActivity.this, message, Toast.LENGTH_SHORT).show();
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
