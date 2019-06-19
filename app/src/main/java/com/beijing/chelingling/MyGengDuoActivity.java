package com.beijing.chelingling;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyGengDuoActivity
        extends AppCompatActivity {
    @Bind({R.id.anxindaijia})
    RelativeLayout anxindaijia;
    @Bind({R.id.bannewrs})
    Banner bannewrs;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            MyGengDuoActivity.this.bannewrs.setBannerStyle(1);
            MyGengDuoActivity.this.bannewrs.isAutoPlay(true);
            MyGengDuoActivity.this.bannewrs.setDelayTime(5000);
            MyGengDuoActivity.this.bannewrs.setIndicatorGravity(6);
            MyGengDuoActivity.this.bannewrs.setImages(MyGengDuoActivity.this.imagess, new Banner.OnLoadImageListener() {
                public void OnLoadImage(ImageView paramAnonymous2ImageView, Object paramAnonymous2Object) {
                    System.out.println("加载中");
                    Picasso.get().load((String) paramAnonymous2Object).into(paramAnonymous2ImageView);
                    System.out.println("加载完");
                }
            });
            return false;
        }
    });
    private List<String> imagess = new ArrayList();
    @Bind({R.id.jiayouchongzhi})
    RelativeLayout jiayouchongzhi;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.qichebaoxian})
    RelativeLayout qichebaoxian;
    @Bind({R.id.qichejinrong})
    RelativeLayout qichejinrong;
    @Bind({R.id.qichemeirong})
    RelativeLayout qichemeirong;
    @Bind({R.id.shigulipei})
    RelativeLayout shigulipei;
    private String[] strs;
    @Bind({R.id.tianjiagnegduo})
    RelativeLayout tianjiagnegduo;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.tu11})
    ImageView tu11;
    @Bind({R.id.tu12})
    ImageView tu12;
    @Bind({R.id.tu13})
    ImageView tu13;
    @Bind({R.id.tu14})
    ImageView tu14;
    @Bind({R.id.tu4})
    ImageView tu4;
    @Bind({R.id.tu5})
    ImageView tu5;
    @Bind({R.id.tu6})
    ImageView tu6;
    @Bind({R.id.tu7})
    ImageView tu7;
    @Bind({R.id.yidongchongdian})
    RelativeLayout yidongchongdian;

    private void postByOkGo(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) OkGo.post(paramString).tag(this)).cacheKey("cachePostKey")).cacheMode(CacheMode.DEFAULT)).params("appid", this.strs[0], new boolean[0])).params("appsecret", this.strs[1], new boolean[0])).params("timestamp", Util.getTime(), new boolean[0])).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("sadsadsadsa", paramAnonymousString);
                try {
                    JSONArray jsonArray = new JSONArray(paramAnonymousString);
                    int i = 0;
                    while (i < jsonArray.length()) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        imagess.add(ChangWeb.YUMING + jsonObject.getString("pic"));
                        i += 1;
                    }
                    Message message = new Message();
                    message.arg1 = 0;
                    MyGengDuoActivity.this.handler.sendMessage(message);
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_geng_duo);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.titles.setText("更多服务");
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        postByOkGo(ChangWeb.MYBANNERS);
        this.bannewrs.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            public void OnBannerClick(View paramAnonymousView, int paramAnonymousInt) {
            }
        });
    }

    @OnClick({R.id.leftimfan, R.id.jiayouchongzhi, R.id.qichebaoxian, R.id.shigulipei, R.id.qichejinrong, R.id.qichemeirong, R.id.anxindaijia, R.id.yidongchongdian, R.id.tianjiagnegduo})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.jiayouchongzhi:
                startActivity(new Intent(this, MyJiaYouKaActivity.class));
                return;
            case R.id.qichebaoxian:
                Toast.makeText(this, "即将上线", Toast.LENGTH_SHORT).show();
                return;
            case R.id.shigulipei:
                Toast.makeText(this, "即将上线", Toast.LENGTH_SHORT).show();
                return;
            case R.id.qichejinrong:
                Toast.makeText(this, "即将上线", Toast.LENGTH_SHORT).show();
                return;
            case R.id.qichemeirong:
                Toast.makeText(this, "即将上线", Toast.LENGTH_SHORT).show();
                return;
            case R.id.anxindaijia:
                Toast.makeText(this, "即将上线", Toast.LENGTH_SHORT).show();
                return;
            case R.id.yidongchongdian:
                Toast.makeText(this, "即将上线", Toast.LENGTH_SHORT).show();
                return;
            case R.id.tianjiagnegduo:
                Toast.makeText(this, "即将上线", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyGengDuoActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */