package com.kyle.crawler.service;

import com.kyle.crawler.entity.ChargeStation;

import java.util.List;

/**
 * @Author: ${user}
 * @Description:
 * @Date: ${Time} ${Date}
 */
public interface IChargeStationService {
    void insert(ChargeStation chargeStation);

    List<ChargeStation> getList();

    ChargeStation getOneByStation(ChargeStation chargeStation);

    void update(ChargeStation chargeStation);
}
