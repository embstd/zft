package com.kinview.zft.casehandle;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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

public class ActivityFormProvide extends Activity {

	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	//表单一
	private EditText et_notification_nuit;
	private EditText et_illegal_location;
	private EditText et_illegal_behavior;
	private EditText et_limited_date;
	
//	private TextView tv_form_title_all;
	private TextView tv_form_title;
	private TextView tv_notification_unit;
	private TextView tv_illegal_location;
	private TextView tv_illegal_behavior;
	private TextView tv_limited_date;
	
	private TextView tv_form_title_all;//总的 标题
	
//	private ThreadGetCaseInfo threadGetCaseInfo;
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
		
		setContentView(R.layout.form_provide_legal_procedures);
		
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
				
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormProvide.this, myHandler, Msg.PROMPT_PRINT,
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
	}
	
	private void setEditTextLock(){
		lockEditText(et_notification_nuit);
		lockEditText(et_illegal_location);
		lockEditText(et_illegal_behavior);
		lockEditText(et_limited_date);
	}
	//已上报案件 所有信息不可修改
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
//		et.setFilters(new InputFilter[]{new InputFilter() {
//			
//			@Override
//			public CharSequence filter(CharSequence source, int start, int end,
//					Spanned dest, int dstart, int dend) {
//				
//				return source.length() < 1 ? source.subSequence(dstart, dend): "";
//			}
//		}});

	}
	
	//显示案件详细信息
	private void showCaseInfo(){
		//上报过的
//		if (!Config.currentFormList.get(Config.bdPositionId).getState().equals("")){
//			reportCase.setVisibility(View.GONE);
//			setEditTextLock();
//			return;
//		}
//		Log.i("ZFTSP", "Config.casePositionId: " + Config.casePositionId + " Config.casePositionId: " + Config.casePositionId);
		if (Config.currentNewCaseList.get(0).getOrganise().equals("") ||
				Config.currentNewCaseList.get(0).getOrganise() == null){
			et_notification_nuit.setText(Config.currentNewCaseList.get(0).getPerson());
		}else {
			et_notification_nuit.setText(Config.currentNewCaseList.get(0).getOrganise());
		}
		       
		et_illegal_location.setText(Config.currentNewCaseList.get(0).getAddress());
		et_illegal_behavior.setText(Config.currentNewCaseList.get(0).getCaseWeiFaXW());
//		et_limited_date.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		et_limited_date.setText("3");
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormProvide.this,
						null, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormProvide.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormProvide.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.REQUEST_REPEAT);
				it.putExtras(b);
				setResult(0, it);
				finish();
				break;
			case Msg.REQUEST_SUCCESS:
				Dialog.showDialog(Dialog.OK, ActivityFormProvide.this,
				myHandler, Msg.ACTIVITY_EXIT, "提示", "上报表单成功", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityFormProvide.this,
						null, 0, "提示", "上报表单错误", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormProvide.this,ActivityFormList.class);
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
		tv_form_title_all = (TextView)this.findViewById(R.id.form_title_all);
		tv_form_title = (TextView)this.findViewById(R.id.form_title);
		tv_notification_unit = (TextView)this.findViewById(R.id.notification_unit);
		tv_illegal_location = (TextView)this.findViewById(R.id.illegal_location);
		tv_illegal_behavior = (TextView)this.findViewById(R.id.illegal_behavior);
		tv_limited_date = (TextView)this.findViewById(R.id.limited_date);
		
		et_notification_nuit = (EditText)this.findViewById(R.id.notification_unit_txt);
		et_illegal_location = (EditText)this.findViewById(R.id.illegal_location_txt);
		et_illegal_behavior = (EditText)this.findViewById(R.id.illegal_behavior_txt);
		et_limited_date = (EditText)this.findViewById(R.id.limited_date_txt);
		
		tv_form_title_all.setText(Config.cgUnitName);
		
