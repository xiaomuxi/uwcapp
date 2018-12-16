package com.network.library.bean.order.request;

import com.network.library.bean.BaseRequest;

public class DeleteOrderRequest extends BaseRequest<DeleteOrderRequest.Query, Object>{
    public static class Query {
        /**
         * ApiId : HC010303
         */

        private String ApiId;
        private String ID;
        private String CustomerID;

        public String getApiId() {
            return ApiId;
        }

        public void setApiId(String apiId) {
            ApiId = apiId;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(String customerID) {
            CustomerID = customerID;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", ID='" + ID + '\'' +
                    ", CustomerID='" + CustomerID + '\'' +
                    '}';
        }
    }
}
