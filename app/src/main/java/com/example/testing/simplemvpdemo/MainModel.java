package com.example.testing.simplemvpdemo;

import com.example.testing.simplemvpdemo.base.IBaseModel;

/**
 * Created by H on 2016/10/12.
 *
 * MainActivity M层
 */

public class MainModel implements IBaseModel {

    public String getFromM(){
        return "ABC FROM M";
    }
//    public Observable<CourseDetailBean> getCourseDetail(String id) {
//        return NetFactory.getNetApi().getCourseDetail(id)
//                .compose(new DefaultTransformer<CourseDetailBean>());
//    }
}
