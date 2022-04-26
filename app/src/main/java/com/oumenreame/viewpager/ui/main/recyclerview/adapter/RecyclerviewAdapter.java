package com.oumenreame.viewpager.ui.main.recyclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.utils.callback.ItemListenerCallback;
import com.oumenreame.viewpager.data.model.Item;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Item> items;
    private final ItemListenerCallback mCallback;

    public RecyclerviewAdapter(Context context, ArrayList<Item> items, ItemListenerCallback mCallback) {
        this.context = context;
        this.items = items;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("LOI", "onBindViewHolder: " + position);
        Item item = items.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtDetail.setText(item.getDetail());
        holder.itemView.setOnClickListener(view -> mCallback.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDetail = itemView.findViewById(R.id.txtDetail);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
