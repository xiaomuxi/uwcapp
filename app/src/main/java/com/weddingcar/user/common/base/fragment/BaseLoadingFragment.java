package com.weddingcar.user.common.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weddingcar.user.common.ui.LoadingLayout;
import com.weddingcar.user.common.utils.LogUtils;
import com.weddingcar.user.common.utils.UIUtils;
import com.weddingcar.user.common.utils.ViewUtils;

import java.util.List;

/**
 * Created by Riky luwei on 2016/8/12.
 */
public abstract class BaseLoadingFragment extends BaseActivityFragment {
    public LoadingLayout mContentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        //每次ViewPager要展示该页面时，均会调用该方法获取显示的View
        LogUtils.i(TAG, " fragment on create view");
        if (mContentView == null) {//为null时，创建一个
            // 每次ViewPager要展示该页面时，均会调用该方法获取显示的View
            LogUtils.i(TAG, " fragment on create loading layout");
            mContext = getActivity();
            init();
            mContentView = new LoadingLayout(UIUtils.getContext()) {
                @Override
                public void load() {
                    LogUtils.i(TAG, "load");
                    BaseLoadingFragment.this.load();
                }

//                @Override
//                public void reload() {
//                    BaseLoadingFragment.this.reload();
//                }

                @Override
                public View createLoadedView() {
                    LogUtils.i(TAG, "loading view create");
                    View loadedView = BaseLoadingFragment.this.createLoadedView();
                    initView(loadedView);
                    initCreated(savedInstanceState);
                    return loadedView;
                }

                @Override
                public void loadViewSuccess() {
                    BaseLoadingFragment.this.loadViewSuccess();
                }

                @Override
                protected boolean isNeedLoadEveryTime() {
                    return BaseLoadingFragment.this.isNeedLoadEveryTime();
                }
            };
        } else {
            // 不为null时，需要把自身从父布局中移除，因为ViewPager会再次添加
            ViewUtils.removeSelfFromParent(mContentView);
        }
        return mContentView;
    }



    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG, "fragment on view created");
        if (mIsVisible) {
            // 只有可见的的时候加载
            show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /** 当显示的时候，加载该页面 */
    public void show() {
        if (mContentView != null) {
            LogUtils.i(TAG, "fragment show");
            mContentView.show();
        }
    }
    public void show(LoadingLayout.LoadResult result) {
        if (mContentView != null) {
            LogUtils.i(TAG, "fragment show");
            mContentView.show(result);
        }
    }

    public LoadingLayout.LoadResult check(Object obj) {
        if (obj == null) {
            return LoadingLayout.LoadResult.ERROR;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            if (list.size() == 0) {
                return LoadingLayout.LoadResult.EMPTY;
            }
        }
        return LoadingLayout.LoadResult.SUCCEED;
    }

    // 加载完成的View
    protected abstract View createLoadedView();

    // 加载成功页面完成
    protected void loadViewSuccess() {
        LogUtils.i(TAG, "load view success");
    }
    // 加载数据
    protected abstract void load();

    // 设置是否需要每次加载
    protected boolean isNeedLoadEveryTime() {
        return false;
    }
//    /**
//     * 点击重新加载的方法
//     */
//    public void reload() {
//
//    }

    public void setLoadingState(LoadingLayout.LoadResult result) {
        mContentView.setState(result.getValue());
    }

    public LoadingLayout.LoadResult getLoadingState() {
        int state = mContentView.getState();
        return LoadingLayout.LoadResult.getResult(state);
    }
}
