package com.vbank.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 *  时间工具
 * @author malongbo
 */
public final class DateUtils {
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	

    /**
     *  获得当前时间
     *  格式为：yyyy-MM-dd HH:mm:ss
    */
    public static String getNowTime() {
        Date nowday = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
        String time = sdf.format(nowday);
        return time;
    }

    /**
     * 获取当前系统时间戳
     * @return
     */
    public static Long getNowTimeStamp() {
        return System.currentTimeMillis();
    }

    public static Long getNowDateTime() {
        return new Date().getTime();
    }
    
    public static String getNowDate() {
    	return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }
    
    /**
     * 自定义日期格式
     * @param format
     * @return
     */
    public static String getNowTime(String format) {
        Date nowday = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);// 精确到秒
        String time = sdf.format(nowday);
        return time;
    }

    /**
     * 将时间字符转成Unix时间戳
     * @param timeStr
     * @return
     * @throws java.text.ParseException
     */
    public static Long getTime(String timeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
        Date date = sdf.parse(timeStr);
        return date.getTime()/1000;
    }

    /**
     * 将Unix时间戳转成时间字符
     * @param timestamp
     * @return
     */
    public static String getTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
        Date date = new Date(timestamp*1000);
        return sdf.format(date);
    }

    /**
     * 获取半年后的时间
     * 时间字符格式为：yyyy-MM-dd HH:mm:ss
     * @return 时间字符串
     */
    public static String getHalfYearLaterTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒

        Calendar calendar = Calendar.getInstance();
        int currMonth = calendar.get(Calendar.MONTH) + 1;

        if (currMonth >= 1 && currMonth <= 6) {
            calendar.add(Calendar.MONTH, 6);
        } else {
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.MONTH, currMonth - 6 - 1);
        }


        return sdf.format(calendar.getTime());
    }
    
    
    /**
     * 获取n年后的时间
     * 时间字符格式为：yyyy-MM-dd HH:mm:ss
     * @return 时间字符串
     */
    public static String getDateAfterYearTime(Date date,int years) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
       
        return sdf.format(calendar.getTime());
    }
    
    
    /**
     * 获取n月后的时间
     * 时间字符格式为：yyyy-MM-dd HH:mm:ss
     * @return 时间字符串
     */
    public static String getDateAfterMonthTime(Date date,int months) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = months/12;
        int month = months%12;
        if(year>0){
        	calendar.add(Calendar.YEAR, year);
        }
		if(month>0){
			calendar.add(Calendar.MONTH, month);
		}
        return sdf.format(calendar.getTime());
    }
    
    

	// 获得当天0点时间戳
	public static long getTimesMorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return  (cal.getTimeInMillis());
	}
	
	
	/**获得当钱时间30秒之前的时间戳
	 * @return
	 */
	public static long getTimesBeforeAnHour() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -30);
		return  (cal.getTimeInMillis());
	}
	
	
	
	/**
	 * 
	 * 分段时间格式化  今天（00:28）、  昨天（昨天）、   昨天之前（2017-02-28）
	 * @throws ParseException
	 */
	public static String sectionTimeFomat(Date tmpDateTime) throws ParseException {
		String sectionTimeStr = "";
		
		Calendar endToday = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		endToday.set(Calendar.HOUR_OF_DAY, 23);
		endToday.set(Calendar.MINUTE, 59);
		endToday.set(Calendar.SECOND, 59);

		Calendar endYes = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		endYes.add(Calendar.DATE, -1);
		endYes.set(Calendar.HOUR_OF_DAY, 23);
		endYes.set(Calendar.MINUTE, 59);
		endYes.set(Calendar.SECOND, 59);
		
		//SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//		long nh = 1000 * 60 * 60;// 一小时的毫秒数
//		long nm = 1000 * 60;// 一分钟的毫秒数
//      long ns = 1000;// 一秒钟的毫秒数

		long diffTodayEnd = endToday.getTime().getTime() - tmpDateTime.getTime();// 今天23:59:59与发帖时间之差
		long diffTodayEndNum = diffTodayEnd / nd;// 今天23:59:59与发帖时间之差多少天
		
	
		long diffYesEnd = endYes.getTime().getTime() - tmpDateTime.getTime();// 昨天23:59:59与发帖时间之差
		long diffYesEndNum = diffYesEnd / nd;// 昨天23:59:59与发帖时间之差多少天
		
	
		if (diffTodayEndNum == 0) {
			// 如果是今天
			SimpleDateFormat dfMh = new SimpleDateFormat("HH:mm");
			sectionTimeStr =  dfMh.format(tmpDateTime);

		} else if (diffYesEndNum == 0) {
			// 如果是介于昨天与前天之间
			sectionTimeStr = "昨天";

		} else if(diffYesEndNum > 0){
			//前天之前
			SimpleDateFormat dfYMdhm = new SimpleDateFormat("yyyy-MM-dd");
			sectionTimeStr = dfYMdhm.format(tmpDateTime);
		}

		return sectionTimeStr;
	}
	
	/**
	 * 获取清算时间
	 * @return
	 */
	public static String getClearTime(){
		String nowDateStr = new SimpleDateFormat("yyyy-MM-").format(new Date())+"15 00:00:00";
		SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_MONTH, 15);
	    calendar.add(Calendar.MONTH, 1);
	    calendar.getTime();
	    String nextDateStr = sdfYMD.format(calendar.getTime())+" 00:00:00";
	    try {
			Date parse1 = sdfYMD.parse(nowDateStr);
			if (new Date().getTime()<parse1.getTime()) {
				return nowDateStr;
			}
			if (new Date().getTime()>parse1.getTime()) {
				return nextDateStr;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取未来某天时间
	 * @param validDay
	 * @return
	 */
	public static String getFutTime(int validDay){
		SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(new Date()); 
		calendar.add(Calendar.DATE,validDay);//把日期往后增加n天
		return sdfYMD.format(calendar.getTime());
	}
	 /**  
	    * 判断当前日期是星期几<br>  
	    * <br>  
	    * @param pTime 修要判断的时间<br>  
	    * @return dayForWeek 判断结果<br>  
	    * @Exception 发生异常<br>  
	    */   
	public  static  int  dayForWeek(Date date){  
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int  dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1 ){  
		  dayForWeek = 7; 
		}else {
		  dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return  dayForWeek;  
	}  
	
	
	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        try {
			smdate=sdf.parse(sdf.format(smdate));
			bdate=sdf.parse(sdf.format(bdate));  
		} catch (ParseException e) {
			e.printStackTrace();
		}  
       
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
      
    
	/** 
	*字符串的日期格式的计算 
	*/  
    public static int daysBetween(String smdate,String bdate){  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        try {
			cal.setTime(sdf.parse(smdate));
			cal.setTime(sdf.parse(bdate)); 
		} catch (ParseException e) {
			e.printStackTrace();
		}    
        long time1 = cal.getTimeInMillis();                 
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  
    
	
    /**
     * 比较两个日期大小
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compareDate(String DATE1, String DATE2) {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	try {
    		Date dt1 = df.parse(DATE1);
	        Date dt2 = df.parse(DATE2);
	        
	        long lt1 = dt1.getTime();
	        long lt2 = dt2.getTime();
	        
			if (lt2 > lt1) {
				//System.out.println("dt1 在dt2前");
			    return 1;
			} else if (lt2 < lt1) {
				//System.out.println("dt1在dt2后");
	            return -1;
	        }else {
	        	 //System.out.println("dt1和dt2时间相等");
	            return 0;
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	    return 0;
	}
    
    
	
	public static void main(String[] args) {
		try {
			System.out.println(DateUtils.dayForWeek(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return
	 */
	public static String getLastDay() {
		// 获取Calendar  
		Calendar calendar = Calendar.getInstance();  
		// 设置时间,当前时间不用设置  
		// calendar.setTime(new Date());  
		// 设置日期为本月最大日期  
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));  
		  
		// 打印  
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");  
		return format.format(calendar.getTime());  
	}
	/**
	 * 获取几个月过后的日期
	 * @param len
	 * @return
	 */
	public static String getMonthLater(Date d,int len) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d); 
		calendar.add(Calendar.MONTH, len);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		return sdf.format(calendar.getTime());
	}
	/**
	 * 获取班级最终结算的日期
	 * @param len
	 * @return
	 */
	public static String getMonthLaterClose(Date d,int len) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d); 
		calendar.add(Calendar.MONTH, len);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM");  
		return sdf.format(calendar.getTime())+"-01 12:00:00";
	}
	/**
	 * @param i
	 * @return
	 */
	public static int calculatDay(Date start,Date end) {
		return (int) ((end.getTime()-start.getTime())/86400000);
	}
	/**
	 * 获取未来某天日期
	 * @param validDay
	 * @return
	 */
	public static String getFutDate(int validDay){
		SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(new Date()); 
		calendar.add(Calendar.DATE,validDay);//把日期往后增加n天
		return sdfYMD.format(calendar.getTime());
	}
	/**
	 * 获取下一个工作日
	 * @param validDay
	 * @return
	 */
	public static String getNextWorkDay(){
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
		//2018年节假日
		String holiday="2018-02-15,2018-02-16,2018-02-19,2018-02-20,2018-02-21,2018-04-05,2018-04-06,2018-04-30,2018-05-01,2018-06-18,2018-09-24,2018-10-01,2018-10-02,2018-10-03,2018-10-04,2018-10-05";
		now.setTime(new Date());
		try{
			while(true)
			{
				now.roll(Calendar.DAY_OF_YEAR,1);
				int a=now.get(Calendar.DAY_OF_WEEK);
				if(a!=7&&a!=1&&!holiday.contains(sdfs.format(now.getTime())))
				{
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(now.getTime());
	}
	 /**
     * 判断当前时间是否在某个时间段之间
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static boolean isBetweenTwoTime(String smdate,String bdate) {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	try {
    		Date dt1 = df.parse(smdate);
	        Date dt2 = df.parse(bdate);
	        long nowTime = new Date().getTime();
	        long lt1 = dt1.getTime();
	        long lt2 = dt2.getTime();
	        
			if(nowTime >= lt1 && nowTime <=lt2) {
				return true;
			}else {
	            return false;
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	    return false;
    }
	
}
