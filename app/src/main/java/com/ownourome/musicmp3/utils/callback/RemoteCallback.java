package com.ownourome.musicmp3.utils.callback;

import com.ownourome.musicmp3.data.models.Result;

public interface RemoteCallback {
    void onSuccess(Result result);
    void onFailed(String messenger);
}
