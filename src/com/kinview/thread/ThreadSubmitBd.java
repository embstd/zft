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
import com.kinview.entity.NewCase;
import com.kinview.webservice.CommonResult;
import com.kinview.webservice.Server;

public class ThreadSubmitBd extends Thread {
	private Handler mHandler;
	private ProgressDialog progressDialog = null;
//	private int flag;	//0,默认刷新,1用户刷新,2后台刷新
	private int aj_id;
	private String bd_id;
	private String bd_name;
	private String param;
	
	
	public void showProcess(Context context,Handler mHandler,
			int aj_id, String bd_id, String bd_name, String param){
		this.mHandler = mHandler;
		this.aj_id = aj_id;
		this.bd_id = bd_id;
		this.bd_name = bd_name;
		this.param = param;
		try{
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("正在提交数据,请稍候...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
			start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		CommonResult result =null;
		result = get(1);
		if(result!=null){
			if(result.getErrorCode().equals("0")){
				//审批成功
				mHandler.sendMessage(mHandler.obtainMessage(Msg.REQUEST_SUCCESS));
			}else if(result.getErrorCode().equals("1")){
				mHandler.sendMessage(mHandler.obtainMessage(Msg.REQUEST_ERROR,result.getErrorDesc()));
			}
		}else{
			//服务器读取失败
			mHandler.sendMessage(mHandler.obtainMessage(Msg.ERROR_SERVER_CONNECT));
		}
		try {
			progressDialog.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Config.threadSubmitBd = null;
	}
	
	public CommonResult get(int i){
		CommonResult result = null;
		try {
			result = Server.submitBd(aj_id, bd_id, bd_name, param);
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
