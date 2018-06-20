package edson.com.freelancer.adapter;

/**
 * Created by Edson on 24/05/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edson.com.freelancer.Model.Proyectos;
import edson.com.freelancer.R;


/**
 * Created by Edson on 15/09/2017.
 */

public class categoriaListAdapter extends BaseAdapter {

    private List<Proyectos> items;
    private Context context;

    public categoriaListAdapter(Context context, List<Proyectos> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layou_list_estado, viewGroup, false);
        }
        TextView tv_NombreList = (TextView)view.findViewById(R.id.tv_NombreList);
        Proyectos item = items.get(i);
        tv_NombreList.setText(item.getName());

        return view;
    }
}

