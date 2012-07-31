package com.kinview.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.CursorJoiner.Result;
import android.database.sqlite.SQLiteDatabase;

import com.kinview.config.Config;
import com.kinview.config.DB;
import com.kinview.config.print;
import com.kinview.config.DB.DatabaseHelper;

public class SystemSettingMgr
{
	private final String table_createSql = "create table if not exists systemsetting(id text,description text,url text);";
	private final String createSql2 = "create table  if not exists tb_type(id   varchar(20),typeName varchar(250),typeId varchar(20),forms  varchar(800),lay varchar(100),rul varchar(100))";
	private final String createSql3 ="create table  if not exists    Question (questionID  varchar(50), typeid  int, organise  varchar(50),fzr  varchar(50),zw   varchar(50),otel   varchar(50),oadd   varchar(255),person   varchar(50),sex   varchar(50),age   varchar(50),idnumber   varchar(50),ptel  varchar(50),padd   varchar(255),thetime   char(20),address   varchar(255),creater  varchar(50),createtime  varchar(100),"
                                   + " intro  text,locker  int,areaid   int,fromtype   varchar(40),lsh  varchar(50),cid   int,state  int,rid  int,cordx  varchar(50),cordy  varchar(50),firstUpTime  varchar(100),succeedUpTime  varchar(100),videoNum  varchar(100),picNum  varchar(100),flag  varchar(50))";
	private final String createSql4 ="create table   if not exists     pic (questionID  varchar(50), picName   varchar(50),picPath     varchar(100))";
	private final String createSql5 ="create table    if not exists    user (userID  varchar(20),userName  varchar(50),password   varchar(50),nameUser   varchar(50),GroupId   varchar(50),Branch   varchar(50),Station   varchar(50),"
    + "Idiograph  varchar(100),ZfzID      varchar(50),Branch_name    varchar(50),Branch_jgdm     varchar(50),Branch_phone    varchar(50),Branch_address   varchar(50),Branch_bossName   varchar(50),Branch_bossIdiograph   varchar(50),lastLoginTime    varchar(50),lastOutTime  varchar(50),state   varchar(50))";
	
	private final String table_name = "systemsetting";
	private String entrance_url = ""; // 记录服务器地址
	private String serverip_url = ""; // 记录服务器地址
	private String map_url = "";
	private String apk_url = "";

	// private Context context; // 用于保存config的调用
	private DB db;
	List<SystemSetting> termList = new ArrayList<SystemSetting>();
	public Context context2;

	public SystemSettingMgr(Context context)
	{
		context2 = context;
		
		// this.context = context;
		db = new DB(context, table_createSql);
		print.out("db = new DB(context,  table_createSql);");
		execSQL(table_createSql); // 检查表格是创建
		execSQL(createSql2); // 检查表格是创建
		execSQL(createSql3); // 检查表格是创建
		execSQL(createSql4); // 检查表格是创建
		execSQL(createSql5); // 检查表格是创建
	}

	public SystemSetting getItem(int position)
	{
		if (termList == null)
			return null;
		return termList.get(position);
	}

	public void delete(String id)
	{
		String sql = "delete from " + table_name + " where id='" + id + "';"; // del
		// 0为未删除
		execSQL(sql);
	}

	private void execSQL(String sql)// 执行SQL
	{
		if (sql.equals(""))
			return;

		SQLiteDatabase sdb = db.mOpenHelper.getWritableDatabase();
		try
		{
			sdb.execSQL(sql);
		}
		catch (SQLException e)
		{
		}
		finally
		{
			db.mOpenHelper.close();
		}

	}
	
	private int execSQLbackInt(String sql)// 执行SQL
	{
		int i = 0;
		if (sql.equals(""))
			return  i;

		SQLiteDatabase sdb = db.mOpenHelper.getWritableDatabase();
		try
		{
			sdb.execSQL(sql);
			i = 1;
		}
		catch (SQLException e)
		{
			i = 0;
		}
		finally
		{
			db.mOpenHelper.close();
		}
		return i;
	}
	

