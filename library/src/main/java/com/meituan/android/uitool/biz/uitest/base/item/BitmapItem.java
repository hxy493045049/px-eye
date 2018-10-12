package com.meituan.android.uitool.biz.uitest.base.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meituan.android.uitool.library.R;

import static com.meituan.android.uitool.utils.FoodUEDimensionUtils.dip2px;


public class BitmapItem extends Item {

    private String name;
    private Bitmap bitmap;

    public BitmapItem(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public boolean isValid() {
        if (bitmap == null) {
            return false;
        }
        return true;
    }

    @Override
    public View getView(Context context) {
        if (itemView != null) {
            return itemView;
        }
        itemView = LayoutInflater.from(context).inflate(R.layout.uet_cell_bitmap_info, null);

        TextView vName = itemView.findViewById(R.id.name);
        ImageView vImage = itemView.findViewById(R.id.image);
        TextView vInfo = itemView.findViewById(R.id.info);

        vName.setText(getName());
        Bitmap bitmap = getBitmap();

        int height = Math.min(bitmap.getHeight(), dip2px(58));
        int width = (int) ((float) height / bitmap.getHeight() * bitmap.getWidth());

        ViewGroup.LayoutParams layoutParams = vImage.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        vImage.setImageBitmap(bitmap);
        vInfo.setText(bitmap.getWidth() + "px*" + bitmap.getHeight() + "px");
        return itemView;
    }
}
