package com.weddingcar.user.function.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.network.library.bean.main.request.CarGroupListRequest;
import com.network.library.bean.main.response.CarGroupEntity;
import com.network.library.constant.HttpAction;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.fragment.BaseLoadingFragment;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;
import com.weddingcar.user.function.main.activity.ChooseCarActivity;
import com.weddingcar.user.function.main.activity.ConfirmOrderActivity;
import com.weddingcar.user.function.main.adapter.CarGroupAdapter;
import com.weddingcar.user.function.main.bean.CarUsageBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by inrokei on 2018/5/1.
 */

public class GroupListFragment extends BaseLoadingFragment {

    private ListView listView;
    private List<CarGroupEntity.Data> dataList = new ArrayList<>();
    CarGroupAdapter carGroupAdapter;
    NetworkController networkController;

    UserInfo userInfo;
    @Override
    protected View setContentView() {
        return UIUtils.inflate(mContext, R.layout.fragment_group_list);
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initView(View view) {
        listView = (ListView) view.findViewById(R.id.list_view);
    }

    @Override
    protected void initCreated(Bundle savedInstanceState) {
        userInfo = SPController.getInstance().getUserInfo();
        carGroupAdapter = new CarGroupAdapter(mContext);
        listView.setAdapter(carGroupAdapter);
        networkController = new NetworkController();
        networkController.attachView(getCarGroupView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarUsageBean mainCar = new CarUsageBean();
                mainCar.setModelKey(dataList.get(position).getCarHeadID());
                mainCar.setModelValue(dataList.get(position).getCarHeadName());
                mainCar.setCarType("main");

                CarUsageBean followCar = new CarUsageBean();
                followCar.setModelKey(dataList.get(position).getCarFollowID());
                followCar.setModelValue(dataList.get(position).getCarFollowName());
                followCar.setCarType("vice");

                Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                intent.putExtra("Date", ((ChooseCarActivity) mContext).getDate());
                intent.putExtra("TYPE", "Group");
                intent.putExtra("CAR_MAIN", mainCar);
                intent.putExtra("CAR_MAIN_COUNT", dataList.get(position).getCarHeadCount());
                intent.putExtra("CAR_VICE", followCar);
                intent.putExtra("CAR_VICE_COUNT", dataList.get(position).getCarFollowCount());
                intent.putExtra("test", new ArrayList<>());

                mContext.startActivity(intent);
            }
        });

        initData();
    }

    public void initData() {
        CarGroupListRequest request = new CarGroupListRequest();
        CarGroupListRequest.Query query = new CarGroupListRequest.Query();
        query.setApiId("HC010204");
        request.setQuery(query);

        networkController.sendRequest(HttpAction.ACTION_GET_CAR_GROUP_LIST, request);
    }


    private NormalView<CarGroupEntity> getCarGroupView = new NormalView<CarGroupEntity>() {
        @Override
        public void onSuccess(CarGroupEntity entity) {
            System.out.println("GET car group --->");
            System.out.println(entity.getData().toString());
            dataList = entity.getData();
            carGroupAdapter.setData(dataList);
            show(check(dataList));
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
}
