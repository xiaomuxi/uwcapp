package com.weddingcar.user.function.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.network.library.bean.main.response.CarGroupEntity;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.adapter.MyBaseAdapter;
import com.weddingcar.user.common.config.Config;
import com.weddingcar.user.common.utils.UIUtils;

public class CarGroupAdapter extends MyBaseAdapter<CarGroupEntity.Data>{

    public CarGroupAdapter(Context c) {
        super(c);
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.item_car_group_list;
    }

    @Override
    protected View getView(int position, View convertView, ViewGroup parent, ViewHolder holder) {
        CarGroupEntity.Data item = getItem(position);

        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        ImageView iv_car = (ImageView) convertView.findViewById(R.id.iv_car);
//        ImageView iv_car1 = (ImageView) convertView.findViewById(R.id.iv_car1);
//        ImageView iv_car2 = (ImageView) convertView.findViewById(R.id.iv_car2);
        TextView tv_car1 = (TextView) convertView.findViewById(R.id.tv_car1);
        TextView tv_car2 = (TextView) convertView.findViewById(R.id.tv_car2);

        tv_title.setText(mContext.getResources().getString(R.string.text_car_group_title, item.getName(), item.getPriceReference()+""));
        tv_car1.setText(mContext.getResources().getString(R.string.text_car_group_main, item.getCarHeadName(), item.getCarHeadCount()+""));
        tv_car2.setText(mContext.getResources().getString(R.string.text_car_group_sub, item.getCarFollowName(), item.getCarFollowCount()+""));

        RequestOptions options = new RequestOptions();
        Glide.with(UIUtils.getContext()).load(Config.getCarBrandsBaseUrl() + item.getImagePathMain()).apply(options).into(iv_car);
        return convertView;
    }
}
