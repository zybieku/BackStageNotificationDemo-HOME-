package com.znq.backstagenotificationdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

/**
 * desc:DemoApplication
 * Author: znq
 * Date: 2016-09-19 14:18
 */
public class DemoApplication extends Application implements Application.ActivityLifecycleCallbacks{
    private   static  DemoApplication single =null;

    public static  DemoApplication getInstance(){
        if (single==null){
            syncInit();
        }
        return single;
    }
    private static synchronized void syncInit() {
        if (null == single) {
            single=new DemoApplication();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        single =this;
        //全局管理Activity生命周期
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }
    int countActivity =0;
    @Override
    public void onActivityStarted(Activity activity) {
        countActivity++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        countActivity--;
        if(countActivity<=0){
           Intent _intent = new Intent(DemoBroadcastReceiver.ACTION_NOTIFY_MESSAGE);
            _intent.setClass(getApplicationContext(), DemoBroadcastReceiver.class);
            sendBroadcast(_intent);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
