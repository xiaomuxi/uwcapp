package com.weddingcar.user.function.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.network.library.bean.main.request.CarModelListRequest;
import com.network.library.bean.main.response.CarModelEntity;
import com.network.library.bean.mine.request.GetCarBrandsRequest;
import com.network.library.bean.mine.response.GetCarBrandsEntity;
import com.network.library.constant.HttpAction;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.fragment.BaseLoadingFragment;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.config.Config;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.ui.ScaleTransitionPagerTitleView;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;
import com.weddingcar.user.function.main.activity.ChooseCarActivity;
import com.weddingcar.user.function.main.activity.ConfirmOrderActivity;
import com.weddingcar.user.function.main.adapter.CarBrandPagerAdapter;
import com.weddingcar.user.function.main.adapter.CarModelAdapter;
import com.weddingcar.user.function.main.bean.CarUsageBean;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by inrokei on 2018/5/1.
 */

public class SelfListFragment extends BaseLoadingFragment {

    @BindView(R.id.lv_model)
    ListView lv_model;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    List<GetCarBrandsEntity.Data> brandDataList = new ArrayList<>();
    List<CarModelEntity.Data> modelDataList = new ArrayList<>();
    UserInfo userInfo;
    NetworkController getBrandListController;
    NetworkController getModelListController;
    private CarBrandPagerAdapter carBrandPagerAdapter;
    CarModelAdapter carModelAdapter;
    int currentIndex = 0;
    String date;
    @Override
    protected View setContentView() {
        return UIUtils.inflate(mContext, R.layout.fragment_self_list);
    }

    @Override
    protected void init() {
        userInfo = SPController.getInstance().getUserInfo();
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initCreated(Bundle savedInstanceState) {
        date = ((ChooseCarActivity) mContext).getDate();

        carBrandPagerAdapter = new CarBrandPagerAdapter(brandDataList);
        view_pager.setAdapter(carBrandPagerAdapter);
        carModelAdapter = new CarModelAdapter(mContext);
        lv_model.setAdapter(carModelAdapter);
        getBrandListController = new NetworkController();
        getModelListController = new NetworkController();
        getBrandListController.attachView(getCarBrandsView);
        getModelListController.attachView(getCarModelView);

        lv_model.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarUsageBean carUsageBean = new CarUsageBean();
                carUsageBean.setBrandValue(modelDataList.get(position).getBrandName());
                carUsageBean.setModelKey(modelDataList.get(position).getId());
                carUsageBean.setModelValue(modelDataList.get(position).getModelName());

                Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                intent.putExtra("Date", ((ChooseCarActivity) mContext).getDate());
                intent.putExtra("TYPE", "Self");
                intent.putExtra("CAR_SELF", carUsageBean);
                mContext.startActivity(intent);
            }
        });

        initData();

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentIndex = i;
                initCarModelList();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return brandDataList == null ? 0 : brandDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                Drawable drawable = context.getResources().getDrawable(R.drawable.ic_car);
                drawable.setBounds(0, 0, 80, 80);
                simplePagerTitleView.setCompoundDrawables(null, drawable, null, null);
                RequestOptions options = new RequestOptions().placeholder(R.drawable.my_head);
                Glide.with(mContext) // safer!
                        .load(Config.getCarBrandsBaseUrl() + brandDataList.get(index).getLogo())
                        .apply(options)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                resource.setBounds(0, 0, 80, 80);
                                simplePagerTitleView.setCompoundDrawables(null, resource, null, null);
                            }
                        });
                simplePagerTitleView.setText(brandDataList.get(index).getValue());
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view_pager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, view_pager);
    }


    public void initData() {
        GetCarBrandsRequest request = new GetCarBrandsRequest();
        GetCarBrandsRequest.Query query = new GetCarBrandsRequest.Query();
        query.setApiId("HC010201");
        query.setUserid(userInfo.getUserId());
        query.setDEVICEID(userInfo.getDeviceId());
        request.setQuery(query);

        getBrandListController.sendRequest(HttpAction.ACTION_GET_CAR_BRANDS, request);
    }

    private NormalView<GetCarBrandsEntity> getCarBrandsView = new NormalView<GetCarBrandsEntity>() {
        @Override
        public void onSuccess(GetCarBrandsEntity entity) {
            System.out.println("GET car brands-->");
            System.out.println(entity.getData().toString());
            brandDataList = entity.getData();
            carBrandPagerAdapter.setData(brandDataList);
            initMagicIndicator();
            currentIndex = 0;
            initCarModelList();
            show(check(brandDataList));
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
            show(check(brandDataList));
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

    private void initCarModelList() {
        System.out.println("initCarModelList======>");
        CarModelListRequest request = new CarModelListRequest();
        CarModelListRequest.Query query = new CarModelListRequest.Query();
        query.setApiId("HC050005");
        query.setBid(brandDataList.get(currentIndex).getKey()+"");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            Date dateTime = simpleDateFormat.parse(date);
            query.setDate(dateTime.getTime()+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.setPageIndex("1");
        query.setPageSize("500");
        query.setUserid(userInfo.getUserId());
        request.setQuery(query);

        getModelListController.sendRequest(HttpAction.ACTION_GET_CAR_MODEL_LIST, request);
    }

    private NormalView<CarModelEntity> getCarModelView = new NormalView<CarModelEntity>() {
        @Override
        public void onSuccess(CarModelEntity entity) {
            System.out.println("GET car models-->");
            System.out.println(entity.getData().toString());
            modelDataList = entity.getData();
            carModelAdapter.setData(modelDataList);
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
    protected View createLoadedView() {
        return setContentView();
    }
    @Override
    protected boolean isNeedLoadEveryTime() {
        return true;
    }
    @Override
    protected void load() {
        initData();
    }
}
