package com.network.library.bean.order.request;

import com.network.library.bean.BaseRequest;

public class OrderListRequest extends BaseRequest<OrderListRequest.Query, Object>{
    public static class Query {
        /**
         * ApiId : HC010302
         */

        private String ApiId;
        private String id;
        private String userid;
        private String DEVICEID;
        private String state;
        private String pageSize;
        private String pageIndex;

        public String getApiId() {
            return ApiId;
        }

        public void setApiId(String apiId) {
            ApiId = apiId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(String pageIndex) {
            this.pageIndex = pageIndex;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", id='" + id + '\'' +
                    ", userid='" + userid + '\'' +
                    ", DEVICEID='" + DEVICEID + '\'' +
                    ", state='" + state + '\'' +
                    ", pageSize='" + pageSize + '\'' +
                    ", pageIndex='" + pageIndex + '\'' +
                    '}';
        }
    }
}
