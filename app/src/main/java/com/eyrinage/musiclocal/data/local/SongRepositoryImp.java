package com.eyrinage.musiclocal.data.local;

import static android.media.MediaMetadataRetriever.METADATA_KEY_ALBUM;
import static android.media.MediaMetadataRetriever.METADATA_KEY_ARTIST;
import static android.media.MediaMetadataRetriever.METADATA_KEY_TITLE;
import static com.eyrinage.musiclocal.utils.Constant.ALBUM_SONG;
import static com.eyrinage.musiclocal.utils.Constant.ARTIST_SONG;
import static com.eyrinage.musiclocal.utils.Constant.PATH_SONG;
import static com.eyrinage.musiclocal.utils.Constant.TITLE;
import static com.eyrinage.musiclocal.utils.Constant._ID;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

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

    public void getSongFolderAsset() {
        AssetManager assetManager = mContext.getAssets();
        try {
            String[] files = assetManager.list("music");
            for (int i = 0; i <= files.length; i++) {
                Song song = getInfoSong("music/"+files[i]);
                song.set_id(i);
                songs.add(song);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Song getInfoSong(String fileName) {
        Song song = new Song();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        try {
            AssetFileDescriptor afd = mContext.getAssets().openFd(fileName);
            mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

            song.setTitle(mmr.extractMetadata(METADATA_KEY_TITLE));
            song.setAlbum(mmr.extractMetadata(METADATA_KEY_ALBUM));
            song.setArtAlbum(mmr.getEmbeddedPicture());
            song.setLocation(fileName);
            song.setArtist(mmr.extractMetadata(METADATA_KEY_ARTIST));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return song;
    }
}
