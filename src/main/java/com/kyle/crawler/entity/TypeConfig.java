package com.kyle.crawler.entity;

/**
 *@ ClassName TypeConfig
 *@ Description 充电吧下拉菜单类型
 *@ author sunkai-019
 *@ Date 2019/4/13 20:04
 *@ version 1.0
 **/
public class TypeConfig {
    private Long id;
    private String name;
    private String code;
    private String group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "TypeConfig{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
