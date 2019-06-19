package com.beijing.chelingling.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.chelingling.MyXiuGaiAddActivity;
import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MyAddGunLiInfo;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.MyQueDingDialog;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Response;

public class MyAddresAdapter
        extends RecyclerView.Adapter<MyAddresAdapter.ViewHolder>
        implements View.OnClickListener {
    private int a = 4;
    private Context context;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            la.remove(paramAnonymousMessage.arg1);
            notifyItemRemoved(paramAnonymousMessage.arg1);
            notifyItemRangeChanged(paramAnonymousMessage.arg1, MyAddresAdapter.this.la.size() - paramAnonymousMessage.arg1);
            return false;
        }
    });
    private Handler handler1 = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            int i = 0;
            while (i < MyAddresAdapter.this.la.size()) {
                la.get(i).setZt("0");
                i += 1;
            }
            la.get(paramAnonymousMessage.arg1).setZt("1");
            notifyDataSetChanged();
            return false;
        }
    });
    private ArrayList<MyAddGunLiInfo> la;
    private OnItemClickListener mItemClickListener;
    private MyQueDingDialog myQueDingDialog;
    private String[] strs;

    public MyAddresAdapter(Context paramContext, ArrayList<MyAddGunLiInfo> paramLinkedList, String[] paramArrayOfString) {
        this.context = paramContext;
        this.la = paramLinkedList;
        this.strs = paramArrayOfString;
    }

    private void postByOkGo(String paramString1, String paramString2, final int paramInt) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString1).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("id", paramString2, new boolean[0])
                .params("timestamp", Util.getTime(), new boolean[0])
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])
                .execute(new StringCallback() {
                    public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                        super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                        Log.i("wandanle", "错误");
                    }

                    public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                        Log.i("wandanle", "%" + paramAnonymousString);
                        try {
                            if (new JSONObject(paramAnonymousString).getString("code").equals("000")) {
                                Message message = new Message();
                                message.arg1 = paramInt;
                                handler.sendMessage(message);
                                return;
                            }
                            Message message = new Message();
                            message.arg1 = 1;
                            handler.sendMessage(message);
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void postByOkGoXGiuaiu(String paramString1, String paramString2, final int paramInt) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString1).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("id", paramString2, new boolean[0])
                .params("timestamp", Util.getTime(), new boolean[0])
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])
                .execute(new StringCallback() {
                    public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                        super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                        Log.i("wandanle", "错误");
                    }

                    public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                        Log.i("wandanle", "%" + paramAnonymousString);
                        try {
                            if (new JSONObject(paramAnonymousString).getString("code").equals("000")) {
                                Message message = new Message();
                                message.arg1 = paramInt;
                                handler1.sendMessage(message);
                                return;
                            }
                            Message message = new Message();
                            message.arg1 = paramInt;
                            handler1.sendMessage(message);
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public int getItemCount() {
        return la.size();
    }

    public void onBindViewHolder(ViewHolder paramViewHolder, final int paramInt) {
        paramViewHolder.shouhuorenname.setText(la.get(paramInt).getName());
        paramViewHolder.shouphone.setText(la.get(paramInt).getTel());
        paramViewHolder.shouhuorenaddress.setText(la.get(paramInt).getAdd() + la.get(paramInt).getAdds());
        if (la.get(paramInt).getZt().equals("0")) {
            paramViewHolder.pinganbaojia.setBackgroundResource(R.drawable.icon_check);
            paramViewHolder.sigthree.setText("设为默认");
        }

        paramViewHolder.delete_ivshan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                myQueDingDialog = new MyQueDingDialog(MyAddresAdapter.this.context);
                MyAddresAdapter.this.myQueDingDialog.setTitle("确认要删除该地址吗");
                MyAddresAdapter.this.myQueDingDialog.setYesOnclickListener("确定", new MyQueDingDialog.onYesOnclickListener() {
                    public void onYesClick() {
                        postByOkGo(ChangWeb.MYADDDELADDS, la.get(paramInt).getId(), paramInt);
                        MyAddresAdapter.this.myQueDingDialog.dismiss();
                    }
                });
                MyAddresAdapter.this.myQueDingDialog.setNoOnclickListener("取消", new MyQueDingDialog.onNoOnclickListener() {
                    public void onNoClick() {
                        MyAddresAdapter.this.myQueDingDialog.dismiss();
                    }
                });
                MyAddresAdapter.this.myQueDingDialog.show();
            }
        });
        paramViewHolder.bianjis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                context.startActivity(new Intent(MyAddresAdapter.this.context, MyXiuGaiAddActivity.class).putExtra("data", (Serializable) MyAddresAdapter.this.la.get(paramInt)));
            }
        });
        paramViewHolder.pinganbaojia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                postByOkGoXGiuaiu(ChangWeb.MYXIUGAIMORENDIZHI, ((MyAddGunLiInfo) MyAddresAdapter.this.la.get(paramInt)).getId(), paramInt);
            }
        });
        paramViewHolder.itemView.setTag(Integer.valueOf(paramInt));
        if (la.get(paramInt).getZt().equals("1")) {
            paramViewHolder.pinganbaojia.setBackgroundResource(R.drawable.icon_checked);
            paramViewHolder.sigthree.setText("默认地址");
        }

    }

    public void onClick(View paramView) {
        if (this.mItemClickListener != null) {
            this.mItemClickListener.onItemClick(((Integer) paramView.getTag()).intValue());
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_myaddres, paramViewGroup, false);
        ViewHolder localViewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return localViewHolder;
    }

    public void setItemClickListener(OnItemClickListener paramOnItemClickListener) {
        this.mItemClickListener = paramOnItemClickListener;
    }

    public static abstract interface OnItemClickListener {
        public abstract void onItemClick(int paramInt);
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {
        private RelativeLayout bianjis;
        private RelativeLayout delete_ivshan;
        private TextView pinganbaojia;
        private TextView shouhuorenaddress;
        private TextView shouhuorenname;
        private TextView shouphone;
        private TextView sigthree;

        public ViewHolder(View paramView) {
            super(paramView);
            this.delete_ivshan = ((RelativeLayout) paramView.findViewById(R.id.delete_ivshan));
            this.bianjis = ((RelativeLayout) paramView.findViewById(R.id.bianjis));
            this.pinganbaojia = ((TextView) paramView.findViewById(R.id.pinganbaojia));
            this.shouhuorenname = ((TextView) paramView.findViewById(R.id.shouhuorenname));
            this.shouphone = ((TextView) paramView.findViewById(R.id.shouphone));
            this.shouhuorenaddress = ((TextView) paramView.findViewById(R.id.shouhuorenaddress));
            this.sigthree = ((TextView) paramView.findViewById(R.id.sigthree));
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyAddresAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */