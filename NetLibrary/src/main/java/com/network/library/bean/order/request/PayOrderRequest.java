package com.network.library.bean.order.request;

import com.network.library.bean.BaseRequest;

public class PayOrderRequest extends BaseRequest<PayOrderRequest.Query, Object>{
    public static class Query {

        private String ApiId;
        private String ID;
        private String IDS;
        private String CustomerID;
        private String IsNeedInsurance;
        private String NameMan;
        private String IdcardMan;
        private String NameWoman;
        private String IdcardWoman;

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

        public String getIDS() {
            return IDS;
        }

        public void setIDS(String IDS) {
            this.IDS = IDS;
        }

        public String getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(String customerID) {
            CustomerID = customerID;
        }

        public String getIsNeedInsurance() {
            return IsNeedInsurance;
        }

        public void setIsNeedInsurance(String isNeedInsurance) {
            IsNeedInsurance = isNeedInsurance;
        }

        public String getNameMan() {
            return NameMan;
        }

        public void setNameMan(String nameMan) {
            NameMan = nameMan;
        }

        public String getIdcardMan() {
            return IdcardMan;
        }

        public void setIdcardMan(String idcardMan) {
            IdcardMan = idcardMan;
        }

        public String getNameWoman() {
            return NameWoman;
        }

        public void setNameWoman(String nameWoman) {
            NameWoman = nameWoman;
        }

        public String getIdcardWoman() {
            return IdcardWoman;
        }

        public void setIdcardWoman(String idcardWoman) {
            IdcardWoman = idcardWoman;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", ID='" + ID + '\'' +
                    ", IDS='" + IDS + '\'' +
                    ", CustomerID='" + CustomerID + '\'' +
                    ", IsNeedInsurance='" + IsNeedInsurance + '\'' +
                    ", NameMan='" + NameMan + '\'' +
                    ", IdcardMan='" + IdcardMan + '\'' +
                    ", NameWoman='" + NameWoman + '\'' +
                    ", IdcardWoman='" + IdcardWoman + '\'' +
                    '}';
        }
    }
}
