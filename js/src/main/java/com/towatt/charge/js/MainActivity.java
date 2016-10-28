package com.towatt.charge.js;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView web_view;
    private ProgressBar pb_loading;
    String load="1234565";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web_view = (WebView)findViewById(R.id.web_view);
        pb_loading = (ProgressBar)findViewById(R.id.pb_loading);
        web_view.loadUrl("file:///android_asset/index1.html");
//        web_view.loadUrl("http://192.168.1.80:8020/websocket/index.html");


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
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //当页面加载成功之后，才能执行这个。
//                web_view.loadUrl("javascript:javaCallJs("+load+")");
//                web_view.loadUrl("javascript:connectToWebSocket('14')");
//                Log.e("TAG", "load="+load);
            }
        });

        //启用支持javascript
        WebSettings settings = web_view.getSettings();
        settings.setJavaScriptEnabled(true);
        //不加载缓存
        web_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


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
                }

            }


        });
        settings.setDefaultTextEncodingName("utf-8");
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

    public void clock1(View v) {
        web_view.loadUrl("javascript:javaCallJs('我')");

//        web_view.loadUrl("javascript:webSocketStop()");
    }

    public void clock2(View v) {
        web_view.loadUrl("javascript:javaTojava('你')");
////        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
////        startActivity(intent);
//        web_view.loadUrl("javascript:connectToWebSocket('14')");
    }

    public class Call{
        @android.webkit.JavascriptInterface
        public void showHtmlcallJava(){
            Toast.makeText(MainActivity.this,"123",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }
        @android.webkit.JavascriptInterface
        public void toastSomething(String str){
            Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
        }
        @android.webkit.JavascriptInterface
        public String returnSomething(String str){
            return str+":"+"123";
        }


        @android.webkit.JavascriptInterface
        public void toConsumptionActivity(){
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //这个方法可以放到stop里面执行
        web_view.loadUrl("javascript:stop('stop')");
    }

    @Override
    protected void onDestroy() {
//        web_view.loadUrl("javascript:stop()");
        super.onDestroy();

    }


}
