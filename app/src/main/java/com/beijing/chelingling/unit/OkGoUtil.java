package com.beijing.chelingling.unit;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class OkGoUtil {
    public static void downLoad(Context paramContext, String paramString1, String paramString2, String paramString3, FileCallback paramFileCallback) {
        ((GetRequest) OkGo.get(paramString1).tag(paramContext)).execute(paramFileCallback);
    }

    public static void getBitmap(Context paramContext, String paramString, BitmapCallback paramBitmapCallback) {
        ((GetRequest) OkGo.get(paramString).tag(paramContext)).execute(paramBitmapCallback);
    }

    public static void saveBitmapFile(Bitmap paramBitmap) {
        Object localObject = new File("/mnt/sdcard/pic/touxiang.jpg");
        try {
            localObject = new BufferedOutputStream(new FileOutputStream((File) localObject));
            paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, (OutputStream) localObject);
            ((BufferedOutputStream) localObject).flush();
            ((BufferedOutputStream) localObject).close();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void upiconloadFile(Context paramContext, String paramString1, String paramString2, File paramFile, StringCallback paramStringCallback) {
        String localObject = Util.load(paramContext);

        String strs[] = localObject.split(",");
        ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) OkGo.post(paramString1).tag(paramContext)).params("appid", strs[0], new boolean[0])).params("appsecret", strs[1], new boolean[0])).params(paramString2, paramFile)).params("timestamp", Util.getTime(), new boolean[0])).params("sign", Util.computeSha1OfString("appid=" + strs[0] + "&appsecret=" + strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])).execute(paramStringCallback);
    }

    public static void upiconloadFileS(Context paramContext, String paramString1, String paramString2, String paramString3, File paramFile, StringCallback paramStringCallback) {
        String localObject = Util.load(paramContext);

        String strs[] = localObject.split(",");
        ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) OkGo.post(paramString1).tag(paramContext)).params("appid", strs[0], new boolean[0])).params("appsecret", strs[1], new boolean[0])).params("content", paramString2, new boolean[0])).params(paramString3, paramFile)).params("timestamp", Util.getTime(), new boolean[0])).params("sign", Util.computeSha1OfString("appid=" + strs[0] + "&appsecret=" + strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])).execute(paramStringCallback);
    }

    public static void uploadFiles(Context paramContext, String paramString1, String paramString2, String paramString3, List<File> paramList, StringCallback paramStringCallback) {
        String localObject = Util.load(paramContext);

        String strs[] = localObject.split(",");
        ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) ((PostRequest) OkGo.post(paramString2).tag(paramContext)).params("appid", strs[0], new boolean[0])).params("appsecret", strs[1], new boolean[0])).params("content", paramString1, new boolean[0])).addFileParams(paramString3, paramList)).params("timestamp", Util.getTime(), new boolean[0])).params("sign", Util.computeSha1OfString("appid=" + strs[0] + "&appsecret=" + strs[1] + "&timestamp=" + Util.getTime()), new boolean[0])).execute(paramStringCallback);
    }
}

