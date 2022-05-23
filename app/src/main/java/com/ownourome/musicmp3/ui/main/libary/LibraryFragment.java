package com.ownourome.musicmp3.ui.main.libary;

import static com.ownourome.musicmp3.ui.main.MainActivity.sSongDownload;
import static com.ownourome.musicmp3.ui.main.MainActivity.sSongFavorite;
import static com.ownourome.musicmp3.ui.main.MainActivity.setSongPlaying;
import static com.ownourome.musicmp3.utils.Constant.POSITION;
import static com.ownourome.musicmp3.utils.Constant.SONG_DATA_EXTRA;
import static com.ownourome.musicmp3.utils.Constant.SONG_ID;
import static com.ownourome.musicmp3.utils.Constant.SONG_LIST;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ownourome.musicmp3.R;
import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.data.souce.Repository;
import com.ownourome.musicmp3.service.PlaySongService;
import com.ownourome.musicmp3.ui.main.adapter.SongAdapter;
import com.ownourome.musicmp3.ui.main.callback.SongItemClick;

import java.util.ArrayList;

public class LibraryFragment extends Fragment {

    private ArrayList<Song> mSongs;
    private SongItemClick mSongItemClick;
    private Repository mRepository;
    private SongAdapter mSongAdapter;
    private Gson mGson;

    public LibraryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSongs = new ArrayList<>();
        mGson = new Gson();

        mSongItemClick = new SongItemClick() {
            @Override
            public void itemClick(int position) {
                String json = mGson.toJson(mSongs);
                Bundle bundle = new Bundle();
                bundle.putInt(POSITION,position);
                bundle.putString(SONG_LIST,json);
                bundle.putString(SONG_ID,mSongs.get(position).getId());
                Intent intent = new Intent(requireContext(), PlaySongService.class);
                intent.putExtra(SONG_DATA_EXTRA,bundle);
                requireActivity().startService(intent);

                setSongPlaying(mSongs.get(position));
            }

            @Override
            public void imgBtnFavoriteClick(Song song) {
                handleFavorite(song);
            }
        };

        mRepository = Repository.getInstance(requireContext());
    }

    private void handleFavorite(Song song) {
        if (song.isFavorite()) {
            song.setFavorite(false);
            sSongFavorite.remove(song.getId());
            if (sSongDownload.contains(song.getId())) {
                mRepository.updateSong(song);
            } else {
                mRepository.deleteSong(song);
            }
        } else {
            song.setFavorite(true);
            sSongFavorite.add(song.getId());
            if (sSongDownload.contains(song.getId())) {
                mRepository.updateSong(song);
            } else {
                mRepository.insertSong(song);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        RecyclerView mRecyclerViewLocal = view.findViewById(R.id.recyclerViewLocal);

        mRecyclerViewLocal.setLayoutManager(new LinearLayoutManager(requireContext()));
        mSongAdapter = new SongAdapter(requireContext(), mSongs, mSongItemClick);
        mRecyclerViewLocal.setAdapter(mSongAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        mSongs = (ArrayList<Song>) mRepository.getAllSongDownload();
        mSongAdapter.updateAdapter(mSongs);

    }
}