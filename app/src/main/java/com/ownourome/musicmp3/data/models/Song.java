package com.ownourome.musicmp3.data.models;

import static com.ownourome.musicmp3.utils.Constant.URL_SONG;

import com.google.gson.annotations.SerializedName;

public class Song {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("artists_names")
    private String artistsNames;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("duration")
    private int duration;

    private String durationString;
    private String linkSong;

    public Song() {
    }

    public Song(String id, String title, String artistsNames, String thumbnail, int duration) {
        this.id = id;
        this.title = title;
        this.artistsNames = artistsNames;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.linkSong = URL_SONG.replace(":idSong", this.id);
        setDurationString();
    }

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString() {
        String minus = ("0"+(this.duration / 60)).substring(1);
        String second = ("0"+(this.duration % 60)).substring(1);

        this.durationString = minus+":"+second;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return linkSong;
    }

    public void setLinkSong(String linkSong) {
        this.linkSong = linkSong;
    }
}
