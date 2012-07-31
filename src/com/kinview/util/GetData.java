package com.kinview.util;

import java.util.ArrayList;

import android.R.integer;
import android.R.string;
import android.app.Service;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kinview.assistant.Assistant;
import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.entity.NewCase;
import com.kinview.entity.TbType;
import com.kinview.webservice.CommonResult;
import com.kinview.webservice.Server;
import com.kinview.webservice.WebService;

public class GetData
{
	public static  Boolean data = true;
    
	Context  context;
	public GetData(Context context1)
	{
		// TODO Auto-generated constructor stub
       print.out("GetData下context1 ="+context1);
		context = context1;
	}
    public GetData ()
	{
		
	}
    
	public void  getData() throws Exception
	{
		SystemSettingMgr  mgrD = new SystemSettingMgr(context);
		mgrD.deleteDataByYwbByGetData();
		
		String xml = Server.GetData();
	    Assistant ass = new Assistant();
	    ArrayList<Object> list = ass.parseXml(ass.upZipBaseString(xml), TbType.class);
	    
	    Config.listtbtype.clear();
		for(Object obj:list){
			TbType item = (TbType)obj;
			Config.listtbtype.add(item);
		}
//		print.out("-----------------------------------------------------------------------------------");
//		print.out(Config.listtbtype.size());108
		for (int i = 0;  i<Config.listtbtype.size() ; i++)
		{
			StringBuffer buffer1 = new StringBuffer();
			buffer1.append("'"+Config.listtbtype.get(i).getId().toString()+"','"+Config.listtbtype.get(i).getTypeName().toString()+"','"+Config.listtbtype.get(i).getTypeId().toString()+"','"+
					Config.listtbtype.get(i).getForms().toString()+"','"+Config.listtbtype.get(i).getLay().toString()+"','"+Config.listtbtype.get(i).getRul().toString()+"'");
			SystemSettingMgr mgr = new SystemSettingMgr(context);  //过不去
			mgr.insertDataByYwb("tb_type", "id,typeName,typeId,forms,lay,rul", buffer1.toString());
			
		}
	}
	
	public Boolean getDBString() throws Exception
	{
		String sql = "select count()  from  tb_type ";
		SystemSettingMgr mgr = new SystemSettingMgr(context);  
		return mgr.selelctDB();
//		return true;
		
	}
	
	
}
