package com.io.meory.holder;

import android.view.View;

import com.io.meory.ShareView;
import com.io.meory.base.BaseViewHolder;

public class MultViewHolder extends BaseViewHolder {

    protected MultViewHolder(View pageView, ShareView shareView) {
        super(pageView, shareView);
    }

    public static MultViewHolder getInstance(View pageView, ShareView shareView) {
        return new MultViewHolder(pageView, shareView);
    }


}
