package com.vbank.common.bean;

/**
 * @author zhangcai
 * @date 2015/3/10
 */
public class BaseResponse {
    
    private Integer code = 1;
    
    private String msg;
    private String sessionKey = "";

    public BaseResponse() {
    }

    public BaseResponse(String msg) {
        this.msg = msg;
    }

    public BaseResponse(Integer code) {
        this.code = code;
    }

    public BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public BaseResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
    
    
    
}
