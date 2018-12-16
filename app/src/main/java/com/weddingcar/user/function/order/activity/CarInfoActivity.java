package com.weddingcar.user.function.order.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.network.library.bean.mine.request.GetCarInfoRequest;
import com.network.library.bean.mine.response.GetCarInfoEntity;
import com.network.library.bean.order.response.OrderCarListEntity;
import com.network.library.constant.HttpAction;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.config.Config;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarInfoActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_car_picture)
    ImageView iv_car_picture;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;
    @BindView(R.id.iv_status)
    ImageView iv_status;
    @BindView(R.id.tv_car_brand)
    TextView tv_car_brand;
    @BindView(R.id.tv_car_color)
    TextView tv_car_color;
    @BindView(R.id.tv_car_plate)
    TextView tv_car_plate;
    @BindView(R.id.tv_description)
    TextView tv_description;
    @BindView(R.id.iv_img1)
    ImageView iv_img1;
    @BindView(R.id.iv_img2)
    ImageView iv_img2;
    @BindView(R.id.iv_img3)
    ImageView iv_img3;
    @BindView(R.id.iv_img4)
    ImageView iv_img4;
    @BindView(R.id.iv_img5)
    ImageView iv_img5;
    @BindView(R.id.iv_img6)
    ImageView iv_img6;
    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.tv_order)
    TextView tv_order;
    @BindView(R.id.tv_customer)
    TextView tv_customer;
    @BindView(R.id.rb_start)
    RatingBar rb_start;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_evaluate)
    TextView tv_evaluate;

    NetworkController mController;
    UserInfo userInfo;
    GetCarInfoEntity.Data carInfo;
    OrderCarListEntity.Data.ModelBean data;

    @Override
    protected void init() {
        super.init();
        data = (OrderCarListEntity.Data.ModelBean) getIntent().getSerializableExtra("DATA");
        setContentView(R.layout.activity_car_info);
        ButterKnife.bind(this);
        userInfo = SPController.getInstance().getUserInfo();
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeftAndRight("车辆信息");
    }

    @Override
    protected void initView() {
        super.initView();
        mController = new NetworkController();
        mController.attachView(getCarInfoView);

        mTopRight.setOnClickListener(this);
        tv_evaluate.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCarInfo();
    }

    private void initCarInfo() {
        GetCarInfoRequest request = new GetCarInfoRequest();
        GetCarInfoRequest.Query query = new GetCarInfoRequest.Query();
        query.setApiId("HC010305");
        query.setDEVICEID(userInfo.getDeviceId());
        query.setUserid(userInfo.getUserId());
        query.setId(data.getDriverID());
        request.setQuery(query);
        mController.sendRequest(HttpAction.ACTION_GET_CAR_INFO, request);
    }

    private NormalView<GetCarInfoEntity> getCarInfoView = new NormalView<GetCarInfoEntity>() {
        @Override
        public void onSuccess(GetCarInfoEntity entity) {
            carInfo = entity.getData().get(0);

            String carModel = StringUtils.checkString(carInfo.getCarBrandName()) + StringUtils.checkString(carInfo.getCarModelName());
            String carColor = StringUtils.isEmpty(carInfo.getCarColorName()) ? "--" : carInfo.getCarColorName();

            tv_name.setText(carInfo.getName());
            RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_car);
            Glide.with(mContext).load(Config.getUserAvatorBaseUrl() + carInfo.getAvator()).apply(options).into(iv_car_picture);
            tv_car_brand.setText(carModel);
            tv_car_color.setText(carColor);
            tv_car_plate.setText(carInfo.getPlate());
            tv_description.setText(carInfo.getIntroduce());
            tv_order_num.setText(getResources().getString(R.string.text_order_num, carInfo.getOrderQuantity()+""));
            tv_phone.setText(carInfo.getCustomerID());
            Glide.with(mContext).load(Config.getUserAvatorBaseUrl() + carInfo.getImagePathZQ()).apply(options).into(iv_img1);
            Glide.with(mContext).load(Config.getUserAvatorBaseUrl() + carInfo.getImagePathYH()).apply(options).into(iv_img2);
            Glide.with(mContext).load(Config.getUserAvatorBaseUrl() + carInfo.getImagePath1()).apply(options).into(iv_img3);
            Glide.with(mContext).load(Config.getUserAvatorBaseUrl() + carInfo.getImagePath2()).apply(options).into(iv_img4);
            Glide.with(mContext).load(Config.getUserAvatorBaseUrl() + carInfo.getImagePath3()).apply(options).into(iv_img5);
            Glide.with(mContext).load(Config.getUserAvatorBaseUrl() + carInfo.getImagePath4()).apply(options).into(iv_img6);

        }

        @Override
        public void showLoading() {}

        @Override
        public void hideLoading() {}

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
            case R.id.tv_evaluate:
                goToEvaluateActivity();
                break;
        }
    }

    private void goToEvaluateActivity() {
        Intent intent = new Intent(this, EvaluateActivity.class);
        startActivity(intent);
    }
}
