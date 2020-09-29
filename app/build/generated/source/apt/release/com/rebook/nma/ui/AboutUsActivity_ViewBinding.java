// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AboutUsActivity_ViewBinding implements Unbinder {
  private AboutUsActivity target;

  @UiThread
  public AboutUsActivity_ViewBinding(AboutUsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AboutUsActivity_ViewBinding(AboutUsActivity target, View source) {
    this.target = target;

    target.expandableListView = Utils.findRequiredViewAsType(source, R.id.list_expandable, "field 'expandableListView'", ExpandableListView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AboutUsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.expandableListView = null;
    target.toolbar = null;
  }
}
