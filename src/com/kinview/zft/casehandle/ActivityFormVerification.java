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

public class ActivityFormVerification extends Activity {

	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	
	//表单
	private TextView tv_form_title_all_3;
	private TextView tv_form_title_3;
	private TextView tv_verification_notice_1;
	private TextView tv_verification_notice_2;
	private TextView tv_verification_notice_3;
	private TextView tv_verification_notice_4;
	private TextView tv_verification_notice_5;
	private TextView tv_verification_notice_6;
	private TextView tv_verification_notice_7;
	private TextView tv_verification_notice_8;
	private TextView tv_verification_notice_9;
	private TextView tv_verification_notice_10;
	private TextView tv_verification_notice_11;
	private TextView tv_verification_notice_12;
	
	private EditText et_verification_notice_1;
	private EditText et_verification_notice_2;
	private EditText et_verification_notice_3;
	private EditText et_verification_notice_4;
	private EditText et_verification_notice_5;
	private EditText et_verification_notice_6;
	private EditText et_verification_notice_7;
	private EditText et_verification_notice_8;
	private EditText et_verification_notice_9;
	private EditText et_verification_notice_10;
	private EditText et_verification_notice_11;
	private EditText et_verification_notice_12;
	private EditText et_verification_notice_13;
	private EditText et_verification_notice_14;
	private EditText et_verification_notice_15;
	private EditText et_verification_notice_16;
	
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
		
		setContentView(R.layout.form_verification_notice);
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormVerification.this, myHandler, Msg.PROMPT_PRINT,
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
		initDatePicker();
	}
	
	//初始化时间控件
	private void initDatePicker(){
		pickDate = (Button) findViewById(R.id.pickdate); 
		
		pickDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				if (pickDate.equals((Button)v)){
					msg.what = ActivityFormVerification.SHOW_DATAPICK;
				}
				ActivityFormVerification.this.dateHandler.sendMessage(msg);
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
		et_verification_notice_7.setText(new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay));
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
			case ActivityFormVerification.SHOW_DATAPICK:
				showDialog(DATE_DIALOG_ID);
				break;
			}
		}
	};
	
	private void setEditTextLock(){
		lockEditText(et_verification_notice_1);
		lockEditText(et_verification_notice_2);
		lockEditText(et_verification_notice_3);
		lockEditText(et_verification_notice_4);
		lockEditText(et_verification_notice_5);
		lockEditText(et_verification_notice_6);
		lockEditText(et_verification_notice_7);
		lockEditText(et_verification_notice_8);
		lockEditText(et_verification_notice_9);
		lockEditText(et_verification_notice_10);
		lockEditText(et_verification_notice_11);
		lockEditText(et_verification_notice_12);
		lockEditText(et_verification_notice_13);
		lockEditText(et_verification_notice_14);
		lockEditText(et_verification_notice_15);
		lockEditText(et_verification_notice_16);
	}
	//已上报案件 所有信息不可修改
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
	}
	
	//显示案件详细信息
	private void showCaseInfo(){
		Log.i("ZFTSP", String.valueOf(Config.currentNewCaseList.toString()));
		if (Config.currentNewCaseList.get(0).getOrganise().equals("") ||
				Config.currentNewCaseList.get(0).getOrganise() == null){
			et_verification_notice_1.setText(Config.currentNewCaseList.get(0).getPerson());
		}else {
			et_verification_notice_1.setText(Config.currentNewCaseList.get(0).getOrganise());
		}
		
//		et_verification_notice_1.setText(Config.currentNewCaseList.get(0).getOrganise());
		et_verification_notice_2.setText(Config.currentNewCaseList.get(0).getThetime());
		et_verification_notice_3.setText(Config.currentNewCaseList.get(0).getAddress());
		et_verification_notice_4.setText(Config.currentNewCaseList.get(0).getCaseWeiFaXW());
		et_verification_notice_5.setText(Config.currentNewCaseList.get(0).getYiju());
//		et_verification_notice_6.setText(Config.currentNewCaseList.get(0).getOrganise());
//		xiandingTimeStr = Config.getXiandingTimeString(Config.currentNewCaseList.get(0).getThetime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(new Date());
		et_verification_notice_7.setText(currentDate);//限定时间
//		et_verification_notice_8.setText(Config.user.getBranch_name());//部门
		if (Config.cityTag.equals("cztnq") || Config.cityTag.equals("czxbq") || Config.cityTag.equals("czwjq")
				|| Config.cityTag.equals("czzlq") || Config.cityTag.equals("czqsyq")){
			et_verification_notice_8.setText(Config.cgUnitNameZhiDui);//部门
		}else {
			et_verification_notice_8.setText(Config.user.getBranch_name());//部门
		}
		
		et_verification_notice_9.setText(Config.user.getBranch_address());//地址
		et_verification_notice_10.setText(Config.user.getBranch_phone());//电话
		et_verification_notice_9.setEnabled(false);
		et_verification_notice_9.setFocusable(false);
		et_verification_notice_10.setEnabled(false);
		et_verification_notice_10.setFocusable(false);
//		et_verification_notice_11.setText(Config.currentNewCaseList.get(0).getOrganise());
//		et_verification_notice_12.setText(Config.currentNewCaseList.get(0).getOrganise());
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormVerification.this,
						null, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormVerification.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormVerification.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.REQUEST_REPEAT);
				it.putExtras(b);
				setResult(0, it);
				finish();
				break;
			case Msg.REQUEST_SUCCESS:
				Dialog.showDialog(Dialog.OK, ActivityFormVerification.this,
				myHandler, Msg.ACTIVITY_EXIT, "提示", "上报表单成功", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityFormVerification.this,
						null, 0, "提示", "上报表单错误", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormVerification.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.SUBMIT_BD_SUCCESS);
				it.putExtras(b);
				setResult(0, it);
				finish();
				break;
			case Msg.PROMPT_PRINT:
				reportCaseProcess();
				break;
			}
		
		}
		
	};
	
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
		tv_form_title_all_3 = (TextView)this.findViewById(R.id.form_title_all_3);
		tv_form_title_3 = (TextView)this.findViewById(R.id.form_title_3);
		tv_verification_notice_1 = (TextView)this.findViewById(R.id.verification_notice_1_name);
		tv_verification_notice_2 = (TextView)this.findViewById(R.id.verification_notice_2_name);
		tv_verification_notice_3 = (TextView)this.findViewById(R.id.verification_notice_3_name);
		tv_verification_notice_4 = (TextView)this.findViewById(R.id.verification_notice_4_name);
		tv_verification_notice_5 = (TextView)this.findViewById(R.id.verification_notice_5_name);
		tv_verification_notice_6 = (TextView)this.findViewById(R.id.verification_notice_6_name);
		tv_verification_notice_7 = (TextView)this.findViewById(R.id.verification_notice_7_name);
		tv_verification_notice_8 = (TextView)this.findViewById(R.id.verification_notice_8_name);
		tv_verification_notice_9 = (TextView)this.findViewById(R.id.verification_notice_9_name);
		tv_verification_notice_10 = (TextView)this.findViewById(R.id.verification_notice_10_name);
		tv_verification_notice_11 = (TextView)this.findViewById(R.id.verification_notice_11_name);
		tv_verification_notice_12 = (TextView)this.findViewById(R.id.verification_notice_12_name);
		
		et_verification_notice_1 = (EditText)this.findViewById(R.id.verification_notice_1_name_txt);
		et_verification_notice_2 = (EditText)this.findViewById(R.id.verification_notice_2_name_txt);
		et_verification_notice_3 = (EditText)this.findViewById(R.id.verification_notice_3_name_txt);
		et_verification_notice_4 = (EditText)this.findViewById(R.id.verification_notice_4_name_txt);
		et_verification_notice_5 = (EditText)this.findViewById(R.id.verification_notice_5_name_txt);
		et_verification_notice_6 = (EditText)this.findViewById(R.id.verification_notice_6_name_txt);
		et_verification_notice_7 = (EditText)this.findViewById(R.id.verification_notice_7_name_txt);
		et_verification_notice_8 = (EditText)this.findViewById(R.id.verification_notice_8_name_txt);
		et_verification_notice_9 = (EditText)this.findViewById(R.id.verification_notice_9_name_txt);
		et_verification_notice_10 = (EditText)this.findViewById(R.id.verification_notice_10_name_txt);
		et_verification_notice_11 = (EditText)this.findViewById(R.id.verification_notice_11_name_txt);
		et_verification_notice_12 = (EditText)this.findViewById(R.id.verification_notice_12_name_txt);
		et_verification_notice_13 = (EditText)this.findViewById(R.id.verification_notice_13_name_txt);
		et_verification_notice_14 = (EditText)this.findViewById(R.id.verification_notice_14_name_txt);
		et_verification_notice_15 = (EditText)this.findViewById(R.id.verification_notice_15_name_txt);
		et_verification_notice_16 = (EditText)this.findViewById(R.id.verification_notice_16_name_txt);
		
		tv_form_title_all_3.setText(Config.cgUnitName);
	}
	
	private String getFormContent(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
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
		
		
		Calendar calendar = Calendar.getInstance();
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH) + 1;
		int day1 = calendar.get(Calendar.DAY_OF_MONTH);
		
		
		StringBuffer content = new StringBuffer();
		content.append(tv_form_title_all_3.getText().toString().trim()).append("\r\n");
		content.append(tv_form_title_3.getText().toString().trim()).append("_");
