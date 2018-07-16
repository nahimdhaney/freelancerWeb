package edson.com.freelancer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import java.util.List;

import edson.com.freelancer.Model.Proyectos;
import edson.com.freelancer.Model.Usuario;
import edson.com.freelancer.adapter.ProyectoFreeListAdapter;
import edson.com.freelancer.adapter.categoriaListAdapter;

public class MisProyectosFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    private static final int TIPO_CONTRATISTA = 1;
    private static final int TIPO_FREELANCER = 2;
    private ListView listView;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_mis_proyectos, container, false);

        /*Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);*/
        listView = (ListView) view.findViewById(R.id.list_misProyectos);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

         Usuario usuario = Usuario.getUsuario();
         switch (usuario.getType()) {
             case TIPO_CONTRATISTA:
                 actualizarListaContratista(usuario.getId()+"");
                 Toast.makeText(getActivity(), "Consumir proyectos_contratista", Toast.LENGTH_SHORT).show();
                 break;
             case TIPO_FREELANCER:
                 actualizarListaFreelancer(usuario.getId()+"");
                 Toast.makeText(getActivity(), "Consumir proyectos_freelancer5", Toast.LENGTH_SHORT).show();
                 break;
         }

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent(getActivity(), NuevoPoyectoFragment.class);
                startActivity(intent);
                break;
        }
        //Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }


    private void actualizarListaContratista( String tipo_contratista) {

        Usuario usuario = Usuario.getUsuario();

        String url = "http://192.168.0.15:8080/Ingenieria_de_software_3/api/proyecto/proyectos_contratista/"+tipo_contratista;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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

                                List<Proyectos> listaProyectos = new ArrayList<>();

                                for (int i = 0; i < proyectos.length(); i++) {
                                    JSONObject proyecto = (JSONObject) proyectos.get(i);

                                    Proyectos objProyecto = new Proyectos();
                                    objProyecto.setId(proyecto.getInt("id"));
                                    objProyecto.setName(proyecto.getString("name"));
                                    objProyecto.setDescription(proyecto.getString("description"));
                                    // objProyecto.setCategory(proyecto.getString("category"));
                                    objProyecto.setPrice(proyecto.getDouble("price"));
                                    objProyecto.setDate(proyecto.getString("date"));
                                    objProyecto.setOwnerdId(proyecto.getDouble("ownerId"));
                                    objProyecto.setFreelancerId(proyecto.getInt("freelancerId"));

                                    listaProyectos.add(objProyecto);
//                                    listView.setAdapter(new ArrayAdapter<>(Activity_List_Estado.this, android.R.layout.simple_list_item_1, ));
                                }

                                ProyectoFreeListAdapter adaptador  = new ProyectoFreeListAdapter(getContext(), listaProyectos);
                                listView.setAdapter(adaptador);

                            }else{
                                String message = (String) response.get("message");
                                Toast.makeText(getContext(), message , Toast.LENGTH_SHORT).show();
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

    //actualizar la lista
    @Override
    public void onResume() {
        super.onResume();
        Usuario usuario = Usuario.getUsuario();
        switch (usuario.getType()) {
            case TIPO_CONTRATISTA:
                actualizarListaContratista(usuario.getId()+"");
                Toast.makeText(getActivity(), "Consumir proyectos_contratista", Toast.LENGTH_SHORT).show();
                break;
            case TIPO_FREELANCER:
                actualizarListaFreelancer(usuario.getId()+"");
                Toast.makeText(getActivity(), "Consumir ", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    private void actualizarListaFreelancer( String tipo_contratista) {

        String url = "http://192.168.0.15:8080/Ingenieria_de_software_3/api/proyecto/proyectos_freelancer5/"+tipo_contratista;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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

                                List<Proyectos> listaProyectos = new ArrayList<>();

                                for (int i = 0; i < proyectos.length(); i++) {
                                    JSONObject proyecto = (JSONObject) proyectos.get(i);

                                    Proyectos objProyecto = new Proyectos();
                                    objProyecto.setId(proyecto.getInt("id"));
                                    objProyecto.setName(proyecto.getString("name"));
                                    objProyecto.setDescription(proyecto.getString("description"));
                                    // objProyecto.setCategory(proyecto.getString("category"));
                                    objProyecto.setPrice(proyecto.getDouble("price"));
                                    objProyecto.setDate(proyecto.getString("date"));
                                    objProyecto.setOwnerdId(proyecto.getDouble("ownerId"));
                                    objProyecto.setFreelancerId(proyecto.getInt("freelancerId"));

                                    listaProyectos.add(objProyecto);
//                                    listView.setAdapter(new ArrayAdapter<>(Activity_List_Estado.this, android.R.layout.simple_list_item_1, ));
                                }

                                ProyectoFreeListAdapter adaptador  = new ProyectoFreeListAdapter(getContext(), listaProyectos);
                                listView.setAdapter(adaptador);

                            }else{
                                String message = (String) response.get("message");
                                Toast.makeText(getContext(), message , Toast.LENGTH_SHORT).show();
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
