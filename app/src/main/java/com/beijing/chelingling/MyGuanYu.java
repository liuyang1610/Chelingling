package com.beijing.chelingling;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.unit.MyQueDingDialog;
import com.beijing.chelingling.unit.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyGuanYu
        extends AppCompatActivity {
    @Bind({R.id.guanfangphone})
    RelativeLayout guanfangphone;
    @Bind({R.id.guangfangweichat})
    RelativeLayout guangfangweichat;
    @Bind({R.id.im43})
    ImageView im43;
    @Bind({R.id.im44})
    ImageView im44;
    @Bind({R.id.im45})
    ImageView im45;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private MyQueDingDialog myQueDingDialog;
    @Bind({R.id.phone})
    TextView phone;
    @Bind({R.id.rights})
    TextView rights;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.titlke})
    RelativeLayout titlke;
    @Bind({R.id.wangfangweb})
    RelativeLayout wangfangweb;
    @Bind({R.id.web})
    TextView web;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main2);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.titles.setText("关于我们");
        this.web.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("WrongConstant")
            public boolean onLongClick(View paramAnonymousView) {
                ((ClipboardManager) MyGuanYu.this.getSystemService("clipboard")).setText(MyGuanYu.this.web.getText().toString().trim());
                Toast.makeText(MyGuanYu.this, "复制成功", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        this.rights.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("WrongConstant")
            public boolean onLongClick(View paramAnonymousView) {
                ((ClipboardManager) MyGuanYu.this.getSystemService("clipboard")).setText(MyGuanYu.this.rights.getText().toString().trim());
                Toast.makeText(MyGuanYu.this, "复制成功", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        this.phone.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("WrongConstant")
            public boolean onLongClick(View paramAnonymousView) {
                ((ClipboardManager) MyGuanYu.this.getSystemService("clipboard")).setText(MyGuanYu.this.phone.getText().toString().trim());
                Toast.makeText(MyGuanYu.this, "复制成功", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @OnClick({R.id.leftimfan, R.id.guangfangweichat, R.id.wangfangweb, R.id.guanfangphone})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            case R.id.guangfangweichat:
            case R.id.wangfangweb:
            default:
                return;
            case R.id.leftimfan:
                finish();
                return;
            case R.id.guanfangphone:
                this.myQueDingDialog = new MyQueDingDialog(this);
                this.myQueDingDialog.setTitle("400-816-5355");
                this.myQueDingDialog.setYesOnclickListener("确定", new MyQueDingDialog.onYesOnclickListener() {
                    public void onYesClick() {
                        Util.call(MyGuanYu.this, "400-816-5355");
                        MyGuanYu.this.myQueDingDialog.dismiss();
                    }
                });
                this.myQueDingDialog.setNoOnclickListener("取消", new MyQueDingDialog.onNoOnclickListener() {
                    public void onNoClick() {
                        MyGuanYu.this.myQueDingDialog.dismiss();
                    }
                });
                this.myQueDingDialog.show();
                break;
        }

    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyGuanYu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */