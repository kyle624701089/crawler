package com.kyle.crawler.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *@ ClassName MyBatisPludConfig
 *@ Description TODO
 *@ author sunkai-019
 *@ Date 2019/9/26 19:46
 *@ version 1.0
 **/
@Configuration
public class MyBatisPludConfig {

    /**
    * @Author: sunkai-019
    * @Description: 分页插件
    * @Date: 19:47 2019/9/26
    * @Param: []
    * @Retrun: com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
    */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
    * @Author: sunkai-019
    * @Description: SQL执行效率插件
    * @Date: 19:48 2019/9/26
    * @Param: []
    * @Retrun: com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor
    */
    @Bean
    @Profile({"dev"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
    * @Author: sunkai-019
    * @Description: 逻辑删除用
    * @Date: 19:49 2019/9/26
    * @Param: []
    * @Retrun: com.baomidou.mybatisplus.core.injector.ISqlInjector
    */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }
}
