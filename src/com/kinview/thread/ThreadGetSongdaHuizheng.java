package com.kinview.thread;

import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import com.kinview.assistant.Assistant;
import com.kinview.config.Config;
import com.kinview.config.Msg;
import com.kinview.config.print;
import com.kinview.entity.SongdaHuizheng;
import com.kinview.util.Case;
import com.kinview.webservice.CommonResult;
import com.kinview.webservice.Server;

public class ThreadGetSongdaHuizheng extends Thread {
	private Handler mHandler;
	private ProgressDialog progressDialog = null;
	private int flag;	//0,默认刷新,1用户刷新,2后台刷新
//	private ActivityCaseHandle  activityCaseHandle;
	private int caseId;
	
	public void showProcess(Context context,Handler mHandler,int flag, int caseId){
		this.caseId = caseId;
		this.flag = flag;
		this.mHandler = mHandler;
//		this.activityCaseHandle = (ActivityCaseHandle)context;
		try{
			if(flag!=2){
				progressDialog = new ProgressDialog(context);
				progressDialog.setMessage("正在获取数据,请稍候...");
				progressDialog.setIndeterminate(true);
				progressDialog.show();
			}else{
				mHandler.sendMessage(mHandler.obtainMessage(Msg.TITLE_PROGRESS_START));
			}
			start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		CommonResult result =null;
		result = get(1);
		if(result!=null){
			print.out("str: "+ result.getResultStr());
			String xml = Assistant.upZipBaseString(result.getResultStr());
			print.out("xml: "+ xml);
			//mHandler.sendMessage(mHandler.obtainMessage(Msg.ACTIVITY_SHOWTOAST,xml));
			ArrayList<Object> list = Assistant.parseXml(xml, SongdaHuizheng.class);
			//Config.listEvent.clear();
			//activityCaseHandle.currentCaseList.clear();
			print.out("size:ddddd"+list.size());
//			int i=0;
			Config.currentSongdaHuizhengList.clear();
			for(Object obj:list){
				//Event item = (Event)obj;
				//Config.listEvent.add(item);
				SongdaHuizheng item = (SongdaHuizheng)obj;
				Config.currentSongdaHuizhengList.add(item);
//				print.out("i="+i);
//				print.out("getAddress="+item.getAddress());
//				print.out("getOrganise="+item.getOrganise());
//				print.out("getAge="+item.getAge());
//				print.out("getFh1="+item.getFh1());
//				i++;
			}
			//记录最后刷新时间
			Config.event_refresh_start = (new Date()).getTime();
			if(flag==0){
				//系统刷新,需要显示list
//				mHandler.sendMessage(mHandler.obtainMessage(Msg.LISTVIEW_SHOW));
				mHandler.sendMessage(mHandler.obtainMessage(Msg.GET_SONGDA_HUIZHENG_SUCCESS));
			}else if(flag==1){
				//用户刷新,不需要再显示list
				mHandler.sendMessage(mHandler.obtainMessage(Msg.LISTVIEW_REFRESH));
			}else if(flag==2){
				//后台刷新,不需要再显示list
				mHandler.sendMessage(mHandler.obtainMessage(Msg.LISTVIEW_REFRESH));
			}
			
		}else{
			//服务器读取失败
			if(flag!=2){//后台刷新,不提示错误
				mHandler.sendMessage(mHandler.obtainMessage(Msg.ERROR_SERVER_CONNECT));
			}
		}
		if(flag!=2){
			try {
				progressDialog.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			mHandler.sendMessage(mHandler.obtainMessage(Msg.TITLE_PROGRESS_STOP));
		}
		Config.threadGetSongdaHuizheng = null;
//		activityCaseHandle.threadGetCase = null;
	}
	
	public CommonResult get(int i){
		CommonResult result = null;
		try {
//			print.out("Config.user.getUserName()="+Config.user.getUserName() + " timePeriod: " + timePeriod);
				result = Server.getSongdaHuizhengInfo(caseId);
		}catch (Exception e) {
			e.printStackTrace();
			if(i==1){
				try {
					sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				result = get(2); 
			}
		}
		
		return result;
	}
}
