// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import com.tuyenmonkey.mkloader.MKLoader;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GeneralListFragment_ViewBinding implements Unbinder {
  private GeneralListFragment target;

  @UiThread
  public GeneralListFragment_ViewBinding(GeneralListFragment target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefresh, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.loading = Utils.findRequiredViewAsType(source, R.id.loader_mk, "field 'loading'", MKLoader.class);
    target.emptyView = Utils.findRequiredViewAsType(source, R.id.emptyView, "field 'emptyView'", ZawgyiTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GeneralListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.swipeRefreshLayout = null;
    target.loading = null;
    target.emptyView = null;
  }
}