package com.kinview.zft.casehandle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.entity.NewCase;
import com.kinview.thread.ThreadExit;
import com.kinview.thread.ThreadGetCaseInfo;
import com.kinview.thread.ThreadSubmitBd;
import com.kinview.util.Form;
import com.kinview.zft.R;

public class ActivityFormEvidenceRegSiteRecord extends Activity {
	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	
	//表单
	private TextView tv_form_title_all_4;
	private TextView tv_form_title_4;
	private TextView tv_evidence_registration_1;
	private TextView tv_evidence_registration_2;
	private TextView tv_evidence_registration_3;
	private TextView tv_evidence_registration_4;
	private TextView tv_evidence_registration_5;
//	private TextView tv_evidence_registration_6;
//	private TextView tv_evidence_registration_7;
//	private TextView tv_evidence_registration_8;
//	private TextView tv_evidence_registration_9;
//	private TextView tv_evidence_registration_10;
	
	private EditText et_evidence_registration_1;
	private EditText et_evidence_registration_2;
	private EditText et_evidence_registration_3;
	private EditText et_evidence_registration_4;
	private EditText et_evidence_registration_5;
//	private EditText et_evidence_registration_6;
//	private EditText et_evidence_registration_7;
//	private EditText et_evidence_registration_8;
//	private EditText et_evidence_registration_9;
//	private EditText et_evidence_registration_10;
	
	private String xiandingTimeStr = "";//限定时间的string
	
	private Button pickDate = null;  
	private static final int SHOW_DATAPICK = 0;   
	private static final int DATE_DIALOG_ID = 1;    
	private int mYear;    
	private int mMonth;  
	private int mDay;
	
//	private String formContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		Intent intent = this.getIntent();
//		position = intent.getIntExtra("position", 0);
		
		setContentView(R.layout.form_evidence_registration_site_record);
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormEvidenceRegSiteRecord.this, myHandler, Msg.PROMPT_PRINT,
						"提示", "如需打印先打印，上报后将无法打印,是否继续上报？", 0, "");
//	
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
		
//		initDatePicker();
	}
	
	//初始化时间控件
	private void initDatePicker(){
		pickDate = (Button) findViewById(R.id.pickdate); 
		
		pickDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				if (pickDate.equals((Button)v)){
					msg.what = ActivityFormEvidenceRegSiteRecord.SHOW_DATAPICK;
				}
				ActivityFormEvidenceRegSiteRecord.this.dateHandler.sendMessage(msg);
			}
		});
		
		final Calendar c = Calendar.getInstance();  
		mYear = c.get(Calendar.YEAR); 
		mMonth = c.get(Calendar.MONTH);    
		mDay = c.get(Calendar.DAY_OF_MONTH);  

//		mHour = c.get(Calendar.HOUR_OF_DAY);
//		mMinute = c.get(Calendar.MINUTE);

		setDateTime();
