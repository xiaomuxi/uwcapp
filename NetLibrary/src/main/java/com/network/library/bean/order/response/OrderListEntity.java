package com.network.library.bean.order.response;

import com.network.library.bean.BaseEntity;

import java.io.Serializable;
import java.util.List;

public class OrderListEntity extends BaseEntity<List<OrderListEntity.Data>>{
    public static class Data implements Serializable{

        /**
         * TheWeddingDateString : 1544716800000
         * ColorName : 黑色
         * JourneyChoose : 10.0
         * HourChoose : 1.0
         * AreaName : 松江区
         * OderId : OR17000839
         * groomName : 尹
         * Iscar : 1
         * ImagePathMain : CX0007918120311000394.PNG
         * Code : 2018001205
         * State : 召集中
         * StateString : 召集中
         * CarBrandName : 奔驰
         * CarModelName : S级
         * Count : 1
         * CountSignUp : 0
         */

        private String TheWeddingDateString;
        private String ColorName;
        private double JourneyChoose;
        private double HourChoose;
        private String AreaName;
        private String OderId;
        private String groomName;
        private String Iscar;
        private String ImagePathMain;
        private String Code;
        private String State;
        private String StateString;
        private String CarBrandName;
        private String CarModelName;
        private int Count;
        private int CountSignUp;

        public String getTheWeddingDateString() {
            return TheWeddingDateString;
        }

        public void setTheWeddingDateString(String TheWeddingDateString) {
            this.TheWeddingDateString = TheWeddingDateString;
        }

        public String getColorName() {
            return ColorName;
        }

        public void setColorName(String ColorName) {
            this.ColorName = ColorName;
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

        public String getOderId() {
            return OderId;
        }

        public void setOderId(String OderId) {
            this.OderId = OderId;
        }

        public String getGroomName() {
            return groomName;
        }

        public void setGroomName(String groomName) {
            this.groomName = groomName;
        }

        public String getIscar() {
            return Iscar;
        }

        public void setIscar(String Iscar) {
            this.Iscar = Iscar;
        }

        public String getImagePathMain() {
            return ImagePathMain;
        }

        public void setImagePathMain(String ImagePathMain) {
            this.ImagePathMain = ImagePathMain;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getStateString() {
            return StateString;
        }

        public void setStateString(String StateString) {
            this.StateString = StateString;
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

        public int getCountSignUp() {
            return CountSignUp;
        }

        public void setCountSignUp(int CountSignUp) {
            this.CountSignUp = CountSignUp;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "TheWeddingDateString='" + TheWeddingDateString + '\'' +
                    ", ColorName='" + ColorName + '\'' +
                    ", JourneyChoose=" + JourneyChoose +
                    ", HourChoose=" + HourChoose +
                    ", AreaName='" + AreaName + '\'' +
                    ", OderId='" + OderId + '\'' +
                    ", groomName='" + groomName + '\'' +
                    ", Iscar='" + Iscar + '\'' +
                    ", ImagePathMain='" + ImagePathMain + '\'' +
                    ", Code='" + Code + '\'' +
                    ", State='" + State + '\'' +
                    ", StateString='" + StateString + '\'' +
                    ", CarBrandName='" + CarBrandName + '\'' +
                    ", CarModelName='" + CarModelName + '\'' +
                    ", Count=" + Count +
                    ", CountSignUp=" + CountSignUp +
                    '}';
        }
    }
}
