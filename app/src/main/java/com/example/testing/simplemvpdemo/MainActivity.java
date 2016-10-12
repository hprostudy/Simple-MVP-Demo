package com.example.testing.simplemvpdemo;

import android.util.Log;

import com.example.testing.simplemvpdemo.base.BaseActivity;
import com.example.testing.simplemvpdemo.base.ILoadingView;

public class MainActivity extends BaseActivity<MainPresenter> implements ILoadingView{

    String mData;
    /**
     * 为view绑定对应presenter
     * @return
     */
    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    /**
     * 初始化数据等
     */
    @Override
    protected void initEventAndData() {
        //V调用P的方法来获取数据
        mData = mPresenter.getData();
    }

    /**
     * 设置布局
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showData() {
        Log.e("Data", "P调用了V的方法 + showData()");
    }
}
