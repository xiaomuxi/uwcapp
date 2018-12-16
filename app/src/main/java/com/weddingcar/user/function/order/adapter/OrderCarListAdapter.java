package com.weddingcar.user.function.order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.network.library.bean.order.response.OrderCarListEntity;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.adapter.MyBaseAdapter;
import com.weddingcar.user.common.config.Config;
import com.weddingcar.user.common.ui.CircleImageView;
import com.weddingcar.user.function.order.inter.OnCheckListener;

import java.util.HashMap;
import java.util.Map;

public class OrderCarListAdapter extends MyBaseAdapter<OrderCarListEntity.Data.ModelBean>{

    private Map<String, OrderCarListEntity.Data.ModelBean> checkedMap = new HashMap<String, OrderCarListEntity.Data.ModelBean>();
    OnCheckListener onCheckListener;
    public OrderCarListAdapter(Context c, OnCheckListener checkListener) {
        super(c);
        onCheckListener = checkListener;
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.item_order_car_list;
    }

    public Map<String, OrderCarListEntity.Data.ModelBean> getCheckMap() {
        return checkedMap;
    }

    @Override
    protected View getView(int position, View convertView, ViewGroup parent, ViewHolder holder) {
        OrderCarListEntity.Data.ModelBean item = getItem(position);

        CircleImageView iv_header = (CircleImageView) convertView.findViewById(R.id.iv_header);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_star = (TextView) convertView.findViewById(R.id.tv_star);
        TextView tv_color = (TextView) convertView.findViewById(R.id.tv_color);
        TextView tv_plate_number = (TextView) convertView.findViewById(R.id.tv_plate_number);
        TextView tv_order_count = (TextView) convertView.findViewById(R.id.tv_order_count);
        TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);
        CheckBox cb_car = (CheckBox) convertView.findViewById(R.id.cb_car);

        RequestOptions options = new RequestOptions().placeholder(R.drawable.my_head);
        Glide.with(mContext).load(Config.getUserAvatorBaseUrl() + item.getAvator()).apply(options).into(iv_header);
        tv_name.setText(item.getName());
        tv_star.setText(item.getScore());
        tv_color.setText(item.getColorName());
        tv_plate_number.setText(item.getCarPlate());
        tv_order_count.setText(item.getOrderQuantity()+"单");
        tv_price.setText("¥"+item.getAmount());
        cb_car.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("onCheckedChanged--->"+isChecked);
                if (isChecked) {
                    checkedMap.put(position+"", item);
                }
                else {
                    checkedMap.remove(position+"");
                }
                onCheckListener.onCheckEvent(isChecked);
            }
        });

        cb_car.setChecked(checkedMap.get(position+"") != null);

        return convertView;
    }
}
