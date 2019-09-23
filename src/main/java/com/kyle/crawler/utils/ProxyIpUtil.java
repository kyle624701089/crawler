package com.kyle.crawler.utils;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    public void getProxyIps() {
        WebClient webClient = new WebClient();
        try {
            HtmlPage page = webClient.getPage(TARGET_URL);
            webClient.waitForBackgroundJavaScript(1000L);
            Document document = Jsoup.parse(page.asXml());
            Element ipList = document.getElementById("ip_list");
            Elements trs = ipList.select("tr");
            for (Element tr : trs) {
                Elements tds = tr.select("td");
                for (Element td : tds) {
                    String text = td.text();
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }finally {
            webClient.close();
        }
    }

    public static void main(String[] args) {
        ProxyIpUtil proxyIpUtil = new ProxyIpUtil();
        proxyIpUtil.getProxyIps();
    }
}
