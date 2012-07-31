package com.kinview.update;

public class MessageXml {

	private int type = 0 ; //��Ϣ������
	private String xml = ""; //��Ϣ����
	private static String[] msg = {
			"��������"	//0	
			,"��½�ɹ�"				//1
			,"�û������������!"	//2
			,"���Ӵ���,����������Ӧ!"	//3
			,"�����޷�����!����������Ϣ!"	//4
			,"ָ�������Ӳ�������!"	//5
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
