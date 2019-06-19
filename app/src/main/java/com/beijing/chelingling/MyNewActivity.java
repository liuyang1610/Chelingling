package com.beijing.chelingling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.chelingling.adapter.MyNesAdapter;
import com.beijing.chelingling.adapter.MyNesAdapter.OnItemClickListener;
import com.beijing.chelingling.info.AnyEventType;
import com.beijing.chelingling.info.MyAddGunLiInfo;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.MyNewInfo;
import com.beijing.chelingling.unit.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyNewActivity
        extends AppCompatActivity {
    public static final String KEY_EXTRAS = "extras";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TITLE = "title";
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static boolean isForeground = false;
    private MyNesAdapter adapter;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                newrecycler.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
                adapter = new MyNesAdapter(MyNewActivity.this, MyNewActivity.this.remen);
                linearLayoutManager = new LinearLayoutManager(MyNewActivity.this);
                newrecycler.setLayoutManager(MyNewActivity.this.linearLayoutManager);
                newrecycler.setAdapter(MyNewActivity.this.adapter);
                adapter.setItemClickListener(new MyNesAdapter.OnItemClickListener() {
                    public void onItemClick(int paramAnonymous2Int) {
                        String str = ChangWeb.MYXITONGTONGZHI + ((MyNewInfo) MyNewActivity.this.remen.get(paramAnonymous2Int)).getId() + ".html";
                        Log.i("vfdsddhb", str);
                        try {
                            if (((MyNewInfo) MyNewActivity.this.remen.get(paramAnonymous2Int)).getUrl().equals("null")) {
                                MyNewActivity.this.startActivity(new Intent(MyNewActivity.this, MyWebActivity.class).putExtra("url", str));
                                return;
                            }
                            MyNewActivity.this.startActivity(new Intent(MyNewActivity.this, MyWebActivity.class).putExtra("url", "http://" + ((MyNewInfo) MyNewActivity.this.remen.get(paramAnonymous2Int)).getUrl()));
                            return;
                        } catch (Exception localException) {
                            MyNewActivity.this.startActivity(new Intent(MyNewActivity.this, MyWebActivity.class).putExtra("url", str));
                        }
                    }
                });
            }
            while (paramAnonymousMessage.arg1 != 1) {
                return false;
            }
            MyNewActivity.this.newrecycler.setVisibility(View.GONE);
            MyNewActivity.this.nodata.setVisibility(View.VISIBLE);
            return false;
        }
    });
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private LinearLayoutManager linearLayoutManager;
    @Bind({R.id.newrecycler})
    RecyclerView newrecycler;
    @Bind({R.id.nodata})
    RelativeLayout nodata;
    private ArrayList<MyNewInfo> remen;
    private String[] strs;
    @Bind({R.id.titles})
    TextView titles;

    private void postByOkGoIcon(String paramString) {
        Log.i("mvbnbvnvb", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("timestamp", Util.getTime(), new boolean[0])
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])
                .execute(new StringCallback() {
                    public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                        super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                        Log.i("mvbnbvLKJHGFnvb", "错误");
                    }

                    public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                        Log.i("mvbnbvnvb", paramAnonymousString);
                        if (paramAnonymousString.length() < 5) {
                            Message message = new Message();
                            message.arg1 = 1;
                            handler.sendMessage(message);
                            return;
                        }

                        Gson gson = new Gson();
                        try {
                            remen = gson.fromJson(paramAnonymousString, new TypeToken<List<MyNewInfo>>() {
                            }.getType());
                            Message message = new Message();
                            message.arg1 = 0;
                            handler.sendMessage(message);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_new);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        EventBus.getDefault().post(new AnyEventType(0));

        SharedPreferences.Editor editor = getSharedPreferences("lock", Context.MODE_PRIVATE).edit();
        editor.putInt("code", 0);
        editor.commit();
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        this.titles.setText("消息");
        postByOkGoIcon(ChangWeb.MYNEWS);
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if (paramInt == 4) {
            finish();
            //overridePendingTransition(R.anim.out_to_left, R.anim.out_to_right);
        }
        return true;
    }

    @OnClick({R.id.leftimfan})
    public void onViewClicked() {
        finish();
        //overridePendingTransition(R.anim.out_to_left, R.anim.out_to_right);
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyNewActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */