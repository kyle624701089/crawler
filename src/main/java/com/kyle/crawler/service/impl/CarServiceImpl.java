package com.kyle.crawler.service.impl;

import com.kyle.crawler.dao.CarMapper;
import com.kyle.crawler.entity.Car;
import com.kyle.crawler.service.ICarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarServiceImpl implements ICarService {
    @Resource
    private CarMapper mapper;

    @Override
    public void addCar(Car car) {
        mapper.addCar(car);
    }

    @Override
    public List<Car> findListCar() {
        return mapper.findListCar();
    }

    @Override
    public List<Car> findListCarByCarTypeId(Integer id) {
        return mapper.findListCarByCarTypeId(id);
    }

    @Override
    public Car findById(Integer id) {
        return mapper.findById(id);
    }
}