//		content.append(tempform.getFormName()).append("_");
		content.append(Config.posFormTitle +"执核字[").append(String.valueOf(year1)).append("]  \r\nNo.")
		.append(tempCase.getLsh()).append("_");
		content.append(et_verification_notice_1.getText().toString().trim()).append(":\r\n\r\n");
		content.append("    经查，你（单位）于").append(et_verification_notice_2.getText().toString().trim())
		.append("，在").append(et_verification_notice_3.getText().toString().trim())
		.append("，进行").append(et_verification_notice_4.getText().toString().trim())
		.append("的行为，已涉嫌违反")
		.append(tempCase.getYiju()).append("之规定.\r\n    ");//分段
		content.append("因你（单位）未能提供上述行为之合法依据，现责令你（单位）自接到本通知之日起，立即停止上述行为，并于");
		content.append(String.valueOf(mYear)).append(" 年 ");
		content.append(String.valueOf(mMonth+1)).append(" 月 ");
		content.append(String.valueOf(mDay)).append(" 日前，携带下列材料到");
		content.append(et_verification_notice_8.getText().toString().trim());
		content.append("（地址： ").append(et_verification_notice_9.getText().toString().trim())
		.append(", 联系电话：").append(et_verification_notice_10.getText().toString().trim());
		content.append("）接受调查处理： \r\n");
