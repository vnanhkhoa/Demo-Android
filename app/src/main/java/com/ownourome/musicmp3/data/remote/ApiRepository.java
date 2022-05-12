package com.ownourome.musicmp3.data.remote;

import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.ui.main.adapter.SongAdapter;

import java.util.ArrayList;

public interface ApiRepository {
    void getSongVN(SongAdapter songAdapter);
}
