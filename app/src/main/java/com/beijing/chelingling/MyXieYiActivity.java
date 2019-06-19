package com.beijing.chelingling;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyXieYiActivity
        extends AppCompatActivity {
    @Bind({R.id.agreess})
    Button agreess;
    private Handler handler = new Handler(new Handler.Callback() {
        @SuppressLint("WrongConstant")
        public boolean handleMessage(Message paramAnonymousMessage) {
            String asd = ChangWeb.MYXIEYI + MyXieYiActivity.this.id + ".html";
            Log.i("wandanle", asd);
            mWebSettings = webview.getSettings();
            MyXieYiActivity.this.mWebSettings.setUseWideViewPort(true);
            MyXieYiActivity.this.mWebSettings.setLoadWithOverviewMode(true);
            MyXieYiActivity.this.mWebSettings.setSupportZoom(true);
            MyXieYiActivity.this.mWebSettings.setBuiltInZoomControls(true);
            MyXieYiActivity.this.mWebSettings.setDisplayZoomControls(false);
            MyXieYiActivity.this.mWebSettings.setCacheMode(1);
            MyXieYiActivity.this.mWebSettings.setAllowFileAccess(true);
            MyXieYiActivity.this.mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            MyXieYiActivity.this.mWebSettings.setLoadsImagesAutomatically(true);
            MyXieYiActivity.this.mWebSettings.setDefaultTextEncodingName("utf-8");
            MyXieYiActivity.this.mWebSettings.setJavaScriptEnabled(true);
            MyXieYiActivity.this.mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            MyXieYiActivity.this.webview.loadUrl(asd);
            MyXieYiActivity.this.webview.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView paramAnonymous2WebView, String paramAnonymous2String) {
                    paramAnonymous2WebView.loadUrl(paramAnonymous2String);
                    return true;
                }
            });
            MyXieYiActivity.this.webview.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView paramAnonymous2WebView, int paramAnonymous2Int) {
                    if (paramAnonymous2Int < 100) {
                        MyXieYiActivity.this.progressBar.setProgress(paramAnonymous2Int);
                    }
                    while (paramAnonymous2Int != 100) {
                        return;
                    }
                    MyXieYiActivity.this.progressBar.setVisibility(8);
                }

                public void onReceivedTitle(WebView paramAnonymous2WebView, String paramAnonymous2String) {
                    System.out.println("标题在这里");
                    MyXieYiActivity.this.titles.setText("详情");
                }
            });
            MyXieYiActivity.this.webview.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView paramAnonymous2WebView, String paramAnonymous2String) {
                }

                public void onPageStarted(WebView paramAnonymous2WebView, String paramAnonymous2String, Bitmap paramAnonymous2Bitmap) {
                    System.out.println("开始加载了");
                }
            });
            return false;
        }
    });
    private String id;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private WebSettings mWebSettings;
    @Bind({R.id.progressBar})
    ProgressBar progressBar;
    private String[] strs;
    private String title;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.webview})
    WebView webview;

    private void postByOkGo(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) OkGo.post(paramString).tag(this)).cacheKey("cachePostKey")).cacheMode(CacheMode.DEFAULT)).params("appid", this.strs[0], new boolean[0])).params("appsecret", this.strs[1], new boolean[0])).params("timestamp", Util.getTime(), new boolean[0])).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                Log.i("wandanle", "错误");
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("wandanle", paramAnonymousString);
                try {
                    JSONObject jsonObject = new JSONObject(paramAnonymousString);
                    id = jsonObject.getString("id");
                    title = jsonObject.getString("content");
                    handler.sendEmptyMessage(1);
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_xie_yi);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        postByOkGo(ChangWeb.MYJIAYOUKAXIEYI);
        this.titles.setText("加油卡协议");
    }

    @OnClick({R.id.leftimfan, R.id.agreess})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.agreess:
                setResult(6, new Intent());
                finish();
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyXieYiActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */