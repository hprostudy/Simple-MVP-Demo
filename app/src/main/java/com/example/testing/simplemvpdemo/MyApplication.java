package com.example.testing.simplemvpdemo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.example.testing.simplemvpdemo.net.DataManager;
import com.example.testing.simplemvpdemo.net.RManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by H on 16/7/7.
 * <p/>
 * 应用程序初始化
 */
public class MyApplication extends Application {

    public static Context mContext;
    public static List<Activity> activityStack;

    public static String imageFolderPath;

    /**
     * 判断文件夹是否创建
     */
    public static boolean isCreate;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        imageFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "WeiMiHui";
        File imageFolder = new File(imageFolderPath);
        if (!imageFolder.exists())
            isCreate = imageFolder.mkdir();

        //渐进式加载
        ProgressiveJpegConfig config = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int i) {
                return i + 2;
            }

            @Override
            public QualityInfo getQualityInfo(int i) {
                boolean isGoodEnough = (i >= 5);
                return ImmutableQualityInfo.of(i, isGoodEnough, false);
            }
        };

        ImagePipelineConfig pipelineConfig = OkHttpImagePipelineConfigFactory
                .newBuilder(this, RManager.INSTANCE.getOkHttpClient())
                .setProgressiveJpegConfig(config)
                .build();

        Fresco.initialize(this, pipelineConfig);

        DataManager.getInstance().initService();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static synchronized void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new ArrayList<Activity>();
        }
        activityStack.add(activity);
    }

    public static synchronized void removeActivity(Activity activity) {
        if (activity != null && activityStack != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 退出应用程序
     */
    public static synchronized void exitApplication() {
        try {
            for (Activity activity : activityStack) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {

        } finally {

        }
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

}
