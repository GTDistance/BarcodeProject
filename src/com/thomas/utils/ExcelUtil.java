package com.thomas.utils;

import com.thomas.beans.PileCodeBean;
import com.thomas.beans.QRCodeBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Thomas.Wang on 2018/10/8.
 */
public class ExcelUtil {
    public static List<QRCodeBean> readExcel(String fileName) throws Exception{
        InputStream is = new FileInputStream(new File(fileName));
        Workbook hssfWorkbook = null;
        if (fileName.endsWith("xlsx")){
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        }else if (fileName.endsWith("xls")){
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }

        List<QRCodeBean> list = new ArrayList<QRCodeBean>();
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet <hssfWorkbook.getNumberOfSheets(); numSheet++) {

            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            String sheetName =  hssfSheet.getSheetName();
            QRCodeBean pileCodeBean = new QRCodeBean();
            pileCodeBean.setFileName(sheetName);
            if (hssfSheet == null) {
                continue;
            }
            int rowNums = hssfSheet.getLastRowNum();
            ArrayList<String> pileGunCodeList=new ArrayList<String>();

            // 循环行Row
            for (int rowNum = 1; rowNum <= rowNums; rowNum++) {
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    String code = hssfRow.getCell(0).toString();
                    if(isInteger(code)){
                        pileGunCodeList.add(code);
                    }
                }
            }
            String[] pileGunCodStr = new String[pileGunCodeList.size()];
            pileGunCodeList.toArray(pileGunCodStr);
            pileCodeBean.setPileGunCodes(pileGunCodStr);
            list.add(pileCodeBean);
        }
        return list;
    }

    /*方法二：推荐，速度最快
  * 判断是否为整数
  * @param str 传入的字符串
  * @return 是整数返回true,否则返回false
*/

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
