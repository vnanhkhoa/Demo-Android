package com.eyrinage.musiclocal.data.local;

import static com.eyrinage.musiclocal.utils.Constant.ALBUM_SONG;
import static com.eyrinage.musiclocal.utils.Constant.ARTIST_SONG;
import static com.eyrinage.musiclocal.utils.Constant.PATH_SONG;
import static com.eyrinage.musiclocal.utils.Constant.TITLE;
import static com.eyrinage.musiclocal.utils.Constant._ID;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.eyrinage.musiclocal.data.models.Song;

import java.util.ArrayList;

public class SongRepositoryImp implements SongRepository {

    private final Context mContext;
    private final ArrayList<Song> songs;

    public SongRepositoryImp(Context context) {
        this.mContext = context;

        songs = new ArrayList<>();
    }

    @Override
    public ArrayList<Song> getSongFromLocal() {

        ContentResolver contentResolver = mContext.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sort = MediaStore.Audio.Media.TITLE + " ASC";

        @SuppressLint("Recycle") Cursor cursor =
                contentResolver.query(songUri, null, null, null, sort);

        while (cursor != null && cursor.moveToNext()) {
            @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(PATH_SONG));
            @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex(ARTIST_SONG));
            @SuppressLint("Range") String album = cursor.getString(cursor.getColumnIndex(ALBUM_SONG));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(TITLE));
            @SuppressLint("Range") long _id = cursor.getLong(cursor.getColumnIndex(_ID));

            Song song = new Song(_id,title, artist, path, album);
            songs.add(song);
        }

        cursor.close();
        return songs;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }


}
