package com.vbank.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHAUtil {
	 /** 
     * 定义加密方式 
     */  
    private final static String KEY_SHA = "SHA";  
    private final static String KEY_SHA1 = "SHA-1";  
    /** 
     * 全局数组 
     */  
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",  
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };  
  
    /** 
     * 构造函数 
     */  
    public SHAUtil() {  
  
    }  
  
    /** 
     * SHA 加密 
     * @param data 需要加密的字节数组 
     * @return 加密之后的字节数组 
     * @throws Exception 
     */  
    public static byte[] encryptSHA(byte[] data){  
        // 创建具有指定算法名称的信息摘要  
//        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);  
        MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance(KEY_SHA1);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        // 使用指定的字节数组对摘要进行最后更新  
        sha.update(data);  
        // 完成摘要计算并返回  
        return sha.digest();  
    }  
  
    /** 
     * SHA 加密 
     * @param data 需要加密的字符串 
     * @return 加密之后的字符串 
     * @throws Exception 
     */  
    public static String encryptSHA(String data){  
        // 验证传入的字符串  
        if (null == data || data.equals("")) {  
            return "";  
        }  
        // 创建具有指定算法名称的信息摘要  
        MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance(KEY_SHA);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        // 使用指定的字节数组对摘要进行最后更新  
        sha.update(data.getBytes());  
        // 完成摘要计算  
        byte[] bytes = sha.digest();  
        // 将得到的字节数组变成字符串返回  
        return byteArrayToHexString(bytes);  
    }  
  
    /** 
     * 将一个字节转化成十六进制形式的字符串 
     * @param b 字节数组 
     * @return 字符串 
     */  
    private static String byteToHexString(byte b) {  
        int ret = b;  
        //System.out.println("ret = " + ret);  
        if (ret < 0) {  
            ret += 256;  
        }  
        int m = ret / 16;  
        int n = ret % 16;  
        return hexDigits[m] + hexDigits[n];  
    }  
  
    /** 
     * 转换字节数组为十六进制字符串 
     * @param bytes 字节数组 
     * @return 十六进制字符串 
     */  
    private static String byteArrayToHexString(byte[] bytes) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < bytes.length; i++) {  
            sb.append(byteToHexString(bytes[i]));  
        }  
        return sb.toString();  
    } 
    
    
    /**
     * 加密管理后台登录密码（规则  1.密码明文md5加密； 2.再加盐值zhuoyue@p2c；3.SHA加密）
     * @param key
     * @return
     */
    public static String encryptAdminPwd(String key) {  
    	
        return encryptSHA(key+"zhuoyue@p2c");
    } 
    
 
    
    /**
     * C端管理后台密码加密规则
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(8);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, 1024);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, 1024);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
    
    
    
    
    /** 
     * 测试方法 
     * @param args 
     */  
    public static void main(String[] args) throws Exception {  
    	String key = "admin664234";  
        System.out.println(encryptAdminPwd(MD5Util.MD5Encode(key)));  //SAAS管理后台加密密码
        //System.out.println(entryptPassword(MD5Utils.MD5(key)));  //C端管理后台加密密码
        
        
        //System.out.println(encryptSHA(key));  
    }  
    
    
    
}
