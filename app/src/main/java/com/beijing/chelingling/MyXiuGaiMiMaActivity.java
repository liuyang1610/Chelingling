package com.beijing.chelingling;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyXiuGaiMiMaActivity extends AppCompatActivity {

    @Bind(R.id.leftimfan)
    ImageView leftimfan;
    @Bind(R.id.titles)
    TextView titles;
    @Bind(R.id.titlke)
    RelativeLayout titlke;
    @Bind(R.id.qian)
    ImageView qian;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.phonenums)
    EditText phonenums;
    @Bind(R.id.sendcard)
    Button sendcard;
    @Bind(R.id.qians)
    ImageView qians;
    @Bind(R.id.card)
    TextView card;
    @Bind(R.id.cardsss)
    EditText cardsss;
    @Bind(R.id.yanzhengnew)
    TextView yanzhengnew;

    private String[] strs;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1 == 0) {
                Toast.makeText(MyXiuGaiMiMaActivity.this, "更换成功", Toast.LENGTH_SHORT).show();
                finish();
            } else if (msg.arg1 == 1) {
                Toast.makeText(MyXiuGaiMiMaActivity.this, "更换失败", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_xiu_gai_mi_ma);
        ButterKnife.bind(this);

        String s = Util.load(this);
        this.strs = s.split(",");
    }

    @OnClick({R.id.leftimfan, R.id.yanzhengnew})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leftimfan:
                finish();
                break;
            case R.id.yanzhengnew:
                if (phonenums.getText().toString().trim().length()>=6 && phonenums.getText().toString().trim().length()<=12) {
                    if (Util.isContainAll(phonenums.getText().toString().trim())) {
                        if (phonenums.getText().toString().trim().equals(cardsss.getText().toString().trim())) {
                            postByGengHuan(ChangWeb.MYXIUGAIMIMA, cardsss.getText().toString().trim());
                        } else {
                            Toast.makeText(MyXiuGaiMiMaActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MyXiuGaiMiMaActivity.this, "至少包含字母与数字", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "请输6-12位数密码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void postByGengHuan(String paramString1, String password) {
        OkGo.post(paramString1).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT).params("appid", this.strs[0], new boolean[0]).params("appsecret", this.strs[1], new boolean[0]).params("password", password, new boolean[0]).params("timestamp", Util.getTime(), new boolean[0]).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0]).execute(new StringCallback() {
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
                    if (paramAnonymousString.equals("400")) {
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
}
