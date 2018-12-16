package com.network.library.bean.order.request;

import com.network.library.bean.BaseRequest;

public class OrderCarListRequest extends BaseRequest<OrderCarListRequest.Query, Object>{

    public static class Query {
        private String ApiId;
        private String orderId;
        private String customerId;

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

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", customerId='" + customerId + '\'' +
                    '}';
        }
    }
}
