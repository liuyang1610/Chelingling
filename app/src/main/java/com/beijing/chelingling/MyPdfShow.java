package com.beijing.chelingling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joanzapata.pdfview.PDFView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPdfShow extends AppCompatActivity {

    @Bind(R.id.leftimfan)
    ImageView leftimfan;
    @Bind(R.id.titles)
    TextView titles;
    @Bind(R.id.titlke)
    RelativeLayout titlke;

    private String urls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pdf_show);
        ButterKnife.bind(this);
        this.urls = getIntent().getStringExtra("url");

    }

    @OnClick({R.id.leftimfan, R.id.titles})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leftimfan:
                break;
            case R.id.titles:
                break;
        }
    }
}
