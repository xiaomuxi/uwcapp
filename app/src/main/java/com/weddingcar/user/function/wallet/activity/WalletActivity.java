package com.weddingcar.user.function.wallet.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.network.library.bean.mine.request.GetBalanceInfoRequest;
import com.network.library.bean.mine.response.GetBalanceInfoEntity;
import com.network.library.constant.HttpAction;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.tv_account_balance)
    TextView tv_account_balance;
    //提现
    @BindView(R.id.tv_account_cash)
    TextView tv_account_cash;

    UserInfo userInfo;
    NetworkController networkController;
    double allowedMoney;
    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);

        userInfo = SPController.getInstance().getUserInfo();
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft("钱包");
        setTopRightImage(R.drawable.btn_detail);
    }

    @Override
    protected void initView() {
        super.initView();
        networkController = new NetworkController();
        networkController.attachView(getBalanceInfoView);

        tv_account_balance.setText("--");
        mTopRight.setOnClickListener(this);
        tv_account_cash.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        GetBalanceInfoRequest request = new GetBalanceInfoRequest();
        GetBalanceInfoRequest.Query query = new GetBalanceInfoRequest.Query();
        query.setApiId("HC010101");
        query.setDEVICEID(userInfo.getDeviceId());
        query.setUserid(userInfo.getUserId());
        query.setId(userInfo.getUserId());
        request.setQuery(query);

        networkController.sendRequest(HttpAction.ACTION_GET_BALANCE_INFO, request);
    }

    private NormalView<GetBalanceInfoEntity> getBalanceInfoView = new NormalView<GetBalanceInfoEntity>() {
        @Override
        public void onSuccess(GetBalanceInfoEntity entity) {
            GetBalanceInfoEntity.Data data = entity.getData().get(0);

            allowedMoney = data.getIntegration();
            String accountBalance = StringUtils.isEmpty(data.getIntegration() + "") ? "--" : data.getIntegration() + "";
            String bondBalance = StringUtils.isEmpty(data.getCashDeposit() + "") ? "--" : data.getCashDeposit() + "";
            tv_account_balance.setText(accountBalance);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right:
                //账户余额明细
                goToBalanceDetailActivity();
                break;
            case R.id.tv_account_cash:
                //账户余额提现
                if (allowedMoney <= 0) {
                    UIUtils.showToastSafe("没有可提现的余额");
                    return;
                }
                goToDrawCashActivity();
                break;
        }
    }


    private void goToBalanceDetailActivity() {
        Intent intent = new Intent(this, BalanceDetailActivity.class);
        startActivity(intent);
    }

    private void goToDrawCashActivity() {
        Intent intent = new Intent(this, DrawCashActivity.class);
        intent.putExtra("ALLOWED_MONEY", allowedMoney);
        startActivity(intent);
    }
}
