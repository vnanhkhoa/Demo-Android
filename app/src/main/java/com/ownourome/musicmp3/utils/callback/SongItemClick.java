package com.ownourome.musicmp3.utils.callback;

import android.widget.ImageButton;

import com.ownourome.musicmp3.data.models.Song;

public interface SongItemClick {
    void itemClick(int position);
    void imgBtnFavoriteClick(Song song);
    void imgBtnDownloadClick(ImageButton imgBtnDownload,int position);
}
