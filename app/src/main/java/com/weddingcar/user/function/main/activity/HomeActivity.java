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

import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.utils.LogUtils;
import com.weddingcar.user.common.utils.StatusBarUtils;
import com.weddingcar.user.common.utils.UIUtils;
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

    LinearLayout mStatusBarView;

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
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
                UIUtils.showToastSafe("管家");
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
        }

    }
}
