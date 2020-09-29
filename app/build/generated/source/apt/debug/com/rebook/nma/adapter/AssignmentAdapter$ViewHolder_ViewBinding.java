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

public class AssignmentAdapter$ViewHolder_ViewBinding implements Unbinder {
  private AssignmentAdapter.ViewHolder target;

  @UiThread
  public AssignmentAdapter$ViewHolder_ViewBinding(AssignmentAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", CardView.class);
    target.txtAssignmentTitle = Utils.findRequiredViewAsType(source, R.id.course_title, "field 'txtAssignmentTitle'", ZawgyiTextView.class);
    target.txtAssignmentDesc = Utils.findRequiredViewAsType(source, R.id.description, "field 'txtAssignmentDesc'", ZawgyiTextView.class);
    target.txtDiscountPrice = Utils.findRequiredViewAsType(source, R.id.discount_price, "field 'txtDiscountPrice'", ZawgyiTextView.class);
    target.txtPrice = Utils.findRequiredViewAsType(source, R.id.course_price, "field 'txtPrice'", ZawgyiTextView.class);
    target.imageViewDownload = Utils.findRequiredViewAsType(source, R.id.general_image, "field 'imageViewDownload'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AssignmentAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cardView = null;
    target.txtAssignmentTitle = null;
    target.txtAssignmentDesc = null;
    target.txtDiscountPrice = null;
    target.txtPrice = null;
    target.imageViewDownload = null;
  }
}
