package com.znq.backstagenotificationdemo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * desc:BaseActivity
 * Author: znq
 * Date: 2016-09-19 16:31
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //判断程序在后台方法一
   /*     if(!isAppOnForeground()){
            Toast.makeText(getApplicationContext(), TAG+"onPause:",
                    Toast.LENGTH_SHORT).show();
        }else {
            Log.e(TAG, "onPause: "+"弹出通知");
            //第一种方式退到后台
            Intent _intent = new Intent(DemoBroadcastReceiver.ACTION_NOTIFY_MESSAGE);
            _intent.setClass(getApplicationContext(), DemoBroadcastReceiver.class);
            sendBroadcast(_intent);

        }*/
    }


    /**
     * 程序是否在前台运行
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        /**
         * 获取Android设备中所有正在运行的App
         */
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
