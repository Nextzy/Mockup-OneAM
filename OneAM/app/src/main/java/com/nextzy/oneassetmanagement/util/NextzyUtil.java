/*
Copyright 2016 Nextzy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.nextzy.oneassetmanagement.util;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Akexorcist on 1/7/2016 AD.
 */
public class NextzyUtil {
    public static void startAnimator(Context context, View view, int animResource, final AnimateFinishCallback callback) {
        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(context, animResource);
        anim.setTarget(view);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (callback != null) {
                    callback.onAnimateFinished();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        anim.start();
        view.setVisibility(View.VISIBLE);
    }

    public static void startAnimatorSet(Context context, View view, int animResource, final AnimateFinishCallback callback) {
        AnimatorSet animSet = (AnimatorSet)AnimatorInflater.loadAnimator(context, animResource);
        animSet.setTarget(view);
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (callback != null) {
                    callback.onAnimateFinished();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animSet.start();
        view.setVisibility(View.VISIBLE);
    }

    public interface AnimateFinishCallback {
        void onAnimateFinished();
    }

    public static void launch(long startTime, final LaunchCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onLaunchCallback();
                }
            }
        }, startTime);
    }

    public interface LaunchCallback {
        void onLaunchCallback();
    }

    public static float getPixelFromDp(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }
}
