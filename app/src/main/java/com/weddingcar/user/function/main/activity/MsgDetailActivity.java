package com.weddingcar.user.function.main.activity;

import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.dao.Notification;
import com.weddingcar.user.common.utils.StringUtils;

public class MsgDetailActivity extends BaseActivity{
    private WebView webView;
    private WebSettings settings;
    private ScrollView sv_content;
    private TextView tv_content;
    private ImageView iv_content;
    private Notification notification;

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_msg_detail);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft("消息详情");
    }

    @Override
    protected void initView() {
        super.initView();
        notification = (Notification) getIntent().getSerializableExtra("NOTIFICATION");
        webView = (WebView) findViewById(R.id.web_notification);
        sv_content = (ScrollView) findViewById(R.id.sv_content);
        tv_content = (TextView) findViewById(R.id.tv_content);
        iv_content = (ImageView) findViewById(R.id.iv_content);

        showContent();
    }

    /**
     * 根据url展示不同内容
     *
     */
    private void showContent() {

        String content = StringUtils.checkString(notification.getContent());
        String picUrl = StringUtils.checkString(notification.getPicUrl());
        String url = StringUtils.checkString(notification.getUrl());

        webView.setVisibility(url.isEmpty()?View.GONE:View.VISIBLE);
        sv_content.setVisibility(url.isEmpty()?View.VISIBLE:View.GONE);
        iv_content.setVisibility(picUrl.isEmpty()?View.GONE:View.VISIBLE);
        if (StringUtils.isEmpty(url)) {
            tv_content.setText(String.valueOf("　　"+content));
            if(!picUrl.isEmpty())
                Glide.with(this).load(picUrl).into(iv_content);
        } else {
            loadWeb(url);
        }
    }

    /**
     * 加载url
     *
     * @param webUrls 网址
     */
    private void loadWeb(String webUrls) {
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); //如果访问的页面中有Javascript，则WebView必须设置支持Javascript
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //支持手势缩放
        settings.setDisplayZoomControls(false); //是否显示缩放按钮
        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true); //自适应屏幕
        settings.setDomStorageEnabled(true);
        settings.setSaveFormData(true);
        settings.setSupportMultipleWindows(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); //优先使用缓存
        webView.setHorizontalScrollbarOverlay(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        webView.requestFocus();
        webView.loadUrl(webUrls);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                hideErrorPage();
                webView.setVisibility(View.GONE);
                sv_content.setVisibility(View.VISIBLE);
                tv_content.setText("消息内容加载失败,请重试...");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();//返回上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 把系统自身请求失败时的网页隐藏
     */
    protected void hideErrorPage() {
        LinearLayout webParentView = (LinearLayout) webView.getParent();
        while (webParentView.getChildCount() > 1) {
            webParentView.removeViewAt(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
