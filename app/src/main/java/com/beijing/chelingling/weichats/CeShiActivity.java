package com.beijing.chelingling.weichats;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.beijing.chelingling.unit.MyApp;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CeShiActivity {

    private String jiaqians;
    private PayReq req = new PayReq();
    private Map<String, String> resultunifiedorder;
    private StringBuffer sb = new StringBuffer();

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        Log.e("Simon", "----" + appSign);
        return appSign;
    }


    private String genOutTradNo() {
        Random random = new Random();
//      return "dasgfsdg1234"; //订单号写死的话只能支付一次，第二次不能生成订单
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }


    private void genPayReq() {

        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        if (resultunifiedorder != null) {
            req.prepayId = resultunifiedorder.get("prepay_id");
            req.packageValue = "prepay_id=" + resultunifiedorder.get("prepay_id");
        } else {
            //Toast.makeText(MainActivity.this, "prepayid为空", Toast.LENGTH_SHORT).show();
        }
        req.nonceStr = getNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");

        Log.e("Simon", "----" + signParams.toString());

    }


    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000L;
    }

    private String getNonceStr() {
        return MD5.getMessageDigest(String.valueOf(new Random().nextInt(10000)).getBytes());
    }

    /**
     * 生成签名
     */

    private String getPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("Simon", ">>>>" + packageSign);
        return packageSign;
    }


    private String getProductArgs() {
        // TODO Auto-generated method stub
        Log.i("Asfdsaf",genOutTradNo());
        StringBuffer xml = new StringBuffer();
        try {
            String nonceStr = getNonceStr();
            xml.append("<xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
            packageParams.add(new BasicNameValuePair("body", "chelingling"));
            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", "https://www.baidu.com"));//写你们的回调地址
            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
            packageParams.add(new BasicNameValuePair("total_fee", jiaqians));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign = getPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlString = toXml(packageParams);
            return xmlString;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }


    private void sendPayReq() {
        MyApp.mWxApi.registerApp("wx74e46dd77f0ac3d2");
        MyApp.mWxApi.sendReq(this.req);
        Log.i(">>>>>", this.req.partnerId);
    }

    /*
  * 转换成xml
  */
    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("Simon", ">>>>" + sb.toString());
        return sb.toString();
    }


    public void StartWeiChat(String paramString1) {

        Log.i("Asfdsaf", paramString1);
        jiaqians = paramString1;
        new PrePayIdAsyncTask().execute(new String[]{"https://api.mch.weixin.qq.com/pay/unifiedorder", paramString1});

    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("Simon", "----" + e.toString());
        }
        return null;

    }


    private class PrePayIdAsyncTask extends AsyncTask<String, Void, Map<String, String>> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //dialog = ProgressDialog.show(this, "提示", "正在提交订单");

        }

        @Override
        protected Map<String, String> doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url = String.format(params[0]);
            String entity = getProductArgs();
            Log.e("Simon", ">>>>" + entity);
            byte[] buf = Util.httpPost(url, entity);
            String content = new String(buf);
            Log.e("orion", "----" + content);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            resultunifiedorder = result;
            genPayReq();
            sendPayReq();
        }
    }

}