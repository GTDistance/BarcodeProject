package com.thomas.utils;


import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import sun.awt.SunHints;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

/**
 * 二维码工具类
 */
public class QRCodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
//    private static final int QRCODE_SIZE = 300;
//    private static final int QRCODE_SIZE = 800;
//    private static final int QRCODE_SIZE = 1000;
//    private static final int QRCODE_SIZE = 600;
    private static final int QRCODE_SIZE = 620;
//    private static final int QRCODE_SIZE = 500;
//    private static final int QRCODE_SIZE = 550;
    // LOGO宽度
//    private static final int WIDTH = 60;
//    private static final int WIDTH = 200;
    private static final int WIDTH = 160;
//    private static final int WIDTH = 150;
    // LOGO高度
//    private static final int HEIGHT = 200;
    private static final int HEIGHT = 160;
//    private static final int HEIGHT = 150;
//    private static final int HEIGHT = 60;

//    private static final int FONT_SIZE = 30;
    private static final int FONT_SIZE = 35;

    private static BufferedImage createImage(String content, String imgPath,
                                             boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath,
                                    boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//            Image image = src.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param destPath     存放目录
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath,
                              boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,
                needCompress);
        mkdirs(destPath);
        String file = new Random().nextInt(99999999) + ".jpg";
        ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
    }


    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content
     * @param imgName      图片的名字
     * @param imgPath
     * @param destPath
     * @param needCompress
     * @throws Exception
     */
    public static void encode(String content, String imgName, String imgPath, String destPath,
                              boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,
                needCompress);
        mkdirs(destPath);
        String file;
        if (imgName != null && !imgName.isEmpty()) {
            file = imgName + ".jpg";
        } else {
            file = new Random().nextInt(99999999) + ".jpg";
        }

        create("编号："+imgName, image, destPath + "/" + file, FONT_SIZE,QRCODE_SIZE, QRCODE_SIZE);
