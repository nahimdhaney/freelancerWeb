package edson.com.freelancer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VerproductoActivity extends AppCompatActivity {

    private TextView tv_nombre;
    private TextView tv_descripcion;
    private TextView tv_categoria;
    private TextView tv_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verproducto);

        tv_nombre = (TextView) findViewById(R.id.tv_nombre);
        tv_descripcion = (TextView) findViewById(R.id.tv_descripcion);
        tv_categoria = (TextView) findViewById(R.id.tv_categoria);
        tv_precio = (TextView) findViewById(R.id.tv_precio);

        Intent it = getIntent();
        if (it != null) {
            Bundle params = it.getExtras();
            if  (params != null) {
                String nombre = params.getString("nombre");
                String descripcion = params.getString("descripcion");
                String categoria = params.getString("categoria");
                double precio = params.getDouble("precio");
                tv_nombre.setText(nombre);
                tv_descripcion.setText(descripcion);
                tv_categoria.setText(categoria);
                tv_precio.setText(String.valueOf(precio));
            }
        }

        /*Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String descripcion = intent.getStringExtra("descripcion");
        String categoria = intent.getStringExtra("categoria");
        double precio = intent.getDoubleExtra("precio",0.0);*/
    }
}
