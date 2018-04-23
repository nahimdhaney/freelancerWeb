package edson.com.freelancer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CodigoVerificacionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_verificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigoverificacion);

        btn_verificar = (Button) findViewById(R.id.btn_verificar);

        btn_verificar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_verificar){
            Intent intent = new Intent(this , NuevaPassActivity.class);
            startActivity(intent);
        }
    }
}
