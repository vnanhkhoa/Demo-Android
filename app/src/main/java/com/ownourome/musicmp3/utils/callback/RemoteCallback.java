package com.ownourome.musicmp3.utils.callback;

import com.ownourome.musicmp3.data.network.response.ResultResponse;

public interface RemoteCallback {
    void onSuccess(ResultResponse resultResponse);
    void onFailed(String messenger);
}
