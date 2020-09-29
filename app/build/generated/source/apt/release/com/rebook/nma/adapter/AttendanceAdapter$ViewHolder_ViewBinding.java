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

public class AttendanceAdapter$ViewHolder_ViewBinding implements Unbinder {
  private AttendanceAdapter.ViewHolder target;

  @UiThread
  public AttendanceAdapter$ViewHolder_ViewBinding(AttendanceAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.txtMarkName = Utils.findRequiredViewAsType(source, R.id.attendance_name, "field 'txtMarkName'", ZawgyiTextView.class);
    target.txtMarkBatch = Utils.findRequiredViewAsType(source, R.id.attendance_batch, "field 'txtMarkBatch'", ZawgyiTextView.class);
    target.txtMarkSeeDetail = Utils.findRequiredViewAsType(source, R.id.attendance_see_detail, "field 'txtMarkSeeDetail'", ZawgyiTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AttendanceAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtMarkName = null;
    target.txtMarkBatch = null;
    target.txtMarkSeeDetail = null;
  }
}
