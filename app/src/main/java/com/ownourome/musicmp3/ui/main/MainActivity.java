package com.ownourome.musicmp3.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ownourome.musicmp3.R;
import com.ownourome.musicmp3.data.models.Result;
import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.data.remote.ApiRepositoryImp;
import com.ownourome.musicmp3.ui.main.adapter.SongAdapter;
import com.ownourome.musicmp3.utils.Constant;
import com.ownourome.musicmp3.utils.callback.RemoteCallback;
import com.ownourome.musicmp3.utils.callback.SongItemClick;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ApiRepositoryImp mApiRepositoryImp;
    private SongItemClick mSongItemClick;
    private SongAdapter mSongAdapter;
    private ArrayList<Song> mSongs;

    private final RemoteCallback getSongVNCallBack = new RemoteCallback() {
        @Override
        public void onSuccess(Result result) {
            if (result.getMsg().equals(Constant.VALUE_SUCCESS)) {
                mSongs = result.getData().getSongs();
                mSongAdapter.updateAdapter(mSongs);
            } else {
                Toast.makeText(MainActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailed(String messenger) {
            Toast.makeText(MainActivity.this, messenger, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListeners();
        initData();

    }

    private void initData() {
        mSongs = new ArrayList<>();
        mApiRepositoryImp = new ApiRepositoryImp();
        mApiRepositoryImp.getSongVN(getSongVNCallBack);

        mSongAdapter = new SongAdapter(
                MainActivity.this,
                mSongs,
                mSongItemClick
        );
        mRecyclerView.setAdapter(mSongAdapter);
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initListeners() {
        mSongItemClick = (position) -> {
            Song song = mSongAdapter.getSongs().get(position);
            Log.e("LOI", "initListeners: " + song.getLinkSong());
            Toast.makeText(this, song.getTitle(), Toast.LENGTH_SHORT).show();
        };
    }
}