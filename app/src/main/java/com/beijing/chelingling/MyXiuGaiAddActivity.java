package com.beijing.chelingling;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.info.JsonBean;
import com.beijing.chelingling.info.MyAddGunLiInfo;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.GetJsonDataUtil;
import com.beijing.chelingling.unit.Util;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;
import ch.ielse.view.SwitchView.OnStateChangedListener;
import okhttp3.Call;
import okhttp3.Response;

public class MyXiuGaiAddActivity
        extends AppCompatActivity {
    private static final int MSG_LOAD_DATA = 1;
    private static final int MSG_LOAD_FAILED = 3;
    private static final int MSG_LOAD_SUCCESS = 2;
    private int a = 0;
    @Bind({R.id.dianphone})
    EditText dianphone;
    @Bind({R.id.diqudianclick})
    RelativeLayout diqudianclick;
    @Bind({R.id.diqushengshi})
    TextView diqushengshi;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                Toast.makeText(MyXiuGaiAddActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                MyXiuGaiAddActivity.this.finish();
                return false;
            }
            Toast.makeText(MyXiuGaiAddActivity.this, "保存失败 请重试", Toast.LENGTH_SHORT).show();
            return false;
        }
    });
    private boolean isLoaded = false;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            switch (paramAnonymousMessage.what) {
                case 1:
                    break;
                case 2:
                    isLoaded = true;
                    break;
                default:
                    return;
            }

        }
    };
    private MyAddGunLiInfo myAddGunLiInfo;
    @Bind({R.id.on1})
    TextView on1;
    @Bind({R.id.on2})
    TextView on2;
    @Bind({R.id.on3})
    TextView on3;
    @Bind({R.id.on4})
    TextView on4;
    @Bind({R.id.on5})
    TextView on5;
    @Bind({R.id.shouhuorenna})
    EditText shouhuorenna;
    @Bind({R.id.stattt})
    TextView stattt;
    private String[] strs;
    @Bind({R.id.swithss})
    SwitchView swithss;
    private Thread thread;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.xiangxiadd})
    EditText xiangxiadd;

    private OptionsPickerView pvOptions;
    //  省份
    private ArrayList<String> provinceBeanList = new ArrayList<>();
    //  城市
    private ArrayList<String> cities;
    private ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    private ArrayList<String> district;
    private ArrayList<List<String>> districts;
    private ArrayList<List<List<String>>> districtList = new ArrayList<>();

    private void postByOkGo(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("id", this.myAddGunLiInfo.getId(), new boolean[0])
                .params("name", this.shouhuorenna.getText().toString().trim(), new boolean[0])
                .params("tel", this.dianphone.getText().toString().trim(), new boolean[0])
                .params("add", this.diqushengshi.getText().toString().trim(), new boolean[0])
                .params("adds", this.xiangxiadd.getText().toString().trim(), new boolean[0])
                .params("zt", this.a, new boolean[0])
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
                                message.arg1 = 0;
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


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_xiu_gai_add);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);
        this.myAddGunLiInfo = ((MyAddGunLiInfo) getIntent().getSerializableExtra("data"));
        this.shouhuorenna.setText(this.myAddGunLiInfo.getName());
        this.dianphone.setText(this.myAddGunLiInfo.getTel());
        this.diqushengshi.setText(this.myAddGunLiInfo.getAdd());
        this.xiangxiadd.setText(this.myAddGunLiInfo.getAdds());
        if (this.myAddGunLiInfo.getZt().equals("0")) {
            this.swithss.setOpened(false);
        }

        this.strs = Util.load(this).split(",");
        this.thread = new Thread(new Runnable() {
            public void run() {
                MyXiuGaiAddActivity.this.initJsonData();
            }
        });
        this.thread.start();
        this.swithss.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            public void toggleToOff(SwitchView paramAnonymousSwitchView) {
                paramAnonymousSwitchView.setOpened(false);
                a = 0;
            }

            public void toggleToOn(SwitchView paramAnonymousSwitchView) {
                paramAnonymousSwitchView.setOpened(true);
                a = 1;
            }
        });

        if (myAddGunLiInfo.getZt().equals("1")) {
            this.swithss.setOpened(true);
        }

        // 初始化Json对象
        initJsonData();
        //初始化Picker
        showPicker();

    }

    @OnClick({R.id.leftimfan, R.id.diqudianclick, R.id.stattt})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.diqudianclick:

                pvOptions.show();

                return;
            case R.id.stattt:
                if ((this.shouhuorenna.getText().toString().trim().length() > 0) && (this.dianphone.getText().toString().trim().length() > 0) && (this.xiangxiadd.getText().toString().trim().length() > 0)) {
                    postByOkGo(ChangWeb.MYADDSHOUHUODIZI);
                    return;
                }
                Toast.makeText(this, "请填写全部资料", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void showPicker() {
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String city = provinceBeanList.get(options1);

                String address; //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceBeanList.get(options1) + "-" + cityList.get(options1).get(options2);

                } else {
//                    address = provinceBeanList.get(options1) + " " + cityList.get(options1).get(option2) + " " + districtList.get(options1).get(option2).get(options3);
                    address = provinceBeanList.get(options1) + "-" + cityList.get(options1).get(options2);

                }
                diqushengshi.setText(address);
            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {

            }
        }).build();

        pvOptions.setPicker(provinceBeanList, cityList);//添加数据源

    }


    private void initJsonData() {
        //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */

        String JsonData = getJson("province.json");//获取assets目录下的json文件数据
        parseJson(JsonData);
    }

    /**
     * 从asset目录下读取fileName文件内容
     *
     * @param fileName 待读取asset下的文件名
     * @return 得到省市县的String
     */
    private String getJson(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * 解析json填充集合
     *
     * @param str 待解析的json，获取省市县
     */
    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
//                provinceBeanList.add(new ProvinceBean(provinceName));
                provinceBeanList.add(provinceName);
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();
                //   声明存放城市的集合
                districts = new ArrayList<>();
                //声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();
                    // 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
//                    for (int k = 0; k < areaArray.length(); k++) {
//                        String areaName = areaArray.getString(k);
//                        //district.add(areaName);
//                    }
//                    //  将区县的集合放入集合
//                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyXiuGaiAddActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */