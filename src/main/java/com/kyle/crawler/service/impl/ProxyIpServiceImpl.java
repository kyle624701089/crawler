package com.kyle.crawler.service.impl;

import com.kyle.crawler.entity.ProxyIp;
import com.kyle.crawler.mapper.ProxyIpMapper;
import com.kyle.crawler.service.ProxyIpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 代理服务器信息表 服务实现类
 * </p>
 *
 * @author kyle
 * @since 2019-09-26
 */
@Service
public class ProxyIpServiceImpl extends ServiceImpl<ProxyIpMapper, ProxyIp> implements ProxyIpService {

}
