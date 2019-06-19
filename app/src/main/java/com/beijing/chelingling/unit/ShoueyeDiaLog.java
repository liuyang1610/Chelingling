package com.beijing.chelingling.unit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.chelingling.R;

public class ShoueyeDiaLog
        extends Dialog {
    private String messageStr;
    private MyQueDingDialog.onNoOnclickListener noOnclickListener;
    private String noStr;
    private String titleStr;
    private TextView titleTv;
    private LinearLayout tupians;
    private MyQueDingDialog.onYesOnclickListener yesOnclickListener;
    private String yesStr;

    public ShoueyeDiaLog(Context paramContext) {
        super(paramContext, R.style.MyDialog);
        setOwnerActivity((Activity) paramContext);
    }

    private void initData() {
        if ((this.titleStr == null) || (this.messageStr != null)) {
        }
    }

    private void initEvent() {
        this.tupians.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (ShoueyeDiaLog.this.yesOnclickListener != null) {
                    ShoueyeDiaLog.this.yesOnclickListener.onYesClick();
                }
            }
        });
    }

    private void initView() {
        this.tupians = ((LinearLayout) findViewById(R.id.tupians));
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.shouyehow);
        setCanceledOnTouchOutside(true);
        initView();
        initData();
        initEvent();
    }

    public void setMessage(String paramString) {
        this.messageStr = paramString;
    }

    public void setNoOnclickListener(String paramString, MyQueDingDialog.onNoOnclickListener paramonNoOnclickListener) {
        if (paramString != null) {
            this.noStr = paramString;
        }
        this.noOnclickListener = paramonNoOnclickListener;
    }

    public void setTitle(String paramString) {
        this.titleStr = paramString;
    }

    public void setYesOnclickListener(String paramString, MyQueDingDialog.onYesOnclickListener paramonYesOnclickListener) {
        if (paramString != null) {
            this.yesStr = paramString;
        }
        this.yesOnclickListener = paramonYesOnclickListener;
    }

    public static abstract interface onNoOnclickListener {
        public abstract void onNoClick();
    }

    public static abstract interface onYesOnclickListener {
        public abstract void onYesClick();
    }
}

