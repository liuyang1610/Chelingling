package com.beijing.chelingling;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.chelingling.info.MyGeRenInfo;
import com.beijing.chelingling.unit.ActionSheetDialog;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.OkGoUtil;
import com.beijing.chelingling.unit.Util;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.squareup.picasso.Picasso;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MySetActivity
        extends AppCompatActivity {
    @Bind({R.id.genghuan})
    RelativeLayout genghuan;
    @Bind({R.id.genghuanziliao})
    RelativeLayout genghuanziliao;
    @Bind({R.id.guanyu})
    RelativeLayout guanyu;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                try {
                    if (!myGeRenInfo.getPic().equals("null")) {
                        Picasso.get().load(ChangWeb.YUMING + MySetActivity.this.myGeRenInfo.getPic()).into(MySetActivity.this.iconbg);
                    }
                } catch (Exception e) {

                }

            } else {

            }
            return false;
        }
    });
    private Bitmap head;
    @Bind({R.id.iconbg})
    CircleTextImageView iconbg;
    @Bind({R.id.im4})
    ImageView im4;
    @Bind({R.id.im43})
    ImageView im43;
    @Bind({R.id.im44})
    ImageView im44;
    @Bind({R.id.im45})
    ImageView im45;
    @Bind({R.id.leftimfan})
    ImageView leftimfan;
    private MyGeRenInfo myGeRenInfo;
    @Bind({R.id.myaddres})
    RelativeLayout myaddres;
    @Bind({R.id.rights})
    ImageView rights;
    StringCallback stringCallback = new StringCallback() {
        public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
            super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
            Log.i("sadsadasdas", "错误");
        }

        public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
            Log.i("sadsadasdas", paramAnonymousString);
            try {
                if (new JSONObject(paramAnonymousString).getString("code").equals("000")) {
                    postByOkGoIcon(ChangWeb.MYHUIYUANICON);
                }
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private String[] strs;
    public File tempFile;
    @Bind({R.id.titles})
    TextView titles;
    @Bind({R.id.touxiang})
    RelativeLayout touxiang;
    private Uri uritempFile;
    @Bind({R.id.yijianfan})
    RelativeLayout yijianfan;

    private void postByOkGoIcon(String paramString) {
        Log.i("mvbnbvnvb", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT).params("appid", this.strs[0], new boolean[0]).params("appsecret", this.strs[1], new boolean[0]).params("timestamp", Util.getTime(), new boolean[0]).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0]).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                Log.i("mvbnbvnvb", "错误");
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("mvbnbvnvb", paramAnonymousString);
                try {
                    Gson gson = new Gson();
                    myGeRenInfo = gson.fromJson(paramAnonymousString, MyGeRenInfo.class);

                    Message message = new Message();
                    message.arg1 = 0;
                    handler.sendMessage(message);
                    return;
                } catch (Exception e) {

                }
            }
        });
    }

    private String setPicToView(Bitmap mBitmap, String imagenames) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = imagenames;
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("WrongConstant")
    public void cropPhotoIcon(Uri paramUri) {
        Intent localIntent = new Intent("com.android.camera.action.CROP");
        localIntent.addFlags(1);
        localIntent.addFlags(2);
        localIntent.setDataAndType(paramUri, "image/*");
        localIntent.putExtra("crop", "true");
        localIntent.putExtra("aspectX", 1);
        localIntent.putExtra("aspectY", 1);
        localIntent.putExtra("outputX", 550);
        localIntent.putExtra("outputY", 550);
        uritempFile = Uri.parse("file:///" + Environment.getExternalStorageDirectory().getPath() + "/small.jpg");
        localIntent.putExtra("output", this.uritempFile);
        localIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(localIntent, 6);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4 && resultCode == RESULT_OK) {
            cropPhotoIcon(data.getData());// 裁剪图片
        }

        if (requestCode == 5 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri contentUri = FileProvider.getUriForFile(this, "game", tempFile);
                cropPhotoIcon(contentUri);
            } else {
                cropPhotoIcon(Uri.fromFile(tempFile));
            }
        }

        if (requestCode == 6) {
            if (data != null) {
                try {
                    head = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                } catch (FileNotFoundException e) {
                    Log.i("afsdf", "错误错误");
                    e.printStackTrace();
                }
//                    Bundle extras = data.getExtras();
//                    head = extras.getParcelable("data");
                if (head != null) {
                    /**
                     * 上传服务器代码
                     */

                    String a = setPicToView(head, "head.jpg");// 保存在SD卡中
                    Log.i("filesss", a + "        555");
                    //iconbg.setImageBitmap(head);// 用ImageView显示出来
                    File file = new File(a);

                    OkGoUtil.upiconloadFile(this, "http://cll.shiduweb.com/index/pics.html", "file0", file, stringCallback);
                }
            }
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_my_set);
        ButterKnife.bind(this);
        getWindow().clearFlags(1024);

    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if (paramInt == 4) {
            finish();
            //overridePendingTransition(R.anim.out_to_right, R.anim.out_to_left);
        }
        return true;
    }

    protected void onPause() {
        super.onPause();
        // MobclickAgent.onPause(this);
    }

    protected void onResume() {
        super.onResume();
        String sa = Util.load(this);
        this.strs = sa.split(",");
        postByOkGoIcon(ChangWeb.MYHUIYUANICON);
        //MobclickAgent.onResume(this);
    }

    @OnClick({R.id.leftimfan, R.id.touxiang, R.id.genghuan, R.id.guanyu, R.id.yijianfan, R.id.genghuanziliao, R.id.myaddres})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.leftimfan:
                finish();
                //overridePendingTransition(R.anim.out_to_right, R.anim.out_to_left);
                return;
            case R.id.touxiang:
                new ActionSheetDialog(this).builder().setCancelable(false).setCanceledOnTouchOutside(false).addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    public void onClick(int paramAnonymousInt) {
                        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), "bg.jpg");
                        //跳转到调用系统相机
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //判断版本
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
                            intent2.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            Uri contentUri = FileProvider.getUriForFile(MySetActivity.this, "game", tempFile);
                            intent2.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                            Log.e("dasd", contentUri.toString());
                        } else {    //否则使用Uri.fromFile(file)方法获取Uri
                            intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                        }
                        startActivityForResult(intent2, 5);// 采用ForResult打开
                    }
                }).addSheetItem("从相册中选取", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    public void onClick(int paramAnonymousInt) {
                        Intent localIntent = new Intent("android.intent.action.PICK", null);
                        localIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        MySetActivity.this.startActivityForResult(localIntent, 4);
                    }
                }).show();
                return;
            case R.id.genghuan:
                startActivity(new Intent(this, GenghuanphoneActivity.class));
                return;
            case R.id.guanyu:
                startActivity(new Intent(this, MyGuanYu.class));
                return;
            case R.id.yijianfan:
                startActivity(new Intent(this, MyYiJianActivity.class));
                return;
            case R.id.genghuanziliao:
                startActivity(new Intent(this, MHuiYuanZiLiaoActivity.class).putExtra("data", this.myGeRenInfo));
                return;
            case R.id.myaddres:
                startActivity(new Intent(this, MyAddreGuanLi.class).putExtra("flag", "1"));
                break;
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\MySetActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */