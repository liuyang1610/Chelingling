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
import com.beijing.chelingling.adapter.MyDaiZhiFuAdapter;
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

public class MyDaiZhiFuFragment
        extends Fragment {
    private MyDaiZhiFuAdapter adapter;
    @Bind({R.id.daizhifurecy})
    RecyclerView daizhifurecy;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            try {
                MyDaiZhiFuFragment.this.remen.size();
                adapter = new MyDaiZhiFuAdapter(getContext(), remen, strs);
                MyDaiZhiFuFragment.this.daizhifurecy.setLayoutManager(MyDaiZhiFuFragment.this.manager);
                MyDaiZhiFuFragment.this.daizhifurecy.setAdapter(MyDaiZhiFuFragment.this.adapter);
                MyDaiZhiFuFragment.this.daizhifurecy.setVisibility(View.VISIBLE);
                MyDaiZhiFuFragment.this.nodata.setVisibility(View.GONE);
                return false;
            } catch (Exception e) {
                MyDaiZhiFuFragment.this.daizhifurecy.setVisibility(View.GONE);
                MyDaiZhiFuFragment.this.nodata.setVisibility(View.VISIBLE);
            }
            return false;
        }
    });
    private LinearLayoutManager manager;
    @Bind({R.id.nodata})
    RelativeLayout nodata;
    private ArrayList<MyDingDanInfo> remen;
    private String[] strs;

    private void postByOkGoJiaRuDingdan(String paramString) {
        OkGo.post(paramString).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0])
                .params("appsecret", this.strs[1])
                .params("zt", 0)
                .params("timestamp", Util.getTime())
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()))
                .execute(new StringCallback() {
                    public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                        super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                        Log.i("wandanle", "错误");
                    }

                    public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                        Log.i("vcbvcbc", paramAnonymousString);
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
        View view = paramLayoutInflater.inflate(R.layout.fragment_daizhifu, null);
        ButterKnife.bind(this, view);
        try {
            String s = Util.load(getContext());
            Log.i("sadsad", paramViewGroup + "  %%%%%%%%%");
            this.strs = s.split(",");
            postByOkGoJiaRuDingdan(ChangWeb.MYDINGDANINFO);
            manager = new LinearLayoutManager(getContext());

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


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\fragment\MyDaiZhiFuFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */