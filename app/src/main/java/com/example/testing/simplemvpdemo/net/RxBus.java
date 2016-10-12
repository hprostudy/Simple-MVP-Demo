package com.example.testing.simplemvpdemo.net;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by H on 16/7/14.
 */
public class RxBus {
    private static volatile RxBus instance;
    private final Subject<Object, Object> BUS;

    private RxBus() {
        BUS = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void post(Object event) {
        BUS.onNext(event);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        // ofType = filter + cast
        return BUS.ofType(eventType);
    }
}