//		tv_form_title.setText(getResources().getString(R.string.form_title_1_name));
	}
	
	
	private String getFormContent(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		//时间格式化
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//		String dateStr = "";
//		try {
//			Date date = sdf.parse(tempCase.getThetime());
//			dateStr = sdf.format(date);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		String str[] =null;
		if (Config.cityTag.equals("czs")){
			str = tempCase.getThetime().split("-");
		}else {
			str = tempCase.getThetime().split("/");
		}
		String year1 = "";
		String month1 = "";
		String day1 = "";
		String theTime = "";
		if (str.length > 2){
			year1 = str[0];
			month1 = str[1];
			day1 = str[2].substring(0, 2);
			theTime = year1 + "年"+month1 + "月"+day1 + "日";
		}else {
			theTime = tempCase.getThetime();
		}
		
		StringBuffer content = new StringBuffer();
		if (Config.cityTag.equals("czjts")){//金坛市
			content.append(tv_form_title_all.getText().toString().trim()).append("\r\n");
			content.append(tv_form_title.getText().toString().trim()).append("_");
//			content.append(tempform.getFormName()).append("_");
			content.append(Config.posFormTitle +"执通字[").append(String.valueOf(year)).append("]  \r\nNo.")
			.append(tempCase.getLsh()).append("_");
			content.append(et_notification_nuit.getText().toString().trim()).append(":\r\n\r\n");
			content.append("    经查，你（单位）于  ").append(theTime)
			.append("在").append(et_illegal_location.getText().toString().trim())
			.append("，进行").append(et_illegal_behavior.getText().toString().trim())
			.append("的行为，因你（单位）现场未能提供相关合法手续，已涉嫌违反")
			.append(tempCase.getFazhe().replaceAll(" ", "")).append("的有关规定，现责令你（单位）立即停止上述行为，并在收到本通知之日起3日内向");
			
			if (Config.cityTag.equals("cztnq") || Config.cityTag.equals("czxbq") || Config.cityTag.equals("czwjq")
					|| Config.cityTag.equals("czzlq") || Config.cityTag.equals("czqsyq")){
				content.append(Config.cgUnitNameZhiDui);//部门
			}else {
				if (Config.cityTag.equals("czs")){
					content.append(Config.cgUnitName);//部门
				}else if (Config.cityTag.equals("czjts")){
					content.append("金坛市城市管理行政执法大队" + Config.user.getBranch_name());//部门
				}else {
					content.append(Config.user.getBranch_name());//部门
				}
				
			}
			content.append("（地址：");
			content.append(Config.user.getBranch_address());
			content.append("，联系电话：");
			content.append(Config.user.getBranch_phone());
			content.append("）提供上述行为的相关合法手续，逾期不提供的，我局将依法查处。").append("_");
			//执法人员
			content.append("当事人签名:\r\n\r\n\r\n");
			content.append("电话:\r\n\r\n\r\n");
			content.append("执法人员签名:\r\n\r\n\r\n");
			content.append("见证人签名:\r\n\r\n\r\n").append("_");
			
//			content.append("执法人员:  ").append("_");
			
			//年月日
			content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" 年 ");
			content.append(String.valueOf(month)).append(" 月 ");
			content.append(String.valueOf(day)).append(" 日");
		}else {
			content.append(tv_form_title_all.getText().toString().trim()).append("\r\n");
			content.append(tv_form_title.getText().toString().trim()).append("_");
//			content.append(tempform.getFormName()).append("_");
			content.append(Config.posFormTitle +"执通字[").append(String.valueOf(year)).append("]  \r\nNo.")
			.append(tempCase.getLsh()).append("_");
			content.append(et_notification_nuit.getText().toString().trim()).append(":\r\n\r\n");
			content.append("    经查，你（单位）于  ").append(theTime)
			.append("在").append(et_illegal_location.getText().toString().trim())
			.append("进行").append(et_illegal_behavior.getText().toString().trim())
			.append("的行为，现场未能提供相关合法手续，已涉嫌违反")
			.append(tempCase.getFazhe().replaceAll(" ", "").replaceAll("\n", "").trim()).append("的有关规定，现责令你（单位）立即停止上述行为，并在收到本通知之日起3日内向");
			
			if (Config.cityTag.equals("cztnq") || Config.cityTag.equals("czxbq") || Config.cityTag.equals("czwjq")
					|| Config.cityTag.equals("czzlq") || Config.cityTag.equals("czqsyq")){
				content.append(Config.cgUnitNameZhiDui);//部门
			}else if (Config.cityTag.equals("czs")){
				content.append(Config.cgUnitName);//部门
			}else {
				content.append(Config.user.getBranch_name());//部门
			}
			content.append("（地址：");
			content.append(Config.user.getBranch_address());
			content.append("，联系电话：");
			content.append(Config.user.getBranch_phone());
			content.append("）提供上述行为的相关合法手续，逾期不提供的，视作无合法手续，我局将依法查处。").append("_");
			//执法人员
			content.append("当事人签名:\r\n\r\n\r\n");
			content.append("电话:\r\n\r\n\r\n");
			content.append("执法人员签名:\r\n\r\n\r\n");
			content.append("见证人签名:\r\n\r\n\r\n").append("_");
			
//			content.append("执法人员:  ").append("_");
			
			//年月日
			content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" 年 ");
			content.append(String.valueOf(month)).append(" 月 ");
			content.append(String.valueOf(day)).append(" 日");
		}
		
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContent2(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempfrom = Config.currentFormList.get(Config.bdPositionId);
//		Case tempC = Config.currentCaseList.get(position);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		StringBuffer content = new StringBuffer();
		
		content.append(String.valueOf(year)).append("_");//年
		if (tempCase.getLsh() == null || tempCase.getLsh().equals("")){
			content.append(" ").append("_");
		}else {
			content.append(tempCase.getLsh()).append("_");//流水号
		}
		//组织或 个人
		String danwei = "";
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
//				danwei = tempCase.getPerson();
				type = 1;
			}
		}else {
//			danwei = tempCase.getOrganise();
			type = 0;
		}
