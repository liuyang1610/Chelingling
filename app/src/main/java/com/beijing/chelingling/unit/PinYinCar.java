package com.beijing.chelingling.unit;

import com.beijing.chelingling.info.MyLoveCarInfo;

import java.util.Comparator;

public class PinYinCar
        implements Comparator<MyLoveCarInfo> {
    public int compare(MyLoveCarInfo o1, MyLoveCarInfo o2) {
        if (o1.getLetters().equals("@")
                || o2.getLetters().equals("#")) {
            return -1;
        } else if (o1.getLetters().equals("#")
                || o2.getLetters().equals("@")) {
            return 1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }
}


