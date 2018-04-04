package com.kyle.crawler.service;

import com.kyle.crawler.entity.CarType;

import java.util.List;

public interface ICarTypeService {
    void addCarType(CarType carType);
    CarType findById(Integer id);
    List<CarType> findListByCarBrandId(Integer carBrandId);
    List<CarType> findListCarType();
}
