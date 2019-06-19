package com.beijing.chelingling.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.beijing.chelingling.MyLiJiZhiFuActivity;
import com.beijing.chelingling.MyZhiFuYesActivity;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

public class WXPayEntryActivity
        extends Activity
        implements IWXAPIEventHandler {
    private IWXAPI api;
    private AlertDialog.Builder builder;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                startActivity(new Intent(WXPayEntryActivity.this, MyZhiFuYesActivity.class));
                finish();
            }
            return false;
        }
    });
    private String[] strs;

    private void postByOkGo(String paramString) {
        Log.i("liuyangdfd", "132432435435444");
        Log.i("liuyangdfd", this.strs.length + "            sfsd");
        OkGo.post(paramString).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("id", MyLiJiZhiFuActivity.id, new boolean[0])
                .params("price", MyLiJiZhiFuActivity.jine, new boolean[0])
                .params("num", MyLiJiZhiFuActivity.num, new boolean[0])
                .params("pay", "1", new boolean[0])
                .params("timestamp", Util.getTime(), new boolean[0])
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])
                .execute(new StringCallback() {
                    public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                        super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                        Log.i("liuyangdfd", "错误");
                    }

                    public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                        Log.i("liuyangdfd", paramAnonymousString);
                        try {
                            if (new JSONObject(paramAnonymousString).getString("code").equals("000")) {
                                Message message = new Message();
                                message.arg1 = 0;
                                handler.sendMessage(message);
                            }
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        this.api = WXAPIFactory.createWXAPI(this, "wx74e46dd77f0ac3d2");
        this.api.handleIntent(getIntent(), this);
    }

    protected void onNewIntent(Intent paramIntent) {
        super.onNewIntent(paramIntent);
        setIntent(paramIntent);
        this.api.handleIntent(paramIntent, this);
    }

    public void onReq(BaseReq paramBaseReq) {
    }

    public void onResp(BaseResp paramBaseResp) {
        if (paramBaseResp.getType() == 5) {
            if (paramBaseResp.errCode == 0) {
                String str = Util.load(this);
                Log.i("liuyangdfd", str + "  %%%%%%%%%");
                this.strs = str.split(",");
                this.builder = new AlertDialog.Builder(this);
                this.builder.setTitle("提示");
                this.builder.setMessage("正在处理支付结果");
                this.builder.show();
                Log.i("liuyangdfd", "132432435435444");
                postByOkGo(ChangWeb.MYZHIFUCHENGGONG);
            }
            if (paramBaseResp.errCode == -2) {
                Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\wxapi\WXPayEntryActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */