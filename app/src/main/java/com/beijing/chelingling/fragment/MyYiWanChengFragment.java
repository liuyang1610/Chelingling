package com.beijing.chelingling.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.beijing.chelingling.R;
import com.beijing.chelingling.adapter.MyJinXingZhongAdapter;
import com.beijing.chelingling.info.MyDingDanInfo;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MyYiWanChengFragment
        extends Fragment {
    private MyJinXingZhongAdapter adapter;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            try {
                MyYiWanChengFragment.this.remen.size();
                adapter = new MyJinXingZhongAdapter(getContext(), true, remen,strs);
                MyYiWanChengFragment.this.yiwanchengrecycl.setLayoutManager(MyYiWanChengFragment.this.manager);
                MyYiWanChengFragment.this.yiwanchengrecycl.setAdapter(MyYiWanChengFragment.this.adapter);
                MyYiWanChengFragment.this.yiwanchengrecycl.setVisibility(View.VISIBLE);
                MyYiWanChengFragment.this.nodata.setVisibility(View.GONE);
                return false;
            } catch (Exception e) {
                MyYiWanChengFragment.this.yiwanchengrecycl.setVisibility(View.GONE);
                MyYiWanChengFragment.this.nodata.setVisibility(View.VISIBLE);
            }
            return false;
        }
    });
    private LinearLayoutManager manager;
    @Bind({R.id.nodata})
    RelativeLayout nodata;
    private ArrayList<MyDingDanInfo> remen;
    private String[] strs;
    @Bind({R.id.yiwanchengrecycl})
    RecyclerView yiwanchengrecycl;

    private void postByOkGoJiaRuDingdan(String paramString) {
        OkGo.post(paramString).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("zt", 1, new boolean[0])
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

                            Gson gson = new Gson();
                            remen = gson.fromJson(paramAnonymousString, new TypeToken<List<MyDingDanInfo>>() {
                            }.getType());
                            Message message = new Message();
                            message.arg1 = 1;
                            handler.sendMessage(message);
                            return;
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(R.layout.fragment_yiwancheng, null);
        ButterKnife.bind(this, view);
        try {
            String s = Util.load(getContext());
            Log.i("sadsad", paramViewGroup + "  %%%%%%%%%");
            this.strs = s.split(",");
            postByOkGoJiaRuDingdan(ChangWeb.MYDINGDANINFO);
            this.manager = new LinearLayoutManager(getContext());

        } catch (Exception e) {
        }
        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        try {
            ButterKnife.unbind(this);
            return;
        } catch (Exception localException) {
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\fragment\MyYiWanChengFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */