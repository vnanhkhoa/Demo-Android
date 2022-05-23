package com.ownourome.musicmp3.ui.detail.inforsong;

import static com.ownourome.musicmp3.ui.detail.DetailActivity.mSong;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ownourome.musicmp3.R;

public class InforSongFragment extends Fragment {

    public TextView tvTitle;
    public TextView tvAlbum;
    public TextView tvArtist;
    public ImageView imgSong;

    public InforSongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infor_song, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    private void initViews(View view) {
        imgSong = view.findViewById(R.id.imgSong);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvAlbum = view.findViewById(R.id.tvAlbum);
        tvArtist = view.findViewById(R.id.tvArtist);
        Log.e("LOI", "initViews: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        setInForSong();
    }

    public void setInForSong() {
        Glide.with(requireContext())
                .load(mSong.getThumbnail())
                .error(R.drawable.icon)
                .into(imgSong);

        tvTitle.setText(mSong.getTitle());
        tvAlbum.setText(mSong.getTitleAlbum());
        tvArtist.setText(mSong.getArtistsNames());
    }
}