package com.beijing.chelingling.unit;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class DigBoos {
    private DownloadManager downloadManager;
    private DownloadManager.Request requestApk;

    @SuppressLint("WrongConstant")
    public long downLoadApk(Context paramContext, String paramString) {
        this.downloadManager = ((DownloadManager) paramContext.getSystemService("download"));
        this.requestApk = new DownloadManager.Request(Uri.parse(paramString));
        this.requestApk.setNotificationVisibility(2);
        this.requestApk.setAllowedNetworkTypes(2);
        this.requestApk.setDestinationInExternalPublicDir(paramContext.getPackageName() + "/myDownLoad", "xiaoyuantong.mkv");
        this.requestApk.setVisibleInDownloadsUi(true);
        this.requestApk.allowScanningByMediaScanner();
        this.requestApk.setTitle("xxx更新下载");
        this.requestApk.setDescription("xxx更新下载");
        return this.downloadManager.enqueue(this.requestApk);
    }

    @SuppressLint("WrongConstant")
    public boolean isDowning(Context paramContext, String paramString) {
        boolean bool4 = false;
        boolean bool3 = false;
        boolean bool2 = bool4;
        boolean bool1 = true;
        try {
            DownloadManager.Query localQuery = new DownloadManager.Query();
            bool2 = bool4;
            localQuery.setFilterByStatus(2);
            bool2 = bool4;
            if (this.downloadManager == null) {
                bool2 = bool4;
                this.downloadManager = ((DownloadManager) paramContext.getSystemService("download"));
            }
            bool2 = bool4;
            Cursor cursor = this.downloadManager.query(localQuery);
            do {
                bool1 = bool3;
                bool2 = bool4;
                if (!cursor.moveToNext()) {
                    break;
                }
                bool2 = bool4;
            }
            while (!paramContext.getString(cursor.getColumnIndex("uri")).equalsIgnoreCase(paramString));

            if (paramContext != null) {
                bool2 = bool1;
                cursor.close();
            }
            return bool1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool2;
    }

    public int[] query(long paramLong) {
        Object localObject = new DownloadManager.Query();
        ((DownloadManager.Query) localObject).setFilterById(new long[]{paramLong});
        localObject = this.downloadManager.query((DownloadManager.Query) localObject);
        if ((localObject != null) && (((Cursor) localObject).moveToFirst())) {
            int i = ((Cursor) localObject).getColumnIndex("local_filename");
            int j = ((Cursor) localObject).getColumnIndex("uri");
            String str1 = ((Cursor) localObject).getString(i);
            String str2 = ((Cursor) localObject).getString(j);
            i = ((Cursor) localObject).getColumnIndex("total_size");
            j = ((Cursor) localObject).getColumnIndex("bytes_so_far");
            i = ((Cursor) localObject).getInt(i);
            j = ((Cursor) localObject).getInt(j);
            int k = ((Cursor) localObject).getInt(((Cursor) localObject).getColumnIndex("status"));
            Log.d(getClass().getName(), "from " + str2 + " 下载到本地 " + str1 + " 文件总大小:" + i + " 已经下载:" + j);
            ((Cursor) localObject).close();
            Log.i("fdfd", i + "   " + j);
            return new int[]{i, j, k};
        }
        return null;
    }
}

