package com.oumenreame.viewpager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.model.Model;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Model> {
    private final Context context;
    private final int resource;
    private final ArrayList<Model> models;

    public ListViewAdapter(@NonNull Context context, int resource, ArrayList<Model> models) {
        super(context, resource, models);
        this.resource = resource;
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("LOI", "getView: "+position);
        @SuppressLint("ViewHolder") View item = LayoutInflater.from(this.context).inflate(this.resource,parent,false);

        TextView txtTitle = item.findViewById(R.id.txtTitle);
        TextView txtDetail = item.findViewById(R.id.txtDetail);

        Model model = models.get(position);
        txtTitle.setText(model.getTitle());
        txtDetail.setText(model.getDetail());


        return item;
    }
}
