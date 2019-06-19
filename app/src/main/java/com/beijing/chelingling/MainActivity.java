package com.beijing.chelingling;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.yindao)
    ImageView yindao;
    @Bind(R.id.liji)
    TextView liji;
    @Bind(R.id.daojishi)
    TextView daojishi;

    String pic;
    private int i;
    private boolean isYou;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (msg.arg1 == 0) {
                if (isYou == true) {
                    startActivity(new Intent(MainActivity.this, ShouYeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(MainActivity.this, PhoneNumActivity.class));
                    finish();
                }
            }
            if (msg.arg1 == 1) {
                Picasso.get().load(ChangWeb.YUMING + pic).fit().into(yindao);
            }
            if (msg.arg1 == 2) {
                daojishi.setText("跳过 " + i);
                if (i == 0) {
                    if (isYou == true) {
                        startActivity(new Intent(MainActivity.this, ShouYeActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(MainActivity.this, PhoneNumActivity.class));
                        finish();
                    }
                }
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        try {
            String haha = Util.load(MainActivity.this);
            String k = haha.split(",")[1];
            Log.i("wojiubuxinle", k + "     asd");
            isYou = true;
            Log.i("nbvxcxz", "dasdas+++++" + isYou);
        } catch (Exception e) {
            isYou = false;
            Log.i("nbvxcxz", "dasdas%%%%%%%%%" + isYou);
        }

        getByOkGo(ChangWeb.MYYINDAOYE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (i = 5; i >= 0; i--) {
                    try {

                        Message message = new Message();
                        message.arg1 = 2;
                        handler.sendMessage(message);
                        Thread.sleep(1000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void getByOkGo(String url) {
        OkGo.get(url)                            // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("sadsadsadsa", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            pic = jsonObject.getString("pic");
                            Message message = new Message();
                            message.arg1 = 1;
                            handler.sendMessage(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return true;

    }

    @OnClick({R.id.liji, R.id.daojishi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.liji:
                i = -1;
                Message message = new Message();
                message.arg1 = 0;
                handler.sendMessage(message);
                break;
            case R.id.daojishi:
                i = -1;
                Message message1 = new Message();
                message1.arg1 = 0;
                handler.sendMessage(message1);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MainActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */