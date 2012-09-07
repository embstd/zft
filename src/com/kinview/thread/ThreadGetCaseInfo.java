package com.kinview.thread;

import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.kinview.assistant.Assistant;
import com.kinview.config.Config;
import com.kinview.config.Msg;
import com.kinview.config.print;
import com.kinview.entity.NewCase;
import com.kinview.util.Case;
import com.kinview.webservice.CommonResult;
import com.kinview.webservice.Server;
import com.kinview.zft.casehandle.ActivityCaseHandle;
import com.kinview.zft.casehandle.ActivityFormProvide;

public class ThreadGetCaseInfo extends Thread {
	private Handler mHandler;
	private ProgressDialog progressDialog = null;
	private int flag;	//0,默认刷新,1用户刷新,2后台刷新
	private int caseId;
	
	public void showProcess(Context context,Handler mHandler,int flag, int caseId){
		this.caseId = caseId;
		this.flag = flag;
		this.mHandler = mHandler;
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
			String xml = Assistant.upZipBaseString(result.getResultStr());
			Log.i("zft", "xml111: "+xml);
			//mHandler.sendMessage(mHandler.obtainMessage(Msg.ACTIVITY_SHOWTOAST,xml));
			Config.currentNewCaseList.clear();
			ArrayList<Object> list = Assistant.parseXml(xml, NewCase.class);
			for(Object obj:list){
				NewCase item = (NewCase)obj;
				Config.currentNewCaseList.add(item);
			}
			//记录最后刷新时间
			Config.event_refresh_start = (new Date()).getTime();
			if(flag==0){
				//系统刷新,需要显示list
//				mHandler.sendMessage(mHandler.obtainMessage(Msg.LISTVIEW_SHOW));
				mHandler.sendMessage(mHandler.obtainMessage(Msg.GETCASEINFO));
			}else if(flag==1){
				//用户刷新,不需要再显示list
				mHandler.sendMessage(mHandler.obtainMessage(Msg.LISTVIEW_REFRESH));
			}else if(flag==2){
				//后台刷新,不需要再显示list
				mHandler.sendMessage(mHandler.obtainMessage(Msg.LISTVIEW_REFRESH));
			}
			
			//测试历史记录
//			Config.eventMgr.save(Config.listEvent);
//			Config.eventMgr.load();
			//测试历史记录结束
			
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
		Config.threadGetCaseInfo = null;
//		activityCaseHandle.threadGetCase = null;
	}
	
	public CommonResult get(int i){
//		User user = Config.user;
		CommonResult result = null;
		try {
//			if(Config.test==1){
				result = Server.getCaseInfo(caseId);
//			}else{
//				result = test();
//				Config.test=1;
//			}
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
