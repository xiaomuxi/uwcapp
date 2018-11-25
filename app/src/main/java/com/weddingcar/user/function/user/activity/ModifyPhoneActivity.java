package com.weddingcar.user.function.user.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.network.library.bean.user.response.RegisterEntity;
import com.network.library.bean.user.response.VerifyCodeEntity;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.ui.VerifyCodeView;
import com.weddingcar.user.common.utils.CheckUtils;
import com.weddingcar.user.common.utils.LogUtils;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyPhoneActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.tv_current_phone)
    TextView tv_current_phone;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_verify_code)
    EditText et_verify_code;
    @BindView(R.id.vcv_code)
    VerifyCodeView vcv_code;
    @BindView(R.id.btn_complete)
    Button btn_complete;

    UserInfo userInfo;
    NetworkController<NormalView> mControllerVerifyCode;
    NetworkController<NormalView> mControllerModifyPhone;
    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_modify_phone);
        ButterKnife.bind(this);
        userInfo = SPController.getInstance().getUserInfo();
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft("修改绑定手机");
    }

    @Override
    protected void initView() {
        super.initView();
        btn_complete.setOnClickListener(this);
        tv_current_phone.setText(userInfo.getUserId());
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_phone.getText().toString().length() > 0) {
                    vcv_code.setBackgroundResource(R.drawable.selector_button_verify_code_red);
                    vcv_code.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ("".equals(editable.toString())) {
                    vcv_code.setBackgroundResource(R.drawable.selector_button_verify_code_gray);
                    vcv_code.setClickable(false);
                }
            }
        });

        initVerifyCodeView();

        mControllerVerifyCode = new NetworkController<>();
        mControllerModifyPhone = new NetworkController<>();
        mControllerVerifyCode.attachView(verifyCodeView);
        mControllerModifyPhone.attachView(modifyFhoneView);
    }

    private void initVerifyCodeView() {
        vcv_code.setMaxTime(60);
        vcv_code.setClickable(false);
        vcv_code.setSendCodeListener(new VerifyCodeView.OnSendCodeListener() {
            @Override
            public void onStartSend() {
                hideKeyBoard();
                String phone = et_phone.getText().toString().trim();
                if (checkInsert(phone, null)) {
                    mControllerVerifyCode.sendVerifyCode(phone, "1");
                }
            }
        });
    }

    private NormalView<VerifyCodeEntity> verifyCodeView = new NormalView<VerifyCodeEntity>() {
        @Override
        public void onSuccess(VerifyCodeEntity entity) {
            LogUtils.i(TAG, "-------"+entity.toString());
            VerifyCodeEntity.Data data = entity.getData().get(0);
            if (data == null) {
                UIUtils.showToastSafe(ToastConstant.TOAST_SERVER_IS_BUSY);
                return;
            }
            if (!StringUtils.equals(entity.getStatus(), "1") || !StringUtils.equals(entity.getCount(), "1")) {
                UIUtils.showToastSafe(entity.getMsg());
                return;
            }

            UIUtils.showToastSafe("验证码已发送");
            vcv_code.startToCountDown();
        }

        @Override
        public void showLoading() {
            showProcess("正在请求验证码...");
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
            LogUtils.e(errorMsg);
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

    private NormalView<RegisterEntity> modifyFhoneView = new NormalView<RegisterEntity>() {
        @Override
        public void onSuccess(RegisterEntity entity) {
            RegisterEntity.Data data = entity.getData().get(0);
            if (data == null) {
                UIUtils.showToastSafe(ToastConstant.TOAST_SERVER_IS_BUSY);
                return;
            }
            if (!StringUtils.equals(entity.getStatus(), "1") || !StringUtils.equals(entity.getCount(), "1")) {
                UIUtils.showToastSafe(entity.getMsg());
                return;
            }

            UIUtils.showToastSafe("修改成功");
            finish();
        }

        @Override
        public void showLoading() {
            showProcess("正在修改账号...");
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
            LogUtils.e(errorMsg);
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

    private boolean checkInsert(String phone, String verifyCode) {
        if (phone != null) {
            if ("".equals(phone)) {
                UIUtils.showToastSafe("新手机号不能为空！");
                return false;
            }

            if (!CheckUtils.checkMobile(phone, false)) {
                UIUtils.showToastSafe("无效的手机号码！");
                return false;
            }
        }

        if (verifyCode != null && StringUtils.isEmpty(verifyCode)) {
            UIUtils.showToastSafe("请输入验证码！");
            return false;
        }

        return true;
    }

    private void modifyPhone() {
        String phone = et_phone.getText().toString().trim();
        String verifyCode = et_verify_code.getText().toString();
        if (checkInsert(phone, verifyCode)) {
            // TODO: go to modify phone
            UIUtils.showToastSafe("完成");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_complete:
                modifyPhone();
                break;
        }
    }
}
