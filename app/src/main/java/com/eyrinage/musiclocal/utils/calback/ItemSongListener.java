package com.eyrinage.musiclocal.utils.calback;

import android.widget.ImageButton;

public interface ItemSongListener {

    void onItemClick(int position);

    void onImageButtonPlayClick(int position);

    void onImageButtonFavoriteClick(ImageButton imageButton, int position);
}
