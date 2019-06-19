package com.beijing.chelingling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyChaKanChongZhiJiLuActivity extends AppCompatActivity {

    @Bind(R.id.leftimfan)
    ImageView leftimfan;
    @Bind(R.id.titles)
    TextView titles;
    @Bind(R.id.titlke)
    RelativeLayout titlke;
    @Bind(R.id.jiaqian)
    TextView jiaqian;
    @Bind(R.id.left)
    TextView left;
    @Bind(R.id.numcontent)
    TextView numcontent;
    @Bind(R.id.right)
    TextView right;

    private String prices;
    private String num_cz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cha_kan_chong_zhi_ji_lu);
        ButterKnife.bind(this);

        prices = getIntent().getStringExtra("prices");
        num_cz = getIntent().getStringExtra("num_cz");

//        DecimalFormat myformat=new java.text.DecimalFormat("0.00");
//        String str = myformat.format((Double.parseDouble(prices)));

        jiaqian.setText(prices);

        if (jiaqian.getText().toString().trim().equals("null")){
            jiaqian.setText("0");
        }

        //numcontent.setText(12 - (Integer.parseInt(num_cz)) + "");

        numcontent.setText(num_cz + "");

    }

    @OnClick(R.id.leftimfan)
    public void onViewClicked() {
        finish();
    }
}
