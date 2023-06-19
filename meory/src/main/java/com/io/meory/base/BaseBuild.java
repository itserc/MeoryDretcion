package com.io.meory.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.LayoutRes;

import com.io.meory.ShareView;
import com.io.meory.interfaces.InitListener;
import com.io.meory.interfaces.LazyListener;
import com.io.meory.interfaces.LifeListener;
import com.io.meory.interfaces.PreLoadListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseBuild<T extends BaseBuild, M extends BaseDelegate, H extends BaseViewHolder, D extends BaseObserve> {

    private int contentLayout;

    //ViewHolder
    private View pageView;

    private ViewStub pageRoot;

    protected ShareView rootView;

    //Build与Layout对应的Type
    protected int type;

    //是否已经init
    protected boolean isInit;

    //是否已经lazy
    protected boolean isLazy;

    //pageView的持有类，并扩展其他方法
    private H vh;

    private M delegate;

    //事件消费订阅者
    public List<D> baseObserves = new ArrayList<>();

    //事件队列
    private List<BaseEvents> eventQueue = new ArrayList<>();

    InitListener initListener = new InitListener<H>() {

        @Override
        public void init(H vh) {

        }
    };

    LazyListener lazyListener = new LazyListener() {
        @Override
        public void onLazy(BaseViewHolder vh) {

        }
    };

    PreLoadListener preLoadListener = new PreLoadListener() {
        @Override
        public void preLoad(BaseViewHolder vh) {

        }
    };

    public List<LifeListener<H>> lifeListeners = new ArrayList<>();

    protected BaseBuild(M delegate, @LayoutRes int layout, int type) {
        this.delegate = delegate;
        this.contentLayout = layout;
        this.type = type;
        this.rootView = (ShareView) delegate.rootView;
    }

    /***
     * 初始化回调，非复用布局Layout只执行一次
     * @return
     */
    public T init(final InitListener<H> init) {
        final InitListener<H> oriInit = initListener;
        initListener = new InitListener<H>() {
            @Override
            public void init(H vh) {
                oriInit.init(vh);
                init.init(vh);
            }
        };
        return (T) this;
    }

    /***
     * 懒加载:View创建完成并展示动画播放完毕
     */
    public T lazy(final LazyListener<H> lazy) {
        final LazyListener<H> oriLazy = lazyListener;
        lazyListener = new LazyListener<H>() {
            @Override
            public void onLazy(H vh) {
                oriLazy.onLazy(vh);
                lazy.onLazy(vh);
            }
        };
        return (T) this;
    }

    /***
     * 页面预加载回调
     * @return
     */
    public T preLoad(final PreLoadListener<H> pre) {
        final PreLoadListener<H> oriPre = preLoadListener;
        preLoadListener = new PreLoadListener<H>() {
            @Override
            public void preLoad(H vh) {
                oriPre.preLoad(vh);
                pre.preLoad(vh);
            }
        };
        return (T) this;
    }

    /***
     * 添加生命周期回调
     * @param lifeListener
     * @return
     */
    public T addLifeListener(LifeListener<H> lifeListener) {
        lifeListeners.add(lifeListener);
        return (T) this;
    }

    /***
     * 获取页的根View
     * @return
     */
    public View getPageView() {
        return pageView;
    }

    /***
     * 获取填充ViewStub
     * @return
     */
    public ViewStub getPageRoot() {
        return pageRoot;
    }

    protected abstract H creatViewHolder(View pageView);

    public H getVH() {
        return vh;
    }

    public InitListener getInitListener() {
        return initListener;
    }

    public LazyListener getLazyListener() {
        return lazyListener;
    }

    public PreLoadListener getPreLoadListener() {
        return preLoadListener;
    }

    /***
     * 完成配置,返回代理
     * @return
     */
    public M cp() {
        return delegate;
    }

    /***
     * 自身实例化View, 非复用布局pageView会为null继而调用inflate,复用布局则会从ViewStub中通过通用ID获取实例的PageView
     */
    protected void bindInstanceView() {

        if (pageView != null) {
            vh = creatViewHolder(pageView);
            return;
        }

        //复用布局
        pageView = (View) pageRoot.getTag();

        if (pageView != null) {
            vh = creatViewHolder(pageView);
            return;
        }
        pageView = pageRoot.inflate();
        pageRoot.setTag(pageView);

        vh = creatViewHolder(pageView);
    }

    protected int getContentLayout() {
        return contentLayout;
    }

    protected int getType() {
        return type;
    }

    protected boolean isInit() {
        return isInit;
    }

    public void setInit(boolean isInit) {
        this.isInit = isInit;
    }

    protected boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean isLazy) {
        this.isLazy = isLazy;
    }

    protected ViewStub bindViewStub(ViewGroup rootView) {
        return bindViewStub(rootView, -1);
    }

    private ViewStub bindViewStub(ViewGroup rootView, int index) {
        if (pageRoot == null) {
            pageRoot = new ViewStub(rootView.getContext());
            rootView.addView(pageRoot, index);
            pageRoot.setLayoutResource(contentLayout);
            pageRoot.setLayoutInflater(LayoutInflater.from(rootView.getContext()));
        }
        return pageRoot;
    }

    /***
     * 适用与复用布局
     * @param pageRoot
     */
    protected void copyPageRoot(ViewStub pageRoot) {
        this.pageRoot = pageRoot;
    }

    /***
     * 隐藏
     */
    void hide() {
        if (pageView != null)
            pageView.setVisibility(View.GONE);
    }

    /***
     * 展示
     */
    void show() {
        if (pageView != null)
            pageView.setVisibility(View.VISIBLE);
    }

    /**
     * 销毁BaseBuild对应View， 意味着重新创建
     */
    public void destory() {
        int index = rootView.indexOfChild(pageView);
        rootView.removeView(pageView);
        pageView = null;
        pageRoot = null;
        bindViewStub(rootView, index);
        isInit = false;
        isLazy = false;
        lifeListeners.clear();
        baseObserves.clear();
    }

    /***
     * 匹配是否可消费事件
     */
    public boolean matchEvent(BaseEvents baseEvents) {
        for (BaseObserve item : baseObserves) {
            if (item.match(baseEvents.typeToken)) {
                item.response(vh, baseEvents.event);
                return true;
            }
        }
        return false;
    }

    /***
     * 缓存一个事件
     * @param events
     */
    void cacheEvents(BaseEvents events) {
        eventQueue.add(events);
    }

    /***
     * 获取缓存的事件队列
     * @return
     */
    public List<BaseEvents> getEventQueue() {
        return eventQueue;
    }

}
