// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileFragment_ViewBinding implements Unbinder {
  private ProfileFragment target;

  @UiThread
  public ProfileFragment_ViewBinding(ProfileFragment target, View source) {
    this.target = target;

    target.txtProfileName = Utils.findRequiredViewAsType(source, R.id.profile_name, "field 'txtProfileName'", ZawgyiTextView.class);
    target.txtProfileEmail = Utils.findRequiredViewAsType(source, R.id.profile_email, "field 'txtProfileEmail'", ZawgyiTextView.class);
    target.cardViewCurrentCourse = Utils.findRequiredViewAsType(source, R.id.current_course_cardView, "field 'cardViewCurrentCourse'", CardView.class);
    target.cardViewHistoryCourse = Utils.findRequiredViewAsType(source, R.id.history_course_cardView, "field 'cardViewHistoryCourse'", CardView.class);
    target.cardViewAssignment = Utils.findRequiredViewAsType(source, R.id.assignment_cardView, "field 'cardViewAssignment'", CardView.class);
    target.cardViewAttendance = Utils.findRequiredViewAsType(source, R.id.attendance_cardView, "field 'cardViewAttendance'", CardView.class);
    target.cardViewAnnouncement = Utils.findRequiredViewAsType(source, R.id.announcement_cardView, "field 'cardViewAnnouncement'", CardView.class);
    target.cardViewMark = Utils.findRequiredViewAsType(source, R.id.mark_cardView, "field 'cardViewMark'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtProfileName = null;
    target.txtProfileEmail = null;
    target.cardViewCurrentCourse = null;
    target.cardViewHistoryCourse = null;
    target.cardViewAssignment = null;
    target.cardViewAttendance = null;
    target.cardViewAnnouncement = null;
    target.cardViewMark = null;
  }
}
