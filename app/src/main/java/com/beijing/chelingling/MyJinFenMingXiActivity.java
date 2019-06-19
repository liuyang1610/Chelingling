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

import com.beijing.chelingling.adapter.MyJiFenMingAdapter;
import com.beijing.chelingling.info.MyJiFenShangCheng;
import com.beijing.chelingling.info.MyJoFenMingXiInfo;
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

public class MyJinFenMingXiActivity
        extends AppCompatActivity {
    private MyJiFenMingAdapter adapter;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (la.size() > 1) {
                nodata.setVisibility(View.GONE);
                MyJinFenMingXiActivity.this.mingxirecy.setVisibility(View.VISIBLE);
                adapter = new MyJiFenMingAdapter(MyJinFenMingXiActivity.this, MyJinFenMingXiActivity.this.la);
                MyJinFenMingXiActivity.this.mingxirecy.setLayoutManager(MyJinFenMingXiActivity.this.manager);
                MyJinFenMingXiActivity.this.mingxirecy.setAdapter(MyJinFenMingXiActivity.this.adapter);
                return false;
            }
            MyJinFenMingXiActivity.this.nodata.setVisibility(View.VISIBLE);
            MyJinFenMingXiActivity.this.mingxirecy.setVisibility(View.GONE);
            return false;
        }
    });
    private ArrayList<MyJoFenMingXiInfo> la;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private LinearLayoutManager manager;
    @Bind({R.id.mingxirecy})
    RecyclerView mingxirecy;
    @Bind({R.id.nodata})
    RelativeLayout nodata;
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
                Log.i("asdsadsa", paramAnonymousString);

                Gson gson = new Gson();

                la = gson.fromJson(paramAnonymousString, new TypeToken<List<MyJoFenMingXiInfo>>() {
                }.getType());

                Message message = new Message();
                message.arg1 = 0;
                handler.sendMessage(message);
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_jin_fen_ming_xi);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.titles.setText("积分明细");
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        this.manager = new LinearLayoutManager(this);
        postByOkGo(ChangWeb.MYJIFENMINGXI);
    }

    @OnClick({R.id.leftimfan})
    public void onViewClicked() {
        finish();
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyJinFenMingXiActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */