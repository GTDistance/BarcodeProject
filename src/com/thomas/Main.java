package com.thomas;

import com.thomas.utils.FileToZip;
import com.thomas.utils.QRCodeUtil;
import com.thomas.beans.QRCodeBean;

public class Main {

    public static void main(String[] args) {
//        hlht://00000000020000002790001001.328096210/
//        String bootFilePath = "/Users/thomas/Documents/玖行/合肥项目/桩的二维码/";
        String bootFilePath = "/Users/thomas/Documents/JavaProject/BarcodeProject/桩的二维码/";



//        QRCodeBean bean = generateLiuMingChuanQRCode();//肥西县刘铭传故居充电站
        QRCodeBean bean = generateFangDaChongQRCode();//肥西县方大冲安置点充电站
//        QRCodeBean bean = generateGaoXinQuQRCode();//高新区长安汽车仓储基地充电站


        String fileName = bean.getFileName();
        String[] pileGunCodes = bean.getPileGunCodes();
        String[] texts =new String[pileGunCodes.length];
        for(int i = 0; i<pileGunCodes.length;i++){
            texts[i] = "hlht://0000000"+pileGunCodes[i]+".328096210/";
        }

        String filePath = bootFilePath+fileName;
        String sourceFilePath = filePath;
        String zipFilePath = filePath;
        try {
            QRCodeUtil.encodeHefi(texts, filePath);
            boolean flag = FileToZip.fileToZip(sourceFilePath, zipFilePath, fileName);
            if(flag){
                System.out.println("文件打包成功!");
            }else{
                System.out.println("文件打包失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static QRCodeBean generateLiuMingChuanQRCode(){
        String fileName = "肥西县刘铭传故居充电站";
                String[] pileGunCodes = {
                "0010010000020004001",
                "0010010000020005001",
                "0010010000020006001",
                "0015030000020004001",
                "0015030000020004002",
                "0015030000020004003",
                "0015030000020005001",
                "0015030000020005002",
                "0015030000020005003",
        };
        return new QRCodeBean(fileName,pileGunCodes);
    }

    private static QRCodeBean generateFangDaChongQRCode(){
        String fileName = "肥西县方大冲安置点充电站";
        String[] pileGunCodes = {
                "0010010000010001001",
                "0010010000010002001",
                "0010010000010003001",
                "0010010000010004001",
                "0010010000010005001",
                "0010010000010006001",
                "0010010000010007001",
                "0010010000010008001",
                "0010010000010009001",
                "0010010000010010001",
                "0010010000010011001",
                "0015010000010012001",
                "0015020000010013001",
                "0015020000010013002",
        };
        return new QRCodeBean(fileName,pileGunCodes);
    }

    private static QRCodeBean generateGaoXinQuQRCode(){
        String fileName = "高新区长安汽车仓储基地充电站";
        String[] pileGunCodes = {
                "0015010000030001001",
                "0015010000030002001",
                "0010010000030003001",
                "0010010000030004001",
                "0010010000030005001",
                "0010010000030006001",
                "0010010000030007001",
                "0010010000030008001",
                "0010010000030009001",
                "0010010000030010001",
                "0010010000030011001",
                "0010010000030012001",
        };
        return new QRCodeBean(fileName,pileGunCodes);
    }


}
