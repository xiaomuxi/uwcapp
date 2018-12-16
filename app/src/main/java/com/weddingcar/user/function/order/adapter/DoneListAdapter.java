package com.weddingcar.user.function.order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.network.library.bean.BaseEntity;
import com.network.library.bean.order.request.DeleteOrderRequest;
import com.network.library.bean.order.response.OrderListEntity;
import com.network.library.constant.HttpAction;
import com.network.library.controller.NetworkController;
import com.network.library.view.NormalView;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.adapter.MyBaseAdapter;
import com.weddingcar.user.common.config.Config;
import com.weddingcar.user.common.config.ToastConstant;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.ui.CircleImageView;
import com.weddingcar.user.common.ui.MaterialDialog;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;
import com.weddingcar.user.function.order.activity.OrderActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DoneListAdapter extends MyBaseAdapter<OrderListEntity.Data>{

    private NetworkController mController;

    public DoneListAdapter(Context c) {
        super(c);
        mController = new NetworkController();
        mController.attachView(deleteOrderView);
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
//        LinearLayout ll_pay = (LinearLayout) convertView.findViewById(R.id.ll_pay);
//        TextView tv_pay_time = (TextView) convertView.findViewById(R.id.tv_pay_time);
//        TextView tv_car_count = (TextView) convertView.findViewById(R.id.tv_car_count);

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
        if (StringUtils.equals(item.getState(), "待评价")) {
            tv_enroll_car_count.setText("进行评价");
        }
        else {
            tv_enroll_car_count.setText("删除");
            tv_enroll_car_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialDialog dialog = new MaterialDialog(mContext);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setTitle("提示");
                    dialog.setMessage("确定要删除该订单吗？");
                    dialog.setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            deleteOrder(item.getCode());
                        }
                    });
                    dialog.show();
                }
            });
        }
        return convertView;
    }

    private void deleteOrder(String orderId) {
        DeleteOrderRequest request = new DeleteOrderRequest();
        DeleteOrderRequest.Query query = new DeleteOrderRequest.Query();
        query.setApiId("HC020304");
        query.setCustomerID(SPController.getInstance().getUserInfo().getUserId());
        query.setID(orderId);
        request.setQuery(query);

        mController.sendRequest(HttpAction.ACTION_DELETE_ORDER, request);
    }

    private NormalView<BaseEntity<String>> deleteOrderView = new NormalView<BaseEntity<String>>() {
        @Override
        public void onSuccess(BaseEntity<String> entity) {
            System.out.println("delete order result");
            System.out.println(entity.toString());
            ((OrderActivity)mContext).getFragment(3).onResume();
        }

        @Override
        public void showLoading() {

        }

        @Override
        public void hideLoading() {

        }

        @Override
        public void onRequestSuccess() {

        }

        @Override
        public void onRequestError(String errorMsg, String methodName) {
            UIUtils.showToastSafe(StringUtils.isEmpty(errorMsg) ? ToastConstant.TOAST_REQUEST_ERROR : errorMsg);
        }
    };

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
