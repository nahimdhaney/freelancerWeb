package edson.com.freelancer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Edson on 02/12/2017.
 */

public class RegistroDialog extends DialogFragment implements View.OnClickListener {


    private EditText edit_email;
    private EditText edit_nombreCompleto;
    private EditText edit_username;
    private EditText edit_contraseña;

    private RadioButton radioButton_contrato;
    private RadioButton radioButton_trabajar;

    private Button btn_aceptar;
    private int valor;

    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }

    public static String APP_TAG = "registro";

    private static final String TAG = RegistroDialog.class.getSimpleName();

    public RegistroDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    public AlertDialog createLoginDialogo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogFragmanetstyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_signin, null);
        builder.setView(v);

        edit_email = (EditText) v.findViewById(R.id.Edit_email);
        edit_nombreCompleto= (EditText) v.findViewById(R.id.Edit_nombreCompleto);
        edit_username = (EditText) v.findViewById(R.id.Edit_Username);
        edit_contraseña = (EditText) v.findViewById(R.id.Edit_contraseña);
        radioButton_contrato = (RadioButton) v.findViewById(R.id.radioContratar);
        radioButton_trabajar = (RadioButton) v.findViewById(R.id.radioTrabajar);
        btn_aceptar = (Button) v.findViewById(R.id.btn_aceptar);

        btn_aceptar.setOnClickListener(this);
        Radio();
        return builder.create();
    }

    private void Radio() {
        radioButton_contrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton_contrato.isChecked() == true) {
                    //setGenero("hombre");
                    setValor(1);
                    radioButton_trabajar.setError(null);
                    radioButton_trabajar.setChecked(false);
                }
            }
        });
        radioButton_trabajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton_trabajar.isChecked() == true) {
                    //setGenero("mujer");
                    setValor(2);
                    radioButton_contrato.setError(null);
                    radioButton_contrato.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_aceptar:
                registrar();
                break;
        }
    }
    public static boolean validarEmailSimple(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void registrar() {

        String emailV = edit_email.getText().toString().trim();
        String nombreCompletoV = edit_nombreCompleto.getText().toString().trim();
        String usernameV = edit_username.getText().toString().trim();
        String contraseñaV = edit_contraseña.getText().toString().trim();

        boolean isValid = true;

        if (emailV.isEmpty()) {
            edit_email.setError("Debe ingresar su correo");
            isValid = false;
        } else if (validarEmailSimple(emailV) == false) {
            edit_email.setError("email no valido");
            isValid = false;
        }
        if (nombreCompletoV.isEmpty()) {
            edit_username.setError("Debe ingresar su nombre");
            isValid = false;
        }
        if (usernameV.isEmpty()) {
            edit_username.setError("Debe ingresar su usuario");
            isValid = false;
        }
        if (contraseñaV.isEmpty()) {
            edit_contraseña.setError("Debe ingresar su contraseña");
            isValid = false;
        } else if (isPasswordValid(contraseñaV) == false) {
            edit_contraseña.setError("la contraseña debe ser mayor a 4 digitos");
            isValid = false;
        }
        if (!isValid) {
            return;
        }
        RegistrarUsuario(emailV,usernameV,nombreCompletoV,contraseñaV,getValor());
    }

    public void RegistrarUsuario(String email, String user,  String nombreCompleto , String contra, int valor){

        String url = "http://192.168.0.15:8080/Ingenieria_de_software_3/api/usuario/register";
        JSONObject obj = new JSONObject();
        try {
            obj.put("email",email);
            obj.put("user",user);
            obj.put("fullName",nombreCompleto);
            obj.put("password",contra);
            obj.put("type",valor);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
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
                                //JSONObject usuario = (JSONObject) response.get("response");
                                String message = (String) response.get("message");
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                dismiss();
//                                Usuario objUsuario = new Usuario();
//                                objUsuario.setId(usuario.getInt("id"));
//                                objUsuario.setFullName(usuario.getString("fullName"));
//                                objUsuario.setUser(usuario.getString("user"));
//                                objUsuario.setEmail(usuario.getString("email"));
//                                objUsuario.setType(usuario.getInt("type"));
//                                Usuario.setUsuario(objUsuario);
                            } else {
                                String message = (String) response.get("message");
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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


