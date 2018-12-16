package com.network.library.bean.main.response;

import com.network.library.bean.BaseEntity;

import java.io.Serializable;
import java.util.List;

public class CarModelEntity extends BaseEntity<List<CarModelEntity.Data>>{

    public static class Data implements Serializable{


        /**
         * id : CX00066
         * ModelName : A4L
         * BrandName : 奥迪
         * VehicleType : 低档
         * Number : 0
         * ImagePathMain : CX0006618120310491978.PNG
         * PriceBaseDistance : 100.0
         * PriceBaseTimeout : 10.0
         * PriceReference : 400.0
         * BrandID : 1
         */

        private String id;
        private String ModelName;
        private String BrandName;
        private String VehicleType;
        private int Number;
        private String ImagePathMain;
        private double PriceBaseDistance;
        private double PriceBaseTimeout;
        private double PriceReference;
        private int BrandID;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModelName() {
            return ModelName;
        }

        public void setModelName(String ModelName) {
            this.ModelName = ModelName;
        }

        public String getBrandName() {
            return BrandName;
        }

        public void setBrandName(String BrandName) {
            this.BrandName = BrandName;
        }

        public String getVehicleType() {
            return VehicleType;
        }

        public void setVehicleType(String VehicleType) {
            this.VehicleType = VehicleType;
        }

        public int getNumber() {
            return Number;
        }

        public void setNumber(int Number) {
            this.Number = Number;
        }

        public String getImagePathMain() {
            return ImagePathMain;
        }

        public void setImagePathMain(String ImagePathMain) {
            this.ImagePathMain = ImagePathMain;
        }

        public double getPriceBaseDistance() {
            return PriceBaseDistance;
        }

        public void setPriceBaseDistance(double PriceBaseDistance) {
            this.PriceBaseDistance = PriceBaseDistance;
        }

        public double getPriceBaseTimeout() {
            return PriceBaseTimeout;
        }

        public void setPriceBaseTimeout(double PriceBaseTimeout) {
            this.PriceBaseTimeout = PriceBaseTimeout;
        }

        public double getPriceReference() {
            return PriceReference;
        }

        public void setPriceReference(double PriceReference) {
            this.PriceReference = PriceReference;
        }

        public int getBrandID() {
            return BrandID;
        }

        public void setBrandID(int BrandID) {
            this.BrandID = BrandID;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", ModelName='" + ModelName + '\'' +
                    ", BrandName='" + BrandName + '\'' +
                    ", VehicleType='" + VehicleType + '\'' +
                    ", Number=" + Number +
                    ", ImagePathMain='" + ImagePathMain + '\'' +
                    ", PriceBaseDistance=" + PriceBaseDistance +
                    ", PriceBaseTimeout=" + PriceBaseTimeout +
                    ", PriceReference=" + PriceReference +
                    ", BrandID=" + BrandID +
                    '}';
        }
    }
}
