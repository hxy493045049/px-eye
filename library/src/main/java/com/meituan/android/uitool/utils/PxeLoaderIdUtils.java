package com.meituan.android.uitool.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/12 on 下午5:39
 */
public class PxeLoaderIdUtils {
    private static AtomicInteger integer = new AtomicInteger(0);

    private PxeLoaderIdUtils() {
    }

    /**
     * loaderId默认从0开始，每次自增1
     *
     * @return loaderId
     */
    public static int generateLoaderId() {
        return integer.getAndIncrement();
    }

    public static final class IdList {
        //UI检查上传图片
        public static final int PXE_UPLOAD_IMAGE = generateLoaderId();
        //加载mock数据信息
        public static final int PXE_MOCK_INFO = generateLoaderId();
    }
}
