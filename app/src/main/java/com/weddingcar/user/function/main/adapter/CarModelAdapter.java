package com.weddingcar.user.function.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.network.library.bean.main.response.CarModelEntity;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.adapter.MyBaseAdapter;
import com.weddingcar.user.common.config.Config;
import com.weddingcar.user.common.utils.UIUtils;

public class CarModelAdapter extends MyBaseAdapter<CarModelEntity.Data>{

    public CarModelAdapter(Context c) {
        super(c);
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.item_car_model_list;
    }

    @Override
    protected View getView(int position, View convertView, ViewGroup parent, ViewHolder holder) {
        CarModelEntity.Data data = getItem(position);
        ImageView iv_model = (ImageView) convertView.findViewById(R.id.iv_model);
        TextView tv_model_name = (TextView) convertView.findViewById(R.id.tv_model_name);
        TextView tv_money = (TextView) convertView.findViewById(R.id.tv_money);
        TextView tv_car_count = (TextView) convertView.findViewById(R.id.tv_car_count);

        RequestOptions options = new RequestOptions();
        Glide.with(UIUtils.getContext()).load(Config.getCarModelUrl() + data.getImagePathMain()).apply(options).into(iv_model);
        tv_model_name.setText(data.getModelName());
        tv_money.setText(mContext.getResources().getString(R.string.text_car_model_money, data.getPriceReference()+""));
        tv_car_count.setText(mContext.getResources().getString(R.string.text_car_model_count, data.getNumber()+""));
        return convertView;
    }
}
