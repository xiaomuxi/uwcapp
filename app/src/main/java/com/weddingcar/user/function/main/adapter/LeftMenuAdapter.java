package com.weddingcar.user.function.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weddingcar.user.R;
import com.weddingcar.user.common.base.adapter.MyBaseAdapter;
import com.weddingcar.user.function.main.bean.LeftMenuItem;

public class LeftMenuAdapter extends MyBaseAdapter<LeftMenuItem>{

    public LeftMenuAdapter(Context c) {
        super(c);
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.item_left_menu;
    }

    @Override
    protected View getView(int position, View convertView, ViewGroup parent, ViewHolder holder) {
        LeftMenuItem item = getItem(position);
        TextView tv_menu = (TextView) convertView.findViewById(R.id.tv_menu);
        tv_menu.setText(item.getName());
        tv_menu.setCompoundDrawables(mContext.getResources().getDrawable(item.getDrawableResId()), null, null, null);

        return convertView;
    }
}
