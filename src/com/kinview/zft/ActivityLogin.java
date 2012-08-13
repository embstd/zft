package com.kinview.zft;

import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.kinview.zft.R;
import com.kinview.assistant.Assistant;
import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Menus;
import com.kinview.config.Msg;
import com.kinview.config.print; //import com.kinview.thread.ThreadCheckNet;
//import com.kinview.thread.ThreadClear;
import com.kinview.update.ActivityUpdate;
import com.kinview.update.DatabaseCopy;
import com.kinview.update.ThreadUpdate;
import com.kinview.util.GetData;
import com.kinview.util.HistoryService;
import com.kinview.util.Login;
import com.kinview.util.User;
import com.kinview.webservice.CommonResult;
import com.kinview.webservice.Server;

import dalvik.system.VMRuntime;

public class ActivityLogin extends Activity
{
	private EditText et_username;
	private EditText et_password;
	private CheckBox ck_savepwd;
	private CheckBox ck_autologin;
	private Button bt_ok;
	private TextView tv_version, tv_appName;
	MyThread LoginThread = null;
	private CommonResult result;

	private String username = "";
	private String password = "";
	ArrayList<String> list_dutyRgnCode = new   ArrayList<String>();
	// �ж��Ƿ��Ѿ��ֹ�����˵�¼��ť
	public boolean autoLogin = false;

	public static final int ITEM0 = Menu.FIRST;
	public static final int ITEM1 = Menu.FIRST + 1;

	private Login login = null;

	protected void onCreate(Bundle savedInstanceState)
	{
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.login);
//		setContentView(R.layout.login1280);
		//��ȡ�����ļ� �жϵ�ǰ����
		readXmlFile();
		if(Config.cityTag.equals("cztnq"))
		{
			//������
			setContentView(R.layout.tianning_login);
		}
		else if (Config.cityTag.equals("czjts")) 
		{
			//��̳��
			setContentView(R.layout.jintan_login);
		}
		else if (Config.cityTag.equals("czlys")) 
		{
			//������
			setContentView(R.layout.liyang_login);
		}
		else if (Config.cityTag.equals("czs")) {
			//��֧��
			setContentView(R.layout.czs_login);
		}
		else {
			//Ĭ��
			setContentView(R.layout.default_login);
		}
		 DatabaseCopy copy = new DatabaseCopy();
         copy.copy2Sdcard();
		// ��½��Ϣ����
		login = new Login(this);

		// ����ϵͳ������Ϣ
		Config.loadConfig(this);

		// ������������һ������,��ô���˳�����
		int updateState = login.getUpdateState();
		if (updateState == 0)
		{ // �������,��һ����Ҫ�˳�
			login.saveUpdateState(1);
			System.exit(0);
		}

		// ��ʼ������
		init();
		loadLogin(); // �������ݿ��¼��¼

		// �������¼���߳�
		ThreadUpdate threaUpdate = new ThreadUpdate(mHandler, "check");
		threaUpdate.start();

