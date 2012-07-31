package com.kinview.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Login {
	
	public Context context;
	
	public Login(Context context){
		this.context=context;
	}
	
	public void saveLogin(String username,String password,boolean ck_savepwd,boolean ck_autologin ){
		SharedPreferences sp = context.getSharedPreferences("login",Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("username", username);
		if(ck_savepwd){
			editor.putString("password", password);
			editor.putString("savepwd", "yes");
		}else{
			editor.putString("password", "");
			editor.putString("savepwd", "no");
		}
		if(ck_autologin){
			editor.putString("autologin", "yes");
		}else{
			editor.putString("autologin", "no");
		}
		editor.putString("offlineusername", username);
		editor.putString("offlinepassword", password);
		editor.commit();
	}
	
	public boolean checkOffLine(String username,String password){
		SharedPreferences sp = context.getSharedPreferences("login",
				Context.MODE_PRIVATE);
		String name = sp.getString("offlineusername", "");
		String pwd = sp.getString("offlinepassword", "");
		if(name.equals("")){
			return false;
		}
		if(name.equals(username) && pwd.equals(password)){
			return true;
		}else{
			return false;
		}
		
	}
	
	public void cancelAutoLogin(){
		SharedPreferences sp = context.getSharedPreferences("login",Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("autologin", "no");
		editor.commit();
	}
	
	public void savePassword(String password){
		SharedPreferences sp = context.getSharedPreferences("login",Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("password", password);
		editor.commit();
	}
	
	public int getUpdateState(){
		SharedPreferences sp = context.getSharedPreferences("login",
				Context.MODE_PRIVATE);
		return sp.getInt("updateState", 0);
	}
	
	public void saveUpdateState(int pid){
		SharedPreferences sp = context.getSharedPreferences("login",Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("updateState", pid);
		editor.commit();
	}
	
	//经典窗口,还是最新窗口
	public void chanageWindowStytle(String stytle){
		SharedPreferences sp = context.getSharedPreferences("login",Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("windowStytle", stytle);
		editor.commit();
	}
}
