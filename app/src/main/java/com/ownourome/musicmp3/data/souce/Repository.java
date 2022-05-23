package com.ownourome.musicmp3.data.souce;

import android.content.Context;

import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.data.souce.local.LocalDataSource;
import com.ownourome.musicmp3.data.souce.local.LocalDataSourceImp;
import com.ownourome.musicmp3.data.souce.remote.RemoteDataSource;
import com.ownourome.musicmp3.data.souce.remote.RemoteDataSourceImp;
import com.ownourome.musicmp3.utils.callback.RemoteCallback;

import java.util.List;

public class Repository implements RemoteDataSource, LocalDataSource {
    private static Repository repository;
    private final RemoteDataSourceImp mRemoteDataSourceImp;
    private final LocalDataSourceImp mLocalDataSourceImp;

    public Repository(Context context) {
        this.mRemoteDataSourceImp = RemoteDataSourceImp.getInstance();
        this.mLocalDataSourceImp = LocalDataSourceImp.getInstance(context);
    }

    public static Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }

    @Override
    public void getSongVN(RemoteCallback callback) {
        this.mRemoteDataSourceImp.getSongVN(callback);
    }

    @Override
    public void getSongUSUK(RemoteCallback callback) {
        this.mRemoteDataSourceImp.getSongUSUK(callback);
    }

    @Override
    public List<String> getSongFavorite() {
        return this.mLocalDataSourceImp.getSongFavorite();
    }

    @Override
    public List<String> getSongDownload() {
        return this.mLocalDataSourceImp.getSongDownload();
    }

    @Override
    public List<Song> getAllSongDownload() {
        return this.mLocalDataSourceImp.getAllSongDownload();
    }

    @Override
    public List<Song> getAllSong() {
        return mLocalDataSourceImp.getAllSong();
    }

    @Override
    public void insertSong(Song song) {
        this.mLocalDataSourceImp.insertSong(song);
    }

    @Override
    public void updateSong(Song song) {
        this.mLocalDataSourceImp.updateSong(song);
    }

    @Override
    public void deleteSong(Song song) {
        this.mLocalDataSourceImp.deleteSong(song);
    }
}
