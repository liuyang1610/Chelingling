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

public class MyXianShangJuTiActivity
        extends AppCompatActivity {
    @Bind({R.id.baocun})
    Button baocun;
    @Bind({R.id.gouxuan})
    ImageView gouxuan;
    @Bind({R.id.gouxuand})
    TextView gouxuand;
    @Bind({R.id.kahao})
    EditText kahao;
    private String kahaos;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.shoujihao})
    EditText shoujihao;
    private String shoujihaos;
    @Bind({R.id.ss})
    ImageView ss;
    @Bind({R.id.ss1})
    ImageView ss1;
    @Bind({R.id.ss2})
    ImageView ss2;
    @Bind({R.id.sste})
    TextView sste;
    @Bind({R.id.sste1})
    TextView sste1;
    @Bind({R.id.sste3})
    TextView sste3;
    @Bind({R.id.tisf})
    ImageView tisf;
    @Bind({R.id.tisf1})
    ImageView tisf1;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.xingming})
    EditText xingming;
    private String xingmings;
    @Bind({R.id.zhongguoshihua})
    RelativeLayout zhongguoshihua;
    @Bind({R.id.zhongguoshiyou})
    RelativeLayout zhongguoshiyou;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_xian_shang_ju_ti);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);
        this.titles.setText("线上充值");

        this.kahaos = getIntent().getStringExtra("kahao");
        this.xingmings = getIntent().getStringExtra("xingming");
        this.shoujihaos = getIntent().getStringExtra("shoujihao");
        if (!this.kahaos.equals("")) {
            this.kahao.setText(this.kahaos);
        }
        if (!this.xingmings.equals("")) {
            this.xingming.setText(this.xingmings);
        }
        if (!this.shoujihaos.equals("")) {
            this.shoujihao.setText(this.shoujihaos);
        }
    }

    @OnClick({R.id.leftimfan, R.id.baocun, R.id.zhongguoshiyou})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.baocun:
                if ((this.kahao.getText().toString().trim().length() > 0) && (this.xingming.getText().toString().trim().length() > 0) && (this.shoujihao.getText().toString().trim().length() > 0)) {
                    Intent intent = new Intent();
                    intent.putExtra("kahao", this.kahao.getText().toString());
                    intent.putExtra("xingming", this.xingming.getText().toString());
                    intent.putExtra("shoujihao", this.shoujihao.getText().toString());
                    setResult(4, intent);
                    finish();
                    return;
                }
                Toast.makeText(this, "请填写全部信息后保存", Toast.LENGTH_SHORT).show();
                return;
            case R.id.zhongguoshiyou:
                Toast.makeText(this, "该选择尚未开通", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyXianShangJuTiActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */