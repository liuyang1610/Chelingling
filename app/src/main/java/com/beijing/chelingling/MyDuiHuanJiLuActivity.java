package com.beijing.chelingling;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.chelingling.adapter.MyDuiHuanAdapter;
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

public class MyDuiHuanJiLuActivity
        extends AppCompatActivity {
    private MyDuiHuanAdapter adapter;
    @Bind({R.id.duihuajyesre})
    RecyclerView duihuajyesre;
    @Bind({R.id.duihuanwu})
    ImageView duihuanwu;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            try {
                if (MyDuiHuanJiLuActivity.this.la.size() > 0) {
                    MyDuiHuanJiLuActivity.this.duihuajyesre.setVisibility(View.VISIBLE);
                    MyDuiHuanJiLuActivity.this.duihuanwu.setVisibility(View.GONE);
                    manager = new LinearLayoutManager(MyDuiHuanJiLuActivity.this);
                    adapter = new MyDuiHuanAdapter(MyDuiHuanJiLuActivity.this, false, la, MyDuiHuanJiLuActivity.this.strs);
                    MyDuiHuanJiLuActivity.this.duihuajyesre.setLayoutManager(MyDuiHuanJiLuActivity.this.manager);
                    MyDuiHuanJiLuActivity.this.duihuajyesre.setAdapter(MyDuiHuanJiLuActivity.this.adapter);
                }
                return false;
            } catch (Exception e) {
                MyDuiHuanJiLuActivity.this.duihuajyesre.setVisibility(View.GONE);
                MyDuiHuanJiLuActivity.this.duihuanwu.setVisibility(View.VISIBLE);
            }
            return false;
        }
    });
    private ArrayList<MyJiFenShangCheng> la;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private LinearLayoutManager manager;
    private String[] strs;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;

    private void postByOkGo(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) OkGo.post(paramString).tag(this)).cacheKey("cachePostKey")).cacheMode(CacheMode.DEFAULT)).params("appid", this.strs[0], new boolean[0])).params("appsecret", this.strs[1], new boolean[0])).params("timestamp", Util.getTime(), new boolean[0])).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                Log.i("wandanle", "错误");
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("wandanle", paramAnonymousString);

                Gson gson = new Gson();
                la = gson.fromJson(paramAnonymousString, new TypeToken<List<MyJiFenShangCheng>>() {
                }.getType());
                Message message = new Message();
                message.arg1 = 0;
                handler.sendMessage(message);
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_dui_huan_ji_lu);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.titles.setText("兑换记录");
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        postByOkGo(ChangWeb.MYJIFENDUIHUANJILU);
    }

    @OnClick({R.id.leftimfan})
    public void onViewClicked() {
        finish();
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyDuiHuanJiLuActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */