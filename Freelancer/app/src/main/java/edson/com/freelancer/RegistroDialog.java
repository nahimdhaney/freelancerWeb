package edson.com.freelancer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edson.com.freelancer.httpclient.HttpConnection;
import edson.com.freelancer.httpclient.MethodType;
import edson.com.freelancer.httpclient.StandarRequestConfiguration;


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


    private void RegistrarUsuario( final String email, final String user, final String nombreCompleto , final String contra, final int valor) {

        //String token = FirebaseInstanceId.getInstance().getToken();

            AsyncTask<String, Integer, String> RegistrarUsuario = new AsyncTask<String, Integer, String>() {
                @Override
                protected String doInBackground(String... strings) {

                    String url = "http://192.168.43.77:8080/RedSocialWeb/ServletRegistro";

                    Hashtable<String, String> params = new Hashtable<>();

                    params.put("username", strings[1]);
                    params.put("contraseña", strings[2]);
                    params.put("email", strings[3]);
                    params.put("opcion", strings[4]);
                    //params.put("token", strings[4]);
                    params.put("accion", "registro");

                    String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(url, MethodType.POST, params));
                    return respuesta;
                }

                @Override
                protected void onPostExecute(String getnick) {

                    if (getnick.contains("invalido")) {
                        edit_username.setError("este nick ya esta registrado");
                        return;
                    } else if (getnick.contains("ok")) {
                        try {
                            dismiss();
                            Toast.makeText(getContext(), "exito", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                            //Log.e(RegistroActivity.APP_TAG, "Error al registrarse", e);
                        }
                    }
                }
            };

            String[] parametros = {user, contra, email, String.valueOf(valor)};
            RegistrarUsuario.execute(parametros);
        //}catch (Exception e){
          //  Toast.makeText(getContext(),"No hay conexión al servidor", Toast.LENGTH_LONG).show();
        //}
    }

        private AsyncTask<String, Integer, String> execuse(String[] p) {
            return execuse(p);
        }


    }

