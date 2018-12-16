package com.network.library.bean.order.request;

import com.network.library.bean.BaseRequest;

public class GetPayInfoRequest extends BaseRequest<GetPayInfoRequest.Query, Object>{

    public static class Query {
        private String orderID;
        private String ApiId;

        public String getApiId() {
            return ApiId;
        }

        public void setApiId(String apiId) {
            ApiId = apiId;
        }

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "orderID='" + orderID + '\'' +
                    ", ApiId='" + ApiId + '\'' +
                    '}';
        }
    }
}
