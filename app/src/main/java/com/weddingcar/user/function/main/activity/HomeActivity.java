package com.weddingcar.user.function.main.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements View.OnClickListener, AMapLocationListener {

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

    @BindView(R.id.tv_today)
    TextView tv_today;
    @BindView(R.id.tv_complete)
    TextView tv_complete;
    @BindView(R.id.tv_ongoing)
    TextView tv_ongoing;
    @BindView(R.id.tv_housekeeper_inner)
    TextView tv_housekeeper_inner;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.mv_map)
    MapView mv_map;
    @BindView(R.id.ll_wedding)
    LinearLayout ll_wedding;

    LinearLayout mStatusBarView;
    UserInfo userInfo;
    NetworkController networkController;
    private AMap mAMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mv_map.onCreate(savedInstanceState);
    }

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
        initMap();

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
        tv_location.setOnClickListener(this);
        ll_wedding.setOnClickListener(this);

        networkController = new NetworkController();
        networkController.attachView(getBalanceInfoView);
        initData();
    }

    private void initMap() {
        if (mAMap == null) {
            mAMap = mv_map.getMap();
//            mAMap.setOnMarkerClickListener(this);
        }
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(8000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car));
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        mAMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        mAMap.getUiSettings().setZoomControlsEnabled(false);
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
    protected void onResume() {
        super.onResume();
        mv_map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv_map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mv_map.onSaveInstanceState(outState);
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
    public void onLocationChanged(AMapLocation amapLocation) {
        System.out.println("YIN---->onLocationChanged");
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                System.out.println("YIN---->success");
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
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
            case R.id.tv_location:
                break;
            case R.id.ll_wedding:
                UIUtils.showToastSafe("日期");
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mv_map.onDestroy();
    }
}
