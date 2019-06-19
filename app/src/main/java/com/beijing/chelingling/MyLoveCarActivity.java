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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.chelingling.adapter.MyLoveCarListAdapter;
import com.beijing.chelingling.info.MyJiFenShangCheng;
import com.beijing.chelingling.info.MyLoveCarLInfo;
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

public class MyLoveCarActivity
        extends AppCompatActivity {
    private MyLoveCarListAdapter adapter;
    @Bind({R.id.addcar})
    TextView addcar;
    @Bind({R.id.bgs})
    ImageView bgs;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                MyLoveCarActivity.this.lovecarrecycler.setVisibility(View.VISIBLE);
                MyLoveCarActivity.this.hasfsdf.setVisibility(View.GONE);

                adapter = new MyLoveCarListAdapter(MyLoveCarActivity.this, MyLoveCarActivity.this.la, MyLoveCarActivity.this.strs);

                MyLoveCarActivity.this.lovecarrecycler.setLayoutManager(MyLoveCarActivity.this.manager);
                MyLoveCarActivity.this.lovecarrecycler.setAdapter(MyLoveCarActivity.this.adapter);
            }
            if (paramAnonymousMessage.arg1 == 1) {
                MyLoveCarActivity.this.lovecarrecycler.setVisibility(View.GONE);
                MyLoveCarActivity.this.hasfsdf.setVisibility(View.VISIBLE);
            }
            return false;
        }
    });
    @Bind({R.id.hasfsdf})
    RelativeLayout hasfsdf;
    private ArrayList<MyLoveCarLInfo> la;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.lovecarrecycler})
    RecyclerView lovecarrecycler;
    private LinearLayoutManager manager;
    @Bind({R.id.safds})
    RelativeLayout safds;
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
                            if (paramAnonymousString.length() > 5) {

                                Gson gson = new Gson();

                                la = gson.fromJson(paramAnonymousString, new TypeToken<List<MyLoveCarLInfo>>() {
                                }.getType());

                                Message message = new Message();
                                message.arg1 = 0;
                                handler.sendMessage(message);
                                return;
                            }
                            Message message = new Message();
                            message.arg1 = 1;
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
        setContentView(R.layout.activity_my_love_car);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);
        String s = Util.load(this);
        this.titles.setText("我的爱车");
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        //postByOkGo(ChangWeb.MYAICARLIST);
        this.manager = new LinearLayoutManager(this);
    }

    protected void onResume() {
        super.onResume();
        postByOkGo(ChangWeb.MYAICARLIST);
    }

    @OnClick({R.id.leftimfan, R.id.addcar})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.addcar:
                startActivity(new Intent(this, MyAddLoveCar.class));
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyLoveCarActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */