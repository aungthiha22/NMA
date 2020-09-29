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

public class SlideActivity_ViewBinding implements Unbinder {
  private SlideActivity target;

  @UiThread
  public SlideActivity_ViewBinding(SlideActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SlideActivity_ViewBinding(SlideActivity target, View source) {
    this.target = target;

    target.connectFacebook = Utils.findRequiredViewAsType(source, R.id.connect_facebook, "field 'connectFacebook'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SlideActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.connectFacebook = null;
  }
}
