package com.ownourome.musicmp3.data.remote;

import com.ownourome.musicmp3.data.models.Result;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRemote {
    @GET("/xhr/chart-realtime?songId=0&videoId=0&albumId=0&chart=song&time=-1")
    Call<Result> getSongVN();
}