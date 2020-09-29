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

public class CustomAdapter$LoadingViewHolder_ViewBinding implements Unbinder {
  private CustomAdapter.LoadingViewHolder target;

  @UiThread
  public CustomAdapter$LoadingViewHolder_ViewBinding(CustomAdapter.LoadingViewHolder target,
      View source) {
    this.target = target;

    target.progressBar = Utils.findRequiredViewAsType(source, R.id.loading, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CustomAdapter.LoadingViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
  }
}
