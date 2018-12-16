package com.weddingcar.user.function.order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.network.library.bean.order.request.OrderListRequest;
import com.network.library.bean.order.response.OrderListEntity;
import com.network.library.constant.HttpAction;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.fragment.BaseLoadingFragment;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.ui.LoadMoreListView;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;
import com.weddingcar.user.function.order.activity.CallingOrderInfoActivity;
import com.weddingcar.user.function.order.activity.PayActivity;
import com.weddingcar.user.function.order.adapter.CallingListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by inrokei on 2018/5/1.
 */

public class CallingListFragment extends BaseLoadingFragment implements LoadMoreListView.OnRefreshListener{

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.list_view)
    LoadMoreListView list_view;
    private List<OrderListEntity.Data> dataList = new ArrayList<>();
    private CallingListAdapter adapter;

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean hasMore = true;

    UserInfo userInfo;
    NetworkController networkController;
    @Override
    protected View setContentView() {
        return UIUtils.inflate(mContext, R.layout.fragment_calling_list);
    }

    @Override
    protected void init() {
        userInfo = SPController.getInstance().getUserInfo();
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initCreated(Bundle savedInstanceState) {
        initRefreshLayout();

        networkController = new NetworkController();
        networkController.attachView(getOrderListView);
        adapter = new CallingListAdapter(mContext);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (StringUtils.equals(dataList.get(position).getState(), "待支付")) {
                    Intent intent = new Intent(mContext, PayActivity.class);
                    intent.putExtra("ORDER_ID", dataList.get(position).getCode());
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(mContext, CallingOrderInfoActivity.class);
                intent.putExtra("DATA", dataList.get(position));
                startActivity(intent);
            }
        });
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 1;
        hasMore = true;
        initData();
    }

    /**
     * 初始化刷新
     */
    private void initRefreshLayout() {
        list_view.setOnRefreshListener(this);
        //设置刷新的颜色
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.background_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark);
        //拖动多长的时候开始刷新
        swipeRefreshLayout.setDistanceToTriggerSync(100);

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(mContext, R.color.bg_white));

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

    public void initData() {
        OrderListRequest request = new OrderListRequest();
        OrderListRequest.Query query = new OrderListRequest.Query();
        query.setApiId("HC010302");
        query.setUserid(userInfo.getUserId());
        query.setDEVICEID(userInfo.getDeviceId());
        query.setId(userInfo.getUserId());
        query.setState("召集中");
        query.setPageIndex(pageNum+"");
        query.setPageSize(pageSize+"");
        request.setQuery(query);

        networkController.sendRequest(HttpAction.ACTION_GET_ORDER_LIST, request);
    }

    private NormalView<OrderListEntity> getOrderListView = new NormalView<OrderListEntity>() {
        @Override
        public void onSuccess(OrderListEntity entity) {
            System.out.println("GET ORDER LIST VIEW ---->");
            System.out.println(entity.getData().toString());
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            if (pageNum == 1) {
                dataList = entity.getData();
                show(check(dataList));
            }
            else {
                dataList.addAll(entity.getData());
                list_view.loadMoreComplete();
            }

            if (entity.getData().size() < pageSize) {
                hasMore = false;
            }

            adapter.setData(dataList);
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
            show(check(dataList));
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

    @Override
    protected View createLoadedView() {
        return setContentView();
    }
    @Override
    protected boolean isNeedLoadEveryTime() {
        return true;
    }
    @Override
    protected void load() {
        initData();
    }

    @Override
    public void onLoadingMore() {
        if (hasMore){
            pageNum++;
            initData();
        }else {
            list_view.loadMoreComplete();
        }
    }
}
