package com.kinview.config;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

public class Dialog {
	public static int message;
	public static Handler mHandler=null;
	public static final int OK = 0;
	public static final int OKCANCEL = 1;

	
	public static void showDialog(int type,Context context,Handler mHandler2,int msg ,String title,String content,int iconId,String btnTxt) {
		//type显示某种类型窗口0,只有确定,1确定加取消
		mHandler = mHandler2;
		message = msg;
		try{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle(title);
			builder.setMessage(content);
			if(iconId!=0){
				builder.setIcon(iconId);
			}
			if(OK==type){
				String txt = "确  定";
				btnTxt = btnTxt.trim();
				if(!btnTxt.equals("")){
					txt = btnTxt;
				}
				builder.setPositiveButton(txt,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								if(mHandler!=null){
									mHandler.sendMessage(mHandler.obtainMessage(message));
									mHandler = null;
								}
							}
						});
			}else if(OKCANCEL==type){
				builder.setPositiveButton("确  定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								if(mHandler!=null){
									mHandler.sendMessage(mHandler.obtainMessage(message));
									mHandler = null;
								}
							}
						});
				builder.setNegativeButton("取  消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
							}
						});
			}
			builder.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void showDialogByNewCase(int type,Context context,Handler mHandler2,int msg ,String title,String content,int iconId,String btnTxt) {
		//type显示某种类型窗口0,只有确定,1确定加取消
		mHandler = mHandler2;
		message = msg;
		try{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle(title);
			builder.setMessage(content);
			if(iconId!=0){
				builder.setIcon(iconId);
			}
			if(OK==type){
				String txt = "确  定";
				btnTxt = btnTxt.trim();
				if(!btnTxt.equals("")){
					txt = btnTxt;
				}
				builder.setPositiveButton(txt,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								if(mHandler!=null){
									mHandler.sendMessage(mHandler.obtainMessage(message));
									mHandler = null;
								}
							}
						});
			}else if(OKCANCEL==type){
				builder.setPositiveButton("确  定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								if(mHandler!=null){
									mHandler.sendMessage(mHandler.obtainMessage(message));
									mHandler = null;
								}
							}
						});
				builder.setNegativeButton("取  消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
							}
						});
			}
			builder.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
}