	public void save(SystemSetting ss)
	{
		if (ss.getId() == "")
			return;
		SQLiteDatabase sdb = db.mOpenHelper.getReadableDatabase();
		String col[] =
		{ "id" };
		String selection = "id='" + ss.getId() + "'";
		Cursor cur = sdb.query(table_name, col, selection, null, null, null,
				null);

		print.out("SystemSetting save selection=" + selection);
		int num = cur.getCount();
		// 用完后关闭数据库表连接
		cur.close();
		sdb.close();

		if (num == 0)
		{
			insert(ss);
		}
		else
		{
			update(ss);
			System.out.println("data aready at database");
		}
		reLoad();
	}

	public void update(SystemSetting ss)
	{

		String sql = "update " + table_name + " set " + "description='"
				+ ss.getDescription() + "'," + "url='" + ss.getUrl()
				+ "' where id='" + ss.getId() + "';";
		execSQL(sql);
	}

	private void insert(SystemSetting ss)
	{
		String sql = "insert into " + table_name
				+ "(id,description,url)values(" + "'" + ss.getId() + "','"
				+ ss.getDescription() + "','" + ss.getUrl() + "');"; // del
		// 0为未删除
		print.out("insert sql=" + sql);
		execSQL(sql);
	}
	
	
	

	public List<SystemSetting> loadSystemSetting()
	{
		termList.clear();

		SQLiteDatabase sdb = db.mOpenHelper.getReadableDatabase();
		String col[] =
		{ "id", "description", "url" };
		Cursor cur = sdb.query(table_name, col, "", null, null, null, null);
		int count = cur.getCount();
		print.out("SystemSetting load  count=  " + count);

		cur.moveToFirst();
		for (int i = 0; i < count; i++)
		{
			SystemSetting ss = new SystemSetting();
			ss.setId(cur.getString(0));
			ss.setDescription(cur.getString(1));
			ss.setUrl(cur.getString(2));
			termList.add(ss);
			cur.moveToNext();
		}
		cur.close();
		sdb.close();

		// 转换地址
		convertUrl();
		return termList;
	}

	public List<Map<String, Object>> getAllItemsForListView()
	{
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		int size = termList.size();
		for (int i = 0; i < size; i++)
		{
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("title", termList.get(i).getDescription());
			item.put("info", termList.get(i).getUrl());
			data.add(item);
		}
		return data;
	}

	private void convertUrl()
	{
		if (termList.size() == 0)
			return;
		int position = 0;
		int count = termList.size();
		print.out("load config  count---------= " + count);
		for (int i = 0; i < count; i++)
		{
			print.out("load config  i---------= " + i);
			if (termList.get(i).getId().equals("serverip"))
			{
				position = 1;
			}
			else if (termList.get(i).getId().equals("entrance_url"))
			{
				position = 2;
			}
			else if (termList.get(i).getId().equals("map_url"))
			{
				position = 3;
			}
			else if (termList.get(i).getId().equals("apk_url"))
			{
				position = 4;
			}
			print.out("load config  position---------= " + position);
			switch (position)
			{
			case 1:
				serverip_url = termList.get(i).getUrl();
				break;
			case 2:
				entrance_url = termList.get(i).getUrl();
				break;
			case 3:
				map_url = termList.get(i).getUrl();
				break;
			case 4:
				apk_url = termList.get(i).getUrl();
				break;
			default:
				break;
			}
		}

	}

	// ********************************************************************************//
	public void insertDataByYwb(String table_name, String Field, String Value)
	{

		String sql = "insert into " + table_name + "(" + Field + ")values("
				+ Value + ");";
		execSQL(sql);
	}
	
	/**
	 * table_name 表名
	 * FieldValue Set值
	 * Where   条件值
	 */
	public int updateDataByYwbBackInt(String table_name, String FieldValue, String Where)
	{

		String sql = "update " + table_name + "  set  " + FieldValue+"  where " + Where;
		print.out("SQL语句="+sql);
		int s =  execSQLbackInt(sql);
		return s;
	}
	
	//删除tb_type表下所有数据
	public void deleteDataByYwbByGetData()
	{
		String sql = "delete  from  tb_type  ";// del
		// 0为未删除
		execSQL(sql);
	}
	
