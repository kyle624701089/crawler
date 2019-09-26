package com.kyle.crawler.service;

import com.kyle.crawler.entity.TypeConfig;

import java.util.List;

/**
 * @Author: ${user}
 * @Description:
 * @Date: ${Time} ${Date}
 */
public interface ITypeConfigService {
    void insert(TypeConfig typeConfig);

    List<TypeConfig> selectListByCode(String code);

    TypeConfig selectByName(String name);

    List<TypeConfig> selectListByGroup(String group);
}
