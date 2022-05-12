package com.ownourome.musicmp3.ui.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ownourome.musicmp3.R;
import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.utils.calback.SongItemClick;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongHolder>{

    private final Context mContext;
    private ArrayList<Song> mSongs;
    private final SongItemClick mSongItemClick;

    public SongAdapter(Context mContext, ArrayList<Song> mSongs, SongItemClick mSongItemClick) {
        this.mContext = mContext;
        this.mSongs = mSongs;
        this.mSongItemClick = mSongItemClick;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_song,parent,false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.onBind(song);
        holder.itemView.setOnClickListener((v -> mSongItemClick.itemClick(position)));
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public ArrayList<Song> getSongs() {
        return mSongs;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter(ArrayList<Song> songs){
        this.mSongs = songs;
        notifyDataSetChanged();
    }
}

class SongHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    TextView tvArtist;
    ImageView imgSong;

    public SongHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvArtist = itemView.findViewById(R.id.tvArtist);
        imgSong = itemView.findViewById(R.id.imgSong);
    }

    public void onBind(Song song) {
        tvTitle.setText(song.getTitle());
        tvArtist.setText(song.getArtistsNames());
        Glide.with(itemView)
                .load(song.getThumbnail())
                .error(R.drawable.ic_round_music_note_24)
                .into(imgSong);
    }
}
