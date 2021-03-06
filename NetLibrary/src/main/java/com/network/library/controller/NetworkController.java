package com.network.library.controller;


import com.network.library.Model.NetworkModel;
import com.network.library.bean.BaseEntity;
import com.network.library.bean.BaseRequest;
import com.network.library.bean.WeatherEntity;
import com.network.library.bean.user.response.RegisterEntity;
import com.network.library.bean.user.response.RobbingInfoEntity;
import com.network.library.bean.user.response.SignUpInfoEntity;
import com.network.library.bean.user.response.VerifyCodeEntity;
import com.network.library.callback.CallBack;
import com.network.library.view.BaseNetView;
import com.network.library.view.GetRobbingView;
import com.network.library.view.GetSignUpListView;
import com.network.library.view.GetWeatherView;
import com.network.library.view.NormalView;

import java.util.List;

public class NetworkController<V extends BaseNetView> {

    // 持有 MVP 中 View 的引用
    private V iMvpView;


    public NetworkController() {
    }

    public void attachView(V view) {
        this.iMvpView = view;
    }

    public void detachView() {
        this.iMvpView = null;
    }

    public boolean isViewAttached() {
        return iMvpView != null;
    }

    public V getView() {
        return iMvpView;
    }

    /**
     * 获取天气
     *
     * @param city 城市
     */

    public void getWeather(final String city) {
        if (!isViewAttached()) {
            return;
        }
        NetworkModel.getWeather(city, new CallBack<WeatherEntity>() {
            @Override
            public void onStart() {
                if (isViewAttached())
                    iMvpView.showLoading();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    iMvpView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                if (isViewAttached())
                    iMvpView.onRequestError(msg, "getWeather");
            }

            @Override
            public void onSuccess(WeatherEntity data) {
                if (isViewAttached())
                    ((GetWeatherView) iMvpView).onGetWeatherSuccess(data);
            }
        });
    }

    public void login(String phone, String pwd) {
        if (!isViewAttached()) {
            return;
        }
        NetworkModel.login(phone, pwd, new CallBack<BaseEntity>() {
            @Override
            public void onStart() {
                if (isViewAttached())
                    iMvpView.showLoading();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    iMvpView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                if (isViewAttached())
                    iMvpView.onRequestError(msg, "login");
            }

            @Override
            public void onSuccess(BaseEntity data) {
                if (isViewAttached())
                    ((NormalView) iMvpView).onSuccess(data);
            }
        });
    }

    public void sendVerifyCode(String phone, String type) {
        if (!isViewAttached()) {
            return;
        }
        NetworkModel.sendVerifyCode(phone, type, new CallBack<VerifyCodeEntity>() {
            @Override
            public void onStart() {
                if (isViewAttached())
                    iMvpView.showLoading();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    iMvpView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                if (isViewAttached())
                    iMvpView.onRequestError(msg, "sendVerifyCode");
            }

            @Override
            public void onSuccess(VerifyCodeEntity data) {
                if (isViewAttached())
                    ((NormalView) iMvpView).onSuccess(data);
            }
        });
    }

    public void register(String phone, String pwd, String pwdAgain, String verifyCode) {
        if (!isViewAttached()) {
            return;
        }
        NetworkModel.register(phone, pwd, pwdAgain, verifyCode, new CallBack<RegisterEntity>() {
            @Override
            public void onStart() {
                if (isViewAttached())
                    iMvpView.showLoading();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    iMvpView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                if (isViewAttached())
                    iMvpView.onRequestError(msg, "sendVerifyCode");
            }

            @Override
            public void onSuccess(RegisterEntity data) {
                if (isViewAttached())
                    ((NormalView) iMvpView).onSuccess(data);
            }
        });
    }

    public void sendRequest(String action, BaseRequest baseRequest) {
        if (!isViewAttached()) {
            return;
        }
        NetworkModel.sendRequest(action, baseRequest.getQuery(), baseRequest.getBody(), new CallBack<BaseEntity>() {
            @Override
            public void onStart() {
                if (isViewAttached())
                    iMvpView.showLoading();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    iMvpView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                if (isViewAttached())
                    iMvpView.onRequestError(msg, action);
            }

            @Override
            public void onSuccess(BaseEntity data) {
                if (isViewAttached())
                    ((NormalView) iMvpView).onSuccess(data);
            }
        });
    }

    public void getSignUpList(final String apiId, String customerId, String orderId) {
        if (!isViewAttached()) {
            return;
        }
        NetworkModel.getSignUpList(apiId, customerId, orderId, new CallBack<BaseEntity<List<SignUpInfoEntity>>>() {
            @Override
            public void onStart() {
                if (isViewAttached())
                    iMvpView.showLoading();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    iMvpView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                if (isViewAttached())
                    iMvpView.onRequestError(msg, "getSignUpList");
            }

            @Override
            public void onSuccess(BaseEntity<List<SignUpInfoEntity>> data) {
                if (isViewAttached())
                    ((GetSignUpListView) iMvpView).onGetSignUpListSuccess(data);
            }
        });
    }

    public void getRobbingList(String apiId, String customerId, String carBrandId, String carModelId, boolean showLoading) {
        if (!isViewAttached()) {
            return;
        }
        NetworkModel.getRobbingList(apiId, customerId, carBrandId, carModelId, new CallBack<BaseEntity<List<RobbingInfoEntity>>>() {
            @Override
            public void onStart() {
                if (isViewAttached() && showLoading)
                    iMvpView.showLoading();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                    iMvpView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                if (isViewAttached())
                    iMvpView.onRequestError(msg, "getRobbingList");
            }

            @Override
            public void onSuccess(BaseEntity<List<RobbingInfoEntity>> data) {
                if (isViewAttached())
                    ((GetRobbingView) iMvpView).onGetRobbingSuccess(data);
            }
        });
    }
}
