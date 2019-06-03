package com.duke.xlws.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.duke.xlws.R;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/11/15.
 */


public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                }).setTitle("title")
                .setMessage("message")
                .setPositiveButton("积极按钮", null)
                .setNegativeButton("消极按钮", null)
                .setNeutralButton("中立", null);;


        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
