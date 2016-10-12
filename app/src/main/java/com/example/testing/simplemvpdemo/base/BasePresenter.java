package com.example.testing.simplemvpdemo.base;

import java.lang.ref.WeakReference;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by H on 16/8/8.
 * <p/>
 * P层基类
 */
public abstract class BasePresenter<V extends IBaseView, M extends IBaseModel> {

    protected WeakReference<V> mViewRef;
    protected M mBaseModel;
    protected CompositeSubscription mCompositeSubscription;

    public BasePresenter() {
        mBaseModel = createModel();
    }

    public abstract M createModel();

    protected V getView() {
        return mViewRef.get();
    }

    public void attachView(IBaseView view) {
        mViewRef = (WeakReference<V>) new WeakReference<>(view);
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
        unSubscribe();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null)
            mCompositeSubscription.unsubscribe();
    }

    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null)
            mCompositeSubscription = new CompositeSubscription();

        mCompositeSubscription.add(subscription);
    }

}