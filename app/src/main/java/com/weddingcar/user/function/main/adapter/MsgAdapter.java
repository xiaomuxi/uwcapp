package com.weddingcar.user.function.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weddingcar.user.R;
import com.weddingcar.user.common.base.adapter.MyBaseAdapter;
import com.weddingcar.user.common.dao.Notification;

public class MsgAdapter extends MyBaseAdapter<Notification>{

    public MsgAdapter(Context c) {
        super(c);
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.item_msg_list;
    }

    @Override
    protected View getView(int position, View convertView, ViewGroup parent, ViewHolder holder) {
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        TextView tv_content = (TextView) convertView.findViewById(R.id.tv_content);
        Notification item = getItem(position);
        tv_title.setText(item.getTitle());
        tv_time.setText(item.getCreateTime());
        tv_content.setText(item.getContent());

        return convertView;
    }
}
