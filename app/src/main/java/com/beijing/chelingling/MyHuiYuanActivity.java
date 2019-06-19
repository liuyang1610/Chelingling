package com.beijing.chelingling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyHuiYuanActivity
        extends AppCompatActivity {
    @Bind({R.id.dengji})
    TextView dengji;
    @Bind({R.id.dengjione})
    TextView dengjione;
    @Bind({R.id.dengjione1})
    TextView dengjione1;
    @Bind({R.id.dengjione2})
    TextView dengjione2;
    @Bind({R.id.dengjione3})
    TextView dengjione3;
    @Bind({R.id.dengjione4})
    TextView dengjione4;
    private String grade;
    @Bind({R.id.huoyuanname})
    TextView huoyuanname;
    @Bind({R.id.iconone})
    TextView iconone;
    @Bind({R.id.iconone1})
    TextView iconone1;
    @Bind({R.id.iconone2})
    TextView iconone2;
    @Bind({R.id.iconone3})
    TextView iconone3;
    @Bind({R.id.iconone4})
    TextView iconone4;
    @Bind({R.id.icononebg})
    ImageView icononebg;
    @Bind({R.id.icononebg1})
    ImageView icononebg1;
    @Bind({R.id.icononebg2})
    ImageView icononebg2;
    @Bind({R.id.icononebg3})
    ImageView icononebg3;
    @Bind({R.id.icononebg4})
    ImageView icononebg4;
    private String integra;
    @Bind({R.id.jifen})
    TextView jifen;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.one1})
    TextView one1;
    @Bind({R.id.one2})
    TextView one2;
    @Bind({R.id.one3})
    TextView one3;
    @Bind({R.id.one4})
    TextView one4;
    @Bind({R.id.one5})
    TextView one5;
    @Bind({R.id.show2})
    TextView show2;
    @Bind({R.id.show3})
    TextView show3;
    @Bind({R.id.show4})
    TextView show4;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.two1})
    TextView two1;
    @Bind({R.id.two2})
    TextView two2;
    @Bind({R.id.two3})
    TextView two3;
    @Bind({R.id.two4})
    TextView two4;
    @Bind({R.id.two5})
    TextView two5;

    public String ToDBC(String paramString) {
        char[] charArray = paramString.toCharArray();
        int i = 0;
        if (i < charArray.length) {
            if (charArray[i] == '　') {
                charArray[i] = 32;

            }else{

                if ((charArray[i] > 65280) && (charArray[i] < 65375)) {
                    charArray[i] = ((char) (charArray[i] - 65248));
                }
            }
        }
        return new String(paramString);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_hui_yuan);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);
        this.titles.setText("我的会员");

        this.integra = getIntent().getStringExtra("integrass");
        this.grade = getIntent().getStringExtra("grade");
        this.jifen.setText("积分：" + this.integra);
        this.dengji.setText("LV." + this.grade);
        this.show4.setText(ToDBC("4.具体消费100元,获得1个积分(完成订单后积分总数为10+积分)"));
        this.show3.setText(ToDBC("3.分享朋友圈、微信/1 次获得 1 个积分 "));
        this.show2.setText(ToDBC("2.完成一笔订单,包括油卡充值、车辆救援等业务,获得5个积分"));
        Integer.parseInt(this.integra);
        if (this.grade.equals("1")) {
            this.huoyuanname.setText("初始会员");
            this.icononebg.setVisibility(View.VISIBLE);
            return;
        }
        if (this.grade.equals("2")) {
            this.huoyuanname.setText("铜牌会员");
            this.icononebg1.setVisibility(View.VISIBLE);
            return;
        }
        if (this.grade.equals("3")) {
            this.huoyuanname.setText("银牌会员");
            this.icononebg2.setVisibility(View.VISIBLE);
            return;
        }
        if (this.grade.equals("4")) {
            this.huoyuanname.setText("金牌会员");
            this.icononebg3.setVisibility(View.VISIBLE);
            return;
        }
        this.huoyuanname.setText("钻石会员");
        this.icononebg4.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.leftimfan})
    public void onViewClicked() {
        finish();
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyHuiYuanActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */