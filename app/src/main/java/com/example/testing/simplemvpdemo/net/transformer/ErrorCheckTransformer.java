package com.example.testing.simplemvpdemo.net.transformer;

import android.content.Intent;

import com.example.testing.simplemvpdemo.MyApplication;
import com.example.testing.simplemvpdemo.net.ApiException;
import com.example.testing.simplemvpdemo.net.NetBean;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by H on 2016/8/8.
 * <p>
 * 错误处理
 */
public class ErrorCheckTransformer<T> implements Observable.Transformer<NetBean<T>, T> {

    private static final String TAG = "ErrorCheckTransformer";
    public static final int DEFAULT_ERROR = 100;

    @Override
    public Observable<T> call(final Observable<NetBean<T>> httpResultObservable) {
        return httpResultObservable.map(new Func1<NetBean<T>, T>() {
            @Override
            public T call(NetBean<T> tNetBean) {
                if (tNetBean != null) {
                    int code = tNetBean.getCode();
                    switch (code) {
                        case 403:
                            //cookie失效
//                            Intent intent = new Intent(MyApplication.mContext, LoginActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.putExtra("from_where", "others");
//                            MyApplication.mContext.startActivity(intent);
                            break;

                        case 200:
                            //返回成功
                            if (tNetBean.getResults() != null)
                                return tNetBean.getResults();
                            else
                                return tNetBean.getResult();

                        case 401:
                            //cookie失效
                            break;

                        default:
                            //服务器接口返回异常
                            String errorMsg = tNetBean.getError();
                            throw new ApiException(DEFAULT_ERROR);
                    }
                } else
                    throw new ApiException(DEFAULT_ERROR);

                return null;
            }
        });
    }

    public static <T> ErrorCheckTransformer<T> create() {
        return new ErrorCheckTransformer<>();
    }

}