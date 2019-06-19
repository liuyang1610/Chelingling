package com.beijing.chelingling;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.adapter.MyTaoCanAdapter;
import com.beijing.chelingling.adapter.MyTaoCanAdapter.OnItemClickListener;
import com.beijing.chelingling.info.MyAddGunLiInfo;
import com.beijing.chelingling.info.MyjiaYoutaoCan;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyJiaYouKaActivity
        extends AppCompatActivity {
    private String card_id = "";
    @Bind({R.id.chongzhifangshiim})
    ImageView chongzhifangshiim;
    @Bind({R.id.chongzhifangshite})
    TextView chongzhifangshite;
    private String chongzhitype = "";
    private String data;
    private AlertDialog dialog;
    @Bind({R.id.dingdanjine})
    TextView dingdanjine;
    private String fapiao = "";
    @Bind({R.id.gouxuan})
    ImageView gouxuan;
    @Bind({R.id.gouxuand})
    TextView gouxuand;

    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                MyJiaYouKaActivity.this.taocannum.setText("套餐1");
                MyJiaYouKaActivity.this.dingdanjine.setText("￥" + la.get(0).getPricea());
                int i = Integer.parseInt(la.get(0).getPricea());
                int j = Integer.parseInt(la.get(0).getPrice());
                MyJiaYouKaActivity.this.taocanyouhui.setText("￥" + (i - j));
                MyJiaYouKaActivity.this.zhifujine.setText("￥" + la.get(0).getPrice());
                card_id = la.get(0).getId();

            }
            if (paramAnonymousMessage.arg1 == 1) {
                startActivity(new Intent(MyJiaYouKaActivity.this, MyLiJiZhiFuActivity.class).putExtra("jiages", zhifujine.getText().toString().substring(1)).putExtra("id", data).putExtra("num", "1"));
            }
            return false;
        }
    });

    private boolean isjiayoukatrue = false;
    private boolean istrue = true;
    private boolean isxianshangtrue = false;
    private String kahao = "";
    private ArrayList<MyjiaYoutaoCan> la;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.lijichongzhi})
    Button lijichongzhi;
    @Bind({R.id.sda})
    ImageView sda;
    private String shoujihao = "";
    private String[] strs;
    @Bind({R.id.taocannum})
    TextView taocannum;
    @Bind({R.id.taocanyouhui})
    TextView taocanyouhui;
    @Bind({R.id.tii})
    ImageView tii;
    @Bind({R.id.tii1})
    ImageView tii1;
    @Bind({R.id.tii2})
    ImageView tii2;
    @Bind({R.id.tii3})
    ImageView tii3;
    @Bind({R.id.tii4})
    ImageView tii4;
    @Bind({R.id.tisf})
    ImageView tisf;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.xianshangchongzhid})
    RelativeLayout xianshangchongzhid;
    private String xingming = "";
    @Bind({R.id.xuanzetaocan})
    ImageView xuanzetaocan;
    @Bind({R.id.xuanzetaocand})
    RelativeLayout xuanzetaocand;
    @Bind({R.id.xuanzetaotext})
    TextView xuanzetaotext;
    @Bind({R.id.xuanzezhfuim})
    ImageView xuanzezhfuim;
    @Bind({R.id.xuanzezhfute})
    TextView xuanzezhfute;
    private String youjidizhi = "";
    @Bind({R.id.youjikachongzhid})
    RelativeLayout youjikachongzhid;
    @Bind({R.id.zhhifuchenggongim})
    ImageView zhhifuchenggongim;
    @Bind({R.id.zhhifuchenggongte})
    TextView zhhifuchenggongte;
    @Bind({R.id.zhifujine})
    TextView zhifujine;

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

                        Gson gson = new Gson();

                        la = gson.fromJson(paramAnonymousString, new TypeToken<List<MyjiaYoutaoCan>>() {
                        }.getType());

                        Message message = new Message();
                        message.arg1 = 0;
                        handler.sendMessage(message);
                    }
                });
    }

    private void postByOkGoJiaRuDingdan(String paramString) {
        OkGo.post(paramString).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("lx", this.chongzhitype, new boolean[0])
                .params("card_id", this.card_id, new boolean[0])
                .params("name", this.xingming, new boolean[0])
                .params("tel", this.shoujihao, new boolean[0])
                .params("add", this.youjidizhi, new boolean[0])
                .params("fapiao", this.fapiao, new boolean[0])
                .params("cards", this.kahao, new boolean[0])
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
                            JSONObject jsonObject = new JSONObject(paramAnonymousString);
                            if (jsonObject.getString("code").equals("000")) {
                                data = jsonObject.getString("data");
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

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if ((paramInt1 == 1) && (paramInt2 == 4)) {
            this.kahao = paramIntent.getStringExtra("kahao");
            this.xingming = paramIntent.getStringExtra("xingming");
            this.shoujihao = paramIntent.getStringExtra("shoujihao");
            Log.i("sadsad", this.kahao + "           " + this.xingming);
            if ((!this.kahao.equals("")) && (!this.xingming.equals("")) && (!this.shoujihao.equals(""))) {
                this.isxianshangtrue = true;
            }
        }
        if ((paramInt1 == 2) && (paramInt2 == 5)) {
            this.xingming = paramIntent.getStringExtra("shoujianname");
            this.shoujihao = paramIntent.getStringExtra("shoujihao");
            this.youjidizhi = paramIntent.getStringExtra("youjidizhi");
            this.fapiao = paramIntent.getStringExtra("fapiao");
            Log.i("sadsad", this.shoujihao + "           " + this.youjidizhi + "           " + this.xingming + "     " + this.fapiao);
            if ((!this.xingming.equals("")) && (!this.shoujihao.equals("")) && (!this.youjidizhi.equals("")) && (!this.fapiao.equals(""))) {
                this.isjiayoukatrue = true;
            }
        }
        if ((paramInt1 == 3) && (paramInt2 == 6)) {
            this.istrue = true;
            this.gouxuan.setBackgroundResource(R.drawable.gouxuantrue);
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_jia_you_ka);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);
        this.titles.setText("加油卡充值");
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        postByOkGo(ChangWeb.MYJIAYOUKATAOCAN);
    }

    @OnClick({R.id.gouxuan, R.id.leftimfan, R.id.xianshangchongzhid, R.id.youjikachongzhid, R.id.xuanzetaocand, R.id.gouxuand, R.id.lijichongzhi})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.gouxuan:
                if (this.istrue == true) {
                    this.istrue = false;
                    this.gouxuan.setBackgroundResource(R.drawable.gouxuanfalse);
                    return;
                }
                this.istrue = true;
                this.gouxuan.setBackgroundResource(R.drawable.gouxuantrue);
                return;
            case R.id.xianshangchongzhid:
                this.xuanzetaocan.setBackgroundResource(R.drawable.icon_71);
                this.chongzhitype = "2";
                this.xianshangchongzhid.setBackgroundResource(R.drawable.blueline);
                this.youjikachongzhid.setBackgroundResource(R.drawable.whitgline);
                startActivityForResult(new Intent(this, MyXianShangJuTiActivity.class).putExtra("kahao", this.kahao).putExtra("xingming", this.xingming).putExtra("shoujihao", this.shoujihao), 1);
                return;
            case R.id.youjikachongzhid:
                this.xuanzetaocan.setBackgroundResource(R.drawable.icon_71);
                this.chongzhitype = "1";
                this.youjikachongzhid.setBackgroundResource(R.drawable.blueline);
                this.xianshangchongzhid.setBackgroundResource(R.drawable.whitgline);
                startActivityForResult(new Intent(this, MyYouJiChongActivity.class).putExtra("xingming", this.xingming).putExtra("shoujihao", this.shoujihao).putExtra("youjidizhi", this.youjidizhi).putExtra("fapiao", this.fapiao), 2);
                return;
            case R.id.xuanzetaocand:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                Object localObject = View.inflate(this, R.layout.xuanzetaocanalert, null);
                alertDialog.setView((View) localObject);
                alertDialog.setCancelable(true);
                localObject = (RecyclerView) ((View) localObject).findViewById(R.id.xuanzerecycle);
                LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(this);
                MyTaoCanAdapter localMyTaoCanAdapter = new MyTaoCanAdapter(this, this.la);
                ((RecyclerView) localObject).setLayoutManager(localLinearLayoutManager);
                ((RecyclerView) localObject).setAdapter(localMyTaoCanAdapter);
                localMyTaoCanAdapter.setItemClickListener(new MyTaoCanAdapter.OnItemClickListener() {
                    public void onItemClick(int paramAnonymousInt) {
                        MyJiaYouKaActivity.this.taocannum.setText("套餐" + (paramAnonymousInt + 1));
                        MyJiaYouKaActivity.this.dingdanjine.setText("￥" + ((MyjiaYoutaoCan) MyJiaYouKaActivity.this.la.get(paramAnonymousInt)).getPricea());
                        double i = Double.parseDouble(la.get(paramAnonymousInt).getPricea());
                        double j = Double.parseDouble(la.get(paramAnonymousInt).getPrice());
                        MyJiaYouKaActivity.this.taocanyouhui.setText("￥" + (i - j));
                        MyJiaYouKaActivity.this.zhifujine.setText("￥" + (la.get(paramAnonymousInt)).getPrice());
                        card_id = la.get(paramAnonymousInt).getId();
                        MyJiaYouKaActivity.this.xuanzetaocan.setBackgroundResource(R.drawable.icon_71);
                        MyJiaYouKaActivity.this.dialog.dismiss();
                    }
                });
                this.dialog = alertDialog.create();
                this.dialog.show();
                return;
            case R.id.gouxuand:
                startActivityForResult(new Intent(this, MyXieYiActivity.class), 3);
                return;
            case R.id.lijichongzhi:
                if (this.istrue == true) {
                    if (this.chongzhitype.equals("")) {
                        Toast.makeText(this, "请选择充值类型", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (this.card_id.equals("")) {
                        Toast.makeText(this, "请选择套餐类型", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ((this.isxianshangtrue == true) || (this.isjiayoukatrue == true)) {
                        postByOkGoJiaRuDingdan(ChangWeb.MYJIARUDINGDAN);
                        return;
                    }
                    Toast.makeText(this, "请完善卡号等信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "请同意《加油卡协议》", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyJiaYouKaActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */