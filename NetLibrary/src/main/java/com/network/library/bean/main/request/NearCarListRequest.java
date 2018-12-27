package com.network.library.bean.main.request;

import com.network.library.bean.BaseRequest;

public class NearCarListRequest extends BaseRequest<NearCarListRequest.Query, Object>{
    public static class Query {
        String ApiId;

        public String getApiId() {
            return ApiId;
        }

        public void setApiId(String apiId) {
            ApiId = apiId;
        }

        @Override
        public String toString() {
            return "Query{" +
                    "ApiId='" + ApiId + '\'' +
                    '}';
        }
    }
}
