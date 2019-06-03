package com.duke.xlws.pdf;

import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.duke.xlws.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;

import java.io.File;

/**
 * 创建：duke
 * 注释：PDF报告预览
 * 版本大小：17
 * 版本名称：v2.1.4
 * 日期：2018/7/18.
 */


public class PDFActivity extends AppCompatActivity {

    private PDFView pdfView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = (PDFView) findViewById(R.id.pdf_view);
        String nbPath = getFilesDir().getAbsolutePath();
        String wbPath = getExternalFilesDir("").getAbsolutePath();
        String wbRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d("callback",
                "内部存储路径->" + nbPath + "\n"
                        + "外部存储路径->" + wbPath + "\n"
                        + "外部存储根路径->" + wbRootPath);

        pdfView.fromAsset("test_pdf.pdf")
                .enableSwipe(true)//是否允许翻页，默认是允许翻页
                .spacing(5)//每页间距 dp
                .defaultPage(0)//默认显示第几页
                .swipeHorizontal(false)// true 水平翻页 false 垂直翻页
                .enableAntialiasing(true)// 改善低分辨率屏幕上的渲染
                .enableDoubletap(true)//允许双击缩放
                .enableAnnotationRendering(true)//显示注解
                .onLoad(new OnLoadCompleteListener() {//加载监听
                    @Override
                    public void loadComplete(int nbPages) {
                        Toast.makeText(PDFActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                    }
                })
                .onError(new OnErrorListener() {//异常监听
                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(PDFActivity.this, "加载错误", Toast.LENGTH_SHORT).show();

                    }
                })
                .onPageChange(new OnPageChangeListener() {//翻页监听
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        Toast.makeText(PDFActivity.this, (page + 1) + "/" + pageCount, Toast.LENGTH_SHORT).show();
                    }
                })
                .onPageScroll(new OnPageScrollListener() {//滚动监听
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {

                    }
                })
                .load();

        String pdfName = "小胖.pdf";
        String pdfUrl = "http://xiaopang/xiaopang.pdf";

        new LoadPDFAsyncTask(pdfName, new LoadPDFAsyncTask.OnLoadPDFListener() {
            @Override
            public void onCompleteListener(File file) {

                pdfView.fromFile(file)
                        .enableSwipe(true)//是否允许翻页，默认是允许翻页
                        .spacing(5)//每页间距 dp
                        .defaultPage(0)//默认显示第几页
                        .swipeHorizontal(false)// true 水平翻页 false 垂直翻页
                        .enableAntialiasing(true)// 改善低分辨率屏幕上的渲染
                        .enableDoubletap(true)//允许双击缩放
                        .enableAnnotationRendering(true)//显示注解
                        .onLoad(new OnLoadCompleteListener() {//加载监听
                            @Override
                            public void loadComplete(int nbPages) {
                                Toast.makeText(PDFActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onError(new OnErrorListener() {//异常监听
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(PDFActivity.this, "加载错误", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .onPageChange(new OnPageChangeListener() {//翻页监听
                            @Override
                            public void onPageChanged(int page, int pageCount) {
                                Toast.makeText(PDFActivity.this, (page + 1) + "/" + pageCount, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onPageScroll(new OnPageScrollListener() {//滚动监听
                            @Override
                            public void onPageScrolled(int page, float positionOffset) {

                            }
                        })
                        .load();
            }

            @Override
            public void onFailureListener() {

            }

            @Override
            public void onProgressListener(Integer curPro, Integer maxPro) {

            }
        }).execute(pdfUrl);



    }


    public int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
