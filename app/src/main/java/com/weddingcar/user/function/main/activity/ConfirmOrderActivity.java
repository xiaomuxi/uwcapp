package com.weddingcar.user.function.main.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DistanceItem;
import com.amap.api.services.route.DistanceResult;
import com.amap.api.services.route.DistanceSearch;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.network.library.bean.BaseEntity;
import com.network.library.bean.main.request.ConfirmOrderRequest;
import com.network.library.bean.main.request.InsertMapRequest;
import com.network.library.bean.main.request.SendOrderRequest;
import com.network.library.bean.main.response.SendOrderEntity;
import com.network.library.constant.HttpAction;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.map.LocationBean;
import com.weddingcar.user.common.utils.CheckUtils;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;
import com.weddingcar.user.function.main.bean.CarUsageBean;
import com.weddingcar.user.function.order.activity.OrderActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener, DistanceSearch.OnDistanceSearchListener{
    public static int REQ_CODE_ADDRESS_GROOM = 1;
    public static int REQ_CODE_ADDRESS_BRIDE = 2;
    public static int REQ_CODE_ADDRESS_COMPLETE = 3;
    public static int REQ_CODE_CAR_BRAND = 4;

    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.ll_groom)
    LinearLayout ll_groom;
    @BindView(R.id.tv_groom_address)
    TextView tv_groom_address;
    @BindView(R.id.ll_bride)
    LinearLayout ll_bride;
    @BindView(R.id.tv_bride_address)
    TextView tv_bride_address;
    @BindView(R.id.ll_complete)
    LinearLayout ll_complete;
    @BindView(R.id.tv_complete_address)
    TextView tv_complete_address;
    @BindView(R.id.tv_mileage)
    TextView tv_mileage;
    @BindView(R.id.ll_car_list)
    LinearLayout ll_car_list;
    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.et_groom_name)
    EditText et_groom_name;
    @BindView(R.id.et_groom_phone)
    EditText et_groom_phone;
    @BindView(R.id.et_bride_name)
    EditText et_bride_name;
    @BindView(R.id.et_bride_phone)
    EditText et_bride_phone;
    @BindView(R.id.et_note)
    EditText et_note;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.mv_map)
    TextureMapView mv_map;

    TimePickerView pvTime;
    private AMap mAMap;
    private AMapLocationClient mLocationClient;
    String date;
    String type;
    DistanceSearch distanceSearch;
    LocationBean groomLocation;
    LocationBean brideLocation;
    LocationBean completeLocation;
    List<String> hourList;
    List<String> kmList;
    float distance = -1;
    Map<View, CarUsageBean> carMap = new HashMap<>();
    View currentView;
    UserInfo userInfo;

    NetworkController sendOrderController;
    NetworkController confirmOrderController;
    NetworkController insertMapController;
    List<CarUsageBean> carList = new ArrayList<>();
    int carOrderIndex = 0;
    int insertMapIndex = 0;
    List<String> orderList = new ArrayList<>();

    @Override
    protected void init() {
        super.init();
        userInfo = SPController.getInstance().getUserInfo();
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        date = getIntent().getStringExtra("Date");
        type = getIntent().getStringExtra("TYPE");
        initCarList();
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft("确认下单");
    }

    @Override
    protected void initView() {
        super.initView();
        distanceSearch = new DistanceSearch(this);

        tv_date.setText(date);
        tv_mileage.setText(getResources().getString(R.string.text_estimate_km, "0"));

        ll_groom.setOnClickListener(this);
        ll_bride.setOnClickListener(this);
        ll_complete.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        sendOrderController = new NetworkController();
        sendOrderController.attachView(sendOrderView);
        confirmOrderController = new NetworkController();
        confirmOrderController.attachView(confirmOrderView);
        insertMapController = new NetworkController();
        insertMapController.attachView(insertMapView);

        initMap();
        initLocation();
        initDatePicker();
        initHourKm();
    }

    private void initCarList() {
        if (StringUtils.equals(type, "Self")) {
            CarUsageBean bean = (CarUsageBean) getIntent().getSerializableExtra("CAR_SELF");
            addItemIntoCarList(bean);
        }
        int mainCount = getIntent().getIntExtra("CAR_MAIN_COUNT", 0);
        int viceCount = getIntent().getIntExtra("CAR_VICE_COUNT", 0);
        CarUsageBean mainCar = (CarUsageBean) getIntent().getSerializableExtra("CAR_MAIN");
        CarUsageBean viceCar = (CarUsageBean) getIntent().getSerializableExtra("CAR_VICE");
        for (int i = 0; i < mainCount; i ++) {
            addItemIntoCarList(mainCar);
        }
        for (int j = 0; j < viceCount; j ++) {
            addItemIntoCarList(viceCar);
        }

    }

    private void initMap() {
        if (mAMap == null) {
            mAMap = mv_map.getMap();
        }
        distanceSearch.setDistanceSearchListener(this);
        mAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent = new Intent(mContext, AddressActivity.class);
                intent.putExtra("GROOM_ADDRESS", groomLocation);
                intent.putExtra("BRIDE_ADDRESS", brideLocation);
                intent.putExtra("COMPLETE_ADDRESS", completeLocation);
                startActivity(intent);
            }
        });
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        UiSettings uiSettings = mAMap.getUiSettings();myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        uiSettings.setZoomControlsEnabled(false);  //隐藏缩放按钮
        uiSettings.setAllGesturesEnabled(false); //禁止所有手势
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
//        设置定位监听
//        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
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

    private void initDatePicker() {
        Calendar startDate = Calendar.getInstance();
        pvTime = new TimePickerBuilder(ConfirmOrderActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date time, View v) {
                date = getTime(time);
                tv_date.setText(date);
            }
        }).setRangDate(startDate, null)
                .setSubmitColor(getResources().getColor(R.color.text_main_red))
                .setCancelColor(getResources().getColor(R.color.text_main_red))
                .build();
    }


    private void initHourKm() {
        hourList = new ArrayList<>();
        kmList = new ArrayList<>();

        for (int i = 1; i < 101; i ++) {
            hourList.add(i+"");
            kmList.add(i*10+"");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mv_map.onCreate(savedInstanceState);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mv_map.onDestroy();
    }

    private void addItemIntoCarList(CarUsageBean carUsageBean) {
        View view = UIUtils.inflate(R.layout.item_car_usage);

        ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
        TextView tv_car_brand = (TextView) view.findViewById(R.id.tv_car_brand);
        TextView tv_car_main = (TextView) view.findViewById(R.id.tv_car_main);
        TextView tv_car_vice = (TextView) view.findViewById(R.id.tv_car_vice);
        LinearLayout ll_time_km = (LinearLayout) view.findViewById(R.id.ll_time_km);
        TextView tv_usage_time = (TextView) view.findViewById(R.id.tv_usage_time);
        TextView tv_usage_km = (TextView) view.findViewById(R.id.tv_usage_km);

        if (carUsageBean != null) {
            tv_car_brand.setText(StringUtils.checkString(carUsageBean.getBrandValue()) + StringUtils.checkString(carUsageBean.getModelValue()));
            tv_car_main.setSelected(StringUtils.equals(carUsageBean.getCarType(), "main"));
            tv_car_vice.setSelected(StringUtils.equals(carUsageBean.getCarType(), "vice"));
        }


        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carMap.size() == 1) {
                    return;
                }
                ll_car_list.removeView(view);
                carMap.remove(view);
            }
        });
        tv_car_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentView = view;
                Intent intent = new Intent(ConfirmOrderActivity.this, CarBrandActivity.class);
                startActivityForResult(intent, REQ_CODE_CAR_BRAND);
            }
        });
        tv_car_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_car_main.isSelected()) {
                    return;
                }
                tv_car_main.setSelected(true);
                tv_car_vice.setSelected(false);
                carMap.get(view).setCarType("main");
            }
        });
        tv_car_vice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_car_vice.isSelected()) {
                    return;
                }
                tv_car_vice.setSelected(true);
                tv_car_main.setSelected(false);
                carMap.get(view).setCarType("vice");
            }
        });
        ll_time_km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView<String> pvOptions  = new OptionsPickerBuilder(ConfirmOrderActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        tv_usage_time.setText(mContext.getResources().getString(R.string.text_car_hours, hourList.get(options1)));
                        tv_usage_km.setText(mContext.getResources().getString(R.string.text_car_km, kmList.get(options2)));
                        CarUsageBean bean = carMap.get(view);
                        bean.setHours(hourList.get(options1));
                        bean.setKm(kmList.get(options2));
                    }
                })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("选择时间及公里")//标题
                .setSubCalSize(16)//确定和取消文字大小
                .setTitleSize(14)//标题文字大小
                .setTitleColor(mContext.getResources().getColor(R.color.text_main_red))//标题文字颜色
                .setSubmitColor(mContext.getResources().getColor(R.color.text_main_red))//确定按钮文字颜色
                .setCancelColor(mContext.getResources().getColor(R.color.text_main_red))//取消按钮文字颜色
                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLabels("小时", "公里", null)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();
                pvOptions.setNPicker(hourList, kmList, null);
                pvOptions.show();
            }
        });

        if (carUsageBean == null) {
            carUsageBean =  new CarUsageBean();
        }
        ll_car_list.addView(view);
        carMap.put(view, carUsageBean);
    }

    private void goToAddressChooseActivity(int reqCode) {
        Intent intent = new Intent(this, AddressChooseActivity.class);
        startActivityForResult(intent, reqCode);
    }

    private void selectDate() {
        pvTime.show();
    }

    private void submit() {
        if (!checkInput()) {
            return;
        }

        for (Map.Entry<View, CarUsageBean> entry : carMap.entrySet()) {
            carList.add(entry.getValue());
        }
        showProcess("正在生成订单...");
        sendOrder(carList.get(0));
    }

    private void sendOrder(CarUsageBean bean) {
        SendOrderRequest request = new SendOrderRequest();
        SendOrderRequest.Query query = new SendOrderRequest.Query();
        query.setApiId("HC020301");
        query.setCustomerID(userInfo.getUserId());
        query.setUserid(userInfo.getUserId());
        query.setIscar(StringUtils.equals(bean.getCarType(), "main") ? "1" : "0");
        query.setAmountTotal("1");
        query.setAmountTotalReference("1");
        query.setCount("1");
        query.setCarColorID("1");
        query.setCarModelID(bean.getModelKey());
        request.setQuery(query);

        sendOrderController.sendRequest(HttpAction.ACTION_SEND_ORDER, request);
    }

    private void confirmOrder(CarUsageBean carUsageBean) {

        ConfirmOrderRequest request = new ConfirmOrderRequest();
        ConfirmOrderRequest.Query query = new ConfirmOrderRequest.Query();
        query.setApiId("HC020302");
        query.setIDS(carUsageBean.getOrderId());
        query.setUserid(userInfo.getUserId());
        query.setCustomerID(userInfo.getUserId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            Date dateTime = simpleDateFormat.parse(date);
            query.setTheWeddingDateString(dateTime.getTime()+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.setJourneyExpect(carUsageBean.getKm());
        query.setJourneyChoose(carUsageBean.getKm());
        query.setHourChoose(carUsageBean.getHours());
        query.setNameTheContact(et_bride_name.getText().toString().trim());
        query.setTelTheContact(et_bride_phone.getText().toString().trim());
        query.setNameTheContactEmergency(et_groom_name.getText().toString().trim());
        query.setTelTheContactEmergency(et_groom_phone.getText().toString().trim());
        query.setNote(et_note.getText().toString().trim());
        query.setAreaID("722");
        query.setAreaID(groomLocation.getAdCode());
        query.setRemark(groomLocation.getAdName());
        request.setQuery(query);

        confirmOrderController.sendRequest(HttpAction.ACTION_CONFIRM_ORDER, request);
    }

    private void insertMap(String orderId, LocationBean locationBean, String no) {
        InsertMapRequest request = new InsertMapRequest();
        InsertMapRequest.Query query = new InsertMapRequest.Query();
        query.setId(orderId);
        query.setApiId("HC020402");
        query.setNo(no);
        query.setUserid(userInfo.getUserId());
        query.setCustomerID(userInfo.getUserId());
        query.setCoordinateName(locationBean.getTitle());
        query.setLatitude(locationBean.getLat()+"");
        query.setLongitude(locationBean.getLon()+"");
        request.setQuery(query);

        insertMapController.sendRequest(HttpAction.ACTION_INSERT_MAP_INFO, request);
    }

    private boolean checkInput() {
        if(groomLocation == null) {
            UIUtils.showToastSafe("请选择新郎地址");
            return false;
        }

        if(brideLocation == null) {
            UIUtils.showToastSafe("请选择新娘地址");
            return false;
        }
        if(completeLocation == null) {
            UIUtils.showToastSafe("请选择结束地址");
            return false;
        }
        for (Map.Entry<View, CarUsageBean> entry : carMap.entrySet()) {
            CarUsageBean bean = entry.getValue();
            if (StringUtils.isEmpty(bean.getModelKey()) || StringUtils.isEmpty(bean.getModelValue())) {
                UIUtils.showToastSafe("请选择车辆型号");
                return false;
            }
            if (StringUtils.isEmpty(bean.getCarType())) {
                UIUtils.showToastSafe("请选择主副婚车");
                return false;
            }
            if (StringUtils.isEmpty(bean.getHours()) || StringUtils.isEmpty(bean.getKm())) {
                UIUtils.showToastSafe("请选择小时和公里数");
                return false;
            }
        }
        if(StringUtils.isEmpty(et_groom_name.getText().toString().trim())) {
            UIUtils.showToastSafe("请输入新郎姓名");
            return false;
        }
        if(!CheckUtils.checkMobile(et_groom_phone.getText().toString().trim(), false)) {
            UIUtils.showToastSafe("请输入正确的新郎的电话");
            return false;
        }
        if(!StringUtils.isEmpty(et_bride_phone.getText().toString().trim()) && !CheckUtils.checkMobile(et_bride_phone.getText().toString().trim(), false)) {
            UIUtils.showToastSafe("请输入正确的新娘的电话");
            return false;
        }

        return true;
    }

    private NormalView<SendOrderEntity> sendOrderView = new NormalView<SendOrderEntity>() {
        @Override
        public void onSuccess(SendOrderEntity entity) {
            String orderId = entity.getData().get(0).getOrderId();
            System.out.println(orderId);
            carList.get(carOrderIndex).setOrderId(orderId);
            if (carOrderIndex == (carList.size() - 1)) {
                carOrderIndex = 0;

                for (int i = 0; i < carList.size(); i ++) {
                    confirmOrder(carList.get(i));
                }
                return;
            }

            carOrderIndex++;
            sendOrder(carList.get(carOrderIndex));
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
            hideProcess();
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

    private NormalView<SendOrderEntity> confirmOrderView = new NormalView<SendOrderEntity>() {
        @Override
        public void onSuccess(SendOrderEntity entity) {
            String orderId = entity.getData().get(0).getOrderId();
            System.out.println(orderId);
            orderList.add(orderId);
            if (orderList.size() == carList.size()) {
                for (int i = 0; i < carList.size(); i ++) {
                    System.out.println("------");
                    System.out.println(carList.get(i).getOrderId());
                    insertMap(carList.get(i).getOrderId(), groomLocation, "1");
                    insertMap(carList.get(i).getOrderId(), brideLocation, "2");
                    insertMap(carList.get(i).getOrderId(), completeLocation, "3");
                }
            }

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
            hideProcess();
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };


    private NormalView<BaseEntity<String>> insertMapView = new NormalView<BaseEntity<String>>() {
        @Override
        public void onSuccess(BaseEntity<String> entity) {
            //Success
            insertMapIndex ++;
            System.out.println("insertMapView-------->"+insertMapIndex);
            System.out.println(entity.toString());
            if (insertMapIndex == orderList.size() * 3) {
                insertMapIndex = 0;
                hideProcess();
                UIUtils.showToastSafe("请求成功");
                Intent[] intents = new Intent[2];
                intents[0] = new Intent(mContext, HomeActivity.class);
                intents[1] = new Intent(mContext, OrderActivity.class);
                mContext.startActivities(intents);
            }
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
            hideProcess();
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_groom:
                goToAddressChooseActivity(REQ_CODE_ADDRESS_GROOM);
                break;
            case R.id.ll_bride:
                goToAddressChooseActivity(REQ_CODE_ADDRESS_BRIDE);
                break;
            case R.id.ll_complete:
                goToAddressChooseActivity(REQ_CODE_ADDRESS_COMPLETE);
                break;
            case R.id.iv_add:
                addItemIntoCarList(null);
                break;
            case R.id.tv_date:
                selectDate();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    private void showAllMakers() {

    }


    private void measuringDistance() {
        if (groomLocation == null || brideLocation == null || completeLocation == null) {
            return;
        }
        LatLonPoint groomPoint = new LatLonPoint(groomLocation.getLat(), groomLocation.getLon());
        LatLonPoint bridePoint = new LatLonPoint(brideLocation.getLat(), brideLocation.getLon());
        LatLonPoint completePoint = new LatLonPoint(completeLocation.getLat(), completeLocation.getLon());
        //设置起点和终点，其中起点支持多个
        List<LatLonPoint> latLonPoints1 = new ArrayList<LatLonPoint>();
        latLonPoints1.add(groomPoint);
        List<LatLonPoint> latLonPoints2 = new ArrayList<LatLonPoint>();
        latLonPoints2.add(bridePoint);

        DistanceSearch.DistanceQuery  distanceQuery1 = new DistanceSearch.DistanceQuery();
        DistanceSearch.DistanceQuery  distanceQuery2 = new DistanceSearch.DistanceQuery();

        distanceQuery1.setOrigins(latLonPoints1);
        distanceQuery2.setOrigins(latLonPoints2);
        distanceQuery1.setDestination(bridePoint);
        distanceQuery2.setDestination(completePoint);
        //设置测量方式，支持直线和驾车
        distanceQuery1.setType(DistanceSearch.TYPE_DRIVING_DISTANCE);
        distanceQuery2.setType(DistanceSearch.TYPE_DRIVING_DISTANCE);

        distanceSearch.calculateRouteDistanceAsyn(distanceQuery1);
        distanceSearch.calculateRouteDistanceAsyn(distanceQuery2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_ADDRESS_GROOM && resultCode == 1) {
            groomLocation = (LocationBean) data.getSerializableExtra("ADDRESS");
            tv_groom_address.setText(groomLocation.getTitle());
            LatLng latLng = new LatLng(groomLocation.getLat(),groomLocation.getLon());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_location_small);
            mAMap.addMarker(new MarkerOptions().position(latLng).title("新郎家").icon(BitmapDescriptorFactory.fromBitmap(bitmap)).visible(true));
            measuringDistance();
            zoomToSpan();
            return;
        }
        if (requestCode == REQ_CODE_ADDRESS_BRIDE && resultCode == 1) {
            brideLocation = (LocationBean) data.getSerializableExtra("ADDRESS");
            tv_bride_address.setText(brideLocation.getTitle());
            LatLng latLng = new LatLng(brideLocation.getLat(), brideLocation.getLon());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_location_small);
            mAMap.addMarker(new MarkerOptions().position(latLng).title("新娘家").icon(BitmapDescriptorFactory.fromBitmap(bitmap)).visible(true));
            measuringDistance();
            zoomToSpan();
            return;
        }
        if (requestCode == REQ_CODE_ADDRESS_COMPLETE && resultCode == 1) {
            completeLocation = (LocationBean) data.getSerializableExtra("ADDRESS");
            tv_complete_address.setText(completeLocation.getTitle());
            LatLng latLng = new LatLng(completeLocation.getLat(),  completeLocation.getLon());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_location_small);
            mAMap.addMarker(new MarkerOptions().position(latLng).title("结束地址").icon(BitmapDescriptorFactory.fromBitmap(bitmap)).visible(true));
            measuringDistance();
            zoomToSpan();
            return;
        }

        if (requestCode == REQ_CODE_CAR_BRAND && resultCode == 1) {
            String brandId = data.getStringExtra("CAR_BRAND_ID");
            String brandValue = data.getStringExtra("CAR_BRAND_VALUE");
            String modelId = data.getStringExtra("CAR_MODEL_ID");
            String modelValue = data.getStringExtra("CAR_MODEL_VALUE");
            TextView tv_car_brand = (TextView) currentView.findViewById(R.id.tv_car_brand);
            tv_car_brand.setText(brandValue+modelValue);
            CarUsageBean bean = carMap.get(currentView);
            bean.setBrandKey(brandId);
            bean.setBrandValue(brandValue);
            bean.setModelKey(modelId);
            bean.setModelValue(modelValue);
        }
    }

    @Override
    public void onDistanceSearched(DistanceResult distanceResult, int code) {
        System.out.println("onDistanceSearched----->"+code);
        if (code == 1000) {
            List<DistanceItem> itemList = distanceResult.getDistanceResults();
            System.out.println(itemList.get(0).getDistance());
            if (distance != -1) {
                distance += itemList.get(0).getDistance();
                System.out.println(distance);
                tv_mileage.setText(getResources().getString(R.string.text_estimate_km, Math.round(distance/100d)/10d+""));
                distance = -1;
                return;
            }
            distance = itemList.get(0).getDistance();
        }
    }

    /**
     *  缩放移动地图，保证所有自定义marker在可视范围中。
     */
    public void zoomToSpan() {
        LatLngBounds.Builder b = LatLngBounds.builder();
        if (groomLocation != null) {
            b.include(new LatLng(groomLocation.getLat(), groomLocation.getLon()));
        }
        if (brideLocation != null) {
            b.include(new LatLng(brideLocation.getLat(), brideLocation.getLon()));
        }
        if (completeLocation != null) {
            b.include(new LatLng(completeLocation.getLat(), completeLocation.getLon()));
        }
        if (mAMap == null)
            return;
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(b.build(), 15));
    }
}
