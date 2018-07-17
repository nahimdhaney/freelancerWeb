package edson.com.freelancer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edson.com.freelancer.Model.Proyectos;
import edson.com.freelancer.Model.Usuario;
import edson.com.freelancer.adapter.categoriaListAdapter;

public class PerfilProyectoFragment extends AppCompatActivity implements View.OnClickListener{

    private TextView nombre;
    private TextView precio;
    private TextView categoria;
    private TextView descripcion;
    private TextView text_fecha;
    private Button btn_postularse;
    String id;

    protected void onCreate(Bundle onSaveInstanceState){
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.fragment_perfilproyecto);

        nombre = (TextView) findViewById(R.id.text_nombre);
        precio = (TextView) findViewById(R.id.text_precio);
        categoria = (TextView) findViewById(R.id.text_categoria);
        descripcion = (TextView) findViewById(R.id.text_descripcion);
        text_fecha = (TextView) findViewById(R.id.text_fecha);
        btn_postularse = (Button) findViewById(R.id.btn_postularse);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        btn_postularse.setOnClickListener(this);


        Intent it = getIntent();
        if (it != null) {
            Bundle params = it.getExtras();
            if (params != null) {
                id = params.getString("id");
                ObtenerProyecto(id);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Quitamos barra de notificaciones
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_postularse:
                acceder();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    private void acceder() {
        /*String nombreV = nombre.getText().toString().trim();
        String descripcionV = descripcion.getText().toString().trim();
        boolean isValid = true;
        if (nombreV.isEmpty()) {
            nombre.setError("Debe ingresar un nombre");
            isValid = false;
        }
        if (descripcionV.isEmpty()) {
            descripcion.setError("Debe ingresar la descripcion");
            isValid = false;
        }
        if (!isValid) {
            return;
        }*/
        Postularse();
    }


    public void Postularse(){
        String url = "http://192.168.0.15:8080/Ingenieria_de_software_3/api/solicitud/insertar/";
        Usuario usuario = Usuario.getUsuario();
        JSONObject obj = new JSONObject();
        try {
            obj.put("projectId",id);
            obj.put("freelancerId",usuario.getId());
            obj.put("oferta",500);
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
                            String message = (String) response.get("message");
                            if (success) {
                                Toast.makeText(PerfilProyectoFragment.this, message, Toast.LENGTH_SHORT).show();
                                //JSONObject usuario = (JSONObject) response.get("response");
                                //Usuario.setUsuario(objUsuario);
                            } else {
                                Toast.makeText(PerfilProyectoFragment.this, message, Toast.LENGTH_SHORT).show();
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

    private void ObtenerProyecto(String id) {

        String url = "http://192.168.0.15:8080/Ingenieria_de_software_3/api/proyecto/"+id;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest= new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = (boolean) response.get("success");
                            if(success){
                                JSONObject proyecto = (JSONObject) response.get("response");
                                String nombreC = proyecto.getString("name").toString();
                                nombre.setText(nombreC);
                                String precioC = proyecto.getString("price").toString();
                                precio.setText(precioC);
                                if(proyecto.getString("category") != null){
                                    String categoriaC = proyecto.getString("category").toString();
                                    categoria.setText(categoriaC);
                                }
                                String descripcionC = proyecto.getString("description").toString();
                                descripcion.setText(descripcionC);
                                String fechaC = proyecto.getString("date").toString();
                                text_fecha.setText(fechaC);
                            }
                            else{
                                String message = (String) response.get("message");
                                Toast.makeText(PerfilProyectoFragment.this, message , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        /*objProyecto.setId(proyecto.getInt("id"));
                        objProyecto.setName(proyecto.getString("name"));
                        objProyecto.setDescription(proyecto.getString("description"));
                        // objProyecto.setCategory(proyecto.getString("category"));
                        objProyecto.setPrice(proyecto.getDouble("price"));
                        objProyecto.setDate(proyecto.getString("date"));
                        objProyecto.setOwnerdId(proyecto.getDouble("ownerId"));
                        objProyecto.setFreelancerId(proyecto.getInt("freelancerId"));*/
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



