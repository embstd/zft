package com.kinview.webservice;

public class ItemData {

	private String key;
	private String value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public ItemData(String key,String value){
		this.key = key;
		this.value = value;
	}
	
	public ItemData(String key,int value){
		this.key = key;
		this.value = ""+value;
	}
	
	public ItemData(String key,double value){
		this.key = key;
		this.value = ""+value;
	}
	
	public ItemData(){
		
	}
}
