// Generated code from Butter Knife. Do not modify!
package com.example.android.potholedetection;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Signup_ViewBinding implements Unbinder {
  private Signup target;

  @UiThread
  public Signup_ViewBinding(Signup target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Signup_ViewBinding(Signup target, View source) {
    this.target = target;

    target._nameText = Utils.findRequiredViewAsType(source, R.id.input_name, "field '_nameText'", EditText.class);
    target._usernameText = Utils.findRequiredViewAsType(source, R.id.input_username, "field '_usernameText'", EditText.class);
    target._emailText = Utils.findRequiredViewAsType(source, R.id.input_email, "field '_emailText'", EditText.class);
    target._passwordText = Utils.findRequiredViewAsType(source, R.id.input_password, "field '_passwordText'", EditText.class);
    target._signupButton = Utils.findRequiredViewAsType(source, R.id.btn_signup, "field '_signupButton'", Button.class);
    target._loginLink = Utils.findRequiredViewAsType(source, R.id.link_login, "field '_loginLink'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Signup target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._nameText = null;
    target._usernameText = null;
    target._emailText = null;
    target._passwordText = null;
    target._signupButton = null;
    target._loginLink = null;
  }
}
