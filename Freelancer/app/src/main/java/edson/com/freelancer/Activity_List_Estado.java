package edson.com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.LinkedList;
import java.util.List;

import edson.com.freelancer.Model.Proyectos;
import edson.com.freelancer.Model.Usuario;
import edson.com.freelancer.adapter.categoriaListAdapter;

import static android.R.attr.id;
import static edson.com.freelancer.LoginActivity.pass;

public class Activity_List_Estado extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    private TextView edit_buscar;
    private Button btn_buscar;
//    public static AppCompatActivity activityclear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_estado);

        Usuario usuario = Usuario.getUsuario();

//        activityclear = this;

        listView = (ListView) findViewById(R.id.list_vista);
        edit_buscar = (EditText) findViewById(R.id.edit_buscar);
        btn_buscar = (Button) findViewById(R.id.btn_buscar);

        btn_buscar.setOnClickListener(this);

        actualizarLista();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {

                //int proyectoid = (int)id;

                /*ArrayList<Proyectos> arrayList = new ArrayList<>();
                Proyectos pro = new Proyectos();
                Proyectos selectedProduct = arrayList.get(index);
                Intent intent = new Intent(getApplicationContext(), VerproductoActivity.class);

                Bundle params = new Bundle();
                params.putString("nombre", selectedProduct.getName());
                params.putString("descripcion", selectedProduct.getDescription());
                params.putString("categoria", selectedProduct.getCategory());
                params.putDouble("precio", selectedProduct.getPrice());
                intent.putExtras(params);
                startActivity(intent);*/

                /*intent.putExtra("nombre", selectedProduct.getName());
                intent.putExtra("descripcion", selectedProduct.getDescription());
                intent.putExtra("categoria", selectedProduct.getCategory());
                intent.putExtra("precio", selectedProduct.getPrice());
                startActivity(intent);*/
            }
        });
//        registerForContextMenu(listView);
    }

    private void actualizarLista() {

        String url = "http://192.168.43.32:8080/freelancerWeb/api/proyecto/";

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

                                List<Proyectos> listaProyectos = new ArrayList<>();

                                for (int i = 0; i < proyectos.length(); i++) {
                                    JSONObject proyecto = (JSONObject) proyectos.get(i);

                                    Proyectos objProyecto = new Proyectos();
                                    objProyecto.setId(proyecto.getInt("id"));
                                    objProyecto.setName(proyecto.getString("name"));
                                    objProyecto.setDescription(proyecto.getString("description"));
                                    objProyecto.setCategory(proyecto.getString("category"));
                                    objProyecto.setPrice(proyecto.getDouble("price"));
                                    objProyecto.setDate(proyecto.getString("date"));
                                    objProyecto.setOwnerd(proyecto.getDouble("ownerId"));
                                    objProyecto.setFreelancer(proyecto.getInt("freelancerId"));

                                    listaProyectos.add(objProyecto);
//                                    listView.setAdapter(new ArrayAdapter<>(Activity_List_Estado.this, android.R.layout.simple_list_item_1, ));
                                }

                                categoriaListAdapter adaptador  = new categoriaListAdapter(Activity_List_Estado.this, listaProyectos);
                                listView.setAdapter(adaptador);

                            }else{
                                String message = (String) response.get("message");
                                Toast.makeText(Activity_List_Estado.this, message , Toast.LENGTH_SHORT).show();
                            }
//                            JSONObject usuario = (JSONObject) response.get("response");
//
//                            Usuario objUsuario = new Usuario();
//                            objUsuario.setId(usuario.getInt("id"));
//                            objUsuario.setFullName(usuario.getString("fullName"));
//                            objUsuario.setUser(usuario.getString("user"));
//                            objUsuario.setEmail(usuario.getString("email"));
//                            objUsuario.setType(usuario.getInt("type"));

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
    protected void onResume() {
        super.onResume();
        actualizarLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buscar:
                acceder();
                break;
        }
    }

    private void acceder() {
        String buscar = edit_buscar.getText().toString().trim();
        boolean isValid = true;
        if (buscar.isEmpty()) {
            edit_buscar.setError("Debe ingresar una palabra");
            isValid = false;
        }
        if (!isValid) {
            return;
        }
        buscarProyecto(buscar);
    }


    private void buscarProyecto(String buscar) {

        String url = "http://192.168.43.32:8080/freelancerWeb/api/proyecto/buscar/"+buscar;

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
                                List<Proyectos> listaProyectos = new ArrayList<>();
                                for (int i = 0; i < proyectos.length(); i++) {
                                    JSONObject proyecto = (JSONObject) proyectos.get(i);
                                    Proyectos objProyecto = new Proyectos();
                                    objProyecto.setId(proyecto.getInt("id"));
                                    objProyecto.setName(proyecto.getString("name"));
                                    objProyecto.setDescription(proyecto.getString("description"));
                                    objProyecto.setCategory(proyecto.getString("category"));
                                    objProyecto.setPrice(proyecto.getDouble("price"));
                                    objProyecto.setDate(proyecto.getString("date"));
                                    objProyecto.setOwnerd(proyecto.getDouble("ownerId"));
                                    objProyecto.setFreelancer(proyecto.getInt("freelancerId"));

                                    listaProyectos.add(objProyecto);

//                                    listView.setAdapter(new ArrayAdapter<>(Activity_List_Estado.this, android.R.layout.simple_list_item_1, ));
                                }

                                categoriaListAdapter adaptador  = new categoriaListAdapter(Activity_List_Estado.this, listaProyectos);
                                listView.setAdapter(adaptador);

                            }else{
                                String message = (String) response.get("message");
                                Toast.makeText(Activity_List_Estado.this, message , Toast.LENGTH_SHORT).show();
                            }
//                            JSONObject usuario = (JSONObject) response.get("response");
//
//                            Usuario objUsuario = new Usuario();
//                            objUsuario.setId(usuario.getInt("id"));
//                            objUsuario.setFullName(usuario.getString("fullName"));
//                            objUsuario.setUser(usuario.getString("user"));
//                            objUsuario.setEmail(usuario.getString("email"));
//                            objUsuario.setType(usuario.getInt("type"));

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
