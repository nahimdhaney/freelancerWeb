package edson.com.freelancer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;

import edson.com.freelancer.Model.Usuario;

public class CuentaFragment extends android.support.v4.app.Fragment implements OnClickListener{

    private static final String TAG ="fragment_explorar";

    private Button button;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_cuenta, container, false);

            button = (Button) view.findViewById(R.id.btn_cerrar);
            button.setOnClickListener(this);

            return view;
        }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cerrar:
                Cerrar();
                break;
        }
    }

    private void Cerrar() {
        Usuario.setUsuario(null);
        guardarUsuarioEnPreferencias();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void guardarUsuarioEnPreferencias() {
        SharedPreferences preferencias = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("usuario", null);
        editor.commit();
    }
}