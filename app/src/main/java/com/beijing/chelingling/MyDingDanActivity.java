package com.beijing.chelingling;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.chelingling.adapter.MyTabAdapter;
import com.beijing.chelingling.fragment.MyDaiZhiFuFragment;
import com.beijing.chelingling.fragment.MyJinXingZhFragment;
import com.beijing.chelingling.fragment.MyYiWanChengFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDingDanActivity
        extends AppCompatActivity {
    private MyTabAdapter adapter;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.tablayout})
    TabLayout tablayout;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.viewpagerss})
    ViewPager viewpagerss;

    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("待支付");
        //add("进行中");
        add("已完成");
    }};

    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(new MyDaiZhiFuFragment());
        //add(new MyJinXingZhFragment());
        add(new MyYiWanChengFragment());
    }};

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_ding_dan);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.titles.setText("我的订单");
        this.adapter = new MyTabAdapter(getSupportFragmentManager(), this.titleList, this.fragmentList);
        this.viewpagerss.setAdapter(this.adapter);
        this.tablayout.setupWithViewPager(this.viewpagerss);
    }

    @OnClick({R.id.leftimfan})
    public void onViewClicked() {
        finish();
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyDingDanActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */