package com.kyle.crawler.service.impl;

import com.kyle.crawler.mapper.CarBrandMapper;
import com.kyle.crawler.entity.CarBrand;
import com.kyle.crawler.service.ICarBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarBrandServiceImpl implements ICarBrandService {
    @Resource
    private CarBrandMapper mapper;

    @Override
    public void addCarBrand(CarBrand carBrand) {
        mapper.addCarBrand(carBrand);
    }

    @Override
    public List<CarBrand> findListCarBrand() {
        return mapper.findListCarBrand();
    }
}
