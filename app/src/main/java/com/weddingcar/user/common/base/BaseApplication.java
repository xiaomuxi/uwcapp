package com.weddingcar.user.common.base;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.network.library.utils.Logger;
import com.weddingcar.user.common.manager.SPController;

import java.util.List;

import static com.weddingcar.user.common.manager.SPController.ALI_PUSH_DEVICE_ID;


/**
 * Base application
 */
public class BaseApplication extends Application {
    public String TAG = this.getClass().getSimpleName();

    //Global context
    private static BaseApplication mInstance;
    //Main thread id
    private static int mMainThreadId = -1;
    //Main thread
    private static Thread mMainThread;
    //Main thread handler
    private static Handler mMainThreadHandler;
    //Main thread looper
    private static Looper mMainLooper;

    @Override
    public void onCreate() {
        super.onCreate();
        initAliPushChannel();
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        mInstance = this;
        String processName = getProcessName(this, android.os.Process.myPid());
        if (null == processName || processName.equals(getApplication().getPackageName())) {
            initSystems();
        }
    }

    private void initAliPushChannel() {
        PushServiceFactory.init(this);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(this, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                String deviceId = pushService.getDeviceId();
                SPController.getInstance().putString(ALI_PUSH_DEVICE_ID, deviceId);
                Logger.D("init AliPushChannel success and deviceId = " + deviceId);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Logger.D("init AliPushChannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "1";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "婚车";
            // 用户可以看到的通知渠道的描述
            String description = "婚车通知";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    /**
     * Init system method
     */
    public void initSystems() {}

    /**
     * Get application instance
     */
    public static BaseApplication getApplication() {
        return mInstance;
    }

    /**
     * Get the main thread id
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * Get the main thread
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * Get the main thread handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * Get the main thread looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    /**
     * Get process name by pid
     * @param cxt   context
     * @param pid   app process id
     * @return processName
     */
    public String getProcessName(Context cxt, int pid) {
        try {
            ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
            if (null != runningApps) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningApps) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
