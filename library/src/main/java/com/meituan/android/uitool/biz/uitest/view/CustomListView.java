package com.meituan.android.uitool.biz.uitest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.meituan.android.uitool.biz.uitest.UETool;
import com.meituan.android.uitool.biz.uitest.base.UITestDialogCallback;
import com.meituan.android.uitool.biz.uitest.base.Element;
import com.meituan.android.uitool.biz.uitest.base.IAttrs;
import com.meituan.android.uitool.biz.uitest.base.ItemArrayList;
import com.meituan.android.uitool.biz.uitest.base.item.BriefDescItem;
import com.meituan.android.uitool.biz.uitest.base.item.Item;
import com.meituan.android.uitool.biz.uitest.base.item.SwitchItem;
import com.meituan.android.uitool.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenbin on 2018/10/9.
 */

public class CustomListView extends ScrollView {

    private LinearLayout listViewContainer;
    private List<Item> items = new ItemArrayList<>();
    private List<Item> validItems = new ArrayList<>();
    private List<Item> itemsCore = new ItemArrayList<>();
    private UITestDialogCallback callback;
    private Element currentElement;
    public CustomListView(Context context) {
        super(context);
        init();
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.uet_scroll_view_layout,this,true);
        listViewContainer = findViewById(R.id.listViewContainer);
    }


    public  Element getCurrentClickedItem() {
        return currentElement;
    }
    public void updateCurrentItem(Element element) {
        currentElement = element;
        for (Item item : validItems) {
            if (item instanceof BriefDescItem) {
                BriefDescItem briefDescItem = (BriefDescItem) item;
                if (element.equals(briefDescItem.getElement())) {
                    briefDescItem.setSelected(true);
                }else {
                    briefDescItem.setSelected(false);
                }
            }
        }
    }

    public void setAttrDialogCallback(UITestDialogCallback callback) {
        this.callback = callback;
    }
    /**
     * 其他附加功能的初始化
     * @param element 当前选中的element
     */
    public void notifyViewAttrChanged(Element element) {
        currentElement = element;
        items.clear();
        itemsCore.clear();
        for (String attrsProvider : UETool.getInstance().getAttrsProvider()) {
            try {
                IAttrs attrs = (IAttrs) Class.forName(attrsProvider).newInstance();
                items.addAll(attrs.getAttrs(element));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        itemsCore.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 显示View层级
     * @param validItems
     */
    public void notifyValidViewItemInserted(List<Item> validItems) {
        this.validItems.clear();
        this.validItems.addAll(validItems);
        items.clear();
        items.addAll(validItems);
        updateCurrentItem(currentElement);
        notifyDataSetChanged();
    }

    public void notifyValidViewItemRemoved() {
        notifyViewAttrChanged(currentElement);
    }
    private void notifyDataSetChanged() {
        listViewContainer.removeAllViews();
        for (Item item : items) {
            if (item instanceof SwitchItem && ((SwitchItem) item).getType() == SwitchItem.Type.TYPE_MOVE) {
                ((SwitchItem) item).setCallback(callback);
            }
            if (item instanceof BriefDescItem) {
                ((BriefDescItem) item).setCallback(callback);
            }

            listViewContainer.addView(item.getView(getContext()));
        }
    }
}
