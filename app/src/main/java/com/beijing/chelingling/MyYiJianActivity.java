package com.beijing.chelingling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.chelingling.imagexuanze.GridViewAdapter;
import com.beijing.chelingling.imagexuanze.PictureSelectorConfig;
import com.beijing.chelingling.imagexuanze.PlusImageActivity;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.FileUtillss;
import com.beijing.chelingling.unit.MyShowDialog;
import com.beijing.chelingling.unit.OkGoUtil;
import com.beijing.chelingling.unit.Util;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyYiJianActivity
        extends AppCompatActivity {
    private int a = 0;
    private int ab = 0;
    @Bind({R.id.asdfs})
    TextView asdfs;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 1) {
                istrue = true;
                progressDialog.hide();
                myShowDialog = new MyShowDialog(MyYiJianActivity.this);
                try {
                    myShowDialog.show();
                } catch (Exception e) {
                }

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(3000L);
                            try {
                                myShowDialog.dismiss();
                            } catch (Exception e) {
                            }
                            finish();
                            return;
                        } catch (InterruptedException localInterruptedException) {
                            localInterruptedException.printStackTrace();
                        }
                    }
                }).start();
            }
            if (paramAnonymousMessage.arg1 == 2) {
                MyYiJianActivity.this.myShowDialog.dismiss();
                MyYiJianActivity.this.finish();
            }
            return false;
        }
    });
    private List<String> imageurl = new ArrayList();
    private boolean istrue = true;
    @Bind({R.id.jianyi})
    EditText jianyi;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    @Bind({R.id.lianxifangshi})
    EditText lianxifangshi;
    private GridViewAdapter mGridViewAddImgAdapter;
    private ArrayList<String> mPicList = new ArrayList();
    private MyShowDialog myShowDialog;
    @Bind({R.id.noScrollgridview})
    GridView noScrollgridview;
    @Bind({R.id.numsdf})
    TextView numsdf;
    private String pic = "";
    private ProgressDialog progressDialog;
    @Bind({R.id.statgss})
    TextView statgss;
    StringCallback stringCallback = new StringCallback() {
        public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
            super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
            Log.i("sadsadasdas", "错误");
        }

        public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {

            Log.i("sadsadasdas", paramAnonymousString);

            try {
                String s = (String) new JSONObject(paramAnonymousString).get("data");
                Log.i("padddddd", paramAnonymousString);
                imageurl.add(s);
                if (a >= mPicList.size()) {
                    Log.i("padddddd", "完成，完成");
                    FileUtillss.deleteDir();
                    StringBuffer stringBuffer = new StringBuffer();

                    for (int i = 0; i < imageurl.size(); i++) {
                        if (i >= MyYiJianActivity.this.imageurl.size()) {
                            break;
                        }
                        if (i == MyYiJianActivity.this.imageurl.size() - 1) {
                            stringBuffer.append(imageurl.get(i));
                            break;
                        }
                        stringBuffer.append(imageurl.get(i) + ",");
                    }
                    pic = stringBuffer.toString();
                    postByOkGo(ChangWeb.YUMING + "/index/opinionss.html");
                }
            } catch (JSONException e) {

            }
        }
    };
    private String[] strs;
    @Bind({R.id.titles})
    TextView titles;

    private void postByOkGo(String paramString) {
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("pic", this.pic, new boolean[0])
                .params("content", this.jianyi.getText().toString(), new boolean[0])
                .params("tel", this.lianxifangshi.getText().toString().trim(), new boolean[0])
                .params("timestamp", Util.getTime(), new boolean[0])
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])
                .execute(new StringCallback() {
                    public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                        super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                        com.tencent.mm.opensdk.utils.Log.i("padddddd", paramAnonymousException.toString() + "sadsad");
                    }

                    public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                        com.tencent.mm.opensdk.utils.Log.i("padddddd", paramAnonymousString);
                        try {
                            JSONObject jsonObject = new JSONObject(paramAnonymousString);
                            if (jsonObject.get("code").equals("000")) {
                                Message message = new Message();
                                message.arg1 = 1;
                                handler.sendMessage(message);
                            } else if (jsonObject.get("code").equals("400")) {

                            }

                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        Log.i("fdfajhg", "compressPath" + "!1111");
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            Log.i("fdfajhg", "compressPath" + "!2222");
            if (localMedia.isCompressed()) {
                Log.i("fdfajhg", "compressPath" + "!3333");
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                Log.i("fdfajhg", compressPath);
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            } else {
                Log.i("fdfajhg", picList.get(0).getPath());
            }
        }

    }

    private void selectPic(int paramInt) {
        PictureSelectorConfig.initMultiConfig(this, paramInt);
    }

    private void viewPluImg(int paramInt) {
        Intent localIntent = new Intent(this, PlusImageActivity.class);
        localIntent.putStringArrayListExtra("img_list", this.mPicList);
        localIntent.putExtra("position", paramInt);
        startActivityForResult(localIntent, 10);
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        if ((paramInt2 == -1) && (paramInt1 == 188)) {
            Log.i("onActivityResult", "99999*******&&^^^");
            refreshAdapter(PictureSelector.obtainMultipleResult(paramIntent));
        }
        if ((paramInt1 == 10) && (paramInt2 == 11)) {
            ArrayList<String> toDeletePicList = paramIntent.getStringArrayListExtra("img_list");
            this.mPicList.clear();
            this.mPicList.addAll(toDeletePicList);
            this.mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_yi_jian);
        getWindow().clearFlags(1024);
        ButterKnife.bind(this);
        this.titles.setText("意见反馈");
        this.strs = Util.load(this).split(",");
        this.mGridViewAddImgAdapter = new GridViewAdapter(this, this.mPicList);
        this.noScrollgridview.setAdapter(this.mGridViewAddImgAdapter);
        this.noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
                if (paramAnonymousInt == paramAnonymousAdapterView.getChildCount() - 1) {
                    if (MyYiJianActivity.this.mPicList.size() == 6) {
                        MyYiJianActivity.this.viewPluImg(paramAnonymousInt);
                        return;
                    }
                    MyYiJianActivity.this.selectPic(6 - MyYiJianActivity.this.mPicList.size());
                    return;
                }
                MyYiJianActivity.this.viewPluImg(paramAnonymousInt);
            }
        });
    }

    @OnClick({R.id.leftimfan, R.id.statgss})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            case R.id.leftimfan:
                finish();
                break;
            case R.id.statgss:
                if (this.istrue == true) {
                    if (this.jianyi.getText().toString().length() <= 0) {
                        Toast.makeText(this, "请填写意见反馈内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (this.lianxifangshi.getText().toString().trim().length() <= 0) {
                        Toast.makeText(this, "请填写联系方式", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    this.istrue = false;
                    this.progressDialog = ProgressDialog.show(this, "提示", "正在上传中……");
                    Log.i("asdsadas", "sadsadas");

                    if (mPicList.size() > 0) {
                        for (int i = 0; i < mPicList.size(); i++) {
                            a += 1;
                            File file = new File(mPicList.get(i));
                            OkGoUtil.upiconloadFile(this, ChangWeb.YUMING + "/index/opinion_pics.html", "pic", file, this.stringCallback);
                        }
                    } else {
                        postByOkGo(ChangWeb.YUMING + "/index/opinionss.html");
                    }

                }
                break;
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MyYiJianActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */