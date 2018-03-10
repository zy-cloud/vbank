package com.vbank.controller.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import com.vbank.common.Require;
import com.vbank.common.bean.BaseResponse;
import com.vbank.common.bean.Code;
import com.vbank.common.utils.StringUtils;

/**
 * 基本的api
 * 基于jfinal controller做一些封装
 * @author dyzeng
 */
public class BaseController extends ApiController {
	private static Log log = Log.getLog(BaseController.class);
	/**
	* 获取卓越微课PC端获取当前用户对象
	* @return
	*/
//    protected Record getAppUser() {
//    	Record student = getAttr("appUser");
//        if (student == null) {
//            String token = getPara("token");
//            return Team.dao.findValidByToken(token);
//        }
//        return getAttr("student");
//    }
    
    
   
    /**
     * 获取企业sass系统管理后台用户对象
     * @return
     */
//    protected Record getSysUser() {
//    	Record student = getAttr("sysUser");
//        if (student == null) {
//            String token = getPara("token");
//            return PcBackService.dao.findValidSysUserByToken(token);
//        }
//        return getAttr("sysUser");
//    }
    
    
   
	
    /**
     * 响应接口不存在*
     */
    public void render404() {
        renderJson(new BaseResponse(Code.NOT_FOUND));
        
    }

    /**
     * 响应请求参数有误*
     * @param message 错误信息
     */
    public void renderArgumentError(String message) {
        renderJson(new BaseResponse(Code.ARGUMENT_ERROR, message));

    }

    /**
     * 响应操作成功*
     * @param message 响应信息
     */
    public void renderSuccess(String message) {
        renderJson(new BaseResponse().setMsg(message));
        
    }

    /**
     * 响应操作失败*
     * @param message 响应信息
     */
    public void renderFailed(String message) {
        renderJson(new BaseResponse(0, message));
        
    }
    
    /**
     * 判断请求类型是否相同*
     * @param name
     * @return
     */
    protected boolean methodType(String name) {
        return getRequest().getMethod().equalsIgnoreCase(name);
    }
    
    
    /**
     * 判断参数值是否为空
     * @param rules
     * @return
     */
    public boolean notNull(Require rules) {

        if (rules == null || rules.getLength() < 1) {
            return true;
        }

        for (int i = 0, total = rules.getLength(); i < total; i++) {
            Object key = rules.get(i);
            String message = rules.getMessage(i);
            BaseResponse response = new BaseResponse(Code.ARGUMENT_ERROR);
            
            if (key == null) {
                renderJson(response.setMsg(message));
                return false;
            }

            if (key instanceof String && StringUtils.isEmpty((String) key)) {
                renderJson(response.setMsg(message));
                return false;
            }

            if (key instanceof Array) {
                Object[] arr = (Object[]) key;

                if (arr.length < 1) {
                    renderJson(response.setMsg(message));
                    return false;
                }
            }
        }

        return true;
    }
    
    
    
  
    /*
	 * 
	 *打印结果方便调试
	 */
	public void renderJson(Object object) {
		HttpServletResponse res = getResponse();
		//res.setHeader("Access-Control-Allow-Credentials", "true");
		//res.addHeader("Access-Control-Allow-Origin", "*");
		//res.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8080"); //本地环境
		//res.setHeader("Access-Control-Allow-Origin", "http://192.168.1.252:8080");//测试环境
		//res.setHeader("Access-Control-Allow-Origin", "cloud.zyveke.com/zywc");//生产环境
		//res.setHeader("Access-Control-Allow-Origin", "http://112.74.86.119:8050");//生产环境
		String originHeader = getRequest().getHeader("Origin");
		String[] IPs = new String[12];
	    IPs[0] = "http://127.0.0.1:8080";
	    IPs[1] = "http://127.0.0.1:8088";
	    IPs[2] = "http://127.0.0.1:8010";;
	    IPs[3] = "http://192.168.1.22:8080";
	    IPs[4] = "http://192.168.1.22:18080";
	    IPs[5] = "http://192.168.1.252:8080";
	    IPs[6] = "http://192.168.1.252:18080";
	    IPs[7] = "http://112.74.86.119:8050";
	    IPs[8] = "http://cloud.zyveke.com";
	    IPs[9] = "http://m.zyveke.com";
	    IPs[10] = "http://saas.zyveke.com";
	    IPs[11] = "http://mp.zyveke.com";
	    
        if(Arrays.asList(IPs).contains(originHeader)){
        	res.setHeader("Access-Control-Allow-Origin", originHeader);
        }
		
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
		res.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
		res.setHeader("Content-Type", "application/json;charset=utf-8");
		
//		String json = JSON.toJSONString(object, filter, SerializerFeature.WriteMapNullValue,
//				SerializerFeature.WriteNullStringAsEmpty); //把null 转成""
		//log.info("返回json:"+JsonKit.toJson(object)); 
//		DateFormat dfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String studentId = getSessionAttr("student_id");
//		TUser stu = TUser.findByStudentId(studentId);
		Map<String,String[]> map = (Map<String,String[]>)getRequest().getParameterMap();
        String para = "";
		for(String name:map.keySet()){
			if (name.contains("base64Files")) {
				continue;
			}
            String[] values = map.get(name);
            para = para + (name+"="+Arrays.toString(values));
        }
		String json = JsonKit.toJson(object);
		if (json.length()>50) {
			json = "Object";
		}
//		if (stu!=null) {
//			log.info(WebUtils.getIP(getRequest())+":【id:"+stu.getInt("student_id")+",name:"+ stu.getStr("nick_name")+"】"
//				   + "请求方法:【"+getRequest().getRequestURI()+"】"
//				   + "请求参数:【"+para+"】"
//				   + "返回数据:"+json);
//		}else{
//			log.info(WebUtils.getIP(getRequest())+":"
//					   + "请求方法:【"+getRequest().getRequestURI()+"】"
//					   + "请求参数:【"+para+"】"
//					   + "返回数据:"+json);
//		}
		super.renderJson(object);
	}

//	@Override
//	public ApiConfig getApiConfig() {
//		ApiConfig ac = new ApiConfig();
//
//		// 配置微信 API 相关常量
//		ac.setToken(PropKit.get("token"));
//		ac.setAppId(PropKit.get("appId"));
//		ac.setAppSecret(PropKit.get("appSecret"));
//
//		/**
//		 * 是否对消息进行加密，对应于微信平台的消息加解密方式： 1：true进行加密且必须配置 encodingAesKey
//		 * 2：false采用明文模式，同时也支持混合模式
//		 */
//		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
//		ac.setEncodingAesKey(PropKit.get("encodingAesKey",
//				"setting it in config file"));
//		return ac;
//	}
    
	/**
     * 取Request中的数据对象
     * @param valueType
     * @return
     * @throws Exception
     */
    public JSONObject getParaToJsonObject()  {
    	JSONObject jo = new JSONObject();
        StringBuilder json = new StringBuilder();
        BufferedReader reader;
		try {
			reader = getRequest().getReader();
			String line = null;
	        while((line = reader.readLine()) != null){
	            json.append(line);
	        }
	        reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Can not parse the parameter \"" + json.toString() + "\" to JSONObject.");
			//e.printStackTrace();
		}
       
        jo = JSONObject.parseObject(json.toString());
        
//        log.info(WebUtils.getIP(getRequest())+":"
//				   + "请求方法:【"+getRequest().getRequestURI()+"】"
//				   + "请求参数:【"+json+"】"
//				   + "返回数据:"+"Object");
        
        
        return jo;
    }
    public void render404Jsp() {
    	renderJsp("/front/pageNotFound.jsp");
	}
}
