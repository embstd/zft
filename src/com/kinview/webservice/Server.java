package com.kinview.webservice;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.ksoap2.serialization.SoapObject;

import android.R.integer;
import android.os.Bundle;

import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.entity.NewCase;
import com.kinview.util.Event;

public class Server {
	/*
	 * 登陆
	 */
	public static CommonResult login(String username,String password) throws Exception{
		print.out("public static CommonResult login");
		CommonResult result =  new CommonResult();
        String functionName="AndroidLogin";
        HashMap<String, Object> params = new HashMap<String, Object>();// 加入参数
        params.put("username", username);
        params.put("password", password);
        WebService webService = new WebService(functionName,params);
//		org.ksoap2.serialization.SoapPrimitive object = (org.ksoap2.serialization.SoapPrimitive)webService.CallWebService();
		SoapObject soapObject = (SoapObject)webService.CallWebService();
        result.convertSoapObject(soapObject);
//		print.out("8");
//		print.out("getErrorCode="+result.getErrorCode());
//		print.out("getErrorDesc="+result.getErrorDesc());
//		print.out("getResultStr="+result.getResultStr());
		return result;
	}
	
	/*
	 * 获取
	 */
	public static String GetDataLingNum() 
	{
		CommonResult result =  new CommonResult();
		String functionName="AndroidGatDataLingNum";
		WebService webService = new WebService(functionName);
		SoapObject soapObject;
		try
		{
			soapObject = (SoapObject)webService.CallWebService();
			result.convertSoapObject(soapObject);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.getResultStr();
	}
	
	
	
	/*
	 * 获取
	 */
	public static String GetData() throws Exception
	{
		CommonResult result =  new CommonResult();
		String functionName="AndroidGatData";
		WebService webService = new WebService(functionName);
		SoapObject soapObject = (SoapObject)webService.CallWebService();
		result.convertSoapObject(soapObject);
		return result.getResultStr();
	}

	/*
	 * 修改密码
	 */
	public static CommonResult modifyPwd(String oldPwd,String newPwd)throws Exception{
		CommonResult result =  new CommonResult();
		String functionName="AndroidChangeUserPass";
		
		// 加入参数
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("username", Config.username);
		params.put("oldPass", oldPwd);
		params.put("newPass", newPwd);
		
		WebService webService = new WebService(functionName,params);
		SoapObject soapObject = (SoapObject)webService.CallWebService();
		result.convertSoapObject(soapObject);
		return result;
	}
	
	
	/*
	 * 获取案件信息
	 */
	public static CommonResult getEvent(String groupid,String userid)throws Exception{
		CommonResult result =  new CommonResult();
		String functionName="AndroidGetCase";
		
		// 加入参数
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("groupid", groupid);
		params.put("userid", userid);
		
		WebService webService = new WebService(functionName,params);
		SoapObject soapObject = (SoapObject)webService.CallWebService();
		result.convertSoapObject(soapObject);
		return result;
	}
	
	public static CommonResult submitEvent(Event event,String text)throws Exception{
		CommonResult result =  new CommonResult();
		String functionName="AndroidSetisOK";
		
//		int type=Integer.parseInt(event.getType());
//		int groupid=Integer.parseInt(Config.user.getGroupid());
//		int caseid=Integer.parseInt(event.getId());
//		int userid=Integer.parseInt(Config.user.getUserid());
		
		// 加入参数
		HashMap<String, Object> params = new HashMap<String, Object>();
//		params.put("type", type);
//		params.put("groupid", groupid);
//		params.put("caseid", caseid);
//		params.put("userid", userid);
		params.put("text", text);
		
		WebService webService = new WebService(functionName,params);
		SoapObject soapObject = (SoapObject)webService.CallWebService();
		result.convertSoapObject(soapObject);
		return result;
	}
	
	public static CommonResult getHistory(Date d_start,Date d_end)throws Exception{
		CommonResult result =  new CommonResult();
		String functionName="AndroidDownloadCase";
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		// 加入参数
		HashMap<String, Object> params = new HashMap<String, Object>();
//		params.put("groupid", Config.user.getGroupid());
//		params.put("userid", Config.user.getUserid());
		params.put("startTime",  sdf1.format(d_start));
//		params.put("startTime",  d_start);
		params.put("endTime",  sdf2.format(d_end));
//		params.put("endTime",  d_end);
		
		params.put("isAll", false);
		
		WebService webService = new WebService(functionName,params);
		SoapObject soapObject = (SoapObject)webService.CallWebService();
		result.convertSoapObject(soapObject);
		return result;
	}

	

	public String SubmitNewCaseByOrganise(String newCase ,String PicList)
	{
		print.out("SubmitNewCaseByOrganise");
		print.out("PicList="+PicList);
		CommonResult result =  new CommonResult();
		String functionName="AndroidSubmitNewCaseByOrganise";
		
		// 加入参数
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("byteStr", newCase);
		params.put("PicList", PicList);
		WebService webService = new WebService(functionName,params);
		SoapObject soapObject =null;
		try
		{
			soapObject = (SoapObject)webService.CallWebService();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.convertSoapObject(soapObject);
		print.out("返回值="+result.getResultStr());
		if(result.getResultStr() == "" &&result.getErrorCode() != "0")
		{
			return "";
		}
		return result.getResultStr();
	}

	public String SubmitNewCaseByPerson(String newCase ,String PicList)
	{
		print.out("SubmitNewCaseByPerson");
		CommonResult result =  new CommonResult();
		String functionName="AndroidSubmitNewCaseByPerson";
		// 加入参数
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("byteStr", newCase);
		params.put("PicList", PicList);
		WebService webService = new WebService(functionName,params);
		SoapObject soapObject =null;
		try
		{
			soapObject = (SoapObject)webService.CallWebService();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.convertSoapObject(soapObject);
		print.out("返回值="+result.getResultStr());
		if(result.getResultStr() == "" &&result.getErrorCode() != "0")
		{
			return "";
		}
		return result.getResultStr();
	}
	
	/*
	 * 获取案件信息
	 */
	public static CommonResult getCase(String username,String timePeriod)throws Exception{
		CommonResult result =  new CommonResult();
		String functionName="AndroidgetMyCase";
		
		// 加入参数
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userName", username);
		params.put("time", timePeriod);
		
		WebService webService = new WebService(functionName,params);
		SoapObject soapObject = (SoapObject)webService.CallWebService();
		result.convertSoapObject(soapObject);
		return result;
	}
	
	/*
	 * 获取案件信息
	 */
	public static CommonResult getFormList(int caseId, String groupid)throws Exception{
		CommonResult result =  new CommonResult();
		String functionName="AndroidgetCaseFromList";
		
		// 加入参数
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("caseID", String.valueOf(caseId));
		params.put("groupid", groupid);
		
		WebService webService = new WebService(functionName,params);
		SoapObject soapObject = (SoapObject)webService.CallWebService();
		result.convertSoapObject(soapObject);
		return result;
	}
	
	/*
	 * 获取案件详细信息
	 */
	public static CommonResult getCaseInfo(int caseId)throws Exception{
		CommonResult result =  new CommonResult();
		String functionName="AndroidgetCaseInformation";
		
		// 加入参数
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("caseid", String.valueOf(caseId));
		
		WebService webService = new WebService(functionName,params);
		SoapObject soapObject = (SoapObject)webService.CallWebService();
		result.convertSoapObject(soapObject);
		return result;
	}
	
	/*
	 * 上报表单
	 */
	public static CommonResult submitBd(int aj_id, String bd_id, String bd_name , String param)throws Exception{
		CommonResult result =  new CommonResult();
		String functionName="AndroidexecuteSB";
		
		// 加入参数
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("aj_id", aj_id);
		params.put("bd_id", bd_id);
		params.put("bd_name", bd_name);
		params.put("param", param);
		
		WebService webService = new WebService(functionName,params);
		SoapObject soapObject = (SoapObject)webService.CallWebService();
		result.convertSoapObject(soapObject);
		return result;
	}
	
	/*
	 * 获取送达回证信息
	 */
	public static CommonResult getSongdaHuizhengInfo(int caseId)throws Exception{
		CommonResult result =  new CommonResult();
		String functionName="AndroidgetFromSdhz";
		
		// 加入参数
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("caseid", caseId);
		
		WebService webService = new WebService(functionName,params);
		SoapObject soapObject = (SoapObject)webService.CallWebService();
		result.convertSoapObject(soapObject);
		return result;
	}
}
