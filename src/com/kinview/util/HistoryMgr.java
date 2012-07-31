package com.kinview.util;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kinview.config.DB;
import com.kinview.config.print;
import com.kinview.entity.NewCase;

public class HistoryMgr
{
	 private final String createSql3 ="create table  if not exists    Question (questionID  varchar(50), typeid  int, organise  varchar(50),fzr  varchar(50),zw   varchar(50),otel   varchar(50),oadd   varchar(255),person   varchar(50),sex   varchar(50),age   varchar(50),idnumber   varchar(50),ptel  varchar(50),padd   varchar(255),thetime   char(20),address   varchar(255),creater  varchar(50),createtime  varchar(100),"
                                      + " intro  text,locker  int,areaid   int,fromtype   varchar(40),lsh  varchar(50),cid   int,state  int,rid  int,cordx  varchar(50),cordy  varchar(50),firstUpTime  varchar(100),succeedUpTime  varchar(100),videoNum  varchar(100),picNum  varchar(100),flag  varchar(50))";
     private final String createSql4 ="create table   if not exists     pic (questionID  varchar(50), picName   varchar(50),picPath     varchar(100),createId   varchar(100))";
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
 	 
 	 public HistoryMgr (Context context)
 	 {
 		context2 = context;
 		db = new DB(context, createSql3);
		execSQL(createSql4); // 检查表格是创建
		execSQL(createSql5); // 检查表格是创建
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

 	public void insertDataByYwb(String table_name, String Field, String Value)
	{

		String sql = "insert into " + table_name + "(" + Field + ")values("
				+ Value + ");";
		execSQL(sql);
	}
	
	public void delete(String id)
	{
		String sql = "delete from " + table_name + " where id='" + id + "';"; // del
		// 0为未删除
		execSQL(sql);
	}
	
	public String[][] getQuestionString(String creater,String flag)
	{
		print.out("getQuestionString()方法开始！");
	   String whereString  =  "creater ='" + creater+"'"+flag;
	   print.out("whereString="+whereString);
		
		String col[] =
		{ "questionID", "organise","person","createtime","flag" };
		SQLiteDatabase sdb = db.mOpenHelper.getReadableDatabase();
		Cursor cur = sdb.query("Question", col, whereString, null, null,
				null, null);
		int num = cur.getCount();
		print.out("cur.getCount() ="+ num);
		String[][] Result = new String[num][5];
		
		cur.moveToFirst();
		for (int j = 0; j < num; j++)
		{
			
			cur.moveToFirst();
			for (int i = 0; i < 5; i++)
			{
//				print.out("j="+j+";    i="+i);
				if(i == 0)
				{
					cur.moveToPosition(j);
				    int nameColumn = cur.getColumnIndex("questionID");
					Result[j][i] = cur.getString(nameColumn);
				}else if (i==1) {
					cur.moveToPosition(j);
					int nameColumn = cur.getColumnIndex("organise");
					if(cur.isNull(cur.getColumnIndex("organise")))
					{
						Result[j][i] ="";
					}
					else 
					{
						Result[j][i] = cur.getString(nameColumn);
					}
                }else if (i==2) {
                	cur.moveToPosition(j);
					int nameColumn = cur.getColumnIndex("person");
					if(cur.isNull(cur.getColumnIndex("person")))
					{
						Result[j][i] ="";
					}
					else 
					{
						Result[j][i] = cur.getString(nameColumn);
					}
					
				}else if (i==3) {
					cur.moveToPosition(j);
					int nameColumn = cur.getColumnIndex("createtime");
					Result[j][i] = cur.getString(nameColumn);
                }else if (i==4) {
                	cur.moveToPosition(j);
					int nameColumn = cur.getColumnIndex("flag");
					Result[j][i] = cur.getString(nameColumn);
                    
				}
				print.out(Result[j][i].toString());
			}
			cur.moveToNext();
		}
		cur.close();
		sdb.close();
		return Result;
	}

	public NewCase getCaseInfoByOrganise(String caseid)
	{
		NewCase  newCase = new NewCase();
		String whereString  =  "questionID ='" + caseid+"'";
		String col[] =
		{ "typeid","organise","fzr","zw","otel","oadd","thetime","address","intro","createtime","picNum","lsh"};
		SQLiteDatabase sdb = db.mOpenHelper.getReadableDatabase();
		Cursor cur = sdb.query("Question", col, whereString, null, null,
				null, null);
		int num = cur.getCount();
		cur.moveToFirst();
        int nameColumn = cur.getColumnIndex("typeid");
     	newCase.setType(Integer.valueOf(cur.getString(nameColumn)));
		
		  nameColumn = cur.getColumnIndex("organise");
		  newCase.setOrganise(cur.getString(nameColumn));
		 
		  nameColumn = cur.getColumnIndex("fzr");
		  newCase.setFzr(cur.getString(nameColumn));
		
		  nameColumn = cur.getColumnIndex("zw");
		  newCase.setZw(cur.getString(nameColumn));
		
		  nameColumn = cur.getColumnIndex("otel");
		  newCase.setOtel(cur.getString(nameColumn));
		
		  nameColumn = cur.getColumnIndex("oadd");
		  newCase.setOadd(cur.getString(nameColumn));
		 
		  nameColumn = cur.getColumnIndex("thetime");
		  newCase.setThetime(cur.getString(nameColumn));
		 
		  nameColumn = cur.getColumnIndex("address");
		  newCase.setAddress(cur.getString(nameColumn));
		  
		  nameColumn = cur.getColumnIndex("intro");
		  newCase.setIntro(cur.getString(nameColumn));
		  
		  nameColumn = cur.getColumnIndex("createtime");
		  newCase.setCreateTime(cur.getString(nameColumn));
		  
		  nameColumn = cur.getColumnIndex("picNum");
		  newCase.setPicNum(Integer.valueOf(cur.getString(nameColumn)));
		  
		  nameColumn = cur.getColumnIndex("lsh");
		  newCase.setLsh(cur.getString(nameColumn));
		 
			cur.close();
			sdb.close();
		return newCase;
	}
	
	
	public NewCase getCaseInfoByPerson(String caseid)
	{
		NewCase  newCase = new NewCase();
		String whereString  =  "questionID ='" + caseid+"'";
		String col[] =
		{ "typeid","person","sex","age","idnumber","ptel","padd","thetime","address","intro","createtime","picNum","lsh" };
		SQLiteDatabase sdb = db.mOpenHelper.getReadableDatabase();
		Cursor cur = sdb.query("Question", col, whereString, null, null,
				null, null);
		int num = cur.getCount();
		cur.moveToFirst();
		  int nameColumn = cur.getColumnIndex("typeid");
		  newCase.setType(Integer.valueOf(cur.getString(nameColumn)));
		  nameColumn = cur.getColumnIndex("person");
		  newCase.setPerson(cur.getString(nameColumn));
		  nameColumn = cur.getColumnIndex("sex");
		  newCase.setSex(cur.getString(nameColumn));
		  nameColumn = cur.getColumnIndex("age");
		  newCase.setAge(cur.getString(nameColumn));
		  nameColumn = cur.getColumnIndex("idnumber");
		  newCase.setIdnumber(cur.getString(nameColumn));
          nameColumn = cur.getColumnIndex("ptel");
		  newCase.setPtel(cur.getString(nameColumn));
		  nameColumn = cur.getColumnIndex("padd");
		  newCase.setPadd(cur.getString(nameColumn));
		  nameColumn = cur.getColumnIndex("thetime");
		  newCase.setThetime(cur.getString(nameColumn));
		  nameColumn = cur.getColumnIndex("address");
		  newCase.setAddress(cur.getString(nameColumn));
		  nameColumn = cur.getColumnIndex("intro");
		  newCase.setIntro(cur.getString(nameColumn));
		  nameColumn = cur.getColumnIndex("createtime");
		  newCase.setCreateTime(cur.getString(nameColumn));
		  nameColumn = cur.getColumnIndex("picNum");
		  newCase.setPicNum(Integer.valueOf(cur.getString(nameColumn)));
		  nameColumn = cur.getColumnIndex("lsh");
		  newCase.setLsh(cur.getString(nameColumn));
			cur.close();
			sdb.close();
		return newCase;
	}
	
	
	public String getAnyou(String  n)
	{
		String whereString  =  "id ='" +n+"'";
		String col[] = {"typeName"};

		SQLiteDatabase sdb1 = db.mOpenHelper.getReadableDatabase();
		Cursor cur1 = sdb1.query("tb_type", col, whereString, null, null,
				null, null);
		cur1.moveToFirst();
		 int nameColumn = cur1.getColumnIndex("typeName");
		  n =(cur1.getString(nameColumn));
		  cur1.close();
			sdb1.close();
		  return n;
	}

	public void cleardata()
	{
		// TODO Auto-generated method stub
		String sql = "delete from Question ";
		execSQL(sql);
		sql = "delete from  pic";
		execSQL(sql);
	}


}
