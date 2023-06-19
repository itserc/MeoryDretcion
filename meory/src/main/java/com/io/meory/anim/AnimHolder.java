package com.io.meory.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.view.View;

import com.io.meory.interfaces.AnimStateListener;
import com.io.meory.utils.Utils;

class AnimHolder {

    private int type;

    //是否进入动画
    private boolean isEnter;

    //是否是当前栈顶的View动画
    private boolean isTopTask;

    private View pageView;

    private AnimStateListener animStateListener;

    private long duration;

    private Animator.AnimatorListener listener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            if (!isEnter)
                animStateListener.exitAnimEnd(pageView, isTopTask);
            else
                animStateListener.enterAnimEnd(pageView, isTopTask);
        }

        @Override
        public void onAnimationStart(Animator animation) {
            if (!isEnter)
                animStateListener.exitAnimStar(pageView, isTopTask);
            else
                animStateListener.enterAnimStar(pageView, isTopTask);
        }
    };

    private ObjectAnimator animator;

    private AnimHolder(@AnimHelper.Type int type, View preView) {
        this.type = type;
        this.pageView = preView;
    }

    /***
     *  将一个进入和一个退出合成一个AnimationSet
     * @param type
     * @param preView
     * @return
     */
    public static AnimHolder getInstance(@AnimHelper.Type int type, View preView) {
        return new AnimHolder(type, preView);
    }

    AnimHolder bindListener(AnimStateListener animStateListener) {
        this.animStateListener = animStateListener;
        return this;
    }

    AnimHolder isTopTask(boolean isTopTask) {
        this.isTopTask = isTopTask;
        return this;
    }

    AnimHolder isDuration(long duration) {
        this.duration = duration;
        return this;
    }

    AnimHolder isEnter(boolean isEnter) {
        this.isEnter = isEnter;
        return this;
    }

    boolean getEnter() {
        return isEnter;
    }

    public AnimHolder build() {

        if (pageView == null)
            return null;

        selectAnimator();

        if (type == AnimHelper.NULL)
            animator.setDuration(0);
        else
            animator.setDuration(duration);

        animator.addListener(listener);
        AnimFactory.getInstance().insert(this);
        return this;
    }

    Animator getAnimator() {
        return animator;
    }

    public View getPageView() {
        return pageView;
    }

    public long getDuration() {
        return duration;
    }

    /***
     * 挑选动画
     */
    private void selectAnimator() {

        switch (type) {

            case AnimHelper.ALPHA_DOWN_HIDE:
                animator = aplha_down_hide();
                break;

            case AnimHelper.ALPHA_UP_SHOW:
                animator = alpha_up_show();
                break;

            case AnimHelper.LEFT_ALL_SHOW:
                animator = left_all_show();
                break;
            case AnimHelper.RIGHT_ALL_HIDE:
                animator = right_all_hide();
                break;

            case AnimHelper.RIGHT_HALF_SHOW:
                animator = right_half_show();
                break;

            case AnimHelper.LEFT_HALF_HIDE:
                animator = left_half_hide();
                break;

            default:
                animator = null_null();

        }

    }

    @SuppressLint("ObjectAnimatorBinding")
    private ObjectAnimator null_null() {
        return ObjectAnimator.ofFloat(pageView, "", 1, 1);
    }

    private ObjectAnimator aplha_down_hide() {
        Keyframe alphaK1 = Keyframe.ofFloat(0f, 1);
        Keyframe alphaK2 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofKeyframe("alpha", alphaK1, alphaK2);
        Keyframe tranK1 = Keyframe.ofFloat(0f, 0);
        Keyframe tranK2 = Keyframe.ofFloat(1f, Utils.getY());
        PropertyValuesHolder translation = PropertyValuesHolder.ofKeyframe("translationY", tranK1, tranK2);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(pageView, alpha, translation);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                pageView.setAlpha(1f);
                pageView.setTranslationY(0);
            }
        });
        return objectAnimator;
    }

    private ObjectAnimator alpha_up_show() {
        Keyframe alphaK1 = Keyframe.ofFloat(0f, 0);
        Keyframe alphaK2 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofKeyframe("alpha", alphaK1, alphaK2);
        Keyframe tranK1 = Keyframe.ofFloat(0f, Utils.getY());
        Keyframe tranK2 = Keyframe.ofFloat(1f, 0);
        PropertyValuesHolder translation = PropertyValuesHolder.ofKeyframe("translationY", tranK1, tranK2);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(pageView, alpha, translation);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                pageView.setAlpha(1f);
                pageView.setTranslationY(0);
            }
        });
        return objectAnimator;
    }

    private ObjectAnimator left_all_show() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(pageView, "translationX", Utils.getX(), 0)
                .setDuration(duration);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                pageView.setTranslationX(0);
            }
        });
        return objectAnimator;
    }

    private ObjectAnimator right_all_hide() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(pageView, "translationX", 0, Utils.getX())
                .setDuration(duration);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                pageView.setTranslationX(0);
            }
        });
        return objectAnimator;
    }

    private ObjectAnimator right_half_show() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(pageView, "translationX", -Utils.getX() / 2f, 0)
                .setDuration(duration);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                pageView.setTranslationX(0);
            }
        });
        return objectAnimator;
    }

    private ObjectAnimator left_half_hide() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(pageView, "translationX", 0, -Utils.getX() / 2f)
                .setDuration(duration);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                pageView.setTranslationX(0);
            }
        });
        return objectAnimator;
    }

}







