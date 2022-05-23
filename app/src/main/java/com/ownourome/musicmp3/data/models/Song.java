package com.ownourome.musicmp3.data.models;

import static com.ownourome.musicmp3.ui.main.MainActivity.sSongDownload;
import static com.ownourome.musicmp3.ui.main.MainActivity.sSongFavorite;
import static com.ownourome.musicmp3.utils.Constant.TIME_FORMAT;
import static com.ownourome.musicmp3.utils.Constant.URL_SONG;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.concurrent.TimeUnit;

@Entity(tableName = "song")
public class Song {

    @SerializedName("id")
    @PrimaryKey
    @NonNull
    private String id;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("artists_names")
    @ColumnInfo(name = "artists_names")
    private String artistsNames;

    @SerializedName("thumbnail")
    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "album")
    private String titleAlbum;

    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite;

    @ColumnInfo(name = "isDownload")
    private boolean isDownload;

    @Ignore
    public Song() {

    }

    public Song(@NonNull String id, String title, String artistsNames, String thumbnail, String location, String titleAlbum, boolean isFavorite, boolean isDownload) {
        this.id = id;
        this.title = title;
        this.artistsNames = artistsNames;
        this.thumbnail = thumbnail;
        this.location = location;
        this.titleAlbum = titleAlbum;
        this.isFavorite = isFavorite;
        this.isDownload = isDownload;
    }

    @Ignore
    public Song(@NonNull String id, String title, String artistsNames, String thumbnail, String location, boolean isFavorite, boolean isDownload) {
        this.id = id;
        this.title = title;
        this.artistsNames = artistsNames;
        this.thumbnail = thumbnail;
        this.location = location;
        this.isFavorite = isFavorite;
        this.isDownload = isDownload;
    }

    @SuppressLint("DefaultLocale")
    public String getDurationString(int millisecond) {
        return String.format(TIME_FORMAT,
                TimeUnit.MILLISECONDS.toMinutes(millisecond),
                TimeUnit.MILLISECONDS.toSeconds(millisecond) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecond))
        );
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistsNames() {
        return artistsNames;
    }

    public void setArtistsNames(String artistsNames) {
        this.artistsNames = artistsNames;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLinkSong() {
        if (!isDownload) {
            return URL_SONG.replace(":idSong", this.id);
        } else {
            if (getLocation() == null) {
                return URL_SONG.replace(":idSong", this.id);
            }
            return getLocation();
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isFavorite() {
        this.isFavorite = sSongFavorite.contains(id);
        return this.isFavorite;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public boolean isDownload() {
        this.isDownload = sSongDownload.contains(this.id);
        return this.isDownload;
    }

    public String getTitleAlbum() {
        if (titleAlbum == null) {
            setTitleAlbum(this.title + " (Single)");
        }
        return this.titleAlbum;
    }

    public void setTitleAlbum(String titleAlbum) {
        this.titleAlbum = titleAlbum;
    }
}
