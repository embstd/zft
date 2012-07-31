package com.kinview.update;

public class MyException extends Exception {

	private static final long serialVersionUID = 1L;

	//0,表示普通消息
	private int type = 0;
	private String message="";
	public MyException(){
	}
	
	public MyException(String message){
		this.message  = message;
	}
	
	public MyException(int type,String message){
		this.type  = type;
		this.message  = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void printStackTrace(){
		System.out.print(message);
	}
	
}
