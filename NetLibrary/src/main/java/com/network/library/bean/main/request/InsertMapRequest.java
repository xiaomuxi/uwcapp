package com.network.library.bean.main.request;

import com.network.library.bean.BaseRequest;

public class InsertMapRequest extends BaseRequest<InsertMapRequest.Query, Object>{
    public static class Query {
        private String ApiId;
        private String userid;
        private String CustomerID;
        private String id;
        private String No;
        private String CoordinateName;
        private String Longitude;
        private String Latitude;


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

        public String getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(String customerID) {
            CustomerID = customerID;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNo() {
            return No;
        }

        public void setNo(String no) {
            No = no;
        }

        public String getCoordinateName() {
            return CoordinateName;
        }

        public void setCoordinateName(String coordinateName) {
            CoordinateName = coordinateName;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String longitude) {
            Longitude = longitude;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String latitude) {
            Latitude = latitude;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", userid='" + userid + '\'' +
                    ", CustomerID='" + CustomerID + '\'' +
                    ", id='" + id + '\'' +
                    ", No='" + No + '\'' +
                    ", CoordinateName='" + CoordinateName + '\'' +
                    ", Longitude='" + Longitude + '\'' +
                    ", Latitude='" + Latitude + '\'' +
                    '}';
        }
    }
}
