package com.emeraldingot.storagesystem.util;

import java.text.DecimalFormat;

public class NumberUtil {
    public static String formatNumber(long number) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(number);
    }

    public static String sOrNo(int value) {
        if (value == 0 || value > 1) {
            return "s";
        }
        else {
            return "";
        }
    }
}
