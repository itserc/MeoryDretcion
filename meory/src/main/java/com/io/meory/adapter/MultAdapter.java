package com.io.meory.adapter;

import com.io.meory.base.BaseAdapter;
import com.io.meory.delegate.ShareMultiDelegate;
import com.io.meory.holder.MultViewHolder;
import com.io.meory.observe.MultiObserve;

public abstract class MultAdapter extends BaseAdapter<ShareMultiDelegate.MultiBuild, ShareMultiDelegate, MultViewHolder, MultiObserve> {

    protected MultAdapter subscribe(MultiObserve<?> observe) {
        build.baseObserves.add(observe);
        return this;
    }

    @Override
    public ShareMultiDelegate.MultiBuild build() {
        return super.build();
    }

    @Override
    protected final ShareMultiDelegate.MultiBuild creatBuild() {
        return ShareMultiDelegate.MultiBuild.getInstance(delege, getLayoutId(), getType());
    }
}
