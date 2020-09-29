// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsAdapter$ViewHolder_ViewBinding implements Unbinder {
  private NewsAdapter.ViewHolder target;

  @UiThread
  public NewsAdapter$ViewHolder_ViewBinding(NewsAdapter.ViewHolder target, View source) {
    this.target = target;

    target.textView = Utils.findRequiredViewAsType(source, R.id.item_title, "field 'textView'", ZawgyiTextView.class);
    target.ratingBar = Utils.findRequiredViewAsType(source, R.id.ratingBar, "field 'ratingBar'", RatingBar.class);
    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.layoutEnroll, "field 'linearLayout'", LinearLayout.class);
    target.viewHide = Utils.findRequiredView(source, R.id.viewHide, "field 'viewHide'");
    target.imageView = Utils.findRequiredViewAsType(source, R.id.item_image, "field 'imageView'", ImageView.class);
    target.itemLayout = Utils.findRequiredViewAsType(source, R.id.item_layout, "field 'itemLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textView = null;
    target.ratingBar = null;
    target.linearLayout = null;
    target.viewHide = null;
    target.imageView = null;
    target.itemLayout = null;
  }
}
