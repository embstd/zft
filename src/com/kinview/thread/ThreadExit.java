package com.kinview.thread;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.kinview.config.Config;

public class ThreadExit extends Thread{
	
	ProgressDialog progressDialog = null;
	Context context;
	
	public ThreadExit(Context context){
		this.context =context;
	}
	
	public void showProcess(){
		//�ر������߳�
		Config.boolean_thread_run = false;
		try{
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("���ڹرճ���,���Ժ�...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}catch(Exception e){
//			e.printStackTrace();
		}
		start();
	}
	
	public void run(){
		PackageManager pm = context.getPackageManager();
		String packageName="";
		PackageInfo pi = null;
		try {
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			packageName = pi.packageName;
		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(!Config.offLineLoad)	//����������ߵ�½,��֪ͨ�������˳�
//		}
		try {
			sleep(600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    activityManager.restartPackage(packageName);
	    
		System.exit(0);
	}
}