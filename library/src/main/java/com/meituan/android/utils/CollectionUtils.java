package com.meituan.android.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/21 on 下午6:00
 */
public class CollectionUtils {
    public static <T> List<T> intersect(List<T> list1, List<T> list2) {
        ArrayList list = new ArrayList(Arrays.asList(new Object[list1.size()]));
        Collections.copy(list, list1);
        list.retainAll(list2);
        return list;
    }

    public static <T> List<T> asList(T... arr) {
        return arr == null ? null : new ArrayList(Arrays.asList(arr));
    }

    public static <T> List<T> union(List<T> list1, List<T> list2) {
        ArrayList list = new ArrayList(Arrays.asList(new Object[list1.size()]));
        Collections.copy(list, list1);
        list.removeAll(list2);
        list.addAll(list2);
        return list;
    }

    public static <T> List<T> diff(List<T> list1, List<T> list2) {
        List unionList = union(list1, list2);
        List intersectList = intersect(list1, list2);
        unionList.removeAll(intersectList);
        return unionList;
    }

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean inArray(T t, List<T> list) {
        return t != null && !isEmpty(list) ? list.contains(t) : false;
    }

    public static <T> void addAll(List<T> list, T... ts) {
        List newList = Arrays.asList(ts);
        list.addAll(newList);
    }

    public static <T> int size(List<T> list) {
        return list == null ? 0 : list.size();
    }

    public static <T> T getValueFromMap(Map maps, String key, T defaultValue) {
        if (maps == null || TextUtils.isEmpty(key) || !maps.containsKey(key)) {
            return defaultValue;
        }
        return (T) maps.get(key);
    }

    public static boolean listEqual(List<?> a, List<?> b) {
        if (a == b) {
            return true;
        }

        if (a != null && b != null) {
            if (a.size() != b.size()) {
                return false;
            } else {
                int size = a.size();
                for (int i = 0; i < size; ++i) {
                    Object aItem = a.get(i);
                    Object bItem = b.get(i);
                    if (aItem == bItem) {
                        continue;
                    } else if (aItem != null && bItem != null) {
                        if (aItem instanceof List && bItem instanceof List) {
                            if (!listEqual((List) aItem, (List) bItem)) {
                                return false;
                            }
                        } else {
                            if (aItem == bItem || (aItem != null && aItem.equals(bItem))) {
                                // do nothing
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }
}
