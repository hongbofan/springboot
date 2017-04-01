package com.example.util;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * 统一json返回格式
 * @author cxy
 *
 */
public class Result<T> {

	private String code;
	private String message;
	private T content;
	
	public Result() {
		super();
	}
	public Result(String code, String message, T content) {
		super();
		this.code = code;
		this.message = message;
		this.content = content;
	}
	
	public static Result success(){
		return success(null);
	}
	
	public static Result success(Object content){
		return new Result("1", "成功", content);
	}
	
	public static Result fail(String message){
		return new Result("0", message, null);
	}
	
	public static Result error(String message){
		return new Result("-1", message, null);
	}

	public static Result error(String message, Object content){
		return new Result("-1", message, content);
	}
	
	public static Result unAuth(String message){
		return new Result("-2", message, null);
	}
	
	@JSONField(serialize = false)
	public boolean isSuc(){
		return "1".equals(code);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}
	
	
}
