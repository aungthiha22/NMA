// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AttendanceAdapter$LoadingViewHolder_ViewBinding implements Unbinder {
  private AttendanceAdapter.LoadingViewHolder target;

  @UiThread
  public AttendanceAdapter$LoadingViewHolder_ViewBinding(AttendanceAdapter.LoadingViewHolder target,
      View source) {
    this.target = target;

    target.progressBar = Utils.findRequiredViewAsType(source, R.id.loading, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AttendanceAdapter.LoadingViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
  }
}
