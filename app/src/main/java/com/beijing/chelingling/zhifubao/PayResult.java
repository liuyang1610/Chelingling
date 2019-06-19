package com.beijing.chelingling.zhifubao;

import android.text.TextUtils;

import java.util.Map;

public class PayResult {
    private String resultStatus;
    private String result;
    private String memo;

    public PayResult(Map<String, String> rawResult) {
        if (rawResult == null) {
            return;
        }

        for (String key : rawResult.keySet()) {
            if (TextUtils.equals(key, "resultStatus")) {
                resultStatus = rawResult.get(key);
            } else if (TextUtils.equals(key, "result")) {
                result = rawResult.get(key);
            } else if (TextUtils.equals(key, "memo")) {
                memo = rawResult.get(key);
            }
        }
    }

    @Override
    public String toString() {
        return "resultStatus={" + resultStatus + "};memo={" + memo
                + "};result={" + result + "}";
    }

    /**
     * @return the resultStatus
     */
    public String getResultStatus() {
        return resultStatus;
    }

    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }
}


/* Location:              G:\apk\dex2jar-2.0\classes-dex2jar.jar!\com\daigou\zhongchufu\zhongchufu\zhifubao\PayResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */