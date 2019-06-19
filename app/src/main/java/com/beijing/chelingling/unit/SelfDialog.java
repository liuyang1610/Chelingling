package com.beijing.chelingling.unit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beijing.chelingling.R;


public class SelfDialog
  extends Dialog
{
  private String messageStr;
  private String noStr;
  private String titleStr;
  private TextView titleTv;
  private TextView titles;
  private onYesOnclickListener yesOnclickListener;
  private String yesStr;
  
  public SelfDialog(Context paramContext)
  {
    super(paramContext, R.style.MyDialog);
  }
  
  private void initData()
  {
    if (this.titleStr != null) {
      this.titleTv.setText(this.titleStr);
    }
    if ((this.messageStr == null) || ((this.yesStr == null) || (this.noStr != null))) {}
  }
  
  private void initEvent()
  {
    this.titles.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (SelfDialog.this.yesOnclickListener != null) {
          SelfDialog.this.yesOnclickListener.onYesClick();
        }
      }
    });
  }
  
  private void initView()
  {
    this.titleTv = ((TextView)findViewById(R.id.queding));
    this.titles = ((TextView)findViewById(R.id.titles));
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.myquedilog);
    setCanceledOnTouchOutside(false);
    initView();
    initData();
    initEvent();
  }
  
  public void setMessage(String paramString)
  {
    this.messageStr = paramString;
  }
  
  public void setTitle(String paramString)
  {
    this.titleStr = paramString;
  }
  
  public void setYesOnclickListener(String paramString, onYesOnclickListener paramonYesOnclickListener)
  {
    if (paramString != null) {
      this.yesStr = paramString;
    }
    this.yesOnclickListener = paramonYesOnclickListener;
  }
  
  public static abstract interface onYesOnclickListener
  {
    public abstract void onYesClick();
  }
}

