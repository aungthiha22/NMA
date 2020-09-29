package com.rebook.nma.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by YDN on 7/24/2017.
 */
public class ZawgyiButton extends Button {
    public ZawgyiButton(Context context) {
        super(context);
        setStyle(context);
    }

    public ZawgyiButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStyle(context);
    }

    public ZawgyiButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setStyle(context);
    }
    public void setStyle(Context context){
        setTypeface(Typeface.createFromAsset(context.getAssets(), "zawgyi.ttf"));
    }
}
