package com.beijing.chelingling.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.chelingling.MyDingDanActivity;
import com.beijing.chelingling.MyGengDuoActivity;
import com.beijing.chelingling.MyHuiYuanActivity;
import com.beijing.chelingling.MyJiFenActivity;
import com.beijing.chelingling.MyLoveCarActivity;
import com.beijing.chelingling.MyXiuGaiMiMaActivity;
import com.beijing.chelingling.PhoneNumActivity;
import com.beijing.chelingling.R;
import com.beijing.chelingling.TouXiangShowActivity;
import com.beijing.chelingling.unit.ActionSheetDialog;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.MyApp;
import com.beijing.chelingling.unit.MyQueDingDialog;
import com.beijing.chelingling.unit.OkGoUtil;
import com.beijing.chelingling.unit.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

public class MyFragment
        extends Fragment {
    private static final int THUMB_SIZE = 200;

    @Bind({R.id.addcar})
    RelativeLayout addcar;
    private AlertDialog dialog;
    private AlertDialog dialog1;
    @Bind({R.id.dingdan})
    RelativeLayout dingdan;
    @Bind({R.id.editss})
    RelativeLayout editss;
    @Bind({R.id.gengduo})
    RelativeLayout gengduo;
    @Bind(R.id.beijingbac)
    ImageView beijingbac;
    @Bind(R.id.xiugaimima)
    RelativeLayout xiugaimima;

    private String grade;
    private Handler handler = new Handler(new Handler.Callback() {
        @SuppressLint({"ResourceType"})
        public boolean handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.arg1 == 0) {
                if (!MyFragment.this.name.equals("null")) {
                    MyFragment.this.myname.setText(MyFragment.this.name);
                } else {
                    MyFragment.this.myname.setText(MyFragment.this.tel);
                }

                MyFragment.this.myline.setBackgroundResource(R.drawable.huiyuan);
                MyFragment.this.myline.setText("LV." + MyFragment.this.grade);

                Log.i("dsfsd", pic);
                Picasso.get().load(ChangWeb.YUMING + pic).into(myicon);
                Picasso.get().load(ChangWeb.YUMING + pic).fit().transform(new BlurTransformation(getContext())).into(beijingbac);


                MyFragment.this.myicon.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramAnonymous2View) {
                        MyFragment.this.startActivity(new Intent(MyFragment.this.getContext(), TouXiangShowActivity.class).putExtra("pic", ChangWeb.YUMING + MyFragment.this.pic));
                    }
                });
            }
            if (paramAnonymousMessage.arg1 == 1) {
                MyFragment.this.myicon.setBackgroundResource(R.drawable.touxiang);
                if (!MyFragment.this.name.equals("null")) {
                    MyFragment.this.myname.setText(MyFragment.this.name);
                } else {
                    MyFragment.this.myname.setText(MyFragment.this.tel);
                }
                MyFragment.this.myline.setBackgroundResource(R.drawable.huiyuan);
                MyFragment.this.myline.setText("LV." + MyFragment.this.grade);
            }
            return false;
        }
    });
    private Bitmap head;
    @Bind({R.id.im1})
    ImageView im1;
    @Bind({R.id.im2})
    ImageView im2;
    @Bind({R.id.im3})
    ImageView im3;
    @Bind({R.id.im4})
    ImageView im4;
    private String integra;
    @Bind({R.id.jifen})
    RelativeLayout jifen;
    @Bind({R.id.lianxikefu})
    RelativeLayout lianxikefu;
    private MyQueDingDialog myQueDingDialog;
    @Bind({R.id.myicon})
    CircleTextImageView myicon;
    @Bind({R.id.myline})
    TextView myline;
    @Bind({R.id.myname})
    TextView myname;
    private String name;
    private String pic;
    StringCallback stringCallback = new StringCallback() {
        public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
            super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
            Log.i("sadsadasdas", "错误");
        }

        public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
            Log.i("sadsadasdas", paramAnonymousString);
            try {
                if (new JSONObject(paramAnonymousString).getString("code").equals("000")) {
                    MyFragment.this.postByOkGoIcon(ChangWeb.MYHUIYUANICON);
                }
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private String[] strs;
    private String tel;
    public File tempFile;
    @Bind({R.id.tu11})
    ImageView tu11;
    @Bind({R.id.tu4})
    ImageView tu4;
    @Bind({R.id.tu42})
    ImageView tu42;
    private Uri uritempFile;
    @Bind({R.id.yaoqing})
    RelativeLayout yaoqing;

    public static byte[] bmpToByteArray(Bitmap paramBitmap, boolean paramBoolean) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
        if (paramBoolean) {
            paramBitmap.recycle();
        }
        try {
            localByteArrayOutputStream.close();
            return localByteArrayOutputStream.toByteArray();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return localByteArrayOutputStream.toByteArray();
    }

    private String buildTransaction(String paramString) {
        if (paramString == null) {
            return String.valueOf(System.currentTimeMillis());
        }
        return paramString + System.currentTimeMillis();
    }

    public static Bitmap drawableBitmapOnWhiteBg(Context paramContext, Bitmap paramBitmap) {
        Bitmap localBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        localCanvas.drawColor(paramContext.getResources().getColor(R.color.weixinfenxiang));
        localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, new Paint());
        return localBitmap;
    }

    public static Bitmap drawableBitmapOnWhiteBgtwo(Context paramContext, Bitmap paramBitmap) {
        Bitmap localBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        localCanvas.drawColor(paramContext.getResources().getColor(R.color.wight));
        localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, new Paint());
        return localBitmap;
    }

    private void postByOkGoIcon(String paramString) {
        Log.i("mvbnbvnvb", this.strs[0] + "           " + this.strs[1]);
        OkGo.post(paramString).tag(this).cacheKey("cachePostKey").cacheMode(CacheMode.DEFAULT).params("appid", this.strs[0], new boolean[0]).params("appsecret", this.strs[1], new boolean[0]).params("timestamp", Util.getTime(), new boolean[0]).params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0]).execute(new StringCallback() {
            public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                Log.i("mvbnbvnvb", "错误");
            }

            public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                Log.i("mvbnbvnvbiconsa", paramAnonymousString);
                try {
                    JSONObject jsonObject = new JSONObject(paramAnonymousString);
                    pic = jsonObject.getString("pic");
                    tel = jsonObject.getString("tel");
                    grade = jsonObject.getString("grade");
                    integra = jsonObject.getString("integra");
                    name = jsonObject.getString("name");

                    if (pic.equals("null")) {
                        Message message = new Message();
                        message.arg1 = 1;
                        handler.sendMessage(message);

                    } else {
                        Message message = new Message();
                        message.arg1 = 0;
                        handler.sendMessage(message);
                    }
                    return;
                } catch (Exception e) {

                }
            }
        });
    }

    private String setPicToView(Bitmap paramBitmap, String paramString) {
        Object localObject = new File(Environment.getExternalStorageDirectory().getPath());
        if (!((File) localObject).exists()) {
            ((File) localObject).mkdir();
        }
        tempFile = new File((File) localObject, paramString);
        try {
            localObject = new FileOutputStream(paramString);
            paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream) localObject);
            ((FileOutputStream) localObject).flush();
            ((FileOutputStream) localObject).close();
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cropPhotoIcon(Uri paramUri) {
        Intent localIntent = new Intent("com.android.camera.action.CROP");
        localIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        localIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        localIntent.setDataAndType(paramUri, "image/*");
        localIntent.putExtra("crop", "true");
        localIntent.putExtra("aspectX", 1);
        localIntent.putExtra("aspectY", 1);
        localIntent.putExtra("outputX", 150);
        localIntent.putExtra("outputY", 150);
        this.uritempFile = Uri.parse("file:///" + Environment.getExternalStorageDirectory().getPath() + "/small.jpg");
        localIntent.putExtra("output", this.uritempFile);
        localIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(localIntent, 6);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4 && resultCode == getActivity().RESULT_OK) {
            cropPhotoIcon(data.getData());// 裁剪图片
        }

        if (requestCode == 5 && resultCode == getActivity().RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri contentUri = FileProvider.getUriForFile(getContext(), "zhongchufu", tempFile);
                cropPhotoIcon(contentUri);
            } else {
                cropPhotoIcon(Uri.fromFile(tempFile));
            }
        }

        if (requestCode == 6) {
            if (data != null) {
                try {
                    head = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(uritempFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
//                    Bundle extras = data.getExtras();
//                    head = extras.getParcelable("data");
                if (head != null) {
                    /**
                     * 上传服务器代码
                     */

                    String a = setPicToView(head, "head.jpg");// 保存在SD卡中
                    //icon.setImageBitmap(head);// 用ImageView显示出来
                    File file = new File(a);
                    Log.i("filesss", file + "");
                    OkGoUtil.upiconloadFile(getContext(), "http://cll.shiduweb.com/index/pics.html", "file0", file, stringCallback);
                }
            }
        }
    }


    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, view);
        String str = Util.load(getContext());
        Log.i("sadsad", paramViewGroup + "  %%%%%%%%%");
        strs = str.split(",");
        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onResume() {
        super.onResume();
        postByOkGoIcon(ChangWeb.MYHUIYUANICON);
    }

    @OnClick({R.id.xiugaimima, R.id.myline, R.id.dingdan, R.id.jifen, R.id.gengduo, R.id.addcar, R.id.yaoqing, R.id.lianxikefu, R.id.editss, R.id.myicon})
    public void onViewClicked(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.xiugaimima:
                startActivity(new Intent(getContext(), MyXiuGaiMiMaActivity.class));
                break;
            case R.id.myline:
                startActivity(new Intent(getContext(), MyHuiYuanActivity.class).putExtra("integrass", this.integra).putExtra("grade", this.grade));
                return;
            case R.id.myicon:

                new ActionSheetDialog(getContext()).builder().setCancelable(false).setCanceledOnTouchOutside(false).addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    public void onClick(int paramAnonymousInt) {

                    }
                }).addSheetItem("从相册中选取", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    public void onClick(int paramAnonymousInt) {
                        Intent localIntent = new Intent("android.intent.action.PICK", null);
                        localIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        MyFragment.this.startActivityForResult(localIntent, 1);
                    }
                }).show();
                return;

            case R.id.dingdan:
                startActivity(new Intent(getContext(), MyDingDanActivity.class));
                return;
            case R.id.jifen:
                startActivity(new Intent(getContext(), MyJiFenActivity.class).putExtra("integra", this.integra));
                return;
            case R.id.gengduo:
                startActivity(new Intent(getContext(), MyGengDuoActivity.class));
                return;
            case R.id.addcar:
                startActivity(new Intent(getContext(), MyLoveCarActivity.class));
                return;
            case R.id.yaoqing:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = View.inflate(getActivity(), R.layout.fenxaingalder, null);
                builder.setView((View) view);
                builder.setCancelable(true);
                RelativeLayout wei = view.findViewById(R.id.weichat);
                RelativeLayout peng = view.findViewById(R.id.pengyouquan);
                final AlertDialog dialog = builder.create();
                wei.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramAnonymousView) {
                        weixinfir();
                        dialog.dismiss();
                    }
                });
                peng.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramAnonymousView) {
                        weixinpengyoquan();
                        dialog.dismiss();
                    }
                });
                builder.show();
                return;
            case R.id.lianxikefu:
                this.myQueDingDialog = new MyQueDingDialog(getContext());
                this.myQueDingDialog.setTitle("400-816-5355");
                this.myQueDingDialog.setYesOnclickListener("确定", new MyQueDingDialog.onYesOnclickListener() {
                    public void onYesClick() {
                        Util.call(MyFragment.this.getContext(), "400-816-5355");
                        MyFragment.this.myQueDingDialog.dismiss();
                    }
                });
                this.myQueDingDialog.setNoOnclickListener("取消", new MyQueDingDialog.onNoOnclickListener() {
                    public void onNoClick() {
                        MyFragment.this.myQueDingDialog.dismiss();
                    }
                });
                this.myQueDingDialog.show();
                return;
            case R.id.editss:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                Object localObject2 = View.inflate(getActivity(), R.layout.exidalert, null);
                builder1.setView((View) localObject2);
                builder1.setCancelable(true);
                final Object localObject1 = (Button) ((View) localObject2).findViewById(R.id.yes);
                localObject2 = (Button) ((View) localObject2).findViewById(R.id.no);
                ((Button) localObject1).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramAnonymousView) {
                        Util.save("", MyFragment.this.getContext());
                        MyFragment.this.startActivity(new Intent(MyFragment.this.getContext(), PhoneNumActivity.class));
                        MyFragment.this.dialog1.dismiss();
                        MyFragment.this.getActivity().finish();
                    }
                });
                ((Button) localObject2).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramAnonymousView) {
                        MyFragment.this.dialog1.dismiss();
                    }
                });
                dialog1 = builder1.create();
                dialog1.show();
                break;
        }

    }

    public void weixinfir() {

        Log.i("fenxiangedsa", "個人");
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://cll.shiduweb.com/news/fenxiangs.html";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "【车零零】汽车平台邀请您加入";
        msg.description = "极速救援30分钟抵达现场，意外保险价格优惠";
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_13);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
            msg.setThumbImage(thumbBmp);
            bmp.recycle();
        } catch (Exception e) {

        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = 0 == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        MyApp.mWxApi.sendReq(req);
    }

    public void weixinpengyoquan() {

        WXWebpageObject webpage1 = new WXWebpageObject();
        webpage1.webpageUrl = "http://cll.shiduweb.com/news/fenxiangs.html";
        WXMediaMessage msg1 = new WXMediaMessage(webpage1);
        msg1.title = "【车零零】汽车平台邀请您加入";
        msg1.description = "极速救援30分钟抵达现场，意外保险价格优惠";
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_13);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
            msg1.setThumbImage(thumbBmp);
            bmp.recycle();
        } catch (Exception e) {

        }
        SendMessageToWX.Req req1 = new SendMessageToWX.Req();
        req1.transaction = buildTransaction("webpage");
        req1.message = msg1;
        req1.scene = 1 == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        MyApp.mWxApi.sendReq(req1);
    }

    public class BlurTransformation implements Transformation {

        RenderScript rs;

        public BlurTransformation(Context context) {
            super();
            rs = RenderScript.create(context);
        }

        @SuppressLint("NewApi")
        @Override
        public Bitmap transform(Bitmap bitmap) {
            // 创建一个Bitmap作为最后处理的效果Bitmap
            Bitmap blurredBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            // 分配内存
            Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
            Allocation output = Allocation.createTyped(rs, input.getType());

            // 根据我们想使用的配置加载一个实例
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setInput(input);

            // 设置模糊半径
            script.setRadius(10);

            //开始操作
            script.forEach(output);

            // 将结果copy到blurredBitmap中
            output.copyTo(blurredBitmap);

            //释放资源
            bitmap.recycle();

            return blurredBitmap;
        }

        @Override
        public String key() {
            return "blur";
        }
    }


}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\fragment\MyFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */