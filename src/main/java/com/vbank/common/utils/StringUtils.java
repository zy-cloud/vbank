package com.vbank.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

import com.jfinal.kit.PropKit;


/**
 * 字符串工具类，继承lang3字符串工具类
 * @author cz
 * 2016年4月3日
 */
public final class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static String encode(String str){
		String encode=null;
		try {
			encode = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encode;
	}
	
	/**
	 * 获取UUID，去掉`-`的
	 * @return uuid
	 */
	public static String getUUID () {
		return UUID.randomUUID().toString().replace("-", "");
	}
	


	 /**
	 * 将字符串中特定模式的字符转换成map中对应的值
	 * 
	 * use: format("my name is ${name}, and i like ${like}!", {"name":"L.cm", "like": "Java"})
	 * 
	 * @param s		需要转换的字符串
	 * @param map	转换所需的键值对集合
	 * @return		转换后的字符串
	 */
	public static String format(String s, Map<String, String> map) {
		StringBuilder sb = new StringBuilder((int)(s.length() * 1.5));
		int cursor = 0;
		for (int start, end; (start = s.indexOf("${", cursor)) != -1 && (end = s.indexOf('}', start)) != -1;) {
			sb.append(s.substring(cursor, start));
			String key = s.substring(start + 2, end);
			sb.append(map.get(StringUtils.trim(key)));
			cursor = end + 1;
		}
		sb.append(s.substring(cursor, s.length()));
		return sb.toString();
	}
	


	/**
	 * 字符串格式化
	 * 
	 * use: format("my name is {0}, and i like {1}!", "L.cm", "java")
	 * 
	 * int long use {0,number,#}
	 * 
	 * @param s 
	 * @param args
	 * @return 转换后的字符串
	 */
	public static String format(String s, Object... args) {
		return MessageFormat.format(s, args);
	}
	
	/**
	 * 替换某个字符
	 * @param str
	 * @param regex
	 * @param args
	 * @return
	 */
	public static String replace(String str,String regex,String... args){
		int length = args.length;
		for (int i = 0; i < length; i++) {
			str=str.replaceFirst(regex, args[i]);
		}
		return str;
	}

	/**
	 * 除字符串中的空格、回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {  
        String dest = "";  
        if (str!=null) {  
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
            Matcher m = p.matcher(str);  
            dest = m.replaceAll("");  
        }  
        return dest;  
    }  
	
	
	
	/**
	 * 转义HTML用于安全过滤
	 * @param html
	 * @return
	 */
	public static String escapeHtml(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}
	
	
	public static String htmlEncodeByRegExp(String str) {
	    if(str.length() == 0){
	    	 return "";
	    }
	    str = str.replace("&","%26;");
	    str = str.replace("<","&lt;");
	    str = str.replace("<","&gt;");
	    str = str.replace(" ","&nbsp;");
	    str = str.replace("\'","&#39;");
	    str = str.replace("\"","&quot;");
	    	
		return str;
	}
	
	
	public static String htmlDecodeByRegExp(String str) {
	    if(str.length() == 0){
	    	 return "";
	    }
	    str = str.replace("%26;","&");
	    str = str.replace("&lt;","<");
	    str = str.replace("&gt;",">");
	    str = str.replace("&nbsp;"," ");
	    str = str.replace("&#39;","\'");
	    str = str.replace("&quot;","\"");
		return str;
	}
	 
	
	
	
	/**
	 * 追加HTML头和尾
	 * @param html
	 * @return
	 */
	public static String addToAppHtml(String str) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<!DOCTYPE html>");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta charset=\"utf-8\">");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">");
		sb.append("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">");
		sb.append("<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">");
		sb.append("<meta name=\"format-detection\" content=\"telephone=no\">");
		sb.append("<title>123</title>");
		sb.append("<meta name=\"description\" content=\"\">");
		sb.append("<meta name=\"keywords\" content=\"\">");
		sb.append("<style>");
		sb.append("* {margin:0;padding:0;font-size:14px;}");
		sb.append("li{list-style: none;}");
		sb.append("a{text-decoration: none;}");
		sb.append("body {margin:0 auto;}");
		sb.append("img{max-width:100%;}");
		sb.append("p{padding:0 10px;}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		
		sb.append(str);
		
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}
	

	/**
	 * 清理字符串，清理出某些不可见字符
	 * @param txt
	 * @return {String}
	 */
	public static String cleanChars(String txt) {
		return txt.replaceAll("[ 　	`·•�\\f\\t\\v]", "");
	}

	// 随机字符串
	private static final String _INT = "0123456789";
	private static final String _STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String _ALL = _INT + _STR;

	private static final Random RANDOM = new Random();

	/**
	 * 生成的随机数类型
	 */
	public static enum RandomType {
		INT, STRING, ALL;
	}

	/**
	 * 随机数生成
	 * @param count
	 * @return
	 */
	public static String random(int count, RandomType randomType) {
		if (count == 0) return "";
		if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		char[] buffer = new char[count];
		for (int i = 0; i < count; i++) {
			if (randomType.equals(RandomType.INT)) {
				buffer[i] = _INT.charAt(RANDOM.nextInt(_INT.length()));
			} else if (randomType.equals(RandomType.STRING)) {
				buffer[i] = _STR.charAt(RANDOM.nextInt(_STR.length()));
			}else {
				buffer[i] = _ALL.charAt(RANDOM.nextInt(_ALL.length()));
			}
		}
		return new String(buffer);
	}
	
	
	/**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password Password or other credentials to use in authenticating
     *        this username
     * @param algorithm Algorithm used to do the digest
     *
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }

        return buf.toString();
    }
    
 
    public static String getAvatarPath(String avatar) {
		if(null != avatar){
				//头像地址拼接
				if (!avatar.startsWith("http://")) {
					avatar = PropKit.get("domain")+"/zhuoyue/upload/images/student/"+avatar;
				}
			}else{
				//如果头像为空补上默认头像
				avatar = PropKit.get("domain")+"/images/student/" + "default.jpg";
		}
		
		return avatar;
    }
    
    /** 
    * 将字符串转成unicode 
    * @param str 待转字符串 
    * @return unicode字符串 
    */ 
    public static String convert(String str) 
    { 
    	str = (str == null ? "" : str); 
    	String tmp; 
    	StringBuffer sb = new StringBuffer(1000); 
    	char c; 
    	int i, j; 
    	sb.setLength(0); 
    	for (i = 0; i < str.length(); i++) 
    	{ 
    		c = str.charAt(i); 
    		sb.append("\\u"); 
    		j = (c >>>8); //取出高8位 
    		tmp = Integer.toHexString(j); 
    		if (tmp.length() == 1) 
    			sb.append("0"); 
    		sb.append(tmp); 
    		j = (c & 0xFF); //取出低8位 
    		tmp = Integer.toHexString(j); 
    		if (tmp.length() == 1) 
    			sb.append("0"); 
    		sb.append(tmp); 

    	} 
    	return (new String(sb)); 
    } 
    
	
}
