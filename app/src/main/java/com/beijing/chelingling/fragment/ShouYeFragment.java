package com.beijing.chelingling.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.chelingling.MyDaoLuJiuYuan;
import com.beijing.chelingling.MyGengDuoActivity;
import com.beijing.chelingling.MyJiaYouKaActivity;
import com.beijing.chelingling.MyNewActivity;
import com.beijing.chelingling.MySetActivity;
import com.beijing.chelingling.MyWebActivity;
import com.beijing.chelingling.R;
import com.beijing.chelingling.adapter.MyZhuanTiShowAdapter;
import com.beijing.chelingling.info.AnyEventType;
import com.beijing.chelingling.info.Myzhuantihuodong;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.ShoueyeDiaLog;
import com.beijing.chelingling.unit.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jauker.widget.BadgeView;
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
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ShouYeFragment
        extends Fragment {
    private MyZhuanTiShowAdapter adapter;
    private BadgeView badgeView;
    @Bind({R.id.banner})
    Banner banner;
    @Bind({R.id.daolujiuyuans})
    LinearLayout daolujiuyuans;
    @Bind({R.id.dianjianniu})
    RelativeLayout dianjianniu;
    @Bind({R.id.gengduo})
    RelativeLayout gengduo;
    @Bind({R.id.gengguo})
    TextView gengguo;
    @Bind({R.id.gengguos})
    TextView gengguos;
    @Bind({R.id.guzhangpaich})
    RelativeLayout guzhangpaich;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                banner.setBannerStyle(1);
                banner.isAutoPlay(true);
                banner.setDelayTime(5000);
                banner.setIndicatorGravity(6);
                banner.setImages(imagess, new Banner.OnLoadImageListener() {
                    public void OnLoadImage(ImageView paramAnonymous2ImageView, Object paramAnonymous2Object) {
                        System.out.println("加载中");
                        Picasso.get().load((String) paramAnonymous2Object).into(paramAnonymous2ImageView);
                        System.out.println("加载完");
                    }
                });
                banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
                    public void OnBannerClick(View paramAnonymous2View, int paramAnonymous2Int) {
                    }
                });
            }
            if (paramAnonymousMessage.arg1 == 1) {

                adapter = new MyZhuanTiShowAdapter(getContext(), remen);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                zhuantishow.setLayoutManager(linearLayoutManager);
                zhuantishow.setAdapter(adapter);
                new PagerSnapHelper().attachToRecyclerView(zhuantishow);
                ShouYeFragment.this.adapter.setItemClickListener(new MyZhuanTiShowAdapter.OnItemClickListener() {
                    public void onItemClick(int paramAnonymous2Int) {
                        String str = ChangWeb.MYZHUANTITONGZHI + ((Myzhuantihuodong) ShouYeFragment.this.remen.get(paramAnonymous2Int)).getId() + ".html";
                        Log.i("vfdsddhb", str);
                        try {
                            if ((remen.get(paramAnonymous2Int)).getUrl().equals("null")) {
                                ShouYeFragment.this.startActivity(new Intent(ShouYeFragment.this.getContext(), MyWebActivity.class).putExtra("url", str));
                                return;
                            }
                            ShouYeFragment.this.startActivity(new Intent(ShouYeFragment.this.getContext(), MyWebActivity.class).putExtra("url", "http://" + ((Myzhuantihuodong) ShouYeFragment.this.remen.get(paramAnonymous2Int)).getUrl()));
                            return;
                        } catch (Exception localException) {
                            ShouYeFragment.this.startActivity(new Intent(ShouYeFragment.this.getContext(), MyWebActivity.class).putExtra("url", str));
                        }
                    }
                });
            }
            return false;
        }
    });
    private List<String> imagess = new ArrayList();
    @Bind({R.id.jiayouchongzhi})
    RelativeLayout jiayouchongzhi;
    @Bind({R.id.kefufuwu})
    RelativeLayout kefufuwu;
    @Bind({R.id.leftimage})
    ImageView leftimage;
    private ShoueyeDiaLog myQueDingDialog;
    @Bind({R.id.qichebaoxian})
    RelativeLayout qichebaoxian;
    @Bind({R.id.qichejinrong})
    RelativeLayout qichejinrong;
    @Bind({R.id.redtong})
    RelativeLayout redtong;
    private ArrayList<Myzhuantihuodong> remen;
    @Bind({R.id.rightimage})
    ImageView rightimage;
    @Bind({R.id.shigulipei})
    RelativeLayout shigulipei;
    private String[] strs;
    @Bind({R.id.title})
    TextView title;
    @Bind({R.id.tu1})
    ImageView tu1;
    @Bind({R.id.tu2})
    ImageView tu2;
    @Bind({R.id.zhuantishow})
    RecyclerView zhuantishow;
    @Bind({R.id.zhuanyejiu})
    RelativeLayout zhuanyejiu;

    private void postByOkGo(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT).params("appid", this.strs[0], new boolean[0]).params("appsecret", this.strs[1], new boolean[0]).params("timestamp", Util.getTime(), new boolean[0]).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0]).execute(new StringCallback() {
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
                    handler.sendMessage(message);
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postByOkGoZhuanti(String paramString) {
        Log.i("mvbnbvnvb", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT).params("appid", this.strs[0], new boolean[0]).params("appsecret", this.strs[1], new boolean[0]).params("timestamp", Util.getTime(), new boolean[0]).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0]).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                Log.i("mvbnbvnvb", paramAnonymousException.toString());
            }

            public void onSuccess(String s, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("mvbnbvnvb", s);
                try {
                    Gson gson = new Gson();

                    remen = gson.fromJson(s, new TypeToken<List<Myzhuantihuodong>>() {
                    }.getType());

                    Message message = new Message();
                    message.arg1 = 1;
                    handler.sendMessage(message);
                    return;
                } catch (Exception paramAnonymousString) {
                }
            }
        });
    }

    public void messageEventBus(AnyEventType paramAnyEventType) {
        Log.i("fdsfds", "dfsdfsdfsdfsdfsda");
        int i = paramAnyEventType.getA();
        this.badgeView.setBadgeCount(i);
    }

    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(R.layout.fragment_shouye, null);
        ButterKnife.bind(this, view);
        this.badgeView = new BadgeView(getContext());
        this.badgeView.setTargetView(this.redtong);
        String strings = Util.load(getContext());
        Log.i("sadsad", paramViewGroup + "  %%%%%%%%%");
        this.strs = strings.split(",");
        postByOkGo(ChangWeb.MYBANNERS);
        postByOkGoZhuanti(ChangWeb.MYZHUANTIHUODONG);
        //EventBus.getDefault().register(this);
        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onResume() {
        super.onResume();
        FragmentActivity localFragmentActivity = getActivity();
        getActivity();
        int i = localFragmentActivity.getSharedPreferences("lock", 0).getInt("code", 0);
        this.badgeView.setBadgeCount(i);
    }

    public void onStop() {
        super.onStop();
        //EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.leftimage, R.id.gengduo, R.id.rightimage, R.id.daolujiuyuans, R.id.jiayouchongzhi, R.id.qichebaoxian, R.id.shigulipei, R.id.qichejinrong})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            case R.id.qichebaoxian:
            case R.id.shigulipei:
            default:
                return;
            case R.id.leftimage:
                startActivity(new Intent(getContext(), MySetActivity.class));
                //getActivity().overridePendingTransition(R.anim.in_from_left, R.anim.in_from_right);
                return;
            case R.id.rightimage:
                startActivity(new Intent(getContext(), MyNewActivity.class));
//                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.in_from_left);
                return;
            case R.id.daolujiuyuans:
                startActivity(new Intent(getContext(), MyDaoLuJiuYuan.class));
                return;
            case R.id.gengduo:
                startActivity(new Intent(getContext(), MyGengDuoActivity.class));
                return;
            case R.id.jiayouchongzhi:
                startActivity(new Intent(getContext(), MyJiaYouKaActivity.class));
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\fragment\ShouYeFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */