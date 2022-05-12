package com.ownourome.musicmp3.data.remote;

import static com.ownourome.musicmp3.data.remote.ApiConfig.getRetrofit;
import static com.ownourome.musicmp3.utils.Constant.*;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ownourome.musicmp3.data.models.Result;
import com.ownourome.musicmp3.ui.main.adapter.SongAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepositoryImp implements ApiRepository {

    private final ApiRemote mApiRemote;
    private final Context mContext;

    public ApiRepositoryImp(Context context) {
        this.mContext = context;
        mApiRemote = getRetrofit(URL_BASE).create(ApiRemote.class);
    }

    @Override
    public void getSongVN(SongAdapter songAdapter) {
        mApiRemote.getSongVN().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    assert result != null;
                    if (result.getMsg().equals(VALUE_SUCCESS)) {
                        songAdapter.updateAdapter(result.getData().getSongs());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
