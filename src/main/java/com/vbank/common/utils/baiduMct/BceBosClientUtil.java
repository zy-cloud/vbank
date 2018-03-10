package com.vbank.common.utils.baiduMct;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.UUID;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.jfinal.kit.PropKit;

public class BceBosClientUtil {
	
	private static final String ACCESS_KEY_ID = PropKit.get("bce_ak");
	private static final String SECRET_ACCESS_KEY = PropKit.get("bce_sk");
	public static final String ENDPOINT = "http://bj.bcebos.com"; 
	
	public static BosClient getBosClient() {
	    return getEndpointBosClient();
	}
	
	public static BosClient getEndpointBosClient() {
	    // 初始化一个BosClient
	    BosClientConfiguration config = new BosClientConfiguration();
	    // 设置HTTP最大连接数为10
	    config.setMaxConnections(10);

	    // 设置TCP连接超时为5000毫秒
	    config.setConnectionTimeoutInMillis(50000000);

	    // 设置Socket传输数据超时的时间为2000毫秒
	    config.setSocketTimeoutInMillis(2000000);
	    
	    config.setSocketBufferSizeInBytes(1073741824);
	    config.setStreamBufferSize(1073741824);
	    
	    config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
	    config.setEndpoint(ENDPOINT);
	    BosClient client = new BosClient(config);
	    return client;
	}
	
	
	public static String createKey() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		String key = "/sass/"+year+"/"+month+"/"+date+"/"+UUID.randomUUID().toString().replaceAll("-", "");
		return key;
	}
	
	
	/**
	 * 复制图片到百度云
	 * @param uStr
	 * @return
	 */
	public static String copyToBce(String uStr){
		try {
			URL url = new URL(uStr);
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
			uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true  
			uc.connect();  
			InputStream iputstream = uc.getInputStream();
			String bucketName = PropKit.get("bucketName");
			BosClient client = BceBosClientUtil.getBosClient();
			//根据日期创建目录
			String key = BceBosClientUtil.createKey();
			String fileType = uStr.substring(uStr.lastIndexOf("."), uStr.length());
			//上传到百度云
			key = key+fileType;
			client.putObject(bucketName,key,iputstream);
			return("http://zyweike.bj.bcebos.com"+key);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	
	/**
	 * 复制文件对象到百度云
	 * @param uStr
	 * @return
	 */
	public static String copyToBceByFile(File file){
		try {
			String bucketName = PropKit.get("bucketName");
			BosClient client = BceBosClientUtil.getBosClient();
			//根据日期创建目录
			String key = BceBosClientUtil.createKey();
			String fileName = file.getName();  
	        String fileType = fileName.substring(fileName.lastIndexOf("."));  
			key = key+fileType;
			client.putObject(bucketName,key,file); //上传到百度云
			return("http://zyweike.bj.bcebos.com"+key);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		//测试拷贝图片
		String imgUrl ="";
		//imgUrl = "http://112.74.86.119:8010" + imgUrl;			
		//imgUrl = "http://192.168.1.252:18080" + imgUrl;
		imgUrl = "http://192.168.1.252:18080" + imgUrl;
		String bceImgUrl = BceBosClientUtil.copyToBce(imgUrl);
		System.out.println("上传至百度云路径：---》" +bceImgUrl);
		
		
       
    }
	

	
}
