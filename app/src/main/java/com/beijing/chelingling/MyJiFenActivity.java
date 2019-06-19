package com.beijing.chelingling;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.adapter.MyDuiHuanAdapter;
import com.beijing.chelingling.info.MyDingDanInfo;
import com.beijing.chelingling.info.MyJiFenShangCheng;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyJiFenActivity
        extends AppCompatActivity {
    private MyDuiHuanAdapter adapter;
    @Bind({R.id.duihuanjilu})
    TextView duihuanjilu;
    @Bind({R.id.duihuanrecycler})
    RecyclerView duihuanrecycler;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                try {
                    if (MyJiFenActivity.this.la.size() >= 0) {
                        manager = new LinearLayoutManager(MyJiFenActivity.this);
                        adapter = new MyDuiHuanAdapter(MyJiFenActivity.this, true, MyJiFenActivity.this.la, MyJiFenActivity.this.strs);
                        MyJiFenActivity.this.duihuanrecycler.setLayoutManager(MyJiFenActivity.this.manager);
                        MyJiFenActivity.this.duihuanrecycler.setAdapter(MyJiFenActivity.this.adapter);
                    }
                    return false;
                } catch (Exception e) {
                    Toast.makeText(MyJiFenActivity.this, "无获取数据", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            Toast.makeText(MyJiFenActivity.this, "无获取数据", Toast.LENGTH_SHORT).show();
            return false;
        }
    });
    private String integra;
    @Bind({R.id.jifenmingxi})
    TextView jifenmingxi;
    @Bind({R.id.jifennum})
    TextView jifennum;
    private ArrayList<MyJiFenShangCheng> la;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private LinearLayoutManager manager;
    private String[] strs;
    @Bind({R.id.titles})
    TextView titles;

    private void postByOkGo(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
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
                        try {

                            Gson gson = new Gson();

                            la = gson.fromJson(paramAnonymousString, new TypeToken<List<MyJiFenShangCheng>>() {
                            }.getType());

                            Message message = new Message();
                            message.arg1 = 0;
                            handler.sendMessage(message);
                            return;
                        } catch (Exception e) {
                            Message message = new Message();
                            message.arg1 = 1;
                            handler.sendMessage(message);
                        }
                    }
                });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_ji_fen);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        this.integra = getIntent().getStringExtra("integra");
        this.jifennum.setText(this.integra);
        this.titles.setText("我的积分");
        postByOkGo(ChangWeb.MYJIFENSHANGPIN);
    }

    @OnClick({R.id.leftimfan, R.id.jifenmingxi, R.id.duihuanjilu})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            case R.id.titles:
            case R.id.jifennum:
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.jifenmingxi:
                startActivity(new Intent(this, MyJinFenMingXiActivity.class));
                return;
            case R.id.duihuanjilu:
                startActivity(new Intent(this, MyDuiHuanJiLuActivity.class));
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyJiFenActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */