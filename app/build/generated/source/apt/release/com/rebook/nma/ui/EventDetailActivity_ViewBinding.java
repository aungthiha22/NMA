// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import com.tuyenmonkey.mkloader.MKLoader;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EventDetailActivity_ViewBinding implements Unbinder {
  private EventDetailActivity target;

  @UiThread
  public EventDetailActivity_ViewBinding(EventDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EventDetailActivity_ViewBinding(EventDetailActivity target, View source) {
    this.target = target;

    target.layout = Utils.findRequiredViewAsType(source, R.id.event_linearLayout, "field 'layout'", LinearLayout.class);
    target.txtEventDetailTitle = Utils.findRequiredViewAsType(source, R.id.event_detail_title, "field 'txtEventDetailTitle'", ZawgyiTextView.class);
    target.txtEventDetailTime = Utils.findRequiredViewAsType(source, R.id.event_detail_time, "field 'txtEventDetailTime'", ZawgyiTextView.class);
    target.txtEventDetailAddress = Utils.findRequiredViewAsType(source, R.id.event_detail_address, "field 'txtEventDetailAddress'", ZawgyiTextView.class);
    target.txtEventDetailDesc = Utils.findRequiredViewAsType(source, R.id.event_detail_description, "field 'txtEventDetailDesc'", ZawgyiTextView.class);
    target.imgEventDetailImage = Utils.findRequiredViewAsType(source, R.id.event_detail_image, "field 'imgEventDetailImage'", ImageView.class);
    target.loader = Utils.findRequiredViewAsType(source, R.id.event_detail_loading, "field 'loader'", MKLoader.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EventDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.layout = null;
    target.txtEventDetailTitle = null;
    target.txtEventDetailTime = null;
    target.txtEventDetailAddress = null;
    target.txtEventDetailDesc = null;
    target.imgEventDetailImage = null;
    target.loader = null;
    target.toolbar = null;
  }
}
