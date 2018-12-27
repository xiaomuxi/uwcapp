package com.weddingcar.user.common.app;


import android.os.StrictMode;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.network.library.utils.RetrofitUtil;
import com.weddingcar.user.common.base.BaseApplication;
import com.weddingcar.user.common.config.GlobalConfig;
import com.weddingcar.user.common.dao.GreenDaoHelper;
import com.weddingcar.user.common.manager.SPController;


/**
 * user wedding car Application
 */

public class UWCApp extends BaseApplication {

    @Override
    public void initSystems() {
        super.initSystems();
        GlobalConfig.init(this);
        SPController.getInstance().init(this);
        RetrofitUtil.getInstance(this);
        GreenDaoHelper.getInstance();

        //7.0闪退
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        initHX();
    }

    private void initHX() {
        //Kefu sdk 初始化简写方式：
        ChatClient.getInstance().init(this, new ChatClient.Options()
                .setAppkey("1144170905115168#app")
                .setTenantId("87719")
                .setConsoleLog(true)
                .showAgentInputState()
                .showVisitorWaitCount()
        );
        UIProvider.getInstance().init(this);
    }
}
