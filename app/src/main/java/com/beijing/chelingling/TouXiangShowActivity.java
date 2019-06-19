package com.beijing.chelingling;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TouXiangShowActivity
        extends AppCompatActivity {
    @Bind({R.id.iconshow})
    ImageView iconshow;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_tou_xiang_show);
        ButterKnife.bind(this);
        String str = getIntent().getStringExtra("pic");
        Picasso.get().load(str).into(this.iconshow);
    }

    @OnClick({R.id.iconshow})
    public void onViewClicked() {
        finish();
    }


}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\TouXiangShowActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */