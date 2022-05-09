package com.eyrinage.musiclocal.data.models;

import static android.media.MediaMetadataRetriever.METADATA_KEY_ALBUM;
import static android.media.MediaMetadataRetriever.METADATA_KEY_ARTIST;
import static android.media.MediaMetadataRetriever.METADATA_KEY_TITLE;
import static com.eyrinage.musiclocal.ui.main.MainActivity.sHashMap;

import android.media.MediaMetadataRetriever;

import java.io.Serializable;

public class Song implements Serializable {

    private long _id;
    private String title;
    private String artist;
    private String location;
    private String album;


    private boolean isFavorite;
    private byte[] artAlbum;

    public Song() {

    }

    public Song(long _id, String title, String artist, String location, String album) {
        this._id = _id;
        this.title = title;
        this.artist = artist;
        this.location = location;
        this.album = album;
        this.artAlbum = getImageAlbum();
        setFavorite(false);

    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        if (sHashMap !=  null) {
            isFavorite = sHashMap.containsKey(_id);
        } else {
            isFavorite = favorite;
        }
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public byte[] getArtAlbum() {
        return this.artAlbum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setArtAlbum(byte[] artAlbum) {
        this.artAlbum = artAlbum;
    }

    private byte[] getImageAlbum() {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            mmr.setDataSource(this.location);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        byte[] bytes = mmr.getEmbeddedPicture();
        mmr.release();
        return bytes;
    }

}