//        MatrixToImageWriter.pressText("编号："+imgName, destPath + "/" + file, image, 1, Color.black, 18, 1000, 1300);
//		ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath 存放目录
     * @author lanyuan
     * Email: mmm333zzz520@163.com
     * @date 2013-12-11 上午10:16:36
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        //当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static void encodeWithImageName(String content, String imgName, String destPath)
            throws Exception {
        QRCodeUtil.encode(content, imgName, null, destPath, false);
    }

    public static void encodeWithImageNames(String[] contents, String[] imgNames, String destPath)
            throws Exception {
        for (int i = 0; i < contents.length; i++) {
            QRCodeUtil.encode(contents[i], imgNames[i], null, destPath, false);
        }

    }

    public static void encodeHefi(String[] contents, String destPath)
            throws Exception {
        DeleteFolder.delAllFile(destPath);
        for (int i = 0; i < contents.length; i++) {
            String content = contents[i];
            String pileGunCode = content.split("hlht://")[1].split("\\.")[0].substring(7);
//			String gunCode = pileGunCode.substring(7);
            QRCodeUtil.encode(content, pileGunCode, null, destPath, false);


        }
    }
    public static void encodeHefi(String[] contents,String logoImagePath, String destPath)
            throws Exception {
        DeleteFolder.delAllFile(destPath);
        for (int i = 0; i < contents.length; i++) {
            String content = contents[i];
            String pileGunCode = content.split("hlht://")[1].split("\\.")[0].substring(7);
//			String gunCode = pileGunCode.substring(7);
            QRCodeUtil.encode(content, pileGunCode, logoImagePath, destPath, true);


        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content  内容
     * @param imgPath  LOGO地址
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath)
            throws Exception {
        QRCodeUtil.encode(content, imgPath, destPath, false);
    }

    /**
     * 生成二维码
     *
     * @param content      内容
     * @param destPath     存储地址
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String destPath,
                              boolean needCompress) throws Exception {
        QRCodeUtil.encode(content, null, destPath, needCompress);
    }

    /**
     * 生成二维码
     *
     * @param content  内容
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String destPath) throws Exception {
        QRCodeUtil.encode(content, null, destPath, false);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath,
                              OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,
                needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param output  输出流
     * @throws Exception
     */
    public static void encode(String content, OutputStream output)
            throws Exception {
        QRCodeUtil.encode(content, null, output, false);
    }

    /**
     * 解析二维码
     *
     * @param file 二维码图片
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
                image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 解析二维码
     *
     * @param path 二维码图片地址
     * @return
     * @throws Exception
     */
    public static String decode(String path) throws Exception {
        return QRCodeUtil.decode(new File(path));
    }

//	public static void main(String[] args) throws Exception {
//		String text = "http://www.yihaomen.com";
////		QRCodeUtil.encode(text, "c:/me.jpg", "c:/barcode", true);
//		QRCodeUtil.encode(text, "", "/Users/thomas/Desktop", true);
//	}

    /**
     * @param str     生产的图片文字
     * @param oldPath 原图片保存路径
     * @param newPath 新图片保存路径
     * @param width   定义生成图片宽度
     * @param height  定义生成图片高度
     * @return
     * @throws IOException
     */
    public static void create(String str, String oldPath, String newPath, int width, int height) {
        try {
            File oldFile = new File(oldPath);
            Image image = ImageIO.read(oldFile);

            File file = new File(newPath);
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bi.createGraphics();
            g2.setBackground(Color.WHITE);
            g2.clearRect(0, 0, width, height);
            g2.drawImage(image, 0, 0, width - 25, height - 25, null); //这里减去25是为了防止字和图重合
            /** 设置生成图片的文字样式 * */
            Font font = new Font("黑体", Font.BOLD, 25);
            g2.setFont(font);
            g2.setPaint(Color.BLACK);

            /** 设置字体在图片中的位置 在这里是居中* */
            FontRenderContext context = g2.getFontRenderContext();
            Rectangle2D bounds = font.getStringBounds(str, context);
            double x = (width - bounds.getWidth()) / 2;
            //double y = (height - bounds.getHeight()) / 2; //Y轴居中
            double y = (height - bounds.getHeight());
            double ascent = -bounds.getY();
            double baseY = y + ascent;

            /** 防止生成的文字带有锯齿 * */
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            /** 在图片上生成文字 * */
            g2.drawString(str, (int) x, (int) baseY);

            ImageIO.write(bi, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param str      生产的图片文字
     * @param oldImage 原图片
     * @param newPath  新图片保存路径
     * @param width    定义生成图片宽度
     * @param height   定义生成图片高度
     * @return
     * @throws IOException
     */
    public static void create(String str, Image oldImage, String newPath, int fontSize,int width, int height) {
        try {
//			File oldFile = new File(oldPath);
            Image image = oldImage;

            File file = new File(newPath);
//            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            BufferedImage bi = new BufferedImage(width, height+fontSize*4, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bi.createGraphics();
            g2.setBackground(Color.WHITE);
//            g2.clearRect(0, 0, width, height);
            g2.clearRect(0, 0, width, height+fontSize*4);
//            g2.drawImage(image, 0, 0, width - 25, height - 25, null); //这里减去25是为了防止字和图重合
            g2.drawImage(image, 0, 0, width , height, null); //这里减去25是为了防止字和图重合
            /** 设置生成图片的文字样式 * */
//            Font font = new Font("黑体", Font.BOLD, fontSize);
            Font font = new Font("宋体", Font.PLAIN, fontSize);
//            Font font = new Font("黑体", Font.BOLD, 18);
            g2.setFont(font);
            g2.setPaint(Color.BLACK);

            /** 设置字体在图片中的位置 在这里是居中* */
            FontRenderContext context = g2.getFontRenderContext();
            Rectangle2D bounds = font.getStringBounds(str, context);
            double x = (width - bounds.getWidth()) / 2;
//            double y = (height - bounds.getHeight()) / 2; //Y轴居中
//            double y = (height - bounds.getHeight());
            double y = (height );
            double ascent = -bounds.getY();
//            double baseY = y + ascent+fontSize*2;
            double baseY = y + ascent*2;

            /** 防止生成的文字带有锯齿 * */
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
//            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_OFF);
//            g2.setRenderingHint(SunHints.KEY_TEXT_ANTIALIASING, SunHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
//            g2.setRenderingHint(SunHints.KEY_STROKE_CONTROL, SunHints.VALUE_STROKE_DEFAULT);
//            g2.setRenderingHint(SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST, 140);
//            g2.setRenderingHint(SunHints.KEY_FRACTIONALMETRICS, SunHints.VALUE_FRACTIONALMETRICS_OFF);
//            g2.setRenderingHint(SunHints.KEY_RENDERING, SunHints.VALUE_RENDER_DEFAULT);

            /** 在图片上生成文字 * */
            g2.drawString(str, (int) x, (int) baseY);

            ImageIO.write(bi, FORMAT_NAME, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
