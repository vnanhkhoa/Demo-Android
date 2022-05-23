package com.ownourome.musicmp3.data.network;

import com.ownourome.musicmp3.data.network.response.ResultResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppApi {

    @GET("/xhr/chart-realtime?songId=0&videoId=0&albumId=0&chart=song&time=-1")
    Call<ResultResponse> getSongVN();

    @GET("xhr/media/get-list?op=top100&start=0&length=20&id=ZWZB96AB")
    Call<ResultResponse> getSongUSUK();
}
