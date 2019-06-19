package com.beijing.chelingling;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.adapter.RvGridAdapter;
import com.beijing.chelingling.info.MyLoveCarLInfo;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.GridSpacingItemDecoration;
import com.beijing.chelingling.unit.Util;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyXiuGaiCatInfoActivity
        extends AppCompatActivity {
    private static final int COLUMN = 5;
    @Bind({R.id.aichepinpai})
    TextView aichepinpai;
    @Bind({R.id.carcard})
    EditText carcard;
    @Bind({R.id.chejiahao})
    EditText chejiahao;
    @Bind({R.id.chejias})
    TextView chejias;
    @Bind({R.id.chewenhaod})
    ImageView chewenhaod;
    @Bind({R.id.diqusuozai})
    TextView diqusuozai;
    @Bind({R.id.fadongjiaho})
    EditText fadongjiaho;
    @Bind({R.id.fadongjis})
    TextView fadongjis;
    @Bind({R.id.fawenhaod})
    ImageView fawenhaod;
    @Bind({R.id.goucheriqi})
    RelativeLayout goucheriqi;
    @Bind({R.id.goucheriqiss})
    TextView goucheriqiss;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            Toast.makeText(MyXiuGaiCatInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            MyXiuGaiCatInfoActivity.this.finish();
            return false;
        }
    });
    private String id = "";
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private RvGridAdapter mRvGridAdapter;
    private MyLoveCarLInfo myLoveCarLInfo;
    @Bind({R.id.rights})
    ImageView rights;
    @Bind({R.id.rights1})
    ImageView rights1;
    @Bind({R.id.rights2})
    ImageView rights2;
    @Bind({R.id.rights3})
    ImageView rights3;
    @Bind({R.id.rights5})
    ImageView rights5;
    private String s = "";
    private String sad = "京";
    private String[] strs;
    private String[] strslist = {"京", "津", "渝", "沪", "冀", "晋", "辽", "吉", "黑", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "琼", "川", "黔", "云", "陕", "甘", "青", "台", "蒙", "桂", "宁", "新", "藏", "港", "澳"};
    @Bind({R.id.sunshudi})
    RelativeLayout sunshudi;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.wancheng})
    TextView wancheng;
    @Bind({R.id.xuanzecartype})
    RelativeLayout xuanzecartype;

    private void postByOkGo(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("car_id", this.myLoveCarLInfo.getId(), new boolean[0])
                .params("brand_id", this.id, new boolean[0])
                .params("plate", this.diqusuozai.getText().toString() + this.carcard.getText().toString(), new boolean[0])
                .params("car_date", this.goucheriqiss.getText().toString().trim(), new boolean[0])
                .params("engine", this.fadongjiaho.getText().toString().trim(), new boolean[0])
                .params("frame", this.chejiahao.getText().toString().trim(), new boolean[0])
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
                                handler.sendEmptyMessage(1);
                            }
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public String getTime(Date paramDate) {
        return new SimpleDateFormat("yyyy-MM-dd").format(paramDate);
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if ((paramInt1 == 1) && (paramInt2 == 4)) {
            this.s = paramIntent.getStringExtra("bian");
            this.id = paramIntent.getStringExtra("id");
            this.aichepinpai.setText(this.s);
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_add_love_car);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.strs = Util.load(this).split(",");
        this.myLoveCarLInfo = ((MyLoveCarLInfo) getIntent().getSerializableExtra("data"));
        this.aichepinpai.setText(this.myLoveCarLInfo.getBrand());
        String s = this.myLoveCarLInfo.getPlate_number();
        this.diqusuozai.setText(s.substring(0, 1));
        this.carcard.setText(s.substring(1));
        this.goucheriqiss.setText(this.myLoveCarLInfo.getCar_date());
        this.fadongjiaho.setText(this.myLoveCarLInfo.getEngine());
        this.chejiahao.setText(this.myLoveCarLInfo.getFrame());
        this.titles.setText("添加爱车");
    }

    @OnClick({R.id.leftimfan, R.id.xuanzecartype, R.id.sunshudi, R.id.goucheriqi, R.id.chewenhaod, R.id.fawenhaod, R.id.wancheng})
    public void onViewClicked(final View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.xuanzecartype:
                startActivityForResult(new Intent(this, MyAddLoveCarTwoActivity.class), 1);
                return;
            case R.id.fawenhaod:
                showimage();
                return;
            case R.id.chewenhaod:
                showimage();
                return;
            case R.id.sunshudi:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                Object localObject = View.inflate(this, R.layout.custom_dialog, null);
                builder.setView((View) localObject);
                builder.setCancelable(true);
                localObject = (RecyclerView) ((View) localObject).findViewById(R.id.grid_rv);
                ((RecyclerView) localObject).setLayoutManager(new GridLayoutManager(this, 5, 1, false));
                ((RecyclerView) localObject).addItemDecoration(new GridSpacingItemDecoration(5, getResources().getDimensionPixelSize(R.dimen.padding_middless), true));
                this.mRvGridAdapter = new RvGridAdapter(this, this.strslist, this.sad);
                ((RecyclerView) localObject).setAdapter(this.mRvGridAdapter);
                final AlertDialog dialog = builder.create();
                dialog.show();
                this.mRvGridAdapter.setOnItemClickListener(new RvGridAdapter.OnItemClickListener() {
                    public void onItemClick(View paramAnonymousView, int paramAnonymousInt) {
                        MyXiuGaiCatInfoActivity.this.diqusuozai.setText(MyXiuGaiCatInfoActivity.this.strslist[paramAnonymousInt]);
                        s = strslist[paramAnonymousInt];
                        dialog.dismiss();
                    }
                });
                return;
            case R.id.goucheriqi:
                TimePickerBuilder timePickerBuilder = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    public void onTimeSelect(Date paramAnonymousDate, View paramAnonymousView) {
                        String s = getTime(paramAnonymousDate);
                        goucheriqiss.setText(s);
                    }
                }).setType(new boolean[]{true, true, true, false, false, false})
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .setContentTextSize(20)
                        .setTitleSize(20)
                        .setOutSideCancelable(true)
                        .isCyclic(true)
                        .setTextColorCenter(-16777216)
                        .setTitleColor(-16777216)
                        .setSubmitColor(-16776961)
                        .setCancelColor(-16776961)
                        .isCenterLabel(false);
                timePickerBuilder.build().setDate(Calendar.getInstance());
                timePickerBuilder.build().show();
                return;
            case R.id.wancheng:
                postByOkGo(ChangWeb.MYXIUGAILOVECAR);
                break;
        }

    }

    public void showimage() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setView(View.inflate(this, R.layout.imageslashow, null));
        localBuilder.setCancelable(true);
        localBuilder.create().show();
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyXiuGaiCatInfoActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */