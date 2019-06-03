package com.duke.xlws.pdf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.duke.xlws.R;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/9/12.
 */


public class PDFWebActivity extends AppCompatActivity {
    private WebView webView;

//        private String fileUrl = "http://cdn.mozilla.net/pdfjs/tracemonkey.pdf";
    private String fileUrl = "file:///android_asset/test_pdf.pdf";
    private String pdfHtml = "file:///android_asset/pdf.html";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_web);

        webView = (WebView) findViewById(R.id.webview);

        initData();
    }


    @SuppressLint("AddJavascriptInterface")
    private void initData() {

        //webview的配置
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(false);//设置WebView是否支持使用屏幕控件或手势进行缩放，默认是true
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        //添加一个java的回调，在pdf解析完数据之后，取消dialog
        webView.addJavascriptInterface(new JsObject(this, fileUrl), "client");

        webView.loadUrl(pdfHtml);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //html页面加载完毕之后发送通知，调用js方法来解析pdf文档
                handler.sendEmptyMessage(1);
                Toast.makeText(PDFWebActivity.this, "加载完毕", Toast.LENGTH_SHORT).show();
            }

        });
    }


    //调用js方法
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String javaScript = "javascript: getpdf2('" + fileUrl + "')";
            webView.loadUrl(javaScript);
        }
    };

    class JsObject {
        Activity mActivity;
        String url;

        public JsObject(Activity activity, String url) {
            mActivity = activity;
            this.url = url;
        }

        //    测试方法
        @JavascriptInterface
        public String dismissProgress() {
//            pro.setVisibility(View.GONE);
            return this.url;
        }
    }
}
