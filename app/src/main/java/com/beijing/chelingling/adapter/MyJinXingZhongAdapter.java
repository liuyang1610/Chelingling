package com.beijing.chelingling.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beijing.chelingling.MyChaKanChongZhiJiLuActivity;
import com.beijing.chelingling.MyJiaYouKaActivity;
import com.beijing.chelingling.MyLiJiZhiFuActivity;
import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MyDingDanInfo;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.picasso.Picasso;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Response;

public class MyJinXingZhongAdapter
        extends RecyclerView.Adapter<MyJinXingZhongAdapter.ViewHolder> {
    private Context context;
    private boolean isone;
    private ArrayList<MyDingDanInfo> remen;
    private String data;
    private String[] strs;
    private int posn;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1 == 1) {
                context.startActivity(new Intent(context, MyLiJiZhiFuActivity.class).putExtra("jiages", remen.get(posn).getPrice()).putExtra("id", data).putExtra("num", "1"));
            }
            return false;
        }
    });

    public MyJinXingZhongAdapter(Context paramContext, boolean paramBoolean, ArrayList<MyDingDanInfo> remen, String[] strs) {
        context = paramContext;
        isone = paramBoolean;
        this.remen = remen;
        this.strs = strs;
        Log.i("wandanlejinixngzhn", remen.size() + "  nvsdefef");
    }

    public int getItemCount() {
        Log.i("wandanlejinixngzhn", remen.size() + "  ada");
        return this.remen.size();

    }

    public void onBindViewHolder(ViewHolder paramViewHolder, final int paramInt) {

        paramViewHolder.chongzhijili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MyChaKanChongZhiJiLuActivity.class)
                        .putExtra("prices", remen.get(paramInt).getNum_price() + "")
                        .putExtra("num_cz", remen.get(paramInt).getNum_cz()));
            }
        });

        Log.i("dsfsdfg", "dfgdgfdg");
        paramViewHolder.zaicigoumai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posn = paramInt;
                postByOkGoJiaRuDingdan(ChangWeb.MYJIARUDINGDAN,remen.get(paramInt));
//                context.startActivity(new Intent(context, MyLiJiZhiFuActivity.class)
//                        .putExtra("jiages", remen.get(paramInt).getPrice())
//                        .putExtra("id", remen.get(paramInt).getDingdan_id())
//                        .putExtra("num", remen.get(paramInt).getNum()));
            }
        });

        paramViewHolder.shangjiaming.setText(remen.get(paramInt).getTitle());
        paramViewHolder.xichenesname.setText((remen.get(paramInt)).getTitle());
        paramViewHolder.xichecontent.setText((remen.get(paramInt)).getDescription() + "");
        Picasso.get().load(ChangWeb.YUMING + (remen.get(paramInt)).getPic()).into(paramViewHolder.newsicon);
        if (paramViewHolder.xichecontent.toString().trim().equals("null")) {
            paramViewHolder.xichecontent.setText("");
        }
        paramViewHolder.shijimoney.setText("￥" + remen.get(paramInt).getPrice());

        paramViewHolder.yingfumoney.setText("￥" + remen.get(paramInt).getPrices() + "");
        paramViewHolder.yingfumoney.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        paramViewHolder.shangpinnum.setText("共" + (remen.get(paramInt)).getNum() + "件商品，");
        paramViewHolder.shangpinjiaqian.setText("已支付" + (remen.get(paramInt)).getPrice() + "元");
        paramViewHolder.shuliang.setText("*" + (remen.get(paramInt)).getNum());

        paramViewHolder.timeshow.setText(Util.getDataString("yyyy年MM月dd日", Long.parseLong(remen.get(paramInt).getBtime() + "000")) + "充值");

        if (this.isone == true) {
            paramViewHolder.wanchengzhaungtai.setText("进行中");
        }
        paramViewHolder.wanchengzhaungtai.setText("已完成");

        if (remen.get(paramInt).getLx().equals("2")) {
            paramViewHolder.chongzhijili.setVisibility(View.VISIBLE);
        }


    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_jinxingzhong, paramViewGroup, false));
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {
        private CircleTextImageView newsicon;
        private TextView shangjiaming;
        private TextView shangpinjiaqian;
        private TextView shangpinnum;
        private TextView shijimoney;
        private TextView shuliang;
        private TextView wanchengzhaungtai;
        private TextView xichecontent;
        private TextView xichenesname;
        private TextView chongzhijili;
        private TextView zaicigoumai;
        private TextView timeshow;
        private TextView yingfumoney;

        public ViewHolder(View paramView) {
            super(paramView);
            this.wanchengzhaungtai = ((TextView) paramView.findViewById(R.id.wanchengzhaungtai));
            this.shangpinnum = ((TextView) paramView.findViewById(R.id.shangpinnum));
            this.shangjiaming = ((TextView) paramView.findViewById(R.id.shangjiaming));
            this.xichenesname = ((TextView) paramView.findViewById(R.id.xichenesname));
            this.xichecontent = ((TextView) paramView.findViewById(R.id.xichecontent));
            this.shijimoney = ((TextView) paramView.findViewById(R.id.shijimoney));
            this.shuliang = ((TextView) paramView.findViewById(R.id.shuliang));
            this.shangpinjiaqian = ((TextView) paramView.findViewById(R.id.shangpinjiaqian));
            this.chongzhijili = ((TextView) paramView.findViewById(R.id.chongzhijili));
            this.zaicigoumai = ((TextView) paramView.findViewById(R.id.zaicigoumai));
            this.timeshow = ((TextView) paramView.findViewById(R.id.timeshow));
            this.yingfumoney = ((TextView) paramView.findViewById(R.id.yingfumoney));
            this.newsicon = ((CircleTextImageView) paramView.findViewById(R.id.newsicon));
        }
    }

    private void postByOkGoJiaRuDingdan(String paramString, MyDingDanInfo myDingDanInfo) {
        OkGo.post(paramString).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("lx", myDingDanInfo.getLx(), new boolean[0])
                .params("card_id", myDingDanInfo.getCard_id(), new boolean[0])
                .params("name", myDingDanInfo.getAdd_name(), new boolean[0])
                .params("tel", myDingDanInfo.getAdd_tel(), new boolean[0])
                .params("add", myDingDanInfo.getAdd(), new boolean[0])
                .params("fapiao", myDingDanInfo.getFapiao(), new boolean[0])
                .params("cards", myDingDanInfo.getCards(), new boolean[0])
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
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyJinXingZhongAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */