// Generated code from Butter Knife. Do not modify!
package com.example.android.potholedetection;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CurrentLocation_ViewBinding implements Unbinder {
  private CurrentLocation target;

  @UiThread
  public CurrentLocation_ViewBinding(CurrentLocation target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CurrentLocation_ViewBinding(CurrentLocation target, View source) {
    this.target = target;

    target.btnProceed = Utils.findRequiredViewAsType(source, R.id.btnLocation, "field 'btnProceed'", Button.class);
    target.tvAddress = Utils.findRequiredViewAsType(source, R.id.tvAddress, "field 'tvAddress'", TextView.class);
    target.tvEmpty = Utils.findRequiredViewAsType(source, R.id.tvEmpty, "field 'tvEmpty'", TextView.class);
    target.rlPick = Utils.findRequiredViewAsType(source, R.id.rlPickLocation, "field 'rlPick'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CurrentLocation target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnProceed = null;
    target.tvAddress = null;
    target.tvEmpty = null;
    target.rlPick = null;
  }
}
