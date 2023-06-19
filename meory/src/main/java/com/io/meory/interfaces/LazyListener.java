package com.io.meory.interfaces;

import com.io.meory.base.BaseViewHolder;

/***
 * 内部懒加载功能接口
 * @param <H>
 */
public interface LazyListener<H extends BaseViewHolder> {

    void onLazy(H vh);

}
