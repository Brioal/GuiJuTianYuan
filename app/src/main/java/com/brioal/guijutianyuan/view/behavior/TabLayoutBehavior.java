package com.brioal.guijutianyuan.view.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Brioal on 2016/5/9.
 */
public class TabLayoutBehavior extends CoordinatorLayout.Behavior {
    private TranslateAnimation translateAnimation;

    public TabLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if (dy > 0) { //下滑
            if (child.getVisibility() == View.GONE) {
                return;
            } else {
                startAnimation(child, 0, child.getMeasuredHeight());
            }
        } else { //上滑
            if (child.getVisibility() == View.VISIBLE) {
                return;
            } else {
                startAnimation(child, child.getMeasuredHeight(), 0);
            }
        }
    }

    public void startAnimation(final View view, final int startY, int endY) {
        translateAnimation = new TranslateAnimation(0.f, 0.f, startY, endY);
        translateAnimation.setDuration(500);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (startY == 0) {
                    view.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(translateAnimation);
    }
}
