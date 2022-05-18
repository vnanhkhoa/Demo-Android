package com.ownourome.musicmp3.data.models;

import static com.ownourome.musicmp3.utils.Constant.URL_SONG;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

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

    @SerializedName("duration")
    @ColumnInfo(name = "duration")
    private int duration;

    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite = false;

    @ColumnInfo(name = "isDownload")
    private boolean isDownload = false;

    public Song() {

    }

    public Song(String id, String title, String artistsNames, String thumbnail, int duration) {
        this.id = id;
        this.title = title;
        this.artistsNames = artistsNames;
        this.thumbnail = thumbnail;
        this.duration = duration;
    }

    public Song(@NonNull String id, String title, String artistsNames, String thumbnail, String location, int duration, boolean isFavorite, boolean isDownload) {
        this.id = id;
        this.title = title;
        this.artistsNames = artistsNames;
        this.thumbnail = thumbnail;
        this.location = location;
        this.duration = duration;
        this.isFavorite = isFavorite;
        this.isDownload = isDownload;
    }

    public String getDurationString() {
        String minus = ("0"+(this.duration / 60)).substring(1);
        String second = ("0"+(this.duration % 60)).substring(1);
        return minus+":"+second;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLinkSong() {
        if (!isDownload) {
            return URL_SONG.replace(":idSong", this.id);
        } else {
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
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }
}
