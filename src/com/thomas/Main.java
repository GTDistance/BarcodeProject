package com.thomas;

import com.thomas.utils.FileToZip;
import com.thomas.utils.QRCodeUtil;

public class Main {

    public static void main(String[] args) {
//        hlht://00000000020000002790001001.328096210/

        String bootFilePath = "/Users/thomas/Documents/玖行/合肥项目/桩的二维码/";
//        String fileName = "特来电二维码";
        String fileName = "盛宏联营";
        String filePath = bootFilePath+fileName;
        String sourceFilePath = filePath;
        String zipFilePath = filePath;

//        //桩编码和枪口号
//        String[] pileGunCodes = {
//                "0015010000020001001",
//                "0015010000020002001",
//                "0015010000020003001",
//                "0010010000020004001",
//                "0010010000020005001",
//                "0010010000020006001",
//        };
        //桩编码和枪口号
        String[] pileGunCodes = {
                "0010010000010001001"
        };
        String[] texts =new String[pileGunCodes.length];
        for(int i = 0; i<pileGunCodes.length;i++){
            texts[i] = "hlht://0000000"+pileGunCodes[i]+".328096210/";
        }

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
}
