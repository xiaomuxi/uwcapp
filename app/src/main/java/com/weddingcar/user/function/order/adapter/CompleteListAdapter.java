package com.weddingcar.user.function.order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.network.library.bean.order.response.OrderListEntity;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.adapter.MyBaseAdapter;
import com.weddingcar.user.common.config.Config;
import com.weddingcar.user.common.ui.CircleImageView;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CompleteListAdapter extends MyBaseAdapter<OrderListEntity.Data>{

    public CompleteListAdapter(Context c) {
        super(c);
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.item_calling_list;
    }

    @Override
    protected View getView(int position, View convertView, ViewGroup parent, ViewHolder holder) {
        TextView tv_order_num = (TextView) convertView.findViewById(R.id.tv_order_num);
        TextView tv_username = (TextView) convertView.findViewById(R.id.tv_username);
        CircleImageView iv_header = (CircleImageView) convertView.findViewById(R.id.iv_header);
        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
        TextView tv_car_brand = (TextView) convertView.findViewById(R.id.tv_car_brand);
        TextView tv_hours = (TextView) convertView.findViewById(R.id.tv_hours);
        TextView tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
        TextView tv_area = (TextView) convertView.findViewById(R.id.tv_area);
        TextView tv_car_type = (TextView) convertView.findViewById(R.id.tv_car_type);
        LinearLayout ll_car_count = (LinearLayout) convertView.findViewById(R.id.ll_car_count);
        TextView tv_enroll_car_count = (TextView) convertView.findViewById(R.id.tv_enroll_car_count);

        OrderListEntity.Data item = getItem(position);
        tv_order_num.setText(mContext.getResources().getString(R.string.text_item_order_num, item.getCode()));
        tv_username.setText(StringUtils.isEmpty(item.getGroomName())?"--":item.getGroomName());
        RequestOptions options = new RequestOptions();
        Glide.with(UIUtils.getContext()).load(Config.getUserAvatorBaseUrl() + item.getImagePathMain()).apply(options).into(iv_header);
        tv_date.setText(getTime(item.getTheWeddingDateString()));
        tv_car_brand.setText(StringUtils.checkString(item.getCarBrandName())+StringUtils.checkString(item.getCarModelName()));
        tv_hours.setText(mContext.getResources().getString(R.string.text_car_hours, item.getHourChoose()+""));
        tv_distance.setText(mContext.getResources().getString(R.string.text_car_km, item.getJourneyChoose()+""));
        tv_area.setText(item.getAreaName());
        tv_car_type.setSelected(StringUtils.equals(item.getIscar(), "1"));
        tv_enroll_car_count.setText(StringUtils.equals("已签到", item.getState()) ? "查看签到车辆" :"填写集合信息");
        return convertView;
    }


    private String getTime(String timestamp) {//可根据需要自行截取数据显示
        long time;
        try {
            time = Long.parseLong(timestamp);
        } catch (Exception e) {
            return "--";
        }

        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

}
