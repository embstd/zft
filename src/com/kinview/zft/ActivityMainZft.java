package com.kinview.zft;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.kinview.camera.ActivityPhoto;
import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.entity.NewCase;
import com.kinview.setting.ActivitySetting;
import com.kinview.zft.ad.ActivityAd;
import com.kinview.zft.casehandle.ActivityCaseHandle;
import com.kinview.zft.history.ActivityHistoryList;
import com.kinview.zft.newcase.ActivityNewCaseByOrganise;
import com.kinview.zft.ztc.ActivityZtc;

public class ActivityMainZft extends Activity
{
	private NewCase newcasebyfastphoto = null;
	private ImageView btn_iv1,btn_iv2,btn_iv3,btn_iv4,btn_iv5;
	private TextView tv_1,tv_2 ,btn_tv1,btn_tv2;
	
	private ImageView btn_ivad_czs,btn_ivztc_czs;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.maineasy);
		if(Config.cityTag.equals("cztnq"))
		{
			//天宁区
			setContentView(R.layout.tianning_maineasy);
		}
		else if (Config.cityTag.equals("czjts")) 
		{
			//金坛市
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			if(dm.widthPixels == 480 && dm.heightPixels == 800)
			  {
				  setContentView(R.layout.jintan_maineasy);
			  }  
			  else if(dm.widthPixels == 800 && dm.heightPixels == 1280)
			  {
				  setContentView(R.layout.jintan_maineasy1280);
			  }else {
				  setContentView(R.layout.jintan_maineasy);
			  }
		}
		else if (Config.cityTag.equals("czlys")) 
		{
			//溧阳市
			setContentView(R.layout.liyang_maineasy);
			  
		}
		else if (Config.cityTag.equals("czs")) 
		{
			//市支队
			print.out("加载常州市的Layout");
			setContentView(R.layout.czs_maineasy);
			  
		}
		else {
			//默认
			setContentView(R.layout.default_maineasy);
		}
	   load();
	}
	private void load()
	{
		
		
		btn_tv1 = (TextView)findViewById(R.id.itemfastphototv);
		btn_tv2 = (TextView)findViewById(R.id.itemsystemsettv);
		tv_1 = (TextView)findViewById(R.id.main_welcome_name);
		tv_2 = (TextView)findViewById(R.id.main_cg_name);
		tv_1.setText("欢迎登录，"+Config.name);
		tv_2.setText(Config.cgUnitName);
		// TODO Auto-generated method stub
		btn_iv1 = (ImageView)findViewById(R.id.itemnewcase);
		btn_iv2 = (ImageView)findViewById(R.id.itemmycase);
		btn_iv3 = (ImageView)findViewById(R.id.itemhistory);
		btn_iv4 = (ImageView)findViewById(R.id.itemfastphoto);
		btn_iv5 = (ImageView)findViewById(R.id.itemsystemset);
//		btn_iv6 = (ImageView)findViewById(R.id.main_iv6);
		if (Config.cityTag.equals("czs")) 
		{
			btn_ivad_czs=(ImageView)findViewById(R.id.itemad);
		  
			btn_ivztc_czs=(ImageView)findViewById(R.id.itemztc);
		}
		
		
		btn_iv1.setImageResource(R.drawable.easynewcase);
		btn_iv2.setImageResource(R.drawable.easymycase);
		btn_iv3.setImageResource(R.drawable.easyhistory);
		btn_iv4.setImageResource(R.drawable.easyfastcase);
		btn_iv5.setImageResource(R.drawable.easysystemset);
//		btn_iv6.setImageResource(R.drawable.zft_main_6);
		if (Config.cityTag.equals("czs")) 
		{
			btn_ivad_czs.setImageResource(R.drawable.czs_ad);;
		  
			btn_ivztc_czs.setImageResource(R.drawable.czs_ztc);
		}
		
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
				if(v.getId() == btn_iv1.getId()){
					//违章查处
				Intent it  = new Intent(ActivityMainZft.this,ActivityNewCaseByOrganise.class);
//					Intent it = new Intent(ActivityMainZft.this, ActivityAd.class);
//					Intent it = new Intent(ActivityMainZft.this, ActivityZtc.class);
					startActivity(it);
				}else if(v.getId() == btn_iv2.getId()){
					//案件办理
					Intent it = new Intent(ActivityMainZft.this, ActivityCaseHandle.class);
					startActivity(it);
				}else if(v.getId() == btn_iv3.getId()){
					//历史记录
					Intent it = new Intent(ActivityMainZft.this, ActivityHistoryList.class);
					startActivity(it);
				}else if(v.getId() == btn_iv4.getId()){
					//快速取证
					fastPhoto();
				}else if(v.getId() == btn_iv5.getId()){
					//系统设置
					 systemSeting();
				}else if(v.getId() == btn_tv1.getId()){
					//快速取证
					fastPhoto();
				}else if(v.getId() == btn_tv2.getId()){
					//系统设置
					 systemSeting();
				}else if(v.getId() == btn_ivad_czs.getId()){
					//广告
					Intent it = new Intent(ActivityMainZft.this, ActivityAd.class);
					startActivity(it);
				}else if(v.getId() == btn_ivztc_czs.getId()){
					//渣土车
					Intent it = new Intent(ActivityMainZft.this, ActivityZtc.class);
					startActivity(it);
				}
				
			}
		};
		
		btn_iv1.setOnTouchListener(otl);
		btn_iv2.setOnTouchListener(otl);
		btn_iv3.setOnTouchListener(otl);
		btn_iv4.setOnTouchListener(otl);
		btn_iv5.setOnTouchListener(otl);
