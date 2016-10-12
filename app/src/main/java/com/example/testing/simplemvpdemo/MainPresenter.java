package com.example.testing.simplemvpdemo;

import com.example.testing.simplemvpdemo.base.BasePresenter;
import com.example.testing.simplemvpdemo.base.ILoadingView;

/**
 * Created by H on 2016/10/12.
 */

public class MainPresenter extends BasePresenter<ILoadingView,MainModel> {
    /**
     * P 与 M 绑定
     * @return
     */
    @Override
    public MainModel createModel() {
        return new MainModel();
    }

    /**
     * 获取Model中的数据
     */
    public String getData(){
        String modelContent = mBaseModel.getFromM();

        //P层调用V 的方法
        mViewRef.get().showData();

        return modelContent;
    }
}
