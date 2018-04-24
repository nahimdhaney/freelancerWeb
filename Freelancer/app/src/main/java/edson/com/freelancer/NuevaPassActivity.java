package edson.com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NuevaPassActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_guardar;
    private EditText edit_nuevapass;
    private EditText edit_nuevapass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevapass);

        btn_guardar = (Button) findViewById(R.id.btn_guardar);
        edit_nuevapass = (EditText) findViewById(R.id.Edit_Nuevacontraseña);
        edit_nuevapass2 = (EditText) findViewById(R.id.Edit_Nuevacontraseña2);

        btn_guardar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_guardar){
            registrar();
        }
    }

    private void registrar() {
        String nuevaPass = this.edit_nuevapass.getText().toString().trim();
        String nuevaPass2 = this.edit_nuevapass2.getText().toString().trim();
        boolean isValid = true;

        if (nuevaPass.isEmpty()) {
            edit_nuevapass.setError("Debe ingresar su nueva contraseña");
            isValid = false;
        }
        if (nuevaPass2.isEmpty()) {
            edit_nuevapass2.setError("Debe volver a ingresar su nueva contraseña");
            isValid = false;
        }
        if(!nuevaPass.isEmpty() && !nuevaPass2.isEmpty()){
            if(!nuevaPass.contains(nuevaPass2)) {
                edit_nuevapass2.setError("Asegure que la contraseña sea la misma");
                isValid = false;
            }
        }
        if (!isValid) {
            return;
        }
        //String email = edit_nuevapass.getText().toString();
        //RegistrarUsuario(username,contraseña,email,getValor());
    }
}
