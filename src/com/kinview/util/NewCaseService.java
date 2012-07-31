package com.kinview.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.kobjects.base64.Base64;

import com.kinview.camera.ActivityPhoto;
import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.entity.NewCase;
import com.kinview.webservice.Server;
import android.R.integer;
import android.R.string;
import android.content.Context;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewCaseService
{
	public NewCaseService()
	{

	}
	public static String NewCaseId_Lsh = "" ;
	Context context1;
	
	
	public NewCaseService(Context context)
	{
		this.context1 = context;
	}

	/**
	 * 加载案件类别
	 * 
	 * @return
	 */
	public String[][] CaseType1(String id)
	{

		SystemSettingMgr systemSettingMgr = new SystemSettingMgr(context1);
		String[][] temp = systemSettingMgr.getTb_typeString(id);
		return temp;
	}

	public String SubmitNewCase(NewCase newCase) throws Exception
	{
		// TODO Auto-generated method stub
		// type = 1 组织 type = 2 个人
		String tempString = "";
		switch (newCase.getType())
		{
		case 1:
			tempString = submitNewCaseByOraganise(newCase);
			break;
		case 2:
			tempString = submitNewCaseByPerson(newCase);
			break;
		default:
			break;
		}
		return tempString;

	}

	private String submitNewCaseByPerson(NewCase newCase) throws Exception
	{
		// TODO Auto-generated method stub 个人
		String temp = SubmitNewCaseByXmlService(newCase);
		Server server = new Server();
		return server.SubmitNewCaseByPerson(temp, newCase.getPicList());

	}

	private String submitNewCaseByOraganise(NewCase newCase) throws Exception
	{
		// TODO Auto-generated method stub 组织
		String temp = SubmitNewCaseByXmlService(newCase);
		Server server = new Server();
		return server.SubmitNewCaseByOrganise(temp, newCase.getPicList());
	}

	public String SubmitNewCaseByXmlService(NewCase newCase)
			throws NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException
	{
		StringBuffer buffer = new StringBuffer();
		buffer
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<ResultSet>\r\n");
		buffer.append("   <item>\r\n\t\t");
		Field[] field = newCase.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		print.out("field.length=" + field.length);
		for (int j = 0; j < field.length; j++) // 遍历所有属性
		{
			String name = field[j].getName(); // 获取属性的名字
			System.out.println("attribute name:" + name);
			String type = field[j].getGenericType().toString(); // 获取属性的类型
			if (type.equals("class java.lang.String"))
			{ // 如果type是类类型，则前面包含"class "，后面跟类名类名
				Method m = newCase.getClass().getMethod("get" + name);
				String value = (String) m.invoke(newCase); // 调用getter方法获取属性值
				if (value != null && !name.equals("PicList"))
				{
					System.out.println("attribute value:" + value);
					buffer.append("<" + name + ">" + value + "</" + name + ">");
				}
				else if (value == null && !name.equals("PicList")
						|| value == "" && !name.equals("PicList"))
				{
					buffer.append("<" + name + ">" + "" + "</" + name + ">");
				}
			}

			// if(type.equals("class java.lang.Integer")){
			if (type.equals(int.class))
			{
				Method m = newCase.getClass().getMethod("get" + name);
				Integer value = (Integer) m.invoke(newCase);
				if (value != null)
				{
					System.out.println("attribute value:" + value);
					buffer.append("<" + name + ">" + value + "</" + name + ">");
				}
				else
				{
					buffer.append("<" + name + ">" + 0 + "</" + name + ">");
				}
			}
			if (type.equals("class java.lang.Short"))
			{
				Method m = newCase.getClass().getMethod("get" + name);
				Short value = (Short) m.invoke(newCase);
				if (value != null)
				{
					System.out.println("attribute value:" + value);
					buffer.append("<" + name + ">" + value + "</" + name + ">");
				}
			}
			if (type.equals("class java.lang.Double"))
			{
				Method m = newCase.getClass().getMethod("get" + name);
				Double value = (Double) m.invoke(newCase);
				if (value != null)
				{
					System.out.println("attribute value:" + value);
					buffer.append("<" + name + ">" + value + "</" + name + ">");
				}
			}
			if (type.equals("class java.lang.Boolean"))
			{
				Method m = newCase.getClass().getMethod("get" + name);
				Boolean value = (Boolean) m.invoke(newCase);
				if (value != null)
				{
					System.out.println("attribute value:" + value);
					buffer.append("<" + name + ">" + value + "</" + name + ">");
				}
			}
			if (type.equals("class java.util.Date"))
			{
				Method m = newCase.getClass().getMethod("get" + name);
				Date value = (Date) m.invoke(newCase);
				if (value != null)
				{
					System.out.println("attribute value:"
							+ value.toLocaleString());
					buffer.append("<" + name + ">" + value + "</" + name + ">");
				}
			}

		}
		buffer.append("   </item>\r\n\t\t</ResultSet>");

		return buffer.toString();

	}

	public int HistorySubmitByOrganise(NewCase n)
	{
		int error = 0;
		int q = 0;
		/*
		 * flag字段意思 flag = 1 案件信息存储本地数据库成功，案件信息中有图片但图片未存储本地数据库成功 flag = 2
		 * 案件信息存储本地数据库成功，案件信息中未选择图片 flag = 3 案件信息存储本地数据库成功，案件信息中有图片，图片也存储本地数据库成功
		 * flag = 4上报服务器失败  flag = 5   上报服务器成功
		 */
		int flag = 0;
		switch (n.getPicNum())
		{
		case 0:
			flag = 2;
			break;
		default:
			flag = 1;
			break;
		}
		print.out("PicNum = "+n.getPicNum()+"   "+"flag="+flag);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssms");
		String questionID = Config.user.getU_UserID() + sdf.format(date);
		// TODO Auto-generated method stub
		SystemSettingMgr mgr = new SystemSettingMgr(context1);
		String field = "questionID,typeid,organise,fzr,zw,otel,oadd,thetime,address,creater,createtime,intro,locker,areaid,fromtype,state,cordx,cordy,firstUpTime,videoNum,picNum,flag";
		String values = "'" + questionID + "','" + n.getCaseWeiFaXW() + "','"
				+ n.getOrganise() + "','" + n.getFzr() + "','" + n.getZw()
				+ "','" + n.getOtel() + "','" + n.getOadd() + "','"
				+ n.getThetime() + "','" + n.getAddress() + "','"
				+ n.getCreate() + "','" + n.getCreateTime() + "','"
				+ n.getIntro() + "','" + n.getLocker() + "','" + n.getAreaid()
				+ "','" + n.getFromtype() + "','" + n.getState() + "','"
				+ n.getCoordX() + "','" + n.getCoordY() + "','"
				+ n.getFirstUpTime() + "','" + n.getVideoNumString() + "','"
				+ n.getPicNumString() + "','" + flag + "'";
		int s = mgr.insertDataByYwbBackInt("Question", field, values);
		print.out("插入本地库后得出值为=="+s);
		if (s == 1)
		{
			if (n.getPicNum() > 0)
			{
				int insert = 0;
				String[] picListStr = n.getPicListStr();
				for (int i = 0; i < picListStr.length; i++)
				{
					insert = mgr.insertDataByYwbBackInt("pic",
							"questionID,picName,picPath", "'" + questionID
									+ "','"
									+ getPicName(picListStr[i].toString())
									+ "','" + picListStr[i].toString() + "'");
					if(insert == 0)
					{
						print.out("图片插入本地库后不成功得出值为=="+insert);
					}
				}
				q += mgr.updateDataByYwbBackInt("Question", "flag = '3'",
						"questionID = '" + questionID+"'");

			}
			else if (n.getPicNum() == 0)
			{
				q += mgr.updateDataByYwbBackInt("Question", "flag = 2",
						"questionID = '" + questionID +"'");
			}
		}
		else if (s == 0)
		{
			error = 10000;
		}
		if (q == 0 && flag != 2)
		{
			error = 10001;
		}
		else  {
		   error = 	UpdateDB(1,questionID,n);  //1组织
		}
		return error;
	}

	private String getPicName(String PicStr)
	{
		if (PicStr == "")
			return "";
		int s = PicStr.lastIndexOf("/");
		String t = PicStr.substring(s + 1);
		return t;
	}

	public int HistorySubmitByPerson(NewCase n1)
	{
		int error = 0;
		int q = 0;
		/*
		 * flag字段意思 
		 * flag = 1 案件信息存储本地数据库成功，案件信息中有图片但图片未存储本地数据库成功
		 * flag = 2案件信息存储本地数据库成功，案件信息中未选择图片
		 * flag = 3 案件信息存储本地数据库成功，案件信息中有图片，图片也存储本地数据库成功
		 * flag = 4上报服务器失败  flag = 5   上报服务器成功
		 */
		int flag = 0;
		switch (n1.getPicNum())
		{
		case 0:
			flag = 2;
			break;
		default:
			flag = 1;
			break;
		}

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssms");
		String questionID = Config.user.getU_UserID() + sdf.format(date);
		// TODO Auto-generated method stub
		SystemSettingMgr mgr = new SystemSettingMgr(context1);
		String field = "questionID,typeid,person,sex,age,idnumber,ptel,padd,thetime,address,creater,createtime,intro,locker,areaid,fromtype,state,cordx,cordy,firstUpTime,videoNum,picNum,flag";
		String values = "'" + questionID + "','" + n1.getCaseWeiFaXW() + "','"
				+ n1.getPerson() + "','" + n1.getSex() + "','" + n1.getAge()
				+ "','" + n1.getIdnumber() + "','" + n1.getPtel() + "','"
				+ n1.getPadd() + "','" + n1.getThetime() + "','"
				+ n1.getAddress() + "','" + n1.getCreate() + "','"
				+ n1.getCreateTime() + "','" + n1.getIntro() + "','"
				+ n1.getLocker() + "','" + n1.getAreaid() + "','"
				+ n1.getFromtype() + "','" + n1.getState() + "','"
				+ n1.getCoordX() + "','" + n1.getCoordY() + "','"
				+ n1.getFirstUpTime() + "','" + n1.getVideoNumString() + "','"
				+ n1.getPicNumString() + "','" + flag + "'";
		int s = mgr.insertDataByYwbBackInt("Question", field, values);
		if (s == 1)
		{
			if (n1.getPicNum() > 0)
			{
				String[] picListStr = n1.getPicListStr();
			
				for (int i = 0; i < picListStr.length; i++)
				{
					q += mgr.insertDataByYwbBackInt("pic",
							"questionID,picName,picPath", "'" + questionID
									+ "','"
									+ getPicName(picListStr[i].toString())
									+ "','" + picListStr[i].toString() + "'");
				}
				q += mgr.updateDataByYwbBackInt("Question", "flag = '3'",
						"questionID = '" + questionID+"'");
			}
			else if (n1.getPicNum() == 0)
			{
				q += mgr.updateDataByYwbBackInt("Question", "flag = '2'",
						"questionID = '" + questionID+"'");
			}
			
		}
		else if (s == 0)
		{
			error = 10000;
		}
		if (q == 0 && flag != 2)
		{
			error = 10001;
		}
		else  {
			error = UpdateDB(2,questionID,n1);  //2个人
		}
		return error;
	}

	/**
	 * 
	 * @param i  1组织    2个人
	 * @return
	 */
	public int UpdateDB(int i,String questionID,NewCase  n)
	{
		int update = 0;
		SystemSettingMgr mgr = new SystemSettingMgr(context1);
		String lsh = "";
		int t = 0;
		int  error = 0;
		String id_lsh = "";
		switch (i)
		{
		case 1:
			 try
			 {
				NewCaseService  service1 = new NewCaseService();
				id_lsh = service1.submitNewCaseByOraganise(n);
			 }
			 catch (Exception e)
		     {
				// TODO Auto-generated catch block
				 error = 20000;
				e.printStackTrace();
			 }
			 if(id_lsh == "")
			 {
				 error = 20000;
				 update =  mgr.updateDataByYwbBackInt("Question", "flag = '4' ",
								"questionID = '" + questionID+"'");
			 }
			 else 
			 {
				NewCaseId_Lsh = id_lsh;
				String[] temp = id_lsh.split(",");
				update =  mgr.updateDataByYwbBackInt("Question", "flag = '5' ,lsh ='"+temp[temp.length-1]+"'",
							"questionID = '" + questionID+"'");
				print.out("修改问题表的中的Flag值和LSH值"+update);
				if (update==0)
				{
					error = 20001 ;
				}
			 }
			break;
		case 2:
			 try
			 {
				 NewCaseService  service2 = new NewCaseService();
				id_lsh = service2.submitNewCaseByPerson(n);
			 }
			 catch (Exception e)
		 	 {
				// TODO Auto-generated catch block
				 error = 20000;
				e.printStackTrace();
			 }
			 if(id_lsh == "")
			 {
				 error = 20000;
				 update =  mgr.updateDataByYwbBackInt("Question", "flag = '4' ",
							"questionID = '" + questionID+"'");
			 }
			 else 
			 {
				 NewCaseId_Lsh = id_lsh;
				 String[] temp = id_lsh.split(",");
					t =  mgr.updateDataByYwbBackInt("Question", "flag = '5' ,lsh ='"+temp[temp.length-1]+"'",
								"questionID = '" + questionID+"'");
			    if (t==0)
				{
			    	error = 20001 ;
				}
			 }
			break;
		default:
			break;
		}
		return error;
	}

}
