package com.network.library.bean.order.request;

import com.network.library.bean.BaseRequest;

public class LockCarRequest extends BaseRequest<LockCarRequest.Query, Object>{
    public static class Query {
        private String ApiId;
        private String OrderOfferIDs;
        private String OrderID;
        private String Amount;

        public String getApiId() {
            return ApiId;
        }

        public void setApiId(String apiId) {
            ApiId = apiId;
        }

        public String getOrderOfferIDs() {
            return OrderOfferIDs;
        }

        public void setOrderOfferIDs(String orderOfferIDs) {
            OrderOfferIDs = orderOfferIDs;
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
                    ", OrderOfferIDs='" + OrderOfferIDs + '\'' +
                    ", OrderID='" + OrderID + '\'' +
                    ", Amount='" + Amount + '\'' +
                    '}';
        }
    }
}
