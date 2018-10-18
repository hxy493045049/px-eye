package com.meituan.android.uitool.biz.attr.dialog.provider.impl;

import android.view.View;
import android.widget.TextView;

import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeAttrProvider;
import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeHolderType;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseAttr;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeNormalAttr;
import com.meituan.android.uitool.utils.PxeResourceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:59
 */
public class PxeTextViewProvider implements IPxeAttrProvider {
    @Override
    public List<PxeBaseAttr> getAttrs(PxeViewInfo viewInfo) {
        List<PxeBaseAttr> items = new ArrayList<>();
        View view = viewInfo.getView();
        TextView textView;
        if (view instanceof TextView) {
            textView = (TextView) view;
            PxeNormalAttr title = new PxeNormalAttr(textView.getClass().getSimpleName(), viewInfo);
            title.setHolderType(PxeHolderType.AttrDialogHolder.TITLE);
            items.add(title);
            items.add(new PxeNormalAttr("字体大小(px)", textView.getTextSize() + "", viewInfo));
            items.add(new PxeNormalAttr("字体颜色", PxeResourceUtils.intToHexColor(textView.getCurrentTextColor()), viewInfo));

//            List<Pair<String, Bitmap>> pairs = PxeViewUtils.getTextViewBitmap(textView);
//            for (Pair<String, Bitmap> pair : pairs) {
//                items.add(new PxeBitmapAttr(pair, viewInfo));
//            }
        }
        return items;
    }
}
