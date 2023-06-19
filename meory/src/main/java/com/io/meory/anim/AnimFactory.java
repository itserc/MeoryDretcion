package com.io.meory.anim;

import android.animation.AnimatorSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

class AnimFactory {

    private AnimHolder enterAnimHolder;

    private AnimHolder exitAnimHolder;

    public static class Holder {
        static AnimFactory animFactory = new AnimFactory();
    }

    public static AnimFactory getInstance() {
        return Holder.animFactory;
    }

    /***
     * 当分别插入View的入场出场动画才进行播放
     * @param animHolder
     */
    void insert(AnimHolder animHolder) {
        if (animHolder == null)
            return;
        if (animHolder.getEnter())
            enterAnimHolder = animHolder;
        else exitAnimHolder = animHolder;
        if (enterAnimHolder == null)
            return;
        if (exitAnimHolder == null)
            return;
        loop();
    }

    private void loop() {
        final AnimHolder temStart = enterAnimHolder;
        final AnimHolder temEnd = exitAnimHolder;
        exitAnimHolder = null;
        enterAnimHolder = null;
        //开启硬件加速
        temStart.getPageView().setLayerType(View.LAYER_TYPE_HARDWARE, null);
        temEnd.getPageView().setLayerType(View.LAYER_TYPE_HARDWARE, null);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(temStart.getAnimator(), temEnd.getAnimator());
        set.setInterpolator(new DecelerateInterpolator(1.5f));
        set.start();
    }
}
