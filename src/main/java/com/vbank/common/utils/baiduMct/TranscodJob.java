package com.vbank.common.utils.baiduMct;

/**
 * @author zhangcai
 * @date 15-3-9
 */
public class TranscodJob {
	private static TranscodJob instance;  
    public TranscodJob (){}  
  
    public static TranscodJob getInstance() {  
    	if (instance == null) {  
    		instance = new TranscodJob();  
    	}  
    	return instance;  
    }  
    static{
    	Task task = new Task();
    	Thread t = new Thread(task);
    	t.start();
    }
    
    private static class Task implements Runnable {// 定时token清除任务的线程类
		public void run() {
			while(true){
				try {
					Thread.sleep(60000);	//睡眠1小时执行一次
					System.out.println(this.getClass().getName()+":token定时任务开始执行!"); 
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
