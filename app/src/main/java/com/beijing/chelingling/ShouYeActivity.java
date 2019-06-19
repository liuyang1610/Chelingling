package com.beijing.chelingling;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.fragment.MyFragment;
import com.beijing.chelingling.fragment.ShouYeFragment;
import com.beijing.chelingling.quanxian.PermissionsManager;
import com.beijing.chelingling.quanxian.PermissionsResultAction;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.beijing.chelingling.view.GifView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ShouYeActivity extends AppCompatActivity {
    private FragmentManager f;
    @Bind({R.id.fragments})
    FrameLayout fragments;
    private FragmentTransaction ft;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            istrue = false;
            return false;
        }
    });
    @Bind({R.id.home})
    ImageView home;
    @Bind({R.id.icons})
    ImageView icons;
    private boolean isExit = false;
    private boolean istrue = true;
    private LocationManager lm;
    private MyFragment myFragment;
    private boolean myself = true;
    private ShouYeFragment shouYeFragment;
    @Bind({R.id.shouye})
    TextView shouye;
    @Bind({R.id.shouyes})
    RelativeLayout shouyes;
    private boolean shouyess = false;
    @Bind({R.id.sos})
    GifView sos;
    private String[] strs;
    @Bind({R.id.viewclick})
    View viewclick;
    @Bind({R.id.wode})
    TextView wode;
    @Bind({R.id.wodes})
    RelativeLayout wodes;

    private void hideFragments(FragmentTransaction paramFragmentTransaction) {
        if (this.shouYeFragment != null) {
            paramFragmentTransaction.hide(this.shouYeFragment);
        }
        if (this.myFragment != null) {
            paramFragmentTransaction.hide(this.myFragment);
        }
    }

    private void postByOkGo(String paramString) {
        Log.i("sadsad", this.strs[0] + "           " + this.strs[1]);

        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("timestamp", Util.getTime(), new boolean[0])
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0]).
                execute(new StringCallback() {
                    public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                        super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                        Log.i("wandanle", "错误");
                    }

                    public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                        Log.i("wandanle", paramAnonymousString);
                    }
                });
    }

    private void setTabSelection(int paramInt) {

        FragmentTransaction transaction = f.beginTransaction();
        transaction.hide(shouYeFragment);
        hideFragments(transaction);
        switch (paramInt) {
            case 0:
                if (shouYeFragment == null) {
                    shouYeFragment = new ShouYeFragment();
                    transaction.add(R.id.fragments, shouYeFragment);
                } else {
                    transaction.show(shouYeFragment);
                }
                break;
            case 1:
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    transaction.add(R.id.fragments, myFragment);
                } else {
                    transaction.show(myFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();

    }

    public void exit() {
        if (!this.isExit) {
            this.isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            this.handler.sendEmptyMessageDelayed(0, 2000L);
            return;
        }
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.HOME");
        startActivity(localIntent);
        System.exit(0);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_shou_ye);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            public void onDenied(String paramAnonymousString) {
            }

            public void onGranted() {
            }
        });
        this.sos.setMovieResource(R.raw.shuibowen);
        String s = Util.load(this);
        Log.i("sadsad", paramBundle + "  %%%%%%%%%");
        this.strs = s.split(",");
        postByOkGo(ChangWeb.MYMEIRIOPENAPP);
        this.f = getSupportFragmentManager();
        this.ft = this.f.beginTransaction();
        this.shouYeFragment = new ShouYeFragment();
        this.ft.add(R.id.fragments, this.shouYeFragment);
        this.ft.commit();
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if (paramInt == 4) {
            exit();
            return false;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }

    protected void onResume() {
        super.onResume();
        this.istrue = true;
    }

    @OnClick({R.id.shouyes, R.id.wodes, R.id.sos, R.id.viewclick})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            case R.id.shouyes:
                this.shouyess = false;
                this.myself = true;
                this.home.setBackgroundResource(R.drawable.icon_home_act);
                this.shouye.setTextColor(Color.parseColor("#049ff9"));
                this.icons.setBackgroundResource(R.drawable.icon_personal);
                this.wode.setTextColor(Color.parseColor("#000000"));
                setTabSelection(0);
                break;
            case R.id.wodes:
                this.myself = false;
                this.shouyess = true;
                this.home.setBackgroundResource(R.drawable.icon_home);
                this.shouye.setTextColor(Color.parseColor("#000000"));
                this.icons.setBackgroundResource(R.drawable.icon_personal_act);
                this.wode.setTextColor(Color.parseColor("#049ff9"));
                setTabSelection(1);
                break;
            case R.id.sos:
                if (istrue == true) {
                    startActivity(new Intent(this, MyMapActivity.class));
                } else {
                    Toast.makeText(this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction("android.settings.LOCATION_SOURCE_SETTINGS");
                    startActivity(intent);
                }
                break;
            case R.id.viewclick:

                break;
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\ShouYeActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */