package com.ownourome.musicmp3.data.souce.local;

import android.content.Context;

import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.data.souce.local.database.SongDao;
import com.ownourome.musicmp3.data.souce.local.database.SongDatabase;

import java.util.List;

public class LocalDataSourceImp implements LocalDataSource{

    private static LocalDataSourceImp localDataSourceImp = null;

    private final SongDao songDao;

    public LocalDataSourceImp(SongDao songDao) {
        this.songDao = songDao;
    }

    public static LocalDataSourceImp getInstance(Context context) {
        if (localDataSourceImp == null) {
            SongDatabase songDatabase = SongDatabase.getInstance(context);
            localDataSourceImp = new LocalDataSourceImp(songDatabase.songDao());
        }
        return localDataSourceImp;
    }

    @Override
    public List<String> getSongFavorite() {
        return songDao.getSongFavorite();
    }

    @Override
    public List<String> getSongDownload() {
        return songDao.getSongDownload();
    }

    @Override
    public List<Song> getAllSongDownload() {
        return songDao.getAllDownload();
    }

    @Override
    public void insertSong(Song song) {
        songDao.insert(song);
    }

    @Override
    public void updateSong(Song song) {
        songDao.update(song);
    }

    @Override
    public void deleteSong(Song song) {
        songDao.delete(song);
    }
}
