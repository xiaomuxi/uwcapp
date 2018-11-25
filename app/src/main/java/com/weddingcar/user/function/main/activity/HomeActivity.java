package com.weddingcar.user.function.main.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.network.library.bean.mine.request.GetBalanceInfoRequest;
import com.network.library.bean.mine.response.GetBalanceInfoEntity;
import com.network.library.constant.HttpAction;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.config.Config;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.ui.CircleImageView;
import com.weddingcar.user.common.utils.LogUtils;
import com.weddingcar.user.common.utils.StatusBarUtils;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;
import com.weddingcar.user.function.housekeeper.activity.HousekeeperActivity;
import com.weddingcar.user.function.user.activity.MineInfoActivity;
import com.weddingcar.user.function.wallet.activity.WalletActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.right)
    LinearLayout mTopRight;
    @BindView(R.id.iv_right)
    ImageView mTopRightImage;
    @BindView(R.id.tv_title)
    TextView mTopTitle;
    @BindView(R.id.left)
    LinearLayout mTopLeft;
    @BindView(R.id.iv_left)
    ImageView mTopLeftImage;
    @BindView(R.id.tv_order)
    TextView tv_order;
    @BindView(R.id.tv_wallet)
    TextView tv_wallet;
    @BindView(R.id.tv_housekeeper)
    TextView tv_housekeeper;
    @BindView(R.id.tv_setting)
    TextView tv_setting;
    @BindView(R.id.tv_apply_account)
    TextView tv_apply_account;
    @BindView(R.id.tv_recruit)
    TextView tv_recruit;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_header)
    CircleImageView iv_header;
    @BindView(R.id.ll_header)
    LinearLayout ll_header;

    LinearLayout mStatusBarView;
    UserInfo userInfo;
    NetworkController networkController;
    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userInfo = SPController.getInstance().getUserInfo();
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
    }

    @Override
    protected void initView() {
        super.initView();
        initStatusBar();
        mTopLeft.setVisibility(View.VISIBLE);
        mTopRight.setVisibility(View.VISIBLE);
        mTopRightImage.setVisibility(View.VISIBLE);
        mTopLeftImage.setVisibility(View.VISIBLE);
        mTopRightImage.setImageResource(R.drawable.navibar_icon_inf);
        mTopLeftImage.setImageResource(R.drawable.navibar_icon_my);
        tv_phone.setText(userInfo.getUserId());
        tv_name.setText(userInfo.getName());
        RequestOptions options = new RequestOptions().placeholder(R.drawable.my_head);
        Glide.with(mContext).load(Config.getUserAvatorBaseUrl() + SPController.getInstance().getString(SPController.USER_INFO_AVATAR, "")).apply(options).into(iv_header);
        mTopTitle.setText("上海市");
        mTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToastDebug("消息");
            }
        });
        mTopLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        tv_order.setOnClickListener(this);
        tv_wallet.setOnClickListener(this);
        tv_housekeeper.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        tv_apply_account.setOnClickListener(this);
        tv_recruit.setOnClickListener(this);
        ll_header.setOnClickListener(this);

        networkController = new NetworkController();
        networkController.attachView(getBalanceInfoView);
        initData();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void initStatusBar() {
        LogUtils.i(TAG, "init status bar");
        int mStatusHeight = UIUtils.getStatusBarHeight(mContext);

        mStatusBarView = new LinearLayout(mContext);
        mStatusBarView.setBackground(ContextCompat.getDrawable(mContext, R.color.bg_white));
        mStatusBarView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mStatusHeight));
        StatusBarUtils.StatusBarLightMode(this);
        mActionBar.addView(mStatusBarView, 0);
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
            String name = StringUtils.isEmpty(data.getName() + "") ? "--" : data.getName() + "";
            tv_name.setText(name);

            RequestOptions options = new RequestOptions().placeholder(R.drawable.my_head);
            Glide.with(mContext).load(Config.getUserAvatorBaseUrl() + data.getAvator()).apply(options).into(iv_header);

            UserInfo newUserInfo = new UserInfo();
            newUserInfo.setUserId(userInfo.getUserId());
            newUserInfo.setSex(data.getSex());
            newUserInfo.setName(data.getName());
            SPController.getInstance().saveUserInfo(newUserInfo);
            SPController.getInstance().putString(SPController.USER_INFO_AVATAR, data.getAvator());
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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        drawer.closeDrawer(GravityCompat.START);
        switch (v.getId()) {
            case R.id.tv_order:
                UIUtils.showToastSafe("订单");
                break;
            case R.id.tv_wallet:
                startActivity(new Intent(this, WalletActivity.class));
                break;
            case R.id.tv_housekeeper:
                startActivity(new Intent(this, HousekeeperActivity.class));
                break;
            case R.id.tv_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.tv_apply_account:
                startActivity(new Intent(this, EnterpriseAccountActivity.class));
                break;
            case R.id.tv_recruit:
                UIUtils.showToastSafe("车主招募");
                break;
            case R.id.ll_header:
                startActivity(new Intent(this, MineInfoActivity.class));
                break;
        }

    }
}
