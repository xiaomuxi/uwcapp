package com.network.library.bean.order.request;

import com.network.library.bean.BaseRequest;

public class OrderPayResultRequest extends BaseRequest<OrderPayResultRequest.Query, Object>{
    public static class Query {
        private String ApiId;
        private String orderId;
        private String reslut;

        public String getApiId() {
            return ApiId;
        }

        public void setApiId(String apiId) {
            ApiId = apiId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getReslut() {
            return reslut;
        }

        public void setReslut(String reslut) {
            this.reslut = reslut;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", reslut='" + reslut + '\'' +
                    '}';
        }
    }
}
