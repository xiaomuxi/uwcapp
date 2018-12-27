package com.weddingcar.user.function.main.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.EMClient;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.network.library.bean.main.request.NearCarListRequest;
import com.network.library.bean.main.response.CarListEntity;
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
import com.weddingcar.user.function.order.activity.OrderActivity;
import com.weddingcar.user.function.user.activity.MineInfoActivity;
import com.weddingcar.user.function.wallet.activity.WalletActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    @BindView(R.id.tv_wedding_time)
    TextView tv_wedding_time;
    @BindView(R.id.tv_self)
    TextView tv_self;
    @BindView(R.id.tv_group)
    TextView tv_group;

    LinearLayout mStatusBarView;
    UserInfo userInfo;
    NetworkController networkController;
    NetworkController carListController;
    private AMap mAMap;
    TimePickerView pvTime;
    public AMapLocationClient mLocationClient;
    public AMapLocationClientOption mLocationOption = null;
    String currentTime;

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
        initLocation();
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
        mTopTitle.setText("--");
        mTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MessageActivity.class));
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
        tv_today.setOnClickListener(this);
        tv_complete.setOnClickListener(this);
        tv_ongoing.setOnClickListener(this);
        tv_housekeeper_inner.setOnClickListener(this);
        tv_self.setOnClickListener(this);
        tv_group.setOnClickListener(this);

        networkController = new NetworkController();
        carListController = new NetworkController();
        networkController.attachView(getBalanceInfoView);
        carListController.attachView(getCarListView);
        initData();
        initTimePicker();
        initNearCarList();
    }

    private void initNearCarList() {
        NearCarListRequest request = new NearCarListRequest();
        NearCarListRequest.Query query = new NearCarListRequest.Query();
        query.setApiId("HC040004");
        request.setQuery(query);

        carListController.sendRequest(HttpAction.ACTION_GET_NEAR_CAR_LIST, request);
    }

    private void initTimePicker() {
        Calendar startDate = Calendar.getInstance();
        currentTime = getTime(startDate.getTime());
        tv_wedding_time.setText(mContext.getResources().getString(R.string.text_wedding_date, currentTime));
        pvTime = new TimePickerBuilder(HomeActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                currentTime = getTime(date);
                tv_wedding_time.setText(mContext.getResources().getString(R.string.text_wedding_date, currentTime));
            }
        }).setRangDate(startDate, null)
                .setSubmitColor(getResources().getColor(R.color.text_main_red))
                .setCancelColor(getResources().getColor(R.color.text_main_red))
                .build();
    }

    private void initMap() {
        if (mAMap == null) {
            mAMap = mv_map.getMap();
        }
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(8000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car));
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        mAMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        mAMap.getUiSettings().setZoomControlsEnabled(false);
    }


    private void initLocation()  {
        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Batte
        // ry_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        //设置单次定位，3s内最精准的位置
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
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

    private NormalView<CarListEntity> getCarListView = new NormalView<CarListEntity>() {
        @Override
        public void onSuccess(CarListEntity entity) {
            List<CarListEntity.Data> dataList = entity.getData();
            System.out.println("getCarListView----->");
            System.out.println(dataList.toString());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_car);
            LatLngBounds.Builder bounds = LatLngBounds.builder();
            for (int i = 0; i < dataList.size(); i ++) {
                LatLng latLng = new LatLng(Double.parseDouble(dataList.get(i).getLatitude()), Double.parseDouble(dataList.get(i).getLongitude()));
                mAMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                bounds.include(latLng);
            }
            mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50));
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
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                System.out.println("location city ----->" + amapLocation.getCity());
                mTopTitle.setText(amapLocation.getCity());

            } else {
                UIUtils.showToastSafe("定位信息获取失败");
                mTopTitle.setText("--");
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    private void openChating(){
        if (ChatClient.getInstance().isLoggedInBefore()){
            //已经登陆，直接进入会话
            Intent chatIntent = new IntentBuilder(mContext)
                    .setServiceIMNumber("kefu001")
                    .build();
            startActivity(chatIntent);

        }else {
            System.out.println("正在登陆---》");
            UserInfo userInfo = SPController.getInstance().getUserInfo();
            EMClient.getInstance().login(userInfo.getUserId(), Config.HX_USER_PASSWORD, new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    //已经登陆，直接进入会话
                    Intent chatIntent = new IntentBuilder(mContext)
                            .setServiceIMNumber("kefu001")
                            .build();
                    startActivity(chatIntent);
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    UIUtils.showToastSafe("连接失败");
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        drawer.closeDrawer(GravityCompat.START);
        switch (v.getId()) {
            case R.id.tv_order:
                startActivity(new Intent(this, OrderActivity.class));
                break;
            case R.id.tv_wallet:
                startActivity(new Intent(this, WalletActivity.class));
                break;
            case R.id.tv_housekeeper_inner:
                openChating();
                break;
            case R.id.tv_housekeeper:
                openChating();
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
                initMap();
                initLocation();
                break;
            case R.id.ll_wedding:
                pvTime.show();
                break;
            case R.id.tv_today:
                Intent intent1 = new Intent(this, OrderActivity.class);
                intent1.putExtra("currentIndex", 2);
                startActivity(intent1);
                break;
            case R.id.tv_complete:
                Intent intent2 = new Intent(this, OrderActivity.class);
                intent2.putExtra("currentIndex", 1);
                startActivity(intent2);
                break;
            case R.id.tv_ongoing:
                Intent intent3 = new Intent(this, OrderActivity.class);
                intent3.putExtra("currentIndex", 0);
                startActivity(intent3);
                break;
            case R.id.tv_self:
                Intent intent4 = new Intent(this, ChooseCarActivity.class);
                intent4.putExtra("time", currentTime);
                intent4.putExtra("index", 0);
                startActivity(intent4);
                break;
            case R.id.tv_group:
                Intent intent5 = new Intent(this, ChooseCarActivity.class);
                intent5.putExtra("time", currentTime);
                intent5.putExtra("index", 1);
                startActivity(intent5);
                break;
        }
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mv_map.onDestroy();
    }
}
