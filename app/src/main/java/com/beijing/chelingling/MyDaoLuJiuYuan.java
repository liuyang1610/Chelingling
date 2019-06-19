package com.beijing.chelingling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDaoLuJiuYuan
  extends AppCompatActivity
{
  @Bind({R.id.leftimfan})
  ImageView leftimfan;
  @Bind({R.id.tanchuimage})
  ImageView tanchuimage;
  @Bind({R.id.titles})
  TextView titles;
  @Bind({R.id.titlke})
  RelativeLayout titlke;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_my_dao_lu_jiu_yuan);
    getWindow().clearFlags(1024);
    ButterKnife.bind(this);
    this.titles.setText("车零零SOS道路救援");
  }
  
  @OnClick({R.id.leftimfan})
  public void onViewClicked()
  {
    finish();
  }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyDaoLuJiuYuan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */