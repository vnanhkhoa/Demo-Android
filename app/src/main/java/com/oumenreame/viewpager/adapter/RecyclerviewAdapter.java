package com.oumenreame.viewpager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.model.Model;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Model> models;

    public RecyclerviewAdapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = models.get(position);

        holder.txtTitle.setText(model.getTitle());
        holder.txtDetail.setText(model.getDetail());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDetail = itemView.findViewById(R.id.txtDetail);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
