package com.kinview.config;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.Menu;
import android.app.Activity;
import android.app.AlertDialog;
import com.kinview.setting.ActivitySetting;
import com.kinview.setting.ActivitySystemSettingList;
import com.kinview.zft.ActivityLogin;
import com.kinview.zft.ActivityMain;
import com.kinview.zft.ActivityMainSutra;
import com.kinview.zft.R;

public class Menus
{

	public static void initMenu(Context context, Menu menu)
	{
		if (context.getClass().equals(ActivityLogin.class))
		{
			// 初始化登陆窗口
			menu.add(0, 0, 0, "设置").setIcon(R.drawable.menu_setting);
			menu.add(0, 1, 0, "退出").setIcon(R.drawable.menu_exit);
		}
		else if (context.getClass().equals(ActivityMain.class)
				|| context.getClass().equals(ActivityMainSutra.class))
		{
			// 主窗口
			menu.add(0, 0, 0, "设置").setIcon(R.drawable.menu_setting);
			menu.add(0, 1, 0, "切换窗口").setIcon(R.drawable.menu_stytle);
			menu.add(0, 2, 0, "退出").setIcon(R.drawable.menu_exit);
		}
		
	}

	public static void actionMenu(Context context, Handler mHandler, int menuId)
	{
		if (context.getClass().equals(ActivityLogin.class))
		{
			// 取消自动登陆
			mHandler.sendMessage(mHandler
					.obtainMessage(Msg.LOGIN_CANCELAUTOLOGIN));
			switch (menuId)
			{
			case 0:
				// 设置
				startSettingSytem(context);
				break;
			case 1:
				// 退出
				Dialog.showDialog(Dialog.OKCANCEL, context, mHandler,
						Msg.ACTIVITY_EXIT, "退出", "你确定要退出?", 0, "");
				break;
			}
		}
		else if (context.getClass().equals(ActivityMain.class)
				|| context.getClass().equals(ActivityMainSutra.class))
		{
			switch (menuId)
			{
			case 0:
				// 设置
				startSetting(context);
				break;
			case 1:
				// 切换窗口
				if (Config.windowStytle.equals("0"))
				{
					Dialog.showDialog(Dialog.OKCANCEL, context, mHandler,
							Msg.ACTIVITY_CHANAGE, "切换窗口", "是否切换到经典窗口?", 0, "");
				}
				else
				{
					Dialog.showDialog(Dialog.OKCANCEL, context, mHandler,
							Msg.ACTIVITY_CHANAGE, "切换窗口", "切换到新版窗口?", 0, "");
				}
				break;
			case 2:
				// 退出
				Dialog.showDialog(Dialog.OKCANCEL, context, mHandler,
						Msg.ACTIVITY_EXIT, "退出程序", "确定要退出程序吗?", 0, "");
				break;
			}
		}
		
	}

	private static void startSettingSytem(Context context)
	{
		// 启动系统参数设置
		Intent it = new Intent(context, ActivitySystemSettingList.class);
		context.startActivity(it);
	}

	private static void startSetting(Context context)
	{
		// 启动设置中心
		Intent it = new Intent(context, ActivitySetting.class);
		context.startActivity(it);
	}

}
