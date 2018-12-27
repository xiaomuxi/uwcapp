package com.weddingcar.user.function.order.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.network.library.bean.BaseEntity;
import com.network.library.bean.order.request.OrderCarListRequest;
import com.network.library.bean.order.request.OrderInfoRequest;
import com.network.library.bean.order.request.UploadAggregateInfoRequest;
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
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;
import com.weddingcar.user.function.main.activity.AddressActivity;
import com.weddingcar.user.function.main.activity.AddressChooseActivity;
import com.weddingcar.user.function.order.adapter.CompleteCarListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompleteOrderInfoActivity extends BaseActivity implements View.OnClickListener{

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
    @BindView(R.id.mv_map)
    TextureMapView mv_map;
    @BindView(R.id.lv_car)
    ListView lv_car;
    @BindView(R.id.tv_modify_aggregate)
    TextView tv_modify_aggregate;
    @BindView(R.id.tv_aggregate_time)
    TextView tv_aggregate_time;
    @BindView(R.id.tv_aggregate_location)
    TextView tv_aggregate_location;
    @BindView(R.id.et_aggregate_address)
    TextView et_aggregate_address;
    @BindView(R.id.et_contact_name)
    TextView et_contact_name;
    @BindView(R.id.et_contact_phone)
    TextView et_contact_phone;

    boolean editable = false;

    OrderListEntity.Data data;
    OrderInfoEntity.Data orderInfo;

    NetworkController orderInfoController;
    NetworkController orderCarListController;
    NetworkController normalController;
    UserInfo userInfo;
    private AMap mAMap;
    LocationBean groomLocation = null;
    LocationBean brideLocation = null;
    LocationBean completeLocation = null;
    LocationBean aggregateLocation = null;

    List<OrderCarListEntity.Data.ModelBean> carList;
    CompleteCarListAdapter carListAdapter;
    TimePickerView pvTime;
    @Override
    protected void init() {
        super.init();
        userInfo = SPController.getInstance().getUserInfo();
        data = (OrderListEntity.Data) getIntent().getSerializableExtra("DATA");
        setContentView(R.layout.activity_complete_info);
        ButterKnife.bind(this);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft("订单详情");
    }

    @Override
    protected void initView() {
        super.initView();
        carList = new ArrayList();
        carListAdapter = new CompleteCarListAdapter(this);
        lv_car.setAdapter(carListAdapter);
        orderInfoController = new NetworkController();
        orderInfoController.attachView(getOrderInfoView);
        orderCarListController = new NetworkController();
        orderCarListController.attachView(getOrderCarListView);
        normalController = new NetworkController();
        normalController.attachView(normalView);

        ll_map_info_title.setOnClickListener(this);
        tv_modify_aggregate.setOnClickListener(this);
        tv_aggregate_time.setOnClickListener(this);
        tv_aggregate_location.setOnClickListener(this);

        lv_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, CarInfoActivity.class);
                intent.putExtra("DATA", carList.get(position));
                startActivity(intent);
            }
        });

        initAggregateInfo();
        initTimePicker();
        initMap();
        initData();
        initOrderCarList();
    }

    private void initTimePicker() {
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                tv_aggregate_time.setText(getPVTime(date));
            }
        })
                .setType(new boolean[]{false, false, false, true, true, false})// 默认全部显示
                .setSubmitColor(getResources().getColor(R.color.text_main_red))
                .setCancelColor(getResources().getColor(R.color.text_main_red))
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .build();
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


    private void initAggregateInfo() {
        tv_modify_aggregate.setText(editable?"保存":"修改");
        tv_aggregate_time.setClickable(editable);
        tv_aggregate_time.setBackground(editable?getResources().getDrawable(R.drawable.shape_white_stroke_square):null);
        tv_aggregate_location.setClickable(editable);
        tv_aggregate_location.setBackground(editable?getResources().getDrawable(R.drawable.shape_white_stroke_square):null);
        et_aggregate_address.setEnabled(editable);
        et_aggregate_address.setBackground(editable?getResources().getDrawable(R.drawable.shape_white_stroke_square):null);
        et_contact_name.setEnabled(editable);
        et_contact_name.setBackground(editable?getResources().getDrawable(R.drawable.shape_white_stroke_square):null);
        et_contact_phone.setEnabled(editable);
        et_contact_phone.setBackground(editable?getResources().getDrawable(R.drawable.shape_white_stroke_square):null);

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
        tv_aggregate_time.setText(orderInfo.getGatherTime());
        if (!StringUtils.isEmpty(orderInfo.getGatherCoordinateName())) {
            tv_aggregate_location.setText(orderInfo.getGatherCoordinateName());
            aggregateLocation = new LocationBean();
            aggregateLocation.setTitle(orderInfo.getGatherCoordinateName());
            aggregateLocation.setLon(Double.parseDouble(orderInfo.getGatherCoordinateLongitude()));
            aggregateLocation.setLat(Double.parseDouble(orderInfo.getGatherCoordinateLatitude()));
        }
        et_aggregate_address.setText(orderInfo.getDetailedAddress());
        et_contact_name.setText(orderInfo.getOrderRl());
        et_contact_phone.setText(orderInfo.getOrderRlTel());
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

    private void updateAggregateInfo() {
        if (StringUtils.isEmpty(tv_aggregate_time.getText().toString().trim())
                || aggregateLocation == null
                || StringUtils.isEmpty(et_contact_name.getText().toString().trim())
                || StringUtils.isEmpty(et_contact_phone.getText().toString().trim())
                || StringUtils.isEmpty(et_aggregate_address.getText().toString().trim()))  {
            UIUtils.showToastSafe("请完善集合信息");
            return;
        }

        UploadAggregateInfoRequest request = new UploadAggregateInfoRequest();
        UploadAggregateInfoRequest.Query query1 = new UploadAggregateInfoRequest.Query();
        query1.setApiId("HC020309");
        UploadAggregateInfoRequest.Query query = new UploadAggregateInfoRequest.Query();
        query.setApiId("HC020309");
        query.setCustomerID(userInfo.getUserId());
        query.setGatherCoordinateName(aggregateLocation.getTitle());
        query.setGatherLongitude(aggregateLocation.getLon()+"");
        query.setGatherLatitude(aggregateLocation.getLat()+"");
        query.setDetailedAddress(et_aggregate_address.getText().toString().trim());
        query.setGatherTime(tv_aggregate_time.getText().toString().trim());
        query.setID(orderInfo.getCode());
        query.setOrderRl(et_contact_name.getText().toString().trim());
        query.setOrderRlTel(et_contact_phone.getText().toString().trim());
        query.setItemId(data.getOderId());
        request.setQuery(query1);
        request.setBody(query);

        normalController.sendRequest(HttpAction.ACTION_UPDATE_AGGREGATE_INFO, request);
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
            hideProcess();
            System.out.println("normalView---->"+entity.getData());
            UIUtils.showToastSafe( "集合信息更新成功");
            editable = false;
            initAggregateInfo();
        }

        @Override
        public void showLoading() {
            showProcess("正在保存集合信息...");
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

    private String getPVTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_map_info_title:
                tv_arrow.setSelected(!tv_arrow.isSelected());
                ll_map_info.setVisibility(tv_arrow.isSelected()?View.VISIBLE:View.GONE);
                break;
            case R.id.tv_modify_aggregate:
                if (!editable) {
                    editable = !editable;
                    initAggregateInfo();
                    return;
                }
                updateAggregateInfo();
                break;
            case R.id.tv_aggregate_time:
                pvTime.show();
                break;
            case R.id.tv_aggregate_location:
                Intent intent = new Intent(this, AddressChooseActivity.class);
                startActivityForResult(intent, 1);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {

            aggregateLocation = (LocationBean) data.getSerializableExtra("ADDRESS");
            tv_aggregate_location.setText(aggregateLocation.getTitle());
        }
    }
}
