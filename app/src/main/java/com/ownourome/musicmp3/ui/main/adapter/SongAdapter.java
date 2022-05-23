package com.ownourome.musicmp3.ui.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.ownourome.musicmp3.R;
import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.ui.main.callback.SongItemClick;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {

    private final Context mContext;
    private final SongItemClick mSongItemClick;
    private ArrayList<Song> mSongs;
    private final CircularProgressDrawable circularProgressDrawable;

    public SongAdapter(Context mContext, ArrayList<Song> mSongs, SongItemClick mSongItemClick) {
        this.mContext = mContext;
        this.mSongs = mSongs;
        this.mSongItemClick = mSongItemClick;

        circularProgressDrawable = new CircularProgressDrawable(mContext);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_song, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter(ArrayList<Song> songs) {
        this.mSongs = songs;
        notifyDataSetChanged();
    }

    class SongHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvArtist;
        ImageView imgSong;
        ImageButton imgBtnFavorite;
        ImageView imgIsDownloaded;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            imgSong = itemView.findViewById(R.id.imgSong);
            imgBtnFavorite = itemView.findViewById(R.id.imgBtnFavorite);
            imgIsDownloaded = itemView.findViewById(R.id.imgIsDownloaded);
        }

        public void onBind(int position) {
            Song song = mSongs.get(position);
            tvTitle.setText(song.getTitle());
            tvArtist.setText(song.getArtistsNames());
            Glide.with(itemView)
                    .load(song.getThumbnail())
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_round_music_note_24)
                    .into(imgSong);

            imgBtnFavorite.setOnClickListener((v) -> {
                mSongItemClick.imgBtnFavoriteClick(song);
                setFavorite(song);
            });
            itemView.setOnClickListener((v -> mSongItemClick.itemClick(position)));
            setFavorite(song);
            setDownload(song);

        }

        private void setDownload(Song song) {
            if (song.isDownload()) {
                imgIsDownloaded.setVisibility(View.VISIBLE);
            } else {
                imgIsDownloaded.setVisibility(View.GONE);
            }
        }

        private void setFavorite(Song song) {
            if (song.isFavorite()) {
                imgBtnFavorite.setColorFilter(mContext.getResources().getColor(R.color.favorite));
            } else {
                imgBtnFavorite.setColorFilter(mContext.getResources().getColor(R.color.black));
            }
        }
    }
}
