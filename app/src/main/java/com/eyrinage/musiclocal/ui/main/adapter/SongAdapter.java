package com.eyrinage.musiclocal.ui.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eyrinage.musiclocal.R;
import com.eyrinage.musiclocal.data.models.Song;
import com.eyrinage.musiclocal.utils.calback.ItemSongListener;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {

    private final ArrayList<Song> mSongs;
    private final Context mContext;
    private final ItemSongListener mItemSongListener;

    public SongAdapter(ArrayList<Song> mSongs, Context mContext, ItemSongListener mItemSongListener) {
        this.mSongs = mSongs;
        this.mContext = mContext;
        this.mItemSongListener = mItemSongListener;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_song, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.tvTitle.setText(song.getTitle());
        holder.tvArtist.setText(song.getArtist());

        holder.imgBtnPlay.setOnClickListener(
                view -> mItemSongListener.onImageButtonPlayClick(position)
        );
        holder.itemView.setOnClickListener(view -> mItemSongListener.onItemClick(position));

        if (song.getArtAlbum() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(song.getArtAlbum(), 0, song.getArtAlbum().length);
            holder.imgAlbum.setImageBitmap(bitmap);
        } else {
            holder.imgAlbum.setImageResource(R.drawable.ic_round_music_note_24);
        }

        holder.imgBtnFavorite.setOnClickListener(
                view -> mItemSongListener.onImageButtonFavoriteClick(holder.imgBtnFavorite, position)
        );

        if (song.isFavorite()) {
            holder.imgBtnFavorite.setImageResource(R.drawable.ic_round_favorite_24);
        } else {
            holder.imgBtnFavorite.setImageResource(R.drawable.ic_round_favorite_border_24);
        }
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    static class SongHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvArtist;
        ImageButton imgBtnPlay;
        ImageButton imgBtnFavorite;
        ImageView imgAlbum;

        public SongHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            imgBtnPlay = itemView.findViewById(R.id.imgBtnPlay);
            imgBtnFavorite = itemView.findViewById(R.id.imgBtnFavorite);
            imgAlbum = itemView.findViewById(R.id.imgAlbum);
        }
    }
}
