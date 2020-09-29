// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import com.tuyenmonkey.mkloader.MKLoader;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeFragment_ViewBinding implements Unbinder {
  private HomeFragment target;

  @UiThread
  public HomeFragment_ViewBinding(HomeFragment target, View source) {
    this.target = target;

    target.scrollHome = Utils.findRequiredViewAsType(source, R.id.scroll_home, "field 'scrollHome'", ScrollView.class);
    target.loading = Utils.findRequiredViewAsType(source, R.id.loader_mk, "field 'loading'", MKLoader.class);
    target.popularRecycler = Utils.findRequiredViewAsType(source, R.id.popular_course_recycle, "field 'popularRecycler'", RecyclerView.class);
    target.newsRecycler = Utils.findRequiredViewAsType(source, R.id.new_recycle, "field 'newsRecycler'", RecyclerView.class);
    target.imgHide = Utils.findRequiredViewAsType(source, R.id.hide_image, "field 'imgHide'", ImageView.class);
    target.txtError = Utils.findRequiredViewAsType(source, R.id.error_connection, "field 'txtError'", ZawgyiTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.scrollHome = null;
    target.loading = null;
    target.popularRecycler = null;
    target.newsRecycler = null;
    target.imgHide = null;
    target.txtError = null;
  }
}
