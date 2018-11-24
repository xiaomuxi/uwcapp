package com.weddingcar.user.function.main.activity;

import android.widget.EditText;

import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterpriseAccountActivity extends BaseActivity {

    @BindView(R.id.et_shop)
    EditText et_shop;
    @BindView(R.id.et_contact_name)
    EditText et_contact_name;
    @BindView(R.id.et_contact)
    EditText et_contact;

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_enterprise_account);
        ButterKnife.bind(this);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft("上传企业信息");
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
