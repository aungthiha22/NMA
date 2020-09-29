// Generated code from Butter Knife. Do not modify!
package com.rebook.nma.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.rebook.nma.R;
import com.rebook.nma.widget.ZawgyiCheckBox;
import com.rebook.nma.widget.ZawgyiEditText;
import com.rebook.nma.widget.ZawgyiTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.loginRegister = Utils.findRequiredViewAsType(source, R.id.login_register, "field 'loginRegister'", ZawgyiTextView.class);
    target.loginLogin = Utils.findRequiredViewAsType(source, R.id.login_login, "field 'loginLogin'", ZawgyiTextView.class);
    target.loginUsername = Utils.findRequiredViewAsType(source, R.id.login_username, "field 'loginUsername'", ZawgyiEditText.class);
    target.loginPassword = Utils.findRequiredViewAsType(source, R.id.login_password, "field 'loginPassword'", ZawgyiEditText.class);
    target.facebookImageView = Utils.findRequiredViewAsType(source, R.id.facebook_image, "field 'facebookImageView'", ImageView.class);
    target.loginWithFacebook = Utils.findRequiredViewAsType(source, R.id.facebook_login, "field 'loginWithFacebook'", LoginButton.class);
    target.googleImageView = Utils.findRequiredViewAsType(source, R.id.google, "field 'googleImageView'", ImageView.class);
    target.loginWithGoogle = Utils.findRequiredViewAsType(source, R.id.google_login, "field 'loginWithGoogle'", SignInButton.class);
    target.cbLogin = Utils.findRequiredViewAsType(source, R.id.check_login, "field 'cbLogin'", ZawgyiCheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.loginRegister = null;
    target.loginLogin = null;
    target.loginUsername = null;
    target.loginPassword = null;
    target.facebookImageView = null;
    target.loginWithFacebook = null;
    target.googleImageView = null;
    target.loginWithGoogle = null;
    target.cbLogin = null;
  }
}
