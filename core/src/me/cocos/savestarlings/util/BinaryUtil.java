package me.cocos.savestarlings.util;

public class BinaryUtil {

    public static double bytesToGigabytes(long bytes) {
        double gigabytes = bytes / (1024.0 * 1024.0 * 1024.0);
        return Math.round(gigabytes * 100.0) / 100.0;
    }
}
