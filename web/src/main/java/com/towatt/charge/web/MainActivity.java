package com.towatt.charge.web;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

public class MainActivity extends AppCompatActivity implements OnDateSetListener {
    TimePickerDialog mDialogAll;
    private WebView web_view;
    private ProgressBar pb_loading;
    private int max=120;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web_view = (WebView)findViewById(R.id.web_view);
        pb_loading = (ProgressBar)findViewById(R.id.pb_loading);
        pb_loading.setMax(max);
        web_view.loadUrl("file:///android_asset/error_web.html");

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        web_view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                pb_loading.setVisibility(View.GONE);
                web_view.loadUrl("file:///android_asset/error_web.html");
            }
        });

        //启用支持javascript
        WebSettings settings = web_view.getSettings();
        settings.setJavaScriptEnabled(true);
        web_view.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


        web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    pb_loading.setVisibility(View.GONE);
                } else {
                    // 加载中
                    pb_loading.setVisibility(View.VISIBLE);
                    if(newProgress==0){
                        pb_loading.setProgress(20);
                    }else{
                        pb_loading.setProgress(newProgress+20);
                    }
                }

            }


        });
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setSupportZoom(true);//设定支持缩放

        settings.setLoadWithOverviewMode(true);

        settings.setBuiltInZoomControls(true);

        settings.setDefaultTextEncodingName("utf-8");
        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
        //注意Java调用JS的时候addJavascriptInterface是不必须的

        web_view.addJavascriptInterface(new Call(), "Android");
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(web_view.canGoBack())
            {
                web_view.goBack();//返回上一页面
                return true;
            }
            else
            {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void click(View v) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public void click1(View v) {
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();
        mDialogAll.show(getSupportFragmentManager(), "all");
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Toast.makeText(this,millseconds+"",Toast.LENGTH_SHORT).show();
    }

    public class Call{
        @android.webkit.JavascriptInterface
        public void loadingAgain(){
            Toast.makeText(MainActivity.this,"123",Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    web_view.loadUrl("file:///android_asset/error_web.html");
                }
            });

        }
    }
}
