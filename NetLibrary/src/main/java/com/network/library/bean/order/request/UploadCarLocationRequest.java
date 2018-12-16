package com.network.library.bean.order.request;

import com.network.library.bean.BaseRequest;

public class UploadCarLocationRequest extends BaseRequest<UploadCarLocationRequest.Query, Object>{

    public static class Query {
        private String ApiId;
        private String driverIDS;
        private String OrderID;
        private String Amount;

        public String getApiId() {
            return ApiId;
        }

        public void setApiId(String apiId) {
            ApiId = apiId;
        }

        public String getDriverIDS() {
            return driverIDS;
        }

        public void setDriverIDS(String driverIDS) {
            this.driverIDS = driverIDS;
        }

        public String getOrderID() {
            return OrderID;
        }

        public void setOrderID(String orderID) {
            OrderID = orderID;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", driverIDS='" + driverIDS + '\'' +
                    ", OrderID='" + OrderID + '\'' +
                    ", Amount='" + Amount + '\'' +
                    '}';
        }
    }
}
