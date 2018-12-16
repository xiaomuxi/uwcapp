package com.network.library.bean.order.response;

import com.network.library.bean.BaseEntity;

import java.util.List;

public class PayInfoEntity extends BaseEntity<List<PayInfoEntity.Data>> {

    public static class Data {

        /**
         * orderPrice : 500.0
         * time : 321
         * TYPE : 待支付
         */

        private double orderPrice;
        private int time;
        private String TYPE;

        public double getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(double orderPrice) {
            this.orderPrice = orderPrice;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orderPrice=" + orderPrice +
                    ", time=" + time +
                    ", TYPE='" + TYPE + '\'' +
                    '}';
        }
    }
}
