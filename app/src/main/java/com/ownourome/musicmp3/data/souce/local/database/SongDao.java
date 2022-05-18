package com.ownourome.musicmp3.data.souce.local.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ownourome.musicmp3.data.models.Song;

import java.util.List;

@Dao
public interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Song song);
    @Update
    void update(Song song);

    @Query(value = "SELECT id From song Where isFavorite = 1")
    List<String> getSongFavorite();

    @Query(value = "SELECT id From song Where isDownload = 1")
    List<String> getSongDownload();

    @Query("SELECT * FROM song Where isDownload = 1")
    List<Song> getAllDownload();

    @Delete
    void delete(Song song);
}
