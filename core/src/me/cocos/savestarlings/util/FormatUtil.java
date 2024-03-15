package me.cocos.savestarlings.util;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtil {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.GERMANY);

    private FormatUtil() {}

    public static String formatNumber(float number) {
        return NUMBER_FORMAT.format(number);
    }
}
