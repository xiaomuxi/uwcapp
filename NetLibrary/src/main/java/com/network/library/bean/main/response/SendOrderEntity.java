package com.network.library.bean.main.response;

import com.network.library.bean.BaseEntity;

import java.util.List;

public class SendOrderEntity extends BaseEntity<List<SendOrderEntity.Data>> {

    public static class Data {
        private String OrderId;

        public String getOrderId() {
            return OrderId;
        }

        public void setOrderId(String orderId) {
            OrderId = orderId;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "OrderId='" + OrderId + '\'' +
                    '}';
        }
    }
}
