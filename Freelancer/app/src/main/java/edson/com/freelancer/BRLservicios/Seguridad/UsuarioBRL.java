package edson.com.freelancer.BRLservicios.Seguridad;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.Hashtable;

import edson.com.freelancer.LoginActivity;
import edson.com.freelancer.BRLservicios.ConnectionBRL;
import edson.com.freelancer.Model.Usuario;
import edson.com.freelancer.httpclient.HttpConnection;
import edson.com.freelancer.httpclient.MethodType;
import edson.com.freelancer.httpclient.StandarRequestConfiguration;

/**
 * Created by Edson on 22/04/2018.
 */

public class UsuarioBRL {

    LoginActivity login = new LoginActivity();

        public static void obtener(final String user, final String contra) {
            //String token = FirebaseInstanceId.getInstance().getToken();
            AsyncTask<String, Integer, String> task = new AsyncTask<String, Integer, String>() {
                @Override
                protected String doInBackground(String... strings) {

                    ConnectionBRL br = new ConnectionBRL();
                    String url = br.cx();

                    Hashtable<String, String> params = new Hashtable<>();
                    params.put("username", strings[0]);
                    params.put("password", strings[1]);
                    params.put("accion", "obtenerUsuario");

                    String respuesta = HttpConnection.sendRequest(new StandarRequestConfiguration(url, MethodType.POST, params));
                    return respuesta;
                }

                @Override
                protected void onPostExecute(String getContenido) {

                    if (getContenido.contains("name")) {
                        //es por que esta mal el nick
                      //  tv_nickname.setError("el nickname es incorrecto");
                        return;
                    } else if (getContenido.contains("pass")) {
                        //es por que esta mal la contraseña
                     //   edit_contraseña.setError("la contraseña es incorrecto   ");
                        return;
                    } else if (getContenido.contains("incorrecto")) {
                        //es por que ambas estan mal
                     //   tv_nickname.setError("el nickname es incorrecto");
                     //   edit_contraseña.setError("la contraseña es incorrecto");
                        return;

                    } else if (getContenido.contains("ok")) {

                        Usuario usr = new Usuario();
                        usr.setUsername(user);

                    //    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //    editor.putString("edson", user);
                    //    editor.putString("cito", contra);
                    //    editor.commit();
                        //Intent itent = new Intent(this, menuActivity.class);
                        //startActivity(itent);
                    }
                }
            };

            String[] parametros = {user, contra};
            task.execute(parametros);
        }

    }

