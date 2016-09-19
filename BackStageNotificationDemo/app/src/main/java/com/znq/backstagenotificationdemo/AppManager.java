package com.znq.backstagenotificationdemo;

import android.app.Activity;

import java.util.Stack;

/**
 * desc:AppManager Activity栈管理,这是一个单例
 * Author: znq
 * Date: 2016-09-19 15:03
 */
public class AppManager {
    private static AppManager instance;
    private static Stack<Activity> activityStack;

    public static AppManager getInstance() {
        if (instance == null) {
            //懒汉双层锁,保证线程安全
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到stack中
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        if (activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
        activityStack.add(activity);
    }

    /**
     * 获取stack中当前的Activity
     */
    public Activity currentActivity() {
        if (null != activityStack && null != activityStack.lastElement()) {
            return activityStack.lastElement();
        }
        return null;
    }

    /**
     * 移除当前的Activity
     */
    public void finishActivity() {
        if (null != activityStack && null != activityStack.lastElement()) {
            finishActivity(activityStack.lastElement());
        }
    }

    /**
     * 移除指定的Activity
     *
     * @param activity 指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除指定Class所对应的Activity
     */
    public void finishActivity(Class<?> cls) {
        Stack<Activity> activitys = new Stack<Activity>();
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                activitys.add(activity);
            }
        }

        for (Activity activity : activitys) {
            finishActivity(activity);
        }
    }

    /**
     * 移除所有的Activity
     */
    public void finishAllActivity() {
        if (activityStack == null)
            return;

        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

}
