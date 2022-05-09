package com.eyrinage.musiclocal.data.local;

import com.eyrinage.musiclocal.data.models.Song;

import java.util.ArrayList;

public interface SongRepository {

    ArrayList<Song> getSongFromLocal();

}
