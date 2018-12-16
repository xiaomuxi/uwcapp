package com.network.library.bean.main.request;

import com.network.library.bean.BaseRequest;

public class ConfirmOrderRequest extends BaseRequest<ConfirmOrderRequest.Query, Object> {

    public static class Query {
        private String ApiId;
        private String userid;
        private String CustomerID;
        private String DEVICEID;
        private String IDS;
        private String TheWeddingDateString;
        private String JourneyExpect;
        private String JourneyChoose;
        private String HourChoose;
        private String AreaID;
        private String Remark;
        private String NameTheContact;
        private String TelTheContact;
        private String NameTheContactEmergency;
        private String TelTheContactEmergency;
        private String Note;

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

        public String getDEVICEID() {
            return DEVICEID;
        }

        public void setDEVICEID(String DEVICEID) {
            this.DEVICEID = DEVICEID;
        }

        public String getIDS() {
            return IDS;
        }

        public void setIDS(String IDS) {
            this.IDS = IDS;
        }

        public String getTheWeddingDateString() {
            return TheWeddingDateString;
        }

        public void setTheWeddingDateString(String theWeddingDateString) {
            TheWeddingDateString = theWeddingDateString;
        }

        public String getJourneyExpect() {
            return JourneyExpect;
        }

        public void setJourneyExpect(String journeyExpect) {
            JourneyExpect = journeyExpect;
        }

        public String getJourneyChoose() {
            return JourneyChoose;
        }

        public void setJourneyChoose(String journeyChoose) {
            JourneyChoose = journeyChoose;
        }

        public String getHourChoose() {
            return HourChoose;
        }

        public void setHourChoose(String hourChoose) {
            HourChoose = hourChoose;
        }

        public String getAreaID() {
            return AreaID;
        }

        public void setAreaID(String areaID) {
            AreaID = areaID;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getNameTheContact() {
            return NameTheContact;
        }

        public void setNameTheContact(String nameTheContact) {
            NameTheContact = nameTheContact;
        }

        public String getTelTheContact() {
            return TelTheContact;
        }

        public void setTelTheContact(String telTheContact) {
            TelTheContact = telTheContact;
        }

        public String getNameTheContactEmergency() {
            return NameTheContactEmergency;
        }

        public void setNameTheContactEmergency(String nameTheContactEmergency) {
            NameTheContactEmergency = nameTheContactEmergency;
        }

        public String getTelTheContactEmergency() {
            return TelTheContactEmergency;
        }

        public void setTelTheContactEmergency(String telTheContactEmergency) {
            TelTheContactEmergency = telTheContactEmergency;
        }

        public String getNote() {
            return Note;
        }

        public void setNote(String note) {
            Note = note;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", userid='" + userid + '\'' +
                    ", CustomerID='" + CustomerID + '\'' +
                    ", DEVICEID='" + DEVICEID + '\'' +
                    ", IDS='" + IDS + '\'' +
                    ", TheWeddingDateString='" + TheWeddingDateString + '\'' +
                    ", JourneyExpect='" + JourneyExpect + '\'' +
                    ", JourneyChoose='" + JourneyChoose + '\'' +
                    ", HourChoose='" + HourChoose + '\'' +
                    ", AreaID='" + AreaID + '\'' +
                    ", Remark='" + Remark + '\'' +
                    ", NameTheContact='" + NameTheContact + '\'' +
                    ", TelTheContact='" + TelTheContact + '\'' +
                    ", NameTheContactEmergency='" + NameTheContactEmergency + '\'' +
                    ", TelTheContactEmergency='" + TelTheContactEmergency + '\'' +
                    ", Note='" + Note + '\'' +
                    '}';
        }
    }
}
