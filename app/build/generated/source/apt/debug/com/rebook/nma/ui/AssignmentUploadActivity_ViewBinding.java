// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.andexert.library.RippleView;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import com.tuyenmonkey.mkloader.MKLoader;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AssignmentUploadActivity_ViewBinding implements Unbinder {
  private AssignmentUploadActivity target;

  @UiThread
  public AssignmentUploadActivity_ViewBinding(AssignmentUploadActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AssignmentUploadActivity_ViewBinding(AssignmentUploadActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.rippleViewChoiceFile = Utils.findRequiredViewAsType(source, R.id.choice_file_rippleView, "field 'rippleViewChoiceFile'", RippleView.class);
    target.txtFileName = Utils.findRequiredViewAsType(source, R.id.file_name, "field 'txtFileName'", ZawgyiTextView.class);
    target.cardViewUploadFile = Utils.findRequiredViewAsType(source, R.id.upLoadCardView, "field 'cardViewUploadFile'", CardView.class);
    target.cardViewDownloadFile = Utils.findRequiredViewAsType(source, R.id.cardViewDownload, "field 'cardViewDownloadFile'", CardView.class);
    target.txtAssignmentDesc = Utils.findRequiredViewAsType(source, R.id.assignment_description, "field 'txtAssignmentDesc'", ZawgyiTextView.class);
    target.txtAssignmentFile = Utils.findRequiredViewAsType(source, R.id.assignment_file, "field 'txtAssignmentFile'", ZawgyiTextView.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.assignment_scroll, "field 'scrollView'", ScrollView.class);
    target.loader = Utils.findRequiredViewAsType(source, R.id.loader_mk, "field 'loader'", MKLoader.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AssignmentUploadActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.rippleViewChoiceFile = null;
    target.txtFileName = null;
    target.cardViewUploadFile = null;
    target.cardViewDownloadFile = null;
    target.txtAssignmentDesc = null;
    target.txtAssignmentFile = null;
    target.scrollView = null;
    target.loader = null;
  }
}
