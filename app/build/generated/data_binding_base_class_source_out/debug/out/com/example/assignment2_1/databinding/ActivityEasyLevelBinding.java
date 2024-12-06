// Generated by view binder compiler. Do not edit!
package com.example.assignment2_1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.assignment2_1.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityEasyLevelBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView txteasyLevel;

  private ActivityEasyLevelBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView imageView2, @NonNull ConstraintLayout main,
      @NonNull TextView txteasyLevel) {
    this.rootView = rootView;
    this.imageView2 = imageView2;
    this.main = main;
    this.txteasyLevel = txteasyLevel;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityEasyLevelBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityEasyLevelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_easy_level, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityEasyLevelBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView2;
      ImageView imageView2 = ViewBindings.findChildViewById(rootView, id);
      if (imageView2 == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.txteasyLevel;
      TextView txteasyLevel = ViewBindings.findChildViewById(rootView, id);
      if (txteasyLevel == null) {
        break missingId;
      }

      return new ActivityEasyLevelBinding((ConstraintLayout) rootView, imageView2, main,
          txteasyLevel);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}