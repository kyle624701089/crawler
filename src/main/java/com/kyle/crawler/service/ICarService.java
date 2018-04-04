package com.kyle.crawler.service;

import com.kyle.crawler.entity.Car;

import java.util.List;

public interface ICarService {
    void addCar(Car car);
    List<Car> findListCar();
    List<Car> findListCarByCarTypeId(Integer id);
    Car findById(Integer id);
}
