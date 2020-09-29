// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PopularCourseAdapter$ViewHolder_ViewBinding implements Unbinder {
  private PopularCourseAdapter.ViewHolder target;

  @UiThread
  public PopularCourseAdapter$ViewHolder_ViewBinding(PopularCourseAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.itemTitle = Utils.findRequiredViewAsType(source, R.id.item_title, "field 'itemTitle'", ZawgyiTextView.class);
    target.itemBranchName = Utils.findRequiredViewAsType(source, R.id.branch_name, "field 'itemBranchName'", ZawgyiTextView.class);
    target.itemDescription = Utils.findRequiredViewAsType(source, R.id.item_description, "field 'itemDescription'", ZawgyiTextView.class);
    target.itemDate = Utils.findRequiredViewAsType(source, R.id.item_date, "field 'itemDate'", ZawgyiTextView.class);
    target.txtPrice = Utils.findRequiredViewAsType(source, R.id.price, "field 'txtPrice'", ZawgyiTextView.class);
    target.imageView = Utils.findRequiredViewAsType(source, R.id.item_image, "field 'imageView'", ImageView.class);
    target.itemLayout = Utils.findRequiredViewAsType(source, R.id.item_layout, "field 'itemLayout'", LinearLayout.class);
    target.itemEnroll = Utils.findRequiredViewAsType(source, R.id.item_enroll, "field 'itemEnroll'", ZawgyiTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PopularCourseAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.itemTitle = null;
    target.itemBranchName = null;
    target.itemDescription = null;
    target.itemDate = null;
    target.txtPrice = null;
    target.imageView = null;
    target.itemLayout = null;
    target.itemEnroll = null;
  }
}
