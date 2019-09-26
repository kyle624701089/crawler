package com.kyle.crawler.mapper;

import com.kyle.crawler.entity.Car;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarMapper {
    void addCar(Car car);
    List<Car> findListCar();
    List<Car> findListCarByCarTypeId(@Param(value = "typeId") Integer typeId);
    Car findById(@Param(value = "id")Integer id);
}
