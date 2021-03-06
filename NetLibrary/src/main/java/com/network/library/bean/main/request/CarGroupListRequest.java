package com.network.library.bean.main.request;

import com.network.library.bean.BaseRequest;

public class CarGroupListRequest extends BaseRequest<CarGroupListRequest.Query, Object>{

    public static class Query {

        /**
         * ApiId : HC010204
         */

        private String ApiId;
        private String userid;
        private String DEVICEID;

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

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    ", userid='" + userid + '\'' +
                    ", DEVICEID='" + DEVICEID + '\'' +
                    '}';
        }
    }
}
