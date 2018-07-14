package edson.com.freelancer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edson.com.freelancer.Model.Usuario;

public class NuevoPoyectoFragment extends AppCompatActivity implements View.OnClickListener{

    private EditText nombre;
    private EditText presupuesto;
    private Spinner categoria;
    private EditText descripcion;
    private EditText edit_fecha;
    private Button btn_publicar;
    private int nivel;

    protected void onCreate(Bundle onSaveInstanceState){
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.fragment_nuevoproyecto);

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        nombre = (EditText) findViewById(R.id.edit_nombre);
        presupuesto = (EditText) findViewById(R.id.edit_presupuesto);
        categoria = (Spinner) findViewById(R.id.edit_spinner);
        descripcion = (EditText) findViewById(R.id.edit_descripcion);
        edit_fecha = (EditText) findViewById(R.id.edit_fecha);
        btn_publicar = (Button) findViewById(R.id.btn_publicar);

        AgregarListaNivelDeActividad();

        edit_fecha.setOnClickListener(this);
        btn_publicar.setOnClickListener(this);
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
            case R.id.edit_fecha:
                ShowDatapinckerDialog();
                break;
            case R.id.btn_publicar:

                break;
        }
    }


    private void ShowDatapinckerDialog(){
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int año = c.get(java.util.Calendar.YEAR);
        int mes = c.get(java.util.Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePickerDialog = new DatePickerDialog
                (this ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String fecha;
                        if(dayOfMonth<10){
                            fecha = "0"+ dayOfMonth;
                        }else{
                            fecha = "" + dayOfMonth;
                        }
                        if ((month+1)<10) {
                            fecha+= "/0" + (month+1) +"/" + year;
                        }else{
                            fecha+= "/" + (month+1) + "/" + year ;
                        }
                        edit_fecha.setText(fecha);
                        edit_fecha.setError(null);
                    }
                }, dia, mes , año);
        datePickerDialog.show();
    }


    private void AgregarListaNivelDeActividad(){
        // todo esto es para lista de actividades
        final List list = new ArrayList();
        list.add("paguina web y software");
        list.add("redaccion y contenido");
        list.add("Actividad Diseño, comunicacion");
        list.add("entrada de datos y comunicacion");
        list.add("Ventas Y marketing");
        list.add("negocio, contabilidad ,recursos");
        list.add("Agronomia");
        list.add("Aplicaciones moviles");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categoria.setAdapter(arrayAdapter);

        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivel = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // fin
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    private void acceder() {

        String nombreV = nombre.getText().toString().trim();
        String descripcionV = descripcion.getText().toString().trim();
        String presupuestoV = presupuesto.getText().toString().trim();
        String fechaV = edit_fecha.getText().toString().trim();
        String categoriaV = categoria.getAdapter().toString().trim();
        boolean isValid = true;
        if (nombreV.isEmpty()) {
            nombre.setError("Debe ingresar un nombre");
            isValid = false;
        }
        if (descripcionV.isEmpty()) {
            descripcion.setError("Debe ingresar la descripcion");
            isValid = false;
        }
        if (presupuestoV.isEmpty()) {
            presupuesto.setError("Debe ingresar un presupuesto");
            isValid = false;
        }
        if (fechaV.isEmpty()) {
            edit_fecha.setError("Debe ingresar la fecha");
            isValid = false;
        }
        if (!isValid) {
            return;
        }
        //Intent itent = new Intent(LoginActivity.this, menuActivity.class);
        //startActivity(itent);
        publicar(nombreV, descripcionV,fechaV,categoriaV,presupuestoV);
    }

    public void publicar(String nombre, String descripcion, String fecha, String categoria, String presupuesto ){
        String url = "http://172.20.10.3:8080/ingenieria_de_software_3/api/usuario/login";
        JSONObject obj = new JSONObject();
        try {
            obj.put("name",nombre);
            obj.put("categoria",categoria);
            obj.put("fecha",fecha);
            obj.put("descripcion",descripcion);
            obj.put("presupuesto",presupuesto);
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

                                Intent intent = new Intent(NuevoPoyectoFragment.this, Activity_List_Estado.class);
                                startActivity(intent);
                            } else {
                                String message = (String) response.get("message");
                                Toast.makeText(NuevoPoyectoFragment.this, message, Toast.LENGTH_SHORT).show();
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



