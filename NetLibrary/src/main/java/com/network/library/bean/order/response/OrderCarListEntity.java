package com.network.library.bean.order.response;

import com.network.library.bean.BaseEntity;

import java.io.Serializable;
import java.util.List;

public class OrderCarListEntity extends BaseEntity<List<OrderCarListEntity.Data>>{

    public static class Data {

        /**
         * Count : 1
         * Model : [{"OrderOfferID":"OF1700001306","QdState":"N","DriverID":"18616270226","Avator":"1861627022613.png","Name":"张硕","Sex":"男","CarPlate":"川2·A4A4H","ColorName":"黑色","Amount":"500.00","Score":"5.0","OrderQuantity":"3"}]
         */

        private int Count;
        private List<ModelBean> Model;

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public List<ModelBean> getModel() {
            return Model;
        }

        public void setModel(List<ModelBean> Model) {
            this.Model = Model;
        }

        public static class ModelBean implements Serializable{
            /**
             * OrderOfferID : OF1700001306
             * QdState : N
             * DriverID : 18616270226
             * Avator : 1861627022613.png
             * Name : 张硕
             * Sex : 男
             * CarPlate : 川2·A4A4H
             * ColorName : 黑色
             * Amount : 500.00
             * Score : 5.0
             * OrderQuantity : 3
             */

            private String OrderOfferID;
            private String QdState;
            private String DriverID;
            private String Avator;
            private String Name;
            private String Sex;
            private String CarPlate;
            private String ColorName;
            private String Amount;
            private String Score;
            private String OrderQuantity;

            public String getOrderOfferID() {
                return OrderOfferID;
            }

            public void setOrderOfferID(String OrderOfferID) {
                this.OrderOfferID = OrderOfferID;
            }

            public String getQdState() {
                return QdState;
            }

            public void setQdState(String QdState) {
                this.QdState = QdState;
            }

            public String getDriverID() {
                return DriverID;
            }

            public void setDriverID(String DriverID) {
                this.DriverID = DriverID;
            }

            public String getAvator() {
                return Avator;
            }

            public void setAvator(String Avator) {
                this.Avator = Avator;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getSex() {
                return Sex;
            }

            public void setSex(String Sex) {
                this.Sex = Sex;
            }

            public String getCarPlate() {
                return CarPlate;
            }

            public void setCarPlate(String CarPlate) {
                this.CarPlate = CarPlate;
            }

            public String getColorName() {
                return ColorName;
            }

            public void setColorName(String ColorName) {
                this.ColorName = ColorName;
            }

            public String getAmount() {
                return Amount;
            }

            public void setAmount(String Amount) {
                this.Amount = Amount;
            }

            public String getScore() {
                return Score;
            }

            public void setScore(String Score) {
                this.Score = Score;
            }

            public String getOrderQuantity() {
                return OrderQuantity;
            }

            public void setOrderQuantity(String OrderQuantity) {
                this.OrderQuantity = OrderQuantity;
            }

            @Override
            public String toString() {
                return "ModelBean{" +
                        "OrderOfferID='" + OrderOfferID + '\'' +
                        ", QdState='" + QdState + '\'' +
                        ", DriverID='" + DriverID + '\'' +
                        ", Avator='" + Avator + '\'' +
                        ", Name='" + Name + '\'' +
                        ", Sex='" + Sex + '\'' +
                        ", CarPlate='" + CarPlate + '\'' +
                        ", ColorName='" + ColorName + '\'' +
                        ", Amount='" + Amount + '\'' +
                        ", Score='" + Score + '\'' +
                        ", OrderQuantity='" + OrderQuantity + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Data{" +
                    "Count=" + Count +
                    ", Model=" + Model +
                    '}';
        }
    }
}
