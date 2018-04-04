package com.kyle.crawler.entity;

import java.util.List;

public class CarType {
    private Integer id;
    private String name;
    private Integer carBrandId;
    private List<Car> cars;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCarBrandId() {
        return carBrandId;
    }

    public void setCarBrandId(Integer carBrandId) {
        this.carBrandId = carBrandId;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "CarType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", carBrandId=" + carBrandId +
                ", cars=" + cars +
                '}';
    }
}
