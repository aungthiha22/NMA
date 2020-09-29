// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class JobDetailActivity_ViewBinding implements Unbinder {
  private JobDetailActivity target;

  @UiThread
  public JobDetailActivity_ViewBinding(JobDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public JobDetailActivity_ViewBinding(JobDetailActivity target, View source) {
    this.target = target;

    target.txtJobTitle = Utils.findRequiredViewAsType(source, R.id.job_title, "field 'txtJobTitle'", ZawgyiTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    JobDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtJobTitle = null;
  }
}
