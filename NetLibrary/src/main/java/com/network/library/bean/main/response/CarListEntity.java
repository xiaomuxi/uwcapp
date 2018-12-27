package com.network.library.bean.main.response;

import com.network.library.bean.BaseEntity;

import java.util.List;

public class CarListEntity extends BaseEntity<List<CarListEntity.Data>>{
    public static class Data {

        /**
         * pic : images/car.png
         * time : 13023181986
         * km :
         * address : 世纪花苑二村
         * id : 世纪花苑二村
         * latitude : 30.943496
         * longitude : 121.315842
         * ATFG : Y
         * STNA : 时昌翠
         * yanse : 黑色
         * xinghao :
         */

        private String pic;
        private String time;
        private String km;
        private String address;
        private String id;
        private String latitude;
        private String longitude;
        private String ATFG;
        private String STNA;
        private String yanse;
        private String xinghao;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getKm() {
            return km;
        }

        public void setKm(String km) {
            this.km = km;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getATFG() {
            return ATFG;
        }

        public void setATFG(String ATFG) {
            this.ATFG = ATFG;
        }

        public String getSTNA() {
            return STNA;
        }

        public void setSTNA(String STNA) {
            this.STNA = STNA;
        }

        public String getYanse() {
            return yanse;
        }

        public void setYanse(String yanse) {
            this.yanse = yanse;
        }

        public String getXinghao() {
            return xinghao;
        }

        public void setXinghao(String xinghao) {
            this.xinghao = xinghao;
        }
    }
}
