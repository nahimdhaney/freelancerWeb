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

public class RestablecerPassActivity extends AppCompatActivity implements View.OnClickListener  {


    private EditText edit_email;
    private Button btn_restablecer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecerpass);

        edit_email = (EditText) findViewById(R.id.Edit_emailConfimacion);
        btn_restablecer = (Button) findViewById(R.id.btn_restablcer);

        btn_restablecer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_restablcer){
            Intent intent = new Intent(this , CodigoVerificacionActivity.class);
            startActivity(intent);
        }
    }
}
