package com.io.meory.holder;

import android.view.View;

import com.io.meory.ShareView;
import com.io.meory.base.BaseViewHolder;

public class TaskViewHolder extends BaseViewHolder {

    protected TaskViewHolder(View pageView, ShareView shareView) {
        super(pageView, shareView);
    }

    public static TaskViewHolder getInstance(View pageView, ShareView shareView) {
        return new TaskViewHolder(pageView, shareView);
    }


}
