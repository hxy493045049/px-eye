package com.meituan.android.uitool.biz.mock.structure;

import java.util.LinkedList;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/8 on 下午5:18
 * 固定长度List
 * 如果List里面的元素个数大于了缓存最大容量，则删除链表的顶端元素
 */
public class PxeFixedLinkedList<T> extends LinkedList<T> {
    private static final long serialVersionUID = 3292612616231532364L;
    // 定义缓存的容量
    private int capacity;

    public PxeFixedLinkedList(int capacity) {
        super();
        this.capacity = capacity;
    }

    @Override
    public boolean add(T e) {
        // 超过长度，移除最后一个
        if (size() + 1 > capacity) {
            super.removeFirst();
        }
        return super.add(e);
    }
}
