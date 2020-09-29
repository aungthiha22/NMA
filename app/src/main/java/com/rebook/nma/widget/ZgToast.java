package com.rebook.nma.widget;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import com.rebook.nma.R;

/**
 * Created by YDN on 7/5/2017.
 */
public class ZgToast extends Toast {
  ZawgyiTextView zawgyiTextView;
    Context context;
    int px;
    public ZgToast(Context context) {
        super(context);
        this.context=context;
        zawgyiTextView=new ZawgyiTextView(context);
        zawgyiTextView.setGravity(Gravity.CENTER);
        setDuration(Toast.LENGTH_SHORT);
       // zawgyiTextView.setBackgroundColor(context.getResources().getColor(R.color.swipe_refresh_color3));
        zawgyiTextView.setTextColor(context.getResources().getColor(R.color.white));
        zawgyiTextView.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.toast_background
        ));
        px= 35;
        zawgyiTextView.setPadding(px,px,px,px);
        setView(zawgyiTextView);
    }
    public void setZgText(String zgText) {
        zawgyiTextView.setText(zgText);
    }

    public void setError(){
        zawgyiTextView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        zawgyiTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.error_toast_background));
        zawgyiTextView.setPadding(px,px,px,px);
    }
}
