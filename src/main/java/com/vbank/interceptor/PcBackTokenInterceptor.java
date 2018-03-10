package com.vbank.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.vbank.common.bean.BaseResponse;
import com.vbank.common.bean.Code;
import com.vbank.common.utils.StringUtils;

/**
 * Sass企业管理后台Token拦截器
 * @author dyzeng
 * @date 2017-07-29
 */
public class PcBackTokenInterceptor implements Interceptor {
//	private static Logger log = Logger.getLogger(TokenInterceptor.class);
    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String token = controller.getPara("token");
        if (StringUtils.isEmpty(token)) {
            controller.renderJson(new BaseResponse(Code.TOKEN_INVALID, "未登录不能进行操作！"));
            return;
        }

        Record sysUser =  new Record();// BussBackPojo.dao.findValidSysUserByToken(token);
        if (sysUser == null) {
    		controller.renderJson(new BaseResponse(Code.TOKEN_INVALID, "登录失效，请重新登录！"));
        }else{
//        	log.info("学员:【id:"+stu.getInt(Student.STUDENT_ID)+",name:"+ stu.getStr(Student.NICK_NAME)+"】请求方法:【"+controller.getRequest().getRequestURI()+"】");
        	controller.setAttr("sysUser", sysUser);
        	inv.invoke();
        }
        
        
        
    }
}
