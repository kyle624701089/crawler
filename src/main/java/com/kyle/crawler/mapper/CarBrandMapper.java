package com.kyle.crawler.mapper;

import com.kyle.crawler.entity.CarBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarBrandMapper {
    void addCarBrand(CarBrand carBrand);
    List<CarBrand> findListCarBrand();
}
