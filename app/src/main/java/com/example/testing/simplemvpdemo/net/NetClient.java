package com.example.testing.simplemvpdemo.net;

import retrofit2.Retrofit;

/**
 * Created by H on 16/8/8.
 */
public class NetClient {

    private final NetApi mNetApi;

    public NetClient() {
        Retrofit retrofit = RManager.INSTANCE.getRetrofit();
        mNetApi = retrofit.create(NetApi.class);
    }

    public NetApi getHotApi() {
        return mNetApi;
    }

}