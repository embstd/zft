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
		
//		setTitle("密码修改");
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
					showToast("未登录服务器!");
					return;
				}
				old_pwd = oldpwd.getText().toString().trim();
				new_pwd1 = newpwd1.getText().toString().trim();
				new_pwd2 = newpwd2.getText().toString().trim();

				if(old_pwd.equals("")||new_pwd1.equals("")){
					Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,null,0,"修改密码",
							"密码不能为空!\n请重新输入!",0,"");
//					DIALOG.showOkDialog(ActivitySettingPassword.this,"修改密码",
//					"密码不能为空!\n请重新输入!");
				}else if (!new_pwd1.equals(new_pwd2)) {
//					DIALOG.showOkDialog(ActivitySettingPassword.this,"修改密码",
//							"两次输入的新密码不一致!\n请重新输入!");
					Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,null,0,"修改密码",
							"两次输入的新密码不一致!\n请重新输入!",0,"");
					newpwd1.setFocusable(true);
					newpwd1.setText("");
					newpwd2.setText("");
				} else if (new_pwd1.equals(old_pwd)) {
//					DIALOG.showOkDialog(ActivitySettingPassword.this,"修改密码",
//							"输入的新密码与原密码一样!");
					Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,null,0,"修改密码",
							"输入的新密码与原密码一样!",0,"");
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
				//修改成功
				Login login = new Login(ActivitySettingPassword.this);
				String newPwd = (String)msg.obj;
				login.savePassword(newPwd);
//				showOkDialog(ActivitySettingPassword.this,"修改密码",
//				"密码修改成功!");
				Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,mHandler,Msg.ACTIVITY_EXIT,"修改密码",
						"密码修改成功!",0,"");
//				finish();
				break;
			case Msg.REQUEST_ERROR:
				//修改失败
				String error = (String)msg.obj;
//				DIALOG.showOkDialog(ActivitySettingPassword.this,"修改密码",error);
				Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,null,0,"修改密码",
				error,0,"");
//				DIALOG.showOkDialog(ActivitySettingPassword.this,"密码修改失败!原密码不正确!");
				break;
			case Msg.ERROR_SERVER_CONNECT:
				//服务器无法连接
//				DIALOG.showOkDialog(ActivitySettingPassword.this,"修改密码",
//				"密码修改失败!服务器连接失败");
				Dialog.showDialog(Dialog.OK,ActivitySettingPassword.this,null,0,"修改密码",
						"密码修改失败!服务器连接失败",0,"");
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
