package com.ownourome.musicmp3.data.network.response;

import com.google.gson.annotations.SerializedName;
import com.ownourome.musicmp3.data.models.Song;

import java.util.ArrayList;

public class Data {

    @SerializedName("song")
    private ArrayList<Song> songs;

    public Data() {
    }

    public Data(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}