//		content.append(tv_verification_notice_12.getText().toString().trim());
		if (!et_verification_notice_12.getText().toString().trim().equals("")){
			content.append("1. ").append(et_verification_notice_12.getText().toString().trim()).append("\r\n");
		}
		if (!et_verification_notice_13.getText().toString().trim().equals("")) {
			content.append("2. ").append(et_verification_notice_13.getText().toString().trim()).append("\r\n");
		}
		if (!et_verification_notice_14.getText().toString().trim().equals("")) {
			content.append("3. ").append(et_verification_notice_14.getText().toString().trim()).append("\r\n");
		}
		if (!et_verification_notice_15.getText().toString().trim().equals("")) {
			content.append("4. ").append(et_verification_notice_15.getText().toString().trim()).append("\r\n");
		}
		if (!et_verification_notice_16.getText().toString().trim().equals("")) {
			content.append("5. ").append(et_verification_notice_16.getText().toString().trim()).append("\r\n");
		}
		
		content.append("\r\n逾期不接受调查或继续上述行为的，本机关将依法查处。\r\n\r\n\r\n");
		content.append("特此通知。\r\n");
		
		content.append("当事人签名:\r\n\r\n\r\n");
		content.append("执法人员签名:\r\n\r\n\r\n");
		content.append("见证人签名:\r\n\r\n\r\n");
		
		//地址
		
		content.append("_");
		content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year1)).append(" 年 ");
		content.append(String.valueOf(month1)).append(" 月 ");
		content.append(String.valueOf(day1)).append(" 日");
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContent2(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempfrom = Config.currentFormList.get(Config.bdPositionId);
//		String str[] = xiandingTimeStr.split("-");
//		String year = str[0];
//		String month = str[1];
//		String day = str[2];
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
		Calendar calendar = Calendar.getInstance();
		int year1 = calendar.get(Calendar.YEAR);
		
//		Calendar calendar = Calendar.getInstance();
//		int year = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH) + 1;
		int day1 = calendar.get(Calendar.DAY_OF_MONTH);
		
		StringBuffer content = new StringBuffer();
		content.append(String.valueOf(year1)).append("_");//年
		if (tempCase.getLsh() == null || tempCase.getLsh().equals("")){
			content.append(" ").append("_");
		}else {
			content.append(tempCase.getLsh()).append("_");//流水号
		}
		
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		
		content
		.append(et_verification_notice_1.getText().toString().trim()).append("_")
		.append(et_verification_notice_2.getText().toString().trim()).append("_")
		.append(et_verification_notice_3.getText().toString().trim()).append("_")
		.append(et_verification_notice_4.getText().toString().trim()).append("_")
		.append(et_verification_notice_5.getText().toString().trim()).append("_")
		.append(year).append("_")
		.append(month).append("_")
		.append(day).append("_")
//		.append(et_verification_notice_6.getText().toString().trim()).append("_")
//		.append(et_verification_notice_7.getText().toString().trim()).append("_")
		.append(et_verification_notice_8.getText().toString().trim()).append("_")
		.append(et_verification_notice_9.getText().toString().trim()).append("_")
		.append(et_verification_notice_10.getText().toString().trim()).append("_");
//		if (!et_verification_notice_12.getText().toString().trim().equals("")){
			content.append(et_verification_notice_12.getText().toString().trim()).append("_");
//		}
//		if (!et_verification_notice_13.getText().toString().trim().equals("")) {
			content.append(et_verification_notice_13.getText().toString().trim()).append("_");
//		}
//		if (!et_verification_notice_14.getText().toString().trim().equals("")) {
			content.append(et_verification_notice_14.getText().toString().trim()).append("_");
//		}
			content.append(et_verification_notice_15.getText().toString().trim()).append("_");
			
			content.append(et_verification_notice_16.getText().toString().trim()).append("_");
			content.append(year1).append("_");	
			content.append(month1).append("_");
			content.append(day1);
//		.append(et_verification_notice_11.getText().toString().trim()).append("_")
//		.append(et_verification_notice_12.getText().toString().trim());
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
			
			Log.i("ZFTSP", "aj_id: " + aj_id +" position: " + Config.bdPositionId + " bd_id: " + bd_id + " bd_name: " + bd_name + " param: " + param);
				
			if (Config.threadSubmitBd == null){
				Config.threadSubmitBd = new ThreadSubmitBd();
				Config.threadSubmitBd.showProcess(this, myHandler, aj_id, bd_id, bd_name, param);
			}
		}
	}
	
	private void posPrintProcess() {
		if (checkInputLegal()) {
			Intent it  = new Intent(ActivityFormVerification.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", Config.bdPositionId);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	
	
	private boolean checkInputLegal(){
//		if (xiandingTimeStr.equals("")){
//			Dialog.showDialog(Dialog.OK, this, null, 0,
//					"提示", "时间不能为空!", 0, "");
//			return false;
//		}else {
			return true;
//		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//如果上报成功刷新列表  先传回数据 后kill 当前activity
	}
	
}