//		content.append(danwei).append("_");
		
		content.append(et_notification_nuit.getText().toString().trim()).append("_");
		//案发时间
		if (tempCase.getThetime() == null || tempCase.getThetime().equals("")){
			content.append(" ").append("_");
		}else {
			content.append(tempCase.getThetime()).append("_");//
		}
		
		content.append(et_illegal_location.getText().toString().trim()).append("_")
		.append(et_illegal_behavior.getText().toString().trim()).append("_");
//		.append(et_limited_date.getText().toString().trim());
		//有关规定
		if (tempCase.getFazhe() == null || tempCase.getFazhe().equals("")){
			content.append(" ").append("_");
		}else {
			content.append(tempCase.getFazhe()).append("_");//
		}
//		//地址
		content.append(Config.user.getBranch_address()).append("_");
//		if (type == 0){
//			if (tempCase.getOadd() == null || tempCase.getOadd().equals("")){
//				content.append(tempCase.getOadd()).append("_");
//			}else {
//				content.append(" ").append("_");
//			}
//		}else if (type == 1){
//			if (tempCase.getPadd() == null || tempCase.getPadd().equals("")){
//				content.append(tempCase.getPadd()).append("_");
//			}else {
//				content.append(" ").append("_");
//			}
//		}
		
		//联系电话
		content.append(Config.user.getBranch_phone()).append("_");
//		if (type == 0){
//			if (tempCase.getOtel() == null || tempCase.getOtel().equals("")){
//				content.append(tempCase.getOtel()).append("_");
//			}else {
//				content.append(" ").append("_");
//			}
//		}else if (type == 1){
//			if (tempCase.getPtel() == null || tempCase.getPtel().equals("")){
//				content.append(tempCase.getPtel()).append("_");
//			}else {
//				content.append(" ").append("_");
//			}
//		}
		
		//执法人员
		content.append(Config.user.getName()).append("_");
		//年月日
		content.append(String.valueOf(year)).append("_");
		content.append(String.valueOf(month)).append("_");
		content.append(String.valueOf(day));
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
			Intent it  = new Intent(ActivityFormProvide.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", Config.bdPositionId);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	private boolean checkInputLegal(){
		if (et_notification_nuit.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"提示", "通知单位 不能为空!", 0, "");
			return false;
		}else if (et_illegal_location.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"提示", "违法地点 不能为空!", 0, "");
			return false;
		}else if (et_illegal_behavior.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"提示", "违法行为 不能为空!", 0, "");
			return false;
		}else if (et_limited_date.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"提示", "限定日期 不能为空!", 0, "");
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//如果上报成功刷新列表  先传回数据 后kill 当前activity
	}
}
