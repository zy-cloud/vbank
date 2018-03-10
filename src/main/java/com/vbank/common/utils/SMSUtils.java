package com.vbank.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;

/**
 * @author cz
 * 2016年4月3日
 */
public class SMSUtils {
	static Log log = Log.getLog(SMSUtils.class);
	/* 短信配置资源 */
	private static final Prop prop = PropKit.use("sms.properties");
	private final static String SMS_URL="";
	/* 短信配置详情 */
	private static final String SMS_NAME 					= prop.get("name");
	private static final String SMS_PWD 					= prop.get("pwd");
	private static final String SMS_CONTENT_REGISTER_CODE 	= prop.get("content_register_code");
	private static final String SMS_CONTENT_FORGET_CODE 	= prop.get("content_forget_code");
	private static final String CONTENT_REGISTER_NOTIFY 	= prop.get("content_register_notify");
	private static final String CONTENT_ORDER_NOTIFY 	    = prop.get("content_order_notify");
	private static final String SMS_TYPE 					= prop.get("type");
	
	public enum SendSMSType {
		REGISTER,FORGET,ORDER_NOTIFY,REGISTER_NOTIFY
	}
	/**
	 * 发送短信验证码
	 * @param mobile
	 * @param code
	 * @param sendSMSType
	 * @return
	 */
	public static int SMSCode(String mobile,String code,String nickName,String courseName,String uesrMobile,String courseCount,SendSMSType sendSMSType){
		String type = sendSMSType.name().toLowerCase();
		String content="";
		int res_code = -9;
		try {
			Map<String, String> queryParas=new HashMap<String, String>();
			queryParas.put("name", SMS_NAME);
			queryParas.put("pwd", SMS_PWD);
			if (type.equals("register")) {
				content=StringUtils.replace(SMS_CONTENT_REGISTER_CODE,"@", code);
			}else if (type.equals("forget")) {
				content=StringUtils.replace(SMS_CONTENT_FORGET_CODE,"@", code);
			}else if (type.equals("order_notify")) {
				content=StringUtils.replace(CONTENT_ORDER_NOTIFY, "@", nickName,courseName,courseCount);
			}else if (type.equals("register_notify")) {
				content=StringUtils.replace(CONTENT_REGISTER_NOTIFY, "@", nickName,uesrMobile);
			}
			queryParas.put("content", content);
			queryParas.put("mobile", mobile);
			queryParas.put("type", SMS_TYPE);
			String result = HttpKit.post(SMS_URL, queryParas, "");
			log.error("发送短信返回结果："+result);
			res_code = Integer.parseInt(result.split(",")[0]);
		} catch (Exception e) {
			res_code=-10;
			log.error("send sms to "+mobile+" error:"+e.getMessage());
		}
		return res_code;
	}
	

    /**
	 * 验证手机格式 isMobileNO
	 */
	public static boolean isMobileNo(String mobiles) {

		if (mobiles.length() != 11) {
			return false;
		}

		/**
		 * 手机号码: 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[0, 1, 6, 7,
		 * 8], 18[0-9] 移动号段:
		 * 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,
		 * 184,187,188 联通号段: 130,131,132,145,152,155,156,170,171,176,185,186
		 * 电信号段: 133,134,153,170,177,180,181,189
		 */
		//String MOBILE = "^1(3[0-9]|4[0-9]|5[0-9]|7[0-9]|8[0-9])\\d{8}$";
		String MOBILE = "^1\\d{10}$";
		/**
		 * 中国移动：China Mobile
		 * 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,
		 * 184,187,188
		 */
		String CM = "^1(3[4-9]|4[7]|5[0-27-9]|7[08]|8[2-478])\\d{8}$";
		/**
		 * 中国联通：China Unicom 130,131,132,145,152,155,156,170,171,176,185,186
		 */
		String CU = "^1(3[0-2]|4[5]|5[256]|7[016]|8[56])\\d{8}$";
		/**
		 * 中国电信：China Telecom 133,134,153,170,177,180,181,189
		 */
		String CT = "^1(3[34]|53|7[07]|8[019])\\d{8}$";

		return mobiles.matches(MOBILE) || mobiles.matches(CM) || mobiles.matches(CU) || mobiles.matches(CT);

	}
    
    

    /**
     * 生成短信验证码*
     * @param length 长度
     * @return 指定长度的随机短信验证码
     */
    public static final String randomSMSCode(int length) {
        boolean numberFlag = true;
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }

    /**
     * 发送短信验证码*
     * @param mobile 手机号码
     * @param code 验证码
     * @return 是否发送成功
     */
    public static final String sendCode(String mobile, String code) {
    	// 这里实现短信发送功能
    	Map<String,Object> data = new HashMap<String, Object>();
    	data.put("#company#", "卓越微课");
//    	data.put("#app#", "卓越成长教育APP");
    	data.put("#code#", code);
    	return SmsServerPlugin.sendSmsCode(mobile,data);
    
    }
    /**
     * 发送应用异常*
     * @param mobile 手机号码
     * @param msg 消息
     * @return 是否发送成功
     */
    public static final String sendExceptions(String mobile, String msg) {
    	// 这里实现短信发送功能
    	Map<String,Object> data = new HashMap<String, Object>();
//    	data.put("#company#", "卓越微课");
//    	data.put("#app#", "卓越成长教育APP");
    	data.put("#code#", msg);
    	return SmsServerPlugin.sendExceptions(mobile,data);
    	
    }
    /**
     * 发送提现预警信息*
     * @param mobile 手机号码
     * @param msg 验证码
     * @return 是否发送成功
     */
    public static final String sendCashOutMsg(String mobile, String name) {
    	// 这里实现短信发送功能
    	Map<String,Object> data = new HashMap<String, Object>();
    	data.put("#name#", name);
    	return SmsServerPlugin.sendCashOut(mobile,data);
    	
    }
    /**
     * 发送提现预警信息*
     * @param mobile 手机号码
     * @param msg 验证码
     * @return 是否发送成功
     */
    public static final String sendTotalOutMsg(String mobile, String name) {
    	// 这里实现短信发送功能
    	Map<String,Object> data = new HashMap<String, Object>();
    	data.put("#name#", name);
    	return SmsServerPlugin.sendTotalOut(mobile,data);
    	
    }
    
    
    /*public static void main(String[] args) {
    	sendTotalOutMsg("15811810352", "张才");
    }*/
    
    
}


