package com.vbank.common.token;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.vbank.common.utils.DateUtils;
import com.vbank.common.utils.StringUtils;
import com.vbank.common.utils.TokenUtil;

/**
 * @author dyzeng
 * @date 2017-07-29
 */
public class AppTokenManager {
    private static AppTokenManager me = new AppTokenManager();
    
    /*public Map<String, Student> tokens;
    public Map<String, String> stuTokens;
    public Map<String, Date> stuDate;
    public TokenManager() {
        tokens = new ConcurrentHashMap<String, Student>();
        stuTokens = new ConcurrentHashMap<String, String>();
        stuDate = new ConcurrentHashMap<String, Date>();
    }*/
   /* static{
    	Task task = new Task();
    	Thread t = new Thread(task);
    	t.start();
    	validTimer();
    }*/
    
    /**
     * 获取单例对象
     * @return
     */
    public static AppTokenManager getMe() {
        return me;
    }

    /**
     * 验证学生登录的token
     * @param token
     * @return
     */
   /* public Student validate(String stuToken) {
        return tokens.get(stuToken);
    }*/
    /**
     * 拿到 已登录学员的token
     */
   /* public String getToken(String stuId){
    	return stuTokens.get(stuId);
    }*/
    /**
     * 学员登出  移除学员token
     */
    /*public void removeToken(String stuId , String stuToken){
    	stuTokens.remove(stuId);
    	tokens.remove(stuToken);
    	stuDate.remove(stuId);
    }*/
    /**
     * 生成token值
     * @param user
     * @return
     */
    public String generateAppToken(Record student) {
    	  //首先清除原来的登录token
//        String oldToken = stuTokens.get(student.get(Student.STUDENT_ID));
//        if(StringUtils.isNotEmpty(oldToken))
//        {
//        	tokens.remove(oldToken);
//        }
        String token = TokenUtil.generateToken();
//        stuTokens.put(student.get(Student.STUDENT_ID).toString(), token);
        
        
        //将token和studentId存入数据库中 12小时有效
        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(new Date()); 
		calendar.add(Calendar.DATE,7);//把日期往后增加1天
        Record studentTokenPc = new Record()
        .set("token", token)
        .set("userId", student.getInt("student_id"))
        .set("loginTime", DateUtils.getNowTime())
        .set("invalidTime", sdfYMD.format(calendar.getTime()))
        .set("remark", "登陆存储token");
        Db.save("t_student_token",studentTokenPc);
        
//        tokens.put(token, student);
//        stuDate.put(student.get(Student.STUDENT_ID).toString(), new Date());
        return token;
    }
    
   
    
   
    
}
