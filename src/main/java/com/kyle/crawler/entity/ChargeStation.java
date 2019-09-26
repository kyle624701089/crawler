package com.kyle.crawler.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *@ ClassName ChargeStation
 *@ Description TODO
 *@ author sunkai-019
 *@ Date 2019/4/23 17:45
 *@ version 1.0
 **/
@Data
public class ChargeStation implements Serializable {
    private Long id;
    private String stationName;
    private String stationAddr;
    private Integer fastCount;
    private Integer slowCount;
    private String jingDu;
    private String weiDu;
    private String isOpen;
    private String operation;
    private String testInfo;
    private String otherInfo;
    private String payType;
    private String dianFee;
    private String serviceFee;
    private String parkFee;
    private String openTime;
    private String city;
    private String province;
    private String area;
}
