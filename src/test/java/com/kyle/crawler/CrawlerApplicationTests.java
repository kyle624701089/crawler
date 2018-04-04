package com.kyle.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.kyle.crawler.entity.Car;
import com.kyle.crawler.entity.CarBrand;
import com.kyle.crawler.entity.CarType;
import com.kyle.crawler.service.ICarBrandService;
import com.kyle.crawler.service.ICarService;
import com.kyle.crawler.service.ICarTypeService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class CrawlerApplicationTests {
    @Resource
    private ICarBrandService brandService;
    @Resource
    private ICarTypeService typeService;
    @Resource
    private ICarService carService;


    /**
     * 将所有车型分类都持久化到数据库
     * @throws Exception
     */
	@Test
    public void saveCarTypes() throws Exception{
        File file = new File("E:\\IDEA_workspace\\github\\crawler\\src\\main\\resources\\所有车型id及对应名称数据.txt");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"utf-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String result = "";
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            result = result + line;
        }
        JSONArray jsonArray = JSON.parseArray(result);
        for (Object array:jsonArray) {
            //车品牌
            JSONObject carBrandJsonObject = JSON.parseObject(array.toString());
            String carBrandLetter = carBrandJsonObject.getString("LETTER");
            Integer carBrandID = carBrandJsonObject.getInteger("ID");
            String carBrandName = carBrandJsonObject.getString("NAME");
            CarBrand carBrand = new CarBrand();
            carBrand.setId(carBrandID);
            carBrand.setName(carBrandName);
            carBrand.setLetter(carBrandLetter);
            brandService.addCarBrand(carBrand);
            JSONArray carTypeArray = carBrandJsonObject.getJSONArray("LIST");
            //车型
            for (Object type:carTypeArray) {
                JSONObject carTypeJsonObject = JSON.parseObject(type.toString());
                Integer carTypeId = carTypeJsonObject.getInteger("ID");
                String carTypeName = carTypeJsonObject.getString("NAME");
                CarType carType = new CarType();
                carType.setId(carTypeId);
                carType.setName(carTypeName);
                carType.setCarBrandId(carBrandID);
                typeService.addCarType(carType);
                JSONArray carArray = carTypeJsonObject.getJSONArray("LIST");
                //车
                for (Object c:carArray) {
                    JSONObject carJsonObject = JSON.parseObject(c.toString());
                    Integer carId = carJsonObject.getInteger("ID");
                    String carName = carJsonObject.getString("NAME");
                    Car car = new Car();
                    car.setId(carId);
                    car.setName(carName);
                    car.setCarTypeId(carTypeId);
                    carService.addCar(car);
                }
            }
            System.out.println(array.toString());
        }
    }

    /**
     * 对网页进行爬取
     */
	public void html(String url,String carTypeName,HSSFWorkbook hssfWorkbook,WebClient webClient){
        long startTime = System.currentTimeMillis();
		FileOutputStream fileOutputStream = null;
        Document doc = null;
		try {
		    //需要同步块依次将网页转换成doc，否则拿到的doc都为空，网页没有生成
		    synchronized (this){
                //获取网页
//			String url = "http://price.pcauto.com.cn/sg3524/config.html";
                HtmlPage htmlPage = webClient.getPage(url);
                // 等待JS驱动dom完成获得还原后的网页
                webClient.waitForBackgroundJavaScript(1000);
                //将网页数据装换成doc
                doc=Jsoup.parse(htmlPage.asXml());
            }
			//构建map填充数据
			LinkedHashMap<String, List<String>> map = new LinkedHashMap<>();
			//获取车型名称 如：一汽-大众奥迪-奥迪A4L
			String carSerialName = doc.select("div[class=dTitW]").text();
            String[] split = carSerialName.split("-");
            int length = split.length;
            carSerialName = split[length-1];
            Elements select = doc.select("[class=carbox carbox-v2]");
			//列数+1=分类+车款
			Integer columnSize = select.size()+1;
			List<String> list1 = new ArrayList<>();
			List<String> list2 = new ArrayList<>();
			for (Element e:select) {
				String carName = e.select("div").select("a").text().trim();
				String[] carPrices = e.select("span").text().split("：");
				String price = carPrices[1];
				list1.add(carName);
				list2.add(price);
			}
			map.put("车款",list1);
			map.put("官方价",list2);
			map = sop(doc, 77, 500, 633,map);
			//创建excel文档
//			HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
			//创建一个工作页sheet
			HSSFSheet sheet = hssfWorkbook.createSheet(carSerialName);
			//定义总的行数
			Integer totalRowCount = map.size();
			Iterator<Map.Entry<String, List<String>>> iterator = map.entrySet().iterator();
			Integer i = 0;
			while (iterator.hasNext()) {
				Map.Entry<String, List<String>> next = iterator.next();
				if (i<totalRowCount){
					//定义行
					HSSFRow row = sheet.createRow(i);
					for (int j = 0; j < columnSize ; j++) {
						//给每行的每个单元格依次添加数据
						if (j==0){
							HSSFCell cell_0 = row.createCell(0);
							cell_0.setCellValue(next.getKey());
						}else {
							row.createCell(j).setCellValue(next.getValue().get(j-1));
						}
					}
				}
				i++;
			}
			//创建文件流输出excel文件
			fileOutputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\车型配置大全\\"+carTypeName+".xls");
			hssfWorkbook.write(fileOutputStream);
		} catch (Exception e) {
            e.printStackTrace();
        } finally {
//            webClient.close();
            /*try{
                fileOutputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }*/
        }
        long endTime = System.currentTimeMillis();
        System.out.println("本次操作耗时："+(endTime-startTime)+" 毫秒");
    }

	private LinkedHashMap<String,List<String>> sop(Document doc, Integer count1, Integer count2, Integer count3, LinkedHashMap<String,List<String>> map){
		for (int i = 1; i <= count1; i++) {
			Elements trs = doc.select("table").select("[id=tr_"+i+"]");
			for (Element tr:trs) {
				Elements divs = tr.select("div");
				//类型名称
				Element ele_0 = divs.get(0);
				//过滤掉分割线
				String ele_0_text = ele_0.text();
				//装载值
				List<String> list = new ArrayList<>();
				for (int j = 1; j < divs.size(); j++) {
					list.add(divs.get(j).text());
				}
				map.put(ele_0_text,list);
			}
		}

		for (int i = count2; i <= count3; i++) {
			Elements trs = doc.select("table").select("[id=tr_"+i+"]");
			for (Element tr:trs) {
				Elements divs = tr.select("div");
				//类型名称
				Element ele_0 = divs.get(0);
				//过滤掉分割线
				String ele_0_text = ele_0.text();
				//装载值
				List<String> list = new ArrayList<String>();
				for (int j = 1; j < divs.size(); j++) {
					list.add(divs.get(j).text());
				}
				map.put(ele_0_text,list);
			}
		}
		return map;
	}


	@Test
    public void htmlTest(){
        List<CarType> listCarType = typeService.findListCarType();
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        // 禁用Css，可避免自动二次請求CSS进行渲染
        webClient.getOptions().setCssEnabled(false);
        // 设置支持JavaScript。
        webClient.getOptions().setJavaScriptEnabled(true);
        // 4 启动客戶端重定向
        webClient.getOptions().setRedirectEnabled(true);
        // 5 js运行错誤時，是否拋出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 6 设置超时
        webClient.getOptions().setTimeout(50000);
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (CarType carType:listCarType) {
            //创建excel文档
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            Integer carTypeId = carType.getId();
            String carTypeName = carType.getName();
            List<Car> carList = carService.findListCarByCarTypeId(carTypeId);
            for (Car car: carList) {
                String url = "http://price.pcauto.com.cn/sg"+car.getId()+"/config.html";
                //执行线程
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        html(url,carTypeName,hssfWorkbook,webClient);
                        try{
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }



    /*@Async("asyncServiceExecutor")
    public void executeAsync(String url,String carTypeName,HSSFWorkbook hssfWorkbook,WebClient webClient){
        try{
            html(url,carTypeName,hssfWorkbook,webClient);
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/
}
