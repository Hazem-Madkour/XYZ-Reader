package com.example.xyzreader;

import android.app.Activity;
import android.os.Build;
import android.transition.TransitionInflater;

public class UtilityTransition {

    static public void setupEnterFadeAnimation(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setEnterTransition(TransitionInflater.from(activity).inflateTransition(R.transition.activity_fade));
        }
    }

    static public void setupExitFadeAnimation(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setExitTransition(TransitionInflater.from(activity).inflateTransition(R.transition.activity_fade));
        }
    }

    static public void setupEnterExplodeAnimation(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setEnterTransition(TransitionInflater.from(activity).inflateTransition(R.transition.activity_fade));
        }
    }

    static public void setupExitExplodeAnimation(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setExitTransition(TransitionInflater.from(activity).inflateTransition(R.transition.activity_fade));
        }
    }

}
