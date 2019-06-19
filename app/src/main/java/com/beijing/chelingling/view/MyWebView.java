package com.beijing.chelingling.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by Administrator on 2018/12/29.
 */

public class MyWebView extends com.tencent.smtt.sdk.WebView {

    public MyWebView(Context context) {
        super(context);
        init();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {

        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        this.setWebViewClient(new SimpleWebViewClient());

        this.setWebChromeClient(new WebChromeClient(){
            //这里可以设置进度条。但我是用另外一种
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
            }
        });
    }

    public static class SimpleWebViewClient extends com.tencent.smtt.sdk.WebViewClient {

        //private SimpleLoadingDialog loadingDialog;

        @Override
        public com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(com.tencent.smtt.sdk.WebView webView, String url) {
            //做广告拦截，ADFIlterTool 为广告拦截工具类
//            if (!ADFilterTool.hasAd(webView.getContext(),url)){
//                return super.shouldInterceptRequest(webView, url);
//            }else {
                return new WebResourceResponse(null,null,null);
            //}
        }
        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String url) {
            webView.loadUrl(url);
            return true;
        }
        //在开始的时候，开始loadingDialog
        @Override
        public void onPageStarted(com.tencent.smtt.sdk.WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            try{
//                loadingDialog = new SimpleLoadingDialog(webView.getContext(),true);
//                loadingDialog.show();
            }catch (Exception e){}
        }
        //在页面加载结束的时候，关闭LoadingDialog
        @Override
        public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
            super.onPageFinished(webView, s);
            try {
//                if (loadingDialog != null) {
//                    //loadingDialog.dismiss();
//                }
            } catch (Exception e) {}
        }

        @Override
        public void onReceivedError(com.tencent.smtt.sdk.WebView webView, com.tencent.smtt.export.external.interfaces.WebResourceRequest webResourceRequest, com.tencent.smtt.export.external.interfaces.WebResourceError webResourceError) {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
        }

        @Override
        public void onReceivedSslError(com.tencent.smtt.sdk.WebView webView, com.tencent.smtt.export.external.interfaces.SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
            sslErrorHandler.proceed();
        }

    }
}

