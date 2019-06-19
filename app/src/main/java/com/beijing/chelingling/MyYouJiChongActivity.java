package com.beijing.chelingling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyYouJiChongActivity
        extends AppCompatActivity {
    @Bind({R.id.baocun})
    Button baocun;
    @Bind({R.id.fapiao})
    EditText fapiao;
    private String fapiaos;
    @Bind({R.id.gouxuan})
    ImageView gouxuan;
    @Bind({R.id.gouxuand})
    TextView gouxuand;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.shoujianname})
    EditText shoujianname;
    @Bind({R.id.shoujihaoma})
    EditText shoujihaoma;
    private String shoujihaos;
    @Bind({R.id.ss})
    ImageView ss;
    @Bind({R.id.ss1})
    ImageView ss1;
    @Bind({R.id.ss2})
    ImageView ss2;
    @Bind({R.id.ss3})
    ImageView ss3;
    @Bind({R.id.sste})
    TextView sste;
    @Bind({R.id.sste1})
    TextView sste1;
    @Bind({R.id.sste2})
    TextView sste2;
    @Bind({R.id.sste3})
    TextView sste3;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    private String xingmings;
    @Bind({R.id.youjidizhi})
    EditText youjidizhi;
    private String youjidizhis;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_you_ji_chong);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);
        this.titles.setText("邮寄充值");
        this.youjidizhis = getIntent().getStringExtra("youjidizhi");
        this.xingmings = getIntent().getStringExtra("xingming");
        this.shoujihaos = getIntent().getStringExtra("shoujihao");
        this.fapiaos = getIntent().getStringExtra("fapiao");
        if (!this.youjidizhis.equals("")) {
            this.youjidizhi.setText(this.youjidizhis);
        }
        if (!this.xingmings.equals("")) {
            this.shoujianname.setText(this.xingmings);
        }
        if (!this.shoujihaos.equals("")) {
            this.shoujihaoma.setText(this.shoujihaos);
        }
        if (!this.fapiaos.equals("")) {
            this.fapiao.setText(this.fapiaos);
        }
    }

    @OnClick({R.id.leftimfan, R.id.baocun})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.baocun:
                if ((this.shoujianname.getText().toString().trim().length() > 0) && (this.shoujihaoma.getText().toString().trim().length() > 0) && (this.youjidizhi.getText().toString().trim().length() > 0) && (this.fapiao.getText().toString().trim().length() > 0)) {
                    Intent intent = new Intent();
                    intent.putExtra("shoujianname", this.shoujianname.getText().toString());
                    intent.putExtra("shoujihao", this.shoujihaoma.getText().toString());
                    intent.putExtra("youjidizhi", this.youjidizhi.getText().toString());
                    intent.putExtra("fapiao", this.fapiao.getText().toString());
                    setResult(5, intent);
                    finish();
                    return;
                }
                Toast.makeText(this, "请填写全部信息后保存", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyYouJiChongActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */