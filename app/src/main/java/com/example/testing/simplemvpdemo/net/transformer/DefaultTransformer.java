package com.example.testing.simplemvpdemo.net.transformer;


import com.example.testing.simplemvpdemo.net.NetBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by H on 16/8/5.
 * <p/>
 * 数据预处理
 */
public class DefaultTransformer<T> implements Observable.Transformer<NetBean<T>, T> {

    @Override
    public Observable<T> call(Observable<NetBean<T>> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ErrorCheckTransformer<T>());
    }
}