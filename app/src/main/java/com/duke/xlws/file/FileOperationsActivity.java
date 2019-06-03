package com.duke.xlws.file;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.duke.xlws.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 创建：duke
 * 注释：
 * 版本大小：
 * 版本名称：
 * 日期：2019/4/23.
 */


public class FileOperationsActivity extends AppCompatActivity {
    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + "";
    private static final String FILE_NAME = "test.txt";
    private static final String MSG = "文件不存在";
    private static final String FILE_NAME_1 = "write.txt";

    private TextView readTv;
    private TextView writeTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_operations);
        readTv = (TextView) findViewById(R.id.read_tv);
        writeTv = (TextView) findViewById(R.id.write_tv);

    }


    //读取文件
    Runnable redRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                File filePath = new File(FILE_PATH);
                if (!filePath.exists()) {
                    toMsg(MSG);
                    return;
                }

                File file = new File(FILE_PATH, FILE_NAME);
                if (!file.exists()) {
                    toMsg(MSG);
                    return;
                }

                FileInputStream fis = new FileInputStream(file);
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();

                int count = 0; //累计读取字节
                int curRead = 0;// 每次读取字节
                byte[] buff = new byte[5]; //每次读取字节数
                //在数组b中写入数据的起始位置的偏移
//                fis.read(b, 0, length)
                while ((curRead = fis.read(buff)) != -1) {
                    count += curRead;
                    baos.write(buff, 0, curRead);

                }

                fis.close();
                baos.close();
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        readTv.setText("读取文件" + FILE_NAME + "成功:" + new String(baos.toByteArray()));
                    }
                });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    //写入文件
    Runnable writeRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                File filePath = new File(FILE_PATH);
                if (!filePath.exists()) {
                    filePath.exists();
                }

                File file = new File(FILE_PATH, FILE_NAME);
                if (!file.exists()) {
                    file.createNewFile();
                }

                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                final String txt = "123456789";
                raf.seek(file.length());
                raf.write(txt.getBytes());
                raf.close();

                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        writeTv.setText("写入文件" + FILE_NAME + "成功:" + txt);
                    }
                });


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };


    private void toMsg(final String txt) {
        runOnUiThread(new Runnable() {
            @SuppressLint({"WrongConstant", "ShowToast"})
            @Override
            public void run() {
                Toast.makeText(FileOperationsActivity.this, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void log(byte[] b) {
        char c;
        for (byte bit : b) {
            if (bit == 0) {
                c = '-';
            } else {
                c = (char) bit;
            }

            Log.d("callback", String.valueOf(c));
        }

        Log.e("callback", "------------");
    }

    public void onRead(View view) {
        new Thread(redRunnable).start();
    }

    public void onWrite(View view) {
        new Thread(writeRunnable).start();
    }
}
