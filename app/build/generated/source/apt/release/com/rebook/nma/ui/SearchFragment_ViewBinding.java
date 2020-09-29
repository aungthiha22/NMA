// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchFragment_ViewBinding implements Unbinder {
  private SearchFragment target;

  @UiThread
  public SearchFragment_ViewBinding(SearchFragment target, View source) {
    this.target = target;

    target.listView = Utils.findRequiredViewAsType(source, R.id.listview, "field 'listView'", ListView.class);
    target.editsearch = Utils.findRequiredViewAsType(source, R.id.search, "field 'editsearch'", SearchView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.listView = null;
    target.editsearch = null;
  }
}
