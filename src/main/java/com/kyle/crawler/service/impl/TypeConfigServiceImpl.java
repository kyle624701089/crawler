package com.kyle.crawler.service.impl;

import com.kyle.crawler.dao.TypeConfigMapper;
import com.kyle.crawler.entity.TypeConfig;
import com.kyle.crawler.service.ITypeConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *@ ClassName Tye
 *@ Description TODO
 *@ author sunkai-019
 *@ Date 2019/4/13 20:14
 *@ version 1.0
 **/
@Service
public class TypeConfigServiceImpl implements ITypeConfigService {
    @Resource
    private TypeConfigMapper mapper;

    @Override
    public List<TypeConfig> selectListByCode(String code) {
        return mapper.selectListByCode(code);
    }

    @Override
    public void insert(TypeConfig typeConfig) {
        mapper.insert(typeConfig);
    }

    @Override
    public TypeConfig selectByName(String name) {
        return mapper.selectByName(name);
    }

    @Override
    public List<TypeConfig> selectListByGroup(String group) {
        return mapper.selectListByGroup(group);
    }
}
