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
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.chelingling.MyLiJiZhiFuActivity;
import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MyDingDanInfo;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.squareup.picasso.Picasso;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Response;

public class MyDaiZhiFuAdapter
        extends RecyclerView.Adapter<MyDaiZhiFuAdapter.ViewHolder> {
    private int b;
    private Context context;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            MyDaiZhiFuAdapter.this.remen.remove(paramAnonymousMessage.arg1);
            MyDaiZhiFuAdapter.this.notifyItemRemoved(paramAnonymousMessage.arg1);
            MyDaiZhiFuAdapter.this.notifyItemRangeChanged(0, MyDaiZhiFuAdapter.this.remen.size());
            return false;
        }
    });
    private ArrayList<MyDingDanInfo> remen;
    private String[] strs;

    public MyDaiZhiFuAdapter(Context paramContext, ArrayList<MyDingDanInfo> paramLinkedList, String[] paramArrayOfString) {
        this.context = paramContext;
        this.remen = paramLinkedList;
        this.strs = paramArrayOfString;
    }

    private void postByOkGo(String paramString, int paramInt) {
        Log.i("liuyangdfd", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("id", ((MyDingDanInfo) this.remen.get(paramInt)).getDingdan_id(), new boolean[0])
                .params("price", ((MyDingDanInfo) this.remen.get(paramInt)).getPrice(), new boolean[0])
                .params("pay", "1", new boolean[0])
                .params("timestamp", Util.getTime(), new boolean[0])
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])
                .execute(new StringCallback() {
                    public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                        super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                        Log.i("liuyangdfd", "错误");
                    }

                    public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                        Log.i("liuyangdfd", paramAnonymousString);
                        try {
                            if (new JSONObject(paramAnonymousString).getString("code").equals("000")) {
                                Message message = new Message();
                                message.arg1 = 0;
                                handler.sendMessage(message);
                            }
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void postByOkGo(String paramString1, String paramString2, final int paramInt) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString1).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("dingdan_id", paramString2, new boolean[0])
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
                            if (new JSONObject(paramAnonymousString).getString("code").equals("000")) {
                                Message message = new Message();
                                message.arg1 = paramInt;
                                MyDaiZhiFuAdapter.this.handler.sendMessage(message);
                            }
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public int getItemCount() {
        return this.remen.size();
    }

    public void onBindViewHolder(final ViewHolder paramViewHolder, final int paramInt) {
        paramViewHolder.shangjia.setText(((MyDingDanInfo) this.remen.get(paramInt)).getTitle());
        paramViewHolder.xichenesname.setText(((MyDingDanInfo) this.remen.get(paramInt)).getTitle());
        paramViewHolder.xichecontent.setText(((MyDingDanInfo) this.remen.get(paramInt)).getDescription() + "");
        if (paramViewHolder.xichecontent.toString().equals("null")) {
            paramViewHolder.xichecontent.setText("");
        }
        Picasso.get().load(ChangWeb.YUMING + (remen.get(paramInt)).getPic()).into(paramViewHolder.newsicon);
        paramViewHolder.yingfumoney.setText("￥" + remen.get(paramInt).getPrices() + "");
        paramViewHolder.yingfumoney.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        paramViewHolder.shijimoney.setText("￥" + remen.get(paramInt).getPrice());
        paramViewHolder.shangpinnum.setText("共" + (remen.get(paramInt)).getNum() + "件商品，");
        paramViewHolder.shangpinjiaqian.setText("合计" + (remen.get(paramInt)).getPrice() + "元");
        paramViewHolder.numcontent.setText(remen.get(paramInt).getNum());
        this.b = Integer.parseInt(remen.get(paramInt).getPrice());
        paramViewHolder.jia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                int i = Integer.parseInt(paramViewHolder.numcontent.getText().toString()) + 1;
                paramViewHolder.numcontent.setText(i + "");
                if (i > 0) {
                    b = i * Integer.parseInt(remen.get(paramInt).getPrice());
                    paramViewHolder.shangpinnum.setText("共" + i + "件商品，");
                    paramViewHolder.shangpinjiaqian.setText("合计" + b + "元");
                }
            }
        });
        paramViewHolder.jian.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                int i = Integer.parseInt(paramViewHolder.numcontent.getText().toString()) - 1;
                Log.i("wandanlejinixngzhn", i + "   ss");
                if (i > 0) {
                    paramViewHolder.numcontent.setText(i + "");
                    Log.i("wandanlejinixngzhn", i + "  aaaaa");
                    b = i * Integer.parseInt(remen.get(paramInt).getPrice());
                    paramViewHolder.shangpinnum.setText("共" + i + "件商品，");
                    paramViewHolder.shangpinjiaqian.setText("合计" + b + "元");
                }
            }
        });
        paramViewHolder.gozhifu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                Log.i("liuyangdfd", "asdasdasf111111" + ((MyDingDanInfo) MyDaiZhiFuAdapter.this.remen.get(paramInt)).getPrice());
                MyDaiZhiFuAdapter.this.context.startActivity(new Intent(MyDaiZhiFuAdapter.this.context, MyLiJiZhiFuActivity.class).putExtra("jiages", ((MyDingDanInfo) MyDaiZhiFuAdapter.this.remen.get(paramInt)).getPrice()).putExtra("id", ((MyDingDanInfo) MyDaiZhiFuAdapter.this.remen.get(paramInt)).getDingdan_id()).putExtra("num", paramViewHolder.shangpinnum.getText().toString().trim()));
            }
        });
        paramViewHolder.deletedingdan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                MyDaiZhiFuAdapter.this.postByOkGo(ChangWeb.MYDELELSDINGDAN, ((MyDingDanInfo) MyDaiZhiFuAdapter.this.remen.get(paramInt)).getDingdan_id(), paramInt);
            }
        });
    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_daizhifu, null));
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {
        private TextView deletedingdan;
        private TextView gozhifu;
        private ImageView jia;
        private ImageView jian;
        private CircleTextImageView newsicon;
        private TextView numcontent;
        private TextView shangjia;
        private TextView shangpinjiaqian;
        private TextView shangpinnum;
        private TextView shijimoney;
        private TextView xichecontent;
        private TextView xichenesname;
        private TextView yingfumoney;

        public ViewHolder(View paramView) {
            super(paramView);
            this.jian = ((ImageView) paramView.findViewById(R.id.jian));
            this.jia = ((ImageView) paramView.findViewById(R.id.jia));
            this.newsicon = ((CircleTextImageView) paramView.findViewById(R.id.newsicon));
            this.numcontent = ((TextView) paramView.findViewById(R.id.numcontent));
            this.shangpinnum = ((TextView) paramView.findViewById(R.id.shangpinnum));
            this.shangjia = ((TextView) paramView.findViewById(R.id.shangjia));
            this.xichenesname = ((TextView) paramView.findViewById(R.id.xichenesname));
            this.xichecontent = ((TextView) paramView.findViewById(R.id.xichecontent));
            this.shijimoney = ((TextView) paramView.findViewById(R.id.shijimoney));
            this.shangpinjiaqian = ((TextView) paramView.findViewById(R.id.shangpinjiaqian));
            this.gozhifu = ((TextView) paramView.findViewById(R.id.gozhifu));
            this.deletedingdan = ((TextView) paramView.findViewById(R.id.deletedingdan));
            this.yingfumoney = ((TextView) paramView.findViewById(R.id.yingfumoney));
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyDaiZhiFuAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */