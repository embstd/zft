package com.kinview.util;

import java.util.ArrayList;

import com.kinview.assistant.Assistant;
import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.entity.Ad;
import com.kinview.entity.AdList;
import com.kinview.entity.AdType;
import com.kinview.entity.TbType;
import com.kinview.webservice.Server;
import com.kinview.zft.R.string;

import android.R.integer;
import android.content.Context;

public class AdService
{
	Context context;
	private static ArrayList<Ad> listadInformation = new ArrayList<Ad>();
	private static ArrayList<AdType> listadtype = new ArrayList<AdType>();
	private static ArrayList<AdList> adlist = new ArrayList<AdList>();
	
	
	String[][] typeString = null;
	public AdService()
	{

	}
	public AdService(Context context1)
	{
		this.context = context1;
	}
	
	
	public String[][] getAdType(String type)
	{
		// TODO Auto-generated method stub
		Server server = new Server();
		String s =  server.GetAdTypeData(type);
		Assistant assistant = new Assistant();
		ArrayList<Object> list = assistant.parseXml(assistant.upZipBaseString(s), AdType.class);
		listadtype.clear();
		for(Object obj:list){
			AdType item = (AdType)obj;
			listadtype.add(item);
		}
		typeString = new String[listadtype.size()][2];
		for (int i = 0; i < listadtype.size(); i++)
		{
			typeString[i][0]= listadtype.get(i).getTypeid().toString();
			typeString[i][1]= listadtype.get(i).getTypename().toString();
		}
		return typeString;
	}
	
	public String[][] getSearch(String text,String address,String style,String type)
	{
		
		Server server = new Server();
		String s =  server.GetAdList(text,address,style ,type);
		Assistant assistant = new Assistant();
		ArrayList<Object> list = assistant.parseXml(assistant.upZipBaseString(s), AdList.class);
		adlist.clear();
		for(Object obj:list){
			AdList item = (AdList)obj;
			adlist.add(item);
		}
		typeString = new String[adlist.size()][AdList.class.getDeclaredFields().length];
		print.out("AdList.class.getDeclaredFields().length="+AdList.class.getDeclaredFields().length);
		for (int i = 0; i < adlist.size(); i++)
		{
			typeString[i][0]= adlist.get(i).getCode().toString();
			typeString[i][1]= adlist.get(i).getApplicant().toString();
			typeString[i][2]= adlist.get(i).getPhone().toString();
		}
		return typeString;
	}
	
	public Ad getAdInformation(String code)
	{
		Ad ad = new Ad();
		// TODO Auto-generated method stub
		Server server = new Server();
		String s =  server.GetAdInformation(code);
		Assistant assistant = new Assistant();
		ArrayList<Object> list = assistant.parseXml(assistant.upZipBaseString(s), Ad.class);
		listadInformation.clear();
		for(Object obj:list){
			ad = (Ad)obj;
//			listadInformation.add(item);
		}
//		typeString = new String[listadInformation.size()][15];
//		for (int i = 0; i < listadInformation.size(); i++)
//		{
//			typeString[i][0]= listadInformation.get(i).getCode().toString();
//			typeString[i][1]= listadInformation.get(i).getApplicant().toString();
//			typeString[i][2]= listadInformation.get(i).getRegion().toString();
//			typeString[i][3]= listadInformation.get(i).getPhone().toString();
//			typeString[i][4]= listadInformation.get(i).getPerson().toString();
//			typeString[i][5]= listadInformation.get(i).getForm().toString();
//			typeString[i][6]= listadInformation.get(i).getType().toString();
//			typeString[i][7]= listadInformation.get(i).getDiduan().toString();
//			typeString[i][8]= listadInformation.get(i).getXingshi().toString();
//			typeString[i][9]= listadInformation.get(i).getLeibie().toString();
//			typeString[i][10]= listadInformation.get(i).getNature().toString();
//			typeString[i][11]= listadInformation.get(i).getIsurge().toString();
//			typeString[i][12]= listadInformation.get(i).getShcc().toString();
//			typeString[i][13]= listadInformation.get(i).getDeadline().toString();
//			typeString[i][14]= listadInformation.get(i).getIsdel().toString();
//		}
		return ad;
	}
	
	
	
	
}
