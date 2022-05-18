package com.ownourome.musicmp3.utils.callback;

import com.ownourome.musicmp3.data.network.response.Result;

public interface RemoteCallback {
    void onSuccess(Result result);
    void onFailed(String messenger);
}
