package com.kyle.crawler.service.impl;

import com.kyle.crawler.mapper.ChargeStationMapper;
import com.kyle.crawler.entity.ChargeStation;
import com.kyle.crawler.service.IChargeStationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *@ ClassName ChargeStationServiceImpl
 *@ Description TODO
 *@ author sunkai-019
 *@ Date 2019/4/23 18:11
 *@ version 1.0
 **/
@Service
public class ChargeStationServiceImpl implements IChargeStationService {

    @Resource
    private ChargeStationMapper mapper;

    @Override
    public void insert(ChargeStation chargeStation) {
        mapper.insert(chargeStation);
    }

    @Override
    public List<ChargeStation> getList() {
        return mapper.getList();
    }

    @Override
    public ChargeStation getOneByStation(ChargeStation chargeStation) {
        return null;
    }

    @Override
    public void update(ChargeStation chargeStation) {
        mapper.update(chargeStation);
    }
}
