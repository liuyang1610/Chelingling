package com.beijing.chelingling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyZhiFuYesActivity
        extends AppCompatActivity {
    @Bind({R.id.chongzhifangshiim})
    ImageView chongzhifangshiim;
    @Bind({R.id.chongzhifangshite})
    TextView chongzhifangshite;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.xuanzetaocan})
    ImageView xuanzetaocan;
    @Bind({R.id.xuanzezhfuim})
    ImageView xuanzezhfuim;
    @Bind({R.id.xuanzezhfute})
    TextView xuanzezhfute;
    @Bind({R.id.zhhifuchenggongim})
    ImageView zhhifuchenggongim;
    @Bind({R.id.zhhifuchenggongte})
    TextView zhhifuchenggongte;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_zhi_fu_yes);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);
        this.titles.setText("支付成功");
    }

    @OnClick({R.id.leftimfan})
    public void onViewClicked() {
        finish();
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyZhiFuYesActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */