package com.kyle.crawler.dao;

import com.kyle.crawler.entity.CarType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarTypeMapper {
    void addCarType(CarType carType);
    CarType findById(@Param(value = "id") Integer id);
    List<CarType> findListByCarBrandId(@Param(value = "carBrandId")Integer carBrandId);
    List<CarType> findListCarType();
}
