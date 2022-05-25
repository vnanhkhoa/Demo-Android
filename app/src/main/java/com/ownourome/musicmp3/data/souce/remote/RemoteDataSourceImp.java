package com.ownourome.musicmp3.data.souce.remote;

import androidx.annotation.NonNull;

import com.ownourome.musicmp3.data.network.NetworkConfig;
import com.ownourome.musicmp3.data.network.response.ResultResponse;
import com.ownourome.musicmp3.utils.callback.RemoteCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSourceImp implements RemoteDataSource {

    private static RemoteDataSourceImp INSTANCE = null;
    private final NetworkConfig mNetWorkConfig;

    public static RemoteDataSourceImp getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSourceImp();
        }
        return INSTANCE;
    }

    public RemoteDataSourceImp() {
        this.mNetWorkConfig = NetworkConfig.getInstance();
    }

    @Override
    public void getSongVN(RemoteCallback callback) {
        mNetWorkConfig.getApi().getSongVN().enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResultResponse> call, @NonNull Response<ResultResponse> response) {
                if (response.isSuccessful()) {
                    ResultResponse resultResponse = response.body();
                    if (resultResponse != null) {
                        callback.onSuccess(resultResponse);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultResponse> call, @NonNull Throwable t) {
                callback.onFailed(t.getMessage());
            }
        });
    }

    @Override
    public void getSongUSUK(RemoteCallback callback) {
        mNetWorkConfig.getApi().getSongUSUK().enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResultResponse> call, @NonNull Response<ResultResponse> response) {
                if (response.isSuccessful()) {
                    ResultResponse resultResponse = response.body();
                    if (resultResponse != null) {
                        callback.onSuccess(resultResponse);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultResponse> call, @NonNull Throwable t) {
                callback.onFailed(t.getMessage());
            }
        });
    }

}
