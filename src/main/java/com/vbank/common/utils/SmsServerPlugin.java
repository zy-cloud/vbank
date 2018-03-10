package com.vbank.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
 
/**
*短信API服务调用代码 
*在线接口文档：http://www.juhe.cn/docs/54
**/
 
public class SmsServerPlugin {
	private static final Logger logger = Logger.getLogger(SmsServerPlugin.class);
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
 
    //配置您申请的KEY
    public static final String APPKEY ="a63c030123019b6f2306faba6e7ca247";
    public static final String QUERYKEY ="58ccb26b7b22ee6a1b58571cf53868e1";
 
    //1.屏蔽词检查测
    public static void checkSmsMsg(){
        String result =null;
        String url ="http://v.juhe.cn/sms/black";//请求接口地址
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
            params.put("word","");//需要检测的短信内容，需要UTF8 URLENCODE
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("error_code")==0){
                logger.info(object.get("result").toString());
            }else{
            	logger.info(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //2.发送短信
    public static String sendSmsCode(String phone , Map<String,Object> data){
        String result =null;
        String url ="http://v.juhe.cn/sms/send";//请求接口地址
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
            params.put("mobile",phone);//接收短信的手机号码
            params.put("tpl_id","2");//短信模板ID，请参考个人中心短信模板设置
            params.put("tpl_value", urlencode(data));//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
            params.put("dtype","json");//返回数据的格式,xml或json，默认json
 
        try {
            result =net(url, params, "GET");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return result;
    }
    //2.发送异常短信
    public static String sendExceptions(String phone , Map<String,Object> data){
    	String result =null;
    	String url ="http://v.juhe.cn/sms/send";//请求接口地址
    	Map<String, Object> params = new HashMap<String, Object>();//请求参数
    	params.put("mobile",phone);//接收短信的手机号码
    	params.put("tpl_id","40343");//短信模板ID，请参考个人中心短信模板设置
    	params.put("tpl_value", urlencode(data));//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
    	params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
    	params.put("dtype","json");//返回数据的格式,xml或json，默认json
    	
    	try {
    		result =net(url, params, "GET");
    		return result;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return result;
    }
	//3.三网手机实名制认证
	public static String queryVerify(String phone,String idcard,String realName) throws Exception{
		String result =null;
		String url ="http://v.juhe.cn/telecom/query";//请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();//请求参数
		params.put("realname",realName);//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
		params.put("idcard",idcard);
		params.put("mobile",phone);
		params.put("type","");
		params.put("showid","");
		params.put("key",QUERYKEY);//应用APPKEY(应用详细页查询)
		try {
			result =net(url, params, "GET");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
	//4.发送提现预警信息
	public static String sendCashOut(String phone , Map<String,Object> data){
		String result =null;
		String url ="http://v.juhe.cn/sms/send";//请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();//请求参数
		params.put("mobile",phone);//接收短信的手机号码
		params.put("tpl_id","60828");//短信模板ID，请参考个人中心短信模板设置
		params.put("tpl_value", urlencode(data));//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
		params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
		params.put("dtype","json");//返回数据的格式,xml或json，默认json
		
		try {
			result =net(url, params, "GET");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//4.发送提现预警信息
	public static String sendTotalOut(String phone , Map<String,Object> data){
		String result =null;
		String url ="http://v.juhe.cn/sms/send";//请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();//请求参数
		params.put("mobile",phone);//接收短信的手机号码
		params.put("tpl_id","60829");//短信模板ID，请参考个人中心短信模板设置
		params.put("tpl_value", urlencode(data));//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
		params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
		params.put("dtype","json");//返回数据的格式,xml或json，默认json
		
		try {
			result =net(url, params, "GET");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
 
    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map<String, Object> params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
 
    //将map型转为请求参数型
    public static String urlencode(Map<String,Object> data) {
        StringBuilder sb = new StringBuilder();
        for (@SuppressWarnings("rawtypes") Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    /**
     * 充值超过198送杂志提示信息
     * @param phone
     * @return
     */
	public static String sendMagazineMsg(String phone) {
		String result =null;
    	String url ="http://v.juhe.cn/sms/send";//请求接口地址
    	Map<String, Object> params = new HashMap<String, Object>();//请求参数
    	params.put("mobile",phone);//接收短信的手机号码
    	params.put("tpl_id","42491");//短信模板ID，请参考个人中心短信模板设置
    	Map<String,Object> data = new HashMap<String, Object>();
    	data.put("#name#", phone);
    	params.put("tpl_value", urlencode(data));//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
    	params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
    	params.put("dtype","json");//返回数据的格式,xml或json，默认json
    	
    	try {
    		result =net(url, params, "GET");
    		return result;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return result;
	}
}