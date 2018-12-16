package com.network.library.bean.order.request;

import com.network.library.bean.BaseRequest;

public class OrderInfoRequest extends BaseRequest<OrderInfoRequest.Query, Object>{
    public static class Query {
        /**
         * ApiId : HC010303
         */

        private String ApiId;
        private String orderId;
        private String userid;
        private String DEVICEID;

        public String getApiId() {
            return ApiId;
        }

        public void setApiId(String apiId) {
            ApiId = apiId;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getDEVICEID() {
            return DEVICEID;
        }

        public void setDEVICEID(String DEVICEID) {
            this.DEVICEID = DEVICEID;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", userid='" + userid + '\'' +
                    ", DEVICEID='" + DEVICEID + '\'' +
                    '}';
        }
    }
}
