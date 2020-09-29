package com.rebook.nma.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yemyatthu on 2/18/15.
 */
public class ZawgyiTextView extends TextView {
  public ZawgyiTextView(Context context) {
    super(context);
    setStyle(context);
  }

  public ZawgyiTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setStyle(context);
  }

  public ZawgyiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setStyle(context);
  }

  private void setStyle(Context context) {
    if(Build.BRAND.contains("")){
      setTypeface(Typeface.createFromAsset(context.getAssets(),"zawgyi_from_web.ttf"));
    }
    else {
      setTypeface(Typeface.createFromAsset(context.getAssets(), "zawgyi.ttf"));
    }
  }
}
