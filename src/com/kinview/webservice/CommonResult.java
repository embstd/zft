package com.kinview.webservice;

import org.ksoap2.serialization.SoapObject;

public class CommonResult
{
	
	private String[] values = {"errorCode","errorDesc","resultStr"};
	
	private String errorCode;
	private String errorDesc;
	private String resultStr;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getResultStr() {
		return resultStr;
	}
	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}
	
	
	public void convertSoapObject(SoapObject so){
		for(int i=0;i<values.length;i++){
			if(so.getProperty(values[i])!=null){
				try{
					setValue(values[i],so.getProperty(values[i]).toString());
//					String v = so.getProperty(values[i]).toString();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private void setValue (String key,String value)throws Exception{
		key = key.trim();
		value = value.trim();
		if(value.equals("anyType{}")){
			value="";
		}
		if(key.equals(values[0])){
			this.errorCode = value;
		}else if(key.equals(values[1])){
			this.errorDesc = value;
		}else if(key.equals(values[2])){
			this.resultStr = value;
		}
	}
	
}
