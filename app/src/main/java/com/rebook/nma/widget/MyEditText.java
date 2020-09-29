package com.rebook.nma.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by yemyatthu on 2/24/15.
 */
public class MyEditText extends EditText{


    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
      super(context, attrs, defStyle);
      init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
      super(context, attrs);
      init();
    }

    public MyEditText(Context context) {
      super(context);
      init();
    }

    private void init() {
      if (!isInEditMode()) {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "zawgyi.ttf");
        setTypeface(tf);
      }
    }
  }



