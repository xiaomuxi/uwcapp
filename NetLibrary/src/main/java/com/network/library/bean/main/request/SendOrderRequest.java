package com.network.library.bean.main.request;

import com.network.library.bean.BaseRequest;

public class SendOrderRequest extends BaseRequest<SendOrderRequest.Query, Object> {
    public static class Query {
        private String ApiId;
        private String userid;
        private String CustomerID;
        private String DEVICEID;
        private String CarModelID;
        private String CarColorID;
        private String AmountTotalReference;
        private String AmountTotal;
        private String Count;
        private String Iscar;


        public String getApiId() {
            return ApiId;
        }

        public void setApiId(String ApiId) {
            this.ApiId = ApiId;
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

        public String getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(String customerID) {
            CustomerID = customerID;
        }

        public String getCarModelID() {
            return CarModelID;
        }

        public void setCarModelID(String carModelID) {
            CarModelID = carModelID;
        }

        public String getCarColorID() {
            return CarColorID;
        }

        public void setCarColorID(String carColorID) {
            CarColorID = carColorID;
        }

        public String getAmountTotalReference() {
            return AmountTotalReference;
        }

        public void setAmountTotalReference(String amountTotalReference) {
            AmountTotalReference = amountTotalReference;
        }

        public String getAmountTotal() {
            return AmountTotal;
        }

        public void setAmountTotal(String amountTotal) {
            AmountTotal = amountTotal;
        }

        public String getCount() {
            return Count;
        }

        public void setCount(String count) {
            Count = count;
        }

        public String getIscar() {
            return Iscar;
        }

        public void setIscar(String iscar) {
            Iscar = iscar;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", userid='" + userid + '\'' +
                    ", CustomerID='" + CustomerID + '\'' +
                    ", DEVICEID='" + DEVICEID + '\'' +
                    ", CarModelID='" + CarModelID + '\'' +
                    ", CarColorID='" + CarColorID + '\'' +
                    ", AmountTotalReference='" + AmountTotalReference + '\'' +
                    ", AmountTotal='" + AmountTotal + '\'' +
                    ", Count='" + Count + '\'' +
                    ", Iscar='" + Iscar + '\'' +
                    '}';
        }
    }
}
