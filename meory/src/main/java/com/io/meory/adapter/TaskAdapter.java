package com.io.meory.adapter;

import android.content.Context;

import com.io.meory.anim.BaseAnim;
import com.io.meory.base.BaseAdapter;
import com.io.meory.base.BaseEvents;
import com.io.meory.delegate.ShareTaskDelegate;
import com.io.meory.holder.TaskViewHolder;
import com.io.meory.observe.TaskObserve;
import com.io.meory.utils.TypeToken;

public abstract class TaskAdapter extends BaseAdapter<ShareTaskDelegate.TaskBuild, ShareTaskDelegate, TaskViewHolder, TaskObserve> {

    public TaskViewHolder vh;

    public Context mContext;

    protected TaskAdapter subscribe(TaskObserve<?> observe) {
        build.baseObserves.add(observe);
        return this;
    }

    @Override
    public final void init(TaskViewHolder vh) {
        this.vh = vh;
        this.mContext = getContext();
        init();
        matchAllEvents();
    }

    public abstract void init();

    @Override
    public final void lazy(TaskViewHolder vh) {
        lazy();
        matchAllEventsClear();
    }

    public abstract void lazy();

    @Override
    public final void preload(TaskViewHolder vh) {
        preload();
    }

    public abstract void preload();

    public TaskViewHolder getVh() {
        return vh;
    }

    public Context getContext() {
        return getVh().getContext();
    }

    @Override
    public final ShareTaskDelegate.TaskBuild build() {
        super.build();
        if (build == null)
            return null;
        build.setFrontType(getFrontType());
        build.setLeaveRetain(leaveRetain());
        return build;
    }

    public final void bindAnimation(BaseAnim anim) {
        if (build == null)
            return;
        build.bindAnimation(anim);
    }

    public int getFrontType() {
        return -1;
    }

    /***
     *  该页面不可视时是否保留View
     */
    public boolean leaveRetain() {
        return true;
    }

    //处理缓存中事件
    private void matchAllEvents() {
        for (BaseEvents item : build.getEventQueue()) {
            build.matchEvent(item);
        }
    }

    //处理缓存中事件， 对于无法处理的进行清除
    private void matchAllEventsClear() {
        matchAllEvents();
        build.getEventQueue().clear();
    }

    public final void post(int type, Object o) {
        vh.getShareView().postData(type, o);
    }

    public final void postData(int type, TypeToken<?> typeToken, Object o) {
        vh.getShareView().postData(type, typeToken, o);
    }

    public final void goTo(int type) {
        vh.getShareView().goTo(type);
    }

    public final void back() {
        vh.getShareView().back();
    }

    @Override
    protected final ShareTaskDelegate.TaskBuild creatBuild() {
        return ShareTaskDelegate.TaskBuild.getInstance(delege, getLayoutId(), getType());
    }
}