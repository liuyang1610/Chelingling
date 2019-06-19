package com.beijing.chelingling.wxapi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.MyApp;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import okhttp3.Call;
import okhttp3.Response;

public class WXEntryActivity
        extends AppCompatActivity
        implements IWXAPIEventHandler {
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            Log.i("wandanle", "结束啦");
            WXEntryActivity.this.finish();
            return false;
        }
    });
    private String[] strs;

    private void postByOkGo(String paramString) {
        Log.i("wandanle", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("timestamp", Util.getTime(), new boolean[0])
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])
                .execute(new StringCallback() {
                    public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                        super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                        Log.i("wandanle", "错误");
                    }

                    public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                        Log.i("wandanle", paramAnonymousString);
                        handler.sendEmptyMessage(1);
                    }
                });
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        MyApp.mWxApi.handleIntent(getIntent(), this);
    }

    public void onReq(BaseReq paramBaseReq) {
    }

    public void onResp(BaseResp paramBaseResp) {
        switch (paramBaseResp.errCode) {
            default:
                break;
            case 0:
                Log.i("wandanle", "OKKKK");
                String s = Util.load(this);
                Log.i("sadsad", paramBaseResp + "  %%%%%%%%%");
                this.strs = s.split(",");
                postByOkGo(ChangWeb.MYFENXIANGAPP);
                break;
            case -2:
                finish();
                break;
            case -4:
                finish();
                break;
            case -3:
                finish();
                break;
            case -5:
                finish();
                break;
            case -6:
                finish();
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\wxapi\WXEntryActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */