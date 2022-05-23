package com.ownourome.musicmp3.data.souce.local.database;

import static com.ownourome.musicmp3.utils.Constant.DATABASE_NAME;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ownourome.musicmp3.data.models.Song;

@Database(entities = {Song.class}, version = 1, exportSchema = false)
public abstract class SongDatabase extends RoomDatabase {

    private static SongDatabase songDatabase = null;

    public static SongDatabase getInstance(Context context) {
        if (songDatabase == null) {
            songDatabase = Room.databaseBuilder(
                    context,
                    SongDatabase.class,
                    DATABASE_NAME
            ).allowMainThreadQueries().build();
        }
        return songDatabase;
    }

    public abstract SongDao songDao();
}
