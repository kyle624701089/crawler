package com.kyle.crawler;

import com.kyle.crawler.entity.ChargeStation;
import com.kyle.crawler.service.IChargeStationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 *@ ClassName ChargeStationTest
 *@ Description TODO
 *@ author sunkai-019
 *@ Date 2019/4/23 18:24
 *@ version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class ChargeStationTest {
    @Resource
    private IChargeStationService chargeStationService;

    @Test
    public void saveChargeStation() throws Exception {
        File directory = new File("D:\\太保资料\\result");
        if (directory.isDirectory()) {
            String[] fileList = directory.list();
            for (int i = 0; i < fileList.length; i++) {
                File file = new File("D:\\太保资料\\result" + "\\" + fileList[i]);
                if (!file.isDirectory()) {
                    //省名称
                    String province = file.getName().split("_")[2].replace(".xls","");
                    HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
                    HSSFSheet sheet = workbook.getSheetAt(0);
                    for (int j = 0; j < sheet.getLastRowNum(); j++) {
                        HSSFRow row = sheet.getRow(j);
                        ChargeStation station = new ChargeStation();
                        HSSFCell cell0 = row.getCell(0);
                        if (cell0 != null) {
                            station.setStationName(cell0.getStringCellValue());
                        }
                        HSSFCell cell1 = row.getCell(1);
                        if (cell1 != null) {
                            station.setStationAddr(cell1.getStringCellValue());
                        }
                        HSSFCell cell2 = row.getCell(2);
                        if (cell2 != null) {
                            station.setFastCount(Double.valueOf(String.valueOf(cell2.getNumericCellValue())).intValue());
                        }

                        HSSFCell cell3 = row.getCell(3);
                        if (cell3 != null) {
                            station.setSlowCount(Double.valueOf(String.valueOf(cell3.getNumericCellValue())).intValue());
                        }
                        HSSFCell cell4 = row.getCell(4);
                        if (cell4 != null) {
                            station.setJingDu(String.valueOf(cell4.getNumericCellValue()));
                        }

                        HSSFCell cell5 = row.getCell(5);
                        if (cell5 != null) {
                            station.setWeiDu(String.valueOf(cell5.getNumericCellValue()));
                        }
                        HSSFCell cell6 = row.getCell(6);
                        if (cell6 != null) {
                            station.setIsOpen(cell6.getStringCellValue());
                        }

                        HSSFCell cell7 = row.getCell(7);
                        if (cell7 != null) {
                            station.setOperation(cell7.getStringCellValue());
                        }
                        HSSFCell cell8 = row.getCell(8);
                        if (cell8 != null) {
                            station.setTestInfo(cell8.getStringCellValue());
                        }

                        HSSFCell cell9 = row.getCell(9);
                        if (cell9 != null) {
                            station.setOtherInfo(cell9.getStringCellValue());
                        }
                        HSSFCell cell10 = row.getCell(10);
                        if (cell10 != null) {
                            station.setPayType(cell10.getStringCellValue());
                        }
                        HSSFCell cell11 = row.getCell(11);
                        if (cell11 != null) {
                            if (cell11.getCellTypeEnum().equals(CellType.STRING)) {
                                station.setDianFee(cell11.getStringCellValue());
                            } else if (cell11.getCellTypeEnum().equals(CellType.NUMERIC)) {
                                station.setDianFee(String.valueOf(cell11.getNumericCellValue()));
                            }
                        }

                        HSSFCell cell12 = row.getCell(12);
                        if (cell12 != null) {
                            if (cell12.getCellTypeEnum().equals(CellType.STRING)) {
                                station.setServiceFee(cell12.getStringCellValue());
                            } else if (cell12.getCellTypeEnum().equals(CellType.NUMERIC)) {
                                station.setServiceFee(String.valueOf(cell12.getNumericCellValue()));
                            }
                        }

                        HSSFCell cell13 = row.getCell(13);
                        if (cell13 != null) {
                            station.setParkFee(cell13.getStringCellValue());
                        }
                        HSSFCell cell14 = row.getCell(14);
                        if (cell14 != null) {
                            station.setOpenTime(cell14.getStringCellValue());
                        }
                        station.setProvince(province);
//                        System.out.println(file.getName());
                        chargeStationService.insert(station);
                    }
                }
            }
        }
    }

    @Test
    public void saveCityAndArea() throws Exception {
        List<ChargeStation> list = chargeStationService.getList();
        for (ChargeStation station : list) {
            String stationAddr = station.getStationAddr().replace(station.getProvince(), "");
            if (stationAddr.contains("市") && stationAddr.indexOf("市") < stationAddr.indexOf("区")) {
                station.setCity(stationAddr.substring(0, stationAddr.indexOf("市") + 1));
                if (stationAddr.contains("区")) {
                    station.setArea(stationAddr.substring(stationAddr.indexOf("市"), stationAddr.indexOf("区") + 1));
                }
            } else if (stationAddr.contains("区")) {
                station.setArea(stationAddr.substring(0, stationAddr.indexOf("区") + 1));
            }
            System.out.println(station);
            chargeStationService.update(station);
        }
    }

    @Test
    public void changeArea() throws Exception {
        List<ChargeStation> list = chargeStationService.getList();
        for (ChargeStation station : list) {
            String province = station.getProvince();
            if ("广东省".equals(province)) {
                String stationName = station.getStationName().replace("广东省", "");
                if (stationName.contains("市") || stationName.contains("县") && StringUtils.isBlank(station.getCity())) {
                    station.setCity(stationName.substring(0, stationName.indexOf("市") + 1));
                    chargeStationService.update(station);
                }
            }
            /*if (!"北京市".equals(province) && !"天津市".equals(province) && !"重庆市".equals(province) && !"上海市".equals(province)) {
                String stationName = station.getStationName().replace(province, "");
                if (stationName.contains("市")) {
                    if (StringUtils.isBlank(station.getCity())) {
                        station.setCity(stationName.substring(0, stationName.indexOf("市") + 1));
                        System.out.println(station);
                        chargeStationService.update(station);
                    }
                }
            }*/


        }
    }
}
