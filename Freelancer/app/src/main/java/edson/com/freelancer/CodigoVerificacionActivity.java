package edson.com.freelancer;

import android.content.Intent;
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

public class CodigoVerificacionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_verificar;
    private EditText edit_codigo;
    String usuario ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigoverificacion);
        btn_verificar = (Button) findViewById(R.id.btn_verificar);
        edit_codigo = (EditText) findViewById(R.id.Edit_codigo);

        Intent it = getIntent();
        if (it != null) {
            Bundle params = it.getExtras();
            if (params != null) {
                usuario = params.getString("usr");
            }
        }
        btn_verificar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_verificar){
            registrar();
            Intent intent = new Intent(this , NuevaPassActivity.class);
            startActivity(intent);
        }
    }

    private void registrar() {
        String edit_codigoV = this.edit_codigo.getText().toString().trim();
        boolean isValid = true;
        if (edit_codigoV.isEmpty()) {
            edit_codigo.setError("Debe ingresar el codigo de verificación");
            isValid = false;
        }
        if (!isValid) {
            return;
        }
        Restablecer(usuario, edit_codigoV);
    }


    private void Restablecer(final String usuario, final String codigo) {

        String url = "http://192.168.43.32:8080/freelancerWeb/api/usuario/validateCode";

        JSONObject obj = new JSONObject();
        try {
            obj.put("user",usuario);
            obj.put("code",codigo);
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
                                Toast.makeText(CodigoVerificacionActivity.this, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CodigoVerificacionActivity.this, NuevaPassActivity.class);
                                Bundle params = new Bundle();
                                params.putString("usr",usuario);
                                params.putString("codigo",codigo);
                                intent.putExtras(params);
                                startActivity(intent);
                            } else {
                                String message = (String) response.get("message");
                                Toast.makeText(CodigoVerificacionActivity.this, message, Toast.LENGTH_SHORT).show();
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
