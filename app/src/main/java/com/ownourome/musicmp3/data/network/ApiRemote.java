package com.ownourome.musicmp3.data.network;

import com.ownourome.musicmp3.data.network.response.ResultResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRemote {
    @GET("/xhr/chart-realtime?songId=0&videoId=0&albumId=0&chart=song&time=-1")
    Call<ResultResponse> getSongVN();
}
