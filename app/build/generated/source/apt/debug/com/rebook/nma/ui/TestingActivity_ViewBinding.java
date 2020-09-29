// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TestingActivity_ViewBinding implements Unbinder {
  private TestingActivity target;

  @UiThread
  public TestingActivity_ViewBinding(TestingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TestingActivity_ViewBinding(TestingActivity target, View source) {
    this.target = target;

    target.btnTesting = Utils.findRequiredViewAsType(source, R.id.testing_button, "field 'btnTesting'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TestingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnTesting = null;
  }
}
