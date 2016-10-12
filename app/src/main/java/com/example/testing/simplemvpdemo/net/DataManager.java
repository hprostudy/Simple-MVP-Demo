package com.example.testing.simplemvpdemo.net;

import android.os.Handler;
import android.os.HandlerThread;

import com.google.gson.Gson;

/**
 * Created by H on 16/8/8.
 */
public class DataManager {

    private static Gson gson;
    private static RxBus rxBus;
    private static NetApi mNetApi;

    private Handler mHandler;
    private DataManager() {}
    public static DataManager getInstance() {
        return DataManagerHolder.INSTANCE;
    }


    public static class DataManagerHolder {
        private final  static DataManager INSTANCE = new DataManager();
    }

    public void initService() {
        gson = new Gson();
        rxBus = RxBus.getDefault();
        HandlerThread ioThread = new HandlerThread("IoThread");
        ioThread.start();
        mHandler = new Handler(ioThread.getLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mNetApi = NetFactory.getNetApi();
            }
        });
    }



}
