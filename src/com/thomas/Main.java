package com.thomas;

import com.thomas.beans.QRCodeBean;
import com.thomas.utils.ExcelUtil;
import com.thomas.utils.FileToZip;
import com.thomas.utils.QRCodeUtil;

import java.util.List;

public class Main {

    private static String bootFilePath = "/Users/thomas/Documents/JavaProject/BarcodeProject/桩的二维码/";
    private static String excelPath = "/Users/thomas/Desktop/桩编码.xlsx";
    private static String logoPath = "/Users/thomas/Documents/JavaProject/BarcodeProject/image/logo.png";

    public static void main(String[] args) {
        try {
            List<QRCodeBean> qrCodeBeans = ExcelUtil.readExcel(excelPath);
            for (QRCodeBean qrCodeBean : qrCodeBeans) {
                generateQRCode(bootFilePath, qrCodeBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateQRCode(String bootFilePath, QRCodeBean bean) {
        String fileName = bean.getFileName();
        String[] pileGunCodes = bean.getPileGunCodes();
        String[] texts = new String[pileGunCodes.length];
        //hlht://00000000020000002790001001.328096210/
        for (int i = 0; i < pileGunCodes.length; i++) {
            texts[i] = "hlht://0000000" + pileGunCodes[i] + ".328096210/";
        }
        String filePath = bootFilePath + fileName;
        String sourceFilePath = filePath;
        String zipFilePath = filePath;
        try {
            QRCodeUtil.encodeHefi(texts,logoPath , filePath);
            boolean flag = FileToZip.fileToZip(sourceFilePath, zipFilePath, fileName);
            if (flag) {
                System.out.println(fileName+"文件打包成功!");
            } else {
                System.out.println(fileName+"文件打包失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//
//          String content = "http://www.changxingnanjing.com?qr_code=01900302|773f3c8f872bf34cc3fe97022720330e";
//          String content = "hlht://00000000020000002790001001.328096210/";
//
//        //存放logo的文件夹
////          String path = "E:/QRCodeImage";
////          String path = "/Users/thomas/Documents/JavaProject/BarcodeProject/image/default-logo-icon.png";
//          String path = "/Users/thomas/Documents/JavaProject/BarcodeProject/image";
//
////    public static void main(String args[]) {
//        try {
//            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//
//            @SuppressWarnings("rawtypes")
//            Map hints = new HashMap();
//
//            //设置UTF-8， 防止中文乱码
//            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//            //设置二维码四周白色区域的大小
//            hints.put(EncodeHintType.MARGIN, 2);
//            //设置二维码的容错性
//            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//
//            //width:图片完整的宽;height:图片完整的高
//            //因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
////            int width = 352;//352
////            int height = 500;//612
//            int width = 1000;//352
//            int height = 1300;//612
//
//            //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
//            BitMatrix bitMatrix = null;
//
//            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//
//
//            //qrcFile用来存放生成的二维码图片（无logo，无文字）
////            File logoFile = new File(path, "logo.jpg");
//            File logoFile = new File("/Users/thomas/Documents/JavaProject/BarcodeProject/image/logo.png");
//
//            //开始画二维码
//            BufferedImage barCodeImage = MatrixToImageWriter.writeToFile(bitMatrix, "jpg");
//
//            //在二维码中加入图片
//            LogoConfig logoConfig = new LogoConfig(); //LogoConfig中设置Logo的属性
//            BufferedImage image = MatrixToImageWriter.addLogo_QRCode(barCodeImage, logoFile, logoConfig);
//
//            int font = 18; //字体大小
//            int fontStyle = 1; //字体风格
//
//            //用来存放带有logo+文字的二维码图片
//            String newImageWithText = path + "/imageWithText.jpg";
//            //附加在图片上的文字信息
////            String text = "0 1 1 0 9 0 2 9";
//            String text = "编号：0020000002790001001";
//
//            //在二维码下方添加文字（文字居中）
//            MatrixToImageWriter.pressText(text, newImageWithText, image, fontStyle, Color.black, font, width, height);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//    public static void main(String[] args) {
//
//        try {
//            List<QRCodeBean> qrCodeBeans = ExcelUtil.readExcel("/Users/thomas/Desktop/桩编码.xlsx");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
