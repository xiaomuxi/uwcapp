package com.weddingcar.user.function.order.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.network.library.bean.BaseEntity;
import com.network.library.bean.mine.response.WXPayOrderEntity;
import com.network.library.bean.order.request.GetPayInfoRequest;
import com.network.library.bean.order.request.OrderPayResultRequest;
import com.network.library.bean.order.request.PayOrderRequest;
import com.network.library.bean.order.response.PayInfoEntity;
import com.network.library.constant.HttpAction;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.config.Config;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.payment.PayResult;
import com.weddingcar.user.common.ui.VerifyCodeView;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends BaseActivity implements View.OnClickListener, IWXAPIEventHandler {

    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.vcv_code)
    VerifyCodeView vcv_code;
    @BindView(R.id.btn_zfb)
    Button btn_zfb;
    @BindView(R.id.btn_wx)
    Button btn_wx;

    private IWXAPI api;

    NetworkController getWXPayOrderController;
    NetworkController getALIPayOrderController;
    NetworkController updatePayResultController;
    NetworkController payInfoController;
    UserInfo userInfo;
    String orderId;
    PayInfoEntity.Data payInfo;
    @Override
    protected void init() {
        super.init();
        orderId = getIntent().getStringExtra("ORDER_ID");
        userInfo = SPController.getInstance().getUserInfo();
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

        api = WXAPIFactory.createWXAPI(this, Config.PAY_APP_ID_WX);
        api.handleIntent(getIntent(), this);
        api.registerApp(Config.PAY_APP_ID_WX);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft("支付");
    }

    @Override
    protected void initView() {
        super.initView();
        getWXPayOrderController = new NetworkController();
        getALIPayOrderController = new NetworkController();
        updatePayResultController = new NetworkController();
        payInfoController = new NetworkController();
        getWXPayOrderController.attachView(getWXPAYOrderView);
        getALIPayOrderController.attachView(getALIPAYOrderView);
        updatePayResultController.attachView(updatePayResultView);
        payInfoController.attachView(getPayInfo);

        btn_zfb.setOnClickListener(this);
        btn_wx.setOnClickListener(this);

        initPayInfo();
        initVerifyCodeView();
    }

    private void initVerifyCodeView() {
        vcv_code.setClickable(false);
        vcv_code.setEndEvent(new VerifyCodeView.OnEndListener() {
            @Override
            public void onEndEvent() {
                UIUtils.showToastSafe("支付时间已过，请重新下单");
                finish();
            }
        });
    }

    private void initPayInfo() {
        GetPayInfoRequest request = new GetPayInfoRequest();
        GetPayInfoRequest.Query query = new GetPayInfoRequest.Query();
        query.setOrderID(orderId);
        query.setApiId("HC060006");
        request.setQuery(query);

        payInfoController.sendRequest(HttpAction.ACTION_GET_ORDER_PRICE, request);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                UIUtils.showToastSafe("支付成功");
                updatePayResult("1");
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                UIUtils.showToastSafe("支付失败");
                updatePayResult("0");
            }
        };
    };

    private NormalView<PayInfoEntity> getPayInfo = new NormalView<PayInfoEntity>() {
        @Override
        public void onSuccess(PayInfoEntity entity) {
            payInfo = entity.getData().get(0);
            tv_money.setText("¥" + payInfo.getOrderPrice());
            vcv_code.setMaxTime(payInfo.getTime());
            vcv_code.startToCountDown();
        }

        @Override
        public void showLoading() {
            showProcess("正在请求数据...");
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
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

    private NormalView<WXPayOrderEntity> getWXPAYOrderView = new NormalView<WXPayOrderEntity>() {
        @Override
        public void onSuccess(WXPayOrderEntity entity) {
            WXPayOrderEntity.Data data = entity.getData().get(0);
            goToWXPay(data);
        }

        @Override
        public void showLoading() {
            showProcess("正在请求数据...");
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
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

    private NormalView<BaseEntity<String>> getALIPAYOrderView = new NormalView<BaseEntity<String>>() {
        @Override
        public void onSuccess(BaseEntity<String> entity) {
            String data = entity.getData();
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    PayTask alipay = new PayTask(PayActivity.this);
                    Map<String, String> result = alipay.payV2(data, true);
                    Log.i("msp", result.toString());

                    Message msg = new Message();
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }

        @Override
        public void showLoading() {
            showProcess("正在请求数据...");
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
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

    private NormalView<BaseEntity<String>> updatePayResultView = new NormalView<BaseEntity<String>>() {
        @Override
        public void onSuccess(BaseEntity<String> entity) {
            String data = entity.getData();
        }

        @Override
        public void showLoading() {
            showProcess("正在请求数据...");
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
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

    private void goToWXPay(WXPayOrderEntity.Data data) {
        PayReq request = new PayReq();
        request.appId = data.getAppid();
        request.partnerId = data.getPartnerid();
        request.prepayId= data.getPrepayid();
        request.packageValue = data.getPackageX();
        request.nonceStr= data.getNoncestr();
        request.timeStamp= data.getTimestamp();
        request.sign= data.getSign();
        api.sendReq(request);
    }

    private void getPayOrder(String type) {

        PayOrderRequest request = new PayOrderRequest();
        PayOrderRequest.Query query = new PayOrderRequest.Query();
        query.setApiId("HC020308");
        query.setID(orderId);
        query.setIDS(type);
        query.setCustomerID(userInfo.getUserId());
        query.setIsNeedInsurance("0");
        query.setNameMan("0");
        query.setNameWoman("0");
        query.setIdcardMan("0");
        query.setIdcardWoman("0");
        request.setQuery(query);

        switch (type) {
            case "1":
                getALIPayOrderController.sendRequest(HttpAction.ACTION_GET_ALI_PAY_ORDER, request);
                break;
            case "2":
                getWXPayOrderController.sendRequest(HttpAction.ACTION_GET_WX_PAY_ORDER, request);
                break;
            default:
                UIUtils.showToastSafe("不支持该支付方式");
                break;
        }
    }

    private void updatePayResult(String result) {
        OrderPayResultRequest request = new OrderPayResultRequest();
        OrderPayResultRequest.Query query = new OrderPayResultRequest.Query();
        query.setApiId("HC020601");
        query.setOrderId(orderId);
        query.setReslut(result);
        request.setQuery(query);

        updatePayResultController.sendRequest(HttpAction.ACTION_UPDATE_PAY_RESULT, request);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_zfb:
                getPayOrder("1");
                break;
            case R.id.btn_wx:
                getPayOrder("2");
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d(TAG, "onPayFinish, errCode = " + baseResp.errCode);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            switch (baseResp.errCode) {
                case 0:
                    UIUtils.showToastSafe("支付成功");
                    updatePayResult("1");
                    break;
                case -2:
                    UIUtils.showToastSafe("已取消支付");
                    updatePayResult("0");
                    break;
                default:
                    UIUtils.showToastSafe("支付失败");
                    updatePayResult("0");
                    break;
            }
        }
    }
}
