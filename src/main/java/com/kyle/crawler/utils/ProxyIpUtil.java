package com.kyle.crawler.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.kyle.crawler.entity.ProxyIp;
import com.kyle.crawler.service.ProxyIpService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Iterator;

/**
 * @ ClassName: ProxyIpUtil
 * @ Description: 爬取https://www.xicidaili.com/有效ip并持久化为代理池
 * @ author: Administrator
 * @ date: 2019/9/23 22:12
 * @ version: 1.0
 **/
public class ProxyIpUtil {
    private final static String TARGET_URL = "https://www.xicidaili.com/nn";

    public void getProxyIps(ProxyIpService proxyIpService) {
        WebClient webClient = new WebClient();
        try {
            for (int j = 2; j < 100; j++) {
                HtmlPage page = webClient.getPage(TARGET_URL + "/" + j);
                webClient.waitForBackgroundJavaScript(1000L);
                Document document = Jsoup.parse(page.asXml());
                Element ipList = document.getElementById("ip_list");
                Elements trs = ipList.select("tr");
                for (Element tr : trs) {
                    Elements tds = tr.select("td");
                    ProxyIp proxyIp = new ProxyIp();
                    for (int i = 0; i < tds.size(); i++) {
                        Element td = tds.get(i);
                        String text = td.text();
                        switch (i) {
                            case 0:
                                // 国家
                                proxyIp.setCountry(td.getElementsByTag("img").attr("alt"));
                                continue;
                            case 1:
                                // ip地址
                                proxyIp.setIp(text);
                                continue;
                            case 2:
                                // 端口
                                proxyIp.setPort(text);
                                continue;
                            case 3:
                                // 服务器地址
                                proxyIp.setServerAddr(text);
                                continue;
                            case 4:
                                // 是否匿名
                                proxyIp.setNoName(text);
                                continue;
                            case 5:
                                // 类型
                                proxyIp.setServerType(text);
                                continue;
                            case 8:
                                // 存活时间
                                proxyIp.setSurviveTime(text);
                                continue;
                            case 9:
                                // 验证时间
                                proxyIp.setValidateTime(text);
                                continue;
                            default:
                                break;
                        }
                    }
                    System.out.println(proxyIp);
                    if (StringUtils.isNotEmpty(proxyIp.getIp())) {
                        proxyIpService.save(proxyIp);
                    }
                }
            }


        } catch (IOException ioe) {
            ioe.printStackTrace();
        }finally {
            webClient.close();
        }
    }
}
