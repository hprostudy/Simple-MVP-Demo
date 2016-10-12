package com.example.testing.simplemvpdemo.base;

/**
 * Created by H on 16/8/8.
 */
public interface ILoadingContentView extends ILoadingView {

    void showContent();
    void showNotdata();
    void showError(String msg);

}
