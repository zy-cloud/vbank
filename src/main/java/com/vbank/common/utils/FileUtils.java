package com.vbank.common.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.vbank.common.bean.SaveFile;



/**
 * @author malongbo
 */
public final class FileUtils {
	private static Logger log = Logger.getLogger(FileUtils.class);

    /**
     * 获取文件扩展名*
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getExtension(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i < 0) return null;

        return fileName.substring(i+1);
    }

    /**
     * 获取文件扩展名*
     * @param file 文件对象
     * @return 扩展名
     */
    public static String getExtension(File file) {
        if (file == null) return null;

        if (file.isDirectory()) return null;

        String fileName = file.getName();
        return getExtension(fileName);
    }

    /**
     * 读取文件*
     * @param filePath 文件路径
     * @return 文件对象
     */
    public static File readFile(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) return null;

        if (!file.exists()) return null;

        return file;
    }
    /**
     * 复制文件
     * @param oldFilePath 源文件路径
     * @param newFilePath 目标文件毒经
     * @return 是否成功
     */
    public static boolean copyFile(String oldFilePath,String newFilePath) {
        try {
            int byteRead = 0;
            File oldFile = new File(oldFilePath);
            if (oldFile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldFilePath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newFilePath);
                byte[] buffer = new byte[1444];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
                fs.close();
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("复制单个文件操作出错 ."+e.toString());
            e.printStackTrace();
           return false;
        }
    }

    /**
     *删除文件
     * @param filePath 文件地址
     * @return 是否成功
     */
    public static boolean delFile(String filePath) {
        return delFile(new File(filePath));
    }

    /**
     * 删除文件
     * @param file 文件对象
     * @return 是否成功
     */
    public static boolean delFile(File file) {
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * png图片转jpg*
     * @param pngImage png图片对象
     * @param jpegFile jpg图片对象
     * @return 转换是否成功
     */
    public static boolean png2jpeg(File pngImage, File jpegFile) {
        BufferedImage bufferedImage;

        try {
            bufferedImage = ImageIO.read(pngImage);

            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            ImageIO.write(bufferedImage, "jpg", jpegFile);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否是图片*
     * @param imgFile 文件对象
     * @return
     */
    public static boolean isImage(File imgFile) {
        try {
            BufferedImage image = ImageIO.read(imgFile);
            return image != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    
    /**
     * 判断文件是否是文档*
     * @param videoFile  文件对象
     * @return
     */
    public static boolean isDoc(File docFile){
    	try {

            FileType type = getType(docFile);
            
            return type == FileType.PDF || 
                    type == FileType.XLS_DOC ;
        } catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    
    
    
    /**
     * 判断文件是否是视频*
     * @param videoFile  文件对象
     * @return
     */
    public static boolean isVideo(File videoFile){
    	try {

            FileType type = getType(videoFile);
            
            return type == FileType.AVI || 
                    type == FileType.RAM || 
                    type == FileType.RM || 
                    type == FileType.MOV || 
                    type == FileType.ASF ||
                    type == FileType.MPG;
        } catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    /**
     * 根据系统当前时间，返回时间层次的文件夹结果，如：upload/2015/01/18/0.jpg
     * @return
     */
    public static String getTimeFilePath(){
    	return new SimpleDateFormat("/yyyy/MM/dd").format(new Date())+"/";
    }

    /**
     * 将文件头转换成16进制字符串
     *
     * @param src 原生byte
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 得到文件头
     *
     * @param file 文件
     * @return 文件头
     * @throws IOException
     */
    private static String getFileContent(File file) throws IOException {

        byte[] b = new byte[28];

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }


    /**
     * 判断文件类型
     *
     * @param file 文件
     * @return 文件类型
     */
    public static FileType getType(File file) throws IOException {

        String fileHead = getFileContent(file);

        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }

    /**
     * 保存上传的文件*
     * @param file 文件对象
     * @return 文件相对路径, 供请求使用
     */
    public static final String saveUploadFile(File file) {
        String saveFilePath = "";
        //表示存放在tomcat应用目录中
//        if (AppProperty.me().appPath() == 1) {
//            saveFilePath = Context.me().getRequest().getSession().getServletContext().getRealPath("/");
//        }
        //saveFilePath = saveFilePath.replace("zhuoyue_api", "zhuoyue");
        //saveFilePath += AppProperty.me().uploadRooPath();
        saveFilePath = "/usr/local/zhuoyue_uploadfiles/apifiles"; //linux服务器的物理磁盘中绝对完整路径
        //saveFilePath ="http://192.168.1.252:18080/zhuoyue/upload"; 
       
        String timeFilePath = FileUtils.getTimeFilePath();
        String urlPath = "";
	   
        if(FileUtils.isImage(file)){//保存图片
        	//urlPath = AppProperty.me().imagePath() + timeFilePath
            urlPath = "/images" + timeFilePath;
            saveFilePath += urlPath;
        }else if(FileUtils.isDoc(file)){//保存文档
        	//urlPath = AppProperty.me().docPath() + timeFilePath
            urlPath = "/docs" + timeFilePath;
            saveFilePath += urlPath;
        }else if(FileUtils.isVideo(file)){//保存视频
            urlPath = "/videos" + timeFilePath;
            saveFilePath += urlPath;
        }else{//其他文件(如果是)
            urlPath = "/others" + timeFilePath;
            saveFilePath += urlPath;
        }
        File saveFileDir = new File(saveFilePath);
        if (!saveFileDir.exists()) {
            saveFileDir.mkdirs();
        }

        //保存 文件
        String fileName = System.currentTimeMillis() + "." + getFileType(file.getName());//取时间戳重新命名
        if (FileUtils.copyFile(file.getAbsolutePath(), saveFilePath + fileName)) {
            //删掉临时文件
            file.delete();
            urlPath += fileName;
            return urlPath;
        } else {
            return null;
        }
    }
    
    
    //获取文件类型
    public static String getFileType(String fileUri){
   	 File file = new File(fileUri);
   	 String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
   	 return fileType;
   	}
    
    
    //获取Base64文件类型
    public static String getBase64FileTypePath(String base64Str){
    	String fileTypePath = "";
    	//Base64文件取出文件头，如：data:image/png;base64,
	   	//String fileType ="data:image/png;base64," //测试用
	   	String fileType = base64Str.substring(0,base64Str.indexOf(","));
	   	fileType = fileType.substring(fileType.indexOf(":")+1,fileType.indexOf("/"));

	    if(fileType.equals("image")){//保存图片
	    	fileTypePath = "/images";
	    }else if(fileType.equals("doc")){//保存文档
	    	fileTypePath = "/docs" ;
	    }else if(fileType.equals("video")){//保存视频
	    	fileTypePath = "/videos" ;
	    }else{//其他文件(如果是)
	    	fileTypePath = "/others" ;
	    }
    	  
    	return  fileTypePath;    
    }
    
    
    //获取Base64文件类型
    public static String getBase64FileType(String base64Str){
		 //Base64文件取出文件头，如：data:image/png;base64,
    	 //String fileType ="data:image/png;base64," //测试用
    	 String fileType = base64Str.substring(0,base64Str.indexOf(","));
    	 fileType = fileType.substring(fileType.indexOf("/")+1, fileType.indexOf(";"));
		 return fileType;
   	}
    
    
    /**
     * 保存上传的文件*
     * @param file 文件对象
     * @return 文件相对路径, 供请求使用
     */
	public static final SaveFile saveUploadBase64File(String base64Str) {
    	String relativePath = "";
    	String saveFilePath = "/usr/local/zhuoyue_uploadfiles/apifiles"; //linux服务器的物理磁盘中绝对完整路径
    	//String saveFilePath = "D:/usr/local/zhuoyue_uploadfiles/apifiles"; //windows本地服务器的物理磁盘中绝对完整路径    --测试用
    	
    	String timeFilePath = FileUtils.getTimeFilePath();
    	relativePath = getBase64FileTypePath(base64Str) + timeFilePath;
    	saveFilePath += relativePath;
    	File saveFileDir = new File(saveFilePath);
        if (!saveFileDir.exists()) {
            saveFileDir.mkdirs();
        }
    	
        //保存 文件
        String fileName = System.currentTimeMillis() + "." + getBase64FileType(base64Str);//取时间戳重新命名
        saveFilePath += fileName;
        try {
        	//过滤特殊字符
		    //base64Str= base64Str.trim().replace("%", "").replace(",", "").replace(" ", "+");
        	//后台解码的时候要去掉Base64的头文件
			base64Str = base64Str.replace("data:image/png;base64,", ""); 
        	base64ToFile(base64Str,saveFilePath);
        	return new SaveFile(fileName,"/zhuoyue/upload"+relativePath+fileName,true); 
        } catch (Exception e) { 
        	return new SaveFile(fileName,"",false);
            //throw new RuntimeException("base64字符串异常或地址异常\n" + e.getMessage());  
        }  

    }
    
    
    /**将Base64 转换为file文件*/  
    public static boolean base64ToFile(String base64Str, String path) {  
        byte[] buffer;  
        try {  
            buffer = Base64.decodeBase64(base64Str);  
            FileOutputStream out = new FileOutputStream(path);  
            out.write(buffer);  
            out.close();  
            return true;  
        } catch (Exception e) {  
            throw new RuntimeException("base64字符串异常或地址异常\n" + e.getMessage());  
        }  
    }  
    
    
    /**
	 * 获取html全部内容
	 * @param url
	 * @return
	 */
	public static String getContentByHtml(URL url){
		StringBuffer document = new StringBuffer();
        try 
        {
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
            document.append(line + "");
            reader.close();
        }
        catch (MalformedURLException e) 
        {
            e.printStackTrace(); 
        }
        catch (IOException e)
        {
            e.printStackTrace(); 
        }
        return document.toString();
	}
	
	/**
	 * 根据url，获取html文件body标签中的内容
	 * @param url
	 * @return
	 */
	public static String getBodyByHtml(URL url){
		StringBuffer document = new StringBuffer();
		String bodyContent = "";
        try 
        {
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
            document.append(line + "");
            reader.close();
            String htmlContent = document.toString();
            bodyContent= htmlContent.substring(htmlContent.indexOf("<body>")+6,htmlContent.indexOf("</body>"));
        }
        catch (MalformedURLException e) 
        {
            e.printStackTrace(); 
        }
        catch (IOException e)
        {
            e.printStackTrace(); 
        }
        
        return bodyContent;
	}
     
}

