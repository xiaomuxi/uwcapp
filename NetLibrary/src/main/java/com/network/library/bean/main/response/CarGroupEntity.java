package com.network.library.bean.main.response;

import com.network.library.bean.BaseEntity;

import java.io.Serializable;
import java.util.List;

public class CarGroupEntity extends BaseEntity<List<CarGroupEntity.Data>>{

    public static class Data implements Serializable {

        /**
         * ID : PK1700032
         * CarFollowID : CX00067
         * ImagePathFollow : PK170003218120311023259.PNG
         * Name : 奔驰S+奥迪A6L
         * ImagePathMain : PK170003218120311023257.PNG
         * CarHeadCount : 1
         * CarHeadName : 奔驰S级
         * CarFollowCount : 5
         * CarFollowName : 奥迪A6L
         * PriceReference : 3500.0
         * CarHeadID : CX00079
         */

        private String ID;
        private String CarFollowID;
        private String ImagePathFollow;
        private String Name;
        private String ImagePathMain;
        private int CarHeadCount;
        private String CarHeadName;
        private int CarFollowCount;
        private String CarFollowName;
        private double PriceReference;
        private String CarHeadID;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getCarFollowID() {
            return CarFollowID;
        }

        public void setCarFollowID(String CarFollowID) {
            this.CarFollowID = CarFollowID;
        }

        public String getImagePathFollow() {
            return ImagePathFollow;
        }

        public void setImagePathFollow(String ImagePathFollow) {
            this.ImagePathFollow = ImagePathFollow;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getImagePathMain() {
            return ImagePathMain;
        }

        public void setImagePathMain(String ImagePathMain) {
            this.ImagePathMain = ImagePathMain;
        }

        public int getCarHeadCount() {
            return CarHeadCount;
        }

        public void setCarHeadCount(int CarHeadCount) {
            this.CarHeadCount = CarHeadCount;
        }

        public String getCarHeadName() {
            return CarHeadName;
        }

        public void setCarHeadName(String CarHeadName) {
            this.CarHeadName = CarHeadName;
        }

        public int getCarFollowCount() {
            return CarFollowCount;
        }

        public void setCarFollowCount(int CarFollowCount) {
            this.CarFollowCount = CarFollowCount;
        }

        public String getCarFollowName() {
            return CarFollowName;
        }

        public void setCarFollowName(String CarFollowName) {
            this.CarFollowName = CarFollowName;
        }

        public double getPriceReference() {
            return PriceReference;
        }

        public void setPriceReference(double PriceReference) {
            this.PriceReference = PriceReference;
        }

        public String getCarHeadID() {
            return CarHeadID;
        }

        public void setCarHeadID(String CarHeadID) {
            this.CarHeadID = CarHeadID;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "ID='" + ID + '\'' +
                    ", CarFollowID='" + CarFollowID + '\'' +
                    ", ImagePathFollow='" + ImagePathFollow + '\'' +
                    ", Name='" + Name + '\'' +
                    ", ImagePathMain='" + ImagePathMain + '\'' +
                    ", CarHeadCount=" + CarHeadCount +
                    ", CarHeadName='" + CarHeadName + '\'' +
                    ", CarFollowCount=" + CarFollowCount +
                    ", CarFollowName='" + CarFollowName + '\'' +
                    ", PriceReference=" + PriceReference +
                    ", CarHeadID='" + CarHeadID + '\'' +
                    '}';
        }
    }
}
