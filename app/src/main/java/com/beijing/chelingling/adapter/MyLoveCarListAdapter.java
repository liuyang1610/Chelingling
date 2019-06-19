package com.beijing.chelingling.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beijing.chelingling.MyPdfShow;
import com.beijing.chelingling.MyWebActivity;
import com.beijing.chelingling.MyXiuGaiCatInfoActivity;
import com.beijing.chelingling.R;
import com.beijing.chelingling.info.MyLoveCarLInfo;
import com.beijing.chelingling.unit.ChangWeb;
import com.beijing.chelingling.unit.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Response;

public class MyLoveCarListAdapter
        extends RecyclerView.Adapter<MyLoveCarListAdapter.ViewHolder> {
    private Context context;
    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message paramAnonymousMessage) {
            MyLoveCarListAdapter.this.la.remove(paramAnonymousMessage.arg1);
            MyLoveCarListAdapter.this.notifyItemRemoved(paramAnonymousMessage.arg1);
            MyLoveCarListAdapter.this.notifyItemRangeChanged(0, MyLoveCarListAdapter.this.la.size());
            return false;
        }
    });
    private ArrayList<MyLoveCarLInfo> la;
    private String[] strs;

    public MyLoveCarListAdapter(Context paramContext, ArrayList<MyLoveCarLInfo> paramLinkedList, String[] paramArrayOfString) {
        this.context = paramContext;
        this.la = paramLinkedList;
        this.strs = paramArrayOfString;
    }

    private void postByOkGo(String paramString1, String paramString2, final int paramInt) {
        Log.i("sadsad", paramString2 + "");
        OkGo.post(paramString1).tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("appid", this.strs[0], new boolean[0])
                .params("appsecret", this.strs[1], new boolean[0])
                .params("car_id", paramString2, new boolean[0])
                .params("timestamp", Util.getTime(), new boolean[0])
                .params("sign", Util.computeSha1OfString("appid=" + this.strs[0] + "&appsecret=" + this.strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])
                .execute(new StringCallback() {
                    public void onError(Call paramAnonymousCall, Response paramAnonymousResponse, Exception paramAnonymousException) {
                        super.onError(paramAnonymousCall, paramAnonymousResponse, paramAnonymousException);
                        Log.i("wandanle", "错误");
                    }

                    public void onSuccess(String paramAnonymousString, Call paramAnonymousCall, Response paramAnonymousResponse) {
                        Log.i("wandanle", paramAnonymousString);
                        try {
                            if (new JSONObject(paramAnonymousString).getString("code").equals("000")) {
                                Message message = new Message();
                                message.arg1 = paramInt;
                                MyLoveCarListAdapter.this.handler.sendMessage(message);
                            }
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public int getItemCount() {
        return this.la.size();
    }

    public void onBindViewHolder(ViewHolder paramViewHolder, final int paramInt) {
        Picasso.get().load(ChangWeb.YUMING + ((MyLoveCarLInfo) this.la.get(paramInt)).getPic()).into(paramViewHolder.carlogo);
        paramViewHolder.cainame.setText(((MyLoveCarLInfo) this.la.get(paramInt)).getBrand());
        paramViewHolder.catnum.setText(((MyLoveCarLInfo) this.la.get(paramInt)).getPlate_number());

        if (la.get(paramInt).getDocument().equals("0")) {
            paramViewHolder.chakan.setVisibility(View.GONE);
        } else {
            paramViewHolder.chakan.setVisibility(View.VISIBLE);
        }

        paramViewHolder.shanchu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                MyLoveCarListAdapter.this.postByOkGo(ChangWeb.MYDELELSCAR, (la.get(paramInt)).getId(), paramInt);
            }
        });
        paramViewHolder.bianji.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                MyLoveCarListAdapter.this.context.startActivity(new Intent(MyLoveCarListAdapter.this.context, MyXiuGaiCatInfoActivity.class).putExtra("data", (Serializable) MyLoveCarListAdapter.this.la.get(paramInt)));
            }
        });

        paramViewHolder.chakan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {

                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(ChangWeb.YUMING+la.get(paramInt).getDocument());
                intent.setData(content_url);
                context.startActivity(intent);

                // MyLoveCarListAdapter.this.context.startActivity(new Intent(MyLoveCarListAdapter.this.context, MyWebActivity.class).putExtra("url", ChangWeb.YUMING + la.get(paramInt).getDocument()));

                //downLoadApk(context, ChangWeb.YUMING + la.get(paramInt).getDocument());
            }
        });
    }

    public void downLoadApk(Context context, String url,int i) {
        /**
         * 在这里返回的 reference 变量是系统为当前的下载请求分配的一个唯一的ID，
         * 我们可以通过这个ID重新获得这个下载任务，进行一些自己想要进行的操作
         * 或者查询下载的状态以及取消下载等等
         */
        Uri uri = Uri.parse(url);        //下载连接
        DownloadManager manager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);  //得到系统的下载管理
        DownloadManager.Request requestApk = new DownloadManager.Request(uri);  //得到连接请求对象
        requestApk.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);   //指定在什么网络下进行下载，这里我指定了WIFI网络
        requestApk.setDestinationInExternalPublicDir(context.getPackageName() + "/myDownLoad", la.get(i).getDocument());  //制定下载文件的保存路径，我这里保存到根目录
        requestApk.setVisibleInDownloadsUi(true);  //设置显示下载界面
        requestApk.allowScanningByMediaScanner();  //表示允许MediaScanner扫描到这个文件，默认不允许。
        requestApk.setTitle("车零零文件下载");      //设置下载中通知栏的提示消息
        requestApk.setDescription("您的电子表单正在下载……");//设置设置下载中通知栏提示的介绍
        long downLoadId = manager.enqueue(requestApk);               //启动下载,该方法返回系统为当前下载请求分配的一个唯一的ID
    }


    public ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_lovecarlist, null));
    }

    public class ViewHolder
            extends RecyclerView.ViewHolder {
        private TextView bianji;
        private TextView cainame;
        private ImageView carlogo;
        private TextView catnum;
        private TextView shanchu;
        private TextView chakan;

        public ViewHolder(View paramView) {
            super(paramView);
            this.carlogo = ((ImageView) paramView.findViewById(R.id.carlogo));
            this.cainame = ((TextView) paramView.findViewById(R.id.cainame));
            this.shanchu = ((TextView) paramView.findViewById(R.id.shanchu));
            this.bianji = ((TextView) paramView.findViewById(R.id.bianji));
            this.catnum = ((TextView) paramView.findViewById(R.id.catnum));
            this.chakan = ((TextView) paramView.findViewById(R.id.chakan));
        }
    }
}


/* Location:              G:\chelingling\dex2jar-2.0\classes-dex2jar.jar!\com\beijing\chelingling\adapter\MyLoveCarListAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */