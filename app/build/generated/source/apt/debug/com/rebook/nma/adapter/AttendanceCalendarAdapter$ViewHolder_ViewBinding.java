// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AttendanceCalendarAdapter$ViewHolder_ViewBinding implements Unbinder {
  private AttendanceCalendarAdapter.ViewHolder target;

  @UiThread
  public AttendanceCalendarAdapter$ViewHolder_ViewBinding(AttendanceCalendarAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.txtMarkName = Utils.findRequiredViewAsType(source, R.id.mark_name, "field 'txtMarkName'", ZawgyiTextView.class);
    target.txtMarkStatus = Utils.findRequiredViewAsType(source, R.id.mark_status, "field 'txtMarkStatus'", ZawgyiTextView.class);
    target.txtMarkReMark = Utils.findRequiredViewAsType(source, R.id.mark_remark, "field 'txtMarkReMark'", ZawgyiTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AttendanceCalendarAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtMarkName = null;
    target.txtMarkStatus = null;
    target.txtMarkReMark = null;
  }
}
