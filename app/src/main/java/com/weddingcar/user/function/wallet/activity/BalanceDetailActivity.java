package com.weddingcar.user.function.wallet.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.network.library.bean.mine.request.GetBalanceDetailListRequest;
import com.network.library.bean.mine.response.BalanceDetailEntity;
import com.network.library.constant.HttpAction;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.ui.LoadMoreListView;
import com.weddingcar.user.common.utils.LogUtils;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;
import com.weddingcar.user.function.wallet.adapter.BalanceListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalanceDetailActivity extends BaseActivity implements LoadMoreListView.OnRefreshListener {

    @BindView(R.id.lv_balance)
    LoadMoreListView lv_balance;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_empty)
    TextView tv_empty;

    NetworkController networkController;
    BalanceListAdapter balanceListAdapter;
    UserInfo userInfo;
    private int pageNum = 1;
    private int pageSize = 15;
    private boolean hasMore = true;

    private List<BalanceDetailEntity.Data> mDataList;
    @Override
    protected void init() {
        super.init();
        userInfo = SPController.getInstance().getUserInfo();
        setContentView(R.layout.activity_balance_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft("余额明细");
    }

    @Override
    protected void initView() {
        super.initView();
        mDataList = new ArrayList<>();
        networkController = new NetworkController();
        networkController.attachView(getBalanceDetailList);

        balanceListAdapter = new BalanceListAdapter(this);
        lv_balance.setAdapter(balanceListAdapter);

        lv_balance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToIncomeDetailActivity(mDataList.get(position));
            }
        });

        initData();
        initRefreshLayout();
    }

    private void goToIncomeDetailActivity(BalanceDetailEntity.Data data) {
        Intent intent = new Intent(this, IncomeDetailActivity.class);
        intent.putExtra("DATA", data);
        startActivity(intent);
    }

    private void initRefreshLayout() {
        lv_balance.setOnRefreshListener(this);
        //设置刷新的颜色
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.background_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark);
        //拖动多长的时候开始刷新
        swipeRefreshLayout.setDistanceToTriggerSync(100);

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.bg_white));

        //刷新监听器
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                hasMore = true;
                initData();
            }

        });
    }

    private void checkData() {
        tv_empty.setVisibility(mDataList.size() == 0 ? View.VISIBLE : View.GONE);
        lv_balance.setVisibility(mDataList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    private void initData() {
        GetBalanceDetailListRequest request = new GetBalanceDetailListRequest();
        GetBalanceDetailListRequest.Query query = new GetBalanceDetailListRequest.Query();
        query.setApiId("HC0206201");
        query.setDEVICEID(userInfo.getDeviceId());
        query.setUserid(userInfo.getUserId());
        query.setCustomerId(userInfo.getUserId());
        query.setPageIndex(pageNum);
        query.setPageSize(pageSize);
        request.setQuery(query);

        networkController.sendRequest(HttpAction.ACTION_GET_BALANCE_DETAIL_LIST, request);
    }

    private NormalView<BalanceDetailEntity> getBalanceDetailList = new NormalView<BalanceDetailEntity>() {
        @Override
        public void onSuccess(BalanceDetailEntity entity) {
            LogUtils.i(TAG, entity.toString());
            hasMore = entity.getData().size() == pageSize;
            if (1 == pageNum) {
                mDataList = entity.getData();
            } else {
                lv_balance.loadMoreComplete();
                mDataList.addAll(entity.getData());
            }
            if (swipeRefreshLayout.isRefreshing()) {
                //完成后调用完成的方法
                swipeRefreshLayout.setRefreshing(false);
            }
            balanceListAdapter.setData(mDataList);
            checkData();
        }

        @Override
        public void showLoading() {

        }

        @Override
        public void hideLoading() {

        }

        @Override
        public void onRequestSuccess() {

        }

        @Override
        public void onRequestError(String errorMsg, String methodName) {
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

    @Override
    public void onLoadingMore() {
        if (hasMore) {
            pageNum++;
            initData();
        } else {
            lv_balance.loadMoreComplete();
        }
    }
}

