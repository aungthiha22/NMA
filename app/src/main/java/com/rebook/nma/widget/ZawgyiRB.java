package com.rebook.nma.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by yemyatthu on 2/24/15.
 */
public class ZawgyiRB extends RadioButton {
  public ZawgyiRB(Context context) {
    super(context);
    init(context);
  }

  public ZawgyiRB(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public ZawgyiRB(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    setTypeface(Typeface.createFromAsset(context.getAssets(), "zawgyi.ttf"));
  }
}
