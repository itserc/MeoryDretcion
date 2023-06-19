package com.io.meory.interfaces;

import com.io.meory.base.BaseViewHolder;

/***
 * 内部初始化功能性接口
 * @param <H>
 */
public interface InitListener<H extends BaseViewHolder> {
    void init(H vh);
}
