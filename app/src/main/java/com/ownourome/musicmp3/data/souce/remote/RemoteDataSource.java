package com.ownourome.musicmp3.data.souce.remote;


import com.ownourome.musicmp3.utils.callback.RemoteCallback;

public interface RemoteDataSource {
    void getSongVN(RemoteCallback callback);
    void getSongUSUK(RemoteCallback callback);
}
