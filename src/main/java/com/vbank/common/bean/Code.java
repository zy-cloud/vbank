package com.vbank.common.bean;

/**
 * @author malongbo
 */
public class Code {

    /**
     * 成功
     */
    public static final int SUCCESS = 1;

    /**
     * 失败 
     */
    public static final int FAIL = 0;

    /**
     * 参数错误: 一般是缺少或参数值不符合要求
     */
    public static final int ARGUMENT_ERROR = 2;

    /**
     * 服务器错误
     */
    public static final int ERROR = 500;

    /**
     * 接口不存在
     */
    public static final int NOT_FOUND = 404;

    /**
     * token无效
     */
    public static final int TOKEN_INVALID = 422;
    /**
     * 重复请求
     */
    public static final int KEY_INVALID = 444;
    /**
     * 帐号已存在*
     */
    public static final int ACCOUNT_EXISTS = 3;

    /**
     * 验证码错误
     */
    public static final int CODE_ERROR = 4;
    
    /**
     * 帐号不存在*
     */
    public static final int ACCOUNT_NOT_EXISTS = 5;
    
    /**
     * 课程权限代码
     */
    public static final int CODE_AUTH = 505;
    
    
  //======================7xx（学习班的状态码）==================
    /**
     * 学习班不存在
     */
    public static final int TEAM_NOT_EXIST = 700;
    
    /**
     *  学员已是班级学员
     */
    public static final int ALREADY_JOIN_TEAM = 701;
    
    /**
     *  班级已经禁止加入
     */
    public static final int NOT_ALLOW_JOIN = 702;
    
    /**
     *  学员已经被移出班级
     */
    public static final int REMOVED_FROM_TEAM = 703;
    
}
