package com.kyle.crawler.mapper;

import com.kyle.crawler.entity.TypeConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: ${user}
 * @Description:
 * @Date: ${Time} ${Date}
 */
@Mapper
public interface TypeConfigMapper {
    void insert(TypeConfig typeConfig);

    List<TypeConfig> selectListByCode(String code);

    TypeConfig selectByName(String name);

    List<TypeConfig> selectListByGroup(String group);
}
