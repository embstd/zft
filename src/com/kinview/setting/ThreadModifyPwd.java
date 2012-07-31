package com.kinview.setting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;

import com.kinview.config.Msg;
import com.kinview.webservice.CommonResult;
import com.kinview.webservice.Server;

public class ThreadModifyPwd extends Thread{
	ProgressDialog progressDialog = null;
	Activity activity;
	String newPwd ="";
	String oldPwd ="";
	Handler mHandler ;
	public ThreadModifyPwd(Activity activity,Handler mHandler,String oldPwd,String newPwd){
		this.activity =activity;
		this.oldPwd = oldPwd;
		this.newPwd = newPwd;
		this.mHandler = mHandler;
	}
	
	public void showProcess(){
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("正在修改密码,请稍候...");
		progressDialog.setIndeterminate(true);
		progressDialog.show();
		start();
	}
	
	public void run(){
		CommonResult cr=null;
		try{
//			cr= Server.login("hourf", "1");
			cr= Server.modifyPwd(oldPwd, newPwd);
		}catch(Exception e){
			e.printStackTrace();
			//网络访问不了
			progressDialog.dismiss();
			mHandler.sendMessage(mHandler.obtainMessage(Msg.ERROR_SERVER_CONNECT));
			return;
		}
//		print.out("cr.getErrorCode() ="+cr.getErrorCode());
//		print.out("getErrorDesc() ="+cr.getErrorDesc());
//		print.out("getResultStr() ="+cr.getResultStr());
		if(cr.getErrorCode().equals("0")){
			mHandler.sendMessage(mHandler.obtainMessage(Msg.REQUEST_SUCCESS,newPwd));
//			mHandler.sendMessageDelayed(mHandler.obtainMessage(Msg.MODIFYPWD_SUCCESS,newPwd), 0);
		}else if(cr.getErrorCode().equals("1")){
			mHandler.sendMessage(mHandler.obtainMessage(Msg.REQUEST_ERROR,cr.getErrorDesc()));
//			mHandler.sendMessageDelayed(mHandler.obtainMessage(Msg.MODIFYPWD_ERROR,cr.getErrorDesc()), 0);
//			mHandler.sendMessageDelayed(mHandler.obtainMessage(Msg.MODIFYPWD_ERROR,0), 0);
		}
		progressDialog.dismiss();
	}
}