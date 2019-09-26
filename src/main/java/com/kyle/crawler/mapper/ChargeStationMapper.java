package com.kyle.crawler.mapper;

import com.kyle.crawler.entity.ChargeStation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: ${user}
 * @Description:
 * @Date: ${Time} ${Date}
 */
@Mapper
public interface ChargeStationMapper {

    void insert(ChargeStation chargeStation);

    List<ChargeStation> getList();

    void update(ChargeStation chargeStation);
}
