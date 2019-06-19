package com.beijing.chelingling;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyMiMaLoginActivity extends AppCompatActivity {

    @Bind(R.id.logo)
    ImageView logo;
    @Bind(R.id.phonenum)
    EditText phonenum;
    @Bind(R.id.one)
    View one;
    @Bind(R.id.yanzhengcode)
    EditText yanzhengcode;
    @Bind(R.id.dianjihuoqucode)
    TextView dianjihuoqucode;
    @Bind(R.id.yeyishen)
    RelativeLayout yeyishen;
    @Bind(R.id.two)
    View two;
    @Bind(R.id.stattt)
    TextView stattt;
    @Bind(R.id.mimalogin)
    TextView mimalogin;
    @Bind(R.id.tishi)
    TextView tishi;

    private String code;
    Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {

            if (paramAnonymousMessage.arg1 == 2) {
                MyMiMaLoginActivity.this.startActivity(new Intent(MyMiMaLoginActivity.this, ShouYeActivity.class));
                Util.destoryActivity("phonenum");
                MyMiMaLoginActivity.this.finish();
            }
            if (paramAnonymousMessage.arg1 == 3) {
                Toast.makeText(MyMiMaLoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }


            return false;
        }
    });

    private Handler zhuce = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            dianjihuoqucode.setEnabled(false);
            Log.i("aaaa", msg.arg1 + "");
            dianjihuoqucode.setText("重新发送(" + msg.arg1 + ")");
            if (msg.arg1 == 0) {
                dianjihuoqucode.setEnabled(true);
                dianjihuoqucode.setText("重新发送");
            }
            if (msg.arg1 == 55) {
                MyMiMaLoginActivity.this.tishi.setVisibility(View.GONE);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mi_ma_login);
        ButterKnife.bind(this);
    }

    public static boolean isChinaPhoneLegal(String paramString)
            throws PatternSyntaxException {
        return Pattern.compile("^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$").matcher(paramString).matches();
    }

    private void postByOkGo(String paramString) {
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT).params("tel", this.phonenum.getText().toString().trim(), new boolean[0]).params("yzm", this.yanzhengcode.getText().toString().trim(), new boolean[0]).params("timestamp", Util.getTime(), new boolean[0]).params("sign", Util.computeSha1OfString("tel=" + this.phonenum.getText().toString().trim() + "&timestamp=" + Util.getTime()), new boolean[0]).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("sadsadsadsa", paramAnonymousString);
                try {
                    JSONObject jsonObject = new JSONObject(paramAnonymousString);
                    if (jsonObject.getString("code").equals("000")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String appid = jsonObject1.getString("appid");
                        String appsecret = jsonObject1.getString("appsecret");
                        Util.save(appid + "," + appsecret, MyMiMaLoginActivity.this);
                        Message message = new Message();
                        message.arg1 = 2;
                        handler.sendMessage(message);

                    }
                    if (jsonObject.getString("code").equals("405")) {
                        Message message = new Message();
                        message.arg1 = 3;
                        handler.sendMessage(message);
                        return;
                    }
                } catch (JSONException ee) {
                    ee.printStackTrace();
                }
            }
        });
    }

    private void postByOkGoHuoQu(String paramString, int paramInt) {
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT).params("tel", this.phonenum.getText().toString().trim(), new boolean[0]).params("yzm", paramInt + "", new boolean[0]).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                Log.i("mvbnbvnvb", "错误");
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("mvbnbvnvb", paramAnonymousString);
                try {
                    JSONObject jsonObject = new JSONObject(paramAnonymousString);
                    code = jsonObject.getString("content");
                    return;
                } catch (JSONException e) {


                    e.printStackTrace();
                }
            }
        });
    }


    @OnClick({R.id.stattt, R.id.mimalogin, R.id.dianjihuoqucode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dianjihuoqucode:
                this.yanzhengcode.setFocusable(true);
                this.yanzhengcode.setFocusableInTouchMode(true);
                this.yanzhengcode.requestFocus();
                if (!isChinaPhoneLegal(this.phonenum.getText().toString().trim())) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    return;
                }
                this.tishi.setVisibility(View.VISIBLE);
                this.tishi.setText("已给  " + this.phonenum.getText().toString().trim() + "用户发送验证码短信，请注意查收");
                int i = (int) ((Math.random() * 9.0D + 1.0D) * 100000.0D);
                postByOkGoHuoQu(ChangWeb.MYYANZHENGCODE, i);
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 60; i >= 0; i--) {
                                    try {
                                        Thread.sleep(1000);
                                        Message m = new Message();
                                        m.arg1 = i;
                                        zhuce.sendMessage(m);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                ).start();
                break;
            case R.id.stattt:
                if (this.yanzhengcode.getText().toString().trim().length() > 0) {
                    if (this.yanzhengcode.getText().toString().trim().equals(this.code)) {
                        postByOkGo(ChangWeb.MYZHUCEDENGLU);
                        return;
                    }
                    Toast.makeText(this, "验证码输入错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mimalogin:
                finish();
                break;
        }
    }
}
