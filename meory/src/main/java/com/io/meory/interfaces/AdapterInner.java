package com.io.meory.interfaces;

import androidx.annotation.LayoutRes;

import com.io.meory.base.BaseBuild;
import com.io.meory.base.BaseDelegate;
import com.io.meory.base.BaseObserve;
import com.io.meory.base.BaseViewHolder;

public interface AdapterInner<T extends BaseBuild, M extends BaseDelegate, H extends BaseViewHolder, D extends BaseObserve> {

    void init(H vh);

    void lazy(H vh);

    void preload(H vh);

    void addLifeListener(LifeListener<H> lifeListener);

    @LayoutRes
    int getLayoutId();

    int getType();

    void injectDelegate(M t);

    T build();
}
