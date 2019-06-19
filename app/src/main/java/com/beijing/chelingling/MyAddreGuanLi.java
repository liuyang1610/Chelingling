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

import com.beijing.chelingling.adapter.MyAddresAdapter;
import com.beijing.chelingling.adapter.MyAddresAdapter.OnItemClickListener;
import com.beijing.chelingling.info.MyAddGunLiInfo;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyAddreGuanLi
        extends AppCompatActivity {
    private MyAddresAdapter addresAdapter;
    @Bind({R.id.bgs})
    ImageView bgs;
    private String flag;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                manager = new LinearLayoutManager(MyAddreGuanLi.this);
                addresAdapter = new MyAddresAdapter(MyAddreGuanLi.this, la, strs);
                infoshow.setLayoutManager(manager);
                infoshow.setAdapter(addresAdapter);
                addresAdapter.setItemClickListener(new MyAddresAdapter.OnItemClickListener() {
                    public void onItemClick(int paramAnonymous2Int) {
                        if (isdianji == true) {
                            Intent localIntent = new Intent();
                            localIntent.putExtra("bian", la.get(paramAnonymous2Int));
                            MyAddreGuanLi.this.setResult(4, localIntent);
                            MyAddreGuanLi.this.finish();
                        }
                    }
                });
            }
            return false;
        }
    });
    @Bind({R.id.infoshow})
    RecyclerView infoshow;
    private boolean isdianji;
    private ArrayList<MyAddGunLiInfo> la;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private LinearLayoutManager manager;
    @Bind({R.id.nodata})
    RelativeLayout nodata;
    @Bind({R.id.stattt})
    TextView stattt;
    private String[] strs;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;

    private void postByOkGo(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT).params("appid", this.strs[0], new boolean[0]).params("appsecret", this.strs[1], new boolean[0]).params("timestamp", Util.getTime(), new boolean[0]).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0]).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                Log.i("wandanle", "错误");
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("wandanle", "%" + paramAnonymousString);
                if (paramAnonymousString.length() > 5) {

                    Gson gson = new Gson();
                    try {
                        la = gson.fromJson(paramAnonymousString, new TypeToken<List<MyAddGunLiInfo>>() {
                        }.getType());

                        Message message = new Message();
                        message.arg1 = 0;
                        handler.sendMessage(message);

                    } catch (Exception e) {
                    }
                    return;
                }
                Message message = new Message();
                message.arg1 = 1;
                handler.sendMessage(message);
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_addre_guan_li);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);
        this.titles.setText("我的收货地址");
        this.strs = Util.load(this).split(",");
        this.flag = getIntent().getStringExtra("flag");
        if (this.flag.equals("1")) {
            this.isdianji = false;
            return;
        }
        this.isdianji = true;
    }

    protected void onResume() {
        super.onResume();
        postByOkGo(ChangWeb.MYADDLISTS);
    }

    @OnClick({R.id.leftimfan, R.id.stattt})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                break;
            case R.id.stattt:
                startActivity(new Intent(this, MyAddAddRessActivity.class));
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyAddreGuanLi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */