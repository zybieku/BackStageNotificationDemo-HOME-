package com.znq.backstagenotificationdemo;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

/**
 * desc:DemoBroadcastReceiver
 * Author: znq
 * Date: 2016-09-19 14:45
 */
public class DemoBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "DemoBroadcastReceiver";
    public static final String ACTION_NOTIFY_MESSAGE = "com.znq.ACTION_NOTIFY_MESSAGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case ACTION_NOTIFY_MESSAGE:
                String name = null;
                String avatar = null;

                //方法一:从AppManger的Stack中拿到当前的Activity
           /*   ActivityManager mActivityManager = ((ActivityManager) context.getApplicationContext()
                        .getSystemService(Context.ACTIVITY_SERVICE));
                List<ActivityManager.RunningTaskInfo> taskInfos = mActivityManager.getRunningTasks(1);
                Class<?> _class= taskInfos.get(0).topActivity.getClass();*/
                //方法二:从AppManger的Stack中拿到当前的Activity
                Class<?> _class = AppManager.getInstance().currentActivity().getClass();
                if (_class != null) {
                    if (null != reflectionActivity()) {
                        //拿到反射的数据
                        Bundle _bundle = reflectionActivity();
                        if (_bundle != null) {
                            name = _bundle.getString("name");
                            avatar = _bundle.getString("avatar");
                        }
                    }
                    NotificationUtils _notifyUtils = NotificationUtils.getInstance();
                    _notifyUtils.init(context.getApplicationContext(), _class);
                    _notifyUtils.showNotification("从" + name + "退出到后台");
                }
                break;
        }
    }

    //反射拿到要通知的信息

    private Bundle reflectionActivity() {
        String workerClassName = AppManager.getInstance().currentActivity().getClass().getName();
        Bundle result = null;
        try {
            Class workerClass = Class.forName(workerClassName);
            //反射出该Class类中的onBackActivity()方法
            Method _method = workerClass.getDeclaredMethod("onBackActivity");
            //取消访问私有方法的合法性检查
            _method.setAccessible(true);
            //调用onBackActivity()方法
            Object oo = AppManager.getInstance().currentActivity();
            result = (Bundle) _method.invoke(oo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
