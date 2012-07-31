package com.kinview.webservice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.entity.NewCase;

public class WebService {
	
//	String nameSpace = "http://tempuri.org/";
	String nameSpace = "http://Kinview.org/";
	String methodName = "";
	HashMap<String, Object> params = null;

	WebService(String methodName,HashMap<String, Object> params){
		this.methodName = methodName;
		this.params = params;
		
	}
	
	WebService(String methodName){
		this.methodName = methodName;
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	protected Object CallWebService() throws Exception {
		print.out("CallWebService()");
		String SOAP_ACTION = nameSpace + methodName;
		Object temp;
		SoapObject attribute = new SoapObject(nameSpace, methodName);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // .net 支持
		if (params != null && !params.isEmpty()){
//			print.out("prams !=null");
			Iterator itor = params.entrySet().iterator();
			while(itor.hasNext()){
				Entry entry = (Entry)itor.next();
				String key = (String)entry.getKey();
				Object value = entry.getValue();
				attribute.addProperty(key,value);
			}
		}
		envelope.bodyOut = attribute;
		AndroidHttpTransport androidHttpTrandsport = new AndroidHttpTransport(
				Config.getWebService_url(),Config.webservice_timeout*1000);
//		print.out(Config.getWebService_url().toString());
		androidHttpTrandsport.call(SOAP_ACTION, envelope); 
	    temp = envelope.getResponse();//getResult();
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	protected Object CallWebServiceByNewCase() throws Exception 
	{
		print.out("CallWebServiceByNewCase()");
		String SOAP_ACTION = nameSpace + methodName;
		Object temp;
		SoapObject attribute = new SoapObject(nameSpace, methodName);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // .net 支持
		envelope.bodyOut = attribute;
//		attribute.addProperty(newCase);
		AndroidHttpTransport androidHttpTrandsport = new AndroidHttpTransport(
				Config.getWebService_url(),Config.webservice_timeout*1000);
//		print.out(Config.getWebService_url().toString());
		androidHttpTrandsport.call(SOAP_ACTION, envelope); 
	    temp = envelope.getResponse();//getResult();
		return temp;
	}
	
	
	
}
