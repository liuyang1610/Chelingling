package com.beijing.chelingling.unit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beijing.chelingling.R;


public class MyQueDingDialog
        extends Dialog {
    private String messageStr;
    private Button no;
    private onNoOnclickListener noOnclickListener;
    private String noStr;
    private String titleStr;
    private TextView titleTv;
    private Button yes;
    private onYesOnclickListener yesOnclickListener;
    private String yesStr;

    public MyQueDingDialog(Context paramContext) {
        super(paramContext, R.style.MyDialog);
    }

    private void initData() {
        if (this.titleStr != null) {
            this.titleTv.setText(this.titleStr);
        }
        if ((this.messageStr == null) || (this.yesStr != null)) {
            this.yes.setText(this.yesStr);
        }
        if (this.noStr != null) {
            this.no.setText(this.noStr);
        }
    }

    private void initEvent() {
        this.yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (MyQueDingDialog.this.yesOnclickListener != null) {
                    MyQueDingDialog.this.yesOnclickListener.onYesClick();
                }
            }
        });
        this.no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (MyQueDingDialog.this.noOnclickListener != null) {
                    MyQueDingDialog.this.noOnclickListener.onNoClick();
                }
            }
        });
    }

    private void initView() {
        this.yes = ((Button) findViewById(R.id.yes));
        this.no = ((Button) findViewById(R.id.no));
        this.titleTv = ((TextView) findViewById(R.id.title));
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.free_exercise_sure_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        initEvent();
    }

    public void setMessage(String paramString) {
        this.messageStr = paramString;
    }

    public void setNoOnclickListener(String paramString, onNoOnclickListener paramonNoOnclickListener) {
        if (paramString != null) {
            this.noStr = paramString;
        }
        this.noOnclickListener = paramonNoOnclickListener;
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

    public static abstract interface onNoOnclickListener {
        public abstract void onNoClick();
    }

    public static abstract interface onYesOnclickListener {
        public abstract void onYesClick();
    }
}


