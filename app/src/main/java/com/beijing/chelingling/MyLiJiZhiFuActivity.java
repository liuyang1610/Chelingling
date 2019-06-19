package com.beijing.chelingling;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.beijing.chelingling.weichats.CeShiActivity;
import com.beijing.chelingling.zhifubao.OrderInfoUtil2_0;
import com.beijing.chelingling.zhifubao.PayResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyLiJiZhiFuActivity
        extends AppCompatActivity {
    public static final String APPID = "2018041202546335";
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUWH07+tPiXhEzxQ2CmCPIYzZKWeTPA6WQBtRhZwqGlNvJydmhEwfitb7gD3lkas+0CKxMhCoXvc9sJ1caS+ZQXBps6dhgqikz34fNWiqpONQq5GXH4t6gwYEfUgc1do/q73YV5PKA7Z94KYJl3t8YkQ6kKXu5NsS55c4hY1tde03w7hvHVoUy/PYd04RUVCdMBOgNvIi0Is7xbbwP7uyICw1VHw7fPEhvrHliWnIZAQcdfD30C0SY9ABpo8jO+Qg3cGy+bkZqAgd3h0OGDfSUOSWyJp8tqLFLjzCQ5/Y82enr52cW1vwIv/fkG8wwl0k1k0lEAcSF8LQEMreIaVizAgMBAAECggEAYs5Jo6CebiDfbk7qf7hb5QWHzJ/xUZ0JMlQKM1IRZbBScCXcqXdmgi8xkkVcQnEOJMi+EjzZWHTZah0N2/r/zTh7dV2P/Fp6FQ3mgvVygsHSNztnO2q1g/c+zKCzIn30v7N9QHn8t7kNxVhIfRekGtXLHbZvrN54y6Tv1bc6zJjev90ejceKQ3fNeuHUm+rJ2JA6EqH+cv1KBS3608tQq6TMjTJd30DAOwvgO5WLG8QEVkuNkXmvJzA/Ab/WG+jMB06PzY04wefbtaKuzmwucaxOzxA+/nJ644MKvqed05rV+ak488dxG65UgDhJT+oioJupgft6oCJLYWAC81EsSQKBgQD9DwKXEvufAgUnodbJtmCrf3Qa32fEaATUzcKNFyuQtyuwCLOeulK5O876F3epBTT/P5eioF41KA7L8N0X4jzeimeYcorIXrThswh5foz3568hWwoIC7YbPLD5g8mmJpcWBs7uLMGpLYx0bm62mLSe+GFJxaUV0BYv4KhmMsqx5QKBgQCWEeZd4Xu/QuhgeeRGsmMjGuxnhGmsiJ5uewF7hOS23xj3ZL22qd+HJUvzi7cwZ9lg0JkugFcQArf/rALVnaTr3gNT2l2R9DzdD1sZklVFtrvCLa1gssGIJyX2V4iRD4fFDGZPlfmvGfTxJNgdy/2ar/c9KbqIHHZ8Hiv7a6iWtwKBgQDPXCtdK/rvcrJSpXThWQahwRCcrI/VCv9aeTiAcIoMTWwJFThKSpdanb2ehPhSkae5uJxCcHKB0E8/oiEpXwNNv8/fxYwfYCuL/i5lj+NniHujM3xm12Hjs7m429qywVBwhieuVxJh1KcRCSTDjyICWbkuwDvkQdzMYqTbCae6WQKBgAq/oXZb7hcW3bn7QQRnRn5/MSuA6mi3KFHVcZe9PvxyIkGl74ijxiTR4QzClw5agdFTX4VbH+E/sJoZYeKW9fcG4cohu832Zl2W+fls57qkifIwf1iLtRzhYPCx+FAbgqf0aWeN4GJQQ6D5+Ji5waJQtC6nTHrvPvVI2/GYFm0lAoGADoha+DniZet3fFi6PxVl5+lqDEINW/DupAt1nRnQ8fwLKGu70bE19l7H+32qxkjrvfdF7NDqdALIKnOm7aV0GNs5LVPOHO17EAkx7k/3q0+KO+t8L9fcHFhtFaybNI7mF8tobZadWLdkYdYEM1Or+jZSJy0EcTAWDRotDIFetgY=";
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    public static String id;
    public static String jine;
    public static String num;
    @Bind({R.id.chongzhifangshiim})
    ImageView chongzhifangshiim;
    @Bind({R.id.chongzhifangshite})
    TextView chongzhifangshite;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            MyLiJiZhiFuActivity.this.startActivity(new Intent(MyLiJiZhiFuActivity.this, MyZhiFuYesActivity.class));
            return false;
        }
    });
    private boolean iswechat = true;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.lijizhifu})
    Button lijizhifu;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Log.i("fdsfds", "000支付成功");
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Log.i("fdsfds", "111支付成功");
                        postByOkGo(ChangWeb.MYZHIFUCHENGGONG);
                    } else {
                        Toast.makeText(MyLiJiZhiFuActivity.this, "支付失败,已加入订单", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private String[] strs;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.weichaim})
    ImageView weichaim;
    @Bind({R.id.weichatte})
    TextView weichatte;
    @Bind({R.id.xianshangchongzhid})
    RelativeLayout xianshangchongzhid;
    @Bind({R.id.xuanzetaocan})
    ImageView xuanzetaocan;
    @Bind({R.id.xuanzezhfuim})
    ImageView xuanzezhfuim;
    @Bind({R.id.xuanzezhfute})
    TextView xuanzezhfute;
    @Bind({R.id.youjikachongzhid})
    RelativeLayout youjikachongzhid;
    @Bind({R.id.zhhifuchenggongim})
    ImageView zhhifuchenggongim;
    @Bind({R.id.zhhifuchenggongte})
    TextView zhhifuchenggongte;
    @Bind({R.id.zhifubaiim})
    ImageView zhifubaiim;
    @Bind({R.id.zhifubao})
    TextView zhifubao;
    private String zhifutype = "1";

    private void postByOkGo(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("id", id, new boolean[0])
                .params("price", jine, new boolean[0])
                .params("num", num, new boolean[0])
                .params("pay", this.zhifutype, new boolean[0])
                .params("timestamp", Util.getTime(), new boolean[0])
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])
                .execute(new StringCallback() {
                    public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                        super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                        Log.i("wandanle", "错误");
                    }

                    public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                        Log.i("wandanle", paramAnonymousString);
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

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_li_ji_zhi_fu);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        this.titles.setText("立即支付");

        jine = getIntent().getStringExtra("jiages");
        id = getIntent().getStringExtra("id");
        num = getIntent().getStringExtra("num");
        Log.i("liuyangdfd", "asdasdasf22222" + jine + "  " + id);
    }

    @OnClick({R.id.leftimfan, R.id.xianshangchongzhid, R.id.youjikachongzhid, R.id.lijizhifu})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            case R.id.leftimfan:
                finish();
                break;
            case R.id.xianshangchongzhid:
                this.iswechat = true;
                this.xianshangchongzhid.setBackgroundResource(R.drawable.greenline);
                this.youjikachongzhid.setBackgroundResource(R.drawable.whitgline);
                this.weichaim.setBackgroundResource(R.drawable.weichattrue);
                this.weichatte.setTextColor(Color.parseColor("#3aaf08"));
                this.zhifubaiim.setBackgroundResource(R.drawable.zhifubaofalse);
                this.zhifubao.setTextColor(Color.parseColor("#8a8a8a"));
                break;
            case R.id.youjikachongzhid:
                this.iswechat = false;
                this.youjikachongzhid.setBackgroundResource(R.drawable.blueline);
                this.xianshangchongzhid.setBackgroundResource(R.drawable.whitgline);
                this.weichaim.setBackgroundResource(R.drawable.weichatfalse);
                this.weichatte.setTextColor(Color.parseColor("#8a8a8a"));
                this.zhifubaiim.setBackgroundResource(R.drawable.zhifubaotrue);
                this.zhifubao.setTextColor(Color.parseColor("#1296db"));
                break;
            case R.id.lijizhifu:
                if (this.iswechat == true) {
                    this.zhifutype = "1";
                    new CeShiActivity().StartWeiChat(jine + "00");
                } else {
                    this.zhifutype = "2";
                    zhifubao(jine);
                }
                break;
        }

    }

    public void zhifubao(String jine) {
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, jine);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        Log.i("fdsfds", "111等待支付kaishi");
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(MyLiJiZhiFuActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Log.i("fdsfds", "111支付kaishi");
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyLiJiZhiFuActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */