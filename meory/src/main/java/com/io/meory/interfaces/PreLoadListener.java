package com.io.meory.interfaces;

import com.io.meory.base.BaseViewHolder;

/***
 * 内部预加载功能性接口
 * @param <H>
 */
public interface PreLoadListener<H extends BaseViewHolder> {

    void preLoad(H vh);

}
