package com.example.testing.simplemvpdemo.net;

import android.text.TextUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by H on 16/5/9.
 */
public enum CookieBuilder {
    INSTANCE;

    public String baseCookie;

    CookieBuilder(){
        StringBuilder baseBuilder = new StringBuilder();
//        addIntoCookie(baseBuilder, "appversion", DeviceUtil.getVersionCode() + "");
//        addIntoCookie(baseBuilder, "devicetype", "2");
//        addIntoCookie(baseBuilder, "devid", DeviceUtil.getImei());
//        addIntoCookie(baseBuilder, "sysversion", DeviceUtil.getSystemVersion());
//        addIntoCookie(baseBuilder, "PHPSESSID", UserUtils.getAccessToken());
//        addIntoCookie(baseBuilder, "apptoken", PreferenceUtils.getString(MyApplication.mContext
//        ,"apptoken",""));
        baseCookie = baseBuilder.toString();
    }

    public StringBuilder addCookieHeader(String cookie) {
        StringBuilder stringBuilder = new StringBuilder(baseCookie);
        if (TextUtils.isEmpty(cookie)) {
            return stringBuilder;
        }
        stringBuilder.append(";");
        stringBuilder.append(cookie);
        return stringBuilder;
    }

    public StringBuilder addCookie(Map<String, String> header){
        StringBuilder stringBuilder = new StringBuilder(baseCookie);
        if(header != null && header.size() > 0){
            Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                addIntoCookie(stringBuilder, entry.getKey(), entry.getValue());
            }
        }
        return stringBuilder;
    }

    private void addIntoCookie(StringBuilder builder, String key, String value){
        if(builder == null){
            return;
        }
        if(!TextUtils.isEmpty(builder.toString())){
            builder.append(";");
        }
        builder.append(key + "=" + value);
    }
}
