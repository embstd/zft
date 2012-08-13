package com.kinview.util;

import java.util.ArrayList;

import android.content.Context;

import com.kinview.assistant.Assistant;
import com.kinview.config.print;
import com.kinview.entity.Ad;
import com.kinview.entity.AdList;
import com.kinview.entity.AdType;
import com.kinview.entity.ZtcList;
import com.kinview.webservice.Server;

public class ZtcService
{
	Context context;
	String stringStr[][]=null;
	private static ArrayList<ZtcList> listztcInformation = new ArrayList<ZtcList>();
	
	private static ArrayList<ZtcList> ztclist = new ArrayList<ZtcList>();
	
	public ZtcService()
	{

	}
	public ZtcService(Context context1)
	{
		this.context = context1;
	}
	
	public String[][] getSearch(String  number,String cut,String  master,String driver,String state)
	{
		Server server = new Server();
		String  s =  server.GetZtcList(number,cut,master ,driver,state);
		Assistant assistant = new Assistant();
		ArrayList<Object> list = assistant.parseXml(assistant.upZipBaseString(s), ZtcList.class);
		ztclist.clear();
		for(Object obj:list){
			ZtcList item = (ZtcList)obj;
			ztclist.add(item);
		}
		stringStr = new String[ztclist.size()][ZtcList.class.getDeclaredFields().length];
		print.out("ztclist.size()="+ztclist.size());
		print.out("ZtcList.class.getDeclaredFields().length="+ZtcList.class.getDeclaredFields().length);
		print.out("ztclist.get(i).getId().toString()="+ztclist.get(0).getId().toString());
		for (int i = 0; i < ztclist.size(); i++)
		{
			stringStr[i][0]= ztclist.get(i).getId().toString();
			stringStr[i][1]= ztclist.get(i).getNumber().toString();
			stringStr[i][2]= ztclist.get(i).getCut().toString();
			stringStr[i][3]= ztclist.get(i).getMaster().toString();
			stringStr[i][4]= ztclist.get(i).getDriver().toString();
			stringStr[i][5]= ztclist.get(i).getInstall().toString();
			stringStr[i][6]= ztclist.get(i).getIsdefault().toString();
			stringStr[i][7]= ztclist.get(i).getFdjh().toString();
	
		}
		
		return stringStr;
	}
	
	
	public ZtcList getZtcInformation(String idString)
	{
		ZtcList ztc = null;
		Server server = new Server();
		String  s =  server.GetZtcView(idString);
		Assistant assistant = new Assistant();
		ArrayList<Object> list = assistant.parseXml(assistant.upZipBaseString(s), ZtcList.class);
		
		for(Object obj:list){
			ztc = (ZtcList)obj;
			
		}
		return ztc;
	}
	
	
}
