package com.weddingcar.user.function.main.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.dao.Notification;
import com.weddingcar.user.common.dao.NotificationManager;
import com.weddingcar.user.function.main.adapter.MsgAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.tv_notification)
    TextView tv_notification;
    @BindView(R.id.tv_activity)
    TextView tv_activity;
    @BindView(R.id.tv_empty)
    TextView tv_empty;
    @BindView(R.id.lv_message)
    ListView lv_message;
    @BindView(R.id.lv_pic)
    ListView lv_pic;

    List<Notification> mDataList = new ArrayList<>();
    List<Notification> mPicList = new ArrayList<>();
    MsgAdapter msgAdapter;
    MsgAdapter activityAdapter;
    int currentIndex = 0;

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft("消息中心");
    }

    @Override
    protected void initView() {
        super.initView();
        msgAdapter = new MsgAdapter(this);
        activityAdapter = new MsgAdapter(this);
        lv_message.setAdapter(msgAdapter);
        lv_pic.setAdapter(activityAdapter);
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MessageActivity.this, MsgDetailActivity.class);
                if (currentIndex == 0) {
                    intent.putExtra("NOTIFICATION", mDataList.get(position));
                }
                else {
                    intent.putExtra("NOTIFICATION", mPicList.get(position));
                }
                startActivity(intent);
            }
        });
        tv_activity.setTextColor(getResources().getColor(R.color.text_main_gray));
      tv_notification.setTextColor(getResources().getColor(R.color.text_black));
        tv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex == 0) {
                    return;
                }
                currentIndex = 0;
                tv_activity.setTextColor(getResources().getColor(R.color.text_main_gray));
                tv_notification.setTextColor(getResources().getColor(R.color.text_black));
                initData();
            }
        });
        tv_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex == 1) {
                    return;
                }
                currentIndex = 1;
                tv_activity.setTextColor(getResources().getColor(R.color.text_black));
                tv_notification.setTextColor(getResources().getColor(R.color.text_main_gray));
                initData();
            }
        });
        initData();
    }

    private void initData() {
        if (currentIndex == 0) {
            mDataList = NotificationManager.getInstance().getNotificationList("MESSAGE");
            msgAdapter.setData(mDataList);
        }
        else {
            mPicList = NotificationManager.getInstance().getNotificationList("ACTIVITY");
            activityAdapter.setData(mPicList);
        }
        checkData();
    }

    public void checkData() {
        if (currentIndex == 0) {
            lv_message.setVisibility(mDataList.size() == 0 ? View.GONE : View.VISIBLE);
            tv_empty.setVisibility(mDataList.size() == 0 ? View.VISIBLE : View.GONE);
        }
        else {
            lv_pic.setVisibility(mPicList.size() == 0 ? View.GONE : View.VISIBLE);
            tv_empty.setVisibility(mPicList.size() == 0 ? View.VISIBLE : View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
