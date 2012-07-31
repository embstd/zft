package com.kinview.zft;

import java.security.KeyStore.LoadStoreParameter;

import com.kinview.config.Msg;
import com.kinview.config.print;
import com.kinview.setting.ActivitySetting;
import com.kinview.zft.casehandle.ActivityCaseHandle;
import com.kinview.zft.history.ActivityHistory2;
import com.kinview.zft.newcase.ActivityNewCaseByOrganise;
import com.kinview.zft.newcase.ActivityNewCaseByPerson;

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
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityMainTemp extends Activity
{

	private ImageView btn_iv1, btn_iv2, btn_iv3, btn_iv4, btn_iv5, btn_iv6;
	private TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// setContentView(R.layout.maintemp);
		if (dm.widthPixels == 480 && dm.heightPixels == 800)
		{
			setContentView(R.layout.maintemp);
		}
		else if (dm.widthPixels == 800 && dm.heightPixels == 1280)
		{
			setContentView(R.layout.maintemp1280);
		}
		else
		{
			setContentView(R.layout.maintemp);
		}

		load();
	}

	private void load()
	{
		// TODO Auto-generated method stub
		btn_iv1 = (ImageView) findViewById(R.id.main_iv1);
		btn_iv2 = (ImageView) findViewById(R.id.main_iv2);
		btn_iv3 = (ImageView) findViewById(R.id.main_iv3);
		btn_iv4 = (ImageView) findViewById(R.id.main_iv4);
		// btn_iv5 = (ImageView)findViewById(R.id.main_iv5);
		// btn_iv6 = (ImageView)findViewById(R.id.main_iv6);

		btn_iv1.setImageResource(R.drawable.zft_main_1);
		btn_iv2.setImageResource(R.drawable.zft_main_2);
		btn_iv3.setImageResource(R.drawable.zft_main_3);
		btn_iv4.setImageResource(R.drawable.zft_main_6);
		// btn_iv5.setImageResource(R.drawable.zft_main_5);
		// btn_iv6.setImageResource(R.drawable.zft_main_6);

		OnTouchListener otl = new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				ImageView iv = (ImageView) v;
				int action = event.getAction();
				if (action == MotionEvent.ACTION_DOWN)
				{
					iv.setBackgroundResource(R.drawable.sutra_btbg);
				}
				else if (action == MotionEvent.ACTION_CANCEL
						|| action == MotionEvent.ACTION_UP)
				{
					iv.setBackgroundResource(0);
				}
				return false;
			}
		};

		OnClickListener ocl = new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (v.getId() == btn_iv1.getId())
				{
					// 违章查处
					Intent it = new Intent(ActivityMainTemp.this,
							ActivityNewCaseByOrganise.class);
					startActivity(it);
				}
				else if (v.getId() == btn_iv2.getId())
				{
					// 案件办理
					Intent it = new Intent(ActivityMainTemp.this,
							ActivityCaseHandle.class);
					startActivity(it);
				}
				else if (v.getId() == btn_iv3.getId())
				{
					// 历史记录
					Intent it = new Intent(ActivityMainTemp.this,
							ActivityHistory2.class);
					startActivity(it);
				}
				else if (v.getId() == btn_iv4.getId())
				{
					// 系统设置
					// 启动设置中心
					Intent it = new Intent(ActivityMainTemp.this,
							ActivitySetting.class);
					startActivity(it);
					// actionClickMenuItem1();
				}
			}
		};

		btn_iv1.setOnTouchListener(otl);
		btn_iv2.setOnTouchListener(otl);
		btn_iv3.setOnTouchListener(otl);
		btn_iv4.setOnTouchListener(otl);

		btn_iv1.setOnClickListener(ocl);
		btn_iv2.setOnClickListener(ocl);
		btn_iv3.setOnClickListener(ocl);
		btn_iv4.setOnClickListener(ocl);

	}

	private Handler myHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
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
		if (context.getClass().equals(ActivityMainTemp.class))
		{
			menu.add(0, 0, 0, "退出").setIcon(R.drawable.newcase_tab3);
		}

	}

	/**
	 * 菜单处理
	 */
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Menus.actionMenu(this, null, item.getItemId());
		actionMenu(this, null, item.getItemId());
		return super.onOptionsItemSelected(item);
	}

	public void actionMenu(Context context, Handler mHandler, int menuId)
	{
		if (context.getClass().equals(ActivityMainTemp.class))
		{
			switch (menuId)
			{
			case 0: // 退出
				System.exit(0);
				break;
			}

		}
	}

}
