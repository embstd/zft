package com.kinview.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kinview.assistant.Assistant;
import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.entity.NewCase;
import com.kinview.zft.R;

import android.R.integer;
import android.R.string;
import android.content.Context;
import android.graphics.Color;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class HistoryService
{
	public static List<Map<String, Object>> list;
	Context context = null;
	int showState = 0;
	SimpleAdapter adapter = null;
	public HistoryService(Context context1, int state)
	{
		this.context = context1;
		this.showState = state;

	}

	// public ListAdapter getListAdapter()
	// {
	// SimpleAdapter adapter = new SimpleAdapter(context,
	// getItemsForList(showState), R.layout.history_list,
	// new String[] {"xu", "id","date" ,"color","eventDesc"}, new int[] {
	// R.id.history_list_xu,R.id.history_list_id,
	// R.id.history_list_date,
	// R.id.history_list_color,R.id.history_list_eventDesc});
	//
	// return null;
	// }

	public ListAdapter getListAdapter()
	{
//		SimpleAdapter adapter = new SimpleAdapter(context,
//				getItemsForList(showState), R.layout.copyhistory_list,
//				new String[]
//				{ "xu", "id", "name", "color", "time" }, new int[]
//				{ R.id.history_list_xu, R.id.history_list_id,
//						R.id.history_list_name, R.id.history_list_color,
//						R.id.history_list_time });
		adapter = null;
	    adapter = new SimpleAdapter(context,getItemsForList(list,showState),R.layout.historyitem,
				new String[]{"title","time","img","caseid","casetype"},
				new int[]{R.id.title,R.id.time,R.id.img,R.id.caseid,R.id.casetype});
	
		return adapter;
	}

	/**
	 * 
	 * @param state
	 *            0=全部 1=上报成功 2=上报失败 flag flag = 1
	 *            案件信息存储本地数据库成功，案件信息中有图片但图片未存储本地数据库成功 flag = 2
	 *            案件信息存储本地数据库成功，案件信息中未选择图片 flag = 3
	 *            案件信息存储本地数据库成功，案件信息中有图片，图片也存储本地数据库成功 flag = 4上报服务器失败 
	 *            flag = 5   上报服务器成功
	 *            
	 * @return
	 */
	public List<Map<String, Object>> getItemsForList(List<Map<String, Object>>list,int state)
	{
		print.out("111111111111111111111");
		String[][] temp = null;
		String flagSelete = "";
		switch (state)
		{
		case 1:
			flagSelete = "  and  flag = '5'";
			break;
		case 2:
			flagSelete = "  and  flag != '5'";
			break;
		default:
			flagSelete = "";
			break;
		}
		print.out("flagSelete=" + flagSelete);
		if(list==null){
			list = new ArrayList<Map<String, Object>>();
		}else{
			list.clear();
		}
		Map<String, Object> map = null;
		HistoryMgr mgr = new HistoryMgr(context);
		temp = mgr.getQuestionString(Config.user.getUserid(), flagSelete);
		if (temp == null)
		{
			print.out("temp == null");
		}
		print.out("temp.length=" + temp.length);
		// print.out("temp.length[0]="+temp[0].length);
		for (int i = 0; i < temp.length; i++)
		{
			for (int j = 0; j < temp[0].length; j++)
			{
				print.out("temp[" + i + "]" + "[" + j + "]"
						+ temp[i][j].toString());

				map = new HashMap<String, Object>();
				map.put("caseid", temp[i][0].toString());
				if (temp[i][2].length()>0)
				{
					map.put("title", temp[i][2].toString());
					map.put("casetype", "2");
				}
				else if (temp[i][1].length()>0)
				{
					map.put("title", temp[i][1].toString());
					map.put("casetype", "1");
				}
				map.put("img", getImg(Integer.valueOf(temp[i][4].toString())));
				map.put("time", temp[i][3].toString());
			}
			list.add(map);
		}
		// TODO Auto-generated method stub
//		print.out("list.size = " + list.size());
//		print.out("list.get(0) = " + list.get(0));
		return list;
	}

	// eventDesc=, id=012062716070171, date=有阿尔卡特点, color=-65536, xu=1

	/*
	 * 用于本地历史任务显示时,转换成显示数据时,显示的颜色
	 */
	public static int getMissionColor(int state)
	{
		int red = Color.parseColor("#FF0000");
		int green = Color.parseColor("#00FF00");
		int black = Color.parseColor("#333333");
		if (state == 11)
		{
			return black;
		}
		int shi = state / 10;
		int ge = state % 10;
		if (shi == 4 || ge == 4)
		{
			return red;
		}
		if (shi == 2 || ge == 2 || shi == 3 || ge == 3)
		{
			return green;
		}

		return red;
	}

	public static int getColor(int flag)
	{

		int color = 0;
		int red = Color.parseColor("#FF0000"); // 上报失败
		int green = Color.parseColor("#00FF00"); // 正在上报
		int black = Color.parseColor("#333333"); // 上报成功
		if (flag == 5)
		{
			color = black;
		}
		else if (flag == 4)
		{
			color = red;
		}
		else if (flag == 1  || flag == 2 || flag == 3)
		{
			color = green;
		}
		else
		{
			color = Color.parseColor("#FF0000"); // 上报失败
		}
		return color;
	}

	public int getImg(int flag)
	{
		int color = 0;
		if (flag == 5)
		{
			color = R.drawable.history_black;
		}
		else if (flag == 4)
		{
			color = R.drawable.history_red;
		}
		else if (flag == 1  || flag == 2 || flag == 3)
		{
		  color = R.drawable.history_green;;
		}
		else
		{
			color = R.drawable.history_red; // 上报失败
		}
		return color;
	}
	
	
	
	

	// public List<Map<String, Object>> getItemsForList(int showState) {
	// print.out("showState="+showState);
	// int State = showState;
	// List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	// HashMap<String, Object> item;
	//
	// int xu = 0;
	// for (int i = 0; i < Config.listHistory.size(); i++) {
	// History term = Config.listHistory.get(i);
	// print.out("term.getState()="+term.getState());
	// if (checkState(term.getState())) {
	// // print.out("History getItemsForList all add");
	// xu++;
	// item = new HashMap<String, Object>();
	// item.put("xu", "" + xu);
	//				
	// //1问题上报,2核实上报,3核查上报,6专项普查上报
	// // if(term.getTaskType().equals("1")){
	// // type="问题上报";
	// // }else if(term.getTaskType().equals("2")){
	// // type="核实上报";
	// // }else if(term.getTaskType().equals("3")){
	// // type="核查上报";
	// // }else if(term.getTaskType().equals("6")){
	// // type="专项普查上报";
	// // }else{
	// // type="未知类型";
	// // }
	//				
	// String type = Assistant.getHistoryTypeName(term.getTaskType());
	// String id = term.getTaskNum();
	// if(id.indexOf(",")!=-1){
	// id = id.split(",")[0];
	// }
	// item.put("id", "ID:" +id);
	// String desc = term.getEventDesc();
	// if(desc.length()>15){
	// desc = desc.substring(0, 12) +"...";
	// }
	// item.put("eventDesc", type + ":"+ desc); //问题描述
	//				
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// Date date =null;
	// try {
	// date = sdf.parse(term.getDate());
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//				
	// String s="";
	// String temp="";
	//				
	// sdf = new SimpleDateFormat("MM月");
	// temp = sdf.format(date);
	// if(temp.indexOf("0")==0){
	// temp = temp.substring(1,temp.length());
	// }
	// s += temp;
	//				
	// sdf = new SimpleDateFormat("dd日");
	// temp = sdf.format(date);
	// if(temp.indexOf("0")==0){
	// temp = temp.substring(1,temp.length());
	// }
	// s += temp;
	//				
	// sdf = new SimpleDateFormat("HH点");
	// temp = sdf.format(date);
	// if(temp.indexOf("0")==0){
	// temp = temp.substring(1,temp.length());
	// }
	// s += "\n"+temp;
	//				
	// sdf = new SimpleDateFormat("mm分");
	// temp = sdf.format(date);
	// if(temp.indexOf("0")==0){
	// temp = temp.substring(1,temp.length());
	// }
	// s += temp;
	// // item.put("date", term.getDate());
	// item.put("date", s);
	// // item.put("state", type + " " +
	// Assistant.getMissionDesc(term.getState()));
	// item.put("color", Assistant.getMissionColor(term.getState()));
	// data.add(item);
	// }
	//
	// }
	//
	// return data;
	// }

}
