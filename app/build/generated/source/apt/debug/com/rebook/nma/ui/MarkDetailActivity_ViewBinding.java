// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import com.tuyenmonkey.mkloader.MKLoader;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MarkDetailActivity_ViewBinding implements Unbinder {
  private MarkDetailActivity target;

  @UiThread
  public MarkDetailActivity_ViewBinding(MarkDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MarkDetailActivity_ViewBinding(MarkDetailActivity target, View source) {
    this.target = target;

    target.txtMarkAttendance = Utils.findRequiredViewAsType(source, R.id.mark_attendance, "field 'txtMarkAttendance'", ZawgyiTextView.class);
    target.txtMarkDiscussion = Utils.findRequiredViewAsType(source, R.id.mark_discussion, "field 'txtMarkDiscussion'", ZawgyiTextView.class);
    target.txtMarkPresentation = Utils.findRequiredViewAsType(source, R.id.mark_presentation, "field 'txtMarkPresentation'", ZawgyiTextView.class);
    target.txtMarkAssignment = Utils.findRequiredViewAsType(source, R.id.mark_assignment, "field 'txtMarkAssignment'", ZawgyiTextView.class);
    target.txtMarkExam = Utils.findRequiredViewAsType(source, R.id.mark_exam, "field 'txtMarkExam'", ZawgyiTextView.class);
    target.txtMarkResult = Utils.findRequiredViewAsType(source, R.id.mark_final_result, "field 'txtMarkResult'", ZawgyiTextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scroll_mark, "field 'scrollView'", ScrollView.class);
    target.mkLoader = Utils.findRequiredViewAsType(source, R.id.loader_mk, "field 'mkLoader'", MKLoader.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MarkDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtMarkAttendance = null;
    target.txtMarkDiscussion = null;
    target.txtMarkPresentation = null;
    target.txtMarkAssignment = null;
    target.txtMarkExam = null;
    target.txtMarkResult = null;
    target.toolbar = null;
    target.scrollView = null;
    target.mkLoader = null;
  }
}
