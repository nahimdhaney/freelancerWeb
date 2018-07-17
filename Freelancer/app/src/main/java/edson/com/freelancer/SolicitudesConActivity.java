package edson.com.freelancer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edson.com.freelancer.Model.Proyectos;
import edson.com.freelancer.Model.Solicitud;
import edson.com.freelancer.Model.Usuario;
import edson.com.freelancer.adapter.categoriaListAdapter;
import edson.com.freelancer.adapter.solicitudesListAdapter;

public class SolicitudesConActivity extends AppCompatActivity{

    private ListView listView;
    String id_proyecto;

    protected void onCreate(Bundle onSaveInstanceState){
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.activity_solicitud);

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        listView = (ListView) findViewById(R.id.list_vista_marca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent it = getIntent();
        if (it != null) {
            Bundle params = it.getExtras();
            if (params != null) {
                id_proyecto = params.getString("id");
                ObtenerSolicitud(id_proyecto);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                int solicitud_id = (int)id;
                createSimpleDialog(solicitud_id).show();
            }
        });
    }

    public AlertDialog createSimpleDialog(final int id_solicitud) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Solicitud")
                .setMessage("Confirmar el freelancer")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AceptarFrelancer(id_solicitud);
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

        return builder.create();
    }


    // Opcion para ir atras sin reiniciar el la actividad anterior de nuevo
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
    public void onBackPressed() {
        finish();
    }


    public void ObtenerSolicitud(String id){

        String url = "http://192.168.0.15:8080/Ingenieria_de_software_3/api/solicitud/solicitudes5/"+id;

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
                                JSONArray proyectos = (JSONArray) response.get("response");

                                List<Solicitud> listaProyectos = new ArrayList<>();
                                for (int i = 0; i < proyectos.length(); i++) {
                                    JSONObject proyecto = (JSONObject) proyectos.get(i);

                                    Solicitud obj = new Solicitud();
                                    obj.setId_solicitud(proyecto.getInt("id_solicitud"));
                                    obj.setId_freelancer(proyecto.getInt("id_freelancer"));
                                    obj.setFreelancer(proyecto.getString("freelancer"));
                                    obj.setOferta(proyecto.getInt("oferta"));
                                    listaProyectos.add(obj);
                                }
                                solicitudesListAdapter adaptador  = new solicitudesListAdapter(SolicitudesConActivity.this, listaProyectos);
                                listView.setAdapter(adaptador);

                            }else{
                                String message = (String) response.get("message");
                                Toast.makeText(SolicitudesConActivity.this, message , Toast.LENGTH_SHORT).show();
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


    public void AceptarFrelancer(final int id_solicitud){

        String url = "http://192.168.0.15:8080/Ingenieria_de_software_3/api/solicitud/aceptar5/";
        JSONObject obj = new JSONObject();
        try {
            obj.put("id",id_solicitud);
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
                                Toast.makeText(SolicitudesConActivity.this, message, Toast.LENGTH_SHORT).show();
                                Confirmar(id_solicitud);
                            } else {
                                Toast.makeText(SolicitudesConActivity.this, message, Toast.LENGTH_SHORT).show();
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


    private void Confirmar(int id_solicitud) {

        String url = "http://192.168.0.15:8080/Ingenieria_de_software_3/api/solicitud/confirmar5/";
        JSONObject obj = new JSONObject();
        try {
            obj.put("id",id_solicitud);
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
                                Toast.makeText(SolicitudesConActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SolicitudesConActivity.this, message, Toast.LENGTH_SHORT).show();
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

