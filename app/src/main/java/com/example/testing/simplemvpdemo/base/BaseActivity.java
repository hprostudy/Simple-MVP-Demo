package com.example.testing.simplemvpdemo.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testing.simplemvpdemo.MyApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by H on 16/7/7.
 * <p/>
 * Activity基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {

    public static final String BUNDLE_EXTRA = "bundle_extra";

    protected Context mContext;
    protected T mPresenter;
    private Unbinder mUnBinder;

    private ViewGroup mTitleBar;
    private boolean isInitActionBar = false;

    private ImageView leftImageView;
    private ImageView rightImageView;
    private TextView rightTextView;
    private TextView titleTextView;

    private ImageView titleImageView;
    protected boolean resumed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        MyApplication.addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(getLayoutId());

        mPresenter = createPresenter();
        if (mPresenter != null)
            mPresenter.attachView(createOptionView());

        mUnBinder = ButterKnife.bind(this);
        initEventAndData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Logger.i("Activity 执行onDestroy");
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        resumed = false;
    }

    protected abstract T createPresenter();

    protected IBaseView createOptionView() {
        return this;
    }

    protected abstract void initEventAndData();

    protected abstract int getLayoutId();

}