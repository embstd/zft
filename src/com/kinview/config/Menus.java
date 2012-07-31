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
			// ��ʼ����½����
			menu.add(0, 0, 0, "����").setIcon(R.drawable.menu_setting);
			menu.add(0, 1, 0, "�˳�").setIcon(R.drawable.menu_exit);
		}
		else if (context.getClass().equals(ActivityMain.class)
				|| context.getClass().equals(ActivityMainSutra.class))
		{
			// ������
			menu.add(0, 0, 0, "����").setIcon(R.drawable.menu_setting);
			menu.add(0, 1, 0, "�л�����").setIcon(R.drawable.menu_stytle);
			menu.add(0, 2, 0, "�˳�").setIcon(R.drawable.menu_exit);
		}
		
	}

	public static void actionMenu(Context context, Handler mHandler, int menuId)
	{
		if (context.getClass().equals(ActivityLogin.class))
		{
			// ȡ���Զ���½
			mHandler.sendMessage(mHandler
					.obtainMessage(Msg.LOGIN_CANCELAUTOLOGIN));
			switch (menuId)
			{
			case 0:
				// ����
				startSettingSytem(context);
				break;
			case 1:
				// �˳�
				Dialog.showDialog(Dialog.OKCANCEL, context, mHandler,
						Msg.ACTIVITY_EXIT, "�˳�", "��ȷ��Ҫ�˳�?", 0, "");
				break;
			}
		}
		else if (context.getClass().equals(ActivityMain.class)
				|| context.getClass().equals(ActivityMainSutra.class))
		{
			switch (menuId)
			{
			case 0:
				// ����
				startSetting(context);
				break;
			case 1:
				// �л�����
				if (Config.windowStytle.equals("0"))
				{
					Dialog.showDialog(Dialog.OKCANCEL, context, mHandler,
							Msg.ACTIVITY_CHANAGE, "�л�����", "�Ƿ��л������䴰��?", 0, "");
				}
				else
				{
					Dialog.showDialog(Dialog.OKCANCEL, context, mHandler,
							Msg.ACTIVITY_CHANAGE, "�л�����", "�л����°洰��?", 0, "");
				}
				break;
			case 2:
				// �˳�
				Dialog.showDialog(Dialog.OKCANCEL, context, mHandler,
						Msg.ACTIVITY_EXIT, "�˳�����", "ȷ��Ҫ�˳�������?", 0, "");
				break;
			}
		}
		
	}

	private static void startSettingSytem(Context context)
	{
		// ����ϵͳ��������
		Intent it = new Intent(context, ActivitySystemSettingList.class);
		context.startActivity(it);
	}

	private static void startSetting(Context context)
	{
		// ������������
		Intent it = new Intent(context, ActivitySetting.class);
		context.startActivity(it);
	}

}
