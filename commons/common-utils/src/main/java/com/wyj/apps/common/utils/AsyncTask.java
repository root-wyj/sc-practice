package com.wyj.apps.common.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/17
 */
public class AsyncTask {

    private static final ExecutorService pool = Executors.newFixedThreadPool(20);


    /**
     * 异步任务
     */
    public static Future<?> doAsyncTask(Callable<?> callback){

        return pool.submit((Callable<?>) callback);
    }


}
