// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiEditText;
import com.rebook.nma.widget.ZawgyiTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target, View source) {
    this.target = target;

    target.login = Utils.findRequiredViewAsType(source, R.id.login, "field 'login'", ZawgyiTextView.class);
    target.register = Utils.findRequiredViewAsType(source, R.id.register, "field 'register'", ZawgyiTextView.class);
    target.registerFirstName = Utils.findRequiredViewAsType(source, R.id.register_first_name, "field 'registerFirstName'", ZawgyiEditText.class);
    target.registerLastName = Utils.findRequiredViewAsType(source, R.id.register_last_name, "field 'registerLastName'", ZawgyiEditText.class);
    target.registerUsername = Utils.findRequiredViewAsType(source, R.id.register_username, "field 'registerUsername'", ZawgyiEditText.class);
    target.registerEmail = Utils.findRequiredViewAsType(source, R.id.register_email, "field 'registerEmail'", ZawgyiEditText.class);
    target.registerPassword = Utils.findRequiredViewAsType(source, R.id.register_password, "field 'registerPassword'", ZawgyiEditText.class);
    target.registerConfirmPassword = Utils.findRequiredViewAsType(source, R.id.register_confirm_password, "field 'registerConfirmPassword'", ZawgyiEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.login = null;
    target.register = null;
    target.registerFirstName = null;
    target.registerLastName = null;
    target.registerUsername = null;
    target.registerEmail = null;
    target.registerPassword = null;
    target.registerConfirmPassword = null;
  }
}
