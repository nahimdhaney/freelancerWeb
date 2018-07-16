package edson.com.freelancer;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.List;

import edson.com.freelancer.Model.Proyectos;
import edson.com.freelancer.Model.Usuario;
import edson.com.freelancer.adapter.categoriaListAdapter;

public class ProyectosFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private static final String TAG ="fragment_proyectos";

    private ListView listView;
    private TextView edit_buscar;
    private Button btn_buscar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_list_estado, container, false);

        Usuario usuario = Usuario.getUsuario();

        listView = (ListView) view.findViewById(R.id.list_vista);
        edit_buscar = (EditText) view.findViewById(R.id.edit_buscar);
        btn_buscar = (Button) view.findViewById(R.id.btn_buscar);

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

        return view;
    }
    private void actualizarLista() {

        String url = "http://192.168.0.15:8080/Ingenieria_de_software_3/api/proyecto/";

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

                                categoriaListAdapter adaptador  = new categoriaListAdapter(getContext(), listaProyectos);
                                listView.setAdapter(adaptador);

                            }else{
                                String message = (String) response.get("message");
                                Toast.makeText(getContext(), message , Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        //actualizarLista();
    }


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

        String url = "http://192.168.0.15:8080/Ingenieria_de_software_3/api/proyecto/buscar/"+buscar;

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
                                    //objProyecto.setCategory(proyecto.getString("category"));
                                    objProyecto.setPrice(proyecto.getDouble("price"));
                                    objProyecto.setDate(proyecto.getString("date"));
                                    objProyecto.setOwnerdId(proyecto.getDouble("ownerId"));
                                    objProyecto.setFreelancerId(proyecto.getInt("freelancerId"));

                                    listaProyectos.add(objProyecto);

//                                    listView.setAdapter(new ArrayAdapter<>(Activity_List_Estado.this, android.R.layout.simple_list_item_1, ));
                                }

                                categoriaListAdapter adaptador  = new categoriaListAdapter(getContext(), listaProyectos);
                                listView.setAdapter(adaptador);

                            }else{
                                String message = (String) response.get("message");
                                Toast.makeText(getContext(), message , Toast.LENGTH_SHORT).show();
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
