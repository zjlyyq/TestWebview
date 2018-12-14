package com.example.jialuzhang.testwebview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

public class BridgeWebViewActivity extends AppCompatActivity {
    public String TAG = "TEST";
    BridgeWebView bridgeWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge_web_view);
        bridgeWebView = (BridgeWebView)findViewById(R.id.bridgeWebview);
        bridgeWebView.loadUrl("file:///android_asset/index2.html");
        bridgeWebView.registerHandler("submitFromWeb",new BridgeHandler() {
            //必须和js同名函数，注册具体执行函数，类似java实现类。
            //第一参数是订阅的java本地函数名字 第二个参数是回调Handler , 参数返回js请求的resqustData,function.onCallBack（）回调到js，调用function(responseData)
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e(TAG, "指定Handler接收来自web的数据：" + data);
                function.onCallBack("DefaultHandler收到Web发来的数据，回传数据给你");
            }
        });
    }
}
