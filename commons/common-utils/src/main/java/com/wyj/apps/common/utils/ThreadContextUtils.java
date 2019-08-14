package com.wyj.apps.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程上下文环境，可以保存一些需要用到的 key value 等等。
 * <br>会在请求结束的时候，清空上下文数据。
 * <br>如果不是和请求一个线程的，请注意需要自己清除上下文数据。
 *
 * Created
 * Author: wyj
 * Date: 2019/5/28
 */
public class ThreadContextUtils {

    private ThreadContextUtils() {}

    private static ThreadLocal<Map<String, Object>> threadContext = ThreadLocal.withInitial(HashMap::new);

    public static <T> void put(String key, T value) {
        threadContext.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        Object valueObj = threadContext.get().get(key);
        if (valueObj != null) {
            return (T)valueObj;
        }

        return null;
    }

    public static void clean(String key) {
        threadContext.get().remove(key);
    }

    public static void clear() {
        threadContext.get().clear();
    }

}
