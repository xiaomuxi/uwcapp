package com.network.library.bean.main.request;

import com.network.library.bean.BaseRequest;

public class CarModelListRequest extends BaseRequest<CarModelListRequest.Query, Object>{

    public static class Query {

        /**
         * ApiId : HC050005
         */

        private String ApiId;
        private String userid;
        private String DEVICEID;
        private String bid;
        private String pageIndex;
        private String pageSize;
        private String date;

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

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(String pageIndex) {
            this.pageIndex = pageIndex;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", userid='" + userid + '\'' +
                    ", DEVICEID='" + DEVICEID + '\'' +
                    ", bid='" + bid + '\'' +
                    ", pageIndex='" + pageIndex + '\'' +
                    ", pageSize='" + pageSize + '\'' +
                    ", date='" + date + '\'' +
                    '}';
        }
    }
}
