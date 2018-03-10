package com.vbank.common.bean;

/** 通常带有result的返回
* @ClassName: NormalResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author cnmobi_db 
* @date 2015年9月23日 下午9:12:48 
*  
*/
public class NormalResponse  extends BaseResponse{
	private Object result;
	

	public NormalResponse(){
		super();
		// TODO Auto-generated constructor stub
	}


	public NormalResponse(Object result) {
		super();
		this.setResult(result);
	}


	public NormalResponse(Integer code) {
		super(code);
	}


	public NormalResponse(Integer code, String msg) {
		super(code, msg);
	}


	public NormalResponse(String msg) {
		super(msg);
	}


	public Object getResult() {
		return result;
	}


	public NormalResponse setResult(Object result) {
		this.result = result;
		return this;
	}
}
