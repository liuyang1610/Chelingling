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

import com.beijing.chelingling.info.MyAddGunLiInfo;
import com.beijing.chelingling.info.MyJiFenShangCheng;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyAddESSr
        extends AppCompatActivity {
    private String add;
    private String adds;
    @Bind({R.id.duihuanjage})
    TextView duihuanjage;
    @Bind({R.id.duihuas})
    TextView duihuas;
    @Bind({R.id.fgf})
    RelativeLayout fgf;
    @Bind({R.id.fgvf})
    RelativeLayout fgvf;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                Toast.makeText(MyAddESSr.this, "兑换成功", Toast.LENGTH_SHORT).show();
                MyAddESSr.this.finish();
            }
            if (paramAnonymousMessage.arg1 == 1) {
                Toast.makeText(MyAddESSr.this, "您的积分不足", Toast.LENGTH_SHORT).show();
            }
            if (paramAnonymousMessage.arg1 == 2) {
                MyAddESSr.this.shouhuorenname.setText(MyAddESSr.this.name);
                MyAddESSr.this.shouphone.setText(MyAddESSr.this.tel);
                MyAddESSr.this.shouhuorenaddress.setText(MyAddESSr.this.add + MyAddESSr.this.adds);
            }
            return false;
        }
    });
    @Bind({R.id.jifen})
    TextView jifen;
    @Bind({R.id.kuncun})
    TextView kuncun;
    @Bind({R.id.kuncunnum})
    TextView kuncunnum;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private MyJiFenShangCheng myJiFenShangCheng;
    private String name;
    @Bind({R.id.queren})
    TextView queren;
    @Bind({R.id.shangpinima})
    ImageView shangpinima;
    @Bind({R.id.shangpinname})
    TextView shangpinname;
    @Bind({R.id.shouhuorenaddress})
    TextView shouhuorenaddress;
    @Bind({R.id.shouhuorenname})
    TextView shouhuorenname;
    @Bind({R.id.shouphone})
    TextView shouphone;
    @Bind({R.id.sigone})
    TextView sigone;
    @Bind({R.id.sigtwo})
    TextView sigtwo;
    private String[] strs;
    private String tel;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.yidui})
    TextView yidui;
    @Bind({R.id.yiduinum})
    TextView yiduinum;

    private void postByOkGo(String paramString1, String paramString2) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1] + "     " + paramString2);
        ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) OkGo.post(paramString1).tag(this)).cacheKey("cachePostKey")).cacheMode(CacheMode.DEFAULT)).params("appid", this.strs[0], new boolean[0])).params("appsecret", this.strs[1], new boolean[0])).params("good_id", paramString2, new boolean[0])).params("add_name", this.shouhuorenname.getText().toString(), new boolean[0])).params("add_tel", this.shouphone.getText().toString().trim(), new boolean[0])).params("add", this.shouhuorenaddress.getText().toString().trim(), new boolean[0])).params("timestamp", Util.getTime(), new boolean[0])).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                Log.i("wandanle", "错误");
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("wandanle", paramAnonymousString);
                try {
                    JSONObject jsonObject = new JSONObject(paramAnonymousString);
                    if (jsonObject.getString("code").equals("000")) {
                        Message message = new Message();
                        message.arg1 = 0;
                        handler.sendMessage(message);
                    }
                    if (jsonObject.getString("code").equals("404")) {
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

    private void postByOkGoAdd(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) OkGo.post(paramString).tag(this)).cacheKey("cachePostKey")).cacheMode(CacheMode.DEFAULT)).params("appid", this.strs[0], new boolean[0])).params("appsecret", this.strs[1], new boolean[0])).params("timestamp", Util.getTime(), new boolean[0])).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                Log.i("wandanle", "错误");
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("wandanle", paramAnonymousString);
                try {
                    JSONObject jsonObject = new JSONObject(paramAnonymousString);
                    name = jsonObject.getString("name");
                    tel = jsonObject.getString("tel");
                    add = jsonObject.getString("add");
                    adds = jsonObject.getString("adds");
                    Message message = new Message();
                    message.arg1 = 2;
                    handler.sendMessage(message);
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if ((paramInt1 == 1) && (paramInt2 == 4)) {
            MyAddGunLiInfo myAddGunLiInfo = (MyAddGunLiInfo) paramIntent.getSerializableExtra("bian");
            this.shouhuorenname.setText(myAddGunLiInfo.getName());
            this.shouphone.setText(myAddGunLiInfo.getTel());
            this.shouhuorenaddress.setText(myAddGunLiInfo.getAdd() + myAddGunLiInfo.getAdds());
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_add_essr);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.titles.setText("兑换详情");
        this.strs = Util.load(this).split(",");
        postByOkGoAdd(ChangWeb.MYGETMORENDIZHI);
        this.myJiFenShangCheng = ((MyJiFenShangCheng) getIntent().getSerializableExtra("id"));
        this.shangpinname.setText(this.myJiFenShangCheng.getTitle());
        Picasso.get().load(ChangWeb.YUMING + this.myJiFenShangCheng.getPic()).into(this.shangpinima);
        this.duihuanjage.setText(this.myJiFenShangCheng.getIntegra());
        this.yiduinum.setText(this.myJiFenShangCheng.getNums());
        this.kuncunnum.setText(this.myJiFenShangCheng.getNum());
        this.jifen.setText(this.myJiFenShangCheng.getIntegra());
    }

    @OnClick({R.id.leftimfan, R.id.fgvf, R.id.fgf, R.id.queren})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.fgvf:
                startActivityForResult(new Intent(this, MyAddreGuanLi.class).putExtra("flag", "2"), 1);
                return;
            case R.id.fgf:
                startActivityForResult(new Intent(this, MyAddreGuanLi.class).putExtra("flag", "2"), 1);
                return;
            case R.id.queren:
                postByOkGo(ChangWeb.MYDUIHUANSHANGPIN, this.myJiFenShangCheng.getId());
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyAddESSr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */