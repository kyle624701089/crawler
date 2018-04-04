package com.kyle.crawler.service;

import com.kyle.crawler.entity.CarBrand;

import java.util.List;

public interface ICarBrandService {
    void addCarBrand(CarBrand carBrand);
    List<CarBrand> findListCarBrand();
}
