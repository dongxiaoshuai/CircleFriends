package com.duke.xlws.pdf;

import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 创建：duke
 * 注释：下载PDF报告并返回
 * 版本大小：
 * 版本名称：
 * 日期：2018/7/18.
 */


public class LoadPDFAsyncTask extends AsyncTask<String, Integer, File> {
    private OnLoadPDFListener onLoadPDFListener;
    private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "tintin" + File.separator + "pdf";
    private String fileName;

    public LoadPDFAsyncTask(String fileName) {
        this.fileName = fileName;
    }

    public LoadPDFAsyncTask(String fileName, OnLoadPDFListener onLoadPDFListener) {
        this.fileName = fileName;
    }

    public void setOnLoadPDFListener(OnLoadPDFListener onLoadPDFListener) {
        this.onLoadPDFListener = onLoadPDFListener;
    }

    //这里是开始线程之前执行的,是在UI线程
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //当任务被取消时回调
    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (onLoadPDFListener != null) onLoadPDFListener.onFailureListener();
    }

    //当任务被取消时回调
    @Override
    protected void onCancelled(File file) {
        super.onCancelled(file);
    }

    //子线程中执行
    @Override
    protected File doInBackground(String... strings) {
        String httpUrl = strings[0];
        if (TextUtils.isEmpty(httpUrl)) {
            if (onLoadPDFListener != null) onLoadPDFListener.onFailureListener();
        }

        File pathFile = new File(filePath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        File pdfFile = new File(filePath + File.separator + fileName);
        if (pdfFile.exists()) {
            return pdfFile;
        }
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int count = conn.getContentLength(); //文件总大小 字节
            int curCount = 0; // 累计下载大小 字节
            int curRead = -1;// 每次读取大小 字节
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(pdfFile);
                byte[] buf = new byte[1024];
                while ((curRead = is.read(buf)) != -1) {
                    curCount += curRead;
                    fos.write(buf, 0, curRead);
                    publishProgress(curCount, count);
                }

                fos.close();
                is.close();
                conn.disconnect();
            }


        } catch (Exception e) {
            e.printStackTrace();
            if (onLoadPDFListener != null) onLoadPDFListener.onFailureListener();
            return null;
        }

        return pdfFile;
    }

    //更新进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (onLoadPDFListener != null) {
            onLoadPDFListener.onProgressListener(values[0], values[1]);
        }
    }

    //当任务执行完成是调用,在UI线程
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (onLoadPDFListener != null) {
            if (file != null && file.exists()) {
                onLoadPDFListener.onCompleteListener(file);
            } else {
                onLoadPDFListener.onFailureListener();
            }
        }

    }


    public interface OnLoadPDFListener {
        void onCompleteListener(File file);

        void onFailureListener();

        void onProgressListener(Integer curPro, Integer maxPro);
    }
}
