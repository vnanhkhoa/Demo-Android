package com.ownourome.musicmp3.ui.detail.playsong;

import static com.ownourome.musicmp3.utils.Constant.TIME_DELAY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.ownourome.musicmp3.R;

public class PlaySongFragment extends Fragment{


    public ImageView mImgSong;
    private MaterialCardView mCardView;
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mCardView.animate().rotationBy(360).withEndAction(this).setDuration(TIME_DELAY)
                    .setInterpolator(new LinearInterpolator()).start();
        }
    };

    public PlaySongFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_song, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        mImgSong = view.findViewById(R.id.imgSong);
        mCardView = view.findViewById(R.id.cardView);

        mCardView.animate().rotationBy(360).withEndAction(runnable).setDuration(TIME_DELAY)
                .setInterpolator(new LinearInterpolator()).start();
    }

    public void setImgSong(String url) {
        Glide.with(requireContext())
                .load(url)
                .error(R.drawable.icon)
                .into(mImgSong);
    }

}