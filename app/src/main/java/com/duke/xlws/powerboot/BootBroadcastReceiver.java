package com.duke.xlws.powerboot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.duke.xlws.MainActivity;

/**
 * 创建：duke
 * 注释：开机启动广播
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/10/19.
 */


public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {

            Intent mainActivityIntent = new Intent(context, MainActivity.class);  // 要启动的Activity
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);

            Log.d("callback", "---BootBroadcastReceiver---");

        }
    }
}
