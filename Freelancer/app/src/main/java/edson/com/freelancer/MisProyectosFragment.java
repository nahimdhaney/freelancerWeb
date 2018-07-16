package edson.com.freelancer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edson.com.freelancer.Model.Usuario;

public class MisProyectosFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    private static final int TIPO_CONTRATISTA = 1;
    private static final int TIPO_FREELANCER = 2;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_mis_proyectos, container, false);

        /*Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);*/

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

         Usuario usuario = Usuario.getUsuario();
         switch (usuario.getType()) {
             case TIPO_CONTRATISTA:
                 Toast.makeText(getActivity(), "Consumir proyectos_contratista", Toast.LENGTH_SHORT).show();
                 break;
             case TIPO_FREELANCER:
                 Toast.makeText(getActivity(), "Consumir proyectos_freelancer5", Toast.LENGTH_SHORT).show();
                 break;
         }

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent(getActivity(), NuevoPoyectoFragment.class);
                startActivity(intent);
                break;
        }
        //Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }




}