//		btn_iv6.setOnTouchListener(otl);
//		btn_tv1.setOnTouchListener(otl);
//		btn_tv2.setOnTouchListener(otl);
		
		btn_iv1.setOnClickListener(ocl);
		btn_iv2.setOnClickListener(ocl);
		btn_iv3.setOnClickListener(ocl);
		btn_iv4.setOnClickListener(ocl);
		btn_iv5.setOnClickListener(ocl);

		btn_tv1.setOnClickListener(ocl);
		btn_tv2.setOnClickListener(ocl);
		if (Config.cityTag.equals("czs")) 
		{
			btn_ivad_czs.setOnClickListener(ocl);
		    btn_ivztc_czs.setOnClickListener(ocl);
		}
		
	}
	
	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			}
		}
	};
	
	
	/**
	 * 设置菜单
	 */
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		// Menus.initMenu(this, menu);
		initMenu(this, menu);
		return true;
	}

	public static void initMenu(Context context, Menu menu)
	{
		if (context.getClass().equals(ActivityMainZft.class))
		{
			menu.add(0, 0, 0, "退出").setIcon(R.drawable.newcase_tab3);
		}
	}

	/**
	 * 菜单处理
	 */
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Menus.actionMenu(this, null, item.gvetItemId());
		actionMenu(this, null, item.getItemId());
		return super.onOptionsItemSelected(item);
	}

	public void actionMenu(Context context, Handler mHandler, int menuId)
	{
		if (context.getClass().equals(ActivityMainZft.class))
		{
			switch (menuId)
			{
			   case 0: // 退出
				 System.exit(0);
				 break;
			}

		}
	}
	
	
	public void fastPhoto()
	{
		//快速取证
		Intent itimg = new Intent(ActivityMainZft.this,
				ActivityPhoto.class);
		Bundle b = new Bundle();
		b.putBoolean("stytle", true);
		b.putBoolean("op", true);
//		b.putString("data", newcasebyfastphoto.getPicList());
//		b.putInt("count", newcasebyfastphoto.getPicNum());
		itimg.putExtras(b);
		startActivityForResult(itimg, 0);
	}
	
	
	
	
	
	public void systemSeting()
	{
		Intent it = new Intent(ActivityMainZft.this, ActivitySetting.class);
		startActivity(it);
	}
	
}
