package com.beijing.chelingling.lovecar;

import java.util.Comparator;

public class PinyinComparator
        implements Comparator<DataBean> {
    public int compare(DataBean paramDataBean1, DataBean paramDataBean2) {
        if ((paramDataBean1.getItem_en().equals("@")) || (paramDataBean2.getItem_en().equals("#"))) {
            return -1;
        }
        if ((paramDataBean1.getItem_en().equals("#")) || (paramDataBean2.getItem_en().equals("@"))) {
            return 1;
        }
        return paramDataBean1.getItem_en().compareTo(paramDataBean2.getItem_en());
    }
}

