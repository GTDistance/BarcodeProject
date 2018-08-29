# BarcodeProject
## 功能描述
java的二维码生成和保存到本地以及压缩成ZIP功能
## 使用的包
- zxing

## 生成二维码
	String text = "http://www.thomas.com";
	QRCodeUtil.encode(text, "c:/me.jpg", "c:/barcode", true);//生成并保存带icon的二维码
	QRCodeUtil.encode(text, "c:/barcode", true);//生成并保存不带icon的二维码
## 文件的压缩
	String fileName = "二维码";//压缩之后的文件名
    String filePath = "c:/barcode/二维码";
    String sourceFilePath = filePath;//需要压缩的文件名
    String zipFilePath = filePath;//压缩到的文件路径
	boolean flag = FileToZip.fileToZip(sourceFilePath, zipFilePath, fileName);
	if(flag){
	    System.out.println("文件打包成功!");
	}else{
	    System.out.println("文件打包失败!");
	}
