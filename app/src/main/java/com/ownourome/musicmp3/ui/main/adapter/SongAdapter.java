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

import com.bumptech.glide.Glide;
import com.ownourome.musicmp3.R;
import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.utils.callback.SongItemClick;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {

    private final Context mContext;
    private ArrayList<Song> mSongs;
    private final SongItemClick mSongItemClick;
    private List<String> songFavorite;
    private List<String> songDownload;

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
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public ArrayList<Song> getSongs() {
        return mSongs;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter(ArrayList<Song> songs, List<String> songFavorite, List<String> songDownload) {
        this.songFavorite = songFavorite;
        this.songDownload = songDownload;
        this.mSongs = songs;
        notifyDataSetChanged();
    }

    public void setSongFavorite(List<String> songFavorite) {
        this.songFavorite = songFavorite;
    }

    public void setSongDownload(List<String> songDownload) {
        this.songDownload = songDownload;
    }

    class SongHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvArtist;
        ImageView imgSong;
        ImageButton imgBtnFavorite;
        ImageButton imgBtnDownload;
        ImageView imgIsDownloaded;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            imgSong = itemView.findViewById(R.id.imgSong);
            imgBtnFavorite = itemView.findViewById(R.id.imgBtnFavorite);
            imgBtnDownload = itemView.findViewById(R.id.imgBtnDownload);
            imgIsDownloaded = itemView.findViewById(R.id.imgIsDownloaded);
        }

        public void onBind(int position) {
            Song song = mSongs.get(position);
            tvTitle.setText(song.getTitle());
            tvArtist.setText(song.getArtistsNames());
            Glide.with(itemView)
                    .load(song.getThumbnail())
                    .error(R.drawable.ic_round_music_note_24)
                    .into(imgSong);

            imgBtnDownload.setOnClickListener((v) -> mSongItemClick.imgBtnDownloadClick(imgBtnDownload, position));
            imgBtnFavorite.setOnClickListener((v) -> {
                mSongItemClick.imgBtnFavoriteClick(song);
                setFavorite(song);
            });
            itemView.setOnClickListener((v -> mSongItemClick.itemClick(position)));
            setFavorite(song);
            setDownload(song);

        }

        private void setDownload(Song song) {
            if (songDownload.contains(song.getId())) {
                imgIsDownloaded.setVisibility(View.VISIBLE);
                imgBtnDownload.setVisibility(View.GONE);
            } else {
                imgIsDownloaded.setVisibility(View.GONE);
                imgBtnDownload.setVisibility(View.VISIBLE);
            }

            song.setDownload(songDownload.contains(song.getId()));
        }

        private void setFavorite(Song song) {
            if (songFavorite.contains(song.getId())) {
                song.setFavorite(true);
                imgBtnFavorite.setColorFilter(mContext.getResources().getColor(R.color.favorite));
            } else {
                song.setFavorite(false);
                imgBtnFavorite.setColorFilter(mContext.getResources().getColor(R.color.black));
            }
        }
    }
}
