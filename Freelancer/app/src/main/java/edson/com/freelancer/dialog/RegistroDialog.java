package edson.com.freelancer.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Hashtable;

import edson.com.freelancer.R;
import edson.com.freelancer.httpclient.HttpConnection;
import edson.com.freelancer.httpclient.MethodType;
import edson.com.freelancer.httpclient.StandarRequestConfiguration;

/**
 * Created by Edson on 02/12/2017.
 */

public class RegistroDialog extends DialogFragment implements View.OnClickListener{


    private EditText edit_username;
    private EditText edit_email;
    private EditText edit_contraseña;
    private Button btn_aceptar;

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

        edit_username = (EditText) v.findViewById(R.id.Edit_Username);
        edit_email = (EditText) v.findViewById(R.id.Edit_email);
        edit_contraseña = (EditText) v.findViewById(R.id.Edit_contraseña);
        btn_aceptar = (Button) v.findViewById(R.id.btn_aceptar);

        btn_aceptar.setOnClickListener(this);

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_aceptar:
                registrar();
                break;
        }
    }


    boolean isValid ;
    private void validarCampo(){
        String nicknameV = edit_username.getText().toString().trim();
        String contraseñaV = edit_contraseña.getText().toString().trim();
        isValid = true;
        if (nicknameV.isEmpty()) {
            edit_username.setError("Debe ingresar");
            isValid = false;
        }
        if (contraseñaV.isEmpty()) {
            edit_contraseña.setError("Debe ingresar");
            isValid = false;
        }
    }

    private void registrar() {

        this.validarCampo();
        if (!isValid) {
            return;
        }


        String username = edit_username.getText().toString();
        String email = edit_email.getText().toString();
        String contraseña = edit_contraseña.getText().toString();


        Toast.makeText(getContext(), contraseña, Toast.LENGTH_LONG).show();

        /*String token = FirebaseInstanceId.getInstance().getToken();
        AsyncTask<String, Integer, String> RegistrarUsuario = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {

                //casa de elmer
                //String url = "http://192.168.1.4:8080/RedSocialWeb/ServletRegistro";

                //micelu
                String url = "http://192.168.43.77:8080/RedSocialWeb/ServletRegistro";

                Hashtable<String, String> params = new Hashtable<>();
                params.put("NombreCompleto", strings[0]);
                params.put("username", strings[1]);
                params.put("password", strings[2]);
                params.put("fechaDeNacimiento", strings[3]);
                params.put("token", strings[4]);
                params.put("accion","registro");

                String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(url, MethodType.POST, params));
                return respuesta;
            }


            @Override
            protected void onPostExecute(String getnick) {
                if(getnick.contains("invalido")){
                    edit_username.setError("este nick ya esta registrado");
                    return;

                }else if(getnick.contains("ok")){
                    try {

                        dismiss();
                        Toast.makeText(getContext(), "exito" ,Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        //Log.e(RegistroActivity.APP_TAG, "Error al registrarse", e);
                    }

                }
            }
        };

        String[] parametros = { NombreCompleto , username , contraseña , fechaDeNacimiento, token };
        RegistrarUsuario.execute(parametros);*/
    }


}
