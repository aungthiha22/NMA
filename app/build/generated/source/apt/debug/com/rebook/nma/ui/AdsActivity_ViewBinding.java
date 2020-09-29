// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AdsActivity_ViewBinding implements Unbinder {
  private AdsActivity target;

  @UiThread
  public AdsActivity_ViewBinding(AdsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AdsActivity_ViewBinding(AdsActivity target, View source) {
    this.target = target;

    target.ads = Utils.findRequiredViewAsType(source, R.id.ads_img, "field 'ads'", ImageView.class);
    target.exit = Utils.findRequiredViewAsType(source, R.id.exit_ads, "field 'exit'", ImageView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBarForAds, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ads = null;
    target.exit = null;
    target.progressBar = null;
  }
}
