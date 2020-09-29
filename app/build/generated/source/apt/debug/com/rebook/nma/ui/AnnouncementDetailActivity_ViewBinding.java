// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ScrollView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiTextView;
import com.tuyenmonkey.mkloader.MKLoader;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AnnouncementDetailActivity_ViewBinding implements Unbinder {
  private AnnouncementDetailActivity target;

  @UiThread
  public AnnouncementDetailActivity_ViewBinding(AnnouncementDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AnnouncementDetailActivity_ViewBinding(AnnouncementDetailActivity target, View source) {
    this.target = target;

    target.txtAnnocementName = Utils.findRequiredViewAsType(source, R.id.announcement_name, "field 'txtAnnocementName'", ZawgyiTextView.class);
    target.txtAnnoncementDate = Utils.findRequiredViewAsType(source, R.id.announcement_date, "field 'txtAnnoncementDate'", ZawgyiTextView.class);
    target.cardViewDownload = Utils.findRequiredViewAsType(source, R.id.announcement_download_cardView, "field 'cardViewDownload'", CardView.class);
    target.txtAnnouncementDesc = Utils.findRequiredViewAsType(source, R.id.announcement_desc, "field 'txtAnnouncementDesc'", ZawgyiTextView.class);
    target.loader = Utils.findRequiredViewAsType(source, R.id.detail_loading, "field 'loader'", MKLoader.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.announcement_scroll, "field 'scrollView'", ScrollView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AnnouncementDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtAnnocementName = null;
    target.txtAnnoncementDate = null;
    target.cardViewDownload = null;
    target.txtAnnouncementDesc = null;
    target.loader = null;
    target.scrollView = null;
  }
}
