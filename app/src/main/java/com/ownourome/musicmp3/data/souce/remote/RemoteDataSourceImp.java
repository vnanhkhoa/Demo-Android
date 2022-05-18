package com.ownourome.musicmp3.data.souce.remote;

import androidx.annotation.NonNull;

import com.ownourome.musicmp3.data.network.response.Result;
import com.ownourome.musicmp3.data.network.ApiConfig;
import com.ownourome.musicmp3.utils.callback.RemoteCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSourceImp implements RemoteDataSource {

    private static RemoteDataSourceImp INSTANCE = null;
    private final ApiConfig mApiConfig;

    public static RemoteDataSourceImp getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSourceImp();
        }
        return INSTANCE;
    }

    public RemoteDataSourceImp() {
        this.mApiConfig = ApiConfig.getInstance();
    }

    @Override
    public void getSongVN(RemoteCallback callback) {
        mApiConfig.getApi().getSongVN().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    if (result != null) {
                        callback.onSuccess(result);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                callback.onFailed(t.getMessage());
            }
        });
    }
}
