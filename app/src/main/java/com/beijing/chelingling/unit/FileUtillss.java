package com.beijing.chelingling.unit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class FileUtillss {
    public static String SDPATH = Environment.getExternalStorageDirectory() + "/tupian/";

    public static File createSDDir(String paramString)
            throws IOException {
        File file = new File(SDPATH + paramString);
        if (Environment.getExternalStorageState().equals("mounted")) {
            System.out.println("createSDDir:" + file.getAbsolutePath());
            System.out.println("createSDDir:" + file.mkdir());
        }
        return file;
    }

    public static void delFile(String paramString) {
        File file = new File(SDPATH + paramString);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    public static void deleteDir() {
//        File localFile1 = new File(SDPATH);
//        if ((localFile1 == null) || (!localFile1.exists()) || (!localFile1.isDirectory())) {
//            return;
//        }
//        File[] arrayOfFile = localFile1.listFiles();
//        int j = arrayOfFile.length;
//        int i = 0;
//        if (i < j) {
//            File localFile2 = arrayOfFile[i];
//            if (localFile2.isFile()) {
//                localFile2.delete();
//            }
//            for (; ; ) {
//                i += 1;
//                break;
//                if (localFile2.isDirectory()) {
//                    deleteDir();
//                }
//            }
//        }
//        localFile1.delete();
    }

    public static boolean fileIsExists(String paramString) {
        try {
            boolean bool = new File(paramString).exists();
            if (!bool) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static Bitmap getBitmap(String paramString)
            throws Exception {
//        URLConnection uri = new URL(paramString).openConnection();
//        uri.setConnectTimeout(5000);
//        uri.setRequestMethod("GET");
//        if (uri.getResponseCode() == 200) {
//            return BitmapFactory.decodeStream(uri.getInputStream());
//        }
        return null;
    }

    public static boolean isFileExist(String paramString) {
        File file = new File(SDPATH + paramString);
        file.isFile();
        return file.exists();
    }

    public static void saveBitmap(Bitmap paramBitmap, String paramString) {
        Log.e("", "保存图片");
        try {
            if (!isFileExist("")) {
                createSDDir("");
            }
            File file = new File(SDPATH, paramString + ".jpg");
            Log.i("pathssss", file.getPath());
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(paramString);
            paramBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Log.e("", "已经保存");
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


