package com.weddingcar.user.function.housekeeper.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.hyphenate.helpdesk.easeui.ui.ChatFragment;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;

public class ChartActivity extends BaseActivity {

    protected ChatFragment chatFragment = null;
    protected String toChatUsername = null;
    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //IM服务号
        assert bundle != null;

        chatFragment = new ChatFragment();
        // 传入参数
        chatFragment.setArguments(intent.getExtras());
        toChatUsername = bundle.getString("IM_NUMBER");
        setContentView(R.layout.activity_chart);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft(toChatUsername);
    }


    @Override
    protected void initView() {
        super.initView();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, chatFragment).commit();
        ChatClient.getInstance().chatManager().bindChat(toChatUsername);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onresume时，取消notification显示
        UIProvider.getInstance().getNotifier().reset();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("IM_NUMBER");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

}
