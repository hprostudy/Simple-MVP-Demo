package com.example.testing.simplemvpdemo.net;

/**
 * Created by H on 16/8/8.
 */
public class NetFactory {

    private static NetApi mNetApi = null;
    private static final Object WATCH = new Object();

    public NetFactory() {

    }

    public static NetApi getNetApi() {
        synchronized (WATCH) {
            if (mNetApi == null)
                mNetApi = new NetClient().getHotApi();
        }
        return mNetApi;
    }

}