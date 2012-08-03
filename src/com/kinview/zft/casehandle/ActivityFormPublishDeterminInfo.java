package com.kinview.zft.casehandle;

import java.util.Calendar;

import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.entity.NewCase;
import com.kinview.thread.ThreadExit;
import com.kinview.thread.ThreadGetCaseInfo;
import com.kinview.thread.ThreadSubmitBd;
import com.kinview.util.Form;
import com.kinview.zft.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityFormPublishDeterminInfo extends Activity {
	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	
	//表单二
	private TextView tv_form_title_all_2;
	private TextView tv_form_title_2;
	private TextView tv_orgnization;
	private TextView tv_competent;
	private TextView tv_competent_post;
	private TextView tv_competent_address;
	private TextView tv_competent_phone;
	private TextView tv_citizen;
	private TextView tv_citizen_sex;
	private TextView tv_citizen_age;
	private TextView tv_citizen_identity_card;
	private TextView tv_citizen_address;
	private TextView tv_citizen_phone;
	
//	private EditText et_form_title_all_2;
//	private EditText et_form_title_2;
	private EditText et_orgnization;
	private EditText et_competent;
	private EditText et_competent_post;
	private EditText et_competent_address;
	private EditText et_competent_phone;
	private EditText et_citizen;
	private EditText et_citizen_sex;
	private EditText et_citizen_age;
	private EditText et_citizen_identity_card;
	private EditText et_citizen_address;
	private EditText et_citizen_phone;
	
//	private int caseId;
	
//	private String formContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		Intent intent = this.getIntent();
//		position = intent.getIntExtra("position", 0);
//		caseId = intent.getIntExtra("caseId", 0);
		
		setContentView(R.layout.form_site_record_info);
		
		init();
		bottomBarLayout = (RelativeLayout)this.findViewById(R.id.form_bottom);
		if (bottomBarLayout == null){
			return;
		}
		reportCase = (TextView)bottomBarLayout.findViewById(R.id.report_case);
		posPrint = (TextView)bottomBarLayout.findViewById(R.id.pos_print);
		reportCase.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormPublishDeterminInfo.this, myHandler, Msg.PROMPT_PRINT,
						"提示", "如需打印先打印，上报后将无法打印,是否继续上报？", 0, "");
//				reportCaseProcess();
//				Toast.makeText(ActivityForm.this, "hhhhhh", Toast.LENGTH_SHORT);
			}
		});
		
		if (Config.currentFormList.get(Config.bdPositionId).getState() != null){
			if (!Config.currentFormList.get(Config.bdPositionId).getState().equals("")){
				reportCase.setVisibility(View.GONE);
				setEditTextLock();
			}
		}

		posPrint.setVisibility(View.GONE);
		posPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posPrintProcess();
			}
		});
		
		if (Config.threadGetCaseInfo == null){
			Config.threadGetCaseInfo = new ThreadGetCaseInfo();
			Config.threadGetCaseInfo.showProcess(this, myHandler, 0, Config.caseId);
		}
	}
	
	private void setEditTextLock(){
		lockEditText(et_orgnization);
		lockEditText(et_competent);
		lockEditText(et_competent_post);
		lockEditText(et_competent_address);
		lockEditText(et_competent_phone);
		lockEditText(et_citizen);
		lockEditText(et_citizen_sex);
		lockEditText(et_citizen_age);
		lockEditText(et_citizen_identity_card);
		lockEditText(et_citizen_address);
		lockEditText(et_citizen_phone);
		
	}
	//已上报案件 所有信息不可修改
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
	}
	
	//显示案件详细信息
	private void showCaseInfo(){
		Log.i("ZFTSP", String.valueOf(Config.currentNewCaseList.toString()));
		et_orgnization.setText(Config.currentNewCaseList.get(0).getOrganise());
		et_competent.setText(Config.currentNewCaseList.get(0).getFzr());
		et_competent_post.setText(Config.currentNewCaseList.get(0).getZw());
		et_competent_address.setText(Config.currentNewCaseList.get(0).getOadd());
		et_competent_phone.setText(Config.currentNewCaseList.get(0).getOtel());
		et_citizen.setText(Config.currentNewCaseList.get(0).getPerson());
		et_citizen_sex.setText(Config.currentNewCaseList.get(0).getSex());
		et_citizen_age.setText(Config.currentNewCaseList.get(0).getAge());
		et_citizen_identity_card.setText(Config.currentNewCaseList.get(0).getIdnumber());
		et_citizen_address.setText(Config.currentNewCaseList.get(0).getPadd());
		et_citizen_phone.setText(Config.currentNewCaseList.get(0).getPtel());
	}
	
	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			Intent it = null;
			Bundle b = null;
			switch (msg.what){
			case Msg.GETCASEINFO:
				showCaseInfo();
//				nextPage();
				break;
			case Msg.LISTVIEW_REFRESH:
//				refresh();
				break;
			case Msg.ERROR_SERVER_CONNECT:
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormPublishDeterminInfo.this,
						null, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormPublishDeterminInfo.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormPublishDeterminInfo.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.REQUEST_REPEAT);
				it.putExtras(b);
				if (getParent() == null){
					setResult(0, it);
				}else {
					getParent().setResult(0, it);
				}
				finish();
				break;
			case Msg.REQUEST_SUCCESS:
				Dialog.showDialog(Dialog.OK, ActivityFormPublishDeterminInfo.this,
				myHandler, Msg.ACTIVITY_EXIT, "提示", "上报表单成功", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityFormPublishDeterminInfo.this,
						null, 0, "提示", "上报表单错误", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormPublishDeterminInfo.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.SUBMIT_BD_SUCCESS);
				it.putExtras(b);
				if (getParent() == null){
					setResult(0, it);
				}else {
					getParent().setResult(0, it);
				}
