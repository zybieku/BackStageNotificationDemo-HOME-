package com.znq.backstagenotificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

/**
 * desc:NotificationUtils
 * Author: znq
 * Date: 2016-09-19 14:45
 */
public class NotificationUtils {
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private Intent mNotificationIntent;
    private int notifId = 1;

    protected Vibrator mVibrator;
    /**
     * 饿汉式单例*/
    private static final NotificationUtils _instance =new NotificationUtils();
    private Context mContext;

    public static NotificationUtils getInstance(){
        return  _instance;
    }

    public void init(Context context, @NonNull Class<?> intentActivity){
        mContext = context;
        //初始化震动
        mVibrator = (Vibrator) context .getSystemService(Context.VIBRATOR_SERVICE);
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationIntent = new Intent(mContext, intentActivity);
        mNotificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (notifId <= 4) {
            //最多显示4条通知
            notifId += 1;
        }
    }

    public void showNotification(@NonNull String contentText){
        PendingIntent _pendingIntent = PendingIntent.getActivity(mContext, notifId, mNotificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("通知测试")
                .setContentText(contentText)
                .setSubText(mContext.getString(R.string.notice_click_into))
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{0, 0, 0, 10})
                //.setFullScreenIntent(_pendingIntent, true)
                .setContentIntent(_pendingIntent);
        //在什么状态下显示通知,比如锁屏状态
        if (Build.VERSION.SDK_INT >= 21) {
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            builder.setCategory(Notification.CATEGORY_MESSAGE);
        }
        mNotification = builder.build();
        mNotificationManager.notify(notifId, mNotification);
    }

    //设置震动
    private void setVibrateNotification() {

        mNotification.defaults |= Notification.DEFAULT_VIBRATE;
        long[] vibrate = {0, 50, 0, 100};
        mNotification.vibrate = vibrate;

    }

    //清除所有推送通知
    public void clearAllNotification() {
        if (mNotificationManager != null)
            mNotificationManager.cancelAll();
    }

}
