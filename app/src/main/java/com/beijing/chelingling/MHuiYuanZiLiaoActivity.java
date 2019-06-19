package com.beijing.chelingling;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.info.JsonBean;
import com.beijing.chelingling.info.MyGeRenInfo;
import com.beijing.chelingling.unit.ActionSheetDialog;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MHuiYuanZiLiaoActivity
        extends AppCompatActivity {
    @Bind({R.id.baocun})
    TextView baocun;
    private String citynames;
    @Bind({R.id.dizhi})
    TextView dizhi;
    @Bind({R.id.dizhis})
    RelativeLayout dizhis;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 1) {
                Toast.makeText(MHuiYuanZiLiaoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                MHuiYuanZiLiaoActivity.this.finish();
            }
            return false;
        }
    });
    private boolean isLoaded = false;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private int mDay;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            switch (paramAnonymousMessage.what) {
                case 1:
                    break;
                case 2:
                    isLoaded = true;
                    break;
                default:
                    break;
            }

        }
    };
    private int mMonth;
    private int mYear;
    @Bind({R.id.mingzi})
    EditText mingzi;
    private MyGeRenInfo myGeRenInfo;


    private OptionsPickerView pvOptions;

    //  省份
    ArrayList<String> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();

    private String province;
    @Bind({R.id.shengri})
    RelativeLayout shengri;
    @Bind({R.id.shengris})
    TextView shengris;
    private String[] strs;
    private Thread thread;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.xingbie})
    RelativeLayout xingbie;
    @Bind({R.id.xingbies})
    TextView xingbies;
    @Bind({R.id.yonghumin})
    TextView yonghumin;
    @Bind({R.id.yonghumin1})
    TextView yonghumin1;
    @Bind({R.id.yonghumin2})
    TextView yonghumin2;
    @Bind({R.id.yonghumin3})
    TextView yonghumin3;
    @Bind({R.id.yonghumin6})
    TextView yonghumin6;
    @Bind({R.id.youxaing})
    EditText youxaing;


    private void postByOkGoJiaRuDingdan(String paramString) {
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", strs[0])
                .params("appsecret", strs[1])
                .params("name", mingzi.getText().toString())
                .params("sex", xingbies.getText().toString())
                .params("province", province)
                .params("city", citynames)
                .params("birthday", shengris.getText().toString())
                .params("email", this.youxaing.getText().toString())
                .params("timestamp", Util.getTime())
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()))
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


    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_mhui_yuan_zi_liao);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        this.titles.setText("会员资料修改");
        try {
            this.myGeRenInfo = ((MyGeRenInfo) getIntent().getSerializableExtra("data"));
            this.shengris.setText(this.myGeRenInfo.getBirthday());
            this.mingzi.setText(this.myGeRenInfo.getName());
            this.xingbies.setText(this.myGeRenInfo.getSex());
            this.dizhi.setText(this.myGeRenInfo.getProvince() + " - " + this.myGeRenInfo.getCity());
            if (this.dizhi.getText().toString().trim().equals("nullnull")) {
                this.dizhi.setText("");
            }
            this.youxaing.setText(this.myGeRenInfo.getEmail());
        } catch (Exception e) {

        }
        if (this.xingbies.getText().toString().length() <= 0) {
            this.xingbies.setText("请选择性别");
        }
        if (this.dizhi.getText().toString().length() <= 0) {
            this.dizhi.setText("请选择地址");
        }
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        // 初始化Json对象
        initJsonData();
        //初始化Picker
        showPicker();

    }

    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    @OnClick({R.id.leftimfan, R.id.xingbie, R.id.dizhis, R.id.shengri, R.id.baocun})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                break;
            case R.id.leftimfan:
                finish();
                break;
            case R.id.xingbie:
                new ActionSheetDialog(this).builder().setCancelable(false).setCanceledOnTouchOutside(false).addSheetItem("男", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    public void onClick(int paramAnonymousInt) {
                        MHuiYuanZiLiaoActivity.this.xingbies.setText("男");
                    }
                }).addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    public void onClick(int paramAnonymousInt) {
                        MHuiYuanZiLiaoActivity.this.xingbies.setText("女");
                    }
                }).show();
                break;
            case R.id.dizhis:
                pvOptions.show();
                break;
            case R.id.shengri:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mYear = year;
                                mMonth = month;
                                mDay = dayOfMonth;
                                String data = year + "-" + (month + 1) + "-" + dayOfMonth + "";
                                shengris.setText(data);
                            }
                        },
                        mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.baocun:
                postByOkGoJiaRuDingdan(ChangWeb.MYHUIYUANXINXIXIUGAI);
                break;
        }

    }

    private void showPicker() {
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String city = provinceBeanList.get(options1);
                citynames = city;
                String address; //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceBeanList.get(options1) + "-" + cityList.get(options1).get(options2);
                    province = cityList.get(options1).get(options2);
                } else {
//                    address = provinceBeanList.get(options1) + " " + cityList.get(options1).get(option2) + " " + districtList.get(options1).get(option2).get(options3);
                    address = provinceBeanList.get(options1) + "-" + cityList.get(options1).get(options2);
                    province = cityList.get(options1).get(options2);
                }
                dizhi.setText(address);
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
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
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


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MHuiYuanZiLiaoActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */