package com.kinview.setting;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.kinview.zft.R;
//import com.kinview.assistant.MyLog;
import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.util.Login;

public class ActivitySettingPassword extends Activity {

	private TextView username, name, oldpwd, newpwd1, newpwd2;
	private Button save, back;
	private String old_pwd,new_pwd1,new_pwd2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.settingpassword);
		
//		setTitle("�����޸�");
		init();
	}

	public void init() {
		username = (TextView) findViewById(R.id.settingpassword_edittext1);
		username.setText(Config.username);
		name = (TextView) findViewById(R.id.settingpassword_edittext2);
		name.setText(Config.name);
		oldpwd = (TextView) findViewById(R.id.settingpassword_edittext3);

		newpwd1 = (TextView) findViewById(R.id.settingpassword_edittext4);
		newpwd2 = (TextView) findViewById(R.id.settingpassword_edittext5);
		save = (Button) findViewById(R.id.settingpassword_button1);
		back = (Button) findViewById(R.id.settingpassword_button2);

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Config.offLineLoad){
					showToast("δ��¼������!");
					return;
				}
				old_pwd = oldpwd.getText().toString().trim();
				new_pwd1 = newpwd1.getText().toString().trim();
				new_pwd2 = newpwd2.getText().toString().trim();

				if(old_pwd.equals("")||new_pwd1.equals("")){
					Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,null,0,"�޸�����",
							"���벻��Ϊ��!\n����������!",0,"");
//					DIALOG.showOkDialog(ActivitySettingPassword.this,"�޸�����",
//					"���벻��Ϊ��!\n����������!");
				}else if (!new_pwd1.equals(new_pwd2)) {
//					DIALOG.showOkDialog(ActivitySettingPassword.this,"�޸�����",
//							"��������������벻һ��!\n����������!");
					Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,null,0,"�޸�����",
							"��������������벻һ��!\n����������!",0,"");
					newpwd1.setFocusable(true);
					newpwd1.setText("");
					newpwd2.setText("");
				} else if (new_pwd1.equals(old_pwd)) {
//					DIALOG.showOkDialog(ActivitySettingPassword.this,"�޸�����",
//							"�������������ԭ����һ��!");
					Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,null,0,"�޸�����",
							"�������������ԭ����һ��!",0,"");
				} else {
					
					ThreadModifyPwd thread = new ThreadModifyPwd(ActivitySettingPassword.this,mHandler,old_pwd,new_pwd1);
					thread.showProcess();
				}
			}
		});
	}

	public void close() {
		finish();
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Msg.REQUEST_SUCCESS:
				//�޸ĳɹ�
				Login login = new Login(ActivitySettingPassword.this);
				String newPwd = (String)msg.obj;
				login.savePassword(newPwd);
//				showOkDialog(ActivitySettingPassword.this,"�޸�����",
//				"�����޸ĳɹ�!");
				Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,mHandler,Msg.ACTIVITY_EXIT,"�޸�����",
						"�����޸ĳɹ�!",0,"");
//				finish();
				break;
			case Msg.REQUEST_ERROR:
				//�޸�ʧ��
				String error = (String)msg.obj;
//				DIALOG.showOkDialog(ActivitySettingPassword.this,"�޸�����",error);
				Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,null,0,"�޸�����",
				error,0,"");
//				DIALOG.showOkDialog(ActivitySettingPassword.this,"�����޸�ʧ��!ԭ���벻��ȷ!");
				break;
			case Msg.ERROR_SERVER_CONNECT:
				//�������޷�����
//				DIALOG.showOkDialog(ActivitySettingPassword.this,"�޸�����",
//				"�����޸�ʧ��!����������ʧ��");
				Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,null,0,"�޸�����",
						"�����޸�ʧ��!����������ʧ��",0,"");
				break;
			case Msg.ACTIVITY_EXIT:
				finish();
				break;
			}
		}
	};

	private void showToast(String msg) {
		Toast toast = Toast
				.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.show();
	}
}
