// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AboutUsAdapter$ViewHolder_ViewBinding implements Unbinder {
  private AboutUsAdapter.ViewHolder target;

  @UiThread
  public AboutUsAdapter$ViewHolder_ViewBinding(AboutUsAdapter.ViewHolder target, View source) {
    this.target = target;

    target.itemName = Utils.findRequiredViewAsType(source, R.id.about_us_itemName, "field 'itemName'", ZawgyiTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AboutUsAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.itemName = null;
  }
}
