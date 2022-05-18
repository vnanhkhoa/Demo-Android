package com.ownourome.musicmp3.data.network.response;

import com.google.gson.annotations.SerializedName;
import com.ownourome.musicmp3.data.models.Song;

import java.util.ArrayList;

public class DataResponse {

    @SerializedName("song")
    private ArrayList<Song> songs;

    public DataResponse() {
    }

    public DataResponse(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}
