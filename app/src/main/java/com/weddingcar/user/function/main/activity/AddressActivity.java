package com.weddingcar.user.function.main.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.map.LocationBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends BaseActivity {

    @BindView(R.id.mv_map)
    TextureMapView mv_map;

    private AMap mAMap;
    private AMapLocationClient mLocationClient;

    private LocationBean groomLocation;
    private LocationBean brideLocation;
    private LocationBean completeLocation;
    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);

        groomLocation = (LocationBean) getIntent().getSerializableExtra("GROOM_ADDRESS");
        brideLocation = (LocationBean) getIntent().getSerializableExtra("BRIDE_ADDRESS");
        completeLocation = (LocationBean) getIntent().getSerializableExtra("COMPLETE_ADDRESS");
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft("接亲地址");
    }

    @Override
    protected void initView() {
        super.initView();

        initMap();
        initLocation();
        initAddressMakers();
    }

    private void initMap() {
        if (mAMap == null) {
            mAMap = mv_map.getMap();
        }
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        UiSettings uiSettings = mAMap.getUiSettings();myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        uiSettings.setZoomControlsEnabled(false);  //隐藏缩放按钮
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

    private void initAddressMakers() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_location_small);
        if (groomLocation != null) {
            LatLng groomLatLng = new LatLng(groomLocation.getLat(),  groomLocation.getLon());
            mAMap.addMarker(new MarkerOptions().position(groomLatLng).title("新郎地址").icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
        }
        if (brideLocation != null)  {
            LatLng brideLatLng = new LatLng(brideLocation.getLat(),  brideLocation.getLon());
            mAMap.addMarker(new MarkerOptions().position(brideLatLng).title("新娘地址").icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
        }
        if (completeLocation != null)  {
            LatLng completeLatLng = new LatLng(completeLocation.getLat(),  completeLocation.getLon());
            mAMap.addMarker(new MarkerOptions().position(completeLatLng).title("结束地址").icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
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
}