		// ���ö��ڴ��С
		VMRuntime.getRuntime().setTargetHeapUtilization(0.75f);

	}
	
	//�������ļ�(��������ִ��)
	private void readXmlFile(){
		SharedPreferences sp = this.getSharedPreferences("cityinfo",Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		String cityTagName = sp.getString("cityTag", "");
		String currentCity ="";
		if (!cityTagName.equals("")){
			currentCity = cityTagName;
//			return;
		}else {
			currentCity = this.getResources().getString(R.string.current_city);
		}
		
		
		String[] cityTag = this.getResources().getStringArray(R.array.cityTag);
		String[] cityName = this.getResources().getStringArray(R.array.cityName);
		String[] cgUnitName = this.getResources().getStringArray(R.array.cgUnitName);
		String[] posFormTitle = this.getResources().getStringArray(R.array.posFormTitle);
		String[] serverIpAddress = this.getResources().getStringArray(R.array.serverIpAddress);
		String[] cgUnitNameZhiDui = this.getResources().getStringArray(R.array.cgUnitNameZhiDui);
		String[] cgUnitNameDaDui = this.getResources().getStringArray(R.array.cgUnitNameDaDui);
		int num = 0;
		for (int i=0; i<cityTag.length; i++){
			if (cityTag[i].equals(currentCity)){//��string.xml�е�һ��
				Config.cityTag = currentCity;
				Config.cityName = cityName[i];
				Config.cgUnitName = cgUnitName[i];
				Config.posFormTitle = posFormTitle[i];
				Config.serverIpAddress = serverIpAddress[i];
				Config.cgUnitNameDaDui = cgUnitNameDaDui[i];
				Config.cgUnitNameZhiDui = cgUnitNameZhiDui[i];
				editor.putString("cityTag", cityTag[i]);
				editor.commit();
				num++;
				break;
			}
		}
		if (num == 0){//û���ҵ����Ƴ�ϵͳ
			System.exit(0);
		}
		Log.i("ZFT", "cityName: "+Config.cityName + " cgUnitName: " +Config.cgUnitName+ " posFormTitle: "+Config.posFormTitle);
	}

	private void init()
	{

		et_username = (EditText) findViewById(R.id.login_edittext1);
		et_password = (EditText) findViewById(R.id.login_edittext2);
		bt_ok = (Button) findViewById(R.id.login_button_ok);
		ck_savepwd = (CheckBox) findViewById(R.id.login_checkbox_remember);
		ck_autologin = (CheckBox) findViewById(R.id.login_checkbox_autolaogin);
		tv_version = (TextView) findViewById(R.id.login_textview_version);
		tv_appName = (TextView) findViewById(R.id.login_appname);

		tv_version.setText("�汾:" + Config.version);
		tv_appName.setText(Config.appName);

		bt_ok.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				// print.out("onClick()");
				// ����û����ֶ�
				boolean flag = true;
				username = (et_username.getText().toString()).trim();
				password = (et_password.getText().toString()).trim();
				if (username.equals(""))
				{
					showToast("�û�������Ϊ��!");
					flag = false;

				}
				else if (password.equals(""))
				{
					showToast("���벻��Ϊ��!");
					flag = false;
				}
				if (Config.serverip.equals(""))
				{
					showToast("��������ַδ����!");
					flag = false;
				}

				if (flag)
				{
					autoLogin = false; // 1
					if (LoginThread == null)
					{
						// ��ֹ��δ���
						LoginThread = new MyThread();
						LoginThread.showProcess();
					}
				}

			}
		});

		ck_savepwd.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if (!ck_savepwd.isChecked())
				{
					ck_autologin.setChecked(false);
				}
				autoLogin = false;
			}
		});

		ck_autologin.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if (!ck_savepwd.isChecked())
				{
					ck_autologin.setChecked(false);
				}
				autoLogin = false;
			}
		});

	}

	public void loadLogin()
	{
		print.out("loadLogin()");
		// ��ȡ�ϴα�����û���������
		SharedPreferences sp = getSharedPreferences("login",
				Context.MODE_PRIVATE);
		et_username.setText(sp.getString("username", ""));
		et_password.setText(sp.getString("password", ""));
		if (sp.getString("savepwd", "").equals("yes"))
		{
			ck_savepwd.setChecked(true);
		}
		if (sp.getString("autologin", "").equals("yes"))
		{
			ck_autologin.setChecked(true);
			autoLogin = true;
		}
		// ���ش�����ʽ
		Config.windowStytle = (sp.getString("windowStytle", "0"));

		// ��������
		// loadSpinner();

		// ����Զ��������ļ�,ɾ����
		String apk = sp.getString("apk", "");
		if (!apk.equals(""))
		{
			File file = new File(apk);
			if (file.exists())
			{
				file.delete();
			}
			Editor editor = sp.edit();
			editor.putString("apk", "");
			editor.commit();
		}
	}

	// �����¼
	public boolean submit(int times) throws Exception
	{
		GetData getData = new GetData(this);
		print.out(" submit (int times) throws Exception");
		username = (et_username.getText().toString()).trim();
		password = (et_password.getText().toString()).trim();
		
			Config.tb_typeLingNum = Integer.valueOf(Server.GetDataLingNum());
		
		if (username.equals(""))
		{
			showToast("s�û�������Ϊ��!");
			return false;

		}
		else if (password.equals(""))
		{
			showToast("���벻��Ϊ��!");
			return false;
		}
		if (Config.serverip.equals(""))
		{
			showToast("��������ַδ����!");
			return false;
		}
		if (Config.debug)
		{
			Log.i("ZFT", "����ģʽ");
			return true;
		}
		if(getData.getDBString())
		{
			getData.getData();
		}
		result = Server.login(username, password);
		print.out(result.getResultStr().toString());
		if (result.getErrorCode().equals("0"))
		{
			
			ArrayList<Object> list = Assistant.parseXml(result.getResultStr(),
					User.class);
			if (list.size() > 0)
			{
				Config.userid = Config.user.getUserid();
				Config.user = (User) list.get(0);
				Config.name = Config.user.getName();
				Config.groupid = Config.user.getGroupid(); //�û�Ȩ��
				Config.station = Config.user.getStation(); //�û���λ
				Config.idiograph = Config.user.getIdiograph(); //�û�ǩ�����·��
				Config.zfzid = Config.user.getZfzid(); //�û�ִ��֤��
				Config.branch = Config.user.getBranch(); //�û����ڲ���id
				
//				HistoryService history = new HistoryService(this);
//				history.getItemsForList(0);
			}
			return true;
		}
		return false;
	}

	public boolean submitOffLine() throws Exception
	{
		print.out("submitOffLine()");
		username = (et_username.getText().toString()).trim();
		password = (et_password.getText().toString()).trim();
		return login.checkOffLine(username, password);
	}

	public void loadMain()
	{
		print.out("loadMain()");
		// �����û���������
		saveLogin();
		Intent it;
//		if (Config.windowStytle.equals("0"))
//		{
//			it = new Intent(ActivityLogin.this, ActivityMainZft.class); // ִ��ͨ
//		}
//		else
//		{
//			it = new Intent(ActivityLogin.this, ActivityMainZft.class);
//		}
		if (Config.windowStytle.equals("0"))
		{
			it = new Intent(ActivityLogin.this, ActivityMainZft.class); // ִ��ͨ
		}
		else
		{
			it = new Intent(ActivityLogin.this, ActivityMainZft.class);
		}
		startActivity(it);
		finish();
	}

	public void showToast(String msg)
	{
		
		Toast toast = Toast
				.makeText(ActivityLogin.this, msg, Toast.LENGTH_LONG);
		toast.show();
	}

	public void saveLogin()
	{
		print.out("saveLogin()");
		Config.username = username;
		Config.password = password;
		// ������Ϣ
		login.saveLogin(username, password, ck_savepwd.isChecked(),
				ck_autologin.isChecked());
	}

	public void autoLogin()
	{
		print.out("autoLogin()");
		// if(ck_autologin.isChecked()){
		if (autoLogin)
		{
			showToast(Config.autologin_waiteseconds + "���Ӻ��Զ���¼");
			waitThread.start(); // �����Զ���¼�߳�
		}
	}

	/**
	 * ���ò˵�
	 */
	public boolean onCreateOptionsMenu(Menu menu)
	{
		print.out("onCreateOptionsMenu()");
		super.onCreateOptionsMenu(menu);
		Menus.initMenu(this, menu);
		return true;
	}

	/**
	 * �˵�����
	 */
	public boolean onOptionsItemSelected(MenuItem item)
	{
		print.out("onOptionsItemSelected()");
		Menus.actionMenu(this, mHandler, item.getItemId());
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		print.out("onKeyDown()");
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			System.exit(0);
			return true;
		}
		else if (keyCode == KeyEvent.KEYCODE_MENU)
		{
			autoLogin = false;
		}
		return super.onKeyUp(keyCode, event);
	}

	public void setEnable(boolean flag)
	{
		print.out("setEnable()");
		et_username.setEnabled(flag);
		et_password.setEnabled(flag);
		ck_savepwd.setEnabled(flag);
		ck_autologin.setEnabled(flag);
		bt_ok.setEnabled(flag);
	}

	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			print.out("handleMessage()");
			Intent it = null;
			switch (msg.what)
			{
			case Msg.LOGIN_USRERROR:
				if (result.getErrorCode().equals("1"))
				{
					Dialog.showDialog(Dialog.OK, ActivityLogin.this, null, 0,
							"����", "�û��������벻��ȷ!", 0, "");
				}
				else if (result.getErrorCode().equals("2"))
				{
					Dialog.showDialog(Dialog.OK, ActivityLogin.this, null, 0,
							"����", "�û��������벻��ȷ!", 0, "");
				}
				else if (result.getErrorCode().equals("-1"))
				{
					Dialog.showDialog(Dialog.OK, ActivityLogin.this, null, 0,
							"����", "�û���������!", 0, "");
				}
				else
				{
					String errorStr = "�������:" + result.getErrorCode()
							+ "\n��������:" + result.getErrorDesc();
					Dialog.showDialog(Dialog.OK, ActivityLogin.this, null, 0,
							"����", errorStr, 0, "");
				}
				break;
			case Msg.LOGIN_SERVERERROR:
				if (Config.offLineOpen)
				{ // ������ߵ�½���ܴ���
					Dialog.showDialog(Dialog.OKCANCEL, ActivityLogin.this,
							mHandler, Msg.LOGIN_OFFLINELOAD, "��¼",
							"�������޷�����!��½ʧ��,�Ƿ����ߵ�½?", 0, "");
				}
				else
				{
					Dialog.showDialog(Dialog.OK, ActivityLogin.this, null, 0,
							"����", "�������޷�����!", 0, "");
				}
				break;
			case Msg.LOGIN_LOCKPANEL:
				setEnable(false);
				break;
			case Msg.LOGIN_UNLOCKPANEL:
				setEnable(true);
				break;
			case Msg.LOGIN_RUNLOGINTHREAD:
				LoginThread = new MyThread(); // ��½
				LoginThread.showProcess();
				break;
			case Msg.LOGIN_OFFLINELOADERROR: // ���ߵ�½ʧ��
				Dialog.showDialog(Dialog.OK, ActivityLogin.this, null, 0, "����",
						"����������֤ʧ��,���ߵ�½ʧ��!", 0, "");
				// �ر����ߵ�½
				Config.offLineLoad = false;
				break;
			case Msg.LOGIN_CONTINUELOAD:
				autoLogin(); // �ж��Ƿ��Զ���¼,����,ִ��
				break;
			case Msg.LOGIN_FORCEUPDATE:
				autoLogin = false; // �ر��Զ�����
				Dialog.showDialog(Dialog.OK, ActivityLogin.this, mHandler,
						Msg.ACTIVITY_FORCEUPDATE, "��������", "ϵͳ��⵽�°汾1!�Ƿ�����?",
						R.drawable.alert_dialog_icon, "��������");
				break;
			case Msg.LOGIN_NEEDUPDATE:
				autoLogin = false;// �ر��Զ�����
				Dialog.showDialog(Dialog.OKCANCEL, ActivityLogin.this,
						mHandler, Msg.ACTIVITY_FORCEUPDATE, "��������",
						"ϵͳ��⵽�°汾!�Ƿ�����?", R.drawable.alert_dialog_icon, "��������");
				break;
			case Msg.ACTIVITY_FORCEUPDATE:
				it = new Intent(ActivityLogin.this, ActivityUpdate.class);
				startActivity(it);
				finish();
				break;
			case Msg.ACTIVITY_NEEDUPDATE:
				it = new Intent(ActivityLogin.this, ActivityUpdate.class);
				startActivity(it);
				break;
			case Msg.LOGIN_CANCELAUTOLOGIN:
				autoLogin = false;
				break;
			case Msg.ACTIVITY_EXIT:
				System.exit(0);
				break;
			case Msg.LOGIN_OFFLINELOAD:
				// �������ߵ�½
				Config.offLineLoad = true; // �����ߵ�½״̬
				LoginThread = new MyThread();
				LoginThread.showProcess();
				break;
			}
		}
	};

	class MyThread extends Thread
	{

		ProgressDialog progressDialog = null;

		private void showProcess()
		{

			progressDialog = new ProgressDialog(ActivityLogin.this);
			progressDialog.setMessage("���ڵ�¼,���Ժ�...");
			progressDialog.setIndeterminate(true);
			try
			{
				progressDialog.show();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			print.out("showProcess()"); // 2
			start();
		}

		public void run()
		{

			try
			{
				print.out("public void run()");
				if (!Config.offLineLoad)
				{

					// �����ߵ�½
					if (submit(1))
					{
						saveLogin();
						loadMain();
					}
					else
					{
						login.cancelAutoLogin();
						// ��¼ʧ��,������Ϣ
						mHandler.sendMessageDelayed(mHandler.obtainMessage(
								Msg.LOGIN_USRERROR, 0), 0);
					}
				}
				else
				{
					// ���ߵ�½
					if (submitOffLine())
					{
						// saveLogin();
						loadMain();
					}
					else
					{
						login.cancelAutoLogin();
						// ��¼ʧ��,������Ϣ
						mHandler.sendMessageDelayed(mHandler.obtainMessage(
								Msg.LOGIN_OFFLINELOADERROR, 0), 0);
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						Msg.LOGIN_SERVERERROR, 0), 0);
			}
			if (autoLogin)
			{
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						Msg.LOGIN_UNLOCKPANEL, 0), 0);
			}
			progressDialog.dismiss();
			LoginThread = null; // ���Լ�����Ϊ��,�Ա��´ε��õ�½
			try
			{
				join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}
	};

	Thread waitThread = new Thread()
	{
		public void run()
		{
			print.out("Trun");
			try
			{
				sleep(Config.autologin_waiteseconds * 1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			// ������Ϣ������ť
			if (autoLogin)
			{
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						Msg.LOGIN_LOCKPANEL, 0), 0);
				// �ȴ�200��������������
				try
				{
					sleep(200);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				// ����Ƿ�����
				if (autoLogin)
				{
					// �����ɹ�,��ʼ��¼�߳�
					mHandler.sendMessageDelayed(mHandler.obtainMessage(
							Msg.LOGIN_RUNLOGINTHREAD, 0), 0);
				}
				else
				{
					// ����ʧ��,�ͷŰ�ť
					mHandler.sendMessageDelayed(mHandler.obtainMessage(
							Msg.LOGIN_UNLOCKPANEL, 0), 0);
				}
			}
			try
			{
				join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}
	};

}
