// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CustomAdapter$ViewHolder_ViewBinding implements Unbinder {
  private CustomAdapter.ViewHolder target;

  @UiThread
  public CustomAdapter$ViewHolder_ViewBinding(CustomAdapter.ViewHolder target, View source) {
    this.target = target;

    target.courseTitle = Utils.findRequiredViewAsType(source, R.id.course_title, "field 'courseTitle'", ZawgyiTextView.class);
    target.generalImage = Utils.findRequiredViewAsType(source, R.id.general_image, "field 'generalImage'", ImageView.class);
    target.txtPrice = Utils.findRequiredViewAsType(source, R.id.discount_price, "field 'txtPrice'", ZawgyiTextView.class);
    target.txtDescription = Utils.findRequiredViewAsType(source, R.id.description, "field 'txtDescription'", ZawgyiTextView.class);
    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", CardView.class);
    target.txtViewDetail = Utils.findRequiredViewAsType(source, R.id.view_detail, "field 'txtViewDetail'", ZawgyiTextView.class);
    target.lyPrice = Utils.findRequiredViewAsType(source, R.id.price_layout, "field 'lyPrice'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CustomAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.courseTitle = null;
    target.generalImage = null;
    target.txtPrice = null;
    target.txtDescription = null;
    target.cardView = null;
    target.txtViewDetail = null;
    target.lyPrice = null;
  }
}
