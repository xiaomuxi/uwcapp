package com.weddingcar.user.common.ui;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.weddingcar.user.R;
import com.weddingcar.user.common.manager.ThreadManager;
import com.weddingcar.user.common.utils.LogUtils;
import com.weddingcar.user.common.utils.UIUtils;

public abstract class LoadingLayout extends FrameLayout {
    private static final int STATE_UNLOADED = 0;//未知状态
    private static final int STATE_LOADING = 1;//加载状态
    private static final int STATE_ERROR = 3;//加载完毕，但是出错状态
    private static final int STATE_EMPTY = 4;//加载完毕，但是没有数据状态
    private static final int STATE_SUCCEED = 5;//加载成功
    private static final String TAG = LoadingLayout.class.getSimpleName();

    private View mLoadingView;//加载时显示的View
    private View mErrorView;//加载出错显示的View
    private View mEmptyView;//加载没有数据显示的View
    private View mSucceedView;//加载成功显示的View

    private int mState;//当前的状态，显示需要根据该状态判断

    public LoadingLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        mState = STATE_UNLOADED;//初始化状态

        //创建对应的View，并添加到布局中
        mLoadingView = createLoadingView();
        if (mLoadingView != null) {
            addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        mErrorView = createErrorView();
        if (mErrorView != null) {
            addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        mEmptyView = createEmptyView();
        if (mEmptyView != null) {
            addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        mSucceedView = createLoadedView();
        if (mSucceedView != null) {
            addView(mSucceedView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        //显示对应的View
        showPageSafe();
    }

    /**
     * 线程安全的方法
     */
    private void showPageSafe() {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    /**
     * 显示对应的View
     */
    private void showPage() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(mState == STATE_UNLOADED || mState == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }
        if (mSucceedView != null) {
            mSucceedView.setVisibility(mState == STATE_SUCCEED ? View.VISIBLE : View.INVISIBLE);
            if (mState == STATE_SUCCEED) {
                loadViewSuccess();
            }
        }
    }

    /**
     * 恢复状态
     */
    public void reset() {
        mState = STATE_UNLOADED;
        showPageSafe();
    }

    /**
     * 是否需要恢复加载状态
     */
    public boolean needReset() {
        if (isNeedLoadEveryTime()) {
            return mState == STATE_ERROR || mState == STATE_EMPTY || mState == STATE_SUCCEED;
        } else {
            return false;
        }
    }


    /**
     * 有外部调用，会根据状态判断是否引发load
     */
    public synchronized void show() {
        LogUtils.i(TAG, "LoadingLayout show");
        if (needReset()) {
            mState = STATE_UNLOADED;
        }
        if (mState == STATE_UNLOADED) {
            mState = STATE_LOADING;
            LoadingTask task = new LoadingTask();
            ThreadManager.getLongPool().execute(task);
        }
        showPageSafe();
    }


    public synchronized void show(LoadResult result) {
        mState = result.getValue();
        showPageSafe();
        needReset();
    }

    protected View createLoadingView() {
        return UIUtils.inflate(R.layout.loading_page_loading);
    }

    protected View createEmptyView() {
        View view = UIUtils.inflate(R.layout.loading_page_empty);
        View bt_reload = view.findViewById(R.id.tv_reload);
        if (bt_reload != null) {
            bt_reload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    needReset();
                    show();
                }
            });
        }
        return view;
    }

    protected View createErrorView() {
        View view = UIUtils.inflate(R.layout.loading_page_error);
        View tv_reload = view.findViewById(R.id.tv_reload);
        if (tv_reload != null) {
            tv_reload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    needReset();
                    show();
                }
            });
        }
        return view;
    }

    public abstract View createLoadedView();

    public abstract void loadViewSuccess();

    protected abstract boolean isNeedLoadEveryTime();

    public abstract void load();
//    public abstract void reload();

    class LoadingTask implements Runnable {
        @Override
        public void run() {
            mState = STATE_LOADING;
            load();
            UIUtils.runInMainThread(new Runnable() {//主线程改变UI
                        @Override
                        public void run() {
                            //状态的改变和界面息息相关，所以需要放到主线程来赋值，保障同步性
                            mState = getState();//根据枚举对象的返回值来改变显示状态码
                            showPage();
                }
            });
        }
    }

    public enum LoadResult {
        UNLOAD(0), LOADING(1), ERROR(3), EMPTY(4), SUCCEED(5);
        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static LoadResult getResult(int value) {
            LoadResult[] values = LoadResult.values();
            for (LoadResult result : values) {
                if (value == result.getValue()) {
                    return result;
                }
            }
            return UNLOAD;
        }
    }

    public int getState() {
        return mState;
    }

    public void setState(int mState) {
        this.mState = mState;
    }
}
