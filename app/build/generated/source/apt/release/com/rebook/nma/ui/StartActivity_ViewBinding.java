// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StartActivity_ViewBinding implements Unbinder {
  private StartActivity target;

  @UiThread
  public StartActivity_ViewBinding(StartActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StartActivity_ViewBinding(StartActivity target, View source) {
    this.target = target;

    target.imgExit = Utils.findRequiredViewAsType(source, R.id.exit_ads, "field 'imgExit'", ImageView.class);
    target.imgAds = Utils.findRequiredViewAsType(source, R.id.ads_img, "field 'imgAds'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StartActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgExit = null;
    target.imgAds = null;
  }
}
