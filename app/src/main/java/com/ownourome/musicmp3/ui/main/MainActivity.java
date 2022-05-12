package com.ownourome.musicmp3.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ownourome.musicmp3.R;
import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.data.remote.ApiRepositoryImp;
import com.ownourome.musicmp3.ui.main.adapter.SongAdapter;
import com.ownourome.musicmp3.utils.calback.SongItemClick;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ApiRepositoryImp mApiRepositoryImp;
    private SongItemClick mSongItemClick;
    private SongAdapter mSongAdapter;
    private ArrayList<Song> mSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiRepositoryImp = new ApiRepositoryImp(this);
        initViews();
        initListeners();
        initData();

    }

    private void initData() {
        mSongs = new ArrayList<>();
        mSongAdapter = new SongAdapter(
                this,
                mSongs,
                mSongItemClick
        );
        mRecyclerView.setAdapter(mSongAdapter);
        mApiRepositoryImp.getSongVN(mSongAdapter);
    }

    private void initListeners() {
        mSongItemClick = (position) -> {
            Song song = mSongAdapter.getSongs().get(position);
            Toast.makeText(this, song.getTitle(), Toast.LENGTH_SHORT).show();
        };
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("LOI", "onResume: "+mSongs.size());
    }
}