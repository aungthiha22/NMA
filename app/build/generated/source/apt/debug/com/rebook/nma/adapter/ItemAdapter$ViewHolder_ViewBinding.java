// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ItemAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ItemAdapter.ViewHolder target;

  @UiThread
  public ItemAdapter$ViewHolder_ViewBinding(ItemAdapter.ViewHolder target, View source) {
    this.target = target;

    target.txtEventName = Utils.findRequiredViewAsType(source, R.id.event_name, "field 'txtEventName'", ZawgyiTextView.class);
    target.imgEvents = Utils.findRequiredViewAsType(source, R.id.event_image, "field 'imgEvents'", ImageView.class);
    target.txtEventTime = Utils.findRequiredViewAsType(source, R.id.event_time, "field 'txtEventTime'", ZawgyiTextView.class);
    target.txtEventlocation = Utils.findRequiredViewAsType(source, R.id.event_location, "field 'txtEventlocation'", ZawgyiTextView.class);
    target.txtEventDescription = Utils.findRequiredViewAsType(source, R.id.event_description, "field 'txtEventDescription'", ZawgyiTextView.class);
    target.cardViewEvent = Utils.findRequiredViewAsType(source, R.id.cardView_event, "field 'cardViewEvent'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ItemAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtEventName = null;
    target.imgEvents = null;
    target.txtEventTime = null;
    target.txtEventlocation = null;
    target.txtEventDescription = null;
    target.cardViewEvent = null;
  }
}
