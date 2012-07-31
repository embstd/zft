package com.kinview.update;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kinview.util.Login;
import com.kinview.zft.R;
import com.kinview.config.Config;

public class ActivityUpdate extends Activity {

	private TextView tv1;
	private ProgressBar pb1;
	private Button btn1;
	private ThreadUpdate thread;
	private String appName = Config.appName;
	private Apk apk = Config.apk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		setTitle(appName + "- ����");
		init();
	}

	private void init() {
		tv1 = (TextView) findViewById(R.id.update_textview1);
		pb1 = (ProgressBar) findViewById(R.id.update_progressbar);
		btn1 = (Button) findViewById(R.id.update_button1);

		btn1.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				btn1.setVisibility(View.INVISIBLE);
				tv1.setText("�ٴγ���...");
				thread = new ThreadUpdate(mHandler,"update");
				thread.start();
			}
			
		});
		if(apk!=null && !apk.getApkUrl().equals("")){
			thread = new ThreadUpdate(mHandler,"update");
			thread.start();
		}

	}

	public Handler mHandler = new Handler() {
		// @Override
		public void handleMessage(Message msg) {
			int pos;
			String filepath;
			
			switch (msg.what) {
			/* ��ȡ��ʶ��Ϊ �뿪�����߳�ʱ��ȡ�õ���Ϣ */
			//ˢ�½�����
			case 1: // show progress persent
				pos = (Integer) msg.obj;
				setValue(pos);
				break;
				//��������
			case 2: // download ok ,start
				filepath = (String)msg.obj;
				openApkByFile(filepath);
				break;
				//��ӡ����
			case 3: // file not found
				filepath = (String)msg.obj;
				tv1.setText(filepath);
				btn1.setVisibility(View.VISIBLE);
				break;
			case 4: // file not found
				tv1.setText("���ڽ�ѹ��װ��,���Ժ�...");
//				btn1.setVisibility(View.VISIBLE);
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void setValue(int pos) {
		if (pos == -1) {
			tv1.setText("�޷�����!���س���!");
		} else {
			tv1.setText("�����ļ���..." + pos + "%");
			pb1.setProgress(pos);
		}
	}
	
	private void openApkByFile(String filename) {
		//�ȿ������ݿ⵽sdcard
		DatabaseCopy dc = new DatabaseCopy();
		dc.copy2Sdcard();
		
		//�����ļ���ַ,�´δ����,�����ʱ�ļ�
		SharedPreferences sp = getSharedPreferences("login",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("apk", filename);
		editor.commit();
		//
		File file  = new File(filename);
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		String type = "application/vnd.android.package-archive";
		intent.setDataAndType(Uri.fromFile(file), type);
//		exitThread.start();	//�����˳��߳�
		startActivity(intent);
		
		Login login = new Login(this);
		login.saveUpdateState(0);	//�ó���֪����Ҫ����������
		finish();
//		print.out("system is exit11111111111");
//		android.os.Process.killProcess(android.os.Process.myPid());
		
//		String str_version = android.os.Build.VERSION.SDK;
//		print.out("str_version="+str_version);
//		float version = Float.parseFloat(str_version);
//		if(version>7){
//			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE); 
//			am.killBackgroundProcesses(getPackageName()); 
//		}else{
//			ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);   
//			manager.restartPackage(getPackageName());
//		}
	}
	
//	Thread exitThread = new Thread(){
//		public void run(){
//			try {
//				sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			String i = android.os.Build.VERSION.SDK;
//			print.out("system is exit");
////			System.exit(0);
//			finish();
//		}
//	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
			showDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void showDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setIcon(R.drawable.alert_dialog_icon);
		builder.setTitle("�˳�");
		builder.setMessage("ֹͣ����?");
		builder.setPositiveButton("ȷ  ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// android.os.Process.killProcess(android.os.Process.myPid());
						if(Config.apk.getType().equals("1")){
							System.exit(0);
						}else{
							finish();
						}
					}
				});
		builder.setNegativeButton("ȡ  ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
		builder.show();
	}

}