//				setResult(0, it);
				finish();
				break;
			case Msg.PROMPT_PRINT:
				reportCaseProcess();
				break;
			}
			
		}
		
	};
	
	
	
	@Override
	protected void onPause() {
		super.onPause();
		Config.otherTabBdContent = getFormContentWhenPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
			myHandler.sendEmptyMessage(Msg.ONBACK);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/*
	 * 开启或关闭进度条
	 */
	public void setTitleProgress(boolean flag){
		setProgressBarIndeterminateVisibility(flag);
	}
	
	private void init(){
		tv_form_title_all_2 = (TextView)this.findViewById(R.id.form_title_all_2);
		tv_form_title_2 = (TextView)this.findViewById(R.id.form_title_2);
		tv_orgnization = (TextView)this.findViewById(R.id.orgnization_name);
		tv_competent = (TextView)this.findViewById(R.id.competent_name);
		tv_competent_post = (TextView)this.findViewById(R.id.competent_post_name);
		tv_competent_address = (TextView)this.findViewById(R.id.competent_address_name);
		tv_competent_phone = (TextView)this.findViewById(R.id.competent_phone_name);
		tv_citizen = (TextView)this.findViewById(R.id.citizen_name);
		tv_citizen_sex = (TextView)this.findViewById(R.id.citizen_sex_name);
		tv_citizen_age = (TextView)this.findViewById(R.id.citizen_age_name);
		tv_citizen_identity_card = (TextView)this.findViewById(R.id.citizen_identity_card_name);
		tv_citizen_address = (TextView)this.findViewById(R.id.citizen_address_name);
		tv_citizen_phone = (TextView)this.findViewById(R.id.citizen_phone_name);
		
//		et_form_title_all_2 = (EditText)this.findViewById(R.id.limited_date_txt);;
//		et_form_title_2 = (EditText)this.findViewById(R.id.limited_date_txt);;
		et_orgnization = (EditText)this.findViewById(R.id.orgnization_name_txt);
		et_competent = (EditText)this.findViewById(R.id.competent_name_txt);
		et_competent_post = (EditText)this.findViewById(R.id.competent_post_name_txt);
		et_competent_address = (EditText)this.findViewById(R.id.competent_address_name_txt);
		et_competent_phone = (EditText)this.findViewById(R.id.competent_phone_name_txt);
		et_citizen = (EditText)this.findViewById(R.id.citizen_name_txt);
		et_citizen_sex = (EditText)this.findViewById(R.id.citizen_sex_name_txt);
		et_citizen_age = (EditText)this.findViewById(R.id.citizen_age_name_txt);
		et_citizen_identity_card = (EditText)this.findViewById(R.id.citizen_identity_card_name_txt);
		et_citizen_address = (EditText)this.findViewById(R.id.citizen_address_name_txt);
		et_citizen_phone = (EditText)this.findViewById(R.id.citizen_phone_name_txt);
		
		tv_form_title_2.setText(getResources().getString(R.string.form_title_14_name));
		tv_form_title_all_2.setText(Config.cgUnitName);
	}
	
	private String getFormContent(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		StringBuffer content = new StringBuffer();
		content.append(tv_form_title_all_2.getText().toString().trim()).append("\r\n");
		content.append(tv_form_title_2.getText().toString().trim()).append("\r\n").append("_");
//		content.append(tempform.getFormName()).append("_");
		content.append(Config.posFormTitle+"执罚字[").append(String.valueOf(year)).append("]  \r\nNo.")
		.append(tempCase.getLsh()).append("_");
		
		if (type == 0){
			content.append(tv_orgnization.getText().toString().trim())
			.append(et_orgnization.getText().toString().trim()).append("\r\n")
			.append(tv_competent.getText().toString().trim())
			.append(et_competent.getText().toString().trim()).append("\r\n")
			.append(tv_competent_post.getText().toString().trim())
			.append(et_competent_post.getText().toString().trim()).append("\r\n")
			.append(tv_competent_address.getText().toString().trim())
			.append(et_competent_address.getText().toString().trim()).append("\r\n")
			.append(tv_competent_phone.getText().toString().trim())
			.append(et_competent_phone.getText().toString().trim()).append("\r\n");
		}else {
			content.append(tv_citizen.getText().toString().trim())
			.append(et_citizen.getText().toString().trim()).append("\r\n")
			.append(tv_citizen_sex.getText().toString().trim())
			.append(et_citizen_sex.getText().toString().trim()).append("\r\n")
			.append(tv_citizen_age.getText().toString().trim())
			.append(et_citizen_age.getText().toString().trim()).append("\r\n")
			.append(tv_citizen_identity_card.getText().toString().trim())
			.append(et_citizen_identity_card.getText().toString().trim()).append("\r\n")
			.append(tv_citizen_address.getText().toString().trim())
			.append(et_citizen_address.getText().toString().trim()).append("\r\n")
			.append(tv_citizen_phone.getText().toString().trim())
			.append(et_citizen_phone.getText().toString().trim()).append("\r\n");
		}
//		content.append("现场勘察示意图:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");//15行
		String[] otherStr = Config.otherTabBdContent.split("_");
		for (int i =0; i<otherStr.length; i++){
			content.append(otherStr[i]);
			if (i%2 != 0){
				content.append("\r\n");
			}
		}
//		content.append("当事人签名:").append("\r\n\r\n\r\n");
//		content.append("检查人员签名:").append("\r\n\r\n\r\n");
//		content.append("见证人签名:").append("\r\n\r\n\r\n");
		
		content.append("_");
		content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" 年 ");
		content.append(String.valueOf(month)).append(" 月 ");
		content.append(String.valueOf(day)).append(" 日");
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContentWhenPause(){
		StringBuffer content = new StringBuffer();
		content.append(tv_orgnization.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_orgnization.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_competent.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_competent.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_competent_post.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_competent_post.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_competent_address.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_competent_address.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_competent_phone.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_competent_phone.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_citizen.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_citizen.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_citizen_sex.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_citizen_sex.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_citizen_age.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_citizen_age.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_citizen_identity_card.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_citizen_identity_card.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_citizen_address.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_citizen_address.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_citizen_phone.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_citizen_phone.getText().toString().trim().replaceAll("_", ""));
		
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContent2(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		StringBuffer content = new StringBuffer();
		
		if (type == 0){
			content
			.append(et_orgnization.getText().toString().trim()).append("_")
			.append(et_competent.getText().toString().trim()).append("_")
			.append(et_competent_post.getText().toString().trim()).append("_")
			.append(et_competent_address.getText().toString().trim()).append("_")
			.append(et_competent_phone.getText().toString().trim()).append("_");
		}else {
			content
			.append(et_citizen.getText().toString().trim()).append("_")
			.append(et_citizen_sex.getText().toString().trim()).append("_")
			.append(et_citizen_age.getText().toString().trim()).append("_")
			.append(et_citizen_identity_card.getText().toString().trim()).append("_")
			.append(et_citizen_address.getText().toString().trim()).append("_")
			.append(et_citizen_phone.getText().toString().trim()).append("_");
		}
//		content.append("现场勘察示意图:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");//15行
		String[] otherStr = Config.otherTabBdContent.split("_");
		for (int i =0; i<otherStr.length; i++){
			if (i%2 != 0){
				content.append(otherStr[i]);
				content.append("_");
			}
		}
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private void reportCaseProcess(){
		if (checkInputLegal()){
			int aj_id =0;
			String bd_id = "";
			String bd_name = "";
			String param = "";
			if (Config.currentFormList.size() != 0){
				aj_id = Integer.valueOf(Config.currentFormList.get(0).getCaseId());
				bd_id = String.valueOf(Config.currentFormList.get(Config.bdPositionId).getId());//注意
				bd_name = Config.currentFormList.get(Config.bdPositionId).getFormName();
				param = getFormContent2();
			}
			
			Log.i("ZFTSP", "otherTabBdContent: " + Config.otherTabBdContent + "aj_id: " + aj_id +" position: " + Config.bdPositionId + " bd_id: " + bd_id + " bd_name: " + bd_name + " param: " + param);
				
			if (Config.threadSubmitBd == null){
				Config.threadSubmitBd = new ThreadSubmitBd();
				Config.threadSubmitBd.showProcess(this, myHandler, aj_id, bd_id, bd_name, param);
			}
		}
	}
	
	private void posPrintProcess() {
		if (checkInputLegal()) {
			Intent it  = new Intent(ActivityFormPublishDeterminInfo.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", Config.bdPositionId);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	
	
	private boolean checkInputLegal(){
//		if (et_orgnization.getText().toString().trim().equals("")){
//			Dialog.showDialog(Dialog.OK, this, null, 0,
//					"提示", "法人或组织名称 不能为空!", 0, "");
//			return false;
//		}else {
//			return true;
//		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		Config.otherTabBdContent = "";
//		Config.otherTabBdContentSubmit = "";
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//如果上报成功刷新列表  先传回数据 后kill 当前activity
	}
}
