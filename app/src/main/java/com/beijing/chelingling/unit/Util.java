package com.beijing.chelingling.unit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class Util {
    private static final String HEX_DIGITS = "0123456789abcdef";
    private static ProgressDialog progressDialog;

    public static String getDataString(String paramString, long paramLong) {
        return new SimpleDateFormat(paramString).format(Long.valueOf(paramLong));
    }

    private static Map<String, Activity> destoryMap = new HashMap();

    public static void addDestoryActivity(Activity paramActivity, String paramString) {
        destoryMap.put(paramString, paramActivity);
    }

    public static void destoryActivity(String paramString) {
        Iterator in = destoryMap.keySet().iterator();
        while (in.hasNext()) {
            String str = (String) in.next();
            ((Activity) destoryMap.get(str)).finish();
        }
    }

    public static void call(Context paramContext, String paramString) {
        paramContext.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + paramString)));
    }

    private static String computeSha1OfByteArray(byte[] paramArrayOfByte) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
            localMessageDigest.update(paramArrayOfByte);

            return toHexString(localMessageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String computeSha1OfString(String paramString) {
        try {
            paramString = computeSha1OfByteArray(paramString.getBytes("UTF-8"));
            return paramString;
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static long getTime() {
        return System.currentTimeMillis();
    }

    /* Error */
    public static String load(Context context) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            //设置将要打开的存储文件名称
            in = context.openFileInput("chelingling.txt");
            //FileInputStream -> InputStreamReader ->BufferedReader
            reader = new BufferedReader(new InputStreamReader(in));
            String line = new String();
            //读取每一行数据，并追加到StringBuilder对象中，直到结束
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    /* Error */
    public static void save(String content, Context context) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            //设置文件名称，以及存储方式
            out = context.openFileOutput("chelingling.txt", Context.MODE_PRIVATE);
            //创建一个OutputStreamWriter对象，传入BufferedWriter的构造器中
            writer = new BufferedWriter(new OutputStreamWriter(out));
            //向文件中写入数据
            writer.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String toHexString(byte[] paramArrayOfByte) {
        StringBuilder localStringBuilder = new StringBuilder(paramArrayOfByte.length * 2);
        int i = 0;
        while (i < paramArrayOfByte.length) {
            int j = paramArrayOfByte[i] & 0xFF;
            localStringBuilder.append("0123456789abcdef".charAt(j >>> 4)).append("0123456789abcdef".charAt(j & 0xF));
            i += 1;
        }
        return localStringBuilder.toString();
    }

    public static boolean isContainAll(String paramString) {
        Log.i("adfsdg",paramString);
        if (paramString != null) {
            return Pattern.compile(".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]").matcher(paramString).matches();
        }
        return false;
    }
}

