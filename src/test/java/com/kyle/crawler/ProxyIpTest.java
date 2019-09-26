package com.kyle.crawler;

import com.kyle.crawler.service.ProxyIpService;
import com.kyle.crawler.utils.ProxyIpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 *@ ClassName ProxyIpTest
 *@ Description TODO
 *@ author sunkai-019
 *@ Date 2019/9/26 20:47
 *@ version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class ProxyIpTest {
    @Resource
    private ProxyIpService proxyIpService;

    @Test
    public void test() {
        ProxyIpUtil proxyIpUtil = new ProxyIpUtil();
        proxyIpUtil.getProxyIps(proxyIpService);
    }
}
