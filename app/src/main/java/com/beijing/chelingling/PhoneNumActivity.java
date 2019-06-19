package com.beijing.chelingling;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class PhoneNumActivity extends AppCompatActivity {

    private String code;
    @Bind({R.id.dianjihuoqucode})
    TextView dianjihuoqucode;
    Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {

            if (paramAnonymousMessage.arg1 == 2) {
                PhoneNumActivity.this.startActivity(new Intent(PhoneNumActivity.this, ShouYeActivity.class));
                PhoneNumActivity.this.finish();
            }
            if (paramAnonymousMessage.arg1 == 3) {
                Toast.makeText(PhoneNumActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
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
                PhoneNumActivity.this.tishi.setVisibility(View.GONE);
            }
            return false;
        }
    });

    @Bind({R.id.logo})
    ImageView logo;
    @Bind({R.id.one})
    View one;
    @Bind({R.id.phonenum})
    EditText phonenum;
    @Bind({R.id.quxiao})
    TextView quxiao;
    @Bind({R.id.stattt})
    TextView stattt;
    @Bind({R.id.tishi})
    TextView tishi;
    @Bind({R.id.two})
    View two;
    @Bind({R.id.yanzhengcode})
    EditText yanzhengcode;
    @Bind(R.id.yanzhengmadenglu)
    TextView yanzhengmadenglu;
    @Bind(R.id.zhaohuimima)
    TextView zhaohuimima;


    public static boolean isChinaPhoneLegal(String paramString)
            throws PatternSyntaxException {
        return Pattern.compile("^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$").matcher(paramString).matches();
    }

    private void postByOkGo(String paramString) {
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT).params("tel", phonenum.getText().toString().trim()).params("password", yanzhengcode.getText().toString().trim()).execute(new StringCallback() {
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
                        Util.save(appid + "," + appsecret, PhoneNumActivity.this);
                        Message message = new Message();
                        message.arg1 = 2;
                        handler.sendMessage(message);
                    }
                    if (jsonObject.getString("code").equals("400")) {
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

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_phone_num);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.quxiao, R.id.stattt, R.id.dianjihuoqucode, R.id.yanzhengmadenglu, R.id.zhaohuimima})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.zhaohuimima:
                startActivity(new Intent(this, MyFindMiMaActivity.class));
                break;
            case R.id.yanzhengmadenglu:
                Util.addDestoryActivity(this, "phonenum");
                startActivity(new Intent(this, MyMiMaLoginActivity.class));
                break;
            case R.id.quxiao:
                finish();
                return;
            case R.id.dianjihuoqucode:

                return;
            case R.id.stattt:
                if (phonenum.getText().toString().trim().length() == 11) {
                    Log.i("dfdsf", "点击了开始");
                    if (!isChinaPhoneLegal(this.phonenum.getText().toString().trim())) {
                        Log.i("dfdsf", "点击了开始1111");
                        Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (this.yanzhengcode.getText().toString().trim().length() > 0) {
                        Log.i("dfdsf", "点击了开始22222");
                        postByOkGo(ChangWeb.MYMIMALOGIN);
                        return;

                    }
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\PhoneNumActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */