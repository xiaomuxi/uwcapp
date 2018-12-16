package com.network.library.bean.order.response;

import com.network.library.bean.BaseEntity;

import java.util.List;

public class OrderInfoEntity extends BaseEntity<List<OrderInfoEntity.Data>>{

    public static class Data {

        /**
         * TheWeddingDateString : 1545062400000
         * JourneyChoose : 10.0
         * HourChoose : 1.0
         * AreaName : 松江区
         * PriceBaseTimeout : 100.0
         * PriceBaseDistance : 10.0
         * OfferCount : 1
         * AmountAverage : 500.0
         * Note :
         * GatherTime :
         * GatherCoordinateName :
         * CustomerAvator : 132038618851.png
         * MapInfos : [{"Number":"1","CoordinateName":"新凯城银杏苑","Longitude":"121.238565","Latitude":"31.113781"},{"Number":"2","CoordinateName":"大眼包子(城置路店)","Longitude":"121.244385","Latitude":"31.115002"},{"Number":"3","CoordinateName":"MROHRS","Longitude":"121.237096","Latitude":"31.13671"}]
         * Drivers : []
         * Tel :
         * AmountAverages : 500.0
         * GatherCoordinateLongitude :
         * GatherCoordinateLatitude :
         * AmountAveragesj : 500.0
         * Iscar : 1
         * DetailedAddress :
         * CustomerName : 尹
         * OrderRl :
         * OrderRlTel :
         * CustomerSex : 男
         * Code : 2018001245
         * CarBrandName : 奥迪
         * CarModelName : Q5
         * Count : 1
         * ColorName : 黑色
         */

        private String TheWeddingDateString;
        private double JourneyChoose;
        private double HourChoose;
        private String AreaName;
        private double PriceBaseTimeout;
        private double PriceBaseDistance;
        private int OfferCount;
        private double AmountAverage;
        private String Note;
        private String GatherTime;
        private String GatherCoordinateName;
        private String CustomerAvator;
        private String Tel;
        private double AmountAverages;
        private String GatherCoordinateLongitude;
        private String GatherCoordinateLatitude;
        private double AmountAveragesj;
        private String Iscar;
        private String DetailedAddress;
        private String CustomerName;
        private String OrderRl;
        private String OrderRlTel;
        private String CustomerSex;
        private String Code;
        private String CarBrandName;
        private String CarModelName;
        private int Count;
        private String ColorName;
        private List<MapInfosBean> MapInfos;
        private List<?> Drivers;

        public String getTheWeddingDateString() {
            return TheWeddingDateString;
        }

        public void setTheWeddingDateString(String TheWeddingDateString) {
            this.TheWeddingDateString = TheWeddingDateString;
        }

        public double getJourneyChoose() {
            return JourneyChoose;
        }

        public void setJourneyChoose(double JourneyChoose) {
            this.JourneyChoose = JourneyChoose;
        }

        public double getHourChoose() {
            return HourChoose;
        }

        public void setHourChoose(double HourChoose) {
            this.HourChoose = HourChoose;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public double getPriceBaseTimeout() {
            return PriceBaseTimeout;
        }

        public void setPriceBaseTimeout(double PriceBaseTimeout) {
            this.PriceBaseTimeout = PriceBaseTimeout;
        }

        public double getPriceBaseDistance() {
            return PriceBaseDistance;
        }

        public void setPriceBaseDistance(double PriceBaseDistance) {
            this.PriceBaseDistance = PriceBaseDistance;
        }

        public int getOfferCount() {
            return OfferCount;
        }

        public void setOfferCount(int OfferCount) {
            this.OfferCount = OfferCount;
        }

        public double getAmountAverage() {
            return AmountAverage;
        }

        public void setAmountAverage(double AmountAverage) {
            this.AmountAverage = AmountAverage;
        }

        public String getNote() {
            return Note;
        }

        public void setNote(String Note) {
            this.Note = Note;
        }

        public String getGatherTime() {
            return GatherTime;
        }

        public void setGatherTime(String GatherTime) {
            this.GatherTime = GatherTime;
        }

        public String getGatherCoordinateName() {
            return GatherCoordinateName;
        }

        public void setGatherCoordinateName(String GatherCoordinateName) {
            this.GatherCoordinateName = GatherCoordinateName;
        }

        public String getCustomerAvator() {
            return CustomerAvator;
        }

        public void setCustomerAvator(String CustomerAvator) {
            this.CustomerAvator = CustomerAvator;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public double getAmountAverages() {
            return AmountAverages;
        }

        public void setAmountAverages(double AmountAverages) {
            this.AmountAverages = AmountAverages;
        }

        public String getGatherCoordinateLongitude() {
            return GatherCoordinateLongitude;
        }

        public void setGatherCoordinateLongitude(String GatherCoordinateLongitude) {
            this.GatherCoordinateLongitude = GatherCoordinateLongitude;
        }

        public String getGatherCoordinateLatitude() {
            return GatherCoordinateLatitude;
        }

        public void setGatherCoordinateLatitude(String GatherCoordinateLatitude) {
            this.GatherCoordinateLatitude = GatherCoordinateLatitude;
        }

        public double getAmountAveragesj() {
            return AmountAveragesj;
        }

        public void setAmountAveragesj(double AmountAveragesj) {
            this.AmountAveragesj = AmountAveragesj;
        }

        public String getIscar() {
            return Iscar;
        }

        public void setIscar(String Iscar) {
            this.Iscar = Iscar;
        }

        public String getDetailedAddress() {
            return DetailedAddress;
        }

        public void setDetailedAddress(String DetailedAddress) {
            this.DetailedAddress = DetailedAddress;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String CustomerName) {
            this.CustomerName = CustomerName;
        }

        public String getOrderRl() {
            return OrderRl;
        }

        public void setOrderRl(String OrderRl) {
            this.OrderRl = OrderRl;
        }

        public String getOrderRlTel() {
            return OrderRlTel;
        }

        public void setOrderRlTel(String OrderRlTel) {
            this.OrderRlTel = OrderRlTel;
        }

        public String getCustomerSex() {
            return CustomerSex;
        }

        public void setCustomerSex(String CustomerSex) {
            this.CustomerSex = CustomerSex;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getCarBrandName() {
            return CarBrandName;
        }

        public void setCarBrandName(String CarBrandName) {
            this.CarBrandName = CarBrandName;
        }

        public String getCarModelName() {
            return CarModelName;
        }

        public void setCarModelName(String CarModelName) {
            this.CarModelName = CarModelName;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public String getColorName() {
            return ColorName;
        }

        public void setColorName(String ColorName) {
            this.ColorName = ColorName;
        }

        public List<MapInfosBean> getMapInfos() {
            return MapInfos;
        }

        public void setMapInfos(List<MapInfosBean> MapInfos) {
            this.MapInfos = MapInfos;
        }

        public List<?> getDrivers() {
            return Drivers;
        }

        public void setDrivers(List<?> Drivers) {
            this.Drivers = Drivers;
        }

        public static class MapInfosBean {
            /**
             * Number : 1
             * CoordinateName : 新凯城银杏苑
             * Longitude : 121.238565
             * Latitude : 31.113781
             */

            private String Number;
            private String CoordinateName;
            private String Longitude;
            private String Latitude;

            public String getNumber() {
                return Number;
            }

            public void setNumber(String Number) {
                this.Number = Number;
            }

            public String getCoordinateName() {
                return CoordinateName;
            }

            public void setCoordinateName(String CoordinateName) {
                this.CoordinateName = CoordinateName;
            }

            public String getLongitude() {
                return Longitude;
            }

            public void setLongitude(String Longitude) {
                this.Longitude = Longitude;
            }

            public String getLatitude() {
                return Latitude;
            }

            public void setLatitude(String Latitude) {
                this.Latitude = Latitude;
            }

            @Override
            public String toString() {
                return "MapInfosBean{" +
                        "Number='" + Number + '\'' +
                        ", CoordinateName='" + CoordinateName + '\'' +
                        ", Longitude='" + Longitude + '\'' +
                        ", Latitude='" + Latitude + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Data{" +
                    "TheWeddingDateString='" + TheWeddingDateString + '\'' +
                    ", JourneyChoose=" + JourneyChoose +
                    ", HourChoose=" + HourChoose +
                    ", AreaName='" + AreaName + '\'' +
                    ", PriceBaseTimeout=" + PriceBaseTimeout +
                    ", PriceBaseDistance=" + PriceBaseDistance +
                    ", OfferCount=" + OfferCount +
                    ", AmountAverage=" + AmountAverage +
                    ", Note='" + Note + '\'' +
                    ", GatherTime='" + GatherTime + '\'' +
                    ", GatherCoordinateName='" + GatherCoordinateName + '\'' +
                    ", CustomerAvator='" + CustomerAvator + '\'' +
                    ", Tel='" + Tel + '\'' +
                    ", AmountAverages=" + AmountAverages +
                    ", GatherCoordinateLongitude='" + GatherCoordinateLongitude + '\'' +
                    ", GatherCoordinateLatitude='" + GatherCoordinateLatitude + '\'' +
                    ", AmountAveragesj=" + AmountAveragesj +
                    ", Iscar='" + Iscar + '\'' +
                    ", DetailedAddress='" + DetailedAddress + '\'' +
                    ", CustomerName='" + CustomerName + '\'' +
                    ", OrderRl='" + OrderRl + '\'' +
                    ", OrderRlTel='" + OrderRlTel + '\'' +
                    ", CustomerSex='" + CustomerSex + '\'' +
                    ", Code='" + Code + '\'' +
                    ", CarBrandName='" + CarBrandName + '\'' +
                    ", CarModelName='" + CarModelName + '\'' +
                    ", Count=" + Count +
                    ", ColorName='" + ColorName + '\'' +
                    ", MapInfos=" + MapInfos +
                    ", Drivers=" + Drivers +
                    '}';
        }
    }

}
