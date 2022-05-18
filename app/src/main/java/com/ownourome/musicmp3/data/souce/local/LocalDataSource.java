package com.ownourome.musicmp3.data.souce.local;

import com.ownourome.musicmp3.data.models.Song;

import java.util.List;

public interface LocalDataSource {

    List<String> getSongFavorite();
    List<String> getSongDownload();
    List<Song> getAllSongDownload();
    void insertSong(Song song);
    void updateSong(Song song);
    void deleteSong(Song song);
}
