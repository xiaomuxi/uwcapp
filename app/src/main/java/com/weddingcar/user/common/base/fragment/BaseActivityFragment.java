package com.weddingcar.user.common.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weddingcar.user.common.utils.LogUtils;
import com.weddingcar.user.common.utils.ViewUtils;


/**
 * A placeholder fragment containing a simple view.
 */
public abstract class BaseActivityFragment extends Fragment {

    public String TAG = getClass().getSimpleName();
    public Activity mContext;
    public View mView;
    public boolean mIsNewView = true;
    public boolean mIsVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = onCreateCacheView(inflater, container, savedInstanceState);
            mIsNewView = true;
        } else {
            mIsNewView = false;
            ViewUtils.removeSelfFromParent(mView);
        }
        return mView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i(TAG, "fragment on view created");
        if (mIsNewView) {
            onCacheViewCreated(view, savedInstanceState);
            initView(mView);
            initCreated(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "fragment on start");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "fragment on resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(TAG, "fragment on pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.i(TAG, "fragment on stop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "fragment on destroy");
    }

    /**
     * 创建可以被缓存的view；
     *
     * @param inflater 加载
     * @param container ViewGroup
     * @param savedInstanceState view
     * @return  view
     */
    public View onCreateCacheView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG, "fragment on create cache view");
        return setContentView();
    }

    /**
     * 当cacheView被创建
     *
     * @param view view
     * @param savedInstanceState view
     */
    public void onCacheViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    protected abstract View setContentView();

    protected abstract void init();

    protected abstract void initView(View view);

    protected abstract void initCreated(Bundle savedInstanceState);

    public void createPresent() {
    }

    /**
     * 该方法在fragment create之前调用 , 在和ViewPager使用才会调用
     * @param isVisibleToUser 可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.i(TAG, "set user visible : " + String.valueOf(isVisibleToUser));
        if(isVisibleToUser){
            mIsVisible = true;
            onVisible();
        }else {
            mIsVisible = false;
            onInVisible();
        }
    }

    protected void onVisible(){
    }

    protected void onInVisible(){
    }
}
