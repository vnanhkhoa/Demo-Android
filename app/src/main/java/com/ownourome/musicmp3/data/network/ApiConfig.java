package com.ownourome.musicmp3.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ownourome.musicmp3.utils.Constant;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {

    private static ApiConfig INSTANCE;
    private final ApiRemote api;

    private ApiConfig(String... baseUrl) {
        Gson gson = new GsonBuilder().create();

        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(logger);
        int TIME_OUT = 60;
        OkHttpClient client = httpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl[0])
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(ApiRemote.class);
    }

    public static ApiConfig getInstance(String... baseUrl) {
        if (INSTANCE == null) {
            if (baseUrl.length == 0) {
                INSTANCE = new ApiConfig(Constant.URL_BASE);
            } else {
                INSTANCE = new ApiConfig(baseUrl);
            }
        }
        return INSTANCE;
    }

    public ApiRemote getApi() {
        return api;
    }
}
