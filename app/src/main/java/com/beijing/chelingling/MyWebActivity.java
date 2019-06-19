package com.beijing.chelingling;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beijing.chelingling.view.MyWebView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyWebActivity
        extends AppCompatActivity {
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private WebSettings mWebSettings;
    @Bind({R.id.progressBar})
    ProgressBar progressBar;
    @Bind({R.id.titles})
    TextView titles;
    private String urls;
    @Bind({R.id.webView1})
    MyWebView webView1;

    private static final String TAG = MainActivity.class.getSimpleName();
    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_web);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.urls = getIntent().getStringExtra("url");

        Log.i("sfds",urls);

        this.mWebSettings = webView1.getSettings();
        this.mWebSettings.setUseWideViewPort(true);
        this.mWebSettings.setLoadWithOverviewMode(true);
        this.mWebSettings.setSupportZoom(true);
        this.mWebSettings.setBuiltInZoomControls(true);
        this.mWebSettings.setDisplayZoomControls(false);
        this.mWebSettings.setCacheMode(1);
        this.mWebSettings.setAllowFileAccess(true);
        this.mWebSettings.setDomStorageEnabled(true);
        this.mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebSettings.setLoadsImagesAutomatically(true);
        this.mWebSettings.setDefaultTextEncodingName("utf-8");
        this.mWebSettings.setJavaScriptEnabled(true);
        this.webView1.setWebViewClient(new WebViewClient());
        this.mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView1.loadUrl(urls);
        this.webView1.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString) {
                paramAnonymousWebView.loadUrl(paramAnonymousString);
                return true;
            }
        });
        this.webView1.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt) {
                if (paramAnonymousInt < 100) {
                    MyWebActivity.this.progressBar.setProgress(paramAnonymousInt);
                }
                while (paramAnonymousInt != 100) {
                    return;
                }
                MyWebActivity.this.progressBar.setVisibility(View.GONE);
            }

            public void onReceivedTitle(WebView paramAnonymousWebView, String paramAnonymousString) {
                System.out.println("标题在这里");
                MyWebActivity.this.titles.setText("详情");
            }
        });
        this.webView1.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString) {
            }

            public void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap) {
                System.out.println("开始加载了");
            }
        });
    }

    protected void onDestroy() {
        if (this.webView1 != null) {
            this.webView1.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            this.webView1.clearHistory();
            ((ViewGroup) this.webView1.getParent()).removeView(this.webView1);
            this.webView1.destroy();
            this.webView1 = null;
        }
        super.onDestroy();
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if ((paramInt == 4) && (this.webView1.canGoBack())) {
            this.webView1.goBack();
            return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }

    @OnClick({R.id.leftimfan})
    public void onViewClicked() {
        finish();
        clearWebViewCache();
    }

    public void clearWebViewCache() {
        deleteFile(getApplicationContext().getCacheDir().getAbsoluteFile());
    }

    public void deleteFile(File paramFile) {
        Object localObject = TAG;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("delete file path=");
        localStringBuilder.append(paramFile.getAbsolutePath());
        Log.i((String) localObject, localStringBuilder.toString());
        if (paramFile.exists()) {
            if (paramFile.isFile()) {
                paramFile.delete();
            } else if (paramFile.isDirectory()) {
                File[] files = paramFile.listFiles();
                int i = 0;
                while (i < files.length) {
                    deleteFile(files[i]);
                    i += 1;
                }
            }
            paramFile.delete();
            return;
        }
        localObject = TAG;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("delete file no exists ");
        localStringBuilder.append(paramFile.getAbsolutePath());
        Log.e((String) localObject, localStringBuilder.toString());
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyWebActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */