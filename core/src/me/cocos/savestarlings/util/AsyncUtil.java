package me.cocos.savestarlings.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncUtil {

    private static final ExecutorService ASYNC_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    public static void runAsync(Runnable runnable) {
        ASYNC_EXECUTOR.execute(runnable);
    }
}
