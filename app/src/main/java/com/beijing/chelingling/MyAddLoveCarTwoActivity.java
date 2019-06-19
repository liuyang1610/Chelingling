package com.beijing.chelingling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.adapter.MyLoveCarAdapter;
import com.beijing.chelingling.adapter.MyLoveCarTwoAdapter;
import com.beijing.chelingling.adapter.MyLoveHeadAdapter;
import com.beijing.chelingling.adapter.MyPinPaiAdapter;
import com.beijing.chelingling.info.MyCarTypeInfo;
import com.beijing.chelingling.info.MyLoveCarInfo;
import com.beijing.chelingling.info.MyLoveCarReMenInfo;
import com.beijing.chelingling.lovecar.DataBean;
import com.beijing.chelingling.lovecar.ListUtil;
import com.beijing.chelingling.lovecar.SlideBar;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.GridSpacingItemDecoration;
import com.beijing.chelingling.unit.PinYinCar;
import com.beijing.chelingling.unit.PinyinUtils;
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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyAddLoveCarTwoActivity
        extends AppCompatActivity {
    private String a;
    private MyPinPaiAdapter adapter;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                header = LayoutInflater.from(MyAddLoveCarTwoActivity.this).inflate(R.layout.header_lovecar, null);
                RecyclerView haha = header.findViewById(R.id.remenpinpai);
                GridLayoutManager localGridLayoutManager = new GridLayoutManager(MyAddLoveCarTwoActivity.this, 5, 1, false);
                MyLoveHeadAdapter localMyLoveHeadAdapter = new MyLoveHeadAdapter(MyAddLoveCarTwoActivity.this, remen);

                haha.setLayoutManager(localGridLayoutManager);
                //haha.addItemDecoration(new GridSpacingItemDecoration(5, 12, true));
                haha.setAdapter(localMyLoveHeadAdapter);
                localMyLoveHeadAdapter.setItemClickListener(new MyLoveHeadAdapter.OnItemClickListener() {
                    public void onItemClick(int paramAnonymous2Int) {
                        lovecartype.setVisibility(View.GONE);
                        istrue = false;
                        Log.i("gfdgdfgdf", paramAnonymous2Int + "   dsf");
                        a = remen.get(paramAnonymous2Int).getId();
                        postByOkGoType(ChangWeb.MYCARTYPEINFO, a);
                    }
                });
                postByOkGo(ChangWeb.MYCARINFO);
            }
            if (paramAnonymousMessage.arg1 == 1) {
                laType = filledData(la);

                Collections.sort(laType, pinYinCar);

                adapter.setData(laType);

                lovecartype.setLayoutManager(manager);
                lovecartype.setAdapter(adapter);
                adapter.setHeaderView(header);
                adapter.setItemClickListener(new MyPinPaiAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int paramInt) {
                        istrue = false;
                        lovecartype.setVisibility(View.GONE);
                        Log.i("gfdgdfgdf", paramInt + "   dsf");
                        a = laType.get(paramInt).getId();
                        postByOkGoType(ChangWeb.MYCARTYPEINFO, a);
                    }
                });
            }
            if (paramAnonymousMessage.arg1 == 2) {

                lovecartype.setVisibility(View.GONE);
                lovecartypetwo.setVisibility(View.VISIBLE);
                Log.i("wandanle", "%" + laerji.size());
                twoAdapter = new MyLoveCarTwoAdapter(MyAddLoveCarTwoActivity.this, MyAddLoveCarTwoActivity.this.laerji);
                MyAddLoveCarTwoActivity.this.lovecartypetwo.setLayoutManager(MyAddLoveCarTwoActivity.this.twomanager);
                MyAddLoveCarTwoActivity.this.lovecartypetwo.setAdapter(MyAddLoveCarTwoActivity.this.twoAdapter);
                MyAddLoveCarTwoActivity.this.twoAdapter.setItemClickListener(MyAddLoveCarTwoActivity.this.onItemClickListener);
            }
            if (paramAnonymousMessage.arg1 == 3) {
                MyAddLoveCarTwoActivity.this.postByOkGo(ChangWeb.MYCARINFO);
            }
            if (paramAnonymousMessage.arg1 == 4) {
                MyAddLoveCarTwoActivity.this.postByOkGoRenMen(ChangWeb.MYCARREMENINFO);
            }
            if (paramAnonymousMessage.arg1 == 5) {
                MyAddLoveCarTwoActivity.this.postByOkGoType(ChangWeb.MYCARTYPEINFO, MyAddLoveCarTwoActivity.this.a);
            }
            if (paramAnonymousMessage.arg1 == 6) {
                Toast.makeText(MyAddLoveCarTwoActivity.this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });
    private View header;
    private boolean isIstrue;
    private boolean istrue = true;
    private List<MyLoveCarInfo> la;
    private List<MyLoveCarInfo> laType;
    private ArrayList<MyCarTypeInfo> laerji;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.lovecartype})
    RecyclerView lovecartype;
    @Bind({R.id.lovecartypetwo})
    RecyclerView lovecartypetwo;

    private LinearLayoutManager manager;

    MyLoveCarTwoAdapter.OnItemClickListener onItemClickListener = new MyLoveCarTwoAdapter.OnItemClickListener() {
        public void onItemClick(int paramAnonymousInt) {
            Intent localIntent = new Intent();
            localIntent.putExtra("bian", ((MyCarTypeInfo) MyAddLoveCarTwoActivity.this.laerji.get(paramAnonymousInt)).getType());
            localIntent.putExtra("id", ((MyCarTypeInfo) MyAddLoveCarTwoActivity.this.laerji.get(paramAnonymousInt)).getId());
            MyAddLoveCarTwoActivity.this.setResult(4, localIntent);
            MyAddLoveCarTwoActivity.this.finish();
        }
    };
    private PinYinCar pinYinCar;
    private List<MyLoveCarReMenInfo> remen;
    @Bind({R.id.slidebar})
    SlideBar slidebar;
    private String[] strs;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    private MyLoveCarTwoAdapter twoAdapter;
    private LinearLayoutManager twomanager;


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
                        Log.i("wandanle", "%" + paramAnonymousString);
                        try {

                            Gson gson = new Gson();

                            la = gson.fromJson(paramAnonymousString, new TypeToken<List<MyLoveCarInfo>>() {
                            }.getType());

                            Message message = new Message();
                            message.arg1 = 1;
                            handler.sendMessage(message);
                            return;
                        } catch (Exception e) {
                            try {
                                if (new JSONObject(paramAnonymousString).getString("code").equals("401")) {
                                    Message message = new Message();
                                    message.arg1 = 3;
                                    handler.sendMessage(message);
                                }

                                return;
                            } catch (JSONException ea) {
                                ea.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void postByOkGoRenMen(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey")
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
                        Log.i("wandanle", "reen " + paramAnonymousString);
                        try {

                            Gson gson = new Gson();

                            remen = gson.fromJson(paramAnonymousString, new TypeToken<List<MyLoveCarReMenInfo>>() {
                            }.getType());

                            Message message = new Message();
                            message.arg1 = 0;
                            handler.sendMessage(message);
                            return;
                        } catch (Exception e) {
                            try {
                                if (new JSONObject(paramAnonymousString).getString("code").equals("401")) {
                                    Message message = new Message();
                                    message.arg1 = 4;
                                    handler.sendMessage(message);
                                }
                                return;
                            } catch (JSONException eas) {
                                eas.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void postByOkGoType(String paramString1, String paramString2) {
        Log.i("sadsad", paramString2 + "  %%");
        OkGo.post(paramString1).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("brand_id", paramString2, new boolean[0])
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
                            Gson gson = new Gson();
                            laerji = gson.fromJson(paramAnonymousString, new TypeToken<List<MyCarTypeInfo>>() {
                            }.getType());
                            Message message = new Message();
                            message.arg1 = 2;
                            handler.sendMessage(message);

                        } catch (Exception e) {
                            Message message = new Message();
                            message.arg1 = 6;
                            handler.sendMessage(message);
                        }
                    }
                });
    }


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_add_love_car_two);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.titles.setText("添加爱车");
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        postByOkGoRenMen(ChangWeb.MYCARREMENINFO);
        manager = new LinearLayoutManager(this);
        adapter = new MyPinPaiAdapter(this);
        this.twomanager = new LinearLayoutManager(this);
        this.pinYinCar = new PinYinCar();

        slidebar.setOnTouchingLetterChangedListener(new SlideBar.OnTouchingLetterChangedListener() {
            public void onTouchingLetterChanged(String paramAnonymousString) {
                Log.i("sdfdf", paramAnonymousString);

                int i = adapter.getPositionForSection(paramAnonymousString.charAt(0));
                if (i != -1) {
                    manager.scrollToPositionWithOffset(i, 0);
                }

            }
        });
    }


    @OnClick({R.id.leftimfan})
    public void onViewClicked() {
        if (this.istrue == true) {
            finish();
            return;
        }
        this.istrue = true;
        this.lovecartype.setVisibility(View.VISIBLE);
        this.lovecartypetwo.setVisibility(View.GONE);
    }


    private List<MyLoveCarInfo> filledData(List<MyLoveCarInfo> paramList) {
        ArrayList<MyLoveCarInfo> localArrayList = new ArrayList();
        int i = 0;

        Log.i("kansafdsdf", paramList.size() + "  ");
        while (i < paramList.size()) {

            MyLoveCarInfo localMyPinPaiInfo = new MyLoveCarInfo();
            localMyPinPaiInfo.setId(paramList.get(i).getId());
            localMyPinPaiInfo.setName(paramList.get(i).getName());
            localMyPinPaiInfo.setInitial(paramList.get(i).getInitial());

            String pinyin;
            try {
                if (paramList.get(i).getName().length() == 0) {
                }
                Log.i("kansafdsdf", PinyinUtils.getPingYin(paramList.get(i).getName() + "  "));
                pinyin = PinyinUtils.getPingYin(paramList.get(i).getName());
            } catch (Exception e) {
                try {
                    if (paramList.get(i).getName().length() == 0) {
                    }
                    pinyin = PinyinUtils.getPingYin(paramList.get(i).getName());
                } catch (Exception e1) {
                    pinyin = PinyinUtils.getPingYin("#");
                }
            }

            Log.i("kansafdsdf", pinyin + "  ");
            //String pinyin = PinyinUtils.getPingYin(date.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                localMyPinPaiInfo.setLetters(sortString.toUpperCase());
            } else {
                localMyPinPaiInfo.setLetters("#");
            }
            Log.i("kansafdsdf", localMyPinPaiInfo.getLetters() + "  ");
            i += 1;
            localArrayList.add(localMyPinPaiInfo);
        }
        return localArrayList;
    }

}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyAddLoveCarTwoActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */