package com.meituan.android.uitool.biz.uitest;

import android.graphics.Bitmap;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meituan.android.uitool.biz.uitest.base.Element;
import com.meituan.android.uitool.biz.uitest.base.IAttrs;
import com.meituan.android.uitool.biz.uitest.base.item.AddMinusEditItem;
import com.meituan.android.uitool.biz.uitest.base.item.BitmapItem;
import com.meituan.android.uitool.biz.uitest.base.item.EditTextItem;
import com.meituan.android.uitool.biz.uitest.base.item.Item;
import com.meituan.android.uitool.biz.uitest.base.item.SwitchItem;
import com.meituan.android.uitool.biz.uitest.base.item.TextItem;
import com.meituan.android.uitool.biz.uitest.base.item.TitleItem;
import com.meituan.android.uitool.utils.FoodUEResourceUtils;
import com.meituan.android.uitool.utils.FoodUEViewUtils;

import java.util.ArrayList;
import java.util.List;

import static com.meituan.android.uitool.utils.FoodUEDimensionUtils.px2dip;
import static com.meituan.android.uitool.utils.FoodUEDimensionUtils.px2sp;


public class UETCore implements IAttrs {

    @Override
    public List<Item> getAttrs(Element element) {
        List<Item> items = new ArrayList<>();

        View view = element.getView();

        items.add(new SwitchItem("控件拖动", element, SwitchItem.Type.TYPE_MOVE));
        IAttrs iAttrs = AttrsManager.createAttrs(view);
        if (iAttrs != null) {
            items.addAll(iAttrs.getAttrs(element));
        }

        items.add(new TitleItem("其他属性"));
        items.add(new TextItem("Class", view.getClass().getName()));
        items.add(new TextItem("Id", FoodUEResourceUtils.getResId(view)));
        items.add(new TextItem("ResName", FoodUEResourceUtils.getResourceName(view.getId())));
        items.add(new TextItem("Clickable", Boolean.toString(view.isClickable()).toUpperCase()));
        items.add(new TextItem("Focused", Boolean.toString(view.isFocused()).toUpperCase()));
        items.add(new AddMinusEditItem("Width（dp）", element, EditTextItem.Type.TYPE_WIDTH, px2dip(view.getWidth())));
        items.add(new AddMinusEditItem("Height（dp）", element, EditTextItem.Type.TYPE_HEIGHT, px2dip(view.getHeight())));
        items.add(new TextItem("Alpha", String.valueOf(view.getAlpha())));
        Object background = FoodUEViewUtils.getBackground(view);
        if (background instanceof String) {
            items.add(new TextItem("Background", (String) background));
        } else if (background instanceof Bitmap) {
            items.add(new BitmapItem("Background", (Bitmap) background));
        }
        items.add(new AddMinusEditItem("PaddingLeft（dp）", element, EditTextItem.Type.TYPE_PADDING_LEFT, px2dip(view.getPaddingLeft())));
        items.add(new AddMinusEditItem("PaddingRight（dp）", element, EditTextItem.Type.TYPE_PADDING_RIGHT, px2dip(view.getPaddingRight())));
        items.add(new AddMinusEditItem("PaddingTop（dp）", element, EditTextItem.Type.TYPE_PADDING_TOP, px2dip(view.getPaddingTop())));
        items.add(new AddMinusEditItem("PaddingBottom（dp）", element, EditTextItem.Type.TYPE_PADDING_BOTTOM, px2dip(view.getPaddingBottom())));

        return items;
    }

    static class AttrsManager {

        public static IAttrs createAttrs(View view) {
            if (view instanceof TextView) {
                return new UETTextView();
            } else if (view instanceof ImageView) {
                return new UETImageView();
            }
            return null;
        }
    }

    static class UETTextView implements IAttrs {

        @Override
        public List<Item> getAttrs(Element element) {
            List<Item> items = new ArrayList<>();
            TextView textView = ((TextView) element.getView());
            items.add(new TitleItem("TextView"));
            items.add(new EditTextItem("Text", element, EditTextItem.Type.TYPE_TEXT, textView.getText().toString()));
            items.add(new AddMinusEditItem("TextSize（sp）", element, EditTextItem.Type.TYPE_TEXT_SIZE, px2sp(textView.getTextSize())));
            items.add(new EditTextItem("TextColor", element, EditTextItem.Type.TYPE_TEXT_COLOR, FoodUEResourceUtils.intToHexColor(textView.getCurrentTextColor())));
            List<Pair<String, Bitmap>> pairs = FoodUEViewUtils.getTextViewBitmap(textView);
            for (Pair<String, Bitmap> pair : pairs) {
                items.add(new BitmapItem(pair.first, pair.second));
            }
            items.add(new SwitchItem("IsBold", element, SwitchItem.Type.TYPE_IS_BOLD, textView.getTypeface() != null ? textView.getTypeface().isBold() : false));
            return items;
        }
    }

    static class UETImageView implements IAttrs {

        @Override
        public List<Item> getAttrs(Element element) {
            List<Item> items = new ArrayList<>();
            ImageView imageView = ((ImageView) element.getView());
            items.add(new TitleItem("ImageView"));
            items.add(new BitmapItem("Bitmap", FoodUEViewUtils.getImageViewBitmap(imageView)));
            items.add(new TextItem("ScaleType", FoodUEViewUtils.getImageViewScaleType(imageView)));
            return items;
        }
    }
}
