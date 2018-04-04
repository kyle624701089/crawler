package com.kyle.crawler.service.impl;

import com.kyle.crawler.dao.CarTypeMapper;
import com.kyle.crawler.entity.CarType;
import com.kyle.crawler.service.ICarTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarTypeServiceImpl implements ICarTypeService {
    @Resource
    private CarTypeMapper mapper;

    @Override
    public void addCarType(CarType carType) {
        mapper.addCarType(carType);
    }

    @Override
    public CarType findById(Integer id) {
        return mapper.findById(id);
    }

    @Override
    public List<CarType> findListByCarBrandId(Integer carBrandId) {
        return mapper.findListByCarBrandId(carBrandId);
    }

    @Override
    public List<CarType> findListCarType() {
        return mapper.findListCarType();
    }
}
