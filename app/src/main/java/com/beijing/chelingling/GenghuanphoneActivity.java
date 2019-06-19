package com.beijing.chelingling;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.SelfDialog;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class GenghuanphoneActivity
        extends AppCompatActivity {
    @Bind({R.id.card})
    TextView card;
    @Bind({R.id.cardsss})
    EditText cardsss;
    private String code;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                Toast.makeText(GenghuanphoneActivity.this, "更换成功", Toast.LENGTH_SHORT).show();
                GenghuanphoneActivity.this.finish();
            }
            if (paramAnonymousMessage.arg1 == 1) {
                Toast.makeText(GenghuanphoneActivity.this, "更换失败", Toast.LENGTH_SHORT).show();
            }
            if (paramAnonymousMessage.arg1 == 3) {
                GenghuanphoneActivity.this.sendcard.setText(GenghuanphoneActivity.this.jishi + "秒后重试");
                if (GenghuanphoneActivity.this.jishi == 0) {
                    GenghuanphoneActivity.this.sendcard.setText("重新发送");
                    GenghuanphoneActivity.this.sendcard.setEnabled(true);
                }
            }
            return false;
        }
    });

    private Handler zhuce = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            sendcard.setEnabled(false);
            Log.i("aaaa", msg.arg1 + "");
            sendcard.setText("重新发送(" + msg.arg1 + ")");
            if (msg.arg1 == 0) {
                sendcard.setEnabled(true);
                sendcard.setText("重新发送");
            }
            return false;
        }
    });

    private int jishi;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.phone})
    TextView phone;
    @Bind({R.id.phonenums})
    EditText phonenums;
    @Bind({R.id.qian})
    ImageView qian;
    @Bind({R.id.qians})
    ImageView qians;
    private SelfDialog selfDialog;
    @Bind({R.id.sendcard})
    Button sendcard;
    private String[] strs;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.yanzhengnew})
    TextView yanzhengnew;

    private void postByGengHuan(String paramString1, String paramString2) {
        OkGo.post(paramString1).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT).params("appid", this.strs[0], new boolean[0]).params("appsecret", this.strs[1], new boolean[0]).params("tel", paramString2, new boolean[0]).params("timestamp", Util.getTime(), new boolean[0]).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0]).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                Log.i("mvbnbvnvb", "错误");
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("mvbnbvnvb", paramAnonymousString);
                try {
                    paramAnonymousString = new JSONObject(paramAnonymousString).getString("code");
                    if (paramAnonymousString.equals("000")) {
                        Message message = new Message();
                        message.arg1 = 0;
                        handler.sendMessage(message);
                    }
                    if (paramAnonymousString.equals("402")) {
                        Message message = new Message();
                        message.arg1 = 1;
                        handler.sendMessage(message);
                    }
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postByOkGoHuoQu(String paramString, int paramInt) {
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT).params("tel", this.phonenums.getText().toString().trim(), new boolean[0]).params("yzm", paramInt + "", new boolean[0]).execute(new StringCallback() {
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

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_genghuanphone);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.titles.setText("更换手机");
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        this.phonenums.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable paramAnonymousEditable) {
                if (paramAnonymousEditable.toString().length() == 11) {
                    GenghuanphoneActivity.this.sendcard.setEnabled(true);
                    GenghuanphoneActivity.this.sendcard.setTextColor(-1);
                    GenghuanphoneActivity.this.sendcard.setBackgroundResource(R.drawable.buttonstyle);
                    return;
                }
                GenghuanphoneActivity.this.sendcard.setEnabled(false);
                GenghuanphoneActivity.this.sendcard.setTextColor(-16777216);
                GenghuanphoneActivity.this.sendcard.setBackgroundResource(R.drawable.buttonstylemoren);
            }

            public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
            }

            public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
            }
        });
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @OnClick({R.id.leftimfan, R.id.yanzhengnew, R.id.sendcard})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            case R.id.leftimfan:
                finish();
                break;
            case R.id.yanzhengnew:
                if (this.phonenums.getText().toString().trim().length() <= 0) {
                    this.selfDialog = new SelfDialog(this);
                    this.selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                        public void onYesClick() {
                            GenghuanphoneActivity.this.selfDialog.dismiss();
                        }
                    });
                    this.selfDialog.show();
                    return;
                }
                if (this.cardsss.getText().toString().trim().length() <= 0) {
                    this.selfDialog = new SelfDialog(this);
                    this.selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                        public void onYesClick() {
                            GenghuanphoneActivity.this.selfDialog.dismiss();
                        }
                    });
                    this.selfDialog.show();
                    return;
                }
                postByGengHuan(ChangWeb.MYGENGHUANPHONE, this.phonenums.getText().toString().trim());
                break;
            case R.id.sendcard:
                this.sendcard.setFocusable(true);
                this.sendcard.setFocusableInTouchMode(true);
                this.sendcard.requestFocus();

                if (!isChinaPhoneLegal(this.phonenums.getText().toString().trim())) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    return;
                }

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
        }
    }

    public static boolean isChinaPhoneLegal(String paramString)
            throws PatternSyntaxException {
        return Pattern.compile("^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$").matcher(paramString).matches();
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\GenghuanphoneActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */