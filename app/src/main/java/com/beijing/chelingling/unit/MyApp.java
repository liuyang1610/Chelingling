package com.beijing.chelingling.unit;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;

import cn.jpush.android.api.JPushInterface;

public class MyApp
        extends MultiDexApplication {
    public static IWXAPI mWxApi;

    private void registToWX() {
        mWxApi = WXAPIFactory.createWXAPI(this, "wx74e46dd77f0ac3d2", true);
        mWxApi.registerApp("wx74e46dd77f0ac3d2");
    }


    public void onCreate() {
        super.onCreate();
        OkGo.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        registToWX();
        initX5();
        UMConfigure.init(this, 1, "");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initX5() {
        //如果没有这个内核，允许在WIFI情况下去下载内核
        QbSdk.setDownloadWithoutWifi(true);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }
            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

}


