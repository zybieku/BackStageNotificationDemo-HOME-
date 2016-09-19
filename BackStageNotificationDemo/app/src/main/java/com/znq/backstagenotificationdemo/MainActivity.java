package com.znq.backstagenotificationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationUtils.getInstance().clearAllNotification();
    }

    /*
        *反射给通知栏的内容 ,这里仅仅做测试*/
    private Bundle onBackActivity() {
        Bundle _bundle = new Bundle();
        _bundle.putString("name", TAG);
        _bundle.putString("avatar", "onBackActivity");
        return _bundle;
    }
}