	public int insertDataByYwbBackInt(String table_name, String Field, String Value)
	{

		String sql = "insert into " + table_name + "(" + Field + ")values("
				+ Value + ");";
		int s =  execSQLbackInt(sql);
		return s;
	}
	

	public boolean selelctDB()
	{
		String col[] =
		{ "id" };
		SQLiteDatabase sdb = db.mOpenHelper.getReadableDatabase();
		Cursor cur = sdb.query("tb_type", col, null, null, null, null, null);
		int num = cur.getCount();
		cur.close();
		sdb.close();
//		 print.out("总行数为:"+num);
//		 print.out("Config.tb_typeLingNum总行数为:"+Config.tb_typeLingNum);
		if (num == Config.tb_typeLingNum)
		{
			return false;
		}
		return true;
	}

	// http://dzh.mop.com/whbm/20120415/0/3SF8SOI295b1fcF3.shtml
	public String[][] getTb_typeString(String id)
	{
		String col[] =
		{ "id", "typeName" };
		SQLiteDatabase sdb = db.mOpenHelper.getReadableDatabase();
		Cursor cur = sdb.query("tb_type", col, "typeid =" + id, null, null,
				null, null);
		int num = cur.getCount();
		String[][] Result = new String[2][num];
		print.out("SystemSettingMgr类下getCaseTypeString()方法cur.getCount()="
				+ cur.getCount() + ";getColumnCount=" + cur.getColumnCount());

		cur.moveToFirst();
		for (int j = 0; j < 2; j++)
		{
			cur.moveToFirst();
			for (int i = 0; i < num; i++)
			{
				if(j == 0)
				{
					int nameColumn = cur.getColumnIndex("id");
					Result[j][i] = cur.getString(nameColumn);
					
					cur.moveToNext();
				}else if (j==1) {
					int nameColumn = cur.getColumnIndex("typeName");
					Result[j][i] = cur.getString(nameColumn);

					cur.moveToNext();
				}
				print.out(Result[j][i].toString());
			}
		}
		cur.close();
		sdb.close();
		return Result;
	}
	
	

	// ********************************************************************************//
	public String getEntrance_url()
	{
		return entrance_url;
	}

	public String getServerip_url()
	{
		return serverip_url;
	}

	public String getMap_url()
	{
		return map_url;
	}

	public String getApk_url()
	{
		return apk_url;
	}

	public void replaceToDefault()
	{

		SystemSetting ss;

		ss = new SystemSetting();
		ss.setId("entrance_url");
		ss.setDescription("请求地址");
		// ss.setUrl("/KinCgtYZServer/services/KinCgtService");
		ss.setUrl("/Service.asmx");
		save(ss);

		ss = new SystemSetting();
		ss.setId("serverip");
		ss.setDescription("服务器地址");
//		ss.setUrl("10.137.129.52:8019");
		ss.setUrl(Config.serverIpAddress);
//		 ss.setUrl("192.168.1.4:8019");
		// ss.setUrl("32.108.2.12:8081");
		save(ss);

		ss = new SystemSetting();
		ss.setId("map_url");
		ss.setDescription("地图地址");
		ss.setUrl("/KinCgtYZServer/update/Android/map/");
		// ss.setUrl("32.108.2.12:8081");
		save(ss);

		ss = new SystemSetting();
		ss.setId("apk_url");
		ss.setDescription("程序升级地址");
		// ss.setUrl("/KinCgtYZServer/update/Android/ApkUpdate.xml");
		
		if (Config.cityTag.equals("cztnq") || Config.cityTag.equals("czjts") || Config.cityTag.equals("czlys")){
			ss.setUrl("/update/ApkUpdate.xml");
		}else {
			ss.setUrl("/androidupdate/ApkUpdate.xml");
		}
		
		save(ss);

		reLoad();
	}

	private void reLoad()
	{
		loadSystemSetting();
		Config.entrance_str = this.getEntrance_url();
		Config.serverip = this.getServerip_url();
		Config.map_url = this.map_url;
		Config.apk_url = this.apk_url;
	}
}
