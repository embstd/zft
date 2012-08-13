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
	// 判断是否已经手工点击了登录按钮
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
		//读取配置文件 判断当前城市
		readXmlFile();
		if(Config.cityTag.equals("cztnq"))
		{
			//天宁区
			setContentView(R.layout.tianning_login);
		}
		else if (Config.cityTag.equals("czjts")) 
		{
			//金坛市
			setContentView(R.layout.jintan_login);
		}
		else if (Config.cityTag.equals("czlys")) 
		{
			//溧阳市
			setContentView(R.layout.liyang_login);
		}
		else if (Config.cityTag.equals("czs")) {
			//市支队
			setContentView(R.layout.czs_login);
		}
		else {
			//默认
			setContentView(R.layout.default_login);
		}
		 DatabaseCopy copy = new DatabaseCopy();
         copy.copy2Sdcard();
		// 登陆信息管理
		login = new Login(this);

		// 加载系统配置信息
		Config.loadConfig(this);

		// 如果是升级后第一次运行,那么就退出程序
		int updateState = login.getUpdateState();
		if (updateState == 0)
		{ // 如果更新,第一次需要退出
			login.saveUpdateState(1);
			System.exit(0);
		}

		// 初始化窗口
		init();
		loadLogin(); // 加载数据库登录记录

		// 启动更新检查线程
		ThreadUpdate threaUpdate = new ThreadUpdate(mHandler, "check");
		threaUpdate.start();

		// 设置堆内存大小
		VMRuntime.getRuntime().setTargetHeapUtilization(0.75f);

	}
	
	//读配置文件(程序最先执行)
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
			if (cityTag[i].equals(currentCity)){//和string.xml中的一样
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
		if (num == 0){//没有找到则推出系统
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

		tv_version.setText("版本:" + Config.version);
		tv_appName.setText(Config.appName);

		bt_ok.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				// print.out("onClick()");
				// 检查用户名字段
				boolean flag = true;
				username = (et_username.getText().toString()).trim();
				password = (et_password.getText().toString()).trim();
				if (username.equals(""))
				{
					showToast("用户名不能为空!");
					flag = false;

				}
				else if (password.equals(""))
				{
					showToast("密码不能为空!");
					flag = false;
				}
				if (Config.serverip.equals(""))
				{
					showToast("服务器地址未配置!");
					flag = false;
				}

				if (flag)
				{
					autoLogin = false; // 1
					if (LoginThread == null)
					{
						// 防止多次创建
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
		// 获取上次保存的用户名和密码
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
		// 加载窗口样式
		Config.windowStytle = (sp.getString("windowStytle", "0"));

		// 加载网格
		// loadSpinner();

		// 检查自动升级的文件,删除它
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

	// 点击登录
	public boolean submit(int times) throws Exception
	{
		GetData getData = new GetData(this);
		print.out(" submit (int times) throws Exception");
		username = (et_username.getText().toString()).trim();
		password = (et_password.getText().toString()).trim();
		
			Config.tb_typeLingNum = Integer.valueOf(Server.GetDataLingNum());
		
		if (username.equals(""))
		{
			showToast("s用户名不能为空!");
			return false;

		}
		else if (password.equals(""))
		{
			showToast("密码不能为空!");
			return false;
		}
		if (Config.serverip.equals(""))
		{
			showToast("服务器地址未配置!");
			return false;
		}
		if (Config.debug)
		{
			Log.i("ZFT", "调试模式");
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
				Config.groupid = Config.user.getGroupid(); //用户权限
				Config.station = Config.user.getStation(); //用户定位
				Config.idiograph = Config.user.getIdiograph(); //用户签名存放路径
				Config.zfzid = Config.user.getZfzid(); //用户执法证号
				Config.branch = Config.user.getBranch(); //用户所在部门id
				
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
		// 更新用户名和密码
		saveLogin();
		Intent it;
//		if (Config.windowStytle.equals("0"))
//		{
//			it = new Intent(ActivityLogin.this, ActivityMainZft.class); // 执法通
//		}
//		else
//		{
//			it = new Intent(ActivityLogin.this, ActivityMainZft.class);
//		}
		if (Config.windowStytle.equals("0"))
		{
			it = new Intent(ActivityLogin.this, ActivityMainZft.class); // 执法通
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
		// 保存信息
		login.saveLogin(username, password, ck_savepwd.isChecked(),
				ck_autologin.isChecked());
	}

	public void autoLogin()
	{
		print.out("autoLogin()");
		// if(ck_autologin.isChecked()){
		if (autoLogin)
		{
			showToast(Config.autologin_waiteseconds + "秒钟后将自动登录");
			waitThread.start(); // 启动自动登录线程
		}
	}

	/**
	 * 设置菜单
	 */
	public boolean onCreateOptionsMenu(Menu menu)
	{
		print.out("onCreateOptionsMenu()");
		super.onCreateOptionsMenu(menu);
		Menus.initMenu(this, menu);
		return true;
	}

	/**
	 * 菜单处理
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
							"错误", "用户名或密码不正确!", 0, "");
				}
				else if (result.getErrorCode().equals("2"))
				{
					Dialog.showDialog(Dialog.OK, ActivityLogin.this, null, 0,
							"错误", "用户名或密码不正确!", 0, "");
				}
				else if (result.getErrorCode().equals("-1"))
				{
					Dialog.showDialog(Dialog.OK, ActivityLogin.this, null, 0,
							"错误", "用户名不存在!", 0, "");
				}
				else
				{
					String errorStr = "错误代码:" + result.getErrorCode()
							+ "\n错误描述:" + result.getErrorDesc();
					Dialog.showDialog(Dialog.OK, ActivityLogin.this, null, 0,
							"错误", errorStr, 0, "");
				}
				break;
			case Msg.LOGIN_SERVERERROR:
				if (Config.offLineOpen)
				{ // 如果离线登陆功能打开了
					Dialog.showDialog(Dialog.OKCANCEL, ActivityLogin.this,
							mHandler, Msg.LOGIN_OFFLINELOAD, "登录",
							"服务器无法连接!登陆失败,是否离线登陆?", 0, "");
				}
				else
				{
					Dialog.showDialog(Dialog.OK, ActivityLogin.this, null, 0,
							"错误", "服务器无法连接!", 0, "");
				}
				break;
			case Msg.LOGIN_LOCKPANEL:
				setEnable(false);
				break;
			case Msg.LOGIN_UNLOCKPANEL:
				setEnable(true);
				break;
			case Msg.LOGIN_RUNLOGINTHREAD:
				LoginThread = new MyThread(); // 登陆
				LoginThread.showProcess();
				break;
			case Msg.LOGIN_OFFLINELOADERROR: // 离线登陆失败
				Dialog.showDialog(Dialog.OK, ActivityLogin.this, null, 0, "错误",
						"本地密码验证失败,离线登陆失败!", 0, "");
				// 关闭离线登陆
				Config.offLineLoad = false;
				break;
			case Msg.LOGIN_CONTINUELOAD:
				autoLogin(); // 判断是否自动登录,如是,执行
				break;
			case Msg.LOGIN_FORCEUPDATE:
				autoLogin = false; // 关闭自动升级
				Dialog.showDialog(Dialog.OK, ActivityLogin.this, mHandler,
						Msg.ACTIVITY_FORCEUPDATE, "程序升级", "系统检测到新版本1!是否升级?",
						R.drawable.alert_dialog_icon, "立即升级");
				break;
			case Msg.LOGIN_NEEDUPDATE:
				autoLogin = false;// 关闭自动升级
				Dialog.showDialog(Dialog.OKCANCEL, ActivityLogin.this,
						mHandler, Msg.ACTIVITY_FORCEUPDATE, "程序升级",
						"系统检测到新版本!是否升级?", R.drawable.alert_dialog_icon, "立即升级");
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
				// 开启离线登陆
				Config.offLineLoad = true; // 打开离线登陆状态
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
			progressDialog.setMessage("正在登录,请稍候...");
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

					// 非离线登陆
					if (submit(1))
					{
						saveLogin();
						loadMain();
					}
					else
					{
						login.cancelAutoLogin();
						// 登录失败,发送消息
						mHandler.sendMessageDelayed(mHandler.obtainMessage(
								Msg.LOGIN_USRERROR, 0), 0);
					}
				}
				else
				{
					// 离线登陆
					if (submitOffLine())
					{
						// saveLogin();
						loadMain();
					}
					else
					{
						login.cancelAutoLogin();
						// 登录失败,发送消息
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
			LoginThread = null; // 将自己设置为空,以便下次调用登陆
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
			// 发送消息锁定按钮
			if (autoLogin)
			{
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						Msg.LOGIN_LOCKPANEL, 0), 0);
				// 等待200毫秒后检查锁定结果
				try
				{
					sleep(200);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				// 检查是否锁定
				if (autoLogin)
				{
					// 锁定成功,开始登录线程
					mHandler.sendMessageDelayed(mHandler.obtainMessage(
							Msg.LOGIN_RUNLOGINTHREAD, 0), 0);
				}
				else
				{
					// 锁定失败,释放按钮
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
