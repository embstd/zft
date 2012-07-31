package com.kinview.zft;


import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Menus;
import com.kinview.config.Msg;
import com.kinview.config.print;
import com.kinview.setting.ActivitySetting;
import com.kinview.thread.ThreadExit;
import com.kinview.util.Login;

public class ActivityMainSutra extends ActivityGroup {

	public static final int ITEM0 = Menu.FIRST;
	public static final int ITEM1 = Menu.FIRST + 1;
	public static final int ITEM2 = Menu.FIRST + 2;
	public static final int ITEM3 = Menu.FIRST + 3;
	public static final int ITEM4 = Menu.FIRST + 4;
	public static final int ITEM5 = Menu.FIRST + 5;
	public static final int ITEM6 = Menu.FIRST + 6;
	
//	@SuppressWarnings("unchecked")
	private ImageView iv_b1,iv_b2,iv_b3,iv_b4,iv_b5,iv_b6,iv_b7,iv_b8,iv_b9;
	private TextView tv_task,tv_tip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainsutra);
		
		initPanel();
		init();
	}
	
	public void init(){
		
	}


	private void initPanel() {
		iv_b1 = (ImageView)findViewById(R.id.sutra_iv1);
		iv_b2 = (ImageView)findViewById(R.id.sutra_iv2);
		iv_b3 = (ImageView)findViewById(R.id.sutra_iv3);
		iv_b4 = (ImageView)findViewById(R.id.sutra_iv4);
		iv_b5 = (ImageView)findViewById(R.id.sutra_iv5);
		iv_b6 = (ImageView)findViewById(R.id.sutra_iv6);
		iv_b7 = (ImageView)findViewById(R.id.sutra_iv7);
		iv_b8 = (ImageView)findViewById(R.id.sutra_iv8);
		iv_b9 = (ImageView)findViewById(R.id.sutra_iv9);
		
		tv_task = (TextView)findViewById(R.id.sutra_tv_task);
		tv_tip = (TextView)findViewById(R.id.sutra_tv_tip);
		
		iv_b1.setImageResource(R.drawable.sutra_b1);
		iv_b2.setImageResource(R.drawable.sutra_b2);
		iv_b3.setImageResource(R.drawable.sutra_b3);
		iv_b4.setImageResource(R.drawable.sutra_b4);
		iv_b5.setImageResource(R.drawable.sutra_b5);
		iv_b6.setImageResource(R.drawable.sutra_b6);
		iv_b7.setImageResource(R.drawable.sutra_b7);
		iv_b8.setImageResource(R.drawable.sutra_b8);
		iv_b9.setImageResource(R.drawable.sutra_b9);
		
		OnTouchListener otl = new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView)v;
				int action = event.getAction();
				if(action == MotionEvent.ACTION_DOWN){
					iv.setBackgroundResource(R.drawable.sutra_btbg);
				}else if(action == MotionEvent.ACTION_CANCEL || action==MotionEvent.ACTION_UP){
					iv.setBackgroundResource(0);
				}
				return false;
			}
		};
		
		OnClickListener ocl = new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(v.getId() == iv_b1.getId()){
					//问题上报
//					actionClickMenuItem0();
					Intent it  = new Intent(ActivityMainSutra.this,ActivityWtcc.class);
					startActivity(it);
				}else if(v.getId() == iv_b2.getId()){
					//任务管理
//					actionTask();
				}else if(v.getId() == iv_b3.getId()){
					//历史记录
//					actionHistory();
				}else if(v.getId() == iv_b4.getId()){
					//今日提醒
//					actionClickMenuItem1();
				}else if(v.getId() == iv_b5.getId()){
					//单键拨号
//					actionClickMenuItem2();
				}else if(v.getId() == iv_b6.getId()){
					//同步
//					actionSync();
				}else if(v.getId() == iv_b7.getId()){
					//帮助
//					actionClickMenuItem4();
				}else if(v.getId() == iv_b8.getId()){
					//设置
//					actionClickMenuItem3();
				}else if(v.getId() == iv_b9.getId()){
					//打卡
//					actionLoginOut();
				}
			}
		};
		
		iv_b1.setOnTouchListener(otl);
		iv_b2.setOnTouchListener(otl);
		iv_b3.setOnTouchListener(otl);
		iv_b4.setOnTouchListener(otl);
		iv_b5.setOnTouchListener(otl);
		iv_b6.setOnTouchListener(otl);
		iv_b7.setOnTouchListener(otl);
		iv_b8.setOnTouchListener(otl);
		iv_b9.setOnTouchListener(otl);
		
		iv_b1.setOnClickListener(ocl);
		iv_b2.setOnClickListener(ocl);
		iv_b3.setOnClickListener(ocl);
		iv_b4.setOnClickListener(ocl);
		iv_b5.setOnClickListener(ocl);
		iv_b6.setOnClickListener(ocl);
		iv_b7.setOnClickListener(ocl);
		iv_b8.setOnClickListener(ocl);
		iv_b9.setOnClickListener(ocl);
		
	}

	public void showToast(String msg) {
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		toast.show();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		Menus.initMenu(this, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Menus.actionMenu(this, mHandler, item.getItemId());
		return super.onOptionsItemSelected(item);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Dialog.showDialog(Dialog.OKCANCEL, this, mHandler, Msg.ACTIVITY_EXIT, "退出程序", "确定要退出程序吗?",0,"");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void setCount(){
//		tv_task.setText("待办任务:  "+Config.listTask.size());
//		tv_tip.setText("今日提醒:  "+Config.listTip.size());
	}
	
	@Override
	protected void onResume() {
		print.out("onResume is call!");
		setCount();
		super.onResume();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		
		try{
			Bundle b = intent.getExtras();
			String logoutType = b.getString("loginout");
			if(logoutType!=null){
//				ThreadExit thread = new ThreadExit(this,logoutType);
//				thread.showProcess();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Msg.ACTIVITY_CHANAGE:
				//保存切换
				Login login = new Login(ActivityMainSutra.this);
				if(Config.windowStytle.equals("0")){
					login.chanageWindowStytle("1");
					Config.windowStytle = "1";
				}else{
					login.chanageWindowStytle("0");
					Config.windowStytle = "0";
				}
				//启动窗口
				Intent it = new Intent(ActivityMainSutra.this,ActivityMain.class);
				it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(it);
				finish();
				
				break;
			case Msg.ACTIVITY_EXIT:
				ThreadExit thread = new ThreadExit(ActivityMainSutra.this);
				thread.showProcess();
				break;
			}
		}
	};
}
