package com.weddingcar.user.function.order.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.network.library.bean.BaseEntity;
import com.network.library.bean.order.request.DeleteOrderRequest;
import com.network.library.bean.order.request.LockCarRequest;
import com.network.library.bean.order.request.OrderCarListRequest;
import com.network.library.bean.order.request.OrderInfoRequest;
import com.network.library.bean.order.request.UploadCarLocationRequest;
import com.network.library.bean.order.response.OrderCarListEntity;
import com.network.library.bean.order.response.OrderInfoEntity;
import com.network.library.bean.order.response.OrderListEntity;
import com.network.library.constant.HttpAction;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.config.Config;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.map.LocationBean;
import com.weddingcar.user.common.ui.CircleImageView;
import com.weddingcar.user.common.ui.MaterialDialog;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;
import com.weddingcar.user.function.main.activity.AddressActivity;
import com.weddingcar.user.function.order.adapter.OrderCarListAdapter;
import com.weddingcar.user.function.order.inter.OnCheckListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CallingOrderInfoActivity extends BaseActivity implements View.OnClickListener, OnCheckListener{

    @BindView(R.id.tv_order_num)
    TextView tv_order_num;
    @BindView(R.id.iv_header)
    CircleImageView iv_header;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_car_type)
    TextView tv_car_type;
    @BindView(R.id.tv_car_brand)
    TextView tv_car_brand;
    @BindView(R.id.tv_hours)
    TextView tv_hours;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_sign_up_count)
    TextView tv_sign_up_count;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.ll_map_info_title)
    LinearLayout ll_map_info_title;
    @BindView(R.id.tv_arrow)
    TextView tv_arrow;
    @BindView(R.id.ll_map_info)
    LinearLayout ll_map_info;
    @BindView(R.id.tv_groom_address)
    TextView tv_groom_address;
    @BindView(R.id.tv_bride_address)
    TextView tv_bride_address;
    @BindView(R.id.tv_complete_address)
    TextView tv_complete_address;
    @BindView(R.id.tv_over_money)
    TextView tv_over_money;
    @BindView(R.id.tv_note)
    TextView tv_note;
    @BindView(R.id.tv_count_money)
    TextView tv_count_money;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.mv_map)
    TextureMapView mv_map;
    @BindView(R.id.lv_car)
    ListView lv_car;

    OrderListEntity.Data data;
    OrderInfoEntity.Data orderInfo;

    NetworkController orderInfoController;
    NetworkController orderCarListController;
    NetworkController normalController;
    NetworkController cancelController;
    UserInfo userInfo;
    private AMap mAMap;
    LocationBean groomLocation = null;
    LocationBean brideLocation = null;
    LocationBean completeLocation = null;

    List<OrderCarListEntity.Data.ModelBean> carList;
    OrderCarListAdapter carListAdapter;
    double price = 0;
    private int carRequestIndex = 0;
    @Override
    protected void init() {
        super.init();
        userInfo = SPController.getInstance().getUserInfo();
        data = (OrderListEntity.Data) getIntent().getSerializableExtra("DATA");
        setContentView(R.layout.activity_calling_info);
        ButterKnife.bind(this);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeftAndRight("订单详情");
        setTopRightText("取消");
    }

    @Override
    protected void initView() {
        super.initView();
        carList = new ArrayList();
        carListAdapter = new OrderCarListAdapter(this, this);
        lv_car.setAdapter(carListAdapter);
        orderInfoController = new NetworkController();
        orderInfoController.attachView(getOrderInfoView);
        orderCarListController = new NetworkController();
        orderCarListController.attachView(getOrderCarListView);
        normalController = new NetworkController();
        normalController.attachView(normalView);
        cancelController = new NetworkController();
        cancelController.attachView(cancelView);

        mTopRight.setOnClickListener(this);
        ll_map_info_title.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
        tv_count_money.setText(getResources().getString(R.string.text_btn_count_money, "0", "0"));

        lv_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("onItemClick----->");
                Intent intent = new Intent(mContext, CarInfoActivity.class);
                intent.putExtra("DATA", carList.get(position));
                startActivity(intent);
            }
        });

        initMap();
        initData();
        initOrderCarList();
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


    private void initMap() {
        if (mAMap == null) {
            mAMap = mv_map.getMap();
        }
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
//        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;
//        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        mAMap.setMyLocationEnabled(false);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        uiSettings.setZoomControlsEnabled(false);  //隐藏缩放按钮
        uiSettings.setAllGesturesEnabled(false); //禁止所有手势
    }

    private void initOrderInfo() {
        if (orderInfo == null) {
            return;
        }
        for (int i = 0; i < orderInfo.getMapInfos().size(); i ++) {
            if (StringUtils.equals(orderInfo.getMapInfos().get(i).getNumber(), "1")) {
                double lat = Double.parseDouble(orderInfo.getMapInfos().get(i).getLatitude());
                double lon = Double.parseDouble(orderInfo.getMapInfos().get(i).getLongitude());
                groomLocation = new LocationBean();
                groomLocation.setTitle(orderInfo.getMapInfos().get(i).getCoordinateName());
                groomLocation.setLat(lat);
                groomLocation.setLon(lon);
                LatLng latLng = new LatLng(lat, lon);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_location_small);
                mAMap.addMarker(new MarkerOptions().position(latLng).title("新郎家").icon(BitmapDescriptorFactory.fromBitmap(bitmap)).visible(true));
            }
            if (StringUtils.equals(orderInfo.getMapInfos().get(i).getNumber(), "2")) {
                double lat = Double.parseDouble(orderInfo.getMapInfos().get(i).getLatitude());
                double lon = Double.parseDouble(orderInfo.getMapInfos().get(i).getLongitude());
                brideLocation = new LocationBean();
                brideLocation.setTitle(orderInfo.getMapInfos().get(i).getCoordinateName());
                brideLocation.setLat(lat);
                brideLocation.setLon(lon);
                LatLng latLng = new LatLng(lat, lon);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_location_small);
                mAMap.addMarker(new MarkerOptions().position(latLng).title("新娘家").icon(BitmapDescriptorFactory.fromBitmap(bitmap)).visible(true));
            }
            if (StringUtils.equals(orderInfo.getMapInfos().get(i).getNumber(), "3")) {
                double lat = Double.parseDouble(orderInfo.getMapInfos().get(i).getLatitude());
                double lon = Double.parseDouble(orderInfo.getMapInfos().get(i).getLongitude());
                completeLocation = new LocationBean();
                completeLocation.setTitle(orderInfo.getMapInfos().get(i).getCoordinateName());
                completeLocation.setLat(lat);
                completeLocation.setLon(lon);
                LatLng latLng = new LatLng(lat, lon);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_location_small);
                mAMap.addMarker(new MarkerOptions().position(latLng).title("结束地址").icon(BitmapDescriptorFactory.fromBitmap(bitmap)).visible(true));
            }
        }
        zoomToSpan(groomLocation, brideLocation, completeLocation);
        tv_order_num.setText(mContext.getResources().getString(R.string.text_item_order_num, orderInfo.getCode()));
        RequestOptions options = new RequestOptions();
        Glide.with(UIUtils.getContext()).load(Config.getUserAvatorBaseUrl() + orderInfo.getCustomerAvator()).apply(options).into(iv_header);
        tv_date.setText(getTime(orderInfo.getTheWeddingDateString()));
        tv_car_brand.setText(StringUtils.checkString(orderInfo.getCarBrandName())+StringUtils.checkString(orderInfo.getCarModelName()));
        tv_hours.setText(mContext.getResources().getString(R.string.text_car_hours, orderInfo.getHourChoose()+""));
        tv_distance.setText(mContext.getResources().getString(R.string.text_car_km, orderInfo.getJourneyChoose()+""));
        tv_area.setText(orderInfo.getAreaName());
        tv_car_type.setSelected(StringUtils.equals(orderInfo.getIscar(), "1"));
        tv_sign_up_count.setText(mContext.getResources().getString(R.string.text_sign_up_count, orderInfo.getOfferCount()+""));
        tv_price.setText(mContext.getResources().getString(R.string.text_current_price, orderInfo.getAmountAverages()+""));
        tv_note.setText(orderInfo.getNote());
        tv_over_money.setText(getResources().getString(R.string.text_over_money, orderInfo.getPriceBaseTimeout()+"", orderInfo.getPriceBaseDistance()+""));
        if (groomLocation != null) {
            tv_groom_address.setText(groomLocation.getTitle());
        }
        if (brideLocation != null) {
            tv_bride_address.setText(brideLocation.getTitle());
        }
        if (completeLocation != null) {
            tv_complete_address.setText(completeLocation.getTitle());
        }
    }

    private void initData() {
        OrderInfoRequest request = new OrderInfoRequest();
        OrderInfoRequest.Query query = new OrderInfoRequest.Query();
        query.setOrderId(data.getCode());
        query.setApiId("HC010303");
        query.setUserid(userInfo.getUserId());
        request.setQuery(query);

        orderInfoController.sendRequest(HttpAction.ACTION_GET_ORDER_INFO, request);
    }

    private void initOrderCarList() {
        OrderCarListRequest request = new OrderCarListRequest();
        OrderCarListRequest.Query query = new OrderCarListRequest.Query();
        query.setApiId("HC010304");
        query.setCustomerId(userInfo.getUserId());
        query.setOrderId(data.getCode());
        request.setQuery(query);

        orderCarListController.sendRequest(HttpAction.ACTION_GET_ORDER_CAR_LIST, request);
    }

    private void lockCarInfo() {
        String offerIDs = "";
        Map<String, OrderCarListEntity.Data.ModelBean> map = carListAdapter.getCheckMap();
        for (Map.Entry<String, OrderCarListEntity.Data.ModelBean> entry : map.entrySet()) {
            if (StringUtils.isEmpty(offerIDs)) {
                offerIDs = entry.getValue().getOrderOfferID();
            }
            else {
                offerIDs += ","+entry.getValue().getOrderOfferID();
            }
        }
        LockCarRequest request = new LockCarRequest();
        LockCarRequest.Query query = new LockCarRequest.Query();
        query.setApiId("HC020307");
        query.setOrderOfferIDs(offerIDs);
        query.setOrderID(orderInfo.getCode());
        query.setAmount(price+"");
        request.setQuery(query);

        normalController.sendRequest(HttpAction.ACTION_LOCK_CAR, request);
    }

    private void uploadCarInfo() {
        String driverIDs = "";
        Map<String, OrderCarListEntity.Data.ModelBean> map = carListAdapter.getCheckMap();
        for (Map.Entry<String, OrderCarListEntity.Data.ModelBean> entry : map.entrySet()) {
            if (StringUtils.isEmpty(driverIDs)) {
                driverIDs = entry.getValue().getDriverID();
            }
            else {
                driverIDs += ","+entry.getValue().getDriverID();
            }
        }
        UploadCarLocationRequest request = new UploadCarLocationRequest();
        UploadCarLocationRequest.Query query = new UploadCarLocationRequest.Query();
        query.setApiId("HC020710");
        query.setDriverIDS(driverIDs);
        query.setOrderID(orderInfo.getCode());
        query.setAmount(price+"");
        request.setQuery(query);

        normalController.sendRequest(HttpAction.ACTION_LOCK_CAR, request);
    }

    private NormalView<OrderInfoEntity> getOrderInfoView = new NormalView<OrderInfoEntity>() {
        @Override
        public void onSuccess(OrderInfoEntity entity) {
            orderInfo = entity.getData().get(0);
            initOrderInfo();
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

    private NormalView<OrderCarListEntity> getOrderCarListView = new NormalView<OrderCarListEntity>() {
        @Override
        public void onSuccess(OrderCarListEntity entity) {
            OrderCarListEntity.Data item = entity.getData().get(0);
            carList = item.getModel();
            carListAdapter.setData(carList);
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

    private NormalView<BaseEntity<String>> normalView = new NormalView<BaseEntity<String>>() {
        @Override
        public void onSuccess(BaseEntity<String> entity) {
            System.out.println("normalView---->"+carRequestIndex+entity.getData());
            System.out.println(carRequestIndex);
            if (carRequestIndex == 1) {
                carRequestIndex = 0;
                hideProcess();
                Intent intent = new Intent(mContext, PayActivity.class);
                intent.putExtra("ORDER_ID", orderInfo.getCode());
                startActivity(intent);
                finish();
                return;
            }

            carRequestIndex++;
            uploadCarInfo();
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

    private NormalView<BaseEntity<String>> cancelView = new NormalView<BaseEntity<String>>() {
        @Override
        public void onSuccess(BaseEntity<String> entity) {
            System.out.println("cancelView---->"+entity.getData());
            finish();
        }

        @Override
        public void showLoading() {
            showProcess("正在取消订单");
        }

        @Override
        public void hideLoading() {
            hideProcess();
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


    private String getTime(String timestamp) {//可根据需要自行截取数据显示
        long time;
        try {
            time = Long.parseLong(timestamp);
        } catch (Exception e) {
            return "--";
        }

        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    private void toPay() {
        if (orderInfo.getOfferCount() == 0) {
            UIUtils.showToastSafe("暂时没有报名车辆");
            return;
        }
        if (carListAdapter.getCheckMap().size() == 0) {
            UIUtils.showToastSafe("请先选择报名车辆!");
            return;
        }
        carRequestIndex = 0;
        showProcess("正在获取支付信息...");
        lockCarInfo();
    }

    private void cancelOrder() {
        DeleteOrderRequest request = new DeleteOrderRequest();
        DeleteOrderRequest.Query query = new DeleteOrderRequest.Query();
        query.setApiId("HC020303");
        query.setID(orderInfo.getCode());
        query.setCustomerID(userInfo.getUserId());

        request.setQuery(query);

        cancelController.sendRequest(HttpAction.ACTION_DELETE_ORDER, request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_map_info_title:
                tv_arrow.setSelected(!tv_arrow.isSelected());
                ll_map_info.setVisibility(tv_arrow.isSelected()?View.VISIBLE:View.GONE);
                break;
            case R.id.tv_pay:
                toPay();
                break;
            case R.id.right:
                MaterialDialog dialog = new MaterialDialog(this);
                dialog.setTitle("提示");
                dialog.setMessage("确定要取消订单吗");
                dialog.setCanceledOnTouchOutside(false);
                dialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        cancelOrder();
                    }
                });
                dialog.show();
                break;
        }
    }


    /**
     *  缩放移动地图，保证所有自定义marker在可视范围中。
     */
    public void zoomToSpan(LocationBean groomLocation, LocationBean brideLocation, LocationBean completeLocation) {
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

    @Override
    public void onCheckEvent(boolean isChecked) {
        if (orderInfo == null) {
            return;
        }
        price = orderInfo.getAmountAverages() * carListAdapter.getCheckMap().size();
        tv_count_money.setText(getResources().getString(R.string.text_btn_count_money, carListAdapter.getCheckMap().size()+"", price+""));
    }
}
