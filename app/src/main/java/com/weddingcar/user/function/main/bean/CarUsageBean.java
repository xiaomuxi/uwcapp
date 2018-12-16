package com.weddingcar.user.function.main.bean;

import java.io.Serializable;

public class CarUsageBean implements Serializable{
    private String brandKey;
    private String brandValue;
    private String modelKey;
    private String modelValue;
    private String carType;
    private String hours;
    private String km;
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBrandKey() {
        return brandKey;
    }

    public void setBrandKey(String brandKey) {
        this.brandKey = brandKey;
    }

    public String getBrandValue() {
        return brandValue;
    }

    public void setBrandValue(String brandValue) {
        this.brandValue = brandValue;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public String getModelValue() {
        return modelValue;
    }

    public void setModelValue(String modelValue) {
        this.modelValue = modelValue;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    @Override
    public String toString() {
        return "CarUsageBean{" +
                "brandKey='" + brandKey + '\'' +
                ", brandValue='" + brandValue + '\'' +
                ", modelKey='" + modelKey + '\'' +
                ", modelValue='" + modelValue + '\'' +
                ", carType='" + carType + '\'' +
                ", hours='" + hours + '\'' +
                ", km='" + km + '\'' +
                '}';
    }
}
