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

public class NetworkConfig {

    private static NetworkConfig INSTANCE;
    private final AppApi api;

    private NetworkConfig() {
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
                .baseUrl(Constant.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(AppApi.class);
    }

    public static NetworkConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkConfig();
        }
        return INSTANCE;
    }

    public AppApi getApi() {
        return api;
    }
}
