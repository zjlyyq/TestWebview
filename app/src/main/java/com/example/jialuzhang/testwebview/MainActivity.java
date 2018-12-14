package com.example.jialuzhang.testwebview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/index.html");
        WebSettings webSettings = webView.getSettings();
//        WebView开启支持JavaScript
        webSettings.setJavaScriptEnabled(true);
//        设置WebViewClient 主要辅助WebView处理各种通知、请求事件
        webView.setWebViewClient(new WebViewClient(){
            //实现WebView中链接在WebView内部跳转
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
//        WebChromeClient主要辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    //网页加载完成
                    Toast.makeText(MainActivity.this,"网页加载完成",Toast.LENGTH_SHORT).show();
                } else {
                    //网页加载中
                    Toast.makeText(MainActivity.this,"网页加载中",Toast.LENGTH_SHORT).show();
                }
            }
        });

//        使用Android原生提供的addJavascriptInterface添加js调原生的接口,wx是别名
        webView.addJavascriptInterface(new JavaScriptInterface(),"wx");
        webView.addJavascriptInterface(new JavaScriptInterface2(),"wx2");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一浏览页面
                return true;
            } else {
                finish();//关闭Activity
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public class JavaScriptInterface{
        @android.webkit.JavascriptInterface
        public void doTrainFinish() {
            finish();
        }
    }
    public class JavaScriptInterface2{
        @android.webkit.JavascriptInterface
        public void gotoBridgeWebView() {
            Intent intent = new Intent(MainActivity.this,BridgeWebViewActivity.class);
            startActivity(intent);
        }
    }
}
