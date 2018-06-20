package edson.com.freelancer;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CuentaFragment extends android.support.v4.app.Fragment {

    private static final String TAG ="fragment_explorar";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_cuenta, container, false);


            return view;
        }

    }