package com.oumenreame.viewpager.ui.main.listview.adapter;

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
import com.oumenreame.viewpager.data.model.Item;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private final int resource;
    private final ArrayList<Item> items;

    public ListViewAdapter(@NonNull Context context, int resource, ArrayList<Item> items) {
        super(context, resource, items);
        this.resource = resource;
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("LOI", "getView: " + position);
        @SuppressLint("ViewHolder") View item = LayoutInflater.from(this.context).inflate(this.resource, parent, false);

        TextView txtTitle = item.findViewById(R.id.txtTitle);
        TextView txtDetail = item.findViewById(R.id.txtDetail);

        Item model = items.get(position);
        txtTitle.setText(model.getTitle());
        txtDetail.setText(model.getDetail());

        return item;
    }
}