//		setTimeOfDay(); 
	}
	
	/**
	  *  设置日期  
	 */ 
	private void setDateTime() {

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		updateDateDisplay();

	}

	/**
	 * 更新日期显示  
	 */ 
	private void updateDateDisplay(){  
//		et_evidence_registration_6.setText(new StringBuilder().append(mYear).append("-")
//				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
//				.append("-").append((mDay < 10) ? "0" + mDay : mDay));
	}

	/**
	 *  日期控件的事件
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		}
	};  


	
	protected android.app.Dialog onCreateDialog(int id) {
		switch (id){
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}
	
	protected void onPrepareDialog(int id, android.app.Dialog dialog) {
		switch (id) {
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}
	
	Handler dateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ActivityFormEvidenceRegSiteRecord.SHOW_DATAPICK:
				showDialog(DATE_DIALOG_ID);
				break;
			}
		}
	};
	
	private void setEditTextLock(){
		lockEditText(et_evidence_registration_1);
		lockEditText(et_evidence_registration_2);
		lockEditText(et_evidence_registration_3);
		lockEditText(et_evidence_registration_4);
		lockEditText(et_evidence_registration_5);
//		lockEditText(et_evidence_registration_6);
//		lockEditText(et_evidence_registration_7);
//		lockEditText(et_evidence_registration_8);
//		lockEditText(et_evidence_registration_9);
//		lockEditText(et_evidence_registration_10);
		
	}
	//已上报案件 所有信息不可修改
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
	}
	

	//显示案件详细信息
	private void showCaseInfo(){
		Log.i("ZFTSP", String.valueOf(Config.currentNewCaseList.toString()));
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
//		if (type == 0){
//			et_evidence_registration_1.setText(tempCase.getOrganise());
//		}else {
//			et_evidence_registration_1.setText(tempCase.getPerson());
//		}
		if (tempCase.getCaseAnYou() == null || tempCase.getCaseAnYou().equals("")){
			et_evidence_registration_1.setText("");//案由
		}else {
			et_evidence_registration_1.setText(tempCase.getCaseAnYou());//案由
		}
		
		
		et_evidence_registration_2.setText(tempCase.getThetime());
		et_evidence_registration_3.setText(tempCase.getAddress());
		et_evidence_registration_4.setText(tempCase.getCaseWeiFaXW());
		
//		et_evidence_registration_5.setText(tempCase.getCaseWeiFaXW());
//		xiandingTimeStr = Config.getXiandingTimeString(Config.currentNewCaseList.get(0).getThetime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(new Date());
		
//		et_evidence_registration_6.setText(currentDate);
//		et_evidence_registration_6.setText(Config.timebyYMDHMS());
//		et_evidence_registration_7.setText(Config.user.getBranch_name());
//		et_evidence_registration_7.setText(Config.cgUnitName + Config.cgUnitNameDaDui);
//		et_evidence_registration_8.setText(Config.user.getBranch_address());
//		et_evidence_registration_9.setText(Config.user.getBranch_phone());
//		et_evidence_registration_10.setText("");
//		if (type == 0){
//			et_evidence_registration_6.setText(Config.timebyYMDHMS());
//			et_evidence_registration_7.setText(Config.user.getBranch_name());
//			et_evidence_registration_8.setText(tempCase.getOadd());
//			et_evidence_registration_9.setText(tempCase.getOtel());
//			et_evidence_registration_10.setText("");
//		}else {
//			et_evidence_registration_6.setText(Config.timebyYMDHMS());
//			et_evidence_registration_7.setText(Config.user.getBranch_name());
//			et_evidence_registration_8.setText(tempCase.getPadd());
//			et_evidence_registration_9.setText(tempCase.getPtel());
//			et_evidence_registration_10.setText("");
//		}
		
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormEvidenceRegSiteRecord.this,
						null, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormEvidenceRegSiteRecord.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormEvidenceRegSiteRecord.this,ActivityFormList.class);
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
				Dialog.showDialog(Dialog.OK, ActivityFormEvidenceRegSiteRecord.this,
				myHandler, Msg.ACTIVITY_EXIT, "提示", "上报表单成功", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityFormEvidenceRegSiteRecord.this,
						null, 0, "提示", "上报表单错误", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormEvidenceRegSiteRecord.this,ActivityFormList.class);
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
//		Config.otherTabBdContentSubmit = getFormContentWhenPause();//上报用
//		Config.otherTabBdContent = getFormContent();//pos打印
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
		tv_form_title_all_4 = (TextView)this.findViewById(R.id.form_title_all_4);
		tv_form_title_4 = (TextView)this.findViewById(R.id.form_title_4);
		tv_evidence_registration_1 = (TextView)this.findViewById(R.id.evidence_registration_site_record_1_name);
		tv_evidence_registration_2 = (TextView)this.findViewById(R.id.evidence_registration_site_record_2_name);
		tv_evidence_registration_3 = (TextView)this.findViewById(R.id.evidence_registration_site_record_3_name);
		tv_evidence_registration_4 = (TextView)this.findViewById(R.id.evidence_registration_site_record_4_name);
		tv_evidence_registration_5 = (TextView)this.findViewById(R.id.evidence_registration_site_record_5_name);
//		tv_evidence_registration_6 = (TextView)this.findViewById(R.id.evidence_registration_6_name);
//		tv_evidence_registration_7 = (TextView)this.findViewById(R.id.evidence_registration_7_name);
//		tv_evidence_registration_8 = (TextView)this.findViewById(R.id.evidence_registration_8_name);
//		tv_evidence_registration_9 = (TextView)this.findViewById(R.id.evidence_registration_9_name);
//		tv_evidence_registration_10 = (TextView)this.findViewById(R.id.evidence_registration_10_name);
		
		et_evidence_registration_1 = (EditText)this.findViewById(R.id.evidence_registration_site_record_1_txt);
		et_evidence_registration_2 = (EditText)this.findViewById(R.id.evidence_registration_site_record_2_txt);
		et_evidence_registration_3 = (EditText)this.findViewById(R.id.evidence_registration_site_record_3_txt);
		et_evidence_registration_4 = (EditText)this.findViewById(R.id.evidence_registration_site_record_4_txt);
		et_evidence_registration_5 = (EditText)this.findViewById(R.id.evidence_registration_site_record_5_txt);
//		et_evidence_registration_6 = (EditText)this.findViewById(R.id.evidence_registration_6_txt);
//		et_evidence_registration_7 = (EditText)this.findViewById(R.id.evidence_registration_7_txt);
//		et_evidence_registration_8 = (EditText)this.findViewById(R.id.evidence_registration_8_txt);
//		et_evidence_registration_9 = (EditText)this.findViewById(R.id.evidence_registration_9_txt);
//		et_evidence_registration_10 = (EditText)this.findViewById(R.id.evidence_registration_10_txt);
		
		tv_form_title_all_4.setText(Config.cgUnitName);
		if (Config.cityTag.equals("czjts")){
			tv_form_title_4.setText(getResources().getString(R.string.form_title_15_name));
		}
	}
	
	private String getFormContent(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH) + 1;
		int day1 = calendar.get(Calendar.DAY_OF_MONTH);
//		String str[] = xiandingTimeStr.split("/");
//		String year = "";
//		String month = "";
//		String day = "";
//		if (str.length > 2){
//			year = str[0];
//			month = str[1];
//			day = str[2].substring(0, 2);
//		}else {
//			
//		}
		
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		StringBuffer content = new StringBuffer();
		content.append(tv_form_title_all_4.getText().toString().trim()).append("\r\n");
		content.append(tv_form_title_4.getText().toString().trim()).append("\r\n").append("_");
//		content.append(tempform.getFormName()).append("_");
//		content.append(Config.posFormTitle +"执登存字[").append(String.valueOf(year1)).append("]  \r\nNo.")
//		.append(tempCase.getLsh()).append("_");
		
		content.append("案由：" +et_evidence_registration_1.getText().toString().trim()).append("\r\n");
		content.append("时间：" +et_evidence_registration_2.getText().toString().trim())
		.append("；地点：").append(et_evidence_registration_3.getText().toString().trim()).append("\r\n");
		content.append("执法人员（姓名、证件号）").append(":\r\n\r\n");
		content.append("当事人                            \r\n").append("性别              ").append("年龄              \r\n")
		.append("身份证号码                                                      ").append("\r\n\r\n");
		
		content.append("住所：").append("\r\n\r\n");
		content.append("电话：").append("\r\n\r\n");
		content.append("现场情况：").append("\r\n").append("    执法人员经").append("          ").append("发现当事人")
		.append("            。").append("在出示执法证件，表明身份并告知回避权后，对当事人进行调查询问，当事人陈述其")
		.append(et_evidence_registration_4.getText().toString().trim()).append("行为未办理")
		.append("               ，且此前城管执法人员曾要求其停止")
		.append(et_evidence_registration_4.getText().toString().trim()).append("行为。\r\n")
		.append("    执法人员当场告知当事人其行为已违反").append(tempCase.getFazhe())
		.append("的 规定，并拟根据《行政处罚法》第三十七条第二款的规定对其 ").append("                       ")
		.append("实施证据先行登记保存，其有权依据《行政强制法》第八条、第十八条第五项的规定进行陈述和申辩。\r\n")
		.append("    当事人陈述和申辩：").append(et_evidence_registration_5.getText().toString().trim()).append("\r\n")
		.append("    在听取当事人陈述、申辩后，执法队员当场交付了证据先行登记保存决定书，对当事人的 ").append("              ")
		.append("（详见证据先行登记保存清单）实施了证据先行登记 保存。\r\n ")
		.append("当事人（确认签名）：").append("  \r\n\r\n").append("               年"+"    月"+"    日\r\n")
		.append("执法人员：").append("  \r\n\r\n").append("               年"+"    月"+"    日\r\n")
		.append("记录人：").append("  \r\n\r\n").append("               年"+"    月"+"    日");
		
		content.append("_");
		content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year1)).append(" 年 ");
		content.append(String.valueOf(month1)).append(" 月 ");
		content.append(String.valueOf(day1)).append(" 日");
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContentWhenPause(){
		StringBuffer content = new StringBuffer();
		content
		.append(et_evidence_registration_1.getText().toString().trim()).append("_")
		.append(et_evidence_registration_2.getText().toString().trim()).append("_")
		.append(et_evidence_registration_3.getText().toString().trim()).append("_")
		.append(et_evidence_registration_4.getText().toString().trim()).append("_")
		.append(et_evidence_registration_5.getText().toString().trim());
//		.append(et_evidence_registration_6.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_7.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_8.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_9.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_10.getText().toString().trim());
		
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContent2(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year1 = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH) + 1;
//		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		String str[] = xiandingTimeStr.split("/");
		String year = "";
		String month = "";
		String day = "";
		if (str.length > 2){
			year = str[0];
			month = str[1];
			day = str[2].substring(0, 2);
		}else {
			
		}
		
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		StringBuffer content = new StringBuffer();
		content.append(String.valueOf(year1)).append("_")
		.append(tempCase.getLsh()).append("_");
		
		content.append(et_evidence_registration_1.getText().toString().trim()).append("_");
		content.append(et_evidence_registration_2.getText().toString().trim()).append("_")
		.append(et_evidence_registration_3.getText().toString().trim()).append("_")
		.append(et_evidence_registration_4.getText().toString().trim()).append("_")
		.append(et_evidence_registration_5.getText().toString().trim()).append("_");
		
		content.append(year).append("_");
		content.append(month).append("_");
		content.append(day).append("_");
//		content.append(et_evidence_registration_7.getText().toString().trim()).append("_");
//		content.append(et_evidence_registration_8.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_9.getText().toString().trim()).append("_");
		
		//待改
//		if (!Config.otherTabBdContentSubmit.equals("")){
//			content.append(Config.otherTabBdContentSubmit);
//		}else {
//			for (int i=0; i<6; i++){
//				content.append("").append("_").append("").append("_").append("").append("_").append("").append("_").append("").append("_");
//			}
//		}
		
		//保存地点
//		content
//		.append(et_evidence_registration_10.getText().toString().trim()).append("_");

		content.append(" 签字 \r\n\r\n").append("_").append(year).append("_").append(month).append("_").append(day).append("_");
		content.append(" 签字 \r\n\r\n").append("_").append(year).append("_").append(month).append("_").append(day).append("_");
		content.append(" 签字 \r\n\r\n").append("_").append(year).append("_").append(month).append("_").append(day).append("_");
		content.append(" 签字 \r\n\r\n").append("_").append(year).append("_").append(month).append("_").append(day).append("_");
		content.append(" 签字 \r\n\r\n").append("_").append(year).append("_").append(month).append("_").append(day).append("_");
		
		
//		String[] otherStr = Config.otherTabBdContent.split("_");
//		for (int i =0; i<otherStr.length; i++){
//			if (i%2 != 0){
//				content.append(otherStr[i]);
//				content.append("_");
//			}
//		}
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
			Intent it  = new Intent(ActivityFormEvidenceRegSiteRecord.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", Config.bdPositionId);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	
	
	private boolean checkInputLegal(){
//		if (et_evidence_registration_1.getText().toString().trim().equals("")){
//			Dialog.showDialog(Dialog.OK, this, null, 0,
//					"提示", "不能为空!", 0, "");
//			return false;
//		}else {
//			return true;
//		}
		return true;
	}
	
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		Config.otherTabBdContent = "";
//		Config.otherTabBdContentSubmit = "";
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//如果上报成功刷新列表  先传回数据 后kill 当前activity
	}
}
