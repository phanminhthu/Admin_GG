package com.danazone.gomgom.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by FPTSHOP on 21/08/2016.
 */
public class ListAdapter2 extends ArrayAdapter<thuocTinh> {

    public ListAdapter2(Context context, int resource, List<thuocTinh> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.dong_list_view, null);
        }
        thuocTinh p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txt = (TextView) view.findViewById(R.id.ten);
            txt.setText(p.SDT);
            TextView tx2t = (TextView) view.findViewById(R.id.noiDung);
            tx2t.setText(p.NoiDung);
        }
        return view;
    }

}