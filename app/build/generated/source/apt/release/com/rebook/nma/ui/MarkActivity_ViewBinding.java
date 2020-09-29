// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import com.tuyenmonkey.mkloader.MKLoader;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MarkActivity_ViewBinding implements Unbinder {
  private MarkActivity target;

  @UiThread
  public MarkActivity_ViewBinding(MarkActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MarkActivity_ViewBinding(MarkActivity target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefresh, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.loading = Utils.findRequiredViewAsType(source, R.id.loader_mk, "field 'loading'", MKLoader.class);
    target.emptyView = Utils.findRequiredViewAsType(source, R.id.emptyView, "field 'emptyView'", ZawgyiTextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MarkActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.swipeRefreshLayout = null;
    target.loading = null;
    target.emptyView = null;
    target.toolbar = null;
  }
}
