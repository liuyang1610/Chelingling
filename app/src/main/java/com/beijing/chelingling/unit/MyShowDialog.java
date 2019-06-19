package com.beijing.chelingling.unit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beijing.chelingling.R;


public class MyShowDialog
        extends Dialog {
    private String messageStr;
    private String noStr;
    private String titleStr;
    private TextView titleTv;
    private onYesOnclickListener yesOnclickListener;
    private String yesStr;

    public MyShowDialog(Context paramContext) {
        super(paramContext, R.style.MyDialog);
    }

    private void initData() {
        if (this.titleStr != null) {
            this.titleTv.setText(this.titleStr);
        }
        if ((this.messageStr == null) || ((this.yesStr == null) || (this.noStr != null))) {
        }
    }

    private void initEvent() {
        this.titleTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (MyShowDialog.this.yesOnclickListener != null) {
                    MyShowDialog.this.yesOnclickListener.onYesClick();
                }
            }
        });
    }

    private void initView() {
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.tijiaochenggongdialog);
        setCanceledOnTouchOutside(false);
        initData();
    }

    public void setMessage(String paramString) {
        this.messageStr = paramString;
    }

    public void setTitle(String paramString) {
        this.titleStr = paramString;
    }

    public void setYesOnclickListener(String paramString, onYesOnclickListener paramonYesOnclickListener) {
        if (paramString != null) {
            this.yesStr = paramString;
        }
        this.yesOnclickListener = paramonYesOnclickListener;
    }

    public static abstract interface onYesOnclickListener {
        public abstract void onYesClick();
    }
}

