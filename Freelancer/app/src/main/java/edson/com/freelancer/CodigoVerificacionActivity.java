package edson.com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CodigoVerificacionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_verificar;
    private EditText edit_codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigoverificacion);

        btn_verificar = (Button) findViewById(R.id.btn_verificar);
        edit_codigo = (EditText) findViewById(R.id.Edit_codigo);

        btn_verificar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_verificar){
            registrar();
            Intent intent = new Intent(this , NuevaPassActivity.class);
            startActivity(intent);
        }
    }

    private void registrar() {
        String edit_codigoV = this.edit_codigo.getText().toString().trim();
        boolean isValid = true;

        if (edit_codigoV.isEmpty()) {
            edit_codigo.setError("Debe ingresar el codigo de verificación");
            isValid = false;
        }
        if (!isValid) {
            return;
        }

        String email = edit_codigo.getText().toString();
        //RegistrarUsuario(username,contraseña,email,getValor());
    }




}
