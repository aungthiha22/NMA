package com.rebook.nma.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by YDN on 12/8/2015.
 */
public class ZawgyiCheckBox extends CheckBox {
    public ZawgyiCheckBox(Context context) {
        super(context);
        init(context);
    }

    public ZawgyiCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZawgyiCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), "zawgyi.ttf"));
    }
}
