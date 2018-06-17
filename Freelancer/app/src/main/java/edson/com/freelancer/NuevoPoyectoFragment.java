package edson.com.freelancer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class NuevoPoyectoFragment extends AppCompatActivity {


    protected void onCreate(Bundle onSaveInstanceState){
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.fragment_nuevoproyecto);

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Quitamos barra de notificaciones
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//
//        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        int jmr = fragmentManager.beginTransaction().replace(R.id.container, this ).addToBackStack("jmr").commit();
}

