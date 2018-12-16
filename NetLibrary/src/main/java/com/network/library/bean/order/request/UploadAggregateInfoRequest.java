package com.network.library.bean.order.request;

import com.network.library.bean.BaseRequest;

public class UploadAggregateInfoRequest extends BaseRequest<Object, UploadAggregateInfoRequest.Query>{

    public static class Query {
        private String ApiId;
        private String CustomerID;
        private String ID;
        private String ItemId;
        private String GatherTime;
        private String GatherCoordinateName;
        private String GatherLongitude;
        private String GatherLatitude;
        private String OrderRl;
        private String OrderRlTel;
        private String DetailedAddress;

        public String getApiId() {
            return ApiId;
        }

        public void setApiId(String apiId) {
            ApiId = apiId;
        }

        public String getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(String customerID) {
            CustomerID = customerID;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getItemId() {
            return ItemId;
        }

        public void setItemId(String itemId) {
            ItemId = itemId;
        }

        public String getGatherTime() {
            return GatherTime;
        }

        public void setGatherTime(String gatherTime) {
            GatherTime = gatherTime;
        }

        public String getGatherCoordinateName() {
            return GatherCoordinateName;
        }

        public void setGatherCoordinateName(String gatherCoordinateName) {
            GatherCoordinateName = gatherCoordinateName;
        }

        public String getGatherLongitude() {
            return GatherLongitude;
        }

        public void setGatherLongitude(String gatherLongitude) {
            GatherLongitude = gatherLongitude;
        }

        public String getGatherLatitude() {
            return GatherLatitude;
        }

        public void setGatherLatitude(String gatherLatitude) {
            GatherLatitude = gatherLatitude;
        }

        public String getOrderRl() {
            return OrderRl;
        }

        public void setOrderRl(String orderRl) {
            OrderRl = orderRl;
        }

        public String getOrderRlTel() {
            return OrderRlTel;
        }

        public void setOrderRlTel(String orderRlTel) {
            OrderRlTel = orderRlTel;
        }

        public String getDetailedAddress() {
            return DetailedAddress;
        }

        public void setDetailedAddress(String detailedAddress) {
            DetailedAddress = detailedAddress;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", CustomerID='" + CustomerID + '\'' +
                    ", ID='" + ID + '\'' +
                    ", ItemId='" + ItemId + '\'' +
                    ", GatherTime='" + GatherTime + '\'' +
                    ", GatherCoordinateName='" + GatherCoordinateName + '\'' +
                    ", GatherLongitude='" + GatherLongitude + '\'' +
                    ", GatherLatitude='" + GatherLatitude + '\'' +
                    ", OrderRl='" + OrderRl + '\'' +
                    ", OrderRlTel='" + OrderRlTel + '\'' +
                    ", DetailedAddress='" + DetailedAddress + '\'' +
                    '}';
        }
    }
}
