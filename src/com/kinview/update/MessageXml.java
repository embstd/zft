package com.kinview.update;

public class MessageXml {

	private int type = 0 ; //消息的类型
	private String xml = ""; //消息内容
	private static String[] msg = {
			"连接正常"	//0	
			,"登陆成功"				//1
			,"用户名或密码错误!"	//2
			,"连接错误,服务器无响应!"	//3
			,"服务无法访问!请检查配置信息!"	//4
			,"指定的连接参数错误!"	//5
			};
	
	public MessageXml(){
	
	}
	
	public MessageXml(int type){
		this.type  = type;
	}
	
	public MessageXml(int type ,String xml){
		this.type = type;
		this.xml = xml;
	}
	
	public static String getMsg(int i){
		return msg[i];
	}
	
	public String getMsg(){
		return msg[type];
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}

	
}
