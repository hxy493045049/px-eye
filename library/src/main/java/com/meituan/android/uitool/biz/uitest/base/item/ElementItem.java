package com.meituan.android.uitool.biz.uitest.base.item;


import com.meituan.android.uitool.biz.uitest.base.Element;

public class ElementItem extends TitleItem {

    private Element element;

    public ElementItem(String name, Element element) {
        super(name);
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
