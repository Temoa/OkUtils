package me.temoa.baseutils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by lai
 * on 2018/3/19.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean isEmpty(T[] objects) {
        return objects == null || objects.length <= 0;
    }
}
