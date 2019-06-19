package com.beijing.chelingling;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.beijing.chelingling.adapter.MyMapAdapter;
import com.beijing.chelingling.adapter.MyMapAdapter.OnItemClickListener;
import com.beijing.chelingling.info.MapInfo;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.MyQueDingDialog;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyMapActivity
        extends AppCompatActivity
        implements LocationSource, AMapLocationListener, AMap.OnCameraChangeListener, PoiSearch.OnPoiSearchListener {
    private AMap aMap;
    private String city;
    private String country;
    private String deepType = "写字楼|公路|公司|商超|学校|地铁|医院|公交站|购物|公园|银行|电影|救援|汽车|加油|保险|汽车美容|充电桩";
    @Bind({R.id.funjinliest})
    RecyclerView funjinliest;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 1) {
                myQueDingDialog = new MyQueDingDialog(MyMapActivity.this);
                MyMapActivity.this.myQueDingDialog.setTitle("400-816-5355");
                MyMapActivity.this.myQueDingDialog.setYesOnclickListener("确定", new MyQueDingDialog.onYesOnclickListener() {
                    public void onYesClick() {
                        Util.call(MyMapActivity.this, "400-816-5355");
                        MyMapActivity.this.myQueDingDialog.dismiss();
                    }
                });
                MyMapActivity.this.myQueDingDialog.setNoOnclickListener("取消", new MyQueDingDialog.onNoOnclickListener() {
                    public void onNoClick() {
                        MyMapActivity.this.myQueDingDialog.dismiss();
                    }
                });
                MyMapActivity.this.myQueDingDialog.show();
            }
            if (paramAnonymousMessage.arg1 == 2) {
            }
            return false;
        }
    });
    @Bind({R.id.iocns})
    ImageView iocns;
    private double jingdu;
    @Bind({R.id.jinjiqiuzhu})
    RelativeLayout jinjiqiuzhu;
    private LatLng latlng;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private List<MapInfo> lisr;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    @Bind({R.id.mapview})
    MapView mapview;
    private MyMapAdapter myMapAdapter;
    private MyQueDingDialog myQueDingDialog;
    private ArrayList<PoiItem> poiItems;
    private PoiResult poiResult;
    private PoiSearch poiSearch;
    private int posas = 0;
    private String province;
    private PoiSearch.Query query;
    private String[] strs;
    @Bind({R.id.textView})
    TextView textView;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.vies})
    View vies;
    private double weidu;

    private void init() {
        if (this.aMap == null) {
            this.aMap = this.mapview.getMap();
            this.aMap.setOnCameraChangeListener(this);
            setUpMap();
        }
    }

    private void postByOkGo(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) OkGo.post(paramString).tag(this)).cacheKey("cachePostKey")).cacheMode(CacheMode.DEFAULT)).params("appid", this.strs[0], new boolean[0])).params("appsecret", this.strs[1], new boolean[0])).params("add", ((MapInfo) this.lisr.get(this.posas)).getDujindizhi(), new boolean[0])).params("timestamp", Util.getTime(), new boolean[0])).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                Log.i("wandanle", "错误");
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("wandanle", paramAnonymousString);
                try {
                    if (new JSONObject(paramAnonymousString).getString("code").equals("000")) {
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

    private void setUpMap() {
        if (this.mLocationClient == null) {
            this.mLocationClient = new AMapLocationClient(getApplicationContext());
            AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
            this.mLocationClient.setLocationListener(this);
            aMapLocationClientOption.setOnceLocation(true);
            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            this.mLocationClient.setLocationOption(aMapLocationClientOption);
            this.mLocationClient.startLocation();
        }
        Object localObject = new MyLocationStyle();
        ((MyLocationStyle) localObject).myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.car));
        ((MyLocationStyle) localObject).strokeColor(Color.argb(0, 0, 0, 0));
        ((MyLocationStyle) localObject).radiusFillColor(Color.argb(0, 0, 0, 0));
        ((MyLocationStyle) localObject).strokeColor(-16777216);
        ((MyLocationStyle) localObject).radiusFillColor(Color.argb(100, 0, 0, 180));
        ((MyLocationStyle) localObject).strokeWidth(1.0F);
        this.aMap.setLocationSource(this);
        this.aMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.aMap.setMyLocationEnabled(true);
    }

    public void activate(LocationSource.OnLocationChangedListener paramOnLocationChangedListener) {
        this.mListener = paramOnLocationChangedListener;
        this.mLocationClient.startLocation();
    }

    public void deactivate() {
        this.mListener = null;
        if (this.mLocationClient != null) {
            this.mLocationClient.stopLocation();
            this.mLocationClient.onDestroy();
        }
        this.mLocationClient = null;
    }

    protected void doSearchQuery(double paramDouble1, double paramDouble2) {
        this.aMap.setOnMapClickListener(null);
        Log.i("fanhuideyanzhengma", this.city);
        this.query = new PoiSearch.Query("", this.deepType, this.city);
        this.query.setPageSize(50);
        this.query.setPageNum(0);
        LatLonPoint localLatLonPoint = new LatLonPoint(paramDouble1, paramDouble2);
        this.poiSearch = new PoiSearch(this, this.query);
        this.poiSearch.setOnPoiSearchListener(this);
        this.poiSearch.setBound(new PoiSearch.SearchBound(localLatLonPoint, 5000, true));
        this.poiSearch.searchPOIAsyn();
    }

    public void onCameraChange(CameraPosition paramCameraPosition) {
    }

    public void onCameraChangeFinish(CameraPosition paramCameraPosition) {
        this.latlng = paramCameraPosition.target;
        this.aMap.clear();
        this.aMap.addMarker(new MarkerOptions().position(this.latlng));
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_map);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        String str = Util.load(this);
        this.mapview.onCreate(paramBundle);
        init();
        Log.i("sadsad", str + "  %%%%%%%%%");
        this.strs = str.split(",");
        this.titles.setText("SOS救援");
    }

    protected void onDestroy() {
        this.mLocationClient.onDestroy();
        super.onDestroy();
    }

    public void onLocationChanged(AMapLocation paramAMapLocation) {
        if ((this.mListener != null) && (paramAMapLocation != null)) {
            if (paramAMapLocation.getErrorCode() == 0) {
                this.mListener.onLocationChanged(paramAMapLocation);
                this.weidu = paramAMapLocation.getLatitude();
                this.jingdu = paramAMapLocation.getLongitude();
                this.latlng = new LatLng(paramAMapLocation.getLatitude(), paramAMapLocation.getLongitude());
                this.aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.latlng, 18.0F));
                this.city = paramAMapLocation.getProvince();
                this.country = paramAMapLocation.getDistrict();
                this.province = paramAMapLocation.getProvince();
                doSearchQuery(this.latlng.latitude, this.latlng.longitude);
            }
        } else {
            return;
        }
        Log.e("AmapErr", "定位失败," + paramAMapLocation.getErrorCode() + ": " + paramAMapLocation.getErrorInfo());
    }

    protected void onPause() {
        super.onPause();
        this.mLocationClient.stopLocation();
    }

    public void onPoiItemSearched(PoiItem paramPoiItem, int paramInt) {
    }

    public void onPoiSearched(PoiResult paramPoiResult, int paramInt) {
        Log.i("fanhuideyanzhengma", paramInt + "   asd");
        if (paramInt == 1000) {
            if ((paramPoiResult != null) && (paramPoiResult.getQuery() != null)) {
                Log.i("fanhuideyanzhengma", paramInt + "   asd1");
                if (!paramPoiResult.getQuery().equals(this.query)) {
                    return;
                }
                Log.i("fanhuideyanzhengma", paramInt + "   asd2");
                this.poiResult = paramPoiResult;
                this.poiItems = this.poiResult.getPois();
                this.lisr = new ArrayList();
                if ((this.poiItems != null) && (this.poiItems.size() > 0)) {
                    int i = 0;
                    while (i < this.poiItems.size()) {
                        Log.i("fanhuideyanzhengma", ((PoiItem) this.poiItems.get(1)).getTitle() + " 4");
                        MapInfo mapInfo = new MapInfo();
                        mapInfo.setDujinname(((PoiItem) this.poiItems.get(i)).getTitle());
                        mapInfo.setDujindizhi(((PoiItem) this.poiItems.get(i)).getSnippet());
                        mapInfo.setJingdu(((PoiItem) this.poiItems.get(i)).getLatLonPoint().getLatitude());
                        mapInfo.setWeidu(((PoiItem) this.poiItems.get(i)).getLatLonPoint().getLongitude());
                        this.lisr.add(mapInfo);
                        i += 1;
                    }
                    Log.i("liuyangxihuande", ((PoiItem) this.poiItems.get(1)).getCityName() + " " + ((PoiItem) this.poiItems.get(1)).getTypeDes() + " " + ((PoiItem) this.poiItems.get(1)).getProvinceName() + " " + ((PoiItem) this.poiItems.get(1)).getAdName() + " " + ((PoiItem) this.poiItems.get(1)).getDirection());
                    this.funjinliest.setLayoutManager(new LinearLayoutManager(this));
                    this.funjinliest.setHasFixedSize(true);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(500L);
                                Message localMessage = new Message();
                                localMessage.arg1 = 2;
                                MyMapActivity.this.handler.sendMessage(localMessage);
                                return;
                            } catch (InterruptedException localInterruptedException) {
                                localInterruptedException.printStackTrace();
                            }
                        }
                    }).start();
                    this.myMapAdapter = new MyMapAdapter(this.lisr, this);
                    this.funjinliest.setAdapter(this.myMapAdapter);
                    this.myMapAdapter.setItemClickListener(new MyMapAdapter.OnItemClickListener() {
                        public void onItemClick(int paramAnonymousInt) {


                            latlng = new LatLng(((MapInfo) MyMapActivity.this.lisr.get(paramAnonymousInt)).getJingdu(), ((MapInfo) MyMapActivity.this.lisr.get(paramAnonymousInt)).getWeidu());

                            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MyMapActivity.this.latlng, 18.0F), 1000L, null);
                        }
                    });
                }
            }
        }


    }

    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.leftimfan, R.id.jinjiqiuzhu})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.jinjiqiuzhu:
                postByOkGo(ChangWeb.MYTIANJIAJIUYUAN);
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyMapActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */