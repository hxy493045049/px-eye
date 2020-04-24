package com.meituan.android.uitool.biz.attr.dialog.provider.impl;

import android.widget.TextView;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseItem;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeTextItem;
import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeItemsProvider;
import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeBaseViewHolder;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;
import com.meituan.android.uitool.utils.PxeResourceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:59
 */
public class PxeTextViewProvider implements IPxeItemsProvider {
    @Override
    public List<PxeBaseItem> getItems(PxeViewInfo viewInfo) {
        List<PxeBaseItem> items = new ArrayList<>();
        if (!(viewInfo.getView() instanceof TextView)) {
            return items;
        }
        TextView textView = (TextView) viewInfo.getView();
        PxeTextItem title = new PxeTextItem(textView.getClass().getSimpleName(), "");
        title.setHolderType(PxeBaseViewHolder.PxeAttrHolderType.TITLE);

        items.add(title);
        items.add(new PxeTextItem("字体大小(px)", textView.getTextSize() + ""));
        items.add(new PxeTextItem("字体颜色", PxeResourceUtils.intToHexColor(textView.getCurrentTextColor())));

//            List<Pair<String, Bitmap>> pairs = PxeViewUtils.getTextViewBitmap(textView);
//            for (Pair<String, Bitmap> pair : pairs) {
//                items.add(new PxeBitmapAttr(pair, viewInfo));
//            }
        return items;
    }
}
