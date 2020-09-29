// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import com.tuyenmonkey.mkloader.MKLoader;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CourseDetailActivity_ViewBinding implements Unbinder {
  private CourseDetailActivity target;

  @UiThread
  public CourseDetailActivity_ViewBinding(CourseDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CourseDetailActivity_ViewBinding(CourseDetailActivity target, View source) {
    this.target = target;

    target.scrollView = Utils.findRequiredViewAsType(source, R.id.detail_scroll, "field 'scrollView'", ScrollView.class);
    target.loading = Utils.findRequiredViewAsType(source, R.id.detail_loading, "field 'loading'", MKLoader.class);
    target.txtCourseTitle = Utils.findRequiredViewAsType(source, R.id.course_detail_title, "field 'txtCourseTitle'", ZawgyiTextView.class);
    target.imgCourseDetail = Utils.findRequiredViewAsType(source, R.id.course_detail_image, "field 'imgCourseDetail'", ImageView.class);
    target.txtStudentCount = Utils.findRequiredViewAsType(source, R.id.course_detail_student_count, "field 'txtStudentCount'", ZawgyiTextView.class);
    target.txtReview = Utils.findRequiredViewAsType(source, R.id.course_detail_review, "field 'txtReview'", ZawgyiTextView.class);
    target.txtHours = Utils.findRequiredViewAsType(source, R.id.course_detail_hours, "field 'txtHours'", ZawgyiTextView.class);
    target.txtDate = Utils.findRequiredViewAsType(source, R.id.course_detail_date, "field 'txtDate'", ZawgyiTextView.class);
    target.txtFees = Utils.findRequiredViewAsType(source, R.id.course_detail_fees, "field 'txtFees'", ZawgyiTextView.class);
    target.txtTime = Utils.findRequiredViewAsType(source, R.id.course_detail_time, "field 'txtTime'", ZawgyiTextView.class);
    target.txtInstructorImage = Utils.findRequiredViewAsType(source, R.id.course_detail_instructor_image, "field 'txtInstructorImage'", ImageView.class);
    target.txtInstructorName = Utils.findRequiredViewAsType(source, R.id.course_detail_instructor_name, "field 'txtInstructorName'", ZawgyiTextView.class);
    target.txtDescription = Utils.findRequiredViewAsType(source, R.id.course_detail_description, "field 'txtDescription'", ZawgyiTextView.class);
    target.txtCertification = Utils.findRequiredViewAsType(source, R.id.course_detail_certificate, "field 'txtCertification'", ZawgyiTextView.class);
    target.txtSeeCurriculum = Utils.findRequiredViewAsType(source, R.id.see_curriculum, "field 'txtSeeCurriculum'", ZawgyiTextView.class);
    target.txtSeeReviews = Utils.findRequiredViewAsType(source, R.id.see_reviews, "field 'txtSeeReviews'", ZawgyiTextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CourseDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.scrollView = null;
    target.loading = null;
    target.txtCourseTitle = null;
    target.imgCourseDetail = null;
    target.txtStudentCount = null;
    target.txtReview = null;
    target.txtHours = null;
    target.txtDate = null;
    target.txtFees = null;
    target.txtTime = null;
    target.txtInstructorImage = null;
    target.txtInstructorName = null;
    target.txtDescription = null;
    target.txtCertification = null;
    target.txtSeeCurriculum = null;
    target.txtSeeReviews = null;
    target.toolbar = null;
  }
}
